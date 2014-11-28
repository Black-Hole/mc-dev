package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {

    public static final File a = new File("banned-players.json");
    public static final File b = new File("banned-ips.json");
    public static final File c = new File("ops.json");
    public static final File d = new File("whitelist.json");
    private static final Logger h = LogManager.getLogger();
    private static final SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
    private final MinecraftServer server;
    public final List players = Lists.newArrayList();
    public final Map f = Maps.newHashMap();
    private final GameProfileBanList k;
    private final IpBanList l;
    private final OpList operators;
    private final WhiteList whitelist;
    private final Map o;
    public IPlayerFileData playerFileData;
    public boolean hasWhitelist;
    protected int maxPlayers;
    private int r;
    private EnumGamemode s;
    private boolean t;
    private int u;

    public PlayerList(MinecraftServer minecraftserver) {
        this.k = new GameProfileBanList(PlayerList.a);
        this.l = new IpBanList(PlayerList.b);
        this.operators = new OpList(PlayerList.c);
        this.whitelist = new WhiteList(PlayerList.d);
        this.o = Maps.newHashMap();
        this.server = minecraftserver;
        this.k.a(false);
        this.l.a(false);
        this.maxPlayers = 8;
    }

    public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
        GameProfile gameprofile = entityplayer.getProfile();
        UserCache usercache = this.server.getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = this.a(entityplayer);

        entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer) entityplayer.world);
        String s1 = "local";

        if (networkmanager.getSocketAddress() != null) {
            s1 = networkmanager.getSocketAddress().toString();
        }

        PlayerList.h.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at (" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        WorldData worlddata = worldserver.getWorldData();
        BlockPosition blockposition = worldserver.getSpawn();

        this.a(entityplayer, (EntityPlayer) null, worldserver);
        PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);

        playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimension(), worldserver.getDifficulty(), this.getMaxPlayers(), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
        playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", (new PacketDataSerializer(Unpooled.buffer())).a(this.getServer().getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.y(), worlddata.z()));
        playerconnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition));
        playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
        entityplayer.getStatisticManager().d();
        entityplayer.getStatisticManager().updateStatistics(entityplayer);
        this.sendScoreboard((ScoreboardServer) worldserver.getScoreboard(), entityplayer);
        this.server.aF();
        ChatMessage chatmessage;

        if (!entityplayer.getName().equalsIgnoreCase(s)) {
            chatmessage = new ChatMessage("multiplayer.player.joined.renamed", new Object[] { entityplayer.getScoreboardDisplayName(), s});
        } else {
            chatmessage = new ChatMessage("multiplayer.player.joined", new Object[] { entityplayer.getScoreboardDisplayName()});
        }

        chatmessage.getChatModifier().setColor(EnumChatFormat.YELLOW);
        this.sendMessage(chatmessage);
        this.onPlayerJoin(entityplayer);
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        this.b(entityplayer, worldserver);
        if (this.server.getResourcePack().length() > 0) {
            entityplayer.setResourcePack(this.server.getResourcePack(), this.server.getResourcePackHash());
        }

        Iterator iterator = entityplayer.getEffects().iterator();

        while (iterator.hasNext()) {
            MobEffect mobeffect = (MobEffect) iterator.next();

            playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

        entityplayer.syncInventory();
        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Riding", 10)) {
            Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), (World) worldserver);

            if (entity != null) {
                entity.attachedToPlayer = true;
                worldserver.addEntity(entity);
                entityplayer.mount(entity);
                entity.attachedToPlayer = false;
            }
        }

    }

    public void sendScoreboard(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = scoreboardserver.getTeams().iterator();

        while (iterator.hasNext()) {
            ScoreboardTeam scoreboardteam = (ScoreboardTeam) iterator.next();

            entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
        }

        for (int i = 0; i < 19; ++i) {
            ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);

            if (scoreboardobjective != null && !hashset.contains(scoreboardobjective)) {
                List list = scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective);
                Iterator iterator1 = list.iterator();

                while (iterator1.hasNext()) {
                    Packet packet = (Packet) iterator1.next();

                    entityplayer.playerConnection.sendPacket(packet);
                }

                hashset.add(scoreboardobjective);
            }
        }

    }

    public void setPlayerFileData(WorldServer[] aworldserver) {
        this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
        aworldserver[0].af().a((IWorldBorderListener) (new WorldBorderListener(this)));
    }

    public void a(EntityPlayer entityplayer, WorldServer worldserver) {
        WorldServer worldserver1 = entityplayer.u();

        if (worldserver != null) {
            worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        }

        worldserver1.getPlayerChunkMap().addPlayer(entityplayer);
        worldserver1.chunkProviderServer.getChunkAt((int) entityplayer.locX >> 4, (int) entityplayer.locZ >> 4);
    }

    public int d() {
        return PlayerChunkMap.getFurthestViewableBlock(this.t());
    }

    public NBTTagCompound a(EntityPlayer entityplayer) {
        NBTTagCompound nbttagcompound = this.server.worldServer[0].getWorldData().i();
        NBTTagCompound nbttagcompound1;

        if (entityplayer.getName().equals(this.server.R()) && nbttagcompound != null) {
            entityplayer.f(nbttagcompound);
            nbttagcompound1 = nbttagcompound;
            PlayerList.h.debug("loading single player");
        } else {
            nbttagcompound1 = this.playerFileData.load(entityplayer);
        }

        return nbttagcompound1;
    }

    protected void savePlayerFile(EntityPlayer entityplayer) {
        this.playerFileData.save(entityplayer);
        ServerStatisticManager serverstatisticmanager = (ServerStatisticManager) this.o.get(entityplayer.getUniqueID());

        if (serverstatisticmanager != null) {
            serverstatisticmanager.b();
        }

    }

    public void onPlayerJoin(EntityPlayer entityplayer) {
        this.players.add(entityplayer);
        this.f.put(entityplayer.getUniqueID(), entityplayer);
        this.sendAll(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityplayer}));
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);

        worldserver.addEntity(entityplayer);
        this.a(entityplayer, (WorldServer) null);

        for (int i = 0; i < this.players.size(); ++i) {
            EntityPlayer entityplayer1 = (EntityPlayer) this.players.get(i);

            entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityplayer1}));
        }

    }

    public void d(EntityPlayer entityplayer) {
        entityplayer.u().getPlayerChunkMap().movePlayer(entityplayer);
    }

    public void disconnect(EntityPlayer entityplayer) {
        entityplayer.b(StatisticList.f);
        this.savePlayerFile(entityplayer);
        WorldServer worldserver = entityplayer.u();

        if (entityplayer.vehicle != null) {
            worldserver.removeEntity(entityplayer.vehicle);
            PlayerList.h.debug("removing player mount");
        }

        worldserver.kill(entityplayer);
        worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        this.f.remove(entityplayer.getUniqueID());
        this.o.remove(entityplayer.getUniqueID());
        this.sendAll(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { entityplayer}));
    }

    public String attemptLogin(SocketAddress socketaddress, GameProfile gameprofile) {
        String s;

        if (this.k.isBanned(gameprofile)) {
            GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry) this.k.get(gameprofile);

            s = "You are banned from this server!\nReason: " + gameprofilebanentry.getReason();
            if (gameprofilebanentry.getExpires() != null) {
                s = s + "\nYour ban will be removed on " + PlayerList.i.format(gameprofilebanentry.getExpires());
            }

            return s;
        } else if (!this.isWhitelisted(gameprofile)) {
            return "You are not white-listed on this server!";
        } else if (this.l.isBanned(socketaddress)) {
            IpBanEntry ipbanentry = this.l.get(socketaddress);

            s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getReason();
            if (ipbanentry.getExpires() != null) {
                s = s + "\nYour ban will be removed on " + PlayerList.i.format(ipbanentry.getExpires());
            }

            return s;
        } else {
            return this.players.size() >= this.maxPlayers ? "The server is full!" : null;
        }
    }

    public EntityPlayer processLogin(GameProfile gameprofile) {
        UUID uuid = EntityHuman.a(gameprofile);
        ArrayList arraylist = Lists.newArrayList();

        EntityPlayer entityplayer;

        for (int i = 0; i < this.players.size(); ++i) {
            entityplayer = (EntityPlayer) this.players.get(i);
            if (entityplayer.getUniqueID().equals(uuid)) {
                arraylist.add(entityplayer);
            }
        }

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            entityplayer = (EntityPlayer) iterator.next();
            entityplayer.playerConnection.disconnect("You logged in from another location");
        }

        Object object;

        if (this.server.W()) {
            object = new DemoPlayerInteractManager(this.server.getWorldServer(0));
        } else {
            object = new PlayerInteractManager(this.server.getWorldServer(0));
        }

        return new EntityPlayer(this.server, this.server.getWorldServer(0), gameprofile, (PlayerInteractManager) object);
    }

    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag) {
        entityplayer.u().getTracker().untrackPlayer(entityplayer);
        entityplayer.u().getTracker().untrackEntity(entityplayer);
        entityplayer.u().getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
        BlockPosition blockposition = entityplayer.getBed();
        boolean flag1 = entityplayer.isRespawnForced();

        entityplayer.dimension = i;
        Object object;

        if (this.server.W()) {
            object = new DemoPlayerInteractManager(this.server.getWorldServer(entityplayer.dimension));
        } else {
            object = new PlayerInteractManager(this.server.getWorldServer(entityplayer.dimension));
        }

        EntityPlayer entityplayer1 = new EntityPlayer(this.server, this.server.getWorldServer(entityplayer.dimension), entityplayer.getProfile(), (PlayerInteractManager) object);

        entityplayer1.playerConnection = entityplayer.playerConnection;
        entityplayer1.copyTo(entityplayer, flag);
        entityplayer1.d(entityplayer.getId());
        entityplayer1.o(entityplayer);
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);

        this.a(entityplayer1, entityplayer, worldserver);
        BlockPosition blockposition1;

        if (blockposition != null) {
            blockposition1 = EntityHuman.getBed(this.server.getWorldServer(entityplayer.dimension), blockposition, flag1);
            if (blockposition1 != null) {
                entityplayer1.setPositionRotation((double) ((float) blockposition1.getX() + 0.5F), (double) ((float) blockposition1.getY() + 0.1F), (double) ((float) blockposition1.getZ() + 0.5F), 0.0F, 0.0F);
                entityplayer1.setRespawnPosition(blockposition, flag1);
            } else {
                entityplayer1.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
            }
        }

        worldserver.chunkProviderServer.getChunkAt((int) entityplayer1.locX >> 4, (int) entityplayer1.locZ >> 4);

        while (!worldserver.getCubes(entityplayer1, entityplayer1.getBoundingBox()).isEmpty() && entityplayer1.locY < 256.0D) {
            entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
        }

        entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn(entityplayer1.dimension, entityplayer1.world.getDifficulty(), entityplayer1.world.getWorldData().getType(), entityplayer1.playerInteractManager.getGameMode()));
        blockposition1 = worldserver.getSpawn();
        entityplayer1.playerConnection.a(entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch);
        entityplayer1.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition1));
        entityplayer1.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer1.exp, entityplayer1.expTotal, entityplayer1.expLevel));
        this.b(entityplayer1, worldserver);
        worldserver.getPlayerChunkMap().addPlayer(entityplayer1);
        worldserver.addEntity(entityplayer1);
        this.players.add(entityplayer1);
        this.f.put(entityplayer1.getUniqueID(), entityplayer1);
        entityplayer1.syncInventory();
        entityplayer1.setHealth(entityplayer1.getHealth());
        return entityplayer1;
    }

    public void changeDimension(EntityPlayer entityplayer, int i) {
        int j = entityplayer.dimension;
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);

        entityplayer.dimension = i;
        WorldServer worldserver1 = this.server.getWorldServer(entityplayer.dimension);

        entityplayer.playerConnection.sendPacket(new PacketPlayOutRespawn(entityplayer.dimension, entityplayer.world.getDifficulty(), entityplayer.world.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
        worldserver.removeEntity(entityplayer);
        entityplayer.dead = false;
        this.changeWorld(entityplayer, j, worldserver, worldserver1);
        this.a(entityplayer, worldserver);
        entityplayer.playerConnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        entityplayer.playerInteractManager.a(worldserver1);
        this.b(entityplayer, worldserver1);
        this.updateClient(entityplayer);
        Iterator iterator = entityplayer.getEffects().iterator();

        while (iterator.hasNext()) {
            MobEffect mobeffect = (MobEffect) iterator.next();

            entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

    }

    public void changeWorld(Entity entity, int i, WorldServer worldserver, WorldServer worldserver1) {
        double d0 = entity.locX;
        double d1 = entity.locZ;
        double d2 = 8.0D;
        float f = entity.yaw;

        worldserver.methodProfiler.a("moving");
        if (entity.dimension == -1) {
            d0 = MathHelper.a(d0 / d2, worldserver1.af().b() + 16.0D, worldserver1.af().d() - 16.0D);
            d1 = MathHelper.a(d1 / d2, worldserver1.af().c() + 16.0D, worldserver1.af().e() - 16.0D);
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else if (entity.dimension == 0) {
            d0 = MathHelper.a(d0 * d2, worldserver1.af().b() + 16.0D, worldserver1.af().d() - 16.0D);
            d1 = MathHelper.a(d1 * d2, worldserver1.af().c() + 16.0D, worldserver1.af().e() - 16.0D);
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else {
            BlockPosition blockposition;

            if (i == 1) {
                blockposition = worldserver1.getSpawn();
            } else {
                blockposition = worldserver1.getDimensionSpawn();
            }

            d0 = (double) blockposition.getX();
            entity.locY = (double) blockposition.getY();
            d1 = (double) blockposition.getZ();
            entity.setPositionRotation(d0, entity.locY, d1, 90.0F, 0.0F);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        }

        worldserver.methodProfiler.b();
        if (i != 1) {
            worldserver.methodProfiler.a("placing");
            d0 = (double) MathHelper.clamp((int) d0, -29999872, 29999872);
            d1 = (double) MathHelper.clamp((int) d1, -29999872, 29999872);
            if (entity.isAlive()) {
                entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
                worldserver1.getTravelAgent().a(entity, f);
                worldserver1.addEntity(entity);
                worldserver1.entityJoinedWorld(entity, false);
            }

            worldserver.methodProfiler.b();
        }

        entity.spawnIn(worldserver1);
    }

    public void tick() {
        if (++this.u > 600) {
            this.sendAll(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_LATENCY, this.players));
            this.u = 0;
        }

    }

    public void sendAll(Packet packet) {
        for (int i = 0; i < this.players.size(); ++i) {
            ((EntityPlayer) this.players.get(i)).playerConnection.sendPacket(packet);
        }

    }

    public void a(Packet packet, int i) {
        for (int j = 0; j < this.players.size(); ++j) {
            EntityPlayer entityplayer = (EntityPlayer) this.players.get(j);

            if (entityplayer.dimension == i) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }

    }

    public void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
        ScoreboardTeamBase scoreboardteambase = entityhuman.getScoreboardTeam();

        if (scoreboardteambase != null) {
            Collection collection = scoreboardteambase.getPlayerNameSet();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                EntityPlayer entityplayer = this.getPlayer(s);

                if (entityplayer != null && entityplayer != entityhuman) {
                    entityplayer.sendMessage(ichatbasecomponent);
                }
            }

        }
    }

    public void b(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
        ScoreboardTeamBase scoreboardteambase = entityhuman.getScoreboardTeam();

        if (scoreboardteambase == null) {
            this.sendMessage(ichatbasecomponent);
        } else {
            for (int i = 0; i < this.players.size(); ++i) {
                EntityPlayer entityplayer = (EntityPlayer) this.players.get(i);

                if (entityplayer.getScoreboardTeam() != scoreboardteambase) {
                    entityplayer.sendMessage(ichatbasecomponent);
                }
            }

        }
    }

    public String f() {
        String s = "";

        for (int i = 0; i < this.players.size(); ++i) {
            if (i > 0) {
                s = s + ", ";
            }

            s = s + ((EntityPlayer) this.players.get(i)).getName();
        }

        return s;
    }

    public String[] g() {
        String[] astring = new String[this.players.size()];

        for (int i = 0; i < this.players.size(); ++i) {
            astring[i] = ((EntityPlayer) this.players.get(i)).getName();
        }

        return astring;
    }

    public GameProfile[] h() {
        GameProfile[] agameprofile = new GameProfile[this.players.size()];

        for (int i = 0; i < this.players.size(); ++i) {
            agameprofile[i] = ((EntityPlayer) this.players.get(i)).getProfile();
        }

        return agameprofile;
    }

    public GameProfileBanList getProfileBans() {
        return this.k;
    }

    public IpBanList getIPBans() {
        return this.l;
    }

    public void addOp(GameProfile gameprofile) {
        this.operators.add(new OpListEntry(gameprofile, this.server.p()));
    }

    public void removeOp(GameProfile gameprofile) {
        this.operators.remove(gameprofile);
    }

    public boolean isWhitelisted(GameProfile gameprofile) {
        return !this.hasWhitelist || this.operators.d(gameprofile) || this.whitelist.d(gameprofile);
    }

    public boolean isOp(GameProfile gameprofile) {
        return this.operators.d(gameprofile) || this.server.S() && this.server.worldServer[0].getWorldData().v() && this.server.R().equalsIgnoreCase(gameprofile.getName()) || this.t;
    }

    public EntityPlayer getPlayer(String s) {
        Iterator iterator = this.players.iterator();

        EntityPlayer entityplayer;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entityplayer = (EntityPlayer) iterator.next();
        } while (!entityplayer.getName().equalsIgnoreCase(s));

        return entityplayer;
    }

    public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) {
        this.sendPacketNearby((EntityHuman) null, d0, d1, d2, d3, i, packet);
    }

    public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
        for (int j = 0; j < this.players.size(); ++j) {
            EntityPlayer entityplayer = (EntityPlayer) this.players.get(j);

            if (entityplayer != entityhuman && entityplayer.dimension == i) {
                double d4 = d0 - entityplayer.locX;
                double d5 = d1 - entityplayer.locY;
                double d6 = d2 - entityplayer.locZ;

                if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                    entityplayer.playerConnection.sendPacket(packet);
                }
            }
        }

    }

    public void savePlayers() {
        for (int i = 0; i < this.players.size(); ++i) {
            this.savePlayerFile((EntityPlayer) this.players.get(i));
        }

    }

    public void addWhitelist(GameProfile gameprofile) {
        this.whitelist.add(new WhiteListEntry(gameprofile));
    }

    public void removeWhitelist(GameProfile gameprofile) {
        this.whitelist.remove(gameprofile);
    }

    public WhiteList getWhitelist() {
        return this.whitelist;
    }

    public String[] getWhitelisted() {
        return this.whitelist.getEntries();
    }

    public OpList getOPs() {
        return this.operators;
    }

    public String[] o() {
        return this.operators.getEntries();
    }

    public void reloadWhitelist() {}

    public void b(EntityPlayer entityplayer, WorldServer worldserver) {
        WorldBorder worldborder = this.server.worldServer[0].af();

        entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldborder, EnumWorldBorderAction.INITIALIZE));
        entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
        if (worldserver.S()) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0.0F));
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, worldserver.j(1.0F)));
            entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, worldserver.h(1.0F)));
        }

    }

    public void updateClient(EntityPlayer entityplayer) {
        entityplayer.updateInventory(entityplayer.defaultContainer);
        entityplayer.triggerHealthUpdate();
        entityplayer.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String[] getSeenPlayers() {
        return this.server.worldServer[0].getDataManager().getPlayerFileData().getSeenPlayers();
    }

    public boolean getHasWhitelist() {
        return this.hasWhitelist;
    }

    public void setHasWhitelist(boolean flag) {
        this.hasWhitelist = flag;
    }

    public List b(String s) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.players.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();

            if (entityplayer.w().equals(s)) {
                arraylist.add(entityplayer);
            }
        }

        return arraylist;
    }

    public int t() {
        return this.r;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public NBTTagCompound u() {
        return null;
    }

    private void a(EntityPlayer entityplayer, EntityPlayer entityplayer1, World world) {
        if (entityplayer1 != null) {
            entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
        } else if (this.s != null) {
            entityplayer.playerInteractManager.setGameMode(this.s);
        }

        entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
    }

    public void v() {
        for (int i = 0; i < this.players.size(); ++i) {
            ((EntityPlayer) this.players.get(i)).playerConnection.disconnect("Server closed");
        }

    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag) {
        this.server.sendMessage(ichatbasecomponent);
        int i = flag ? 1 : 0;

        this.sendAll(new PacketPlayOutChat(ichatbasecomponent, (byte) i));
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        this.sendMessage(ichatbasecomponent, true);
    }

    public ServerStatisticManager a(EntityHuman entityhuman) {
        UUID uuid = entityhuman.getUniqueID();
        ServerStatisticManager serverstatisticmanager = uuid == null ? null : (ServerStatisticManager) this.o.get(uuid);

        if (serverstatisticmanager == null) {
            File file = new File(this.server.getWorldServer(0).getDataManager().getDirectory(), "stats");
            File file1 = new File(file, uuid.toString() + ".json");

            if (!file1.exists()) {
                File file2 = new File(file, entityhuman.getName() + ".json");

                if (file2.exists() && file2.isFile()) {
                    file2.renameTo(file1);
                }
            }

            serverstatisticmanager = new ServerStatisticManager(this.server, file1);
            serverstatisticmanager.a();
            this.o.put(uuid, serverstatisticmanager);
        }

        return serverstatisticmanager;
    }

    public void a(int i) {
        this.r = i;
        if (this.server.worldServer != null) {
            WorldServer[] aworldserver = this.server.worldServer;
            int j = aworldserver.length;

            for (int k = 0; k < j; ++k) {
                WorldServer worldserver = aworldserver[k];

                if (worldserver != null) {
                    worldserver.getPlayerChunkMap().a(i);
                }
            }

        }
    }

    public EntityPlayer a(UUID uuid) {
        return (EntityPlayer) this.f.get(uuid);
    }
}

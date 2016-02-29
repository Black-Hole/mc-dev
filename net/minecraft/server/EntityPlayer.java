package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayer extends EntityHuman implements ICrafting {

    private static final Logger bQ = LogManager.getLogger();
    private String locale = "en_US";
    public PlayerConnection playerConnection;
    public final MinecraftServer server;
    public final PlayerInteractManager playerInteractManager;
    public double d;
    public double e;
    public final List<Integer> removeQueue = Lists.newLinkedList();
    private final ServerStatisticManager bT;
    private float bU = Float.MIN_VALUE;
    private int bV = Integer.MIN_VALUE;
    private int bW = Integer.MIN_VALUE;
    private int bX = Integer.MIN_VALUE;
    private int bY = Integer.MIN_VALUE;
    private int bZ = Integer.MIN_VALUE;
    private float lastHealthSent = -1.0E8F;
    private int cb = -99999999;
    private boolean cc = true;
    public int lastSentExp = -99999999;
    public int invulnerableTicks = 60;
    private EntityHuman.EnumChatVisibility cf;
    private boolean cg = true;
    private long ch = System.currentTimeMillis();
    private Entity ci = null;
    private boolean cj;
    private int containerCounter;
    public boolean f;
    public int ping;
    public boolean viewingCredits;

    public EntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(worldserver, gameprofile);
        playerinteractmanager.player = this;
        this.playerInteractManager = playerinteractmanager;
        BlockPosition blockposition = worldserver.getSpawn();

        if (!worldserver.worldProvider.m() && worldserver.getWorldData().getGameType() != WorldSettings.EnumGamemode.ADVENTURE) {
            int i = Math.max(0, minecraftserver.a(worldserver));
            int j = MathHelper.floor(worldserver.getWorldBorder().b((double) blockposition.getX(), (double) blockposition.getZ()));

            if (j < i) {
                i = j;
            }

            if (j <= 1) {
                i = 1;
            }

            blockposition = worldserver.q(blockposition.a(this.random.nextInt(i * 2 + 1) - i, 0, this.random.nextInt(i * 2 + 1) - i));
        }

        this.server = minecraftserver;
        this.bT = minecraftserver.getPlayerList().a((EntityHuman) this);
        this.P = 0.0F;
        this.setPositionRotation(blockposition, 0.0F, 0.0F);

        while (!worldserver.getCubes(this, this.getBoundingBox()).isEmpty() && this.locY < 255.0D) {
            this.setPosition(this.locX, this.locY + 1.0D, this.locZ);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("playerGameType", 99)) {
            if (this.h().getForceGamemode()) {
                this.playerInteractManager.setGameMode(this.h().getGamemode());
            } else {
                this.playerInteractManager.setGameMode(WorldSettings.EnumGamemode.getById(nbttagcompound.getInt("playerGameType")));
            }
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("playerGameType", this.playerInteractManager.getGameMode().getId());
        Entity entity = this.getVehicle();

        if (this.by() != null && entity != this & entity.b(EntityPlayer.class).size() == 1) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();

            entity.d(nbttagcompound2);
            nbttagcompound1.a("Attach", this.by().getUniqueID());
            nbttagcompound1.set("Entity", nbttagcompound2);
            nbttagcompound.set("RootVehicle", nbttagcompound1);
        }

    }

    public void levelDown(int i) {
        super.levelDown(i);
        this.lastSentExp = -1;
    }

    public void enchantDone(int i) {
        super.enchantDone(i);
        this.lastSentExp = -1;
    }

    public void syncInventory() {
        this.activeContainer.addSlotListener(this);
    }

    public void enterCombat() {
        super.enterCombat();
        this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(this.getCombatTracker(), PacketPlayOutCombatEvent.EnumCombatEventType.ENTER_COMBAT));
    }

    public void exitCombat() {
        super.exitCombat();
        this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(this.getCombatTracker(), PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT));
    }

    protected ItemCooldown l() {
        return new ItemCooldownPlayer(this);
    }

    public void m() {
        this.playerInteractManager.a();
        --this.invulnerableTicks;
        if (this.noDamageTicks > 0) {
            --this.noDamageTicks;
        }

        this.activeContainer.b();
        if (!this.world.isClientSide && !this.activeContainer.a((EntityHuman) this)) {
            this.closeInventory();
            this.activeContainer = this.defaultContainer;
        }

        while (!this.removeQueue.isEmpty()) {
            int i = Math.min(this.removeQueue.size(), Integer.MAX_VALUE);
            int[] aint = new int[i];
            Iterator iterator = this.removeQueue.iterator();
            int j = 0;

            while (iterator.hasNext() && j < i) {
                aint[j++] = ((Integer) iterator.next()).intValue();
                iterator.remove();
            }

            this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(aint));
        }

        Entity entity = this.getSpecatorTarget();

        if (entity != this) {
            if (!entity.isAlive()) {
                this.setSpectatorTarget(this);
            } else {
                this.setLocation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
                this.server.getPlayerList().d(this);
                if (this.isSneaking()) {
                    this.setSpectatorTarget(this);
                }
            }
        }

    }

    public void k_() {
        try {
            super.m();

            for (int i = 0; i < this.inventory.getSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);

                if (itemstack != null && itemstack.getItem().f()) {
                    Packet packet = ((ItemWorldMapBase) itemstack.getItem()).a(itemstack, this.world, (EntityHuman) this);

                    if (packet != null) {
                        this.playerConnection.sendPacket(packet);
                    }
                }
            }

            if (this.getHealth() != this.lastHealthSent || this.cb != this.foodData.getFoodLevel() || this.foodData.getSaturationLevel() == 0.0F != this.cc) {
                this.playerConnection.sendPacket(new PacketPlayOutUpdateHealth(this.getHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
                this.lastHealthSent = this.getHealth();
                this.cb = this.foodData.getFoodLevel();
                this.cc = this.foodData.getSaturationLevel() == 0.0F;
            }

            if (this.getHealth() + this.getAbsorptionHearts() != this.bU) {
                this.bU = this.getHealth() + this.getAbsorptionHearts();
                this.a(IScoreboardCriteria.g, MathHelper.f(this.bU));
            }

            if (this.foodData.getFoodLevel() != this.bV) {
                this.bV = this.foodData.getFoodLevel();
                this.a(IScoreboardCriteria.h, MathHelper.f((float) this.bV));
            }

            if (this.getAirTicks() != this.bW) {
                this.bW = this.getAirTicks();
                this.a(IScoreboardCriteria.i, MathHelper.f((float) this.bW));
            }

            if (this.getArmorStrength() != this.bX) {
                this.bX = this.getArmorStrength();
                this.a(IScoreboardCriteria.j, MathHelper.f((float) this.bX));
            }

            if (this.expTotal != this.bZ) {
                this.bZ = this.expTotal;
                this.a(IScoreboardCriteria.k, MathHelper.f((float) this.bZ));
            }

            if (this.expLevel != this.bY) {
                this.bY = this.expLevel;
                this.a(IScoreboardCriteria.l, MathHelper.f((float) this.bY));
            }

            if (this.expTotal != this.lastSentExp) {
                this.lastSentExp = this.expTotal;
                this.playerConnection.sendPacket(new PacketPlayOutExperience(this.exp, this.expTotal, this.expLevel));
            }

            if (this.ticksLived % 20 * 5 == 0 && !this.getStatisticManager().hasAchievement(AchievementList.L)) {
                this.o();
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");

            this.appendEntityCrashDetails(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }

    private void a(IScoreboardCriteria iscoreboardcriteria, int i) {
        Collection collection = this.getScoreboard().getObjectivesForCriteria(iscoreboardcriteria);
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();
            ScoreboardScore scoreboardscore = this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective);

            scoreboardscore.setScore(i);
        }

    }

    protected void o() {
        BiomeBase biomebase = this.world.getBiome(new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ)));
        String s = biomebase.l();
        AchievementSet achievementset = (AchievementSet) this.getStatisticManager().b((Statistic) AchievementList.L);

        if (achievementset == null) {
            achievementset = (AchievementSet) this.getStatisticManager().a(AchievementList.L, new AchievementSet());
        }

        achievementset.add(s);
        if (this.getStatisticManager().b(AchievementList.L) && achievementset.size() >= BiomeBase.i.size()) {
            HashSet hashset = Sets.newHashSet(BiomeBase.i);
            Iterator iterator = achievementset.iterator();

            while (iterator.hasNext()) {
                String s1 = (String) iterator.next();
                Iterator iterator1 = hashset.iterator();

                while (iterator1.hasNext()) {
                    BiomeBase biomebase1 = (BiomeBase) iterator1.next();

                    if (biomebase1.l().equals(s1)) {
                        iterator1.remove();
                    }
                }

                if (hashset.isEmpty()) {
                    break;
                }
            }

            if (hashset.isEmpty()) {
                this.b((Statistic) AchievementList.L);
            }
        }

    }

    public void die(DamageSource damagesource) {
        boolean flag = this.world.getGameRules().getBoolean("showDeathMessages");

        this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(this.getCombatTracker(), PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED, flag));
        if (flag) {
            ScoreboardTeamBase scoreboardteambase = this.aO();

            if (scoreboardteambase != null && scoreboardteambase.j() != ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS) {
                if (scoreboardteambase.j() == ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OTHER_TEAMS) {
                    this.server.getPlayerList().a((EntityHuman) this, this.getCombatTracker().getDeathMessage());
                } else if (scoreboardteambase.j() == ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OWN_TEAM) {
                    this.server.getPlayerList().b((EntityHuman) this, this.getCombatTracker().getDeathMessage());
                }
            } else {
                this.server.getPlayerList().sendMessage(this.getCombatTracker().getDeathMessage());
            }
        }

        if (!this.world.getGameRules().getBoolean("keepInventory") && !this.isSpectator()) {
            this.inventory.n();
        }

        Collection collection = this.world.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.d);
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();
            ScoreboardScore scoreboardscore = this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective);

            scoreboardscore.incrementScore();
        }

        EntityLiving entityliving = this.bV();

        if (entityliving != null) {
            EntityTypes.MonsterEggInfo entitytypes_monsteregginfo = (EntityTypes.MonsterEggInfo) EntityTypes.eggInfo.get(EntityTypes.b((Entity) entityliving));

            if (entitytypes_monsteregginfo != null) {
                this.b(entitytypes_monsteregginfo.e);
            }

            entityliving.b(this, this.ba);
        }

        this.b(StatisticList.A);
        this.a(StatisticList.h);
        this.getCombatTracker().g();
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            boolean flag = this.server.aa() && this.canPvP() && "fall".equals(damagesource.translationIndex);

            if (!flag && this.invulnerableTicks > 0 && damagesource != DamageSource.OUT_OF_WORLD) {
                return false;
            } else {
                if (damagesource instanceof EntityDamageSource) {
                    Entity entity = damagesource.getEntity();

                    if (entity instanceof EntityHuman && !this.a((EntityHuman) entity)) {
                        return false;
                    }

                    if (entity instanceof EntityArrow) {
                        EntityArrow entityarrow = (EntityArrow) entity;

                        if (entityarrow.shooter instanceof EntityHuman && !this.a((EntityHuman) entityarrow.shooter)) {
                            return false;
                        }
                    }
                }

                return super.damageEntity(damagesource, f);
            }
        }
    }

    public boolean a(EntityHuman entityhuman) {
        return !this.canPvP() ? false : super.a(entityhuman);
    }

    private boolean canPvP() {
        return this.server.getPVP();
    }

    public Entity c(int i) {
        this.cj = true;
        if (this.dimension == 1 && i == 1) {
            this.world.kill(this);
            if (!this.viewingCredits) {
                this.viewingCredits = true;
                if (this.a(AchievementList.D)) {
                    this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 0.0F));
                } else {
                    this.b((Statistic) AchievementList.D);
                    this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 1.0F));
                }
            }

            return this;
        } else {
            if (this.dimension == 0 && i == 1) {
                this.b((Statistic) AchievementList.C);
                i = 1;
            } else {
                this.b((Statistic) AchievementList.y);
            }

            this.server.getPlayerList().a(this, i);
            this.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1032, BlockPosition.ZERO, 0, false));
            this.lastSentExp = -1;
            this.lastHealthSent = -1.0F;
            this.cb = -1;
            return this;
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        return entityplayer.isSpectator() ? this.getSpecatorTarget() == this : (this.isSpectator() ? false : super.a(entityplayer));
    }

    private void a(TileEntity tileentity) {
        if (tileentity != null) {
            Packet packet = tileentity.getUpdatePacket();

            if (packet != null) {
                this.playerConnection.sendPacket(packet);
            }
        }

    }

    public void receive(Entity entity, int i) {
        super.receive(entity, i);
        this.activeContainer.b();
    }

    public EntityHuman.EnumBedResult a(BlockPosition blockposition) {
        EntityHuman.EnumBedResult entityhuman_enumbedresult = super.a(blockposition);

        if (entityhuman_enumbedresult == EntityHuman.EnumBedResult.OK) {
            this.b(StatisticList.ad);
            PacketPlayOutBed packetplayoutbed = new PacketPlayOutBed(this, blockposition);

            this.x().getTracker().a((Entity) this, (Packet) packetplayoutbed);
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.playerConnection.sendPacket(packetplayoutbed);
        }

        return entityhuman_enumbedresult;
    }

    public void a(boolean flag, boolean flag1, boolean flag2) {
        if (this.isSleeping()) {
            this.x().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 2));
        }

        super.a(flag, flag1, flag2);
        if (this.playerConnection != null) {
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        }

    }

    public boolean a(Entity entity, boolean flag) {
        Entity entity1 = this.by();

        if (!super.a(entity, flag)) {
            return false;
        } else {
            Entity entity2 = this.by();

            if (entity2 != entity1 && this.playerConnection != null) {
                this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            }

            return true;
        }
    }

    public void stopRiding() {
        Entity entity = this.by();

        super.stopRiding();
        Entity entity1 = this.by();

        if (entity1 != entity && this.playerConnection != null) {
            this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        }

    }

    public boolean isInvulnerable(DamageSource damagesource) {
        return super.isInvulnerable(damagesource) || this.K();
    }

    protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {}

    protected void b(BlockPosition blockposition) {
        if (!this.isSpectator()) {
            super.b(blockposition);
        }

    }

    public void a(double d0, boolean flag) {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locY - 0.20000000298023224D);
        int k = MathHelper.floor(this.locZ);
        BlockPosition blockposition = new BlockPosition(i, j, k);
        IBlockData iblockdata = this.world.getType(blockposition);

        if (iblockdata.getMaterial() == Material.AIR) {
            BlockPosition blockposition1 = blockposition.down();
            IBlockData iblockdata1 = this.world.getType(blockposition1);
            Block block = iblockdata1.getBlock();

            if (block instanceof BlockFence || block instanceof BlockCobbleWall || block instanceof BlockFenceGate) {
                blockposition = blockposition1;
                iblockdata = iblockdata1;
            }
        }

        super.a(d0, flag, iblockdata, blockposition);
    }

    public void openSign(TileEntitySign tileentitysign) {
        tileentitysign.a((EntityHuman) this);
        this.playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(tileentitysign.getPosition()));
    }

    public void nextContainerCounter() {
        this.containerCounter = this.containerCounter % 100 + 1;
    }

    public void openTileEntity(ITileEntityContainer itileentitycontainer) {
        if (itileentitycontainer instanceof ILootable && ((ILootable) itileentitycontainer).b() != null && this.isSpectator()) {
            this.sendMessage((new ChatMessage("container.spectatorCantOpen", new Object[0])).setChatModifier((new ChatModifier()).setColor(EnumChatFormat.RED)));
        } else {
            this.nextContainerCounter();
            this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, itileentitycontainer.getContainerName(), itileentitycontainer.getScoreboardDisplayName()));
            this.activeContainer = itileentitycontainer.createContainer(this.inventory, this);
            this.activeContainer.windowId = this.containerCounter;
            this.activeContainer.addSlotListener(this);
        }
    }

    public void openContainer(IInventory iinventory) {
        if (iinventory instanceof ILootable && ((ILootable) iinventory).b() != null && this.isSpectator()) {
            this.sendMessage((new ChatMessage("container.spectatorCantOpen", new Object[0])).setChatModifier((new ChatModifier()).setColor(EnumChatFormat.RED)));
        } else {
            if (this.activeContainer != this.defaultContainer) {
                this.closeInventory();
            }

            if (iinventory instanceof ITileInventory) {
                ITileInventory itileinventory = (ITileInventory) iinventory;

                if (itileinventory.x_() && !this.a(itileinventory.y_()) && !this.isSpectator()) {
                    this.playerConnection.sendPacket(new PacketPlayOutChat(new ChatMessage("container.isLocked", new Object[] { iinventory.getScoreboardDisplayName()}), (byte) 2));
                    this.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(SoundEffects.W, SoundCategory.BLOCKS, this.locX, this.locY, this.locZ, 1.0F, 1.0F));
                    return;
                }
            }

            this.nextContainerCounter();
            if (iinventory instanceof ITileEntityContainer) {
                this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, ((ITileEntityContainer) iinventory).getContainerName(), iinventory.getScoreboardDisplayName(), iinventory.getSize()));
                this.activeContainer = ((ITileEntityContainer) iinventory).createContainer(this.inventory, this);
            } else {
                this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:container", iinventory.getScoreboardDisplayName(), iinventory.getSize()));
                this.activeContainer = new ContainerChest(this.inventory, iinventory, this);
            }

            this.activeContainer.windowId = this.containerCounter;
            this.activeContainer.addSlotListener(this);
        }
    }

    public void openTrade(IMerchant imerchant) {
        this.nextContainerCounter();
        this.activeContainer = new ContainerMerchant(this.inventory, imerchant, this.world);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
        InventoryMerchant inventorymerchant = ((ContainerMerchant) this.activeContainer).e();
        IChatBaseComponent ichatbasecomponent = imerchant.getScoreboardDisplayName();

        this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:villager", ichatbasecomponent, inventorymerchant.getSize()));
        MerchantRecipeList merchantrecipelist = imerchant.getOffers(this);

        if (merchantrecipelist != null) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());

            packetdataserializer.writeInt(this.containerCounter);
            merchantrecipelist.a(packetdataserializer);
            this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", packetdataserializer));
        }

    }

    public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory) {
        if (this.activeContainer != this.defaultContainer) {
            this.closeInventory();
        }

        this.nextContainerCounter();
        this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "EntityHorse", iinventory.getScoreboardDisplayName(), iinventory.getSize(), entityhorse.getId()));
        this.activeContainer = new ContainerHorse(this.inventory, iinventory, entityhorse, this);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    public void a(ItemStack itemstack, EnumHand enumhand) {
        Item item = itemstack.getItem();

        if (item == Items.WRITTEN_BOOK) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());

            packetdataserializer.a((Enum) enumhand);
            this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", packetdataserializer));
        }

    }

    public void a(TileEntityCommand tileentitycommand) {
        if (this.a(2, "")) {
            tileentitycommand.d(true);
            this.a((TileEntity) tileentitycommand);
        }

    }

    public void a(Container container, int i, ItemStack itemstack) {
        if (!(container.getSlot(i) instanceof SlotResult)) {
            if (!this.f) {
                this.playerConnection.sendPacket(new PacketPlayOutSetSlot(container.windowId, i, itemstack));
            }
        }
    }

    public void updateInventory(Container container) {
        this.a(container, container.a());
    }

    public void a(Container container, List<ItemStack> list) {
        this.playerConnection.sendPacket(new PacketPlayOutWindowItems(container.windowId, list));
        this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
    }

    public void setContainerData(Container container, int i, int j) {
        this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, i, j));
    }

    public void setContainerData(Container container, IInventory iinventory) {
        for (int i = 0; i < iinventory.g(); ++i) {
            this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, i, iinventory.getProperty(i)));
        }

    }

    public void closeInventory() {
        this.playerConnection.sendPacket(new PacketPlayOutCloseWindow(this.activeContainer.windowId));
        this.s();
    }

    public void broadcastCarriedItem() {
        if (!this.f) {
            this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
        }
    }

    public void s() {
        this.activeContainer.b((EntityHuman) this);
        this.activeContainer = this.defaultContainer;
    }

    public void a(float f, float f1, boolean flag, boolean flag1) {
        if (this.isPassenger()) {
            if (f >= -1.0F && f <= 1.0F) {
                this.bd = f;
            }

            if (f1 >= -1.0F && f1 <= 1.0F) {
                this.be = f1;
            }

            this.bc = flag;
            this.setSneaking(flag1);
        }

    }

    public boolean a(Achievement achievement) {
        return this.bT.hasAchievement(achievement);
    }

    public void a(Statistic statistic, int i) {
        if (statistic != null) {
            this.bT.b(this, statistic, i);
            Iterator iterator = this.getScoreboard().getObjectivesForCriteria(statistic.k()).iterator();

            while (iterator.hasNext()) {
                ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();

                this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective).addScore(i);
            }

            if (this.bT.e()) {
                this.bT.a(this);
            }

        }
    }

    public void a(Statistic statistic) {
        if (statistic != null) {
            this.bT.setStatistic(this, statistic, 0);
            Iterator iterator = this.getScoreboard().getObjectivesForCriteria(statistic.k()).iterator();

            while (iterator.hasNext()) {
                ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();

                this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective).setScore(0);
            }

            if (this.bT.e()) {
                this.bT.a(this);
            }

        }
    }

    public void t() {
        this.az();
        if (this.sleeping) {
            this.a(true, false, false);
        }

    }

    public void triggerHealthUpdate() {
        this.lastHealthSent = -1.0E8F;
    }

    public void b(IChatBaseComponent ichatbasecomponent) {
        this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
    }

    protected void v() {
        if (this.bm != null && this.cs()) {
            this.playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 9));
            super.v();
        }

    }

    public void copyTo(EntityHuman entityhuman, boolean flag) {
        super.copyTo(entityhuman, flag);
        this.lastSentExp = -1;
        this.lastHealthSent = -1.0F;
        this.cb = -1;
        this.removeQueue.addAll(((EntityPlayer) entityhuman).removeQueue);
    }

    protected void a(MobEffect mobeffect) {
        super.a(mobeffect);
        this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.getId(), mobeffect));
    }

    protected void a(MobEffect mobeffect, boolean flag) {
        super.a(mobeffect, flag);
        this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.getId(), mobeffect));
    }

    protected void b(MobEffect mobeffect) {
        super.b(mobeffect);
        this.playerConnection.sendPacket(new PacketPlayOutRemoveEntityEffect(this.getId(), mobeffect.getMobEffect()));
    }

    public void enderTeleportTo(double d0, double d1, double d2) {
        this.playerConnection.a(d0, d1, d2, this.yaw, this.pitch);
    }

    public void a(Entity entity) {
        this.x().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 4));
    }

    public void b(Entity entity) {
        this.x().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 5));
    }

    public void updateAbilities() {
        if (this.playerConnection != null) {
            this.playerConnection.sendPacket(new PacketPlayOutAbilities(this.abilities));
            this.F();
        }
    }

    public WorldServer x() {
        return (WorldServer) this.world;
    }

    public void a(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
        this.playerInteractManager.setGameMode(worldsettings_enumgamemode);
        this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(3, (float) worldsettings_enumgamemode.getId()));
        if (worldsettings_enumgamemode == WorldSettings.EnumGamemode.SPECTATOR) {
            this.stopRiding();
        } else {
            this.setSpectatorTarget(this);
        }

        this.updateAbilities();
        this.cq();
    }

    public boolean isSpectator() {
        return this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR;
    }

    public boolean l_() {
        return this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE;
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
    }

    public boolean a(int i, String s) {
        if ("seed".equals(s) && !this.server.aa()) {
            return true;
        } else if (!"tell".equals(s) && !"help".equals(s) && !"me".equals(s) && !"trigger".equals(s)) {
            if (this.server.getPlayerList().isOp(this.getProfile())) {
                OpListEntry oplistentry = (OpListEntry) this.server.getPlayerList().getOPs().get(this.getProfile());

                return oplistentry != null ? oplistentry.a() >= i : this.server.q() >= i;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public String A() {
        String s = this.playerConnection.networkManager.getSocketAddress().toString();

        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }

    public void a(PacketPlayInSettings packetplayinsettings) {
        this.locale = packetplayinsettings.a();
        this.cf = packetplayinsettings.c();
        this.cg = packetplayinsettings.d();
        this.getDataWatcher().set(EntityPlayer.bp, Byte.valueOf((byte) packetplayinsettings.e()));
        this.getDataWatcher().set(EntityPlayer.bq, Byte.valueOf((byte) (packetplayinsettings.f() == EnumMainHand.LEFT ? 0 : 1)));
    }

    public EntityHuman.EnumChatVisibility getChatFlags() {
        return this.cf;
    }

    public void setResourcePack(String s, String s1) {
        this.playerConnection.sendPacket(new PacketPlayOutResourcePackSend(s, s1));
    }

    public BlockPosition getChunkCoordinates() {
        return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
    }

    public void resetIdleTimer() {
        this.ch = MinecraftServer.av();
    }

    public ServerStatisticManager getStatisticManager() {
        return this.bT;
    }

    public void c(Entity entity) {
        if (entity instanceof EntityHuman) {
            this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { entity.getId()}));
        } else {
            this.removeQueue.add(Integer.valueOf(entity.getId()));
        }

    }

    public void d(Entity entity) {
        this.removeQueue.remove(Integer.valueOf(entity.getId()));
    }

    protected void F() {
        if (this.isSpectator()) {
            this.bM();
            this.setInvisible(true);
        } else {
            super.F();
        }

        this.x().getTracker().a(this);
    }

    public Entity getSpecatorTarget() {
        return (Entity) (this.ci == null ? this : this.ci);
    }

    public void setSpectatorTarget(Entity entity) {
        Entity entity1 = this.getSpecatorTarget();

        this.ci = (Entity) (entity == null ? this : entity);
        if (entity1 != this.ci) {
            this.playerConnection.sendPacket(new PacketPlayOutCamera(this.ci));
            this.enderTeleportTo(this.ci.locX, this.ci.locY, this.ci.locZ);
        }

    }

    protected void H() {
        if (this.portalCooldown > 0 && !this.cj) {
            --this.portalCooldown;
        }

    }

    public void attack(Entity entity) {
        if (this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR) {
            this.setSpectatorTarget(entity);
        } else {
            super.attack(entity);
        }

    }

    public long I() {
        return this.ch;
    }

    public IChatBaseComponent getPlayerListName() {
        return null;
    }

    public void a(EnumHand enumhand) {
        super.a(enumhand);
        this.cZ();
    }

    public boolean K() {
        return this.cj;
    }

    public void L() {
        this.cj = false;
    }

    public void M() {
        this.setFlag(7, true);
    }

    public void N() {
        this.setFlag(7, true);
        this.setFlag(7, false);
    }
}

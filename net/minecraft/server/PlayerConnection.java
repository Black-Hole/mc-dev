package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerConnection implements PacketListenerPlayIn, ITickable {

    private static final Logger LOGGER = LogManager.getLogger();
    public final NetworkManager networkManager;
    private final MinecraftServer minecraftServer;
    public EntityPlayer player;
    private int e;
    private int f;
    private long g;
    private long h;
    private int chatThrottle;
    private int j;
    private final IntHashMap<Short> k = new IntHashMap();
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;
    private double q;
    private Entity r;
    private double s;
    private double t;
    private double u;
    private double v;
    private double w;
    private double x;
    private Vec3D teleportPos;
    private int teleportAwait;
    private int A;
    private boolean B;
    private int C;
    private boolean D;
    private int E;
    private int F;
    private int G;

    public PlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        this.minecraftServer = minecraftserver;
        this.networkManager = networkmanager;
        networkmanager.setPacketListener(this);
        this.player = entityplayer;
        entityplayer.playerConnection = this;
    }

    public void c() {
        this.d();
        this.player.k_();
        this.player.setLocation(this.l, this.m, this.n, this.player.yaw, this.player.pitch);
        ++this.e;
        this.G = this.F;
        if (this.B) {
            if (++this.C > 80) {
                PlayerConnection.LOGGER.warn(this.player.getName() + " was kicked for floating too long!");
                this.disconnect("Flying is not enabled on this server");
                return;
            }
        } else {
            this.B = false;
            this.C = 0;
        }

        this.r = this.player.getVehicle();
        if (this.r != this.player && this.r.bt() == this.player) {
            this.s = this.r.locX;
            this.t = this.r.locY;
            this.u = this.r.locZ;
            this.v = this.r.locX;
            this.w = this.r.locY;
            this.x = this.r.locZ;
            if (this.D && this.player.getVehicle().bt() == this.player) {
                if (++this.E > 80) {
                    PlayerConnection.LOGGER.warn(this.player.getName() + " was kicked for floating a vehicle too long!");
                    this.disconnect("Flying is not enabled on this server");
                    return;
                }
            } else {
                this.D = false;
                this.E = 0;
            }
        } else {
            this.r = null;
            this.D = false;
            this.E = 0;
        }

        this.minecraftServer.methodProfiler.a("keepAlive");
        if ((long) this.e - this.h > 40L) {
            this.h = (long) this.e;
            this.g = this.e();
            this.f = (int) this.g;
            this.sendPacket(new PacketPlayOutKeepAlive(this.f));
        }

        this.minecraftServer.methodProfiler.b();
        if (this.chatThrottle > 0) {
            --this.chatThrottle;
        }

        if (this.j > 0) {
            --this.j;
        }

        if (this.player.I() > 0L && this.minecraftServer.getIdleTimeout() > 0 && MinecraftServer.av() - this.player.I() > (long) (this.minecraftServer.getIdleTimeout() * 1000 * 60)) {
            this.disconnect("You have been idle for too long!");
        }

    }

    private void d() {
        this.l = this.player.locX;
        this.m = this.player.locY;
        this.n = this.player.locZ;
        this.o = this.player.locX;
        this.p = this.player.locY;
        this.q = this.player.locZ;
    }

    public NetworkManager a() {
        return this.networkManager;
    }

    public void disconnect(String s) {
        final ChatComponentText chatcomponenttext = new ChatComponentText(s);

        this.networkManager.sendPacket(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                PlayerConnection.this.networkManager.close(chatcomponenttext);
            }
        }, new GenericFutureListener[0]);
        this.networkManager.stopReading();
        Futures.getUnchecked(this.minecraftServer.postToMainThread(new Runnable() {
            public void run() {
                PlayerConnection.this.networkManager.handleDisconnection();
            }
        }));
    }

    public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
        PlayerConnectionUtils.ensureMainThread(packetplayinsteervehicle, this, this.player.x());
        this.player.a(packetplayinsteervehicle.a(), packetplayinsteervehicle.b(), packetplayinsteervehicle.c(), packetplayinsteervehicle.d());
    }

    private static boolean b(PacketPlayInFlying packetplayinflying) {
        return Doubles.isFinite(packetplayinflying.a(0.0D)) && Doubles.isFinite(packetplayinflying.b(0.0D)) && Doubles.isFinite(packetplayinflying.c(0.0D)) && Floats.isFinite(packetplayinflying.b(0.0F)) && Floats.isFinite(packetplayinflying.a(0.0F)) ? false : Math.abs(packetplayinflying.a(0.0D)) <= 3.0E7D && Math.abs(packetplayinflying.a(0.0D)) <= 3.0E7D;
    }

    private static boolean b(PacketPlayInVehicleMove packetplayinvehiclemove) {
        return !Doubles.isFinite(packetplayinvehiclemove.getX()) || !Doubles.isFinite(packetplayinvehiclemove.getY()) || !Doubles.isFinite(packetplayinvehiclemove.getZ()) || !Floats.isFinite(packetplayinvehiclemove.getPitch()) || !Floats.isFinite(packetplayinvehiclemove.getYaw());
    }

    public void a(PacketPlayInVehicleMove packetplayinvehiclemove) {
        PlayerConnectionUtils.ensureMainThread(packetplayinvehiclemove, this, this.player.x());
        if (b(packetplayinvehiclemove)) {
            this.disconnect("Invalid move vehicle packet received");
        } else {
            Entity entity = this.player.getVehicle();

            if (entity != this.player && entity.bt() == this.player && entity == this.r) {
                WorldServer worldserver = this.player.x();
                double d0 = entity.locX;
                double d1 = entity.locY;
                double d2 = entity.locZ;
                double d3 = packetplayinvehiclemove.getX();
                double d4 = packetplayinvehiclemove.getY();
                double d5 = packetplayinvehiclemove.getZ();
                float f = packetplayinvehiclemove.getYaw();
                float f1 = packetplayinvehiclemove.getPitch();
                double d6 = d3 - this.s;
                double d7 = d4 - this.t;
                double d8 = d5 - this.u;
                double d9 = entity.motX * entity.motX + entity.motY * entity.motY + entity.motZ * entity.motZ;
                double d10 = d6 * d6 + d7 * d7 + d8 * d8;

                if (d10 - d9 > 100.0D && (!this.minecraftServer.R() || !this.minecraftServer.Q().equals(entity.getName()))) {
                    PlayerConnection.LOGGER.warn(entity.getName() + " (vehicle of " + this.player.getName() + ") moved too quickly! " + d6 + "," + d7 + "," + d8);
                    this.networkManager.sendPacket(new PacketPlayOutVehicleMove(entity));
                    return;
                }

                boolean flag = worldserver.getCubes(entity, entity.getBoundingBox().shrink(0.0625D)).isEmpty();

                d6 = d3 - this.v;
                d7 = d4 - this.w - 1.0E-6D;
                d8 = d5 - this.x;
                entity.move(d6, d7, d8);
                double d11 = d7;

                d6 = d3 - entity.locX;
                d7 = d4 - entity.locY;
                if (d7 > -0.5D || d7 < 0.5D) {
                    d7 = 0.0D;
                }

                d8 = d5 - entity.locZ;
                d10 = d6 * d6 + d7 * d7 + d8 * d8;
                boolean flag1 = false;

                if (d10 > 0.0625D) {
                    flag1 = true;
                    PlayerConnection.LOGGER.warn(entity.getName() + " moved wrongly!");
                }

                entity.setLocation(d3, d4, d5, f, f1);
                boolean flag2 = worldserver.getCubes(entity, entity.getBoundingBox().shrink(0.0625D)).isEmpty();

                if (flag && (flag1 || !flag2)) {
                    entity.setLocation(d0, d1, d2, f, f1);
                    this.networkManager.sendPacket(new PacketPlayOutVehicleMove(entity));
                    return;
                }

                this.minecraftServer.getPlayerList().d(this.player);
                this.player.checkMovement(this.player.locX - d0, this.player.locY - d1, this.player.locZ - d2);
                this.D = d11 >= -0.03125D && !this.minecraftServer.getAllowFlight() && !worldserver.d(entity.getBoundingBox().g(0.0625D).a(0.0D, -0.55D, 0.0D));
                this.v = entity.locX;
                this.w = entity.locY;
                this.x = entity.locZ;
            }

        }
    }

    public void a(PacketPlayInTeleportAccept packetplayinteleportaccept) {
        PlayerConnectionUtils.ensureMainThread(packetplayinteleportaccept, this, this.player.x());
        if (packetplayinteleportaccept.a() == this.teleportAwait) {
            this.player.setLocation(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z, this.player.yaw, this.player.pitch);
            if (this.player.K()) {
                this.o = this.teleportPos.x;
                this.p = this.teleportPos.y;
                this.q = this.teleportPos.z;
                this.player.L();
            }

            this.teleportPos = null;
        }

    }

    public void a(PacketPlayInFlying packetplayinflying) {
        PlayerConnectionUtils.ensureMainThread(packetplayinflying, this, this.player.x());
        if (b(packetplayinflying)) {
            this.disconnect("Invalid move player packet received");
        } else {
            WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);

            if (!this.player.viewingCredits) {
                if (this.e == 0) {
                    this.d();
                }

                if (this.teleportPos != null) {
                    if (this.e - this.A > 20) {
                        this.A = this.e;
                        this.a(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z, this.player.yaw, this.player.pitch);
                    }

                } else {
                    this.A = this.e;
                    if (this.player.isPassenger()) {
                        this.player.setLocation(this.player.locX, this.player.locY, this.player.locZ, packetplayinflying.a(this.player.yaw), packetplayinflying.b(this.player.pitch));
                        this.minecraftServer.getPlayerList().d(this.player);
                    } else {
                        double d0 = this.player.locX;
                        double d1 = this.player.locY;
                        double d2 = this.player.locZ;
                        double d3 = this.player.locY;
                        double d4 = packetplayinflying.a(this.player.locX);
                        double d5 = packetplayinflying.b(this.player.locY);
                        double d6 = packetplayinflying.c(this.player.locZ);
                        float f = packetplayinflying.a(this.player.yaw);
                        float f1 = packetplayinflying.b(this.player.pitch);
                        double d7 = d4 - this.l;
                        double d8 = d5 - this.m;
                        double d9 = d6 - this.n;
                        double d10 = this.player.motX * this.player.motX + this.player.motY * this.player.motY + this.player.motZ * this.player.motZ;
                        double d11 = d7 * d7 + d8 * d8 + d9 * d9;

                        ++this.F;
                        int i = this.F - this.G;

                        if (i > 5) {
                            PlayerConnection.LOGGER.debug(this.player.getName() + " is sending move packets too frequently (" + i + " packets since last tick)");
                            i = 1;
                        }

                        if (!this.player.K() && (!this.player.x().getGameRules().getBoolean("disableElytraMovementCheck") || !this.player.cB())) {
                            float f2 = this.player.cB() ? 300.0F : 100.0F;

                            if (d11 - d10 > (double) (f2 * (float) i) && (!this.minecraftServer.R() || !this.minecraftServer.Q().equals(this.player.getName()))) {
                                PlayerConnection.LOGGER.warn(this.player.getName() + " moved too quickly! " + d7 + "," + d8 + "," + d9);
                                this.a(this.player.locX, this.player.locY, this.player.locZ, this.player.yaw, this.player.pitch);
                                return;
                            }
                        }

                        boolean flag = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(0.0625D)).isEmpty();

                        d7 = d4 - this.o;
                        d8 = d5 - this.p;
                        d9 = d6 - this.q;
                        if (this.player.onGround && !packetplayinflying.a() && d8 > 0.0D) {
                            this.player.ch();
                        }

                        this.player.move(d7, d8, d9);
                        this.player.onGround = packetplayinflying.a();
                        double d12 = d8;

                        d7 = d4 - this.player.locX;
                        d8 = d5 - this.player.locY;
                        if (d8 > -0.5D || d8 < 0.5D) {
                            d8 = 0.0D;
                        }

                        d9 = d6 - this.player.locZ;
                        d11 = d7 * d7 + d8 * d8 + d9 * d9;
                        boolean flag1 = false;

                        if (!this.player.K() && d11 > 0.0625D && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative() && this.player.playerInteractManager.getGameMode() != WorldSettings.EnumGamemode.SPECTATOR) {
                            flag1 = true;
                            PlayerConnection.LOGGER.warn(this.player.getName() + " moved wrongly!");
                        }

                        this.player.setLocation(d4, d5, d6, f, f1);
                        this.player.checkMovement(this.player.locX - d0, this.player.locY - d1, this.player.locZ - d2);
                        if (!this.player.noclip && !this.player.isSleeping()) {
                            boolean flag2 = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(0.0625D)).isEmpty();

                            if (flag && (flag1 || !flag2)) {
                                this.a(d0, d1, d2, f, f1);
                                return;
                            }
                        }

                        this.B = d12 >= -0.03125D;
                        this.B &= !this.minecraftServer.getAllowFlight() && !this.player.abilities.canFly;
                        this.B &= !this.player.hasEffect(MobEffects.LEVITATION) && !this.player.cB() && !worldserver.d(this.player.getBoundingBox().g(0.0625D).a(0.0D, -0.55D, 0.0D));
                        this.player.onGround = packetplayinflying.a();
                        this.minecraftServer.getPlayerList().d(this.player);
                        this.player.a(this.player.locY - d3, packetplayinflying.a());
                        this.o = this.player.locX;
                        this.p = this.player.locY;
                        this.q = this.player.locZ;
                    }
                }
            }
        }
    }

    public void a(double d0, double d1, double d2, float f, float f1) {
        this.a(d0, d1, d2, f, f1, Collections.emptySet());
    }

    public void a(double d0, double d1, double d2, float f, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set) {
        double d3 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X) ? this.player.locX : 0.0D;
        double d4 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y) ? this.player.locY : 0.0D;
        double d5 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z) ? this.player.locZ : 0.0D;

        this.teleportPos = new Vec3D(d0 + d3, d1 + d4, d2 + d5);
        float f2 = f;
        float f3 = f1;

        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
            f2 = f + this.player.yaw;
        }

        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
            f3 = f1 + this.player.pitch;
        }

        if (++this.teleportAwait == Integer.MAX_VALUE) {
            this.teleportAwait = 0;
        }

        this.A = this.e;
        this.player.setLocation(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z, f2, f3);
        this.player.playerConnection.sendPacket(new PacketPlayOutPosition(d0, d1, d2, f, f1, set, this.teleportAwait));
    }

    public void a(PacketPlayInBlockDig packetplayinblockdig) {
        PlayerConnectionUtils.ensureMainThread(packetplayinblockdig, this, this.player.x());
        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        BlockPosition blockposition = packetplayinblockdig.a();

        this.player.resetIdleTimer();
        ItemStack itemstack;

        switch (PlayerConnection.SyntheticClass_1.a[packetplayinblockdig.c().ordinal()]) {
        case 1:
            if (!this.player.isSpectator()) {
                itemstack = this.player.b(EnumHand.OFF_HAND);
                this.player.a(EnumHand.OFF_HAND, this.player.b(EnumHand.MAIN_HAND));
                this.player.a(EnumHand.MAIN_HAND, itemstack);
            }

            return;

        case 2:
            if (!this.player.isSpectator()) {
                this.player.a(false);
            }

            return;

        case 3:
            if (!this.player.isSpectator()) {
                this.player.a(true);
            }

            return;

        case 4:
            this.player.clearActiveItem();
            itemstack = this.player.getItemInMainHand();
            if (itemstack != null && itemstack.count == 0) {
                this.player.a(EnumHand.MAIN_HAND, (ItemStack) null);
            }

            return;

        case 5:
        case 6:
        case 7:
            double d0 = this.player.locX - ((double) blockposition.getX() + 0.5D);
            double d1 = this.player.locY - ((double) blockposition.getY() + 0.5D) + 1.5D;
            double d2 = this.player.locZ - ((double) blockposition.getZ() + 0.5D);
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d3 > 36.0D) {
                return;
            } else if (blockposition.getY() >= this.minecraftServer.getMaxBuildHeight()) {
                return;
            } else {
                if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                    if (!this.minecraftServer.a(worldserver, blockposition, this.player) && worldserver.getWorldBorder().a(blockposition)) {
                        this.player.playerInteractManager.a(blockposition, packetplayinblockdig.b());
                    } else {
                        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
                    }
                } else {
                    if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                        this.player.playerInteractManager.a(blockposition);
                    } else if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                        this.player.playerInteractManager.e();
                    }

                    if (worldserver.getType(blockposition).getMaterial() != Material.AIR) {
                        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
                    }
                }

                return;
            }

        default:
            throw new IllegalArgumentException("Invalid player action");
        }
    }

    public void a(PacketPlayInUseItem packetplayinuseitem) {
        PlayerConnectionUtils.ensureMainThread(packetplayinuseitem, this, this.player.x());
        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        EnumHand enumhand = packetplayinuseitem.c();
        ItemStack itemstack = this.player.b(enumhand);
        BlockPosition blockposition = packetplayinuseitem.a();
        EnumDirection enumdirection = packetplayinuseitem.b();

        this.player.resetIdleTimer();
        if (blockposition.getY() >= this.minecraftServer.getMaxBuildHeight() - 1 && (enumdirection == EnumDirection.UP || blockposition.getY() >= this.minecraftServer.getMaxBuildHeight())) {
            ChatMessage chatmessage = new ChatMessage("build.tooHigh", new Object[] { Integer.valueOf(this.minecraftServer.getMaxBuildHeight())});

            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            this.player.playerConnection.sendPacket(new PacketPlayOutChat(chatmessage));
        } else if (this.teleportPos == null && this.player.e((double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D) < 64.0D && !this.minecraftServer.a(worldserver, blockposition, this.player) && worldserver.getWorldBorder().a(blockposition)) {
            this.player.playerInteractManager.a(this.player, worldserver, itemstack, enumhand, blockposition, enumdirection, packetplayinuseitem.d(), packetplayinuseitem.e(), packetplayinuseitem.f());
        }

        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition.shift(enumdirection)));
        itemstack = this.player.b(enumhand);
        if (itemstack != null && itemstack.count == 0) {
            this.player.a(enumhand, (ItemStack) null);
            itemstack = null;
        }

    }

    public void a(PacketPlayInBlockPlace packetplayinblockplace) {
        PlayerConnectionUtils.ensureMainThread(packetplayinblockplace, this, this.player.x());
        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        EnumHand enumhand = packetplayinblockplace.a();
        ItemStack itemstack = this.player.b(enumhand);

        this.player.resetIdleTimer();
        if (itemstack != null) {
            this.player.playerInteractManager.a(this.player, worldserver, itemstack, enumhand);
            itemstack = this.player.b(enumhand);
            if (itemstack != null && itemstack.count == 0) {
                this.player.a(enumhand, (ItemStack) null);
                itemstack = null;
            }

        }
    }

    public void a(PacketPlayInSpectate packetplayinspectate) {
        PlayerConnectionUtils.ensureMainThread(packetplayinspectate, this, this.player.x());
        if (this.player.isSpectator()) {
            Entity entity = null;
            WorldServer[] aworldserver = this.minecraftServer.worldServer;
            int i = aworldserver.length;

            for (int j = 0; j < i; ++j) {
                WorldServer worldserver = aworldserver[j];

                if (worldserver != null) {
                    entity = packetplayinspectate.a(worldserver);
                    if (entity != null) {
                        break;
                    }
                }
            }

            if (entity != null) {
                this.player.setSpectatorTarget(this.player);
                this.player.stopRiding();
                if (entity.world != this.player.world) {
                    WorldServer worldserver1 = this.player.x();
                    WorldServer worldserver2 = (WorldServer) entity.world;

                    this.player.dimension = entity.dimension;
                    this.sendPacket(new PacketPlayOutRespawn(this.player.dimension, worldserver1.getDifficulty(), worldserver1.getWorldData().getType(), this.player.playerInteractManager.getGameMode()));
                    this.minecraftServer.getPlayerList().f(this.player);
                    worldserver1.removeEntity(this.player);
                    this.player.dead = false;
                    this.player.setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
                    if (this.player.isAlive()) {
                        worldserver1.entityJoinedWorld(this.player, false);
                        worldserver2.addEntity(this.player);
                        worldserver2.entityJoinedWorld(this.player, false);
                    }

                    this.player.spawnIn(worldserver2);
                    this.minecraftServer.getPlayerList().a(this.player, worldserver1);
                    this.player.enderTeleportTo(entity.locX, entity.locY, entity.locZ);
                    this.player.playerInteractManager.a(worldserver2);
                    this.minecraftServer.getPlayerList().b(this.player, worldserver2);
                    this.minecraftServer.getPlayerList().updateClient(this.player);
                } else {
                    this.player.enderTeleportTo(entity.locX, entity.locY, entity.locZ);
                }
            }
        }

    }

    public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {}

    public void a(PacketPlayInBoatMove packetplayinboatmove) {
        PlayerConnectionUtils.ensureMainThread(packetplayinboatmove, this, this.player.x());
        Entity entity = this.player.by();

        if (entity instanceof EntityBoat) {
            ((EntityBoat) entity).a(packetplayinboatmove.a(), packetplayinboatmove.b());
        }

    }

    public void a(IChatBaseComponent ichatbasecomponent) {
        PlayerConnection.LOGGER.info(this.player.getName() + " lost connection: " + ichatbasecomponent);
        this.minecraftServer.aC();
        ChatMessage chatmessage = new ChatMessage("multiplayer.player.left", new Object[] { this.player.getScoreboardDisplayName()});

        chatmessage.getChatModifier().setColor(EnumChatFormat.YELLOW);
        this.minecraftServer.getPlayerList().sendMessage(chatmessage);
        this.player.t();
        this.minecraftServer.getPlayerList().disconnect(this.player);
        if (this.minecraftServer.R() && this.player.getName().equals(this.minecraftServer.Q())) {
            PlayerConnection.LOGGER.info("Stopping singleplayer server as player logged out");
            this.minecraftServer.safeShutdown();
        }

    }

    public void sendPacket(final Packet<?> packet) {
        if (packet instanceof PacketPlayOutChat) {
            PacketPlayOutChat packetplayoutchat = (PacketPlayOutChat) packet;
            EntityHuman.EnumChatVisibility entityhuman_enumchatvisibility = this.player.getChatFlags();

            if (entityhuman_enumchatvisibility == EntityHuman.EnumChatVisibility.HIDDEN) {
                return;
            }

            if (entityhuman_enumchatvisibility == EntityHuman.EnumChatVisibility.SYSTEM && !packetplayoutchat.b()) {
                return;
            }
        }

        try {
            this.networkManager.sendPacket(packet);
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Sending packet");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");

            crashreportsystemdetails.a("Packet class", new Callable() {
                public String a() throws Exception {
                    return packet.getClass().getCanonicalName();
                }

                public Object call() throws Exception {
                    return this.a();
                }
            });
            throw new ReportedException(crashreport);
        }
    }

    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
        PlayerConnectionUtils.ensureMainThread(packetplayinhelditemslot, this, this.player.x());
        if (packetplayinhelditemslot.a() >= 0 && packetplayinhelditemslot.a() < PlayerInventory.getHotbarSize()) {
            this.player.inventory.itemInHandIndex = packetplayinhelditemslot.a();
            this.player.resetIdleTimer();
        } else {
            PlayerConnection.LOGGER.warn(this.player.getName() + " tried to set an invalid carried item");
        }
    }

    public void a(PacketPlayInChat packetplayinchat) {
        PlayerConnectionUtils.ensureMainThread(packetplayinchat, this, this.player.x());
        if (this.player.getChatFlags() == EntityHuman.EnumChatVisibility.HIDDEN) {
            ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);

            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            this.sendPacket(new PacketPlayOutChat(chatmessage));
        } else {
            this.player.resetIdleTimer();
            String s = packetplayinchat.a();

            s = StringUtils.normalizeSpace(s);

            for (int i = 0; i < s.length(); ++i) {
                if (!SharedConstants.isAllowedChatCharacter(s.charAt(i))) {
                    this.disconnect("Illegal characters in chat");
                    return;
                }
            }

            if (s.startsWith("/")) {
                this.handleCommand(s);
            } else {
                ChatMessage chatmessage1 = new ChatMessage("chat.type.text", new Object[] { this.player.getScoreboardDisplayName(), s});

                this.minecraftServer.getPlayerList().sendMessage(chatmessage1, false);
            }

            this.chatThrottle += 20;
            if (this.chatThrottle > 200 && !this.minecraftServer.getPlayerList().isOp(this.player.getProfile())) {
                this.disconnect("disconnect.spam");
            }

        }
    }

    private void handleCommand(String s) {
        this.minecraftServer.getCommandHandler().a(this.player, s);
    }

    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
        PlayerConnectionUtils.ensureMainThread(packetplayinarmanimation, this, this.player.x());
        this.player.resetIdleTimer();
        this.player.a(packetplayinarmanimation.a());
    }

    public void a(PacketPlayInEntityAction packetplayinentityaction) {
        PlayerConnectionUtils.ensureMainThread(packetplayinentityaction, this, this.player.x());
        this.player.resetIdleTimer();
        IJumpable ijumpable;

        switch (PlayerConnection.SyntheticClass_1.b[packetplayinentityaction.b().ordinal()]) {
        case 1:
            this.player.setSneaking(true);
            break;

        case 2:
            this.player.setSneaking(false);
            break;

        case 3:
            this.player.setSprinting(true);
            break;

        case 4:
            this.player.setSprinting(false);
            break;

        case 5:
            this.player.a(false, true, true);
            this.teleportPos = new Vec3D(this.player.locX, this.player.locY, this.player.locZ);
            break;

        case 6:
            if (this.player.by() instanceof IJumpable) {
                ijumpable = (IJumpable) this.player.by();
                int i = packetplayinentityaction.c();

                if (ijumpable.b() && i > 0) {
                    ijumpable.b(i);
                }
            }
            break;

        case 7:
            if (this.player.by() instanceof IJumpable) {
                ijumpable = (IJumpable) this.player.by();
                ijumpable.r_();
            }
            break;

        case 8:
            if (this.player.by() instanceof EntityHorse) {
                ((EntityHorse) this.player.by()).f((EntityHuman) this.player);
            }
            break;

        case 9:
            if (!this.player.onGround && this.player.motY < 0.0D && !this.player.cB() && !this.player.isInWater()) {
                ItemStack itemstack = this.player.getEquipment(EnumItemSlot.CHEST);

                if (itemstack != null && itemstack.getItem() == Items.cR && ItemElytra.d(itemstack)) {
                    this.player.M();
                }
            } else {
                this.player.N();
            }
            break;

        default:
            throw new IllegalArgumentException("Invalid client command!");
        }

    }

    public void a(PacketPlayInUseEntity packetplayinuseentity) {
        PlayerConnectionUtils.ensureMainThread(packetplayinuseentity, this, this.player.x());
        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        Entity entity = packetplayinuseentity.a((World) worldserver);

        this.player.resetIdleTimer();
        if (entity != null) {
            boolean flag = this.player.hasLineOfSight(entity);
            double d0 = 36.0D;

            if (!flag) {
                d0 = 9.0D;
            }

            if (this.player.h(entity) < d0) {
                EnumHand enumhand;
                ItemStack itemstack;

                if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
                    enumhand = packetplayinuseentity.b();
                    itemstack = this.player.b(enumhand);
                    this.player.a(entity, itemstack, enumhand);
                } else if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
                    enumhand = packetplayinuseentity.b();
                    itemstack = this.player.b(enumhand);
                    entity.a((EntityHuman) this.player, packetplayinuseentity.c(), itemstack, enumhand);
                } else if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                    if (entity instanceof EntityItem || entity instanceof EntityExperienceOrb || entity instanceof EntityArrow || entity == this.player) {
                        this.disconnect("Attempting to attack an invalid entity");
                        this.minecraftServer.warning("Player " + this.player.getName() + " tried to attack an invalid entity");
                        return;
                    }

                    this.player.attack(entity);
                }
            }
        }

    }

    public void a(PacketPlayInClientCommand packetplayinclientcommand) {
        PlayerConnectionUtils.ensureMainThread(packetplayinclientcommand, this, this.player.x());
        this.player.resetIdleTimer();
        PacketPlayInClientCommand.EnumClientCommand packetplayinclientcommand_enumclientcommand = packetplayinclientcommand.a();

        switch (PlayerConnection.SyntheticClass_1.c[packetplayinclientcommand_enumclientcommand.ordinal()]) {
        case 1:
            if (this.player.viewingCredits) {
                this.player.viewingCredits = false;
                this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, true);
            } else {
                if (this.player.getHealth() > 0.0F) {
                    return;
                }

                this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
                if (this.minecraftServer.isHardcore()) {
                    this.player.a(WorldSettings.EnumGamemode.SPECTATOR);
                    this.player.x().getGameRules().set("spectatorsGenerateChunks", "false");
                }
            }
            break;

        case 2:
            this.player.getStatisticManager().a(this.player);
            break;

        case 3:
            this.player.b((Statistic) AchievementList.f);
        }

    }

    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
        PlayerConnectionUtils.ensureMainThread(packetplayinclosewindow, this, this.player.x());
        this.player.s();
    }

    public void a(PacketPlayInWindowClick packetplayinwindowclick) {
        PlayerConnectionUtils.ensureMainThread(packetplayinwindowclick, this, this.player.x());
        this.player.resetIdleTimer();
        if (this.player.activeContainer.windowId == packetplayinwindowclick.a() && this.player.activeContainer.c(this.player)) {
            if (this.player.isSpectator()) {
                ArrayList arraylist = Lists.newArrayList();

                for (int i = 0; i < this.player.activeContainer.c.size(); ++i) {
                    arraylist.add(((Slot) this.player.activeContainer.c.get(i)).getItem());
                }

                this.player.a(this.player.activeContainer, (List) arraylist);
            } else {
                ItemStack itemstack = this.player.activeContainer.a(packetplayinwindowclick.b(), packetplayinwindowclick.c(), packetplayinwindowclick.f(), this.player);

                if (ItemStack.matches(packetplayinwindowclick.e(), itemstack)) {
                    this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.a(), packetplayinwindowclick.d(), true));
                    this.player.f = true;
                    this.player.activeContainer.b();
                    this.player.broadcastCarriedItem();
                    this.player.f = false;
                } else {
                    this.k.a(this.player.activeContainer.windowId, Short.valueOf(packetplayinwindowclick.d()));
                    this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.a(), packetplayinwindowclick.d(), false));
                    this.player.activeContainer.a(this.player, false);
                    ArrayList arraylist1 = Lists.newArrayList();

                    for (int j = 0; j < this.player.activeContainer.c.size(); ++j) {
                        ItemStack itemstack1 = ((Slot) this.player.activeContainer.c.get(j)).getItem();
                        ItemStack itemstack2 = itemstack1 != null && itemstack1.count > 0 ? itemstack1 : null;

                        arraylist1.add(itemstack2);
                    }

                    this.player.a(this.player.activeContainer, (List) arraylist1);
                }
            }
        }

    }

    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
        PlayerConnectionUtils.ensureMainThread(packetplayinenchantitem, this, this.player.x());
        this.player.resetIdleTimer();
        if (this.player.activeContainer.windowId == packetplayinenchantitem.a() && this.player.activeContainer.c(this.player) && !this.player.isSpectator()) {
            this.player.activeContainer.a(this.player, packetplayinenchantitem.b());
            this.player.activeContainer.b();
        }

    }

    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
        PlayerConnectionUtils.ensureMainThread(packetplayinsetcreativeslot, this, this.player.x());
        if (this.player.playerInteractManager.isCreative()) {
            boolean flag = packetplayinsetcreativeslot.a() < 0;
            ItemStack itemstack = packetplayinsetcreativeslot.getItemStack();

            if (itemstack != null && itemstack.hasTag() && itemstack.getTag().hasKeyOfType("BlockEntityTag", 10)) {
                NBTTagCompound nbttagcompound = itemstack.getTag().getCompound("BlockEntityTag");

                if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
                    BlockPosition blockposition = new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z"));
                    TileEntity tileentity = this.player.world.getTileEntity(blockposition);

                    if (tileentity != null) {
                        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                        tileentity.save(nbttagcompound1);
                        nbttagcompound1.remove("x");
                        nbttagcompound1.remove("y");
                        nbttagcompound1.remove("z");
                        itemstack.a("BlockEntityTag", (NBTBase) nbttagcompound1);
                    }
                }
            }

            boolean flag1 = packetplayinsetcreativeslot.a() >= 1 && packetplayinsetcreativeslot.a() <= 45;
            boolean flag2 = itemstack == null || itemstack.getItem() != null;
            boolean flag3 = itemstack == null || itemstack.getData() >= 0 && itemstack.count <= 64 && itemstack.count > 0;

            if (flag1 && flag2 && flag3) {
                if (itemstack == null) {
                    this.player.defaultContainer.setItem(packetplayinsetcreativeslot.a(), (ItemStack) null);
                } else {
                    this.player.defaultContainer.setItem(packetplayinsetcreativeslot.a(), itemstack);
                }

                this.player.defaultContainer.a(this.player, true);
            } else if (flag && flag2 && flag3 && this.j < 200) {
                this.j += 20;
                EntityItem entityitem = this.player.drop(itemstack, true);

                if (entityitem != null) {
                    entityitem.j();
                }
            }
        }

    }

    public void a(PacketPlayInTransaction packetplayintransaction) {
        PlayerConnectionUtils.ensureMainThread(packetplayintransaction, this, this.player.x());
        Short oshort = (Short) this.k.get(this.player.activeContainer.windowId);

        if (oshort != null && packetplayintransaction.b() == oshort.shortValue() && this.player.activeContainer.windowId == packetplayintransaction.a() && !this.player.activeContainer.c(this.player) && !this.player.isSpectator()) {
            this.player.activeContainer.a(this.player, true);
        }

    }

    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
        PlayerConnectionUtils.ensureMainThread(packetplayinupdatesign, this, this.player.x());
        this.player.resetIdleTimer();
        WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
        BlockPosition blockposition = packetplayinupdatesign.a();

        if (worldserver.isLoaded(blockposition)) {
            IBlockData iblockdata = worldserver.getType(blockposition);
            TileEntity tileentity = worldserver.getTileEntity(blockposition);

            if (!(tileentity instanceof TileEntitySign)) {
                return;
            }

            TileEntitySign tileentitysign = (TileEntitySign) tileentity;

            if (!tileentitysign.b() || tileentitysign.c() != this.player) {
                this.minecraftServer.warning("Player " + this.player.getName() + " just tried to change non-editable sign");
                return;
            }

            String[] astring = packetplayinupdatesign.b();

            for (int i = 0; i < astring.length; ++i) {
                tileentitysign.lines[i] = new ChatComponentText(EnumChatFormat.a(astring[i]));
            }

            tileentitysign.update();
            worldserver.notify(blockposition, iblockdata, iblockdata, 3);
        }

    }

    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
        if (packetplayinkeepalive.a() == this.f) {
            int i = (int) (this.e() - this.g);

            this.player.ping = (this.player.ping * 3 + i) / 4;
        }

    }

    private long e() {
        return System.nanoTime() / 1000000L;
    }

    public void a(PacketPlayInAbilities packetplayinabilities) {
        PlayerConnectionUtils.ensureMainThread(packetplayinabilities, this, this.player.x());
        this.player.abilities.isFlying = packetplayinabilities.isFlying() && this.player.abilities.canFly;
    }

    public void a(PacketPlayInTabComplete packetplayintabcomplete) {
        PlayerConnectionUtils.ensureMainThread(packetplayintabcomplete, this, this.player.x());
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.minecraftServer.tabCompleteCommand(this.player, packetplayintabcomplete.a(), packetplayintabcomplete.b(), packetplayintabcomplete.c()).iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            arraylist.add(s);
        }

        this.player.playerConnection.sendPacket(new PacketPlayOutTabComplete((String[]) arraylist.toArray(new String[arraylist.size()])));
    }

    public void a(PacketPlayInSettings packetplayinsettings) {
        PlayerConnectionUtils.ensureMainThread(packetplayinsettings, this, this.player.x());
        this.player.a(packetplayinsettings);
    }

    public void a(PacketPlayInCustomPayload packetplayincustompayload) {
        PlayerConnectionUtils.ensureMainThread(packetplayincustompayload, this, this.player.x());
        String s = packetplayincustompayload.a();
        PacketDataSerializer packetdataserializer;
        ItemStack itemstack;
        ItemStack itemstack1;

        if ("MC|BEdit".equals(s)) {
            packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(packetplayincustompayload.b()));

            try {
                itemstack = packetdataserializer.k();
                if (itemstack == null) {
                    return;
                }

                if (!ItemBookAndQuill.b(itemstack.getTag())) {
                    throw new IOException("Invalid book tag!");
                }

                itemstack1 = this.player.getItemInMainHand();
                if (itemstack1 != null) {
                    if (itemstack.getItem() == Items.WRITABLE_BOOK && itemstack.getItem() == itemstack1.getItem()) {
                        itemstack1.a("pages", (NBTBase) itemstack.getTag().getList("pages", 8));
                    }

                    return;
                }
            } catch (Exception exception) {
                PlayerConnection.LOGGER.error("Couldn\'t handle book info", exception);
                return;
            } finally {
                packetdataserializer.release();
            }

            return;
        } else {
            String s1;

            if ("MC|BSign".equals(s)) {
                packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(packetplayincustompayload.b()));

                try {
                    itemstack = packetdataserializer.k();
                    if (itemstack == null) {
                        return;
                    }

                    if (!ItemWrittenBook.b(itemstack.getTag())) {
                        throw new IOException("Invalid book tag!");
                    }

                    itemstack1 = this.player.getItemInMainHand();
                    if (itemstack1 != null) {
                        if (itemstack.getItem() == Items.WRITABLE_BOOK && itemstack1.getItem() == Items.WRITABLE_BOOK) {
                            itemstack1.a("author", (NBTBase) (new NBTTagString(this.player.getName())));
                            itemstack1.a("title", (NBTBase) (new NBTTagString(itemstack.getTag().getString("title"))));
                            NBTTagList nbttaglist = itemstack.getTag().getList("pages", 8);

                            for (int i = 0; i < nbttaglist.size(); ++i) {
                                s1 = nbttaglist.getString(i);
                                ChatComponentText chatcomponenttext = new ChatComponentText(s1);

                                s1 = IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) chatcomponenttext);
                                nbttaglist.a(i, new NBTTagString(s1));
                            }

                            itemstack1.a("pages", (NBTBase) nbttaglist);
                            itemstack1.setItem(Items.WRITTEN_BOOK);
                        }

                        return;
                    }
                } catch (Exception exception1) {
                    PlayerConnection.LOGGER.error("Couldn\'t sign book", exception1);
                    return;
                } finally {
                    packetdataserializer.release();
                }

                return;
            } else if ("MC|TrSel".equals(s)) {
                try {
                    int j = packetplayincustompayload.b().readInt();
                    Container container = this.player.activeContainer;

                    if (container instanceof ContainerMerchant) {
                        ((ContainerMerchant) container).d(j);
                    }
                } catch (Exception exception2) {
                    PlayerConnection.LOGGER.error("Couldn\'t select trade", exception2);
                }
            } else {
                TileEntity tileentity;

                if ("MC|AdvCmd".equals(s)) {
                    if (!this.minecraftServer.getEnableCommandBlock()) {
                        this.player.sendMessage(new ChatMessage("advMode.notEnabled", new Object[0]));
                        return;
                    }

                    if (!this.player.a(2, "") || !this.player.abilities.canInstantlyBuild) {
                        this.player.sendMessage(new ChatMessage("advMode.notAllowed", new Object[0]));
                        return;
                    }

                    packetdataserializer = packetplayincustompayload.b();

                    try {
                        byte b0 = packetdataserializer.readByte();
                        CommandBlockListenerAbstract commandblocklistenerabstract = null;

                        if (b0 == 0) {
                            tileentity = this.player.world.getTileEntity(new BlockPosition(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt()));
                            if (tileentity instanceof TileEntityCommand) {
                                commandblocklistenerabstract = ((TileEntityCommand) tileentity).getCommandBlock();
                            }
                        } else if (b0 == 1) {
                            Entity entity = this.player.world.getEntity(packetdataserializer.readInt());

                            if (entity instanceof EntityMinecartCommandBlock) {
                                commandblocklistenerabstract = ((EntityMinecartCommandBlock) entity).getCommandBlock();
                            }
                        }

                        String s2 = packetdataserializer.c(packetdataserializer.readableBytes());
                        boolean flag = packetdataserializer.readBoolean();

                        if (commandblocklistenerabstract != null) {
                            commandblocklistenerabstract.setCommand(s2);
                            commandblocklistenerabstract.a(flag);
                            if (!flag) {
                                commandblocklistenerabstract.b((IChatBaseComponent) null);
                            }

                            commandblocklistenerabstract.i();
                            this.player.sendMessage(new ChatMessage("advMode.setCommand.success", new Object[] { s2}));
                        }
                    } catch (Exception exception3) {
                        PlayerConnection.LOGGER.error("Couldn\'t set command block", exception3);
                    } finally {
                        packetdataserializer.release();
                    }
                } else if ("MC|AutoCmd".equals(s)) {
                    if (!this.minecraftServer.getEnableCommandBlock()) {
                        this.player.sendMessage(new ChatMessage("advMode.notEnabled", new Object[0]));
                        return;
                    }

                    if (!this.player.a(2, "") || !this.player.abilities.canInstantlyBuild) {
                        this.player.sendMessage(new ChatMessage("advMode.notAllowed", new Object[0]));
                        return;
                    }

                    packetdataserializer = packetplayincustompayload.b();

                    try {
                        CommandBlockListenerAbstract commandblocklistenerabstract1 = null;
                        TileEntityCommand tileentitycommand = null;
                        BlockPosition blockposition = new BlockPosition(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt());
                        TileEntity tileentity1 = this.player.world.getTileEntity(blockposition);

                        if (tileentity1 instanceof TileEntityCommand) {
                            tileentitycommand = (TileEntityCommand) tileentity1;
                            commandblocklistenerabstract1 = tileentitycommand.getCommandBlock();
                        }

                        s1 = packetdataserializer.c(packetdataserializer.readableBytes());
                        boolean flag1 = packetdataserializer.readBoolean();
                        TileEntityCommand.Type tileentitycommand_type = TileEntityCommand.Type.valueOf(packetdataserializer.c(16));
                        boolean flag2 = packetdataserializer.readBoolean();
                        boolean flag3 = packetdataserializer.readBoolean();

                        if (commandblocklistenerabstract1 != null) {
                            EnumDirection enumdirection = (EnumDirection) this.player.world.getType(blockposition).get(BlockCommand.a);
                            IBlockData iblockdata;

                            switch (PlayerConnection.SyntheticClass_1.d[tileentitycommand_type.ordinal()]) {
                            case 1:
                                iblockdata = Blocks.dd.getBlockData();
                                this.player.world.setTypeAndData(blockposition, iblockdata.set(BlockCommand.a, enumdirection).set(BlockCommand.b, Boolean.valueOf(flag2)), 2);
                                break;

                            case 2:
                                iblockdata = Blocks.dc.getBlockData();
                                this.player.world.setTypeAndData(blockposition, iblockdata.set(BlockCommand.a, enumdirection).set(BlockCommand.b, Boolean.valueOf(flag2)), 2);
                                break;

                            case 3:
                                iblockdata = Blocks.COMMAND_BLOCK.getBlockData();
                                this.player.world.setTypeAndData(blockposition, iblockdata.set(BlockCommand.a, enumdirection).set(BlockCommand.b, Boolean.valueOf(flag2)), 2);
                            }

                            tileentity1.z();
                            this.player.world.setTileEntity(blockposition, tileentity1);
                            commandblocklistenerabstract1.setCommand(s1);
                            commandblocklistenerabstract1.a(flag1);
                            if (!flag1) {
                                commandblocklistenerabstract1.b((IChatBaseComponent) null);
                            }

                            tileentitycommand.b(flag3);
                            commandblocklistenerabstract1.i();
                            if (!UtilColor.b(s1)) {
                                this.player.sendMessage(new ChatMessage("advMode.setCommand.success", new Object[] { s1}));
                            }
                        }
                    } catch (Exception exception4) {
                        PlayerConnection.LOGGER.error("Couldn\'t set command block", exception4);
                    } finally {
                        packetdataserializer.release();
                    }
                } else {
                    int k;

                    if ("MC|Beacon".equals(s)) {
                        if (this.player.activeContainer instanceof ContainerBeacon) {
                            try {
                                packetdataserializer = packetplayincustompayload.b();
                                k = packetdataserializer.readInt();
                                int l = packetdataserializer.readInt();
                                ContainerBeacon containerbeacon = (ContainerBeacon) this.player.activeContainer;
                                Slot slot = containerbeacon.getSlot(0);

                                if (slot.hasItem()) {
                                    slot.a(1);
                                    IInventory iinventory = containerbeacon.e();

                                    iinventory.setProperty(1, k);
                                    iinventory.setProperty(2, l);
                                    iinventory.update();
                                }
                            } catch (Exception exception5) {
                                PlayerConnection.LOGGER.error("Couldn\'t set beacon", exception5);
                            }
                        }
                    } else if ("MC|ItemName".equals(s)) {
                        if (this.player.activeContainer instanceof ContainerAnvil) {
                            ContainerAnvil containeranvil = (ContainerAnvil) this.player.activeContainer;

                            if (packetplayincustompayload.b() != null && packetplayincustompayload.b().readableBytes() >= 1) {
                                String s3 = SharedConstants.a(packetplayincustompayload.b().c(32767));

                                if (s3.length() <= 30) {
                                    containeranvil.a(s3);
                                }
                            } else {
                                containeranvil.a("");
                            }
                        }
                    } else if ("MC|Struct".equals(s)) {
                        packetdataserializer = packetplayincustompayload.b();

                        try {
                            if (this.player.a(4, "") && this.player.abilities.canInstantlyBuild) {
                                BlockPosition blockposition1 = new BlockPosition(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt());
                                IBlockData iblockdata1 = this.player.world.getType(blockposition1);

                                tileentity = this.player.world.getTileEntity(blockposition1);
                                if (tileentity instanceof TileEntityStructure) {
                                    TileEntityStructure tileentitystructure = (TileEntityStructure) tileentity;
                                    byte b1 = packetdataserializer.readByte();
                                    String s4 = packetdataserializer.c(32);

                                    tileentitystructure.a(TileEntityStructure.UsageMode.valueOf(s4));
                                    tileentitystructure.a(packetdataserializer.c(64));
                                    tileentitystructure.b(new BlockPosition(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt()));
                                    tileentitystructure.c(new BlockPosition(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt()));
                                    String s5 = packetdataserializer.c(32);

                                    tileentitystructure.a(EnumBlockMirror.valueOf(s5));
                                    String s6 = packetdataserializer.c(32);

                                    tileentitystructure.a(EnumBlockRotation.valueOf(s6));
                                    tileentitystructure.b(packetdataserializer.c(128));
                                    tileentitystructure.a(packetdataserializer.readBoolean());
                                    if (b1 == 2) {
                                        if (tileentitystructure.m()) {
                                            this.player.b((IChatBaseComponent) (new ChatComponentText("Structure saved")));
                                        } else {
                                            this.player.b((IChatBaseComponent) (new ChatComponentText("Structure NOT saved")));
                                        }
                                    } else if (b1 == 3) {
                                        if (tileentitystructure.n()) {
                                            this.player.b((IChatBaseComponent) (new ChatComponentText("Structure loaded")));
                                        } else {
                                            this.player.b((IChatBaseComponent) (new ChatComponentText("Structure prepared")));
                                        }
                                    } else if (b1 == 4 && tileentitystructure.l()) {
                                        this.player.b((IChatBaseComponent) (new ChatComponentText("Size detected")));
                                    }

                                    tileentitystructure.update();
                                    this.player.world.notify(blockposition1, iblockdata1, iblockdata1, 3);
                                }
                            }
                        } catch (Exception exception6) {
                            PlayerConnection.LOGGER.error("Couldn\'t set structure block", exception6);
                        } finally {
                            packetdataserializer.release();
                        }
                    } else if ("MC|PickItem".equals(s)) {
                        packetdataserializer = packetplayincustompayload.b();

                        try {
                            k = packetdataserializer.g();
                            this.player.inventory.d(k);
                            this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-2, this.player.inventory.itemInHandIndex, this.player.inventory.getItem(this.player.inventory.itemInHandIndex)));
                            this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-2, k, this.player.inventory.getItem(k)));
                            this.player.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(this.player.inventory.itemInHandIndex));
                        } catch (Exception exception7) {
                            PlayerConnection.LOGGER.error("Couldn\'t pick item", exception7);
                        } finally {
                            packetdataserializer.release();
                        }
                    }
                }
            }
        }

    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b;
        static final int[] c;
        static final int[] d = new int[TileEntityCommand.Type.values().length];

        static {
            try {
                PlayerConnection.SyntheticClass_1.d[TileEntityCommand.Type.SEQUENCE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.d[TileEntityCommand.Type.AUTO.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.d[TileEntityCommand.Type.REDSTONE.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            c = new int[PacketPlayInClientCommand.EnumClientCommand.values().length];

            try {
                PlayerConnection.SyntheticClass_1.c[PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.c[PacketPlayInClientCommand.EnumClientCommand.REQUEST_STATS.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.c[PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            b = new int[PacketPlayInEntityAction.EnumPlayerAction.values().length];

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SLEEPING.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.START_RIDING_JUMP.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.STOP_RIDING_JUMP.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.OPEN_INVENTORY.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.b[PacketPlayInEntityAction.EnumPlayerAction.START_FALL_FLYING.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            a = new int[PacketPlayInBlockDig.EnumPlayerDigType.values().length];

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.SWAP_HELD_ITEMS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                PlayerConnection.SyntheticClass_1.a[PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

        }
    }
}

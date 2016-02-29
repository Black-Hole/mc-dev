package net.minecraft.server;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity implements ICommandListener {

    private static final Logger a = LogManager.getLogger();
    private static final AxisAlignedBB b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    private static double c = 1.0D;
    private static int entityCount;
    private int id;
    public boolean i;
    public final List<Entity> passengers;
    protected int j;
    private Entity as;
    public boolean attachedToPlayer;
    public World world;
    public double lastX;
    public double lastY;
    public double lastZ;
    public double locX;
    public double locY;
    public double locZ;
    public double motX;
    public double motY;
    public double motZ;
    public float yaw;
    public float pitch;
    public float lastYaw;
    public float lastPitch;
    private AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean positionChanged;
    public boolean B;
    public boolean C;
    public boolean velocityChanged;
    protected boolean E;
    private boolean au;
    public boolean dead;
    public float width;
    public float length;
    public float I;
    public float J;
    public float K;
    public float fallDistance;
    private int av;
    public double M;
    public double N;
    public double O;
    public float P;
    public boolean noclip;
    public float R;
    protected Random random;
    public int ticksLived;
    public int maxFireTicks;
    public int fireTicks;
    public boolean inWater;
    public int noDamageTicks;
    protected boolean justCreated;
    protected boolean fireProof;
    protected DataWatcher datawatcher;
    private static final DataWatcherObject<Byte> ax = DataWatcher.a(Entity.class, DataWatcherRegistry.a);
    private static final DataWatcherObject<Integer> ay = DataWatcher.a(Entity.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<String> az = DataWatcher.a(Entity.class, DataWatcherRegistry.d);
    private static final DataWatcherObject<Boolean> aA = DataWatcher.a(Entity.class, DataWatcherRegistry.h);
    private static final DataWatcherObject<Boolean> aB = DataWatcher.a(Entity.class, DataWatcherRegistry.h);
    public boolean aa;
    public int ab;
    public int ac;
    public int ad;
    public boolean ah;
    public boolean impulse;
    public int portalCooldown;
    protected boolean ak;
    protected int al;
    public int dimension;
    protected BlockPosition an;
    protected Vec3D ao;
    protected EnumDirection ap;
    private boolean invulnerable;
    protected UUID uniqueID;
    private final CommandObjectiveExecutor aD;
    private final List<ItemStack> aE;
    public boolean glowing;
    private final Set<String> aF;
    private boolean aG;

    public Entity(World world) {
        this.id = Entity.entityCount++;
        this.passengers = Lists.newArrayList();
        this.boundingBox = Entity.b;
        this.width = 0.6F;
        this.length = 1.8F;
        this.av = 1;
        this.random = new Random();
        this.maxFireTicks = 1;
        this.justCreated = true;
        this.uniqueID = MathHelper.a(this.random);
        this.aD = new CommandObjectiveExecutor();
        this.aE = Lists.newArrayList();
        this.aF = Sets.newHashSet();
        this.world = world;
        this.setPosition(0.0D, 0.0D, 0.0D);
        if (world != null) {
            this.dimension = world.worldProvider.getDimensionManager().getDimensionID();
        }

        this.datawatcher = new DataWatcher(this);
        this.datawatcher.register(Entity.ax, Byte.valueOf((byte) 0));
        this.datawatcher.register(Entity.ay, Integer.valueOf(300));
        this.datawatcher.register(Entity.aA, Boolean.valueOf(false));
        this.datawatcher.register(Entity.az, "");
        this.datawatcher.register(Entity.aB, Boolean.valueOf(false));
        this.i();
    }

    public int getId() {
        return this.id;
    }

    public void f(int i) {
        this.id = i;
    }

    public Set<String> P() {
        return this.aF;
    }

    public boolean a(String s) {
        if (this.aF.size() >= 1024) {
            return false;
        } else {
            this.aF.add(s);
            return true;
        }
    }

    public boolean b(String s) {
        return this.aF.remove(s);
    }

    public void Q() {
        this.die();
    }

    protected abstract void i();

    public DataWatcher getDataWatcher() {
        return this.datawatcher;
    }

    public boolean equals(Object object) {
        return object instanceof Entity ? ((Entity) object).id == this.id : false;
    }

    public int hashCode() {
        return this.id;
    }

    public void die() {
        this.dead = true;
    }

    public void b(boolean flag) {}

    public void setSize(float f, float f1) {
        if (f != this.width || f1 != this.length) {
            float f2 = this.width;

            this.width = f;
            this.length = f1;
            AxisAlignedBB axisalignedbb = this.getBoundingBox();

            this.a(new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c, axisalignedbb.a + (double) this.width, axisalignedbb.b + (double) this.length, axisalignedbb.c + (double) this.width));
            if (this.width > f2 && !this.justCreated && !this.world.isClientSide) {
                this.move((double) (f2 - this.width), 0.0D, (double) (f2 - this.width));
            }
        }

    }

    protected void setYawPitch(float f, float f1) {
        this.yaw = f % 360.0F;
        this.pitch = f1 % 360.0F;
    }

    public void setPosition(double d0, double d1, double d2) {
        this.locX = d0;
        this.locY = d1;
        this.locZ = d2;
        float f = this.width / 2.0F;
        float f1 = this.length;

        this.a(new AxisAlignedBB(d0 - (double) f, d1, d2 - (double) f, d0 + (double) f, d1 + (double) f1, d2 + (double) f));
    }

    public void m() {
        if (!this.world.isClientSide) {
            this.setFlag(6, this.aM());
        }

        this.U();
    }

    public void U() {
        this.world.methodProfiler.a("entityBaseTick");
        if (this.isPassenger() && this.by().dead) {
            this.stopRiding();
        }

        if (this.j > 0) {
            --this.j;
        }

        this.I = this.J;
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.lastPitch = this.pitch;
        this.lastYaw = this.yaw;
        if (!this.world.isClientSide && this.world instanceof WorldServer) {
            this.world.methodProfiler.a("portal");
            if (this.ak) {
                MinecraftServer minecraftserver = this.world.getMinecraftServer();

                if (minecraftserver.getAllowNether()) {
                    if (!this.isPassenger()) {
                        int i = this.V();

                        if (this.al++ >= i) {
                            this.al = i;
                            this.portalCooldown = this.aC();
                            byte b0;

                            if (this.world.worldProvider.getDimensionManager().getDimensionID() == -1) {
                                b0 = 0;
                            } else {
                                b0 = -1;
                            }

                            this.c(b0);
                        }
                    }

                    this.ak = false;
                }
            } else {
                if (this.al > 0) {
                    this.al -= 4;
                }

                if (this.al < 0) {
                    this.al = 0;
                }
            }

            this.H();
            this.world.methodProfiler.b();
        }

        this.al();
        this.aj();
        if (this.world.isClientSide) {
            this.fireTicks = 0;
        } else if (this.fireTicks > 0) {
            if (this.fireProof) {
                this.fireTicks -= 4;
                if (this.fireTicks < 0) {
                    this.fireTicks = 0;
                }
            } else {
                if (this.fireTicks % 20 == 0) {
                    this.damageEntity(DamageSource.BURN, 1.0F);
                }

                --this.fireTicks;
            }
        }

        if (this.an()) {
            this.burnFromLava();
            this.fallDistance *= 0.5F;
        }

        if (this.locY < -64.0D) {
            this.Y();
        }

        if (!this.world.isClientSide) {
            this.setFlag(0, this.fireTicks > 0);
        }

        this.justCreated = false;
        this.world.methodProfiler.b();
    }

    protected void H() {
        if (this.portalCooldown > 0) {
            --this.portalCooldown;
        }

    }

    public int V() {
        return 1;
    }

    protected void burnFromLava() {
        if (!this.fireProof) {
            this.damageEntity(DamageSource.LAVA, 4.0F);
            this.setOnFire(15);
        }
    }

    public void setOnFire(int i) {
        int j = i * 20;

        if (this instanceof EntityLiving) {
            j = EnchantmentProtection.a((EntityLiving) this, j);
        }

        if (this.fireTicks < j) {
            this.fireTicks = j;
        }

    }

    public void extinguish() {
        this.fireTicks = 0;
    }

    protected void Y() {
        this.die();
    }

    public boolean c(double d0, double d1, double d2) {
        AxisAlignedBB axisalignedbb = this.getBoundingBox().c(d0, d1, d2);

        return this.b(axisalignedbb);
    }

    private boolean b(AxisAlignedBB axisalignedbb) {
        return this.world.getCubes(this, axisalignedbb).isEmpty() && !this.world.containsLiquid(axisalignedbb);
    }

    public void move(double d0, double d1, double d2) {
        if (this.noclip) {
            this.a(this.getBoundingBox().c(d0, d1, d2));
            this.recalcPosition();
        } else {
            this.world.methodProfiler.a("move");
            double d3 = this.locX;
            double d4 = this.locY;
            double d5 = this.locZ;

            if (this.E) {
                this.E = false;
                d0 *= 0.25D;
                d1 *= 0.05000000074505806D;
                d2 *= 0.25D;
                this.motX = 0.0D;
                this.motY = 0.0D;
                this.motZ = 0.0D;
            }

            double d6 = d0;
            double d7 = d1;
            double d8 = d2;
            boolean flag = this.onGround && this.isSneaking() && this instanceof EntityHuman;

            if (flag) {
                double d9;

                for (d9 = 0.05D; d0 != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(d0, -1.0D, 0.0D)).isEmpty(); d6 = d0) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }
                }

                for (; d2 != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(0.0D, -1.0D, d2)).isEmpty(); d8 = d2) {
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }
                }

                for (; d0 != 0.0D && d2 != 0.0D && this.world.getCubes(this, this.getBoundingBox().c(d0, -1.0D, d2)).isEmpty(); d8 = d2) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }

                    d6 = d0;
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }
                }
            }

            List list = this.world.getCubes(this, this.getBoundingBox().a(d0, d1, d2));
            AxisAlignedBB axisalignedbb = this.getBoundingBox();
            int i = 0;

            int j;

            for (j = list.size(); i < j; ++i) {
                d1 = ((AxisAlignedBB) list.get(i)).b(this.getBoundingBox(), d1);
            }

            this.a(this.getBoundingBox().c(0.0D, d1, 0.0D));
            boolean flag1 = this.onGround || d7 != d1 && d7 < 0.0D;

            j = 0;

            int k;

            for (k = list.size(); j < k; ++j) {
                d0 = ((AxisAlignedBB) list.get(j)).a(this.getBoundingBox(), d0);
            }

            this.a(this.getBoundingBox().c(d0, 0.0D, 0.0D));
            j = 0;

            for (k = list.size(); j < k; ++j) {
                d2 = ((AxisAlignedBB) list.get(j)).c(this.getBoundingBox(), d2);
            }

            this.a(this.getBoundingBox().c(0.0D, 0.0D, d2));
            double d10;

            if (this.P > 0.0F && flag1 && (d6 != d0 || d8 != d2)) {
                double d11 = d0;
                double d12 = d1;
                double d13 = d2;
                AxisAlignedBB axisalignedbb1 = this.getBoundingBox();

                this.a(axisalignedbb);
                d1 = (double) this.P;
                List list1 = this.world.getCubes(this, this.getBoundingBox().a(d6, d1, d8));
                AxisAlignedBB axisalignedbb2 = this.getBoundingBox();
                AxisAlignedBB axisalignedbb3 = axisalignedbb2.a(d6, 0.0D, d8);

                d10 = d1;
                int l = 0;

                for (int i1 = list1.size(); l < i1; ++l) {
                    d10 = ((AxisAlignedBB) list1.get(l)).b(axisalignedbb3, d10);
                }

                axisalignedbb2 = axisalignedbb2.c(0.0D, d10, 0.0D);
                double d14 = d6;
                int j1 = 0;

                for (int k1 = list1.size(); j1 < k1; ++j1) {
                    d14 = ((AxisAlignedBB) list1.get(j1)).a(axisalignedbb2, d14);
                }

                axisalignedbb2 = axisalignedbb2.c(d14, 0.0D, 0.0D);
                double d15 = d8;
                int l1 = 0;

                for (int i2 = list1.size(); l1 < i2; ++l1) {
                    d15 = ((AxisAlignedBB) list1.get(l1)).c(axisalignedbb2, d15);
                }

                axisalignedbb2 = axisalignedbb2.c(0.0D, 0.0D, d15);
                AxisAlignedBB axisalignedbb4 = this.getBoundingBox();
                double d16 = d1;
                int j2 = 0;

                for (int k2 = list1.size(); j2 < k2; ++j2) {
                    d16 = ((AxisAlignedBB) list1.get(j2)).b(axisalignedbb4, d16);
                }

                axisalignedbb4 = axisalignedbb4.c(0.0D, d16, 0.0D);
                double d17 = d6;
                int l2 = 0;

                for (int i3 = list1.size(); l2 < i3; ++l2) {
                    d17 = ((AxisAlignedBB) list1.get(l2)).a(axisalignedbb4, d17);
                }

                axisalignedbb4 = axisalignedbb4.c(d17, 0.0D, 0.0D);
                double d18 = d8;
                int j3 = 0;

                for (int k3 = list1.size(); j3 < k3; ++j3) {
                    d18 = ((AxisAlignedBB) list1.get(j3)).c(axisalignedbb4, d18);
                }

                axisalignedbb4 = axisalignedbb4.c(0.0D, 0.0D, d18);
                double d19 = d14 * d14 + d15 * d15;
                double d20 = d17 * d17 + d18 * d18;

                if (d19 > d20) {
                    d0 = d14;
                    d2 = d15;
                    d1 = -d10;
                    this.a(axisalignedbb2);
                } else {
                    d0 = d17;
                    d2 = d18;
                    d1 = -d16;
                    this.a(axisalignedbb4);
                }

                int l3 = 0;

                for (int i4 = list1.size(); l3 < i4; ++l3) {
                    d1 = ((AxisAlignedBB) list1.get(l3)).b(this.getBoundingBox(), d1);
                }

                this.a(this.getBoundingBox().c(0.0D, d1, 0.0D));
                if (d11 * d11 + d13 * d13 >= d0 * d0 + d2 * d2) {
                    d0 = d11;
                    d1 = d12;
                    d2 = d13;
                    this.a(axisalignedbb1);
                }
            }

            this.world.methodProfiler.b();
            this.world.methodProfiler.a("rest");
            this.recalcPosition();
            this.positionChanged = d6 != d0 || d8 != d2;
            this.B = d7 != d1;
            this.onGround = this.B && d7 < 0.0D;
            this.C = this.positionChanged || this.B;
            j = MathHelper.floor(this.locX);
            k = MathHelper.floor(this.locY - 0.20000000298023224D);
            int j4 = MathHelper.floor(this.locZ);
            BlockPosition blockposition = new BlockPosition(j, k, j4);
            IBlockData iblockdata = this.world.getType(blockposition);

            if (iblockdata.getMaterial() == Material.AIR) {
                BlockPosition blockposition1 = blockposition.down();
                IBlockData iblockdata1 = this.world.getType(blockposition1);
                Block block = iblockdata1.getBlock();

                if (block instanceof BlockFence || block instanceof BlockCobbleWall || block instanceof BlockFenceGate) {
                    iblockdata = iblockdata1;
                    blockposition = blockposition1;
                }
            }

            this.a(d1, this.onGround, iblockdata, blockposition);
            if (d6 != d0) {
                this.motX = 0.0D;
            }

            if (d8 != d2) {
                this.motZ = 0.0D;
            }

            Block block1 = iblockdata.getBlock();

            if (d7 != d1) {
                block1.a(this.world, this);
            }

            if (this.playStepSound() && !flag && !this.isPassenger()) {
                double d21 = this.locX - d3;
                double d22 = this.locY - d4;

                d10 = this.locZ - d5;
                if (block1 != Blocks.LADDER) {
                    d22 = 0.0D;
                }

                if (block1 != null && this.onGround) {
                    block1.stepOn(this.world, blockposition, this);
                }

                this.J = (float) ((double) this.J + (double) MathHelper.sqrt(d21 * d21 + d10 * d10) * 0.6D);
                this.K = (float) ((double) this.K + (double) MathHelper.sqrt(d21 * d21 + d22 * d22 + d10 * d10) * 0.6D);
                if (this.K > (float) this.av && iblockdata.getMaterial() != Material.AIR) {
                    this.av = (int) this.K + 1;
                    if (this.isInWater()) {
                        float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;

                        if (f > 1.0F) {
                            f = 1.0F;
                        }

                        this.a(this.aa(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    }

                    this.a(blockposition, block1);
                }
            }

            try {
                this.checkBlockCollisions();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");

                this.appendEntityCrashDetails(crashreportsystemdetails);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = this.ah();

            if (this.world.f(this.getBoundingBox().shrink(0.001D))) {
                this.burn(1);
                if (!flag2) {
                    ++this.fireTicks;
                    if (this.fireTicks == 0) {
                        this.setOnFire(8);
                    }
                }
            } else if (this.fireTicks <= 0) {
                this.fireTicks = -this.maxFireTicks;
            }

            if (flag2 && this.fireTicks > 0) {
                this.a(SoundEffects.bE, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                this.fireTicks = -this.maxFireTicks;
            }

            this.world.methodProfiler.b();
        }
    }

    public void recalcPosition() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();

        this.locX = (axisalignedbb.a + axisalignedbb.d) / 2.0D;
        this.locY = axisalignedbb.b;
        this.locZ = (axisalignedbb.c + axisalignedbb.f) / 2.0D;
    }

    protected SoundEffect aa() {
        return SoundEffects.bI;
    }

    protected SoundEffect ab() {
        return SoundEffects.bH;
    }

    protected void checkBlockCollisions() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        BlockPosition.PooledBlockPosition blockposition_pooledblockposition = BlockPosition.PooledBlockPosition.c(axisalignedbb.a + 0.001D, axisalignedbb.b + 0.001D, axisalignedbb.c + 0.001D);
        BlockPosition.PooledBlockPosition blockposition_pooledblockposition1 = BlockPosition.PooledBlockPosition.c(axisalignedbb.d - 0.001D, axisalignedbb.e - 0.001D, axisalignedbb.f - 0.001D);
        BlockPosition.PooledBlockPosition blockposition_pooledblockposition2 = BlockPosition.PooledBlockPosition.s();

        if (this.world.areChunksLoadedBetween(blockposition_pooledblockposition, blockposition_pooledblockposition1)) {
            for (int i = blockposition_pooledblockposition.getX(); i <= blockposition_pooledblockposition1.getX(); ++i) {
                for (int j = blockposition_pooledblockposition.getY(); j <= blockposition_pooledblockposition1.getY(); ++j) {
                    for (int k = blockposition_pooledblockposition.getZ(); k <= blockposition_pooledblockposition1.getZ(); ++k) {
                        blockposition_pooledblockposition2.d(i, j, k);
                        IBlockData iblockdata = this.world.getType(blockposition_pooledblockposition2);

                        try {
                            iblockdata.getBlock().a(this.world, (BlockPosition) blockposition_pooledblockposition2, iblockdata, this);
                        } catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
                            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");

                            CrashReportSystemDetails.a(crashreportsystemdetails, blockposition_pooledblockposition2, iblockdata);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }

        blockposition_pooledblockposition.t();
        blockposition_pooledblockposition1.t();
        blockposition_pooledblockposition2.t();
    }

    protected void a(BlockPosition blockposition, Block block) {
        SoundEffectType soundeffecttype = block.w();

        if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
            soundeffecttype = Blocks.SNOW_LAYER.w();
            this.a(soundeffecttype.d(), soundeffecttype.a() * 0.15F, soundeffecttype.b());
        } else if (!block.getBlockData().getMaterial().isLiquid()) {
            this.a(soundeffecttype.d(), soundeffecttype.a() * 0.15F, soundeffecttype.b());
        }

    }

    public void a(SoundEffect soundeffect, float f, float f1) {
        if (!this.ad()) {
            this.world.a((EntityHuman) null, this.locX, this.locY, this.locZ, soundeffect, this.bz(), f, f1);
        }

    }

    public boolean ad() {
        return ((Boolean) this.datawatcher.get(Entity.aB)).booleanValue();
    }

    public void c(boolean flag) {
        this.datawatcher.set(Entity.aB, Boolean.valueOf(flag));
    }

    protected boolean playStepSound() {
        return true;
    }

    protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
        if (flag) {
            if (this.fallDistance > 0.0F) {
                iblockdata.getBlock().fallOn(this.world, blockposition, this, this.fallDistance);
            }

            this.fallDistance = 0.0F;
        } else if (d0 < 0.0D) {
            this.fallDistance = (float) ((double) this.fallDistance - d0);
        }

    }

    public AxisAlignedBB af() {
        return null;
    }

    protected void burn(int i) {
        if (!this.fireProof) {
            this.damageEntity(DamageSource.FIRE, (float) i);
        }

    }

    public final boolean isFireProof() {
        return this.fireProof;
    }

    public void e(float f, float f1) {
        if (this.isVehicle()) {
            Iterator iterator = this.bu().iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                entity.e(f, f1);
            }
        }

    }

    public boolean ah() {
        if (this.inWater) {
            return true;
        } else {
            BlockPosition.PooledBlockPosition blockposition_pooledblockposition = BlockPosition.PooledBlockPosition.c(this.locX, this.locY, this.locZ);

            if (!this.world.isRainingAt(blockposition_pooledblockposition) && !this.world.isRainingAt(blockposition_pooledblockposition.d(this.locX, this.locY + (double) this.length, this.locZ))) {
                blockposition_pooledblockposition.t();
                return false;
            } else {
                blockposition_pooledblockposition.t();
                return true;
            }
        }
    }

    public boolean isInWater() {
        return this.inWater;
    }

    public boolean aj() {
        if (this.by() instanceof EntityBoat) {
            this.inWater = false;
        } else if (this.world.a(this.getBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, this)) {
            if (!this.inWater && !this.justCreated) {
                this.ak();
            }

            this.fallDistance = 0.0F;
            this.inWater = true;
            this.fireTicks = 0;
        } else {
            this.inWater = false;
        }

        return this.inWater;
    }

    protected void ak() {
        float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;

        if (f > 1.0F) {
            f = 1.0F;
        }

        this.a(this.ab(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        float f1 = (float) MathHelper.floor(this.getBoundingBox().b);

        int i;
        float f2;
        float f3;

        for (i = 0; (float) i < 1.0F + this.width * 20.0F; ++i) {
            f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
            f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
            this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (double) f2, (double) (f1 + 1.0F), this.locZ + (double) f3, this.motX, this.motY - (double) (this.random.nextFloat() * 0.2F), this.motZ, new int[0]);
        }

        for (i = 0; (float) i < 1.0F + this.width * 20.0F; ++i) {
            f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
            f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
            this.world.addParticle(EnumParticle.WATER_SPLASH, this.locX + (double) f2, (double) (f1 + 1.0F), this.locZ + (double) f3, this.motX, this.motY, this.motZ, new int[0]);
        }

    }

    public void al() {
        if (this.isSprinting() && !this.isInWater()) {
            this.am();
        }

    }

    protected void am() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locY - 0.20000000298023224D);
        int k = MathHelper.floor(this.locZ);
        BlockPosition blockposition = new BlockPosition(i, j, k);
        IBlockData iblockdata = this.world.getType(blockposition);

        if (iblockdata.i() != EnumRenderType.INVISIBLE) {
            this.world.addParticle(EnumParticle.BLOCK_CRACK, this.locX + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, this.getBoundingBox().b + 0.1D, this.locZ + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, -this.motX * 4.0D, 1.5D, -this.motZ * 4.0D, new int[] { Block.getCombinedId(iblockdata)});
        }

    }

    public boolean a(Material material) {
        if (this.by() instanceof EntityBoat) {
            return false;
        } else {
            double d0 = this.locY + (double) this.getHeadHeight();
            BlockPosition blockposition = new BlockPosition(this.locX, d0, this.locZ);
            IBlockData iblockdata = this.world.getType(blockposition);

            if (iblockdata.getMaterial() == material) {
                float f = BlockFluids.e(iblockdata.getBlock().toLegacyData(iblockdata)) - 0.11111111F;
                float f1 = (float) (blockposition.getY() + 1) - f;
                boolean flag = d0 < (double) f1;

                return !flag && this instanceof EntityHuman ? false : flag;
            } else {
                return false;
            }
        }
    }

    public boolean an() {
        return this.world.a(this.getBoundingBox().grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
    }

    public void a(float f, float f1, float f2) {
        float f3 = f * f + f1 * f1;

        if (f3 >= 1.0E-4F) {
            f3 = MathHelper.c(f3);
            if (f3 < 1.0F) {
                f3 = 1.0F;
            }

            f3 = f2 / f3;
            f *= f3;
            f1 *= f3;
            float f4 = MathHelper.sin(this.yaw * 0.017453292F);
            float f5 = MathHelper.cos(this.yaw * 0.017453292F);

            this.motX += (double) (f * f5 - f1 * f4);
            this.motZ += (double) (f1 * f5 + f * f4);
        }
    }

    public float e(float f) {
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ));

        if (this.world.isLoaded(blockposition_mutableblockposition)) {
            blockposition_mutableblockposition.p(MathHelper.floor(this.locY + (double) this.getHeadHeight()));
            return this.world.n(blockposition_mutableblockposition);
        } else {
            return 0.0F;
        }
    }

    public void spawnIn(World world) {
        this.world = world;
    }

    public void setLocation(double d0, double d1, double d2, float f, float f1) {
        this.lastX = this.locX = MathHelper.a(d0, -3.0E7D, 3.0E7D);
        this.lastY = this.locY = d1;
        this.lastZ = this.locZ = MathHelper.a(d2, -3.0E7D, 3.0E7D);
        f1 = MathHelper.a(f1, -90.0F, 90.0F);
        this.lastYaw = this.yaw = f;
        this.lastPitch = this.pitch = f1;
        double d3 = (double) (this.lastYaw - f);

        if (d3 < -180.0D) {
            this.lastYaw += 360.0F;
        }

        if (d3 >= 180.0D) {
            this.lastYaw -= 360.0F;
        }

        this.setPosition(this.locX, this.locY, this.locZ);
        this.setYawPitch(f, f1);
    }

    public void setPositionRotation(BlockPosition blockposition, float f, float f1) {
        this.setPositionRotation((double) blockposition.getX() + 0.5D, (double) blockposition.getY(), (double) blockposition.getZ() + 0.5D, f, f1);
    }

    public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
        this.M = this.lastX = this.locX = d0;
        this.N = this.lastY = this.locY = d1;
        this.O = this.lastZ = this.locZ = d2;
        this.yaw = f;
        this.pitch = f1;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    public float g(Entity entity) {
        float f = (float) (this.locX - entity.locX);
        float f1 = (float) (this.locY - entity.locY);
        float f2 = (float) (this.locZ - entity.locZ);

        return MathHelper.c(f * f + f1 * f1 + f2 * f2);
    }

    public double e(double d0, double d1, double d2) {
        double d3 = this.locX - d0;
        double d4 = this.locY - d1;
        double d5 = this.locZ - d2;

        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double c(BlockPosition blockposition) {
        return blockposition.distanceSquared(this.locX, this.locY, this.locZ);
    }

    public double d(BlockPosition blockposition) {
        return blockposition.f(this.locX, this.locY, this.locZ);
    }

    public double f(double d0, double d1, double d2) {
        double d3 = this.locX - d0;
        double d4 = this.locY - d1;
        double d5 = this.locZ - d2;

        return (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double h(Entity entity) {
        double d0 = this.locX - entity.locX;
        double d1 = this.locY - entity.locY;
        double d2 = this.locZ - entity.locZ;

        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public void d(EntityHuman entityhuman) {}

    public void collide(Entity entity) {
        if (!this.x(entity)) {
            if (!entity.noclip && !this.noclip) {
                double d0 = entity.locX - this.locX;
                double d1 = entity.locZ - this.locZ;
                double d2 = MathHelper.a(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = (double) MathHelper.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.05000000074505806D;
                    d1 *= 0.05000000074505806D;
                    d0 *= (double) (1.0F - this.R);
                    d1 *= (double) (1.0F - this.R);
                    if (!this.isVehicle()) {
                        this.g(-d0, 0.0D, -d1);
                    }

                    if (!entity.isVehicle()) {
                        entity.g(d0, 0.0D, d1);
                    }
                }

            }
        }
    }

    public void g(double d0, double d1, double d2) {
        this.motX += d0;
        this.motY += d1;
        this.motZ += d2;
        this.impulse = true;
    }

    protected void ao() {
        this.velocityChanged = true;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            this.ao();
            return false;
        }
    }

    public Vec3D f(float f) {
        if (f == 1.0F) {
            return this.f(this.pitch, this.yaw);
        } else {
            float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
            float f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;

            return this.f(f1, f2);
        }
    }

    protected final Vec3D f(float f, float f1) {
        float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);

        return new Vec3D((double) (f3 * f4), (double) f5, (double) (f2 * f4));
    }

    public boolean isInteractable() {
        return false;
    }

    public boolean isCollidable() {
        return false;
    }

    public void b(Entity entity, int i) {}

    public boolean c(NBTTagCompound nbttagcompound) {
        String s = this.as();

        if (!this.dead && s != null) {
            nbttagcompound.setString("id", s);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        String s = this.as();

        if (!this.dead && s != null && !this.isPassenger()) {
            nbttagcompound.setString("id", s);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public void e(NBTTagCompound nbttagcompound) {
        try {
            nbttagcompound.set("Pos", this.a(new double[] { this.locX, this.locY, this.locZ}));
            nbttagcompound.set("Motion", this.a(new double[] { this.motX, this.motY, this.motZ}));
            nbttagcompound.set("Rotation", this.a(new float[] { this.yaw, this.pitch}));
            nbttagcompound.setFloat("FallDistance", this.fallDistance);
            nbttagcompound.setShort("Fire", (short) this.fireTicks);
            nbttagcompound.setShort("Air", (short) this.getAirTicks());
            nbttagcompound.setBoolean("OnGround", this.onGround);
            nbttagcompound.setInt("Dimension", this.dimension);
            nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
            nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
            nbttagcompound.a("UUID", this.getUniqueID());
            if (this.getCustomName() != null && !this.getCustomName().isEmpty()) {
                nbttagcompound.setString("CustomName", this.getCustomName());
            }

            if (this.getCustomNameVisible()) {
                nbttagcompound.setBoolean("CustomNameVisible", this.getCustomNameVisible());
            }

            this.aD.b(nbttagcompound);
            if (this.ad()) {
                nbttagcompound.setBoolean("Silent", this.ad());
            }

            if (this.glowing) {
                nbttagcompound.setBoolean("Glowing", this.glowing);
            }

            NBTTagList nbttaglist;
            Iterator iterator;

            if (this.aF.size() > 0) {
                nbttaglist = new NBTTagList();
                iterator = this.aF.iterator();

                while (iterator.hasNext()) {
                    String s = (String) iterator.next();

                    nbttaglist.add(new NBTTagString(s));
                }

                nbttagcompound.set("Tags", nbttaglist);
            }

            this.b(nbttagcompound);
            if (this.isVehicle()) {
                nbttaglist = new NBTTagList();
                iterator = this.bu().iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                    if (entity.c(nbttagcompound1)) {
                        nbttaglist.add(nbttagcompound1);
                    }
                }

                if (!nbttaglist.isEmpty()) {
                    nbttagcompound.set("Passengers", nbttaglist);
                }
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");

            this.appendEntityCrashDetails(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }

    public void f(NBTTagCompound nbttagcompound) {
        try {
            NBTTagList nbttaglist = nbttagcompound.getList("Pos", 6);
            NBTTagList nbttaglist1 = nbttagcompound.getList("Motion", 6);
            NBTTagList nbttaglist2 = nbttagcompound.getList("Rotation", 5);

            this.motX = nbttaglist1.e(0);
            this.motY = nbttaglist1.e(1);
            this.motZ = nbttaglist1.e(2);
            if (Math.abs(this.motX) > 10.0D) {
                this.motX = 0.0D;
            }

            if (Math.abs(this.motY) > 10.0D) {
                this.motY = 0.0D;
            }

            if (Math.abs(this.motZ) > 10.0D) {
                this.motZ = 0.0D;
            }

            this.lastX = this.M = this.locX = nbttaglist.e(0);
            this.lastY = this.N = this.locY = nbttaglist.e(1);
            this.lastZ = this.O = this.locZ = nbttaglist.e(2);
            this.lastYaw = this.yaw = nbttaglist2.f(0);
            this.lastPitch = this.pitch = nbttaglist2.f(1);
            this.h(this.yaw);
            this.i(this.yaw);
            this.fallDistance = nbttagcompound.getFloat("FallDistance");
            this.fireTicks = nbttagcompound.getShort("Fire");
            this.setAirTicks(nbttagcompound.getShort("Air"));
            this.onGround = nbttagcompound.getBoolean("OnGround");
            if (nbttagcompound.hasKey("Dimension")) {
                this.dimension = nbttagcompound.getInt("Dimension");
            }

            this.invulnerable = nbttagcompound.getBoolean("Invulnerable");
            this.portalCooldown = nbttagcompound.getInt("PortalCooldown");
            if (nbttagcompound.b("UUID")) {
                this.uniqueID = nbttagcompound.a("UUID");
            }

            this.setPosition(this.locX, this.locY, this.locZ);
            this.setYawPitch(this.yaw, this.pitch);
            if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
                this.setCustomName(nbttagcompound.getString("CustomName"));
            }

            this.setCustomNameVisible(nbttagcompound.getBoolean("CustomNameVisible"));
            this.aD.a(nbttagcompound);
            this.c(nbttagcompound.getBoolean("Silent"));
            this.f(nbttagcompound.getBoolean("Glowing"));
            if (nbttagcompound.hasKeyOfType("Tags", 9)) {
                this.aF.clear();
                NBTTagList nbttaglist3 = nbttagcompound.getList("Tags", 8);
                int i = Math.min(nbttaglist3.size(), 1024);

                for (int j = 0; j < i; ++j) {
                    this.aF.add(nbttaglist3.getString(j));
                }
            }

            this.a(nbttagcompound);
            if (this.ar()) {
                this.setPosition(this.locX, this.locY, this.locZ);
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");

            this.appendEntityCrashDetails(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }

    protected boolean ar() {
        return true;
    }

    protected final String as() {
        return EntityTypes.b(this);
    }

    protected abstract void a(NBTTagCompound nbttagcompound);

    protected abstract void b(NBTTagCompound nbttagcompound);

    public void at() {}

    protected NBTTagList a(double... adouble) {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble1 = adouble;
        int i = adouble.length;

        for (int j = 0; j < i; ++j) {
            double d0 = adouble1[j];

            nbttaglist.add(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }

    protected NBTTagList a(float... afloat) {
        NBTTagList nbttaglist = new NBTTagList();
        float[] afloat1 = afloat;
        int i = afloat.length;

        for (int j = 0; j < i; ++j) {
            float f = afloat1[j];

            nbttaglist.add(new NBTTagFloat(f));
        }

        return nbttaglist;
    }

    public EntityItem a(Item item, int i) {
        return this.a(item, i, 0.0F);
    }

    public EntityItem a(Item item, int i, float f) {
        return this.a(new ItemStack(item, i, 0), f);
    }

    public EntityItem a(ItemStack itemstack, float f) {
        if (itemstack.count != 0 && itemstack.getItem() != null) {
            EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + (double) f, this.locZ, itemstack);

            entityitem.q();
            this.world.addEntity(entityitem);
            return entityitem;
        } else {
            return null;
        }
    }

    public boolean isAlive() {
        return !this.dead;
    }

    public boolean inBlock() {
        if (this.noclip) {
            return false;
        } else {
            BlockPosition.PooledBlockPosition blockposition_pooledblockposition = BlockPosition.PooledBlockPosition.s();

            for (int i = 0; i < 8; ++i) {
                int j = MathHelper.floor(this.locY + (double) (((float) ((i >> 0) % 2) - 0.5F) * 0.1F) + (double) this.getHeadHeight());
                int k = MathHelper.floor(this.locX + (double) (((float) ((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
                int l = MathHelper.floor(this.locZ + (double) (((float) ((i >> 2) % 2) - 0.5F) * this.width * 0.8F));

                if (blockposition_pooledblockposition.getX() != k || blockposition_pooledblockposition.getY() != j || blockposition_pooledblockposition.getZ() != l) {
                    blockposition_pooledblockposition.d(k, j, l);
                    if (this.world.getType(blockposition_pooledblockposition).getBlock().j()) {
                        blockposition_pooledblockposition.t();
                        return true;
                    }
                }
            }

            blockposition_pooledblockposition.t();
            return false;
        }
    }

    public boolean a(EntityHuman entityhuman, ItemStack itemstack, EnumHand enumhand) {
        return false;
    }

    public AxisAlignedBB j(Entity entity) {
        return null;
    }

    public void aw() {
        Entity entity = this.by();

        if (this.isPassenger() && entity.dead) {
            this.stopRiding();
        } else {
            this.motX = 0.0D;
            this.motY = 0.0D;
            this.motZ = 0.0D;
            this.m();
            if (this.isPassenger()) {
                entity.k(this);
            }
        }
    }

    public void k(Entity entity) {
        if (this.w(entity)) {
            entity.setPosition(this.locX, this.locY + this.ay() + entity.ax(), this.locZ);
        }
    }

    public double ax() {
        return 0.0D;
    }

    public double ay() {
        return (double) this.length * 0.75D;
    }

    public boolean startRiding(Entity entity) {
        return this.a(entity, false);
    }

    public boolean a(Entity entity, boolean flag) {
        if (!flag && (!this.n(entity) || !entity.q(this))) {
            return false;
        } else {
            if (this.isPassenger()) {
                this.stopRiding();
            }

            this.as = entity;
            this.as.o(this);
            return true;
        }
    }

    protected boolean n(Entity entity) {
        return this.j <= 0;
    }

    public void az() {
        for (int i = this.passengers.size() - 1; i >= 0; --i) {
            ((Entity) this.passengers.get(i)).stopRiding();
        }

    }

    public void stopRiding() {
        if (this.as != null) {
            Entity entity = this.as;

            this.as = null;
            entity.p(this);
        }

    }

    protected void o(Entity entity) {
        if (entity.by() != this) {
            throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
        } else {
            if (!this.world.isClientSide && entity instanceof EntityHuman && !(this.bt() instanceof EntityHuman)) {
                this.passengers.add(0, entity);
            } else {
                this.passengers.add(entity);
            }

        }
    }

    protected void p(Entity entity) {
        if (entity.by() == this) {
            throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
        } else {
            this.passengers.remove(entity);
            entity.j = 60;
        }
    }

    protected boolean q(Entity entity) {
        return this.bu().size() < 1;
    }

    public float aA() {
        return 0.0F;
    }

    public Vec3D aB() {
        return null;
    }

    public void e(BlockPosition blockposition) {
        if (this.portalCooldown > 0) {
            this.portalCooldown = this.aC();
        } else {
            if (!this.world.isClientSide && !blockposition.equals(this.an)) {
                this.an = new BlockPosition(blockposition);
                ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = Blocks.PORTAL.c(this.world, this.an);
                double d0 = shapedetector_shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X ? (double) shapedetector_shapedetectorcollection.a().getZ() : (double) shapedetector_shapedetectorcollection.a().getX();
                double d1 = shapedetector_shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X ? this.locZ : this.locX;

                d1 = Math.abs(MathHelper.c(d1 - (double) (shapedetector_shapedetectorcollection.getFacing().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE ? 1 : 0), d0, d0 - (double) shapedetector_shapedetectorcollection.d()));
                double d2 = MathHelper.c(this.locY - 1.0D, (double) shapedetector_shapedetectorcollection.a().getY(), (double) (shapedetector_shapedetectorcollection.a().getY() - shapedetector_shapedetectorcollection.e()));

                this.ao = new Vec3D(d1, d2, 0.0D);
                this.ap = shapedetector_shapedetectorcollection.getFacing();
            }

            this.ak = true;
        }
    }

    public int aC() {
        return 300;
    }

    public Iterable<ItemStack> aE() {
        return this.aE;
    }

    public Iterable<ItemStack> getArmorItems() {
        return this.aE;
    }

    public Iterable<ItemStack> aG() {
        return Iterables.concat(this.aE(), this.getArmorItems());
    }

    public void setEquipment(EnumItemSlot enumitemslot, ItemStack itemstack) {}

    public boolean isBurning() {
        boolean flag = this.world != null && this.world.isClientSide;

        return !this.fireProof && (this.fireTicks > 0 || flag && this.getFlag(0));
    }

    public boolean isPassenger() {
        return this.by() != null;
    }

    public boolean isVehicle() {
        return !this.bu().isEmpty();
    }

    public boolean isSneaking() {
        return this.getFlag(1);
    }

    public void setSneaking(boolean flag) {
        this.setFlag(1, flag);
    }

    public boolean isSprinting() {
        return this.getFlag(3);
    }

    public void setSprinting(boolean flag) {
        this.setFlag(3, flag);
    }

    public boolean aM() {
        return this.glowing || this.world.isClientSide && this.getFlag(6);
    }

    public void f(boolean flag) {
        this.glowing = flag;
        if (!this.world.isClientSide) {
            this.setFlag(6, this.glowing);
        }

    }

    public boolean isInvisible() {
        return this.getFlag(5);
    }

    public ScoreboardTeamBase aO() {
        return this.world.getScoreboard().getPlayerTeam(this.getUniqueID().toString());
    }

    public boolean r(Entity entity) {
        return this.a(entity.aO());
    }

    public boolean a(ScoreboardTeamBase scoreboardteambase) {
        return this.aO() != null ? this.aO().isAlly(scoreboardteambase) : false;
    }

    public void setInvisible(boolean flag) {
        this.setFlag(5, flag);
    }

    public boolean getFlag(int i) {
        return (((Byte) this.datawatcher.get(Entity.ax)).byteValue() & 1 << i) != 0;
    }

    public void setFlag(int i, boolean flag) {
        byte b0 = ((Byte) this.datawatcher.get(Entity.ax)).byteValue();

        if (flag) {
            this.datawatcher.set(Entity.ax, Byte.valueOf((byte) (b0 | 1 << i)));
        } else {
            this.datawatcher.set(Entity.ax, Byte.valueOf((byte) (b0 & ~(1 << i))));
        }

    }

    public int getAirTicks() {
        return ((Integer) this.datawatcher.get(Entity.ay)).intValue();
    }

    public void setAirTicks(int i) {
        this.datawatcher.set(Entity.ay, Integer.valueOf(i));
    }

    public void onLightningStrike(EntityLightning entitylightning) {
        this.damageEntity(DamageSource.LIGHTNING, 5.0F);
        ++this.fireTicks;
        if (this.fireTicks == 0) {
            this.setOnFire(8);
        }

    }

    public void b(EntityLiving entityliving) {}

    protected boolean j(double d0, double d1, double d2) {
        BlockPosition blockposition = new BlockPosition(d0, d1, d2);
        double d3 = d0 - (double) blockposition.getX();
        double d4 = d1 - (double) blockposition.getY();
        double d5 = d2 - (double) blockposition.getZ();
        List list = this.world.a(this.getBoundingBox());

        if (list.isEmpty()) {
            return false;
        } else {
            EnumDirection enumdirection = EnumDirection.UP;
            double d6 = Double.MAX_VALUE;

            if (!this.world.t(blockposition.west()) && d3 < d6) {
                d6 = d3;
                enumdirection = EnumDirection.WEST;
            }

            if (!this.world.t(blockposition.east()) && 1.0D - d3 < d6) {
                d6 = 1.0D - d3;
                enumdirection = EnumDirection.EAST;
            }

            if (!this.world.t(blockposition.north()) && d5 < d6) {
                d6 = d5;
                enumdirection = EnumDirection.NORTH;
            }

            if (!this.world.t(blockposition.south()) && 1.0D - d5 < d6) {
                d6 = 1.0D - d5;
                enumdirection = EnumDirection.SOUTH;
            }

            if (!this.world.t(blockposition.up()) && 1.0D - d4 < d6) {
                d6 = 1.0D - d4;
                enumdirection = EnumDirection.UP;
            }

            float f = this.random.nextFloat() * 0.2F + 0.1F;
            float f1 = (float) enumdirection.c().a();

            if (enumdirection.k() == EnumDirection.EnumAxis.X) {
                this.motX += (double) (f1 * f);
            } else if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
                this.motY += (double) (f1 * f);
            } else if (enumdirection.k() == EnumDirection.EnumAxis.Z) {
                this.motZ += (double) (f1 * f);
            }

            return true;
        }
    }

    public void aQ() {
        this.E = true;
        this.fallDistance = 0.0F;
    }

    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomName();
        } else {
            String s = EntityTypes.b(this);

            if (s == null) {
                s = "generic";
            }

            return LocaleI18n.get("entity." + s + ".name");
        }
    }

    public Entity[] aR() {
        return null;
    }

    public boolean s(Entity entity) {
        return this == entity;
    }

    public float getHeadRotation() {
        return 0.0F;
    }

    public void h(float f) {}

    public void i(float f) {}

    public boolean aT() {
        return true;
    }

    public boolean t(Entity entity) {
        return false;
    }

    public String toString() {
        return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[] { this.getClass().getSimpleName(), this.getName(), Integer.valueOf(this.id), this.world == null ? "~NULL~" : this.world.getWorldData().getName(), Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ)});
    }

    public boolean isInvulnerable(DamageSource damagesource) {
        return this.invulnerable && damagesource != DamageSource.OUT_OF_WORLD && !damagesource.u();
    }

    public void h(boolean flag) {
        this.invulnerable = flag;
    }

    public void u(Entity entity) {
        this.setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
    }

    private void a(Entity entity) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        entity.e(nbttagcompound);
        nbttagcompound.remove("Dimension");
        this.f(nbttagcompound);
        this.portalCooldown = entity.portalCooldown;
        this.an = entity.an;
        this.ao = entity.ao;
        this.ap = entity.ap;
    }

    public Entity c(int i) {
        if (!this.world.isClientSide && !this.dead) {
            this.world.methodProfiler.a("changeDimension");
            MinecraftServer minecraftserver = this.h();
            int j = this.dimension;
            WorldServer worldserver = minecraftserver.getWorldServer(j);
            WorldServer worldserver1 = minecraftserver.getWorldServer(i);

            this.dimension = i;
            if (j == 1 && i == 1) {
                worldserver1 = minecraftserver.getWorldServer(0);
                this.dimension = 0;
            }

            this.world.kill(this);
            this.dead = false;
            this.world.methodProfiler.a("reposition");
            BlockPosition blockposition;

            if (i == 1) {
                blockposition = worldserver1.getDimensionSpawn();
            } else {
                double d0 = this.locX;
                double d1 = this.locZ;
                double d2 = 8.0D;

                if (i == -1) {
                    d0 = MathHelper.a(d0 / d2, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
                    d1 = MathHelper.a(d1 / d2, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
                } else if (i == 0) {
                    d0 = MathHelper.a(d0 * d2, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
                    d1 = MathHelper.a(d1 * d2, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
                }

                d0 = (double) MathHelper.clamp((int) d0, -29999872, 29999872);
                d1 = (double) MathHelper.clamp((int) d1, -29999872, 29999872);
                float f = this.yaw;

                this.setPositionRotation(d0, this.locY, d1, 90.0F, 0.0F);
                PortalTravelAgent portaltravelagent = worldserver1.getTravelAgent();

                portaltravelagent.b(this, f);
                blockposition = new BlockPosition(this);
            }

            worldserver.entityJoinedWorld(this, false);
            this.world.methodProfiler.c("reloading");
            Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);

            if (entity != null) {
                entity.a(this);
                if (j == 1 && i == 1) {
                    BlockPosition blockposition1 = worldserver1.q(worldserver1.getSpawn());

                    entity.setPositionRotation(blockposition1, entity.yaw, entity.pitch);
                } else {
                    entity.setPositionRotation(blockposition, entity.yaw, entity.pitch);
                }

                boolean flag = entity.attachedToPlayer;

                entity.attachedToPlayer = true;
                worldserver1.addEntity(entity);
                entity.attachedToPlayer = flag;
                worldserver1.entityJoinedWorld(entity, false);
            }

            this.dead = true;
            this.world.methodProfiler.b();
            worldserver.m();
            worldserver1.m();
            this.world.methodProfiler.b();
            return entity;
        } else {
            return null;
        }
    }

    public boolean aV() {
        return true;
    }

    public float a(Explosion explosion, World world, BlockPosition blockposition, IBlockData iblockdata) {
        return iblockdata.getBlock().a(this);
    }

    public boolean a(Explosion explosion, World world, BlockPosition blockposition, IBlockData iblockdata, float f) {
        return true;
    }

    public int aW() {
        return 3;
    }

    public Vec3D getPortalOffset() {
        return this.ao;
    }

    public EnumDirection getPortalDirection() {
        return this.ap;
    }

    public boolean isIgnoreBlockTrigger() {
        return false;
    }

    public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
        crashreportsystemdetails.a("Entity Type", new Callable() {
            public String a() throws Exception {
                return EntityTypes.b(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreportsystemdetails.a("Entity ID", (Object) Integer.valueOf(this.id));
        crashreportsystemdetails.a("Entity Name", new Callable() {
            public String a() throws Exception {
                return Entity.this.getName();
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreportsystemdetails.a("Entity\'s Exact location", (Object) String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ)}));
        crashreportsystemdetails.a("Entity\'s Block location", (Object) CrashReportSystemDetails.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)));
        crashreportsystemdetails.a("Entity\'s Momentum", (Object) String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motX), Double.valueOf(this.motY), Double.valueOf(this.motZ)}));
        crashreportsystemdetails.a("Entity\'s Passengers", new Callable() {
            public String a() throws Exception {
                return Entity.this.bu().toString();
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreportsystemdetails.a("Entity\'s Vehicle", new Callable() {
            public String a() throws Exception {
                return Entity.this.by().toString();
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
    }

    public void a(UUID uuid) {
        this.uniqueID = uuid;
    }

    public UUID getUniqueID() {
        return this.uniqueID;
    }

    public boolean bd() {
        return true;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(this.aO(), this.getName()));

        chatcomponenttext.getChatModifier().setChatHoverable(this.bk());
        chatcomponenttext.getChatModifier().setInsertion(this.getUniqueID().toString());
        return chatcomponenttext;
    }

    public void setCustomName(String s) {
        this.datawatcher.set(Entity.az, s);
    }

    public String getCustomName() {
        return (String) this.datawatcher.get(Entity.az);
    }

    public boolean hasCustomName() {
        return !((String) this.datawatcher.get(Entity.az)).isEmpty();
    }

    public void setCustomNameVisible(boolean flag) {
        this.datawatcher.set(Entity.aA, Boolean.valueOf(flag));
    }

    public boolean getCustomNameVisible() {
        return ((Boolean) this.datawatcher.get(Entity.aA)).booleanValue();
    }

    public void enderTeleportTo(double d0, double d1, double d2) {
        this.aG = true;
        this.setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
        this.world.entityJoinedWorld(this, false);
    }

    public void a(DataWatcherObject<?> datawatcherobject) {}

    public EnumDirection getDirection() {
        return EnumDirection.fromType2(MathHelper.floor((double) (this.yaw * 4.0F / 360.0F) + 0.5D) & 3);
    }

    public EnumDirection bj() {
        return this.getDirection();
    }

    protected ChatHoverable bk() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        String s = EntityTypes.b(this);

        nbttagcompound.setString("id", this.getUniqueID().toString());
        if (s != null) {
            nbttagcompound.setString("type", s);
        }

        nbttagcompound.setString("name", this.getName());
        return new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ENTITY, new ChatComponentText(nbttagcompound.toString()));
    }

    public boolean a(EntityPlayer entityplayer) {
        return true;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void a(AxisAlignedBB axisalignedbb) {
        this.boundingBox = axisalignedbb;
    }

    public float getHeadHeight() {
        return this.length * 0.85F;
    }

    public boolean bo() {
        return this.au;
    }

    public void j(boolean flag) {
        this.au = flag;
    }

    public boolean c(int i, ItemStack itemstack) {
        return false;
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {}

    public boolean a(int i, String s) {
        return true;
    }

    public BlockPosition getChunkCoordinates() {
        return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
    }

    public Vec3D d() {
        return new Vec3D(this.locX, this.locY, this.locZ);
    }

    public World getWorld() {
        return this.world;
    }

    public Entity f() {
        return this;
    }

    public boolean getSendCommandFeedback() {
        return false;
    }

    public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {
        if (this.world != null && !this.world.isClientSide) {
            this.aD.a(this.world.getMinecraftServer(), this, commandobjectiveexecutor_enumcommandresult, i);
        }

    }

    public MinecraftServer h() {
        return this.world.getMinecraftServer();
    }

    public CommandObjectiveExecutor bp() {
        return this.aD;
    }

    public void v(Entity entity) {
        this.aD.a(entity.bp());
    }

    public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, ItemStack itemstack, EnumHand enumhand) {
        return EnumInteractionResult.PASS;
    }

    public boolean bq() {
        return false;
    }

    protected void a(EntityLiving entityliving, Entity entity) {
        if (entity instanceof EntityLiving) {
            EnchantmentManager.a((EntityLiving) entity, (Entity) entityliving);
        }

        EnchantmentManager.b(entityliving, entity);
    }

    public void b(EntityPlayer entityplayer) {}

    public void c(EntityPlayer entityplayer) {}

    public float a(EnumBlockRotation enumblockrotation) {
        float f = MathHelper.g(this.yaw);

        switch (Entity.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return f + 180.0F;

        case 2:
            return f + 270.0F;

        case 3:
            return f + 90.0F;

        default:
            return f;
        }
    }

    public float a(EnumBlockMirror enumblockmirror) {
        float f = MathHelper.g(this.yaw);

        switch (Entity.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            return -f;

        case 2:
            return 180.0F - f;

        default:
            return f;
        }
    }

    public boolean br() {
        return false;
    }

    public boolean bs() {
        boolean flag = this.aG;

        this.aG = false;
        return flag;
    }

    public Entity bt() {
        return null;
    }

    public List<Entity> bu() {
        return (List) (this.passengers.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.passengers));
    }

    public boolean w(Entity entity) {
        Iterator iterator = this.bu().iterator();

        Entity entity1;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            entity1 = (Entity) iterator.next();
        } while (!entity1.equals(entity));

        return true;
    }

    public Collection<Entity> bv() {
        HashSet hashset = Sets.newHashSet();

        this.a(Entity.class, (Set) hashset);
        return hashset;
    }

    public <T extends Entity> Collection<T> b(Class<T> oclass) {
        HashSet hashset = Sets.newHashSet();

        this.a(oclass, (Set) hashset);
        return hashset;
    }

    private <T extends Entity> void a(Class<T> oclass, Set<T> set) {
        Entity entity;

        for (Iterator iterator = this.bu().iterator(); iterator.hasNext(); entity.a(oclass, set)) {
            entity = (Entity) iterator.next();
            if (oclass.isAssignableFrom(entity.getClass())) {
                set.add(entity);
            }
        }

    }

    public Entity getVehicle() {
        Entity entity;

        for (entity = this; entity.isPassenger(); entity = entity.by()) {
            ;
        }

        return entity;
    }

    public boolean x(Entity entity) {
        return this.getVehicle() == entity.getVehicle();
    }

    public boolean y(Entity entity) {
        Iterator iterator = this.bu().iterator();

        Entity entity1;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            entity1 = (Entity) iterator.next();
            if (entity1.equals(entity)) {
                return true;
            }
        } while (!entity1.y(entity));

        return true;
    }

    public boolean bx() {
        Entity entity = this.bt();

        return entity instanceof EntityHuman ? ((EntityHuman) entity).cJ() : !this.world.isClientSide;
    }

    public Entity by() {
        return this.as;
    }

    public EnumPistonReaction z() {
        return EnumPistonReaction.NORMAL;
    }

    public SoundCategory bz() {
        return SoundCategory.NEUTRAL;
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockMirror.values().length];

        static {
            try {
                Entity.SyntheticClass_1.b[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                Entity.SyntheticClass_1.b[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumBlockRotation.values().length];

            try {
                Entity.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                Entity.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                Entity.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }
}

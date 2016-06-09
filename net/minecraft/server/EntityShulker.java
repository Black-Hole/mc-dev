package net.minecraft.server;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class EntityShulker extends EntityGolem implements IMonster {

    private static final UUID bx = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
    private static final AttributeModifier by = (new AttributeModifier(EntityShulker.bx, "Covered armor bonus", 20.0D, 0)).a(false);
    protected static final DataWatcherObject<EnumDirection> a = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.l);
    protected static final DataWatcherObject<Optional<BlockPosition>> b = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.k);
    protected static final DataWatcherObject<Byte> c = DataWatcher.a(EntityShulker.class, DataWatcherRegistry.a);
    private float bz;
    private float bA;
    private BlockPosition bB;
    private int bC;

    public EntityShulker(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.aP = 180.0F;
        this.aO = 180.0F;
        this.fireProof = true;
        this.bB = null;
        this.b_ = 5;
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity) {
        this.aO = 180.0F;
        this.aP = 180.0F;
        this.yaw = 180.0F;
        this.lastYaw = 180.0F;
        this.aQ = 180.0F;
        this.aR = 180.0F;
        return super.prepare(difficultydamagescaler, groupdataentity);
    }

    protected void r() {
        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(4, new EntityShulker.a());
        this.goalSelector.a(7, new EntityShulker.e((EntityShulker.SyntheticClass_1) null));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new EntityShulker.d(this));
        this.targetSelector.a(3, new EntityShulker.c(this));
    }

    protected boolean playStepSound() {
        return false;
    }

    public SoundCategory bC() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEffect G() {
        return SoundEffects.ff;
    }

    public void D() {
        if (!this.dk()) {
            super.D();
        }

    }

    protected SoundEffect bW() {
        return SoundEffects.fj;
    }

    protected SoundEffect bV() {
        return this.dk() ? SoundEffects.fl : SoundEffects.fk;
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityShulker.a, EnumDirection.DOWN);
        this.datawatcher.register(EntityShulker.b, Optional.absent());
        this.datawatcher.register(EntityShulker.c, Byte.valueOf((byte) 0));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
    }

    protected EntityAIBodyControl s() {
        return new EntityShulker.b(this);
    }

    public static void b(DataConverterManager dataconvertermanager) {
        EntityInsentient.a(dataconvertermanager, "Shulker");
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.set(EntityShulker.a, EnumDirection.fromType1(nbttagcompound.getByte("AttachFace")));
        this.datawatcher.set(EntityShulker.c, Byte.valueOf(nbttagcompound.getByte("Peek")));
        if (nbttagcompound.hasKey("APX")) {
            int i = nbttagcompound.getInt("APX");
            int j = nbttagcompound.getInt("APY");
            int k = nbttagcompound.getInt("APZ");

            this.datawatcher.set(EntityShulker.b, Optional.of(new BlockPosition(i, j, k)));
        } else {
            this.datawatcher.set(EntityShulker.b, Optional.absent());
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setByte("AttachFace", (byte) ((EnumDirection) this.datawatcher.get(EntityShulker.a)).a());
        nbttagcompound.setByte("Peek", ((Byte) this.datawatcher.get(EntityShulker.c)).byteValue());
        BlockPosition blockposition = this.df();

        if (blockposition != null) {
            nbttagcompound.setInt("APX", blockposition.getX());
            nbttagcompound.setInt("APY", blockposition.getY());
            nbttagcompound.setInt("APZ", blockposition.getZ());
        }

    }

    public void m() {
        super.m();
        BlockPosition blockposition = (BlockPosition) ((Optional) this.datawatcher.get(EntityShulker.b)).orNull();

        if (blockposition == null && !this.world.isClientSide) {
            blockposition = new BlockPosition(this);
            this.datawatcher.set(EntityShulker.b, Optional.of(blockposition));
        }

        float f;

        if (this.isPassenger()) {
            blockposition = null;
            f = this.bB().yaw;
            this.yaw = f;
            this.aO = f;
            this.aP = f;
            this.bC = 0;
        } else if (!this.world.isClientSide) {
            IBlockData iblockdata = this.world.getType(blockposition);

            if (iblockdata.getMaterial() != Material.AIR) {
                EnumDirection enumdirection;

                if (iblockdata.getBlock() == Blocks.PISTON_EXTENSION) {
                    enumdirection = (EnumDirection) iblockdata.get(BlockPiston.FACING);
                    blockposition = blockposition.shift(enumdirection);
                    this.datawatcher.set(EntityShulker.b, Optional.of(blockposition));
                } else if (iblockdata.getBlock() == Blocks.PISTON_HEAD) {
                    enumdirection = (EnumDirection) iblockdata.get(BlockPistonExtension.FACING);
                    blockposition = blockposition.shift(enumdirection);
                    this.datawatcher.set(EntityShulker.b, Optional.of(blockposition));
                } else {
                    this.o();
                }
            }

            BlockPosition blockposition1 = blockposition.shift(this.de());

            if (!this.world.d(blockposition1, false)) {
                boolean flag = false;
                EnumDirection[] aenumdirection = EnumDirection.values();
                int i = aenumdirection.length;

                for (int j = 0; j < i; ++j) {
                    EnumDirection enumdirection1 = aenumdirection[j];

                    blockposition1 = blockposition.shift(enumdirection1);
                    if (this.world.d(blockposition1, false)) {
                        this.datawatcher.set(EntityShulker.a, enumdirection1);
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    this.o();
                }
            }

            BlockPosition blockposition2 = blockposition.shift(this.de().opposite());

            if (this.world.d(blockposition2, false)) {
                this.o();
            }
        }

        f = (float) this.dg() * 0.01F;
        this.bz = this.bA;
        if (this.bA > f) {
            this.bA = MathHelper.a(this.bA - 0.05F, f, 1.0F);
        } else if (this.bA < f) {
            this.bA = MathHelper.a(this.bA + 0.05F, 0.0F, f);
        }

        if (blockposition != null) {
            if (this.world.isClientSide) {
                if (this.bC > 0 && this.bB != null) {
                    --this.bC;
                } else {
                    this.bB = blockposition;
                }
            }

            this.locX = (double) blockposition.getX() + 0.5D;
            this.locY = (double) blockposition.getY();
            this.locZ = (double) blockposition.getZ() + 0.5D;
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.M = this.locX;
            this.N = this.locY;
            this.O = this.locZ;
            double d0 = 0.5D - (double) MathHelper.sin((0.5F + this.bA) * 3.1415927F) * 0.5D;
            double d1 = 0.5D - (double) MathHelper.sin((0.5F + this.bz) * 3.1415927F) * 0.5D;
            double d2 = d0 - d1;
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            EnumDirection enumdirection2 = this.de();

            switch (EntityShulker.SyntheticClass_1.a[enumdirection2.ordinal()]) {
            case 1:
                this.a(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D + d0, this.locZ + 0.5D));
                d4 = d2;
                break;

            case 2:
                this.a(new AxisAlignedBB(this.locX - 0.5D, this.locY - d0, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D));
                d4 = -d2;
                break;

            case 3:
                this.a(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D + d0));
                d5 = d2;
                break;

            case 4:
                this.a(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D - d0, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D));
                d5 = -d2;
                break;

            case 5:
                this.a(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D + d0, this.locY + 1.0D, this.locZ + 0.5D));
                d3 = d2;
                break;

            case 6:
                this.a(new AxisAlignedBB(this.locX - 0.5D - d0, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D));
                d3 = -d2;
            }

            if (d2 > 0.0D) {
                List list = this.world.getEntities(this, this.getBoundingBox());

                if (!list.isEmpty()) {
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        Entity entity = (Entity) iterator.next();

                        if (!(entity instanceof EntityShulker) && !entity.noclip) {
                            entity.move(d3, d4, d5);
                        }
                    }
                }
            }
        }

    }

    public void setPosition(double d0, double d1, double d2) {
        super.setPosition(d0, d1, d2);
        if (this.datawatcher != null && this.ticksLived != 0) {
            Optional optional = (Optional) this.datawatcher.get(EntityShulker.b);
            Optional optional1 = Optional.of(new BlockPosition(d0, d1, d2));

            if (!optional1.equals(optional)) {
                this.datawatcher.set(EntityShulker.b, optional1);
                this.datawatcher.set(EntityShulker.c, Byte.valueOf((byte) 0));
                this.impulse = true;
            }

        }
    }

    protected boolean o() {
        if (!this.hasAI() && this.isAlive()) {
            BlockPosition blockposition = new BlockPosition(this);

            for (int i = 0; i < 5; ++i) {
                BlockPosition blockposition1 = blockposition.a(8 - this.random.nextInt(17), 8 - this.random.nextInt(17), 8 - this.random.nextInt(17));

                if (blockposition1.getY() > 0 && this.world.isEmpty(blockposition1) && this.world.a(this.world.getWorldBorder(), (Entity) this) && this.world.getCubes(this, new AxisAlignedBB(blockposition1)).isEmpty()) {
                    boolean flag = false;
                    EnumDirection[] aenumdirection = EnumDirection.values();
                    int j = aenumdirection.length;

                    for (int k = 0; k < j; ++k) {
                        EnumDirection enumdirection = aenumdirection[k];

                        if (this.world.d(blockposition1.shift(enumdirection), false)) {
                            this.datawatcher.set(EntityShulker.a, enumdirection);
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        this.a(SoundEffects.fo, 1.0F, 1.0F);
                        this.datawatcher.set(EntityShulker.b, Optional.of(blockposition1));
                        this.datawatcher.set(EntityShulker.c, Byte.valueOf((byte) 0));
                        this.setGoalTarget((EntityLiving) null);
                        return true;
                    }
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public void n() {
        super.n();
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.aP = 180.0F;
        this.aO = 180.0F;
        this.yaw = 180.0F;
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (EntityShulker.b.equals(datawatcherobject) && this.world.isClientSide && !this.isPassenger()) {
            BlockPosition blockposition = this.df();

            if (blockposition != null) {
                if (this.bB == null) {
                    this.bB = blockposition;
                } else {
                    this.bC = 6;
                }

                this.locX = (double) blockposition.getX() + 0.5D;
                this.locY = (double) blockposition.getY();
                this.locZ = (double) blockposition.getZ() + 0.5D;
                this.lastX = this.locX;
                this.lastY = this.locY;
                this.lastZ = this.locZ;
                this.M = this.locX;
                this.N = this.locY;
                this.O = this.locZ;
            }
        }

        super.a(datawatcherobject);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.dk()) {
            Entity entity = damagesource.i();

            if (entity instanceof EntityArrow) {
                return false;
            }
        }

        if (super.damageEntity(damagesource, f)) {
            if ((double) this.getHealth() < (double) this.getMaxHealth() * 0.5D && this.random.nextInt(4) == 0) {
                this.o();
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean dk() {
        return this.dg() == 0;
    }

    @Nullable
    public AxisAlignedBB ag() {
        return this.isAlive() ? this.getBoundingBox() : null;
    }

    public EnumDirection de() {
        return (EnumDirection) this.datawatcher.get(EntityShulker.a);
    }

    @Nullable
    public BlockPosition df() {
        return (BlockPosition) ((Optional) this.datawatcher.get(EntityShulker.b)).orNull();
    }

    public void g(@Nullable BlockPosition blockposition) {
        this.datawatcher.set(EntityShulker.b, Optional.fromNullable(blockposition));
    }

    public int dg() {
        return ((Byte) this.datawatcher.get(EntityShulker.c)).byteValue();
    }

    public void a(int i) {
        if (!this.world.isClientSide) {
            this.getAttributeInstance(GenericAttributes.g).c(EntityShulker.by);
            if (i == 0) {
                this.getAttributeInstance(GenericAttributes.g).b(EntityShulker.by);
                this.a(SoundEffects.fi, 1.0F, 1.0F);
            } else {
                this.a(SoundEffects.fm, 1.0F, 1.0F);
            }
        }

        this.datawatcher.set(EntityShulker.c, Byte.valueOf((byte) i));
    }

    public float getHeadHeight() {
        return 0.5F;
    }

    public int N() {
        return 180;
    }

    public int cJ() {
        return 180;
    }

    public void collide(Entity entity) {}

    public float aA() {
        return 0.0F;
    }

    @Nullable
    protected MinecraftKey J() {
        return LootTables.y;
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.UP.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                EntityShulker.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }

    static class c extends PathfinderGoalNearestAttackableTarget<EntityLiving> {

        public c(EntityShulker entityshulker) {
            super(entityshulker, EntityLiving.class, 10, true, false, new Predicate() {
                public boolean a(@Nullable EntityLiving entityliving) {
                    return entityliving instanceof IMonster;
                }

                public boolean apply(Object object) {
                    return this.a((EntityLiving) object);
                }
            });
        }

        public boolean a() {
            return this.e.aQ() == null ? false : super.a();
        }

        protected AxisAlignedBB a(double d0) {
            EnumDirection enumdirection = ((EntityShulker) this.e).de();

            return enumdirection.k() == EnumDirection.EnumAxis.X ? this.e.getBoundingBox().grow(4.0D, d0, d0) : (enumdirection.k() == EnumDirection.EnumAxis.Z ? this.e.getBoundingBox().grow(d0, d0, 4.0D) : this.e.getBoundingBox().grow(d0, 4.0D, d0));
        }
    }

    class d extends PathfinderGoalNearestAttackableTarget<EntityHuman> {

        public d(EntityShulker entityshulker) {
            super(entityshulker, EntityHuman.class, true);
        }

        public boolean a() {
            return EntityShulker.this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : super.a();
        }

        protected AxisAlignedBB a(double d0) {
            EnumDirection enumdirection = ((EntityShulker) this.e).de();

            return enumdirection.k() == EnumDirection.EnumAxis.X ? this.e.getBoundingBox().grow(4.0D, d0, d0) : (enumdirection.k() == EnumDirection.EnumAxis.Z ? this.e.getBoundingBox().grow(d0, d0, 4.0D) : this.e.getBoundingBox().grow(d0, 4.0D, d0));
        }
    }

    class a extends PathfinderGoal {

        private int b;

        public a() {
            this.a(3);
        }

        public boolean a() {
            EntityLiving entityliving = EntityShulker.this.getGoalTarget();

            return entityliving != null && entityliving.isAlive() ? EntityShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL : false;
        }

        public void c() {
            this.b = 20;
            EntityShulker.this.a(100);
        }

        public void d() {
            EntityShulker.this.a(0);
        }

        public void e() {
            if (EntityShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                --this.b;
                EntityLiving entityliving = EntityShulker.this.getGoalTarget();

                EntityShulker.this.getControllerLook().a(entityliving, 180.0F, 180.0F);
                double d0 = EntityShulker.this.h(entityliving);

                if (d0 < 400.0D) {
                    if (this.b <= 0) {
                        this.b = 20 + EntityShulker.this.random.nextInt(10) * 20 / 2;
                        EntityShulkerBullet entityshulkerbullet = new EntityShulkerBullet(EntityShulker.this.world, EntityShulker.this, entityliving, EntityShulker.this.de().k());

                        EntityShulker.this.world.addEntity(entityshulkerbullet);
                        EntityShulker.this.a(SoundEffects.fn, 2.0F, (EntityShulker.this.random.nextFloat() - EntityShulker.this.random.nextFloat()) * 0.2F + 1.0F);
                    }
                } else {
                    EntityShulker.this.setGoalTarget((EntityLiving) null);
                }

                super.e();
            }
        }
    }

    class e extends PathfinderGoal {

        private int b;

        private e() {}

        public boolean a() {
            return EntityShulker.this.getGoalTarget() == null && EntityShulker.this.random.nextInt(40) == 0;
        }

        public boolean b() {
            return EntityShulker.this.getGoalTarget() == null && this.b > 0;
        }

        public void c() {
            this.b = 20 * (1 + EntityShulker.this.random.nextInt(3));
            EntityShulker.this.a(30);
        }

        public void d() {
            if (EntityShulker.this.getGoalTarget() == null) {
                EntityShulker.this.a(0);
            }

        }

        public void e() {
            --this.b;
        }

        e(EntityShulker.SyntheticClass_1 entityshulker_syntheticclass_1) {
            this();
        }
    }

    class b extends EntityAIBodyControl {

        public b(EntityLiving entityliving) {
            super(entityliving);
        }

        public void a() {}
    }
}

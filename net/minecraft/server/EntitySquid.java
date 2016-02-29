package net.minecraft.server;

public class EntitySquid extends EntityWaterAnimal {

    public float a;
    public float b;
    public float c;
    public float bt;
    public float bu;
    public float bv;
    public float bw;
    public float bx;
    private float by;
    private float bz;
    private float bA;
    private float bB;
    private float bC;
    private float bD;

    public EntitySquid(World world) {
        super(world);
        this.setSize(0.8F, 0.8F);
        this.random.setSeed((long) (1 + this.getId()));
        this.bz = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    protected void r() {
        this.goalSelector.a(0, new EntitySquid.PathfinderGoalSquid(this));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
    }

    public float getHeadHeight() {
        return this.length * 0.5F;
    }

    protected SoundEffect G() {
        return SoundEffects.fW;
    }

    protected SoundEffect bR() {
        return SoundEffects.fY;
    }

    protected SoundEffect bS() {
        return SoundEffects.fX;
    }

    protected float cd() {
        return 0.4F;
    }

    protected boolean playStepSound() {
        return false;
    }

    protected MinecraftKey J() {
        return LootTables.af;
    }

    public boolean isInWater() {
        return super.isInWater();
    }

    public void n() {
        super.n();
        this.b = this.a;
        this.bt = this.c;
        this.bv = this.bu;
        this.bx = this.bw;
        this.bu += this.bz;
        if ((double) this.bu > 6.283185307179586D) {
            if (this.world.isClientSide) {
                this.bu = 6.2831855F;
            } else {
                this.bu = (float) ((double) this.bu - 6.283185307179586D);
                if (this.random.nextInt(10) == 0) {
                    this.bz = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.broadcastEntityEffect(this, (byte) 19);
            }
        }

        if (this.inWater) {
            float f;

            if (this.bu < 3.1415927F) {
                f = this.bu / 3.1415927F;
                this.bw = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double) f > 0.75D) {
                    this.by = 1.0F;
                    this.bA = 1.0F;
                } else {
                    this.bA *= 0.8F;
                }
            } else {
                this.bw = 0.0F;
                this.by *= 0.9F;
                this.bA *= 0.99F;
            }

            if (!this.world.isClientSide) {
                this.motX = (double) (this.bB * this.by);
                this.motY = (double) (this.bC * this.by);
                this.motZ = (double) (this.bD * this.by);
            }

            f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.aM += (-((float) MathHelper.b(this.motX, this.motZ)) * 57.295776F - this.aM) * 0.1F;
            this.yaw = this.aM;
            this.c = (float) ((double) this.c + 3.141592653589793D * (double) this.bA * 1.5D);
            this.a += (-((float) MathHelper.b((double) f, this.motY)) * 57.295776F - this.a) * 0.1F;
        } else {
            this.bw = MathHelper.e(MathHelper.sin(this.bu)) * 3.1415927F * 0.25F;
            if (!this.world.isClientSide) {
                this.motX = 0.0D;
                this.motZ = 0.0D;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    this.motY += 0.05D * (double) (this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motY;
                } else {
                    this.motY -= 0.08D;
                }

                this.motY *= 0.9800000190734863D;
            }

            this.a = (float) ((double) this.a + (double) (-90.0F - this.a) * 0.02D);
        }

    }

    public void g(float f, float f1) {
        this.move(this.motX, this.motY, this.motZ);
    }

    public boolean cF() {
        return this.locY > 45.0D && this.locY < (double) this.world.K() && super.cF();
    }

    public void b(float f, float f1, float f2) {
        this.bB = f;
        this.bC = f1;
        this.bD = f2;
    }

    public boolean o() {
        return this.bB != 0.0F || this.bC != 0.0F || this.bD != 0.0F;
    }

    static class PathfinderGoalSquid extends PathfinderGoal {

        private EntitySquid a;

        public PathfinderGoalSquid(EntitySquid entitysquid) {
            this.a = entitysquid;
        }

        public boolean a() {
            return true;
        }

        public void e() {
            int i = this.a.bK();

            if (i > 100) {
                this.a.b(0.0F, 0.0F, 0.0F);
            } else if (this.a.getRandom().nextInt(50) == 0 || !this.a.inWater || !this.a.o()) {
                float f = this.a.getRandom().nextFloat() * 6.2831855F;
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.a.getRandom().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;

                this.a.b(f1, f2, f3);
            }

        }
    }
}

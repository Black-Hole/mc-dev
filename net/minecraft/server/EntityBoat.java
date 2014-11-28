package net.minecraft.server;

import java.util.List;

public class EntityBoat extends Entity {

    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;

    public EntityBoat(World world) {
        super(world);
        this.a = true;
        this.b = 0.07D;
        this.k = true;
        this.a(1.5F, 0.6F);
    }

    protected boolean r_() {
        return false;
    }

    protected void h() {
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Float(0.0F));
    }

    public AxisAlignedBB j(Entity entity) {
        return entity.getBoundingBox();
    }

    public AxisAlignedBB S() {
        return this.getBoundingBox();
    }

    public boolean ae() {
        return true;
    }

    public EntityBoat(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
    }

    public double an() {
        return (double) this.length * 0.0D - 0.30000001192092896D;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (!this.world.isStatic && !this.dead) {
            if (this.passenger != null && this.passenger == damagesource.getEntity() && damagesource instanceof EntityDamageSourceIndirect) {
                return false;
            } else {
                this.b(-this.m());
                this.a(10);
                this.setDamage(this.j() + f * 10.0F);
                this.ac();
                boolean flag = damagesource.getEntity() instanceof EntityHuman && ((EntityHuman) damagesource.getEntity()).abilities.canInstantlyBuild;

                if (flag || this.j() > 40.0F) {
                    if (this.passenger != null) {
                        this.passenger.mount(this);
                    }

                    if (!flag) {
                        this.a(Items.BOAT, 1, 0.0F);
                    }

                    this.die();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public boolean ad() {
        return !this.dead;
    }

    public void s_() {
        super.s_();
        if (this.l() > 0) {
            this.a(this.l() - 1);
        }

        if (this.j() > 0.0F) {
            this.setDamage(this.j() - 1.0F);
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        byte b0 = 5;
        double d0 = 0.0D;

        for (int i = 0; i < b0; ++i) {
            double d1 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double) (i + 0) / (double) b0 - 0.125D;
            double d2 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double) (i + 1) / (double) b0 - 0.125D;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.getBoundingBox().a, d1, this.getBoundingBox().c, this.getBoundingBox().d, d2, this.getBoundingBox().f);

            if (this.world.b(axisalignedbb, Material.WATER)) {
                d0 += 1.0D / (double) b0;
            }
        }

        double d3 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        double d4;
        double d5;
        int j;

        if (d3 > 0.2975D) {
            d4 = Math.cos((double) this.yaw * 3.141592653589793D / 180.0D);
            d5 = Math.sin((double) this.yaw * 3.141592653589793D / 180.0D);

            for (j = 0; (double) j < 1.0D + d3 * 60.0D; ++j) {
                double d6 = (double) (this.random.nextFloat() * 2.0F - 1.0F);
                double d7 = (double) (this.random.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;

                if (this.random.nextBoolean()) {
                    d8 = this.locX - d4 * d6 * 0.8D + d5 * d7;
                    d9 = this.locZ - d5 * d6 * 0.8D - d4 * d7;
                    this.world.addParticle(EnumParticle.WATER_SPLASH, d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ, new int[0]);
                } else {
                    d8 = this.locX + d4 + d5 * d6 * 0.7D;
                    d9 = this.locZ + d5 - d4 * d6 * 0.7D;
                    this.world.addParticle(EnumParticle.WATER_SPLASH, d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ, new int[0]);
                }
            }
        }

        double d10;
        double d11;

        if (this.world.isStatic && this.a) {
            if (this.c > 0) {
                d4 = this.locX + (this.d - this.locX) / (double) this.c;
                d5 = this.locY + (this.e - this.locY) / (double) this.c;
                d10 = this.locZ + (this.f - this.locZ) / (double) this.c;
                d11 = MathHelper.g(this.g - (double) this.yaw);
                this.yaw = (float) ((double) this.yaw + d11 / (double) this.c);
                this.pitch = (float) ((double) this.pitch + (this.h - (double) this.pitch) / (double) this.c);
                --this.c;
                this.setPosition(d4, d5, d10);
                this.setYawPitch(this.yaw, this.pitch);
            } else {
                d4 = this.locX + this.motX;
                d5 = this.locY + this.motY;
                d10 = this.locZ + this.motZ;
                this.setPosition(d4, d5, d10);
                if (this.onGround) {
                    this.motX *= 0.5D;
                    this.motY *= 0.5D;
                    this.motZ *= 0.5D;
                }

                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }

        } else {
            if (d0 < 1.0D) {
                d4 = d0 * 2.0D - 1.0D;
                this.motY += 0.03999999910593033D * d4;
            } else {
                if (this.motY < 0.0D) {
                    this.motY /= 2.0D;
                }

                this.motY += 0.007000000216066837D;
            }

            if (this.passenger instanceof EntityLiving) {
                EntityLiving entityliving = (EntityLiving) this.passenger;
                float f = this.passenger.yaw + -entityliving.aX * 90.0F;

                this.motX += -Math.sin((double) (f * 3.1415927F / 180.0F)) * this.b * (double) entityliving.aY * 0.05000000074505806D;
                this.motZ += Math.cos((double) (f * 3.1415927F / 180.0F)) * this.b * (double) entityliving.aY * 0.05000000074505806D;
            }

            d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d4 > 0.35D) {
                d5 = 0.35D / d4;
                this.motX *= d5;
                this.motZ *= d5;
                d4 = 0.35D;
            }

            if (d4 > d3 && this.b < 0.35D) {
                this.b += (0.35D - this.b) / 35.0D;
                if (this.b > 0.35D) {
                    this.b = 0.35D;
                }
            } else {
                this.b -= (this.b - 0.07D) / 35.0D;
                if (this.b < 0.07D) {
                    this.b = 0.07D;
                }
            }

            int k;

            for (k = 0; k < 4; ++k) {
                int l = MathHelper.floor(this.locX + ((double) (k % 2) - 0.5D) * 0.8D);

                j = MathHelper.floor(this.locZ + ((double) (k / 2) - 0.5D) * 0.8D);

                for (int i1 = 0; i1 < 2; ++i1) {
                    int j1 = MathHelper.floor(this.locY) + i1;
                    BlockPosition blockposition = new BlockPosition(l, j1, j);
                    Block block = this.world.getType(blockposition).getBlock();

                    if (block == Blocks.SNOW_LAYER) {
                        this.world.setAir(blockposition);
                        this.positionChanged = false;
                    } else if (block == Blocks.WATERLILY) {
                        this.world.setAir(blockposition, true);
                        this.positionChanged = false;
                    }
                }
            }

            if (this.onGround) {
                this.motX *= 0.5D;
                this.motY *= 0.5D;
                this.motZ *= 0.5D;
            }

            this.move(this.motX, this.motY, this.motZ);
            if (this.positionChanged && d3 > 0.2D) {
                if (!this.world.isStatic && !this.dead) {
                    this.die();

                    for (k = 0; k < 3; ++k) {
                        this.a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
                    }

                    for (k = 0; k < 2; ++k) {
                        this.a(Items.STICK, 1, 0.0F);
                    }
                }
            } else {
                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }

            this.pitch = 0.0F;
            d5 = (double) this.yaw;
            d10 = this.lastX - this.locX;
            d11 = this.lastZ - this.locZ;
            if (d10 * d10 + d11 * d11 > 0.001D) {
                d5 = (double) ((float) (Math.atan2(d11, d10) * 180.0D / 3.141592653589793D));
            }

            double d12 = MathHelper.g(d5 - (double) this.yaw);

            if (d12 > 20.0D) {
                d12 = 20.0D;
            }

            if (d12 < -20.0D) {
                d12 = -20.0D;
            }

            this.yaw = (float) ((double) this.yaw + d12);
            this.setYawPitch(this.yaw, this.pitch);
            if (!this.world.isStatic) {
                List list = this.world.getEntities(this, this.getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty()) {
                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        Entity entity = (Entity) list.get(k1);

                        if (entity != this.passenger && entity.ae() && entity instanceof EntityBoat) {
                            entity.collide(this);
                        }
                    }
                }

                if (this.passenger != null && this.passenger.dead) {
                    this.passenger = null;
                }

            }
        }
    }

    public void al() {
        if (this.passenger != null) {
            double d0 = Math.cos((double) this.yaw * 3.141592653589793D / 180.0D) * 0.4D;
            double d1 = Math.sin((double) this.yaw * 3.141592653589793D / 180.0D) * 0.4D;

            this.passenger.setPosition(this.locX + d0, this.locY + this.an() + this.passenger.am(), this.locZ + d1);
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {}

    protected void a(NBTTagCompound nbttagcompound) {}

    public boolean e(EntityHuman entityhuman) {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityhuman) {
            return true;
        } else {
            if (!this.world.isStatic) {
                entityhuman.mount(this);
            }

            return true;
        }
    }

    protected void a(double d0, boolean flag, Block block, BlockPosition blockposition) {
        if (flag) {
            if (this.fallDistance > 3.0F) {
                this.e(this.fallDistance, 1.0F);
                if (!this.world.isStatic && !this.dead) {
                    this.die();

                    int i;

                    for (i = 0; i < 3; ++i) {
                        this.a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
                    }

                    for (i = 0; i < 2; ++i) {
                        this.a(Items.STICK, 1, 0.0F);
                    }
                }

                this.fallDistance = 0.0F;
            }
        } else if (this.world.getType((new BlockPosition(this)).down()).getBlock().getMaterial() != Material.WATER && d0 < 0.0D) {
            this.fallDistance = (float) ((double) this.fallDistance - d0);
        }

    }

    public void setDamage(float f) {
        this.datawatcher.watch(19, Float.valueOf(f));
    }

    public float j() {
        return this.datawatcher.getFloat(19);
    }

    public void a(int i) {
        this.datawatcher.watch(17, Integer.valueOf(i));
    }

    public int l() {
        return this.datawatcher.getInt(17);
    }

    public void b(int i) {
        this.datawatcher.watch(18, Integer.valueOf(i));
    }

    public int m() {
        return this.datawatcher.getInt(18);
    }
}

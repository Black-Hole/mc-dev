package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

public class EntityPig extends EntityAnimal {

    private static final DataWatcherObject<Boolean> bv = DataWatcher.a(EntityPig.class, DataWatcherRegistry.h);
    private static final Set<Item> bw = Sets.newHashSet(new Item[] { Items.CARROT, Items.POTATO, Items.BEETROOT});
    private boolean bx;
    private int bz;
    private int bA;

    public EntityPig(World world) {
        super(world);
        this.setSize(0.9F, 0.9F);
    }

    protected void r() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_ON_A_STICK, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, false, EntityPig.bw));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    public Entity bt() {
        return this.bu().isEmpty() ? null : (Entity) this.bu().get(0);
    }

    public boolean cK() {
        Entity entity = this.bt();

        if (!(entity instanceof EntityHuman)) {
            return false;
        } else {
            EntityHuman entityhuman = (EntityHuman) entity;
            ItemStack itemstack = entityhuman.getItemInMainHand();

            if (itemstack != null && itemstack.getItem() == Items.CARROT_ON_A_STICK) {
                return true;
            } else {
                itemstack = entityhuman.getItemInOffHand();
                return itemstack != null && itemstack.getItem() == Items.CARROT_ON_A_STICK;
            }
        }
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityPig.bv, Boolean.valueOf(false));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", this.hasSaddle());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSaddle(nbttagcompound.getBoolean("Saddle"));
    }

    protected SoundEffect G() {
        return SoundEffects.dP;
    }

    protected SoundEffect bR() {
        return SoundEffects.dR;
    }

    protected SoundEffect bS() {
        return SoundEffects.dQ;
    }

    protected void a(BlockPosition blockposition, Block block) {
        this.a(SoundEffects.dT, 0.15F, 1.0F);
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
        if (!super.a(entityhuman, enumhand, itemstack)) {
            if (this.hasSaddle() && !this.world.isClientSide && !this.isVehicle()) {
                entityhuman.startRiding(this);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    protected void dropEquipment(boolean flag, int i) {
        super.dropEquipment(flag, i);
        if (this.hasSaddle()) {
            this.a(Items.SADDLE, 1);
        }

    }

    protected MinecraftKey J() {
        return LootTables.C;
    }

    public boolean hasSaddle() {
        return ((Boolean) this.datawatcher.get(EntityPig.bv)).booleanValue();
    }

    public void setSaddle(boolean flag) {
        if (flag) {
            this.datawatcher.set(EntityPig.bv, Boolean.valueOf(true));
        } else {
            this.datawatcher.set(EntityPig.bv, Boolean.valueOf(false));
        }

    }

    public void onLightningStrike(EntityLightning entitylightning) {
        if (!this.world.isClientSide && !this.dead) {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);

            entitypigzombie.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            entitypigzombie.m(this.cR());
            if (this.hasCustomName()) {
                entitypigzombie.setCustomName(this.getCustomName());
                entitypigzombie.setCustomNameVisible(this.getCustomNameVisible());
            }

            this.world.addEntity(entitypigzombie);
            this.die();
        }
    }

    public void e(float f, float f1) {
        super.e(f, f1);
        if (f > 5.0F) {
            Iterator iterator = this.b(EntityHuman.class).iterator();

            while (iterator.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator.next();

                entityhuman.b((Statistic) AchievementList.u);
            }
        }

    }

    public void g(float f, float f1) {
        Entity entity = this.bu().isEmpty() ? null : (Entity) this.bu().get(0);

        if (this.isVehicle() && this.cK()) {
            this.lastYaw = this.yaw = entity.yaw;
            this.pitch = entity.pitch * 0.5F;
            this.setYawPitch(this.yaw, this.pitch);
            this.aO = this.aM = this.yaw;
            this.P = 1.0F;
            this.aQ = this.ck() * 0.1F;
            if (this.bx()) {
                float f2 = (float) this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue() * 0.225F;

                if (this.bx) {
                    if (this.bz++ > this.bA) {
                        this.bx = false;
                    }

                    f2 += f2 * 1.15F * MathHelper.sin((float) this.bz / (float) this.bA * 3.1415927F);
                }

                this.l(f2);
                super.g(0.0F, 1.0F);
            } else {
                this.motX = 0.0D;
                this.motY = 0.0D;
                this.motZ = 0.0D;
            }

            this.aE = this.aF;
            double d0 = this.locX - this.lastX;
            double d1 = this.locZ - this.lastZ;
            float f3 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;

            if (f3 > 1.0F) {
                f3 = 1.0F;
            }

            this.aF += (f3 - this.aF) * 0.4F;
            this.aG += this.aF;
        } else {
            this.P = 0.5F;
            this.aQ = 0.02F;
            super.g(f, f1);
        }
    }

    public boolean da() {
        if (this.bx) {
            return false;
        } else {
            this.bx = true;
            this.bz = 0;
            this.bA = this.getRandom().nextInt(841) + 140;
            return true;
        }
    }

    public EntityPig b(EntityAgeable entityageable) {
        return new EntityPig(this.world);
    }

    public boolean e(ItemStack itemstack) {
        return itemstack != null && EntityPig.bw.contains(itemstack.getItem());
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}

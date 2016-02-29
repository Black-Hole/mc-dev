package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Set;

public class EntityChicken extends EntityAnimal {

    private static final Set<Item> bD = Sets.newHashSet(new Item[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS});
    public float bv;
    public float bw;
    public float bx;
    public float bz;
    public float bA = 1.0F;
    public int bB;
    public boolean bC;

    public EntityChicken(World world) {
        super(world);
        this.setSize(0.4F, 0.7F);
        this.bB = this.random.nextInt(6000) + 6000;
        this.a(PathType.WATER, 0.0F);
    }

    protected void r() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.4D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D, false, EntityChicken.bD));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }

    public float getHeadHeight() {
        return this.length;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    public void n() {
        super.n();
        this.bz = this.bv;
        this.bx = this.bw;
        this.bw = (float) ((double) this.bw + (double) (this.onGround ? -1 : 4) * 0.3D);
        this.bw = MathHelper.a(this.bw, 0.0F, 1.0F);
        if (!this.onGround && this.bA < 1.0F) {
            this.bA = 1.0F;
        }

        this.bA = (float) ((double) this.bA * 0.9D);
        if (!this.onGround && this.motY < 0.0D) {
            this.motY *= 0.6D;
        }

        this.bv += this.bA * 2.0F;
        if (!this.world.isClientSide && !this.isBaby() && !this.isChickenJockey() && --this.bB <= 0) {
            this.a(SoundEffects.aa, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.a(Items.EGG, 1);
            this.bB = this.random.nextInt(6000) + 6000;
        }

    }

    public void e(float f, float f1) {}

    protected SoundEffect G() {
        return SoundEffects.Y;
    }

    protected SoundEffect bR() {
        return SoundEffects.ab;
    }

    protected SoundEffect bS() {
        return SoundEffects.Z;
    }

    protected void a(BlockPosition blockposition, Block block) {
        this.a(SoundEffects.ac, 0.15F, 1.0F);
    }

    protected MinecraftKey J() {
        return LootTables.B;
    }

    public EntityChicken b(EntityAgeable entityageable) {
        return new EntityChicken(this.world);
    }

    public boolean e(ItemStack itemstack) {
        return itemstack != null && EntityChicken.bD.contains(itemstack.getItem());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bC = nbttagcompound.getBoolean("IsChickenJockey");
        if (nbttagcompound.hasKey("EggLayTime")) {
            this.bB = nbttagcompound.getInt("EggLayTime");
        }

    }

    protected int getExpValue(EntityHuman entityhuman) {
        return this.isChickenJockey() ? 10 : super.getExpValue(entityhuman);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("IsChickenJockey", this.bC);
        nbttagcompound.setInt("EggLayTime", this.bB);
    }

    protected boolean isTypeNotPersistent() {
        return this.isChickenJockey() && !this.isVehicle();
    }

    public void k(Entity entity) {
        super.k(entity);
        float f = MathHelper.sin(this.aM * 0.017453292F);
        float f1 = MathHelper.cos(this.aM * 0.017453292F);
        float f2 = 0.1F;
        float f3 = 0.0F;

        entity.setPosition(this.locX + (double) (f2 * f), this.locY + (double) (this.length * 0.5F) + entity.ax() + (double) f3, this.locZ - (double) (f2 * f1));
        if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).aM = this.aM;
        }

    }

    public boolean isChickenJockey() {
        return this.bC;
    }

    public void o(boolean flag) {
        this.bC = flag;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}

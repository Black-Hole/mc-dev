package net.minecraft.server;

import com.google.common.base.Predicate;

public class EntityOcelot extends EntityTameableAnimal {

    private static final DataWatcherObject<Integer> bz = DataWatcher.a(EntityOcelot.class, DataWatcherRegistry.b);
    private PathfinderGoalAvoidTarget<EntityHuman> bA;
    private PathfinderGoalTempt bB;

    public EntityOcelot(World world) {
        super(world);
        this.setSize(0.6F, 0.7F);
    }

    protected void r() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.goalSit = new PathfinderGoalSit(this));
        this.goalSelector.a(3, this.bB = new PathfinderGoalTempt(this, 0.6D, Items.FISH, true));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 0.8D));
        this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
        this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
        this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.8D));
        this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.8D));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
        this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, false, (Predicate) null));
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityOcelot.bz, Integer.valueOf(0));
    }

    public void M() {
        if (this.getControllerMove().a()) {
            double d0 = this.getControllerMove().b();

            if (d0 == 0.6D) {
                this.setSneaking(true);
                this.setSprinting(false);
            } else if (d0 == 1.33D) {
                this.setSneaking(false);
                this.setSprinting(true);
            } else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        } else {
            this.setSneaking(false);
            this.setSprinting(false);
        }

    }

    protected boolean isTypeNotPersistent() {
        return !this.isTamed() && this.ticksLived > 2400;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
    }

    public void e(float f, float f1) {}

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("CatType", this.getCatType());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setCatType(nbttagcompound.getInt("CatType"));
    }

    protected SoundEffect G() {
        return this.isTamed() ? (this.isInLove() ? SoundEffects.T : (this.random.nextInt(4) == 0 ? SoundEffects.U : SoundEffects.P)) : null;
    }

    protected SoundEffect bR() {
        return SoundEffects.S;
    }

    protected SoundEffect bS() {
        return SoundEffects.Q;
    }

    protected float cd() {
        return 0.4F;
    }

    public boolean B(Entity entity) {
        return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            if (this.goalSit != null) {
                this.goalSit.setSitting(false);
            }

            return super.damageEntity(damagesource, f);
        }
    }

    protected MinecraftKey J() {
        return LootTables.J;
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
        if (this.isTamed()) {
            if (this.d((EntityLiving) entityhuman) && !this.world.isClientSide && !this.e(itemstack)) {
                this.goalSit.setSitting(!this.isSitting());
            }
        } else if ((this.bB == null || this.bB.f()) && itemstack != null && itemstack.getItem() == Items.FISH && entityhuman.h(this) < 9.0D) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                --itemstack.count;
            }

            if (!this.world.isClientSide) {
                if (this.random.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setCatType(1 + this.world.random.nextInt(3));
                    this.setOwnerUUID(entityhuman.getUniqueID());
                    this.o(true);
                    this.goalSit.setSitting(true);
                    this.world.broadcastEntityEffect(this, (byte) 7);
                } else {
                    this.o(false);
                    this.world.broadcastEntityEffect(this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityhuman, enumhand, itemstack);
    }

    public EntityOcelot b(EntityAgeable entityageable) {
        EntityOcelot entityocelot = new EntityOcelot(this.world);

        if (this.isTamed()) {
            entityocelot.setOwnerUUID(this.getOwnerUUID());
            entityocelot.setTamed(true);
            entityocelot.setCatType(this.getCatType());
        }

        return entityocelot;
    }

    public boolean e(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.FISH;
    }

    public boolean mate(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(entityanimal instanceof EntityOcelot)) {
            return false;
        } else {
            EntityOcelot entityocelot = (EntityOcelot) entityanimal;

            return !entityocelot.isTamed() ? false : this.isInLove() && entityocelot.isInLove();
        }
    }

    public int getCatType() {
        return ((Integer) this.datawatcher.get(EntityOcelot.bz)).intValue();
    }

    public void setCatType(int i) {
        this.datawatcher.set(EntityOcelot.bz, Integer.valueOf(i));
    }

    public boolean cF() {
        return this.world.random.nextInt(3) != 0;
    }

    public boolean canSpawn() {
        if (this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox())) {
            BlockPosition blockposition = new BlockPosition(this.locX, this.getBoundingBox().b, this.locZ);

            if (blockposition.getY() < this.world.K()) {
                return false;
            }

            IBlockData iblockdata = this.world.getType(blockposition.down());
            Block block = iblockdata.getBlock();

            if (block == Blocks.GRASS || iblockdata.getMaterial() == Material.LEAVES) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return this.hasCustomName() ? this.getCustomName() : (this.isTamed() ? LocaleI18n.get("entity.Cat.name") : super.getName());
    }

    public void setTamed(boolean flag) {
        super.setTamed(flag);
    }

    protected void da() {
        if (this.bA == null) {
            this.bA = new PathfinderGoalAvoidTarget(this, EntityHuman.class, 16.0F, 0.8D, 1.33D);
        }

        this.goalSelector.a((PathfinderGoal) this.bA);
        if (!this.isTamed()) {
            this.goalSelector.a(4, this.bA);
        }

    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
        if (this.world.random.nextInt(7) == 0) {
            for (int i = 0; i < 2; ++i) {
                EntityOcelot entityocelot = new EntityOcelot(this.world);

                entityocelot.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
                entityocelot.setAgeRaw(-24000);
                this.world.addEntity(entityocelot);
            }
        }

        return groupdataentity;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}

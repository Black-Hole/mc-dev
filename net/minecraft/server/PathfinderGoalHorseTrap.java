package net.minecraft.server;

public class PathfinderGoalHorseTrap extends PathfinderGoal {

    private final EntityHorse a;

    public PathfinderGoalHorseTrap(EntityHorse entityhorse) {
        this.a = entityhorse;
    }

    public boolean a() {
        return this.a.world.isPlayerNearby(this.a.locX, this.a.locY, this.a.locZ, 10.0D);
    }

    public void e() {
        DifficultyDamageScaler difficultydamagescaler = this.a.world.D(new BlockPosition(this.a));

        this.a.x(false);
        this.a.setType(EnumHorseType.SKELETON);
        this.a.setTame(true);
        this.a.setAgeRaw(0);
        this.a.world.strikeLightning(new EntityLightning(this.a.world, this.a.locX, this.a.locY, this.a.locZ, true));
        EntitySkeleton entityskeleton = this.a(difficultydamagescaler, this.a);

        entityskeleton.startRiding(this.a);

        for (int i = 0; i < 3; ++i) {
            EntityHorse entityhorse = this.a(difficultydamagescaler);
            EntitySkeleton entityskeleton1 = this.a(difficultydamagescaler, entityhorse);

            entityskeleton1.startRiding(entityhorse);
            entityhorse.g(this.a.getRandom().nextGaussian() * 0.5D, 0.0D, this.a.getRandom().nextGaussian() * 0.5D);
        }

    }

    private EntityHorse a(DifficultyDamageScaler difficultydamagescaler) {
        EntityHorse entityhorse = new EntityHorse(this.a.world);

        entityhorse.prepare(difficultydamagescaler, (GroupDataEntity) null);
        entityhorse.setPosition(this.a.locX, this.a.locY, this.a.locZ);
        entityhorse.noDamageTicks = 60;
        entityhorse.cL();
        entityhorse.setType(EnumHorseType.SKELETON);
        entityhorse.setTame(true);
        entityhorse.setAgeRaw(0);
        entityhorse.world.addEntity(entityhorse);
        return entityhorse;
    }

    private EntitySkeleton a(DifficultyDamageScaler difficultydamagescaler, EntityHorse entityhorse) {
        EntitySkeleton entityskeleton = new EntitySkeleton(entityhorse.world);

        entityskeleton.prepare(difficultydamagescaler, (GroupDataEntity) null);
        entityskeleton.setPosition(entityhorse.locX, entityhorse.locY, entityhorse.locZ);
        entityskeleton.noDamageTicks = 60;
        entityskeleton.cL();
        if (entityskeleton.getEquipment(EnumItemSlot.HEAD) == null) {
            entityskeleton.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        }

        EnchantmentManager.a(entityskeleton.getRandom(), entityskeleton.getItemInMainHand(), (int) (5.0F + difficultydamagescaler.c() * (float) entityskeleton.getRandom().nextInt(18)), false);
        EnchantmentManager.a(entityskeleton.getRandom(), entityskeleton.getEquipment(EnumItemSlot.HEAD), (int) (5.0F + difficultydamagescaler.c() * (float) entityskeleton.getRandom().nextInt(18)), false);
        entityskeleton.world.addEntity(entityskeleton);
        return entityskeleton;
    }
}

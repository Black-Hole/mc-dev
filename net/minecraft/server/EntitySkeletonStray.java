package net.minecraft.server;

public class EntitySkeletonStray extends EntitySkeletonAbstract {

    public EntitySkeletonStray(EntityTypes<? extends EntitySkeletonStray> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    public boolean a(GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn) {
        return super.a(generatoraccess, enummobspawn) && (enummobspawn == EnumMobSpawn.SPAWNER || generatoraccess.f(new BlockPosition(this)));
    }

    @Override
    protected SoundEffect getSoundAmbient() {
        return SoundEffects.ENTITY_STRAY_AMBIENT;
    }

    @Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return SoundEffects.ENTITY_STRAY_HURT;
    }

    @Override
    protected SoundEffect getSoundDeath() {
        return SoundEffects.ENTITY_STRAY_DEATH;
    }

    @Override
    SoundEffect l() {
        return SoundEffects.ENTITY_STRAY_STEP;
    }

    @Override
    protected EntityArrow b(ItemStack itemstack, float f) {
        EntityArrow entityarrow = super.b(itemstack, f);

        if (entityarrow instanceof EntityTippedArrow) {
            ((EntityTippedArrow) entityarrow).a(new MobEffect(MobEffects.SLOWER_MOVEMENT, 600));
        }

        return entityarrow;
    }
}

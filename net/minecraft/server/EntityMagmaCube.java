package net.minecraft.server;

import java.util.Random;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(EntityTypes<? extends EntityMagmaCube> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public static boolean b(EntityTypes<EntityMagmaCube> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, Random random) {
        return generatoraccess.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean a(IWorldReader iworldreader) {
        return iworldreader.i(this) && !iworldreader.containsLiquid(this.getBoundingBox());
    }

    @Override
    public void setSize(int i, boolean flag) {
        super.setSize(i, flag);
        this.getAttributeInstance(GenericAttributes.ARMOR).setValue((double) (i * 3));
    }

    @Override
    public float aF() {
        return 1.0F;
    }

    @Override
    protected ParticleParam l() {
        return Particles.FLAME;
    }

    @Override
    protected MinecraftKey getDefaultLootTable() {
        return this.eb() ? LootTables.a : this.getEntityType().g();
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    protected int dU() {
        return super.dU() * 4;
    }

    @Override
    protected void dV() {
        this.b *= 0.9F;
    }

    @Override
    protected void jump() {
        Vec3D vec3d = this.getMot();

        this.setMot(vec3d.x, (double) (0.42F + (float) this.getSize() * 0.1F), vec3d.z);
        this.impulse = true;
    }

    @Override
    protected void c(Tag<FluidType> tag) {
        if (tag == TagsFluid.LAVA) {
            Vec3D vec3d = this.getMot();

            this.setMot(vec3d.x, (double) (0.22F + (float) this.getSize() * 0.05F), vec3d.z);
            this.impulse = true;
        } else {
            super.c(tag);
        }

    }

    @Override
    public void b(float f, float f1) {}

    @Override
    protected boolean dW() {
        return this.df();
    }

    @Override
    protected int dX() {
        return super.dX() + 2;
    }

    @Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return this.eb() ? SoundEffects.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_HURT;
    }

    @Override
    protected SoundEffect getSoundDeath() {
        return this.eb() ? SoundEffects.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_DEATH;
    }

    @Override
    protected SoundEffect getSoundSquish() {
        return this.eb() ? SoundEffects.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_SQUISH;
    }

    @Override
    protected SoundEffect getSoundJump() {
        return SoundEffects.ENTITY_MAGMA_CUBE_JUMP;
    }
}

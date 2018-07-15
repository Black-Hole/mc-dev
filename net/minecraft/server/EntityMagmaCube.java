package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(EntityTypes.MAGMA_CUBE, world);
        this.fireProof = true;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public boolean a(GeneratorAccess generatoraccess) {
        return generatoraccess.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean a(IWorldReader iworldreader) {
        return iworldreader.b(this, this.getBoundingBox()) && iworldreader.getCubes(this, this.getBoundingBox()) && !iworldreader.containsLiquid(this.getBoundingBox());
    }

    protected void setSize(int i, boolean flag) {
        super.setSize(i, flag);
        this.getAttributeInstance(GenericAttributes.h).setValue((double) (i * 3));
    }

    public float az() {
        return 1.0F;
    }

    protected ParticleParam l() {
        return Particles.y;
    }

    @Nullable
    protected MinecraftKey G() {
        return this.dy() ? LootTables.a : LootTables.ap;
    }

    public boolean isBurning() {
        return false;
    }

    protected int dr() {
        return super.dr() * 4;
    }

    protected void ds() {
        this.a *= 0.9F;
    }

    protected void cG() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    protected void c(Tag<FluidType> tag) {
        if (tag == TagsFluid.b) {
            this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
            this.impulse = true;
        } else {
            super.c(tag);
        }

    }

    public void c(float f, float f1) {}

    protected boolean dt() {
        return true;
    }

    protected int du() {
        return super.du() + 2;
    }

    protected SoundEffect d(DamageSource damagesource) {
        return this.dy() ? SoundEffects.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_HURT;
    }

    protected SoundEffect cr() {
        return this.dy() ? SoundEffects.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_DEATH;
    }

    protected SoundEffect dv() {
        return this.dy() ? SoundEffects.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEffects.ENTITY_MAGMA_CUBE_SQUISH;
    }

    protected SoundEffect dw() {
        return SoundEffects.ENTITY_MAGMA_CUBE_JUMP;
    }
}

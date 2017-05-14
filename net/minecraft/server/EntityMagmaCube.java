package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.fireProof = true;
    }

    public static void a(DataConverterManager dataconvertermanager) {
        EntityInsentient.a(dataconvertermanager, EntityMagmaCube.class);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public boolean P() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    protected void setSize(int i, boolean flag) {
        super.setSize(i, flag);
        this.getAttributeInstance(GenericAttributes.h).setValue((double) (i * 3));
    }

    public float f(float f) {
        return 1.0F;
    }

    protected EnumParticle p() {
        return EnumParticle.FLAME;
    }

    protected EntitySlime dc() {
        return new EntityMagmaCube(this.world);
    }

    @Nullable
    protected MinecraftKey J() {
        return this.dk() ? LootTables.a : LootTables.ai;
    }

    public boolean isBurning() {
        return false;
    }

    protected int dd() {
        return super.dd() * 4;
    }

    protected void de() {
        this.a *= 0.9F;
    }

    protected void cs() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    protected void cu() {
        this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
        this.impulse = true;
    }

    public void e(float f, float f1) {}

    protected boolean df() {
        return true;
    }

    protected int dg() {
        return super.dg() + 2;
    }

    protected SoundEffect d(DamageSource damagesource) {
        return this.dk() ? SoundEffects.hj : SoundEffects.dW;
    }

    protected SoundEffect cd() {
        return this.dk() ? SoundEffects.hi : SoundEffects.dV;
    }

    protected SoundEffect dh() {
        return this.dk() ? SoundEffects.hk : SoundEffects.dY;
    }

    protected SoundEffect di() {
        return SoundEffects.dX;
    }
}

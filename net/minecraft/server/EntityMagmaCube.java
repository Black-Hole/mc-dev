package net.minecraft.server;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.fireProof = true;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public boolean cF() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    protected void setSize(int i) {
        super.setSize(i);
        this.getAttributeInstance(GenericAttributes.g).setValue((double) (i * 3));
    }

    public float e(float f) {
        return 1.0F;
    }

    protected EnumParticle o() {
        return EnumParticle.FLAME;
    }

    protected EntitySlime cT() {
        return new EntityMagmaCube(this.world);
    }

    protected MinecraftKey J() {
        return !this.db() ? LootTables.ad : LootTables.a;
    }

    public boolean isBurning() {
        return false;
    }

    protected int cU() {
        return super.cU() * 4;
    }

    protected void cV() {
        this.a *= 0.9F;
    }

    protected void ch() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    protected void cj() {
        this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
        this.impulse = true;
    }

    public void e(float f, float f1) {}

    protected boolean cW() {
        return true;
    }

    protected int cX() {
        return super.cX() + 2;
    }

    protected SoundEffect bR() {
        return this.db() ? SoundEffects.fA : SoundEffects.dk;
    }

    protected SoundEffect bS() {
        return this.db() ? SoundEffects.fz : SoundEffects.dj;
    }

    protected SoundEffect cY() {
        return this.db() ? SoundEffects.fB : SoundEffects.dm;
    }

    protected SoundEffect cZ() {
        return SoundEffects.dl;
    }
}

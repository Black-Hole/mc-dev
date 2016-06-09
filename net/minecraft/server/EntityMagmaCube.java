package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.fireProof = true;
    }

    public static void b(DataConverterManager dataconvertermanager) {
        EntityInsentient.a(dataconvertermanager, "LavaSlime");
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public boolean cK() {
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

    protected EntitySlime cY() {
        return new EntityMagmaCube(this.world);
    }

    @Nullable
    protected MinecraftKey J() {
        return this.dg() ? LootTables.a : LootTables.af;
    }

    public boolean isBurning() {
        return false;
    }

    protected int cZ() {
        return super.cZ() * 4;
    }

    protected void da() {
        this.a *= 0.9F;
    }

    protected void cl() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    protected void cn() {
        this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
        this.impulse = true;
    }

    public void e(float f, float f1) {}

    protected boolean db() {
        return true;
    }

    protected int dc() {
        return super.dc() + 2;
    }

    protected SoundEffect bV() {
        return this.dg() ? SoundEffects.fM : SoundEffects.dq;
    }

    protected SoundEffect bW() {
        return this.dg() ? SoundEffects.fL : SoundEffects.dp;
    }

    protected SoundEffect dd() {
        return this.dg() ? SoundEffects.fN : SoundEffects.ds;
    }

    protected SoundEffect de() {
        return SoundEffects.dr;
    }
}

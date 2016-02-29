package net.minecraft.server;

public abstract class EntityGolem extends EntityCreature implements IAnimal {

    public EntityGolem(World world) {
        super(world);
    }

    public void e(float f, float f1) {}

    protected SoundEffect G() {
        return null;
    }

    protected SoundEffect bR() {
        return null;
    }

    protected SoundEffect bS() {
        return null;
    }

    public int C() {
        return 120;
    }

    protected boolean isTypeNotPersistent() {
        return false;
    }
}

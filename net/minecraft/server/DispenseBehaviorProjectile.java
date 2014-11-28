package net.minecraft.server;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem {

    public DispenseBehaviorProjectile() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        World world = isourceblock.i();
        IPosition iposition = BlockDispenser.a(isourceblock);
        EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
        IProjectile iprojectile = this.a(world, iposition);

        iprojectile.shoot((double) enumdirection.getAdjacentX(), (double) ((float) enumdirection.getAdjacentY() + 0.1F), (double) enumdirection.getAdjacentZ(), this.b(), this.a());
        world.addEntity((Entity) iprojectile);
        itemstack.a(1);
        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        isourceblock.i().triggerEffect(1002, isourceblock.getBlockPosition(), 0);
    }

    protected abstract IProjectile a(World world, IPosition iposition);

    protected float a() {
        return 6.0F;
    }

    protected float b() {
        return 1.1F;
    }
}

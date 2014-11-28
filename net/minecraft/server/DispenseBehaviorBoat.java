package net.minecraft.server;

final class DispenseBehaviorBoat extends DispenseBehaviorItem {

    private final DispenseBehaviorItem b = new DispenseBehaviorItem();

    DispenseBehaviorBoat() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
        World world = isourceblock.i();
        double d0 = isourceblock.getX() + (double) ((float) enumdirection.getAdjacentX() * 1.125F);
        double d1 = isourceblock.getY() + (double) ((float) enumdirection.getAdjacentY() * 1.125F);
        double d2 = isourceblock.getZ() + (double) ((float) enumdirection.getAdjacentZ() * 1.125F);
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
        Material material = world.getType(blockposition).getBlock().getMaterial();
        double d3;

        if (Material.WATER.equals(material)) {
            d3 = 1.0D;
        } else {
            if (!Material.AIR.equals(material) || !Material.WATER.equals(world.getType(blockposition.down()).getBlock().getMaterial())) {
                return this.b.a(isourceblock, itemstack);
            }

            d3 = 0.0D;
        }

        EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);

        world.addEntity(entityboat);
        itemstack.a(1);
        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        isourceblock.i().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
    }
}

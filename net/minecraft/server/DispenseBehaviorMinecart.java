package net.minecraft.server;

final class DispenseBehaviorMinecart extends DispenseBehaviorItem {

    private final DispenseBehaviorItem b = new DispenseBehaviorItem();

    DispenseBehaviorMinecart() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
        World world = isourceblock.i();
        double d0 = isourceblock.getX() + (double) enumdirection.getAdjacentX() * 1.125D;
        double d1 = Math.floor(isourceblock.getY()) + (double) enumdirection.getAdjacentY();
        double d2 = isourceblock.getZ() + (double) enumdirection.getAdjacentZ() * 1.125D;
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
        IBlockData iblockdata = world.getType(blockposition);
        EnumTrackPosition enumtrackposition = iblockdata.getBlock() instanceof BlockMinecartTrackAbstract ? (EnumTrackPosition) iblockdata.get(((BlockMinecartTrackAbstract) iblockdata.getBlock()).l()) : EnumTrackPosition.NORTH_SOUTH;
        double d3;

        if (BlockMinecartTrackAbstract.d(iblockdata)) {
            if (enumtrackposition.c()) {
                d3 = 0.6D;
            } else {
                d3 = 0.1D;
            }
        } else {
            if (iblockdata.getBlock().getMaterial() != Material.AIR || !BlockMinecartTrackAbstract.d(world.getType(blockposition.down()))) {
                return this.b.a(isourceblock, itemstack);
            }

            IBlockData iblockdata1 = world.getType(blockposition.down());
            EnumTrackPosition enumtrackposition1 = iblockdata1.getBlock() instanceof BlockMinecartTrackAbstract ? (EnumTrackPosition) iblockdata1.get(((BlockMinecartTrackAbstract) iblockdata1.getBlock()).l()) : EnumTrackPosition.NORTH_SOUTH;

            if (enumdirection != EnumDirection.DOWN && enumtrackposition1.c()) {
                d3 = -0.4D;
            } else {
                d3 = -0.9D;
            }
        }

        EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, d0, d1 + d3, d2, ItemMinecart.a((ItemMinecart) itemstack.getItem()));

        if (itemstack.hasName()) {
            entityminecartabstract.setCustomName(itemstack.getName());
        }

        world.addEntity(entityminecartabstract);
        itemstack.a(1);
        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        isourceblock.i().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
    }
}

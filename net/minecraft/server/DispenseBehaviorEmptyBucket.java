package net.minecraft.server;

final class DispenseBehaviorEmptyBucket extends DispenseBehaviorItem {

    private final DispenseBehaviorItem b = new DispenseBehaviorItem();

    DispenseBehaviorEmptyBucket() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        World world = isourceblock.i();
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
        IBlockData iblockdata = world.getType(blockposition);
        Block block = iblockdata.getBlock();
        Material material = block.getMaterial();
        Item item;

        if (Material.WATER.equals(material) && block instanceof BlockFluids && ((Integer) iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
            item = Items.WATER_BUCKET;
        } else {
            if (!Material.LAVA.equals(material) || !(block instanceof BlockFluids) || ((Integer) iblockdata.get(BlockFluids.LEVEL)).intValue() != 0) {
                return super.b(isourceblock, itemstack);
            }

            item = Items.LAVA_BUCKET;
        }

        world.setAir(blockposition);
        if (--itemstack.count == 0) {
            itemstack.setItem(item);
            itemstack.count = 1;
        } else if (((TileEntityDispenser) isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
            this.b.a(isourceblock, new ItemStack(item));
        }

        return itemstack;
    }
}

package net.minecraft.server;

final class DispenseBehaviorFlintAndSteel extends DispenseBehaviorItem {

    private boolean b = true;

    DispenseBehaviorFlintAndSteel() {}

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        World world = isourceblock.i();
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));

        if (world.isEmpty(blockposition)) {
            world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
            if (itemstack.isDamaged(1, world.random)) {
                itemstack.count = 0;
            }
        } else if (world.getType(blockposition).getBlock() == Blocks.TNT) {
            Blocks.TNT.postBreak(world, blockposition, Blocks.TNT.getBlockData().set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
            world.setAir(blockposition);
        } else {
            this.b = false;
        }

        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        if (this.b) {
            isourceblock.i().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
        } else {
            isourceblock.i().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
        }

    }
}

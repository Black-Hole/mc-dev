package net.minecraft.server;

final class DispenseBehaviorBonemeal extends DispenseBehaviorItem {

    private boolean b = true;

    DispenseBehaviorBonemeal() {}

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        if (EnumColor.WHITE == EnumColor.fromInvColorIndex(itemstack.getData())) {
            World world = isourceblock.i();
            BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));

            if (ItemDye.a(itemstack, world, blockposition)) {
                if (!world.isStatic) {
                    world.triggerEffect(2005, blockposition, 0);
                }
            } else {
                this.b = false;
            }

            return itemstack;
        } else {
            return super.b(isourceblock, itemstack);
        }
    }

    protected void a(ISourceBlock isourceblock) {
        if (this.b) {
            isourceblock.i().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
        } else {
            isourceblock.i().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
        }

    }
}

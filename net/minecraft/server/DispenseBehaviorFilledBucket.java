package net.minecraft.server;

final class DispenseBehaviorFilledBucket extends DispenseBehaviorItem {

    private final DispenseBehaviorItem b = new DispenseBehaviorItem();

    DispenseBehaviorFilledBucket() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        ItemBucket itembucket = (ItemBucket) itemstack.getItem();
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));

        if (itembucket.a(isourceblock.i(), blockposition)) {
            itemstack.setItem(Items.BUCKET);
            itemstack.count = 1;
            return itemstack;
        } else {
            return this.b.a(isourceblock, itemstack);
        }
    }
}

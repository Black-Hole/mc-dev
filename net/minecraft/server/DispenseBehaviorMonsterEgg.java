package net.minecraft.server;

final class DispenseBehaviorMonsterEgg extends DispenseBehaviorItem {

    DispenseBehaviorMonsterEgg() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
        double d0 = isourceblock.getX() + (double) enumdirection.getAdjacentX();
        double d1 = (double) ((float) isourceblock.getBlockPosition().getY() + 0.2F);
        double d2 = isourceblock.getZ() + (double) enumdirection.getAdjacentZ();
        Entity entity = ItemMonsterEgg.a(isourceblock.i(), itemstack.getData(), d0, d1, d2);

        if (entity instanceof EntityLiving && itemstack.hasName()) {
            ((EntityInsentient) entity).setCustomName(itemstack.getName());
        }

        itemstack.a(1);
        return itemstack;
    }
}

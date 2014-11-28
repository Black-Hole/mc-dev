package net.minecraft.server;

import java.util.Random;

final class DispenseBehaviorFireball extends DispenseBehaviorItem {

    DispenseBehaviorFireball() {}

    public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
        IPosition iposition = BlockDispenser.a(isourceblock);
        double d0 = iposition.getX() + (double) ((float) enumdirection.getAdjacentX() * 0.3F);
        double d1 = iposition.getY() + (double) ((float) enumdirection.getAdjacentX() * 0.3F);
        double d2 = iposition.getZ() + (double) ((float) enumdirection.getAdjacentZ() * 0.3F);
        World world = isourceblock.i();
        Random random = world.random;
        double d3 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentX();
        double d4 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentY();
        double d5 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentZ();

        world.addEntity(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
        itemstack.a(1);
        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        isourceblock.i().triggerEffect(1009, isourceblock.getBlockPosition(), 0);
    }
}

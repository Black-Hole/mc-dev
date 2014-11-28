package net.minecraft.server;

final class DispenseBehaviorTNT extends DispenseBehaviorItem {

    DispenseBehaviorTNT() {}

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        World world = isourceblock.i();
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) blockposition.getX() + 0.5D, (double) blockposition.getY(), (double) blockposition.getZ() + 0.5D, (EntityLiving) null);

        world.addEntity(entitytntprimed);
        world.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
        --itemstack.count;
        return itemstack;
    }
}

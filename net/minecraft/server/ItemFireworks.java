package net.minecraft.server;

public class ItemFireworks extends Item {

    public ItemFireworks() {}

    public EnumInteractionResult a(EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        if (!world.isClientSide) {
            ItemStack itemstack = entityhuman.b(enumhand);
            EntityFireworks entityfireworks = new EntityFireworks(world, (double) ((float) blockposition.getX() + f), (double) ((float) blockposition.getY() + f1), (double) ((float) blockposition.getZ() + f2), itemstack);

            world.addEntity(entityfireworks);
            if (!entityhuman.abilities.canInstantlyBuild) {
                itemstack.subtract(1);
            }
        }

        return EnumInteractionResult.SUCCESS;
    }
}

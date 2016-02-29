package net.minecraft.server;

public class ItemCarrotStick extends Item {

    public ItemCarrotStick() {
        this.a(CreativeModeTab.e);
        this.d(1);
        this.setMaxDurability(25);
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        if (entityhuman.isPassenger() && entityhuman.by() instanceof EntityPig) {
            EntityPig entitypig = (EntityPig) entityhuman.by();

            if (itemstack.j() - itemstack.getData() >= 7 && entitypig.da()) {
                itemstack.damage(7, entityhuman);
                if (itemstack.count == 0) {
                    ItemStack itemstack1 = new ItemStack(Items.FISHING_ROD);

                    itemstack1.setTag(itemstack.getTag());
                    return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack1);
                }

                return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
            }
        }

        entityhuman.b(StatisticList.b((Item) this));
        return new InteractionResultWrapper(EnumInteractionResult.PASS, itemstack);
    }
}

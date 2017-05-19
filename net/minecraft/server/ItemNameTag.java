package net.minecraft.server;

public class ItemNameTag extends Item {

    public ItemNameTag() {
        this.b(CreativeModeTab.i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
        if (itemstack.hasName() && !(entityliving instanceof EntityHuman)) {
            entityliving.setCustomName(itemstack.getName());
            if (entityliving instanceof EntityInsentient) {
                ((EntityInsentient) entityliving).cU();
            }

            itemstack.subtract(1);
            return true;
        } else {
            return false;
        }
    }
}

package net.minecraft.server;

import java.util.Collection;

class RecipeTippedArrow implements IRecipe {

    private static final ItemStack[] a = new ItemStack[9];

    RecipeTippedArrow() {}

    public boolean a(InventoryCrafting inventorycrafting, World world) {
        if (inventorycrafting.i() == 3 && inventorycrafting.h() == 3) {
            for (int i = 0; i < inventorycrafting.i(); ++i) {
                for (int j = 0; j < inventorycrafting.h(); ++j) {
                    ItemStack itemstack = inventorycrafting.c(i, j);

                    if (itemstack == null) {
                        return false;
                    }

                    Item item = itemstack.getItem();

                    if (i == 1 && j == 1) {
                        if (item != Items.LINGERING_POTION) {
                            return false;
                        }
                    } else if (item != Items.ARROW) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public ItemStack craftItem(InventoryCrafting inventorycrafting) {
        ItemStack itemstack = inventorycrafting.c(1, 1);

        if (itemstack != null && itemstack.getItem() == Items.LINGERING_POTION) {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);

            PotionUtil.a(itemstack1, PotionUtil.c(itemstack));
            PotionUtil.a(itemstack1, (Collection) PotionUtil.b(itemstack));
            return itemstack1;
        } else {
            return null;
        }
    }

    public int a() {
        return 9;
    }

    public ItemStack b() {
        return null;
    }

    public ItemStack[] b(InventoryCrafting inventorycrafting) {
        return RecipeTippedArrow.a;
    }
}

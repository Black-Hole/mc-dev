package net.minecraft.server;

public interface IRecipe {

    boolean a(InventoryCrafting inventorycrafting, World world);

    ItemStack craftItem(InventoryCrafting inventorycrafting);

    ItemStack b();

    NonNullList<ItemStack> b(InventoryCrafting inventorycrafting);

    default boolean c() {
        return false;
    }
}

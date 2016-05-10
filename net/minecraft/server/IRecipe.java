package net.minecraft.server;

import javax.annotation.Nullable;

public interface IRecipe {

    boolean a(InventoryCrafting inventorycrafting, World world);

    @Nullable
    ItemStack craftItem(InventoryCrafting inventorycrafting);

    int a();

    @Nullable
    ItemStack b();

    ItemStack[] b(InventoryCrafting inventorycrafting);
}

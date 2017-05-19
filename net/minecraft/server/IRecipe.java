package net.minecraft.server;

public abstract class IRecipe implements Comparable<IRecipe> {

    protected String a;

    public IRecipe() {
        this("");
    }

    public IRecipe(String s) {
        this.a = s;
    }

    public abstract boolean a(InventoryCrafting inventorycrafting, World world);

    public abstract ItemStack craftItem(InventoryCrafting inventorycrafting);

    public abstract ItemStack b();

    public abstract NonNullList<ItemStack> b(InventoryCrafting inventorycrafting);

    public boolean c() {
        return false;
    }

    public int a(IRecipe irecipe) {
        return ((MinecraftKey) CraftingManager.recipes.b(this)).toString().compareTo(((MinecraftKey) CraftingManager.recipes.b(irecipe)).toString());
    }

    public int compareTo(Object object) {
        return this.a((IRecipe) object);
    }
}

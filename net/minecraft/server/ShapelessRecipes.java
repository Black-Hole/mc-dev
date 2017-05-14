package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ShapelessRecipes extends IRecipe {

    private final ItemStack result;
    private final NonNullList<RecipeItemStack> ingredients;

    public ShapelessRecipes(String s, ItemStack itemstack, NonNullList<RecipeItemStack> nonnulllist) {
        super(s);
        this.result = itemstack;
        this.ingredients = nonnulllist;
    }

    public ItemStack b() {
        return this.result;
    }

    public NonNullList<ItemStack> b(InventoryCrafting inventorycrafting) {
        NonNullList nonnulllist = NonNullList.a(inventorycrafting.getSize(), ItemStack.a);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inventorycrafting.getItem(i);

            if (itemstack.getItem().r()) {
                nonnulllist.set(i, new ItemStack(itemstack.getItem().q()));
            }
        }

        return nonnulllist;
    }

    public boolean a(InventoryCrafting inventorycrafting, World world) {
        ArrayList arraylist = Lists.newArrayList(this.ingredients);

        for (int i = 0; i < inventorycrafting.i(); ++i) {
            for (int j = 0; j < inventorycrafting.j(); ++j) {
                ItemStack itemstack = inventorycrafting.c(j, i);

                if (!itemstack.isEmpty()) {
                    boolean flag = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext()) {
                        RecipeItemStack recipeitemstack = (RecipeItemStack) iterator.next();

                        if (recipeitemstack.a(itemstack)) {
                            flag = true;
                            arraylist.remove(recipeitemstack);
                            break;
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    public ItemStack craftItem(InventoryCrafting inventorycrafting) {
        return this.result.cloneItemStack();
    }

    public static ShapelessRecipes a(JsonObject jsonobject) {
        String s = ChatDeserializer.a(jsonobject, "group", "");
        NonNullList nonnulllist = a(ChatDeserializer.u(jsonobject, "ingredients"));
        ItemStack itemstack = ShapedRecipes.a(ChatDeserializer.t(jsonobject, "result"), true);

        return new ShapelessRecipes(s, itemstack, nonnulllist);
    }

    private static NonNullList<RecipeItemStack> a(JsonArray jsonarray) {
        NonNullList nonnulllist = NonNullList.a();

        for (int i = 0; i < jsonarray.size(); ++i) {
            nonnulllist.add(ShapedRecipes.a(jsonarray.get(i)));
        }

        return nonnulllist;
    }
}

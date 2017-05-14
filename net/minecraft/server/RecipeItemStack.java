package net.minecraft.server;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;

public class RecipeItemStack implements Predicate<ItemStack> {

    public static final RecipeItemStack a = new RecipeItemStack(new ItemStack[0]) {
        public boolean a(@Nullable ItemStack itemstack) {
            return itemstack.isEmpty();
        }

        public boolean apply(@Nullable Object object) {
            return this.a((ItemStack) object);
        }
    };
    public final ItemStack[] choices;

    public RecipeItemStack(ItemStack... aitemstack) {
        this.choices = aitemstack;
    }

    public boolean a(@Nullable ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        } else {
            ItemStack[] aitemstack = this.choices;
            int i = aitemstack.length;

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack1 = aitemstack[j];

                if (itemstack1.getItem() == itemstack.getItem()) {
                    int k = itemstack1.getData();

                    if (k == 32767 || k == itemstack.getData()) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static RecipeItemStack a(Item item) {
        return a(new ItemStack[] { new ItemStack(item, 1, 32767)});
    }

    public static RecipeItemStack a(Item... aitem) {
        ItemStack[] aitemstack = new ItemStack[aitem.length];

        for (int i = 0; i < aitem.length; ++i) {
            aitemstack[i] = new ItemStack(aitem[i]);
        }

        return a(aitemstack);
    }

    public static RecipeItemStack a(ItemStack... aitemstack) {
        return aitemstack.length == 0 ? RecipeItemStack.a : new RecipeItemStack(aitemstack);
    }

    public boolean apply(@Nullable Object object) {
        return this.a((ItemStack) object);
    }
}

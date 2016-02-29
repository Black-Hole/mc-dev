package net.minecraft.server;

public class ContainerUtil {

    public static ItemStack a(ItemStack[] aitemstack, int i, int j) {
        if (i >= 0 && i < aitemstack.length && aitemstack[i] != null && j > 0) {
            ItemStack itemstack = aitemstack[i].cloneAndSubtract(j);

            if (aitemstack[i].count == 0) {
                aitemstack[i] = null;
            }

            return itemstack;
        } else {
            return null;
        }
    }

    public static ItemStack a(ItemStack[] aitemstack, int i) {
        if (i >= 0 && i < aitemstack.length) {
            ItemStack itemstack = aitemstack[i];

            aitemstack[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }
}

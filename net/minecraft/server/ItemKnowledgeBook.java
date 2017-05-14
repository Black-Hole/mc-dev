package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class ItemKnowledgeBook extends Item {

    public ItemKnowledgeBook() {
        this.d(1);
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand).cloneItemStack();

        entityhuman.a(enumhand, ItemStack.a);
        NBTTagCompound nbttagcompound = itemstack.getTag();

        if (nbttagcompound != null && this.b(nbttagcompound)) {
            NBTTagList nbttaglist = nbttagcompound.getList("Recipes", 8);
            ArrayList arraylist = Lists.newArrayList();

            for (int i = 0; i < nbttaglist.size(); ++i) {
                MinecraftKey minecraftkey = new MinecraftKey(nbttaglist.getString(i));

                if (this.b(minecraftkey)) {
                    arraylist.add(CraftingManager.a(minecraftkey));
                }
            }

            entityhuman.a((List) arraylist);
            entityhuman.b(StatisticList.b((Item) this));
            return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            return new InteractionResultWrapper(EnumInteractionResult.FAIL, itemstack);
        }
    }

    private boolean b(@Nullable NBTTagCompound nbttagcompound) {
        return nbttagcompound != null && nbttagcompound.hasKey("Recipes");
    }

    private boolean b(@Nullable MinecraftKey minecraftkey) {
        return minecraftkey != null && CraftingManager.a(minecraftkey) != null;
    }
}

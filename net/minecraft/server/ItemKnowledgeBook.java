package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemKnowledgeBook extends Item {

    private static final Logger a = LogManager.getLogger();

    public ItemKnowledgeBook() {
        this.d(1);
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        NBTTagCompound nbttagcompound = itemstack.getTag();

        if (!entityhuman.abilities.canInstantlyBuild) {
            entityhuman.a(enumhand, ItemStack.a);
        }

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Recipes", 9)) {
            if (!world.isClientSide) {
                NBTTagList nbttaglist = nbttagcompound.getList("Recipes", 8);
                ArrayList arraylist = Lists.newArrayList();

                for (int i = 0; i < nbttaglist.size(); ++i) {
                    IRecipe irecipe = CraftingManager.a(new MinecraftKey(nbttaglist.getString(i)));

                    if (irecipe != null) {
                        arraylist.add(irecipe);
                    }
                }

                entityhuman.a((List) arraylist);
                entityhuman.b(StatisticList.b((Item) this));
            }

            return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            return new InteractionResultWrapper(EnumInteractionResult.FAIL, itemstack);
        }
    }
}

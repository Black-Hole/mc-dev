package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeBookServer extends RecipeBook {

    private static final Logger e = LogManager.getLogger();

    public RecipeBookServer() {}

    public void a(List<IRecipe> list, EntityPlayer entityplayer) {
        ArrayList arraylist = Lists.newArrayList();
        ArrayList arraylist1 = Lists.newArrayList();
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            IRecipe irecipe = (IRecipe) iterator.next();

            if (this.a(irecipe)) {
                this.a.add(irecipe);
                arraylist.add(irecipe);
                arraylist1.add(irecipe);
                CriterionTriggers.f.a(entityplayer, irecipe);
            }
        }

        this.a(entityplayer, arraylist, arraylist1, 1);
    }

    public void b(List<IRecipe> list, EntityPlayer entityplayer) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            IRecipe irecipe = (IRecipe) iterator.next();

            this.b(irecipe);
            arraylist.add(irecipe);
        }

        this.a(entityplayer, arraylist, Lists.newArrayList(), 2);
    }

    private void a(EntityPlayer entityplayer, List<IRecipe> list, List<IRecipe> list1, int i) {
        entityplayer.playerConnection.sendPacket(new PacketPlayOutRecipes(list, list1, this.c, this.d, i));
    }

    public NBTTagCompound e() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.setBoolean("isGuiOpen", this.c);
        nbttagcompound.setBoolean("isFilteringCraftable", this.d);
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            IRecipe irecipe = (IRecipe) iterator.next();

            nbttaglist.add(new NBTTagString(((MinecraftKey) CraftingManager.recipes.b(irecipe)).toString()));
        }

        nbttagcompound.set("recipes", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        Iterator iterator1 = this.a.iterator();

        while (iterator1.hasNext()) {
            IRecipe irecipe1 = (IRecipe) iterator1.next();

            nbttaglist1.add(new NBTTagString(((MinecraftKey) CraftingManager.recipes.b(irecipe1)).toString()));
        }

        nbttagcompound.set("toBeDisplayed", nbttaglist1);
        return nbttagcompound;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.c = nbttagcompound.getBoolean("isGuiOpen");
        this.d = nbttagcompound.getBoolean("isFilteringCraftable");
        this.b.clear();
        NBTTagList nbttaglist = nbttagcompound.getList("recipes", 8);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            MinecraftKey minecraftkey = new MinecraftKey(nbttaglist.getString(i));

            if (CraftingManager.a(minecraftkey) == null) {
                RecipeBookServer.e.info("[ServerRecipeBook] Tried to load unrecognized recipe: " + minecraftkey + " removed now.");
            } else {
                this.b.add(CraftingManager.a(minecraftkey));
            }
        }

        NBTTagList nbttaglist1 = nbttagcompound.getList("toBeDisplayed", 8);

        for (int j = 0; j < nbttaglist1.size(); ++j) {
            MinecraftKey minecraftkey1 = new MinecraftKey(nbttaglist1.getString(j));

            if (CraftingManager.a(minecraftkey1) == null) {
                RecipeBookServer.e.info("[ServerRecipeBook] Tried to load unrecognized recipe: " + minecraftkey1 + " removed now.");
            } else {
                this.a.add(CraftingManager.a(minecraftkey1));
            }
        }

    }

    public void a(EntityPlayer entityplayer) {
        entityplayer.playerConnection.sendPacket(new PacketPlayOutRecipes(Lists.newArrayList(this.b), this.a, this.c, this.d, 3));
    }
}

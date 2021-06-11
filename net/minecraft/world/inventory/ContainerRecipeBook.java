package net.minecraft.world.inventory;

import net.minecraft.recipebook.AutoRecipe;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.item.crafting.IRecipe;

public abstract class ContainerRecipeBook<C extends IInventory> extends Container {

    public ContainerRecipeBook(Containers<?> containers, int i) {
        super(containers, i);
    }

    public void a(boolean flag, IRecipe<?> irecipe, EntityPlayer entityplayer) {
        (new AutoRecipe<>(this)).a(entityplayer, irecipe, flag);
    }

    public abstract void a(AutoRecipeStackManager autorecipestackmanager);

    public abstract void i();

    public abstract boolean a(IRecipe<? super C> irecipe);

    public abstract int j();

    public abstract int k();

    public abstract int l();

    public abstract int m();

    public abstract RecipeBookType q();

    public abstract boolean d(int i);
}

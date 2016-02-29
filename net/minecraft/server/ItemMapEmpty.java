package net.minecraft.server;

public class ItemMapEmpty extends ItemWorldMapBase {

    protected ItemMapEmpty() {
        this.a(CreativeModeTab.f);
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack1 = new ItemStack(Items.FILLED_MAP, 1, world.b("map"));
        String s = "map_" + itemstack1.getData();
        WorldMap worldmap = new WorldMap(s);

        world.a(s, (PersistentBase) worldmap);
        worldmap.scale = 0;
        worldmap.a(entityhuman.locX, entityhuman.locZ, worldmap.scale);
        worldmap.map = (byte) world.worldProvider.getDimensionManager().getDimensionID();
        worldmap.track = true;
        worldmap.c();
        --itemstack.count;
        if (itemstack.count <= 0) {
            return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack1);
        } else {
            if (!entityhuman.inventory.pickup(itemstack1.cloneItemStack())) {
                entityhuman.drop(itemstack1, false);
            }

            entityhuman.b(StatisticList.b((Item) this));
            return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
        }
    }
}

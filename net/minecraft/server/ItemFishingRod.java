package net.minecraft.server;

public class ItemFishingRod extends Item {

    public ItemFishingRod() {
        this.setMaxDurability(64);
        this.c(1);
        this.a(CreativeModeTab.i);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if (entityhuman.hookedFish != null) {
            int i = entityhuman.hookedFish.l();

            itemstack.damage(i, entityhuman);
            entityhuman.bv();
        } else {
            world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (ItemFishingRod.g.nextFloat() * 0.4F + 0.8F));
            if (!world.isStatic) {
                world.addEntity(new EntityFishingHook(world, entityhuman));
            }

            entityhuman.bv();
            entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        }

        return itemstack;
    }

    public boolean f_(ItemStack itemstack) {
        return super.f_(itemstack);
    }

    public int b() {
        return 1;
    }
}

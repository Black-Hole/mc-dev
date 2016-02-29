package net.minecraft.server;

public class ItemEnderPearl extends Item {

    public ItemEnderPearl() {
        this.maxStackSize = 16;
        this.a(CreativeModeTab.f);
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            --itemstack.count;
        }

        world.a((EntityHuman) null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.be, SoundCategory.NEUTRAL, 0.5F, 0.4F / (ItemEnderPearl.i.nextFloat() * 0.4F + 0.8F));
        entityhuman.da().a(this, 20);
        if (!world.isClientSide) {
            EntityEnderPearl entityenderpearl = new EntityEnderPearl(world, entityhuman);

            entityenderpearl.a(entityhuman, entityhuman.pitch, entityhuman.yaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(entityenderpearl);
        }

        entityhuman.b(StatisticList.b((Item) this));
        return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
    }
}

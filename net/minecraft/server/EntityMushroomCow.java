package net.minecraft.server;

public class EntityMushroomCow extends EntityCow {

    public EntityMushroomCow(World world) {
        super(world);
        this.setSize(0.9F, 1.4F);
        this.by = Blocks.MYCELIUM;
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() == Items.BOWL && this.getAge() >= 0 && !entityhuman.abilities.canInstantlyBuild) {
            if (--itemstack.count == 0) {
                entityhuman.a(enumhand, new ItemStack(Items.MUSHROOM_STEW));
            } else if (!entityhuman.inventory.pickup(new ItemStack(Items.MUSHROOM_STEW))) {
                entityhuman.drop(new ItemStack(Items.MUSHROOM_STEW), false);
            }

            return true;
        } else if (itemstack != null && itemstack.getItem() == Items.SHEARS && this.getAge() >= 0) {
            this.die();
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX, this.locY + (double) (this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
            if (!this.world.isClientSide) {
                EntityCow entitycow = new EntityCow(this.world);

                entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                entitycow.setHealth(this.getHealth());
                entitycow.aM = this.aM;
                if (this.hasCustomName()) {
                    entitycow.setCustomName(this.getCustomName());
                }

                this.world.addEntity(entitycow);

                for (int i = 0; i < 5; ++i) {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + (double) this.length, this.locZ, new ItemStack(Blocks.RED_MUSHROOM)));
                }

                itemstack.damage(1, entityhuman);
                this.a(SoundEffects.dw, 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.a(entityhuman, enumhand, itemstack);
        }
    }

    public EntityMushroomCow c(EntityAgeable entityageable) {
        return new EntityMushroomCow(this.world);
    }

    protected MinecraftKey J() {
        return LootTables.H;
    }

    public EntityCow b(EntityAgeable entityageable) {
        return this.c(entityageable);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.c(entityageable);
    }
}

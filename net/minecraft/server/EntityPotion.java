package net.minecraft.server;

import com.google.common.base.Optional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPotion extends EntityProjectile {

    private static final DataWatcherObject<Optional<ItemStack>> d = DataWatcher.a(EntityItem.class, DataWatcherRegistry.f);
    private static final Logger e = LogManager.getLogger();

    public EntityPotion(World world) {
        super(world);
    }

    public EntityPotion(World world, EntityLiving entityliving, ItemStack itemstack) {
        super(world, entityliving);
        this.setItem(itemstack);
    }

    public EntityPotion(World world, double d0, double d1, double d2, ItemStack itemstack) {
        super(world, d0, d1, d2);
        if (itemstack != null) {
            this.setItem(itemstack);
        }

    }

    protected void i() {
        this.getDataWatcher().register(EntityPotion.d, Optional.absent());
    }

    public ItemStack getItem() {
        ItemStack itemstack = (ItemStack) ((Optional) this.getDataWatcher().get(EntityPotion.d)).orNull();

        if (itemstack != null && (itemstack.getItem() == Items.SPLASH_POTION || itemstack.getItem() == Items.LINGERING_POTION)) {
            return itemstack;
        } else {
            if (this.world != null) {
                EntityPotion.e.error("ThrownPotion entity " + this.getId() + " has no item?!");
            }

            return new ItemStack(Items.SPLASH_POTION);
        }
    }

    public void setItem(ItemStack itemstack) {
        this.getDataWatcher().set(EntityPotion.d, Optional.fromNullable(itemstack));
        this.getDataWatcher().markDirty(EntityPotion.d);
    }

    protected float j() {
        return 0.05F;
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.world.isClientSide) {
            ItemStack itemstack = this.getItem();
            PotionRegistry potionregistry = PotionUtil.c(itemstack);
            List list = PotionUtil.getEffects(itemstack);
            Iterator iterator;

            if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK && potionregistry == Potions.b && list.isEmpty()) {
                BlockPosition blockposition = movingobjectposition.a().shift(movingobjectposition.direction);

                this.a(blockposition);
                iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumDirection enumdirection = (EnumDirection) iterator.next();

                    this.a(blockposition.shift(enumdirection));
                }

                this.world.triggerEffect(2002, new BlockPosition(this), PotionRegistry.a(potionregistry));
                this.die();
            } else {
                if (!list.isEmpty()) {
                    if (this.n()) {
                        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.locX, this.locY, this.locZ);

                        entityareaeffectcloud.a(this.getShooter());
                        entityareaeffectcloud.setRadius(3.0F);
                        entityareaeffectcloud.setRadiusOnUse(-0.5F);
                        entityareaeffectcloud.setWaitTime(10);
                        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());
                        entityareaeffectcloud.a(potionregistry);
                        iterator = PotionUtil.b(itemstack).iterator();

                        while (iterator.hasNext()) {
                            MobEffect mobeffect = (MobEffect) iterator.next();

                            entityareaeffectcloud.a(new MobEffect(mobeffect.getMobEffect(), mobeffect.getDuration(), mobeffect.getAmplifier()));
                        }

                        this.world.addEntity(entityareaeffectcloud);
                    } else {
                        AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
                        List list1 = this.world.a(EntityLiving.class, axisalignedbb);

                        if (!list1.isEmpty()) {
                            Iterator iterator1 = list1.iterator();

                            while (iterator1.hasNext()) {
                                EntityLiving entityliving = (EntityLiving) iterator1.next();

                                if (entityliving.cD()) {
                                    double d0 = this.h(entityliving);

                                    if (d0 < 16.0D) {
                                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                                        if (entityliving == movingobjectposition.entity) {
                                            d1 = 1.0D;
                                        }

                                        Iterator iterator2 = list.iterator();

                                        while (iterator2.hasNext()) {
                                            MobEffect mobeffect1 = (MobEffect) iterator2.next();
                                            MobEffectList mobeffectlist = mobeffect1.getMobEffect();

                                            if (mobeffectlist.isInstant()) {
                                                mobeffectlist.applyInstantEffect(this, this.getShooter(), entityliving, mobeffect1.getAmplifier(), d1);
                                            } else {
                                                int i = (int) (d1 * (double) mobeffect1.getDuration() + 0.5D);

                                                if (i > 20) {
                                                    entityliving.addEffect(new MobEffect(mobeffectlist, i, mobeffect1.getAmplifier()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                this.world.triggerEffect(2002, new BlockPosition(this), PotionRegistry.a(potionregistry));
                this.die();
            }
        }
    }

    private boolean n() {
        return this.getItem().getItem() == Items.LINGERING_POTION;
    }

    private void a(BlockPosition blockposition) {
        if (this.world.getType(blockposition).getBlock() == Blocks.FIRE) {
            this.world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("Potion"));

        if (itemstack == null) {
            this.die();
        } else {
            this.setItem(itemstack);
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        ItemStack itemstack = this.getItem();

        if (itemstack != null) {
            nbttagcompound.set("Potion", itemstack.save(new NBTTagCompound()));
        }

    }
}

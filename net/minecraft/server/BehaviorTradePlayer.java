package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public class BehaviorTradePlayer extends Behavior<EntityVillager> {

    @Nullable
    private ItemStack a;
    private final List<ItemStack> b = Lists.newArrayList();
    private int c;
    private int d;
    private int e;

    public BehaviorTradePlayer(int i, int j) {
        super(i, j);
    }

    public boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        BehaviorController<?> behaviorcontroller = entityvillager.getBehaviorController();

        if (!behaviorcontroller.c(MemoryModuleType.INTERACTION_TARGET).isPresent()) {
            return false;
        } else {
            EntityLiving entityliving = (EntityLiving) behaviorcontroller.c(MemoryModuleType.INTERACTION_TARGET).get();

            return entityliving.getEntityType() == EntityTypes.PLAYER && entityvillager.isAlive() && entityliving.isAlive() && !entityvillager.isBaby() && entityvillager.h((Entity) entityliving) <= 17.0D;
        }
    }

    public boolean g(WorldServer worldserver, EntityVillager entityvillager, long i) {
        return this.a(worldserver, entityvillager) && this.e > 0 && entityvillager.getBehaviorController().c(MemoryModuleType.INTERACTION_TARGET).isPresent();
    }

    public void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        super.a(worldserver, entityvillager, i);
        this.c(entityvillager);
        this.c = 0;
        this.d = 0;
        this.e = 40;
    }

    public void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
        EntityLiving entityliving = this.c(entityvillager);

        this.a(entityliving, entityvillager);
        if (!this.b.isEmpty()) {
            this.d(entityvillager);
        } else {
            entityvillager.setSlot(EnumItemSlot.MAINHAND, ItemStack.a);
            this.e = Math.min(this.e, 40);
        }

        --this.e;
    }

    public void f(WorldServer worldserver, EntityVillager entityvillager, long i) {
        super.f(worldserver, entityvillager, i);
        entityvillager.getBehaviorController().b(MemoryModuleType.INTERACTION_TARGET);
        entityvillager.setSlot(EnumItemSlot.MAINHAND, ItemStack.a);
        this.a = null;
    }

    @Override
    public Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    private void a(EntityLiving entityliving, EntityVillager entityvillager) {
        boolean flag = false;
        ItemStack itemstack = entityliving.getItemInMainHand();

        if (this.a == null || !ItemStack.c(this.a, itemstack)) {
            this.a = itemstack;
            flag = true;
            this.b.clear();
        }

        if (flag && !this.a.isEmpty()) {
            this.b(entityvillager);
            if (!this.b.isEmpty()) {
                this.e = 900;
                this.a(entityvillager);
            }
        }

    }

    private void a(EntityVillager entityvillager) {
        entityvillager.setSlot(EnumItemSlot.MAINHAND, (ItemStack) this.b.get(0));
    }

    private void b(EntityVillager entityvillager) {
        Iterator iterator = entityvillager.getOffers().iterator();

        while (iterator.hasNext()) {
            MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

            if (!merchantrecipe.isFullyUsed() && this.a(merchantrecipe)) {
                this.b.add(merchantrecipe.getSellingItem());
            }
        }

    }

    private boolean a(MerchantRecipe merchantrecipe) {
        return ItemStack.c(this.a, merchantrecipe.getBuyItem1()) || ItemStack.c(this.a, merchantrecipe.getBuyItem2());
    }

    private EntityLiving c(EntityVillager entityvillager) {
        BehaviorController<?> behaviorcontroller = entityvillager.getBehaviorController();
        EntityLiving entityliving = (EntityLiving) behaviorcontroller.c(MemoryModuleType.INTERACTION_TARGET).get();

        behaviorcontroller.a(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorPositionEntity(entityliving)));
        return entityliving;
    }

    private void d(EntityVillager entityvillager) {
        if (this.b.size() >= 2 && ++this.c >= 40) {
            ++this.d;
            this.c = 0;
            if (this.d > this.b.size() - 1) {
                this.d = 0;
            }

            entityvillager.setSlot(EnumItemSlot.MAINHAND, (ItemStack) this.b.get(this.d));
        }

    }
}

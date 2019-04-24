package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import java.util.stream.Collectors;

public class BehaviorTradeVillager extends Behavior<EntityVillager> {

    private Set<Item> a = ImmutableSet.of();

    public BehaviorTradeVillager() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.VISIBLE_MOBS, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        return BehaviorUtil.a(entityvillager.getBehaviorController(), MemoryModuleType.INTERACTION_TARGET, EntityTypes.VILLAGER);
    }

    protected boolean g(WorldServer worldserver, EntityVillager entityvillager, long i) {
        return this.a(worldserver, entityvillager);
    }

    protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        EntityVillager entityvillager1 = (EntityVillager) entityvillager.getBehaviorController().c(MemoryModuleType.INTERACTION_TARGET).get();

        BehaviorUtil.a((EntityLiving) entityvillager, (EntityLiving) entityvillager1);
        this.a = a(entityvillager, entityvillager1);
    }

    protected void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
        EntityVillager entityvillager1 = (EntityVillager) entityvillager.getBehaviorController().c(MemoryModuleType.INTERACTION_TARGET).get();

        if (entityvillager.h((Entity) entityvillager1) <= 5.0D) {
            BehaviorUtil.a((EntityLiving) entityvillager, (EntityLiving) entityvillager1);
            entityvillager.a(entityvillager1, i);
            if (entityvillager.eo() && entityvillager1.ep()) {
                a(entityvillager, EntityVillager.bA.keySet(), entityvillager1);
            }

            if (!this.a.isEmpty() && entityvillager.getInventory().a(this.a)) {
                a(entityvillager, this.a, entityvillager1);
            }

        }
    }

    protected void f(WorldServer worldserver, EntityVillager entityvillager, long i) {
        entityvillager.getBehaviorController().b(MemoryModuleType.INTERACTION_TARGET);
    }

    private static Set<Item> a(EntityVillager entityvillager, EntityVillager entityvillager1) {
        ImmutableSet<Item> immutableset = entityvillager1.getVillagerData().getProfession().c();
        ImmutableSet<Item> immutableset1 = entityvillager.getVillagerData().getProfession().c();

        return (Set) immutableset.stream().filter((item) -> {
            return !immutableset1.contains(item);
        }).collect(Collectors.toSet());
    }

    private static void a(EntityVillager entityvillager, Set<Item> set, EntityLiving entityliving) {
        InventorySubcontainer inventorysubcontainer = entityvillager.getInventory();
        ItemStack itemstack = ItemStack.a;

        for (int i = 0; i < inventorysubcontainer.getSize(); ++i) {
            ItemStack itemstack1 = inventorysubcontainer.getItem(i);

            if (!itemstack1.isEmpty()) {
                Item item = itemstack1.getItem();

                if (set.contains(item)) {
                    int j = itemstack1.getCount() / 2;

                    itemstack1.subtract(j);
                    itemstack = new ItemStack(item, j);
                    break;
                }
            }
        }

        if (!itemstack.isEmpty()) {
            BehaviorUtil.a((EntityLiving) entityvillager, itemstack, entityliving);
        }

    }
}

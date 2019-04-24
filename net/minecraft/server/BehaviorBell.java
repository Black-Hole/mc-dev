package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BehaviorBell extends Behavior<EntityLiving> {

    public BehaviorBell() {}

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        Optional<GlobalPos> optional = behaviorcontroller.c(MemoryModuleType.MEETING_POINT);

        return worldserver.getRandom().nextInt(100) == 0 && optional.isPresent() && Objects.equals(worldserver.getWorldProvider().getDimensionManager(), ((GlobalPos) optional.get()).a()) && ((GlobalPos) optional.get()).b().a((IPosition) entityliving.ch(), 4.0D) && ((List) behaviorcontroller.c(MemoryModuleType.VISIBLE_MOBS).get()).stream().anyMatch((entityliving1) -> {
            return EntityTypes.VILLAGER.equals(entityliving1.getEntityType());
        });
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.VISIBLE_MOBS, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();

        behaviorcontroller.c(MemoryModuleType.VISIBLE_MOBS).ifPresent((list) -> {
            list.stream().filter((entityliving1) -> {
                return EntityTypes.VILLAGER.equals(entityliving1.getEntityType());
            }).filter((entityliving1) -> {
                return entityliving1.h((Entity) entityliving) <= 32.0D;
            }).findFirst().ifPresent((entityliving1) -> {
                behaviorcontroller.a(MemoryModuleType.INTERACTION_TARGET, (Object) entityliving1);
                behaviorcontroller.a(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorPositionEntity(entityliving1)));
                behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(new BehaviorPositionEntity(entityliving1), 0.3F, 1)));
            });
        });
    }
}

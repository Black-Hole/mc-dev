package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class BehaviorLookTarget extends Behavior<EntityLiving> {

    private final Predicate<EntityLiving> a;
    private final float b;

    public BehaviorLookTarget(EnumCreatureType enumcreaturetype, float f) {
        this((entityliving) -> {
            return enumcreaturetype.equals(entityliving.getEntityType().d());
        }, f);
    }

    public BehaviorLookTarget(EntityTypes<?> entitytypes, float f) {
        this((entityliving) -> {
            return entitytypes.equals(entityliving.getEntityType());
        }, f);
    }

    public BehaviorLookTarget(Predicate<EntityLiving> predicate, float f) {
        this.a = predicate;
        this.b = f * f;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.VISIBLE_MOBS, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return ((List) entityliving.getBehaviorController().c(MemoryModuleType.VISIBLE_MOBS).get()).stream().anyMatch(this.a);
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();

        behaviorcontroller.c(MemoryModuleType.VISIBLE_MOBS).ifPresent((list) -> {
            list.stream().filter(this.a).filter((entityliving1) -> {
                return entityliving1.h((Entity) entityliving) <= (double) this.b;
            }).findFirst().ifPresent((entityliving1) -> {
                behaviorcontroller.a(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorPositionEntity(entityliving1)));
            });
        });
    }
}

package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class BehaviorInteract<E extends EntityLiving, T extends EntityLiving> extends Behavior<E> {

    private final int a;
    private final float b;
    private final EntityTypes<? extends T> c;
    private final int d;
    private final Predicate<T> e;
    private final Predicate<E> f;
    private final MemoryModuleType<T> g;

    public BehaviorInteract(EntityTypes<? extends T> entitytypes, int i, Predicate<E> predicate, Predicate<T> predicate1, MemoryModuleType<T> memorymoduletype, float f, int j) {
        this.c = entitytypes;
        this.b = f;
        this.d = i * i;
        this.a = j;
        this.e = predicate1;
        this.f = predicate;
        this.g = memorymoduletype;
    }

    public static <T extends EntityLiving> BehaviorInteract<EntityLiving, T> a(EntityTypes<? extends T> entitytypes, int i, MemoryModuleType<T> memorymoduletype, float f, int j) {
        return new BehaviorInteract<>(entitytypes, i, (entityliving) -> {
            return true;
        }, (entityliving) -> {
            return true;
        }, memorymoduletype, f, j);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(this.g, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.VISIBLE_MOBS, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, E e0) {
        return this.f.test(e0) && ((List) e0.getBehaviorController().c(MemoryModuleType.VISIBLE_MOBS).get()).stream().anyMatch((entityliving) -> {
            return this.c.equals(entityliving.getEntityType()) && this.e.test(entityliving);
        });
    }

    @Override
    protected void a(WorldServer worldserver, E e0, long i) {
        BehaviorController<?> behaviorcontroller = e0.getBehaviorController();

        behaviorcontroller.c(MemoryModuleType.VISIBLE_MOBS).ifPresent((list) -> {
            list.stream().filter((entityliving) -> {
                return this.c.equals(entityliving.getEntityType());
            }).map((entityliving) -> {
                return entityliving;
            }).filter((entityliving) -> {
                return entityliving.h((Entity) e0) <= (double) this.d;
            }).filter(this.e).findFirst().ifPresent((entityliving) -> {
                behaviorcontroller.a(this.g, (Object) entityliving);
                behaviorcontroller.a(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorPositionEntity(entityliving)));
                behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(new BehaviorPositionEntity(entityliving), this.b, this.a)));
            });
        });
    }
}

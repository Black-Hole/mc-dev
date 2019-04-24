package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorLook extends Behavior<EntityInsentient> {

    public BehaviorLook(int i, int j) {
        super(i, j);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean g(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        return entityinsentient.getBehaviorController().c(MemoryModuleType.LOOK_TARGET).filter((behaviorposition) -> {
            return behaviorposition.a(entityinsentient);
        }).isPresent();
    }

    protected void f(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        entityinsentient.getBehaviorController().b(MemoryModuleType.LOOK_TARGET);
    }

    protected void d(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        entityinsentient.getBehaviorController().c(MemoryModuleType.LOOK_TARGET).ifPresent((behaviorposition) -> {
            entityinsentient.getControllerLook().a(behaviorposition.b());
        });
    }
}

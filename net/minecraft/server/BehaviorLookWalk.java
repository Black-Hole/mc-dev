package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorLookWalk extends Behavior<EntityLiving> {

    private final float a;
    private final int b;

    public BehaviorLookWalk(float f, int i) {
        this.a = f;
        this.b = i;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        BehaviorPosition behaviorposition = (BehaviorPosition) behaviorcontroller.c(MemoryModuleType.LOOK_TARGET).get();

        behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(behaviorposition, this.a, this.b)));
    }
}

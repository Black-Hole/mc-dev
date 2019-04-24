package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorSwim extends Behavior<EntityInsentient> {

    private final float a;
    private final float b;

    public BehaviorSwim(float f, float f1) {
        this.a = f;
        this.b = f1;
    }

    protected boolean a(WorldServer worldserver, EntityInsentient entityinsentient) {
        return entityinsentient.isInWater() && entityinsentient.ce() > (double) this.a || entityinsentient.aC();
    }

    protected boolean g(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        return this.a(worldserver, entityinsentient);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }

    protected void d(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        if (entityinsentient.getRandom().nextFloat() < this.b) {
            entityinsentient.getControllerJump().jump();
        }

    }
}

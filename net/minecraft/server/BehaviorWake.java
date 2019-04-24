package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorWake extends Behavior<EntityLiving> {

    public BehaviorWake() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return !entityliving.getBehaviorController().c(Activity.e) && entityliving.isSleeping();
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        entityliving.dy();
    }
}

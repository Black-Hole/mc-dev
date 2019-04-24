package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorSchedule extends Behavior<EntityLiving> {

    public BehaviorSchedule() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        entityliving.getBehaviorController().a(worldserver.getDayTime(), worldserver.getTime());
    }
}

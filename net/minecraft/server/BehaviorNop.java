package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorNop extends Behavior<EntityLiving> {

    public BehaviorNop(int i, int j) {
        super(i, j);
    }

    @Override
    protected boolean g(WorldServer worldserver, EntityLiving entityliving, long i) {
        return true;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }
}

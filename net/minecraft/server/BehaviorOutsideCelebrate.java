package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorOutsideCelebrate extends BehaviorOutside {

    public BehaviorOutsideCelebrate(float f) {
        super(f);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        Raid raid = worldserver.c_(new BlockPosition(entityliving));

        return raid != null && raid.e() && super.a(worldserver, entityliving);
    }
}

package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorRaid extends Behavior<EntityLiving> {

    public BehaviorRaid() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return worldserver.random.nextInt(20) == 0;
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        Raid raid = worldserver.c_(new BlockPosition(entityliving));

        if (raid != null) {
            if (raid.c() && !raid.b()) {
                behaviorcontroller.b(Activity.h);
                behaviorcontroller.a(Activity.h);
            } else {
                behaviorcontroller.b(Activity.i);
                behaviorcontroller.a(Activity.i);
            }
        }

    }
}

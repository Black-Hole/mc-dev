package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorPanic extends Behavior<EntityLiving> {

    public BehaviorPanic() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of();
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        if (b(entityliving) || a(entityliving)) {
            BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();

            if (!behaviorcontroller.c(Activity.g)) {
                behaviorcontroller.b(MemoryModuleType.PATH);
                behaviorcontroller.b(MemoryModuleType.WALK_TARGET);
                behaviorcontroller.b(MemoryModuleType.LOOK_TARGET);
                behaviorcontroller.b(MemoryModuleType.BREED_TARGET);
                behaviorcontroller.b(MemoryModuleType.INTERACTION_TARGET);
            }

            behaviorcontroller.a(Activity.g);
        }

    }

    public static boolean a(EntityLiving entityliving) {
        return entityliving.getBehaviorController().a(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean b(EntityLiving entityliving) {
        return entityliving.getBehaviorController().a(MemoryModuleType.HURT_BY);
    }
}

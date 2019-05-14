package net.minecraft.server;

import com.google.common.collect.ImmutableMap;

public class BehaviorPanic extends Behavior<EntityLiving> {

    public BehaviorPanic() {
        super(ImmutableMap.of());
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        if (b(entityliving) || a(entityliving)) {
            BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();

            if (!behaviorcontroller.c(Activity.PANIC)) {
                behaviorcontroller.removeMemory(MemoryModuleType.PATH);
                behaviorcontroller.removeMemory(MemoryModuleType.WALK_TARGET);
                behaviorcontroller.removeMemory(MemoryModuleType.LOOK_TARGET);
                behaviorcontroller.removeMemory(MemoryModuleType.BREED_TARGET);
                behaviorcontroller.removeMemory(MemoryModuleType.INTERACTION_TARGET);
            }

            behaviorcontroller.a(Activity.PANIC);
        }

    }

    public static boolean a(EntityLiving entityliving) {
        return entityliving.getBehaviorController().hasMemory(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean b(EntityLiving entityliving) {
        return entityliving.getBehaviorController().hasMemory(MemoryModuleType.HURT_BY);
    }
}

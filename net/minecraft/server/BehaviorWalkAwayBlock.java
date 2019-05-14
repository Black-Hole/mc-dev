package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class BehaviorWalkAwayBlock extends Behavior<EntityVillager> {

    private final MemoryModuleType<GlobalPos> a;
    private final float b;
    private final int c;
    private final int d;
    private final int e;

    public BehaviorWalkAwayBlock(MemoryModuleType<GlobalPos> memorymoduletype, float f, int i, int j, int k) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, memorymoduletype, MemoryStatus.VALUE_PRESENT));
        this.a = memorymoduletype;
        this.b = f;
        this.c = i;
        this.d = j;
        this.e = k;
    }

    protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        BehaviorController<?> behaviorcontroller = entityvillager.getBehaviorController();

        behaviorcontroller.getMemory(this.a).ifPresent((globalpos) -> {
            if (!this.a(worldserver, entityvillager, globalpos) && !this.a(worldserver, entityvillager)) {
                if (!this.b(worldserver, entityvillager, globalpos)) {
                    behaviorcontroller.setMemory(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(globalpos.b(), this.b, this.c)));
                }
            } else {
                entityvillager.a(this.a);
                behaviorcontroller.removeMemory(this.a);
            }

        });
    }

    private boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        Optional<Long> optional = entityvillager.getBehaviorController().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        return optional.isPresent() ? worldserver.getTime() - (Long) optional.get() > (long) this.e : false;
    }

    private boolean a(WorldServer worldserver, EntityVillager entityvillager, GlobalPos globalpos) {
        return globalpos.a() != worldserver.getWorldProvider().getDimensionManager() || globalpos.b().n(new BlockPosition(entityvillager)) > this.d;
    }

    private boolean b(WorldServer worldserver, EntityVillager entityvillager, GlobalPos globalpos) {
        return globalpos.a() == worldserver.getWorldProvider().getDimensionManager() && globalpos.b().n(new BlockPosition(entityvillager)) <= this.c;
    }
}

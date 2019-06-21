package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;

public class BehaviorWork extends Behavior<EntityVillager> {

    private int a;
    private boolean b;

    public BehaviorWork() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));
    }

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        return this.a(worldserver.getDayTime() % 24000L, entityvillager.et());
    }

    protected void f(WorldServer worldserver, EntityVillager entityvillager, long i) {
        this.b = false;
        this.a = 0;
        entityvillager.getBehaviorController().removeMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
        BehaviorController<EntityVillager> behaviorcontroller = entityvillager.getBehaviorController();

        behaviorcontroller.setMemory(MemoryModuleType.LAST_WORKED_AT_POI, (Object) MinecraftSerializableLong.a(i));
        if (!this.b) {
            entityvillager.ek();
            this.b = true;
            entityvillager.el();
            behaviorcontroller.getMemory(MemoryModuleType.JOB_SITE).ifPresent((globalpos) -> {
                behaviorcontroller.setMemory(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorTarget(globalpos.getBlockPosition())));
            });
        }

        ++this.a;
    }

    protected boolean g(WorldServer worldserver, EntityVillager entityvillager, long i) {
        Optional<GlobalPos> optional = entityvillager.getBehaviorController().getMemory(MemoryModuleType.JOB_SITE);

        if (!optional.isPresent()) {
            return false;
        } else {
            GlobalPos globalpos = (GlobalPos) optional.get();

            return this.a < 100 && Objects.equals(globalpos.getDimensionManager(), worldserver.getWorldProvider().getDimensionManager()) && globalpos.getBlockPosition().a((IPosition) entityvillager.ci(), 1.73D);
        }
    }

    private boolean a(long i, long j) {
        return j == 0L || i < j || i > j + 3500L;
    }
}

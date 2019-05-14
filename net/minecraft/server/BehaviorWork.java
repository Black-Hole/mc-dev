package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;

public class BehaviorWork extends Behavior<EntityVillager> {

    private int a;
    private boolean b;

    public BehaviorWork() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.GOLEM_SPAWN_CONDITIONS, MemoryStatus.REGISTERED));
    }

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        return this.a(worldserver.getDayTime() % 24000L, entityvillager.eq());
    }

    protected void f(WorldServer worldserver, EntityVillager entityvillager, long i) {
        this.b = false;
        this.a = 0;
        entityvillager.getBehaviorController().removeMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
        BehaviorController<EntityVillager> behaviorcontroller = entityvillager.getBehaviorController();
        EntityVillager.a entityvillager_a = (EntityVillager.a) behaviorcontroller.getMemory(MemoryModuleType.GOLEM_SPAWN_CONDITIONS).orElseGet(EntityVillager.a::new);

        entityvillager_a.a(i);
        behaviorcontroller.setMemory(MemoryModuleType.GOLEM_SPAWN_CONDITIONS, (Object) entityvillager_a);
        if (!this.b) {
            entityvillager.ei();
            this.b = true;
            entityvillager.ej();
            behaviorcontroller.getMemory(MemoryModuleType.JOB_SITE).ifPresent((globalpos) -> {
                behaviorcontroller.setMemory(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorTarget(globalpos.b())));
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

            return this.a < 100 && Objects.equals(globalpos.a(), worldserver.getWorldProvider().getDimensionManager()) && globalpos.b().a((IPosition) entityvillager.ch(), 1.73D);
        }
    }

    private boolean a(long i, long j) {
        return j == 0L || i < j || i > j + 3500L;
    }
}

package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BehaviorWork extends Behavior<EntityVillager> {

    private int a;
    private boolean b;

    public BehaviorWork() {}

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        return this.a(worldserver.getDayTime() % 24000L, entityvillager.er());
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.GOLEM_SPAWN_CONDITIONS, MemoryStatus.REGISTERED));
    }

    protected void f(WorldServer worldserver, EntityVillager entityvillager, long i) {
        this.b = false;
        this.a = 0;
        entityvillager.getBehaviorController().b(MemoryModuleType.LOOK_TARGET);
    }

    protected void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
        BehaviorController<EntityVillager> behaviorcontroller = entityvillager.getBehaviorController();
        EntityVillager.a entityvillager_a = (EntityVillager.a) behaviorcontroller.c(MemoryModuleType.GOLEM_SPAWN_CONDITIONS).orElseGet(EntityVillager.a::new);

        entityvillager_a.a(i);
        behaviorcontroller.a(MemoryModuleType.GOLEM_SPAWN_CONDITIONS, (Object) entityvillager_a);
        if (!this.b) {
            entityvillager.ei();
            this.b = true;
            entityvillager.ej();
            behaviorcontroller.c(MemoryModuleType.JOB_SITE).ifPresent((globalpos) -> {
                behaviorcontroller.a(MemoryModuleType.LOOK_TARGET, (Object) (new BehaviorTarget(globalpos.b())));
            });
        }

        ++this.a;
    }

    protected boolean g(WorldServer worldserver, EntityVillager entityvillager, long i) {
        Optional<GlobalPos> optional = entityvillager.getBehaviorController().c(MemoryModuleType.JOB_SITE);

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

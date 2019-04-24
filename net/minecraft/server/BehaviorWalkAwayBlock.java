package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorWalkAwayBlock extends Behavior<EntityVillager> {

    private final MemoryModuleType<GlobalPos> a;
    private final float b;
    private final int c;
    private final int d;

    public BehaviorWalkAwayBlock(MemoryModuleType<GlobalPos> memorymoduletype, float f, int i, int j) {
        this.a = memorymoduletype;
        this.b = f;
        this.c = i;
        this.d = j;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(this.a, MemoryStatus.VALUE_PRESENT));
    }

    protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        BehaviorController<?> behaviorcontroller = entityvillager.getBehaviorController();

        behaviorcontroller.c(this.a).ifPresent((globalpos) -> {
            if (this.a(worldserver, entityvillager, globalpos)) {
                entityvillager.a(this.a);
                behaviorcontroller.b(this.a);
            } else if (!this.b(worldserver, entityvillager, globalpos)) {
                behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(globalpos.b(), this.b, this.c)));
            }

        });
    }

    private boolean a(WorldServer worldserver, EntityVillager entityvillager, GlobalPos globalpos) {
        return globalpos.a() != worldserver.getWorldProvider().getDimensionManager() || globalpos.b().n(new BlockPosition(entityvillager)) > this.d;
    }

    private boolean b(WorldServer worldserver, EntityVillager entityvillager, GlobalPos globalpos) {
        return globalpos.a() == worldserver.getWorldProvider().getDimensionManager() && globalpos.b().n(new BlockPosition(entityvillager)) <= this.c;
    }
}

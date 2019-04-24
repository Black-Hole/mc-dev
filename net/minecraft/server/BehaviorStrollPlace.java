package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BehaviorStrollPlace extends Behavior<EntityCreature> {

    private final MemoryModuleType<GlobalPos> a;
    private final int b;
    private final int c;
    private long d;

    public BehaviorStrollPlace(MemoryModuleType<GlobalPos> memorymoduletype, int i, int j) {
        this.a = memorymoduletype;
        this.b = i;
        this.c = j;
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        Optional<GlobalPos> optional = entitycreature.getBehaviorController().c(this.a);

        return optional.isPresent() && Objects.equals(worldserver.getWorldProvider().getDimensionManager(), ((GlobalPos) optional.get()).a()) && ((GlobalPos) optional.get()).b().a((IPosition) entitycreature.ch(), (double) this.c);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED), Pair.of(this.a, MemoryStatus.VALUE_PRESENT));
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        if (i > this.d) {
            BehaviorController<?> behaviorcontroller = entitycreature.getBehaviorController();
            Optional<GlobalPos> optional = behaviorcontroller.c(this.a);

            optional.ifPresent((globalpos) -> {
                behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(globalpos.b(), 0.4F, this.b)));
            });
            this.d = i + 80L;
        }

    }
}

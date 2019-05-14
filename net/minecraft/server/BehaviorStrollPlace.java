package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;

public class BehaviorStrollPlace extends Behavior<EntityCreature> {

    private final MemoryModuleType<GlobalPos> a;
    private final int b;
    private final int c;
    private long d;

    public BehaviorStrollPlace(MemoryModuleType<GlobalPos> memorymoduletype, int i, int j) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, memorymoduletype, MemoryStatus.VALUE_PRESENT));
        this.a = memorymoduletype;
        this.b = i;
        this.c = j;
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        Optional<GlobalPos> optional = entitycreature.getBehaviorController().getMemory(this.a);

        return optional.isPresent() && Objects.equals(worldserver.getWorldProvider().getDimensionManager(), ((GlobalPos) optional.get()).a()) && ((GlobalPos) optional.get()).b().a((IPosition) entitycreature.ch(), (double) this.c);
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        if (i > this.d) {
            BehaviorController<?> behaviorcontroller = entitycreature.getBehaviorController();
            Optional<GlobalPos> optional = behaviorcontroller.getMemory(this.a);

            optional.ifPresent((globalpos) -> {
                behaviorcontroller.setMemory(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(globalpos.b(), 0.4F, this.b)));
            });
            this.d = i + 80L;
        }

    }
}

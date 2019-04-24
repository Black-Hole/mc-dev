package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BehaviorStrollPosition extends Behavior<EntityCreature> {

    private final MemoryModuleType<GlobalPos> a;
    private long b;
    private final int c;

    public BehaviorStrollPosition(MemoryModuleType<GlobalPos> memorymoduletype, int i) {
        this.a = memorymoduletype;
        this.c = i;
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
        if (i > this.b) {
            Optional<Vec3D> optional = Optional.ofNullable(RandomPositionGenerator.b(entitycreature, 8, 6));

            entitycreature.getBehaviorController().a(MemoryModuleType.WALK_TARGET, optional.map((vec3d) -> {
                return new MemoryTarget(vec3d, 0.4F, 1);
            }));
            this.b = i + 180L;
        }

    }
}

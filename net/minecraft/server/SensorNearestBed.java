package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class SensorNearestBed extends Sensor<EntityInsentient> {

    public SensorNearestBed() {
        super(200);
    }

    @Override
    public Set<MemoryModuleType<?>> a() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_BED);
    }

    protected void a(WorldServer worldserver, EntityInsentient entityinsentient) {
        if (entityinsentient.isBaby()) {
            entityinsentient.getBehaviorController().setMemory(MemoryModuleType.NEAREST_BED, this.b(worldserver, entityinsentient));
        }
    }

    private Optional<BlockPosition> b(WorldServer worldserver, EntityInsentient entityinsentient) {
        VillagePlace villageplace = worldserver.B();
        Predicate<BlockPosition> predicate = (blockposition) -> {
            if (blockposition.equals(new BlockPosition(entityinsentient))) {
                return true;
            } else {
                PathEntity pathentity = entityinsentient.getNavigation().b(blockposition);

                return pathentity != null && pathentity.a(blockposition);
            }
        };

        return villageplace.b(VillagePlaceType.q.c(), predicate, new BlockPosition(entityinsentient), 16, VillagePlace.Occupancy.ANY);
    }
}

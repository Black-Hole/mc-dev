package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class BehaviourWalkHome extends Behavior<EntityLiving> {

    private final float a;
    private long b;

    public BehaviourWalkHome(float f) {
        this.a = f;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        if (worldserver.getTime() - this.b < 40L) {
            return false;
        } else {
            EntityCreature entitycreature = (EntityCreature) entityliving;
            VillagePlace villageplace = worldserver.B();
            Optional<BlockPosition> optional = villageplace.b(VillagePlaceType.q.c(), (blockposition) -> {
                return true;
            }, new BlockPosition(entityliving), 48, VillagePlace.Occupancy.ANY);

            return optional.isPresent() && ((BlockPosition) optional.get()).m(new BaseBlockPosition(entitycreature.locX, entitycreature.locY, entitycreature.locZ)) > 4.0D;
        }
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        this.b = worldserver.getTime();
        EntityCreature entitycreature = (EntityCreature) entityliving;
        VillagePlace villageplace = worldserver.B();
        Predicate<BlockPosition> predicate = (blockposition) -> {
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);

            if (worldserver.getType(blockposition.down()).isAir()) {
                blockposition_mutableblockposition.c(EnumDirection.DOWN);
            }

            while (worldserver.getType(blockposition_mutableblockposition).isAir() && blockposition_mutableblockposition.getY() >= 0) {
                blockposition_mutableblockposition.c(EnumDirection.DOWN);
            }

            PathEntity pathentity = entitycreature.getNavigation().b(blockposition_mutableblockposition.immutableCopy());

            return pathentity != null && pathentity.h();
        };

        villageplace.b(VillagePlaceType.q.c(), predicate, new BlockPosition(entityliving), 48, VillagePlace.Occupancy.ANY).ifPresent((blockposition) -> {
            entityliving.getBehaviorController().a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(blockposition, this.a, 1)));
            PacketDebug.c(worldserver, blockposition);
        });
    }
}

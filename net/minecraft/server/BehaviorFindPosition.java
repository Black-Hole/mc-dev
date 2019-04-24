package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import java.util.function.Predicate;

public class BehaviorFindPosition extends Behavior<EntityLiving> {

    private final Predicate<VillagePlaceType> a;
    private final MemoryModuleType<GlobalPos> b;
    private final boolean c;
    private long d;

    public BehaviorFindPosition(VillagePlaceType villageplacetype, MemoryModuleType<GlobalPos> memorymoduletype, boolean flag) {
        this.a = villageplacetype.c();
        this.b = memorymoduletype;
        this.c = flag;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(this.b, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return this.c && entityliving.isBaby() ? false : worldserver.getTime() - this.d >= 40L;
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        this.d = worldserver.getTime();
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

        villageplace.b(this.a, predicate, new BlockPosition(entityliving), 48).ifPresent((blockposition) -> {
            entityliving.getBehaviorController().a(this.b, (Object) GlobalPos.a(worldserver.getWorldProvider().getDimensionManager(), blockposition));
            PacketDebug.c(worldserver, blockposition);
        });
    }
}

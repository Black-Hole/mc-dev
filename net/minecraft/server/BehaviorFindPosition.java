package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import java.util.Optional;
import java.util.function.Predicate;

public class BehaviorFindPosition extends Behavior<EntityLiving> {

    private final VillagePlaceType a;
    private final MemoryModuleType<GlobalPos> b;
    private final boolean c;
    private long d;
    private final Long2LongMap e = new Long2LongOpenHashMap();
    private int f;

    public BehaviorFindPosition(VillagePlaceType villageplacetype, MemoryModuleType<GlobalPos> memorymoduletype, boolean flag) {
        super(ImmutableMap.of(memorymoduletype, MemoryStatus.VALUE_ABSENT));
        this.a = villageplacetype;
        this.b = memorymoduletype;
        this.c = flag;
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return this.c && entityliving.isBaby() ? false : worldserver.getTime() - this.d >= 20L;
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        this.f = 0;
        this.d = worldserver.getTime() + (long) worldserver.getRandom().nextInt(20);
        EntityCreature entitycreature = (EntityCreature) entityliving;
        VillagePlace villageplace = worldserver.B();
        Predicate<BlockPosition> predicate = (blockposition) -> {
            long j = blockposition.asLong();

            if (this.e.containsKey(j)) {
                return false;
            } else if (++this.f >= 5) {
                return false;
            } else {
                Object object;

                if (this.a == VillagePlaceType.r) {
                    BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);

                    this.a(worldserver, blockposition_mutableblockposition);
                    blockposition_mutableblockposition.c(EnumDirection.UP);
                    object = blockposition_mutableblockposition;
                } else {
                    object = blockposition;
                }

                PathEntity pathentity = entitycreature.getNavigation().b((BlockPosition) object);
                boolean flag = pathentity != null && pathentity.a((BlockPosition) object);

                if (!flag) {
                    this.e.put(j, this.d + 40L);
                }

                return flag;
            }
        };
        Optional<BlockPosition> optional = villageplace.b(this.a.c(), predicate, new BlockPosition(entityliving), 48);

        if (optional.isPresent()) {
            BlockPosition blockposition = (BlockPosition) optional.get();

            entityliving.getBehaviorController().setMemory(this.b, (Object) GlobalPos.a(worldserver.getWorldProvider().getDimensionManager(), blockposition));
            PacketDebug.c(worldserver, blockposition);
        } else if (this.f < 5) {
            this.e.long2LongEntrySet().removeIf((entry) -> {
                return entry.getLongValue() < this.d;
            });
        }

    }

    private void a(WorldServer worldserver, BlockPosition.MutableBlockPosition blockposition_mutableblockposition) {
        do {
            blockposition_mutableblockposition.c(EnumDirection.DOWN);
        } while (worldserver.getType(blockposition_mutableblockposition).getCollisionShape(worldserver, blockposition_mutableblockposition).isEmpty() && blockposition_mutableblockposition.getY() > 0);

    }
}

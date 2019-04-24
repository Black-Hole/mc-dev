package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BehaviorSleep extends Behavior<EntityLiving> {

    private long a;

    public BehaviorSleep() {}

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        if (entityliving.isPassenger()) {
            return false;
        } else {
            GlobalPos globalpos = (GlobalPos) entityliving.getBehaviorController().c(MemoryModuleType.HOME).get();

            if (!Objects.equals(worldserver.getWorldProvider().getDimensionManager(), globalpos.a())) {
                return false;
            } else {
                IBlockData iblockdata = worldserver.getType(globalpos.b());

                return globalpos.b().a((IPosition) entityliving.ch(), 2.0D) && iblockdata.getBlock().a(TagsBlock.BEDS) && !(Boolean) iblockdata.get(BlockBed.OCCUPIED);
            }
        }
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean g(WorldServer worldserver, EntityLiving entityliving, long i) {
        Optional<GlobalPos> optional = entityliving.getBehaviorController().c(MemoryModuleType.HOME);

        if (!optional.isPresent()) {
            return false;
        } else {
            BlockPosition blockposition = ((GlobalPos) optional.get()).b();

            return entityliving.getBehaviorController().c(Activity.e) && entityliving.locY > (double) blockposition.getY() + 0.4D && blockposition.a((IPosition) entityliving.ch(), 1.14D);
        }
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        if (i > this.a) {
            entityliving.e(((GlobalPos) entityliving.getBehaviorController().c(MemoryModuleType.HOME).get()).b());
        }

    }

    @Override
    protected boolean a(long i) {
        return false;
    }

    @Override
    protected void f(WorldServer worldserver, EntityLiving entityliving, long i) {
        if (entityliving.isSleeping()) {
            entityliving.dy();
            this.a = i + 40L;
        }

    }
}

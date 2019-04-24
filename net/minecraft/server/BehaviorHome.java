package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Set;

public class BehaviorHome extends Behavior<EntityLiving> {

    private final float a;
    private final int b;
    private final int c;
    private Optional<BlockPosition> d = Optional.empty();

    public BehaviorHome(int i, float f, int j) {
        this.b = i;
        this.a = f;
        this.c = j;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.HOME, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.HIDING_PLACE, MemoryStatus.REGISTERED));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        Optional<BlockPosition> optional = worldserver.B().a((villageplacetype) -> {
            return villageplacetype == VillagePlaceType.q;
        }, (blockposition) -> {
            return true;
        }, new BlockPosition(entityliving), this.c + 1, VillagePlace.Occupancy.ANY);

        if (optional.isPresent() && ((BlockPosition) optional.get()).a((IPosition) entityliving.ch(), (double) this.c)) {
            this.d = optional;
        } else {
            this.d = Optional.empty();
        }

        return true;
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        Optional<BlockPosition> optional = this.d;

        if (!optional.isPresent()) {
            optional = worldserver.B().a((villageplacetype) -> {
                return villageplacetype == VillagePlaceType.q;
            }, (blockposition) -> {
                return true;
            }, VillagePlace.Occupancy.ANY, new BlockPosition(entityliving), this.b, entityliving.getRandom());
            if (!optional.isPresent()) {
                Optional<GlobalPos> optional1 = behaviorcontroller.c(MemoryModuleType.HOME);

                if (optional1.isPresent()) {
                    optional = Optional.of(((GlobalPos) optional1.get()).b());
                }
            }
        }

        if (optional.isPresent()) {
            behaviorcontroller.b(MemoryModuleType.PATH);
            behaviorcontroller.b(MemoryModuleType.LOOK_TARGET);
            behaviorcontroller.b(MemoryModuleType.BREED_TARGET);
            behaviorcontroller.b(MemoryModuleType.INTERACTION_TARGET);
            behaviorcontroller.a(MemoryModuleType.HIDING_PLACE, (Object) GlobalPos.a(worldserver.getWorldProvider().getDimensionManager(), (BlockPosition) optional.get()));
            if (!((BlockPosition) optional.get()).a((IPosition) entityliving.ch(), (double) this.c)) {
                behaviorcontroller.a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget((BlockPosition) optional.get(), this.a, this.c)));
            }
        }

    }
}

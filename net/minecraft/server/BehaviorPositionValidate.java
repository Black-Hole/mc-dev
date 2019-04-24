package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class BehaviorPositionValidate extends Behavior<EntityLiving> {

    private final MemoryModuleType<GlobalPos> a;
    private final Predicate<VillagePlaceType> b;

    public BehaviorPositionValidate(VillagePlaceType villageplacetype, MemoryModuleType<GlobalPos> memorymoduletype) {
        this.b = villageplacetype.c();
        this.a = memorymoduletype;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(this.a, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        GlobalPos globalpos = (GlobalPos) entityliving.getBehaviorController().c(this.a).get();

        return Objects.equals(worldserver.getWorldProvider().getDimensionManager(), globalpos.a()) && globalpos.b().a((IPosition) entityliving.ch(), 3.0D);
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        GlobalPos globalpos = (GlobalPos) behaviorcontroller.c(this.a).get();
        WorldServer worldserver1 = worldserver.getMinecraftServer().getWorldServer(globalpos.a());

        if (!worldserver1.B().a(globalpos.b(), this.b)) {
            behaviorcontroller.b(this.a);
        }

    }
}

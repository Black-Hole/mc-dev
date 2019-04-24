package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorProfession extends Behavior<EntityVillager> {

    public BehaviorProfession() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_ABSENT));
    }

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        VillagerData villagerdata = entityvillager.getVillagerData();

        return villagerdata.getProfession() != VillagerProfession.NONE && villagerdata.getProfession() != VillagerProfession.NITWIT && entityvillager.dV() == 0 && villagerdata.getLevel() <= 1;
    }

    protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        entityvillager.setVillagerData(entityvillager.getVillagerData().withProfession(VillagerProfession.NONE));
        entityvillager.a(worldserver);
    }
}

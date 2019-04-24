package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorCareer extends Behavior<EntityVillager> {

    public BehaviorCareer() {}

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
        return entityvillager.getVillagerData().getProfession() == VillagerProfession.NONE;
    }

    protected void a(WorldServer worldserver, EntityVillager entityvillager, long i) {
        GlobalPos globalpos = (GlobalPos) entityvillager.getBehaviorController().c(MemoryModuleType.JOB_SITE).get();
        MinecraftServer minecraftserver = worldserver.getMinecraftServer();

        minecraftserver.getWorldServer(globalpos.a()).B().c(globalpos.b()).ifPresent((villageplacetype) -> {
            IRegistry.VILLAGER_PROFESSION.d().filter((villagerprofession) -> {
                return villagerprofession.b() == villageplacetype;
            }).findFirst().ifPresent((villagerprofession) -> {
                entityvillager.setVillagerData(entityvillager.getVillagerData().withProfession(villagerprofession));
                entityvillager.a(worldserver);
            });
        });
    }
}

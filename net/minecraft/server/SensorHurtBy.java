package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class SensorHurtBy extends Sensor<EntityLiving> {

    public SensorHurtBy() {}

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();

        if (entityliving.cD() != null) {
            behaviorcontroller.a(MemoryModuleType.HURT_BY, (Object) entityliving.cD());
            Entity entity = ((DamageSource) behaviorcontroller.c(MemoryModuleType.HURT_BY).get()).getEntity();

            if (entity instanceof EntityLiving) {
                behaviorcontroller.a(MemoryModuleType.HURT_BY_ENTITY, (Object) ((EntityLiving) entity));
            }
        } else {
            behaviorcontroller.b(MemoryModuleType.HURT_BY);
        }

    }

    @Override
    public Set<MemoryModuleType<?>> a() {
        return ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
    }
}

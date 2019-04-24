package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Set;

public class BehaviorWalkAwayEntity extends Behavior<EntityCreature> {

    private final MemoryModuleType<? extends Entity> a;
    private final float b;

    public BehaviorWalkAwayEntity(MemoryModuleType<? extends Entity> memorymoduletype, float f) {
        this.a = memorymoduletype;
        this.b = f;
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        Entity entity = (Entity) entitycreature.getBehaviorController().c(this.a).get();

        return entitycreature.h(entity) < 16.0D;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(this.a, MemoryStatus.VALUE_PRESENT));
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        Entity entity = (Entity) entitycreature.getBehaviorController().c(this.a).get();

        a(entitycreature, entity, this.b);
    }

    public static void a(EntityCreature entitycreature, Entity entity, float f) {
        for (int i = 0; i < 10; ++i) {
            Vec3D vec3d = new Vec3D(entitycreature.locX, entitycreature.locY, entitycreature.locZ);
            Vec3D vec3d1 = new Vec3D(entity.locX, entity.locY, entity.locZ);
            Vec3D vec3d2 = vec3d.d(vec3d1).d();
            Vec3D vec3d3 = RandomPositionGenerator.a(entitycreature, 16, 7, vec3d2, 0.3141592741012573D);

            if (vec3d3 != null) {
                entitycreature.getBehaviorController().a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(vec3d3, f, 0)));
                return;
            }
        }

    }
}

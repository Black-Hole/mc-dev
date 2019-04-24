package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Set;

public class BehaviorStrollRandom extends Behavior<EntityCreature> {

    private final float a;

    public BehaviorStrollRandom(float f) {
        this.a = f;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        BlockPosition blockposition = new BlockPosition(entitycreature);

        if (worldserver.b_(blockposition)) {
            this.a(entitycreature);
        } else {
            SectionPosition sectionposition = SectionPosition.a(blockposition);
            SectionPosition sectionposition1 = BehaviorUtil.a(worldserver, sectionposition, 2);

            if (sectionposition1 != sectionposition) {
                this.a(entitycreature, sectionposition1);
            } else {
                this.a(entitycreature);
            }
        }

    }

    private void a(EntityCreature entitycreature, SectionPosition sectionposition) {
        BlockPosition blockposition = sectionposition.t();
        Optional<Vec3D> optional = Optional.ofNullable(RandomPositionGenerator.a(entitycreature, 10, 7, new Vec3D((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ())));

        entitycreature.getBehaviorController().a(MemoryModuleType.WALK_TARGET, optional.map((vec3d) -> {
            return new MemoryTarget(vec3d, this.a, 0);
        }));
    }

    private void a(EntityCreature entitycreature) {
        Optional<Vec3D> optional = Optional.ofNullable(RandomPositionGenerator.b(entitycreature, 10, 7));

        entitycreature.getBehaviorController().a(MemoryModuleType.WALK_TARGET, optional.map((vec3d) -> {
            return new MemoryTarget(vec3d, this.a, 0);
        }));
    }
}

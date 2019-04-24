package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class BehaviorOutside extends Behavior<EntityLiving> {

    private final float a;

    public BehaviorOutside(float f) {
        this.a = f;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        Optional<Vec3D> optional = Optional.ofNullable(this.b(worldserver, entityliving));

        if (optional.isPresent()) {
            entityliving.getBehaviorController().a(MemoryModuleType.WALK_TARGET, optional.map((vec3d) -> {
                return new MemoryTarget(vec3d, this.a, 0);
            }));
        }

    }

    @Override
    protected boolean a(WorldServer worldserver, EntityLiving entityliving) {
        return !worldserver.f(new BlockPosition(entityliving.locX, entityliving.getBoundingBox().minY, entityliving.locZ));
    }

    @Nullable
    private Vec3D b(WorldServer worldserver, EntityLiving entityliving) {
        Random random = entityliving.getRandom();
        BlockPosition blockposition = new BlockPosition(entityliving.locX, entityliving.getBoundingBox().minY, entityliving.locZ);

        for (int i = 0; i < 10; ++i) {
            BlockPosition blockposition1 = blockposition.b(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (worldserver.f(blockposition1)) {
                return new Vec3D((double) blockposition1.getX(), (double) blockposition1.getY(), (double) blockposition1.getZ());
            }
        }

        return null;
    }
}

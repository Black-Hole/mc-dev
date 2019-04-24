package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BehaviorStrollInside extends Behavior<EntityCreature> {

    private final float a;

    public BehaviorStrollInside(float f) {
        this.a = f;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        return !worldserver.f(new BlockPosition(entitycreature));
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        BlockPosition blockposition = new BlockPosition(entitycreature);
        List<BlockPosition> list = (List) BlockPosition.b(blockposition.b(-1, -1, -1), blockposition.b(1, 1, 1)).map(BlockPosition::immutableCopy).collect(Collectors.toList());

        Collections.shuffle(list);
        Optional<BlockPosition> optional = list.stream().filter((blockposition1) -> {
            return !worldserver.f(blockposition1);
        }).filter((blockposition1) -> {
            return worldserver.a(blockposition1, (Entity) entitycreature);
        }).filter((blockposition1) -> {
            return worldserver.getCubes(entitycreature);
        }).findFirst();

        optional.ifPresent((blockposition1) -> {
            entitycreature.getBehaviorController().a(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(blockposition1, this.a, 0)));
        });
    }
}

package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;

public class BehaviorGateSingle<E extends EntityLiving> extends BehaviorGate<E> {

    public BehaviorGateSingle(List<Pair<Behavior<? super E>, Integer>> list) {
        this(ImmutableSet.of(), list);
    }

    public BehaviorGateSingle(Set<Pair<MemoryModuleType<?>, MemoryStatus>> set, List<Pair<Behavior<? super E>, Integer>> list) {
        super(set, ImmutableSet.of(), BehaviorGate.Order.SHUFFLED, BehaviorGate.Execution.RUN_ONE, list);
    }
}

package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Set;

public class BehaviourHide extends Behavior<EntityLiving> {

    private final int a;
    private final int b;
    private int c;

    public BehaviourHide(int i, int j) {
        this.b = i * 20;
        this.c = 0;
        this.a = j;
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.HIDING_PLACE, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.HEARD_BELL_TIME, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        Optional<Long> optional = behaviorcontroller.c(MemoryModuleType.HEARD_BELL_TIME);
        boolean flag = (Long) optional.get() + 300L <= i;

        if (this.c <= this.b && !flag) {
            BlockPosition blockposition = ((GlobalPos) behaviorcontroller.c(MemoryModuleType.HIDING_PLACE).get()).b();

            if (blockposition.a((BaseBlockPosition) (new BlockPosition(entityliving)), (double) (this.a + 1))) {
                ++this.c;
            }

        } else {
            behaviorcontroller.b(MemoryModuleType.HEARD_BELL_TIME);
            behaviorcontroller.b(MemoryModuleType.HIDING_PLACE);
            behaviorcontroller.a(worldserver.getDayTime(), worldserver.getTime());
            this.c = 0;
        }
    }
}

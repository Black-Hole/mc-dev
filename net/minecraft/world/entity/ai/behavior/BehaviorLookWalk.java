package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class BehaviorLookWalk extends Behavior<EntityLiving> {

    private final Function<EntityLiving, Float> speedModifier;
    private final int closeEnoughDistance;

    public BehaviorLookWalk(float f, int i) {
        this((entityliving) -> {
            return f;
        }, i);
    }

    public BehaviorLookWalk(Function<EntityLiving, Float> function, int i) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT));
        this.speedModifier = function;
        this.closeEnoughDistance = i;
    }

    @Override
    protected void a(WorldServer worldserver, EntityLiving entityliving, long i) {
        BehaviorController<?> behaviorcontroller = entityliving.getBehaviorController();
        BehaviorPosition behaviorposition = (BehaviorPosition) behaviorcontroller.getMemory(MemoryModuleType.LOOK_TARGET).get();

        behaviorcontroller.setMemory(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(behaviorposition, (Float) this.speedModifier.apply(entityliving), this.closeEnoughDistance)));
    }
}

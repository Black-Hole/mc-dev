package net.minecraft.world.entity.ai.behavior;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class RandomSwim extends BehaviorStrollRandomUnconstrained {

    public RandomSwim(float f) {
        super(f);
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        return entitycreature.aO();
    }

    @Override
    protected Vec3D a(EntityCreature entitycreature) {
        Vec3D vec3d = BehaviorUtil.a(entitycreature, this.maxHorizontalDistance, this.maxVerticalDistance);

        return vec3d != null && entitycreature.level.getFluid(new BlockPosition(vec3d)).isEmpty() ? null : vec3d;
    }
}

package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget {

    private final EntityIronGolem golem;
    private EntityLiving potentialTarget;
    private final PathfinderTargetCondition attackTargeting = PathfinderTargetCondition.a().a(64.0D);

    public PathfinderGoalDefendVillage(EntityIronGolem entityirongolem) {
        super(entityirongolem, false, true);
        this.golem = entityirongolem;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        AxisAlignedBB axisalignedbb = this.golem.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
        List<? extends EntityLiving> list = this.golem.level.a(EntityVillager.class, this.attackTargeting, (EntityLiving) this.golem, axisalignedbb);
        List<EntityHuman> list1 = this.golem.level.a(this.attackTargeting, (EntityLiving) this.golem, axisalignedbb);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityLiving entityliving = (EntityLiving) iterator.next();
            EntityVillager entityvillager = (EntityVillager) entityliving;
            Iterator iterator1 = list1.iterator();

            while (iterator1.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator1.next();
                int i = entityvillager.g(entityhuman);

                if (i <= -100) {
                    this.potentialTarget = entityhuman;
                }
            }
        }

        if (this.potentialTarget == null) {
            return false;
        } else if (this.potentialTarget instanceof EntityHuman && (this.potentialTarget.isSpectator() || ((EntityHuman) this.potentialTarget).isCreative())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void c() {
        this.golem.setGoalTarget(this.potentialTarget);
        super.c();
    }
}

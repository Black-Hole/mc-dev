package net.minecraft.world.entity.ai.goal.target;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.scores.ScoreboardTeamBase;

public abstract class PathfinderGoalTarget extends PathfinderGoal {

    private static final int EMPTY_REACH_CACHE = 0;
    private static final int CAN_REACH_CACHE = 1;
    private static final int CANT_REACH_CACHE = 2;
    protected final EntityInsentient mob;
    protected final boolean mustSee;
    private final boolean mustReach;
    private int reachCache;
    private int reachCacheTime;
    private int unseenTicks;
    protected EntityLiving targetMob;
    protected int unseenMemoryTicks;

    public PathfinderGoalTarget(EntityInsentient entityinsentient, boolean flag) {
        this(entityinsentient, flag, false);
    }

    public PathfinderGoalTarget(EntityInsentient entityinsentient, boolean flag, boolean flag1) {
        this.unseenMemoryTicks = 60;
        this.mob = entityinsentient;
        this.mustSee = flag;
        this.mustReach = flag1;
    }

    @Override
    public boolean b() {
        EntityLiving entityliving = this.mob.getGoalTarget();

        if (entityliving == null) {
            entityliving = this.targetMob;
        }

        if (entityliving == null) {
            return false;
        } else if (!this.mob.c(entityliving)) {
            return false;
        } else {
            ScoreboardTeamBase scoreboardteambase = this.mob.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();

            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
                return false;
            } else {
                double d0 = this.k();

                if (this.mob.f((Entity) entityliving) > d0 * d0) {
                    return false;
                } else {
                    if (this.mustSee) {
                        if (this.mob.getEntitySenses().a(entityliving)) {
                            this.unseenTicks = 0;
                        } else if (++this.unseenTicks > this.unseenMemoryTicks) {
                            return false;
                        }
                    }

                    this.mob.setGoalTarget(entityliving);
                    return true;
                }
            }
        }
    }

    protected double k() {
        return this.mob.b(GenericAttributes.FOLLOW_RANGE);
    }

    @Override
    public void c() {
        this.reachCache = 0;
        this.reachCacheTime = 0;
        this.unseenTicks = 0;
    }

    @Override
    public void d() {
        this.mob.setGoalTarget((EntityLiving) null);
        this.targetMob = null;
    }

    protected boolean a(@Nullable EntityLiving entityliving, PathfinderTargetCondition pathfindertargetcondition) {
        if (entityliving == null) {
            return false;
        } else if (!pathfindertargetcondition.a(this.mob, entityliving)) {
            return false;
        } else if (!this.mob.a(entityliving.getChunkCoordinates())) {
            return false;
        } else {
            if (this.mustReach) {
                if (--this.reachCacheTime <= 0) {
                    this.reachCache = 0;
                }

                if (this.reachCache == 0) {
                    this.reachCache = this.a(entityliving) ? 1 : 2;
                }

                if (this.reachCache == 2) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean a(EntityLiving entityliving) {
        this.reachCacheTime = 10 + this.mob.getRandom().nextInt(5);
        PathEntity pathentity = this.mob.getNavigation().a((Entity) entityliving, 0);

        if (pathentity == null) {
            return false;
        } else {
            PathPoint pathpoint = pathentity.d();

            if (pathpoint == null) {
                return false;
            } else {
                int i = pathpoint.x - entityliving.cW();
                int j = pathpoint.z - entityliving.dc();

                return (double) (i * i + j * j) <= 2.25D;
            }
        }
    }

    public PathfinderGoalTarget a(int i) {
        this.unseenMemoryTicks = i;
        return this;
    }
}

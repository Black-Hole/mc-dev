package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.Vec3D;

public class RamTarget<E extends EntityCreature> extends Behavior<E> {

    public static final int TIME_OUT_DURATION = 200;
    public static final float RAM_SPEED_FORCE_FACTOR = 1.65F;
    private final Function<E, UniformInt> getTimeBetweenRams;
    private final PathfinderTargetCondition ramTargeting;
    private final ToIntFunction<E> getDamage;
    private final float speed;
    private final ToDoubleFunction<E> getKnockbackForce;
    private Vec3D ramDirection;
    private final Function<E, SoundEffect> getImpactSound;

    public RamTarget(Function<E, UniformInt> function, PathfinderTargetCondition pathfindertargetcondition, ToIntFunction<E> tointfunction, float f, ToDoubleFunction<E> todoublefunction, Function<E, SoundEffect> function1) {
        super(ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), 200);
        this.getTimeBetweenRams = function;
        this.ramTargeting = pathfindertargetcondition;
        this.getDamage = tointfunction;
        this.speed = f;
        this.getKnockbackForce = todoublefunction;
        this.getImpactSound = function1;
        this.ramDirection = Vec3D.ZERO;
    }

    protected boolean a(WorldServer worldserver, EntityCreature entitycreature) {
        return entitycreature.getBehaviorController().hasMemory(MemoryModuleType.RAM_TARGET);
    }

    protected boolean b(WorldServer worldserver, EntityCreature entitycreature, long i) {
        return entitycreature.getBehaviorController().hasMemory(MemoryModuleType.RAM_TARGET);
    }

    protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
        BlockPosition blockposition = entitycreature.getChunkCoordinates();
        BehaviorController<?> behaviorcontroller = entitycreature.getBehaviorController();
        Vec3D vec3d = (Vec3D) behaviorcontroller.getMemory(MemoryModuleType.RAM_TARGET).get();

        this.ramDirection = (new Vec3D((double) blockposition.getX() - vec3d.getX(), 0.0D, (double) blockposition.getZ() - vec3d.getZ())).d();
        behaviorcontroller.setMemory(MemoryModuleType.WALK_TARGET, (Object) (new MemoryTarget(vec3d, this.speed, 0)));
    }

    protected void d(WorldServer worldserver, E e0, long i) {
        List<EntityLiving> list = worldserver.a(EntityLiving.class, this.ramTargeting, (EntityLiving) e0, e0.getBoundingBox());
        BehaviorController<?> behaviorcontroller = e0.getBehaviorController();

        if (!list.isEmpty()) {
            EntityLiving entityliving = (EntityLiving) list.get(0);

            entityliving.damageEntity(DamageSource.mobAttack(e0).r(), (float) this.getDamage.applyAsInt(e0));
            float f = entityliving.applyBlockingModifier(DamageSource.mobAttack(e0)) ? 0.5F : 1.0F;
            float f1 = MathHelper.a(e0.ev() * 1.65F, 0.2F, 3.0F);

            entityliving.p((double) (f * f1) * this.getKnockbackForce.applyAsDouble(e0), this.ramDirection.getX(), this.ramDirection.getZ());
            this.b(worldserver, e0);
            worldserver.playSound((EntityHuman) null, (Entity) e0, (SoundEffect) this.getImpactSound.apply(e0), SoundCategory.HOSTILE, 1.0F, 1.0F);
        } else {
            Optional<MemoryTarget> optional = behaviorcontroller.getMemory(MemoryModuleType.WALK_TARGET);
            Optional<Vec3D> optional1 = behaviorcontroller.getMemory(MemoryModuleType.RAM_TARGET);
            boolean flag = !optional.isPresent() || !optional1.isPresent() || ((MemoryTarget) optional.get()).a().a().f((Vec3D) optional1.get()) < 0.25D;

            if (flag) {
                this.b(worldserver, e0);
            }
        }

    }

    protected void b(WorldServer worldserver, E e0) {
        worldserver.broadcastEntityEffect(e0, (byte) 59);
        e0.getBehaviorController().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, (Object) ((UniformInt) this.getTimeBetweenRams.apply(e0)).a(worldserver.random));
        e0.getBehaviorController().removeMemory(MemoryModuleType.RAM_TARGET);
    }
}

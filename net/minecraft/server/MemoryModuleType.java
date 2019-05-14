package net.minecraft.server;

import com.mojang.datafixers.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MemoryModuleType<U> {

    public static final MemoryModuleType<Void> DUMMY = a("dummy", Optional.empty());
    public static final MemoryModuleType<GlobalPos> HOME = a("home", Optional.of(GlobalPos::a));
    public static final MemoryModuleType<GlobalPos> JOB_SITE = a("job_site", Optional.of(GlobalPos::a));
    public static final MemoryModuleType<GlobalPos> MEETING_POINT = a("meeting_point", Optional.of(GlobalPos::a));
    public static final MemoryModuleType<List<GlobalPos>> SECONDARY_JOB_SITE = a("secondary_job_site", Optional.empty());
    public static final MemoryModuleType<List<EntityLiving>> MOBS = a("mobs", Optional.empty());
    public static final MemoryModuleType<List<EntityLiving>> VISIBLE_MOBS = a("visible_mobs", Optional.empty());
    public static final MemoryModuleType<List<EntityLiving>> VISIBLE_VILLAGER_BABIES = a("visible_villager_babies", Optional.empty());
    public static final MemoryModuleType<List<EntityHuman>> NEAREST_PLAYERS = a("nearest_players", Optional.empty());
    public static final MemoryModuleType<EntityHuman> NEAREST_VISIBLE_PLAYER = a("nearest_visible_player", Optional.empty());
    public static final MemoryModuleType<MemoryTarget> WALK_TARGET = a("walk_target", Optional.empty());
    public static final MemoryModuleType<BehaviorPosition> LOOK_TARGET = a("look_target", Optional.empty());
    public static final MemoryModuleType<EntityLiving> INTERACTION_TARGET = a("interaction_target", Optional.empty());
    public static final MemoryModuleType<EntityVillager> BREED_TARGET = a("breed_target", Optional.empty());
    public static final MemoryModuleType<PathEntity> PATH = a("path", Optional.empty());
    public static final MemoryModuleType<List<GlobalPos>> INTERACTABLE_DOORS = a("interactable_doors", Optional.empty());
    public static final MemoryModuleType<BlockPosition> NEAREST_BED = a("nearest_bed", Optional.empty());
    public static final MemoryModuleType<DamageSource> HURT_BY = a("hurt_by", Optional.empty());
    public static final MemoryModuleType<EntityLiving> HURT_BY_ENTITY = a("hurt_by_entity", Optional.empty());
    public static final MemoryModuleType<EntityLiving> NEAREST_HOSTILE = a("nearest_hostile", Optional.empty());
    public static final MemoryModuleType<EntityVillager.a> GOLEM_SPAWN_CONDITIONS = a("golem_spawn_conditions", Optional.empty());
    public static final MemoryModuleType<GlobalPos> HIDING_PLACE = a("hiding_place", Optional.empty());
    public static final MemoryModuleType<Long> HEARD_BELL_TIME = a("heard_bell_time", Optional.empty());
    public static final MemoryModuleType<Long> CANT_REACH_WALK_TARGET_SINCE = a("cant_reach_walk_target_since", Optional.empty());
    private final Optional<Function<Dynamic<?>, U>> y;

    private MemoryModuleType(Optional<Function<Dynamic<?>, U>> optional) {
        this.y = optional;
    }

    public String toString() {
        return IRegistry.MEMORY_MODULE_TYPE.getKey(this).toString();
    }

    public Optional<Function<Dynamic<?>, U>> a() {
        return this.y;
    }

    private static <U> MemoryModuleType<U> a(String s, Optional<Function<Dynamic<?>, U>> optional) {
        return (MemoryModuleType) IRegistry.a((IRegistry) IRegistry.MEMORY_MODULE_TYPE, new MinecraftKey(s), (Object) (new MemoryModuleType<>(optional)));
    }
}

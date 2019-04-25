package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class IRegistry<T> implements Registry<T> {

    protected static final Logger LOGGER = LogManager.getLogger();
    private static final Map<MinecraftKey, Supplier<?>> a = Maps.newLinkedHashMap();
    public static final IRegistryWritable<IRegistryWritable<?>> f = new RegistryMaterials<>();
    public static final IRegistry<SoundEffect> SOUND_EVENT = a("sound_event", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return SoundEffects.ENTITY_ITEM_PICKUP;
    });
    public static final RegistryBlocks<FluidType> FLUID = (RegistryBlocks) a("fluid", (IRegistryWritable) (new RegistryBlocks<>("empty")), () -> {
        return FluidTypes.EMPTY;
    });
    public static final IRegistry<MobEffectList> MOB_EFFECT = a("mob_effect", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return MobEffects.LUCK;
    });
    public static final RegistryBlocks<Block> BLOCK = (RegistryBlocks) a("block", (IRegistryWritable) (new RegistryBlocks<>("air")), () -> {
        return Blocks.AIR;
    });
    public static final IRegistry<Enchantment> ENCHANTMENT = a("enchantment", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Enchantments.LOOT_BONUS_BLOCKS;
    });
    public static final RegistryBlocks<EntityTypes<?>> ENTITY_TYPE = (RegistryBlocks) a("entity_type", (IRegistryWritable) (new RegistryBlocks<>("pig")), () -> {
        return EntityTypes.PIG;
    });
    public static final RegistryBlocks<Item> ITEM = (RegistryBlocks) a("item", (IRegistryWritable) (new RegistryBlocks<>("air")), () -> {
        return Items.AIR;
    });
    public static final RegistryBlocks<PotionRegistry> POTION = (RegistryBlocks) a("potion", (IRegistryWritable) (new RegistryBlocks<>("empty")), () -> {
        return Potions.EMPTY;
    });
    public static final IRegistry<WorldGenCarverAbstract<?>> CARVER = a("carver", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenCarverAbstract.a;
    });
    public static final IRegistry<WorldGenSurface<?>> SURFACE_BUILDER = a("surface_builder", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenSurface.G;
    });
    public static final IRegistry<WorldGenerator<?>> FEATURE = a("feature", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenerator.ORE;
    });
    public static final IRegistry<WorldGenDecorator<?>> DECORATOR = a("decorator", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenDecorator.h;
    });
    public static final IRegistry<BiomeBase> BIOME = a("biome", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Biomes.b;
    });
    public static final IRegistry<Particle<? extends ParticleParam>> PARTICLE_TYPE = a("particle_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Particles.BLOCK;
    });
    public static final IRegistry<BiomeLayout<?, ?>> BIOME_SOURCE_TYPE = a("biome_source_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return BiomeLayout.c;
    });
    public static final IRegistry<TileEntityTypes<?>> BLOCK_ENTITY_TYPE = a("block_entity_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return TileEntityTypes.FURNACE;
    });
    public static final IRegistry<ChunkGeneratorType<?, ?>> CHUNK_GENERATOR_TYPE = a("chunk_generator_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return ChunkGeneratorType.e;
    });
    public static final IRegistry<DimensionManager> DIMENSION_TYPE = a("dimension_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return DimensionManager.OVERWORLD;
    });
    public static final RegistryBlocks<Paintings> MOTIVE = (RegistryBlocks) a("motive", (IRegistryWritable) (new RegistryBlocks<>("kebab")), () -> {
        return Paintings.a;
    });
    public static final IRegistry<MinecraftKey> CUSTOM_STAT = a("custom_stat", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return StatisticList.JUMP;
    });
    public static final RegistryBlocks<ChunkStatus> CHUNK_STATUS = (RegistryBlocks) a("chunk_status", (IRegistryWritable) (new RegistryBlocks<>("empty")), () -> {
        return ChunkStatus.EMPTY;
    });
    public static final IRegistry<StructureGenerator<?>> STRUCTURE_FEATURE = a("structure_feature", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenFactory.a;
    });
    public static final IRegistry<WorldGenFeatureStructurePieceType> STRUCTURE_PIECE = a("structure_piece", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenFeatureStructurePieceType.c;
    });
    public static final IRegistry<DefinedStructureRuleTestType> RULE_TEST = a("rule_test", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return DefinedStructureRuleTestType.b;
    });
    public static final IRegistry<DefinedStructureStructureProcessorType> STRUCTURE_PROCESSOR = a("structure_processor", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return DefinedStructureStructureProcessorType.b;
    });
    public static final IRegistry<WorldGenFeatureDefinedStructurePools> STRUCTURE_POOL_ELEMENT = a("structure_pool_element", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return WorldGenFeatureDefinedStructurePools.e;
    });
    public static final IRegistry<Containers<?>> MENU = a("menu", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Containers.ANVIL;
    });
    public static final IRegistry<Recipes<?>> RECIPE_TYPE = a("recipe_type", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Recipes.CRAFTING;
    });
    public static final IRegistry<RecipeSerializer<?>> RECIPE_SERIALIZER = a("recipe_serializer", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return RecipeSerializer.b;
    });
    public static final IRegistry<StatisticWrapper<?>> STATS = a("stat_type", (IRegistryWritable) (new RegistryMaterials<>()));
    public static final RegistryBlocks<VillagerType> VILLAGER_TYPE = (RegistryBlocks) a("villager_type", (IRegistryWritable) (new RegistryBlocks<>("plains")), () -> {
        return VillagerType.c;
    });
    public static final RegistryBlocks<VillagerProfession> VILLAGER_PROFESSION = (RegistryBlocks) a("villager_profession", (IRegistryWritable) (new RegistryBlocks<>("none")), () -> {
        return VillagerProfession.NONE;
    });
    public static final RegistryBlocks<VillagePlaceType> POINT_OF_INTEREST_TYPE = (RegistryBlocks) a("point_of_interest_type", (IRegistryWritable) (new RegistryBlocks<>("unemployed")), () -> {
        return VillagePlaceType.b;
    });
    public static final RegistryBlocks<MemoryModuleType<?>> MEMORY_MODULE_TYPE = (RegistryBlocks) a("memory_module_type", (IRegistryWritable) (new RegistryBlocks<>("dummy")), () -> {
        return MemoryModuleType.DUMMY;
    });
    public static final RegistryBlocks<SensorType<?>> SENSOR_TYPE = (RegistryBlocks) a("sensor_type", (IRegistryWritable) (new RegistryBlocks<>("dummy")), () -> {
        return SensorType.a;
    });
    public static final RegistryMaterials<Schedule> SCHEDULE = (RegistryMaterials) a("schedule", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Schedule.a;
    });
    public static final RegistryMaterials<Activity> ACTIVITY = (RegistryMaterials) a("activity", (IRegistryWritable) (new RegistryMaterials<>()), () -> {
        return Activity.b;
    });

    public IRegistry() {}

    private static <T> void a(String s, Supplier<T> supplier) {
        IRegistry.a.put(new MinecraftKey(s), supplier);
    }

    private static <T, R extends IRegistryWritable<T>> R a(String s, R r0, Supplier<T> supplier) {
        a(s, supplier);
        a(s, r0);
        return r0;
    }

    private static <T> IRegistry<T> a(String s, IRegistryWritable<T> iregistrywritable) {
        IRegistry.f.a(new MinecraftKey(s), (Object) iregistrywritable);
        return iregistrywritable;
    }

    @Nullable
    public abstract MinecraftKey getKey(T t0);

    public abstract int a(@Nullable T t0);

    @Nullable
    public abstract T get(@Nullable MinecraftKey minecraftkey);

    public abstract Optional<T> getOptional(@Nullable MinecraftKey minecraftkey);

    public abstract Set<MinecraftKey> keySet();

    @Nullable
    public abstract T a(Random random);

    public Stream<T> d() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public static <T> T a(IRegistry<? super T> iregistry, String s, T t0) {
        return a(iregistry, new MinecraftKey(s), t0);
    }

    public static <T> T a(IRegistry<? super T> iregistry, MinecraftKey minecraftkey, T t0) {
        return ((IRegistryWritable) iregistry).a(minecraftkey, t0);
    }

    public static <T> T a(IRegistry<? super T> iregistry, int i, String s, T t0) {
        return ((IRegistryWritable) iregistry).a(i, new MinecraftKey(s), t0);
    }

    static {
        IRegistry.a.entrySet().forEach((entry) -> {
            if (((Supplier) entry.getValue()).get() == null) {
                IRegistry.LOGGER.error("Unable to bootstrap registry '{}'", entry.getKey());
            }

        });
        IRegistry.f.forEach((iregistrywritable) -> {
            if (iregistrywritable.c()) {
                IRegistry.LOGGER.error("Registry '{}' was empty after loading", IRegistry.f.getKey(iregistrywritable));
                if (SharedConstants.b) {
                    throw new IllegalStateException("Registry: '" + IRegistry.f.getKey(iregistrywritable) + "' is empty, not allowed, fix me!");
                }
            }

            if (iregistrywritable instanceof RegistryBlocks) {
                MinecraftKey minecraftkey = ((RegistryBlocks) iregistrywritable).a();

                Validate.notNull(iregistrywritable.get(minecraftkey), "Missing default of DefaultedMappedRegistry: " + minecraftkey, new Object[0]);
            }

        });
    }
}

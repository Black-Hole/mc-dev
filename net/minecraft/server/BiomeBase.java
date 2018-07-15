package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeBase {

    public static final Logger a = LogManager.getLogger();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> b = new WorldGenCaves();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> c = new WorldGenCavesHell();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> d = new WorldGenCanyon();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> e = new WorldGenCanyonOcean();
    public static final WorldGenCarverAbstract<WorldGenFeatureConfigurationChance> f = new WorldGenCavesOcean();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> g = new WorldGenDecoratorHeight();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> h = new WorldGenDecoratorSkyVisible();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> i = new WorldGenDecoratorHeight32();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> j = new WorldGenDecoratorHeightDouble();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> k = new WorldGenDecoratorHeight64();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorNoiseConfiguration> l = new WorldGenDecoratorNoiseHeight32();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorNoiseConfiguration> m = new WorldGenDecoratorNoiseHeightDouble();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> n = new WorldGenDecoratorEmpty();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> o = new WorldGenDecoratorChunkCenter();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> p = new WorldGenDecoratorChance();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> q = new WorldGenDecoratorChanceHeight();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> r = new WorldGenDecoratorChancePass();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> s = new WorldGenDecoratorSkyVisibleBiased();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyExtraChanceConfiguration> t = new WorldGenDecoratorHeightExtraChance();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> u = new WorldGenDecoratorNetherHeight();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> v = new WorldGenDecoratorHeightBiased();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> w = new WorldGenDecoratorHeightBiased2();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> x = new WorldGenDecoratorNetherRandomCount();
    public static final WorldGenDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> y = new WorldGenDecoratorNetherChance();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyChanceConfiguration> z = new WorldGenFeatureChanceDecorator();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyChanceConfiguration> A = new WorldGenFeatureChanceDecoratorHeight();
    public static final WorldGenDecorator<WorldGenDecoratorHeightAverageConfiguration> B = new WorldGenDecoratorHeightAverage();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> C = new WorldGenDecoratorSolidTop();
    public static final WorldGenDecorator<WorldGenDecoratorRangeConfiguration> D = new WorldGenDecoratorSolidTopHeight();
    public static final WorldGenDecorator<WorldGenDecoratorNoiseConfiguration> E = new WorldGenDecoratorSolidTopNoise();
    public static final WorldGenDecorator<WorldGenDecoratorCarveMaskConfiguration> F = new WorldGenDecoratorCarveMask();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> G = new WorldGenDecoratorForestRock();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> H = new WorldGenDecoratorNetherFire();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> I = new WorldGenDecoratorNetherMagma();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> J = new WorldGenDecoratorEmerald();
    public static final WorldGenDecorator<WorldGenDecoratorLakeChanceConfiguration> K = new WorldGenDecoratorLakeLava();
    public static final WorldGenDecorator<WorldGenDecoratorLakeChanceConfiguration> L = new WorldGenDecoratorLakeWater();
    public static final WorldGenDecorator<WorldGenDecoratorDungeonConfiguration> M = new WorldGenDecoratorDungeon();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> N = new WorldGenDecoratorRoofedTree();
    public static final WorldGenDecorator<WorldGenDecoratorChanceConfiguration> O = new WorldGenDecoratorIceburg();
    public static final WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> P = new WorldGenDecoratorNetherGlowstone();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> Q = new WorldGenDecoratorSpike();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> R = new WorldGenDecoratorEndIsland();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> S = new WorldGenDecoratorChorusPlant();
    public static final WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> T = new WorldGenDecoratorEndGateway();
    protected static final IBlockData U = Blocks.AIR.getBlockData();
    protected static final IBlockData V = Blocks.DIRT.getBlockData();
    protected static final IBlockData W = Blocks.GRASS_BLOCK.getBlockData();
    protected static final IBlockData X = Blocks.PODZOL.getBlockData();
    protected static final IBlockData Y = Blocks.GRAVEL.getBlockData();
    protected static final IBlockData Z = Blocks.STONE.getBlockData();
    protected static final IBlockData aa = Blocks.COARSE_DIRT.getBlockData();
    protected static final IBlockData ab = Blocks.SAND.getBlockData();
    protected static final IBlockData ac = Blocks.RED_SAND.getBlockData();
    protected static final IBlockData ad = Blocks.WHITE_TERRACOTTA.getBlockData();
    protected static final IBlockData ae = Blocks.MYCELIUM.getBlockData();
    protected static final IBlockData af = Blocks.NETHERRACK.getBlockData();
    protected static final IBlockData ag = Blocks.END_STONE.getBlockData();
    public static final WorldGenSurfaceConfigurationBase ah = new WorldGenSurfaceConfigurationBase(BiomeBase.U, BiomeBase.U, BiomeBase.U);
    public static final WorldGenSurfaceConfigurationBase ai = new WorldGenSurfaceConfigurationBase(BiomeBase.V, BiomeBase.V, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase aj = new WorldGenSurfaceConfigurationBase(BiomeBase.W, BiomeBase.V, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase ak = new WorldGenSurfaceConfigurationBase(BiomeBase.Z, BiomeBase.Z, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase al = new WorldGenSurfaceConfigurationBase(BiomeBase.Y, BiomeBase.Y, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase am = new WorldGenSurfaceConfigurationBase(BiomeBase.aa, BiomeBase.V, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase an = new WorldGenSurfaceConfigurationBase(BiomeBase.X, BiomeBase.V, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase ao = new WorldGenSurfaceConfigurationBase(BiomeBase.ab, BiomeBase.ab, BiomeBase.ab);
    public static final WorldGenSurfaceConfigurationBase ap = new WorldGenSurfaceConfigurationBase(BiomeBase.W, BiomeBase.V, BiomeBase.ab);
    public static final WorldGenSurfaceConfigurationBase aq = new WorldGenSurfaceConfigurationBase(BiomeBase.ab, BiomeBase.ab, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase ar = new WorldGenSurfaceConfigurationBase(BiomeBase.ac, BiomeBase.ad, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase as = new WorldGenSurfaceConfigurationBase(BiomeBase.ae, BiomeBase.V, BiomeBase.Y);
    public static final WorldGenSurfaceConfigurationBase at = new WorldGenSurfaceConfigurationBase(BiomeBase.af, BiomeBase.af, BiomeBase.af);
    public static final WorldGenSurfaceConfigurationBase au = new WorldGenSurfaceConfigurationBase(BiomeBase.ag, BiomeBase.ag, BiomeBase.ag);
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> av = new WorldGenSurfaceDefaultBlock();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aw = new WorldGenSurfaceExtremeHills();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> ax = new WorldGenSurfaceSavannaMutated();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> ay = new WorldGenSurfaceExtremeHillMutated();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> az = new WorldGenSurfaceTaigaMega();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aA = new WorldGenSurfaceSwamp();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aB = new WorldGenSurfaceMesa();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aC = new WorldGenSurfaceMesaForest();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aD = new WorldGenSurfaceMesaBryce();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aE = new WorldGenSurfaceFrozenOcean();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aF = new WorldGenSurfaceNether();
    public static final WorldGenSurface<WorldGenSurfaceConfigurationBase> aG = new WorldGenSurfaceEmpty();
    public static final Set<BiomeBase> aH = Sets.newHashSet();
    public static final RegistryBlockID<BiomeBase> aI = new RegistryBlockID();
    protected static final NoiseGenerator3 aJ = new NoiseGenerator3(new Random(1234L), 1);
    public static final NoiseGenerator3 aK = new NoiseGenerator3(new Random(2345L), 1);
    public static final RegistryMaterials<MinecraftKey, BiomeBase> REGISTRY_ID = new RegistryMaterials();
    @Nullable
    protected String aM;
    protected final float aN;
    protected final float aO;
    protected final float aP;
    protected final float aQ;
    protected final int aR;
    protected final int aS;
    @Nullable
    protected final String aT;
    protected final WorldGenSurfaceComposite<?> aU;
    protected final BiomeBase.Geography aV;
    protected final BiomeBase.Precipitation aW;
    protected final Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>> aX = Maps.newHashMap();
    protected final Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite<?, ?>>> aY = Maps.newHashMap();
    protected final List<WorldGenFeatureCompositeFlower<?>> aZ = Lists.newArrayList();
    protected final Map<StructureGenerator<?>, WorldGenFeatureConfiguration> ba = Maps.newHashMap();
    private final Map<EnumCreatureType, List<BiomeBase.BiomeMeta>> bb = Maps.newHashMap();

    public static int a(BiomeBase biomebase) {
        return BiomeBase.REGISTRY_ID.a((Object) biomebase);
    }

    @Nullable
    public static BiomeBase a(int i) {
        return (BiomeBase) BiomeBase.REGISTRY_ID.getId(i);
    }

    @Nullable
    public static BiomeBase b(BiomeBase biomebase) {
        return (BiomeBase) BiomeBase.aI.fromId(a(biomebase));
    }

    public static <C extends WorldGenFeatureConfiguration> WorldGenCarverWrapper<C> a(WorldGenCarver<C> worldgencarver, C c0) {
        return new WorldGenCarverWrapper(worldgencarver, c0);
    }

    public static <F extends WorldGenFeatureConfiguration, D extends WorldGenFeatureDecoratorConfiguration> WorldGenFeatureComposite<F, D> a(WorldGenerator<F> worldgenerator, F f0, WorldGenDecorator<D> worldgendecorator, D d0) {
        return new WorldGenFeatureComposite(worldgenerator, f0, worldgendecorator, d0);
    }

    public static <D extends WorldGenFeatureDecoratorConfiguration> WorldGenFeatureCompositeFlower<D> a(WorldGenFlowers worldgenflowers, WorldGenDecorator<D> worldgendecorator, D d0) {
        return new WorldGenFeatureCompositeFlower(worldgenflowers, worldgendecorator, d0);
    }

    protected BiomeBase(BiomeBase.a biomebase_a) {
        if (biomebase_a.a != null && biomebase_a.b != null && biomebase_a.c != null && biomebase_a.d != null && biomebase_a.e != null && biomebase_a.f != null && biomebase_a.g != null && biomebase_a.h != null && biomebase_a.i != null) {
            this.aU = biomebase_a.a;
            this.aW = biomebase_a.b;
            this.aV = biomebase_a.c;
            this.aN = biomebase_a.d.floatValue();
            this.aO = biomebase_a.e.floatValue();
            this.aP = biomebase_a.f.floatValue();
            this.aQ = biomebase_a.g.floatValue();
            this.aR = biomebase_a.h.intValue();
            this.aS = biomebase_a.i.intValue();
            this.aT = biomebase_a.j;
            WorldGenStage.Decoration[] aworldgenstage_decoration = WorldGenStage.Decoration.values();
            int i = aworldgenstage_decoration.length;

            int j;

            for (j = 0; j < i; ++j) {
                WorldGenStage.Decoration worldgenstage_decoration = aworldgenstage_decoration[j];

                this.aY.put(worldgenstage_decoration, Lists.newArrayList());
            }

            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();

            i = aenumcreaturetype.length;

            for (j = 0; j < i; ++j) {
                EnumCreatureType enumcreaturetype = aenumcreaturetype[j];

                this.bb.put(enumcreaturetype, Lists.newArrayList());
            }

        } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome for " + this.getClass().getSimpleName() + "\n" + biomebase_a);
        }
    }

    protected void a() {
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.f, new WorldGenMineshaftConfiguration(0.004000000189989805D, WorldGenMineshaft.Type.NORMAL), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.e, new WorldGenFeatureVillageConfiguration(0, WorldGenVillagePieces.Material.OAK), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.m, new WorldGenFeatureStrongholdConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.l, new WorldGenFeatureSwampHutConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.i, new WorldGenFeatureDesertPyramidConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.h, new WorldGenFeatureJunglePyramidConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.j, new WorldGenFeatureIglooConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.k, new WorldGenFeatureShipwreckConfiguration(false), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.n, new WorldGenMonumentConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.g, new WorldGenMansionConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.o, new WorldGenFeatureOceanRuinConfiguration(WorldGenFeatureOceanRuin.Temperature.COLD, 0.3F, 0.9F), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.r, new WorldGenBuriedTreasureConfiguration(0.01F), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e));
    }

    public boolean b() {
        return this.aT != null;
    }

    protected void a(EnumCreatureType enumcreaturetype, BiomeBase.BiomeMeta biomebase_biomemeta) {
        ((List) this.bb.get(enumcreaturetype)).add(biomebase_biomemeta);
    }

    public List<BiomeBase.BiomeMeta> getMobs(EnumCreatureType enumcreaturetype) {
        return (List) this.bb.get(enumcreaturetype);
    }

    public BiomeBase.Precipitation c() {
        return this.aW;
    }

    public boolean d() {
        return this.getHumidity() > 0.85F;
    }

    public float e() {
        return 0.1F;
    }

    public float c(BlockPosition blockposition) {
        if (blockposition.getY() > 64) {
            float f = (float) (BiomeBase.aJ.a((double) ((float) blockposition.getX() / 8.0F), (double) ((float) blockposition.getZ() / 8.0F)) * 4.0D);

            return this.getTemperature() - (f + (float) blockposition.getY() - 64.0F) * 0.05F / 30.0F;
        } else {
            return this.getTemperature();
        }
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        return this.a(iworldreader, blockposition, true);
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition, boolean flag) {
        if (this.c(blockposition) >= 0.15F) {
            return false;
        } else {
            if (blockposition.getY() >= 0 && blockposition.getY() < 256 && iworldreader.getBrightness(EnumSkyBlock.BLOCK, blockposition) < 10) {
                IBlockData iblockdata = iworldreader.getType(blockposition);
                Fluid fluid = iworldreader.b(blockposition);

                if (fluid.c() == FluidTypes.c && iblockdata.getBlock() instanceof BlockFluids) {
                    if (!flag) {
                        return true;
                    }

                    boolean flag1 = iworldreader.B(blockposition.west()) && iworldreader.B(blockposition.east()) && iworldreader.B(blockposition.north()) && iworldreader.B(blockposition.south());

                    if (!flag1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean b(IWorldReader iworldreader, BlockPosition blockposition) {
        if (this.c(blockposition) >= 0.15F) {
            return false;
        } else {
            if (blockposition.getY() >= 0 && blockposition.getY() < 256 && iworldreader.getBrightness(EnumSkyBlock.BLOCK, blockposition) < 10) {
                IBlockData iblockdata = iworldreader.getType(blockposition);

                if (iblockdata.isAir() && Blocks.SNOW.getBlockData().canPlace(iworldreader, blockposition)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void a(WorldGenStage.Decoration worldgenstage_decoration, WorldGenFeatureComposite<?, ?> worldgenfeaturecomposite) {
        if (worldgenfeaturecomposite instanceof WorldGenFeatureCompositeFlower) {
            this.aZ.add((WorldGenFeatureCompositeFlower) worldgenfeaturecomposite);
        }

        ((List) this.aY.get(worldgenstage_decoration)).add(worldgenfeaturecomposite);
    }

    public <C extends WorldGenFeatureConfiguration> void a(WorldGenStage.Features worldgenstage_features, WorldGenCarverWrapper<C> worldgencarverwrapper) {
        ((List) this.aX.computeIfAbsent(worldgenstage_features, (worldgenstage_features) -> {
            return Lists.newArrayList();
        })).add(worldgencarverwrapper);
    }

    public List<WorldGenCarverWrapper<?>> a(WorldGenStage.Features worldgenstage_features) {
        return (List) this.aX.computeIfAbsent(worldgenstage_features, (worldgenstage_features) -> {
            return Lists.newArrayList();
        });
    }

    public <C extends WorldGenFeatureConfiguration> void a(StructureGenerator<C> structuregenerator, C c0) {
        this.ba.put(structuregenerator, c0);
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(StructureGenerator<C> structuregenerator) {
        return this.ba.containsKey(structuregenerator);
    }

    @Nullable
    public <C extends WorldGenFeatureConfiguration> WorldGenFeatureConfiguration b(StructureGenerator<C> structuregenerator) {
        return (WorldGenFeatureConfiguration) this.ba.get(structuregenerator);
    }

    public List<WorldGenFeatureCompositeFlower<?>> f() {
        return this.aZ;
    }

    public List<WorldGenFeatureComposite<?, ?>> a(WorldGenStage.Decoration worldgenstage_decoration) {
        return (List) this.aY.get(worldgenstage_decoration);
    }

    public void a(WorldGenStage.Decoration worldgenstage_decoration, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, GeneratorAccess generatoraccess, long i, SeededRandom seededrandom, BlockPosition blockposition) {
        int j = 0;

        for (Iterator iterator = ((List) this.aY.get(worldgenstage_decoration)).iterator(); iterator.hasNext(); ++j) {
            WorldGenFeatureComposite worldgenfeaturecomposite = (WorldGenFeatureComposite) iterator.next();

            seededrandom.b(i, j, worldgenstage_decoration.ordinal());
            worldgenfeaturecomposite.a(generatoraccess, chunkgenerator, seededrandom, blockposition, WorldGenFeatureConfiguration.e);
        }

    }

    public void a(Random random, IChunkAccess ichunkaccess, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long i1) {
        this.aU.a(i1);
        this.aU.a(random, ichunkaccess, this, i, j, k, d0, iblockdata, iblockdata1, l, i1, BiomeBase.ah);
    }

    public BiomeBase.EnumTemperature g() {
        return this.aV == BiomeBase.Geography.OCEAN ? BiomeBase.EnumTemperature.OCEAN : ((double) this.getTemperature() < 0.2D ? BiomeBase.EnumTemperature.COLD : ((double) this.getTemperature() < 1.0D ? BiomeBase.EnumTemperature.MEDIUM : BiomeBase.EnumTemperature.WARM));
    }

    @Nullable
    public static BiomeBase getBiome(int i) {
        return a(i);
    }

    public static BiomeBase getBiome(int i, BiomeBase biomebase) {
        BiomeBase biomebase1 = getBiome(i);

        return biomebase1 == null ? biomebase : biomebase1;
    }

    public final float h() {
        return this.aN;
    }

    public final float getHumidity() {
        return this.aQ;
    }

    public String k() {
        if (this.aM == null) {
            this.aM = SystemUtils.a("biome", (MinecraftKey) BiomeBase.REGISTRY_ID.b(this));
        }

        return this.aM;
    }

    public final float l() {
        return this.aO;
    }

    public final float getTemperature() {
        return this.aP;
    }

    public final int n() {
        return this.aR;
    }

    public final int o() {
        return this.aS;
    }

    public final BiomeBase.Geography p() {
        return this.aV;
    }

    public WorldGenSurfaceComposite<?> q() {
        return this.aU;
    }

    public WorldGenSurfaceConfiguration r() {
        return this.aU.a();
    }

    @Nullable
    public String s() {
        return this.aT;
    }

    public static void t() {
        a(0, "ocean", new BiomeOcean());
        a(1, "plains", new BiomePlains());
        a(2, "desert", new BiomeDesert());
        a(3, "mountains", new BiomeBigHills());
        a(4, "forest", new BiomeForest());
        a(5, "taiga", new BiomeTaiga());
        a(6, "swamp", new BiomeSwamp());
        a(7, "river", new BiomeRiver());
        a(8, "nether", new BiomeHell());
        a(9, "the_end", new BiomeTheEnd());
        a(10, "frozen_ocean", new BiomeFrozenOcean());
        a(11, "frozen_river", new BiomeFrozenRiver());
        a(12, "snowy_tundra", new BiomeIcePlains());
        a(13, "snowy_mountains", new BiomeIceMountains());
        a(14, "mushroom_fields", new BiomeMushrooms());
        a(15, "mushroom_field_shore", new BiomeMushroomIslandShore());
        a(16, "beach", new BiomeBeach());
        a(17, "desert_hills", new BiomeDesertHills());
        a(18, "wooded_hills", new BiomeForestHills());
        a(19, "taiga_hills", new BiomeTaigaHills());
        a(20, "mountain_edge", new BiomeExtremeHillsEdge());
        a(21, "jungle", new BiomeJungle());
        a(22, "jungle_hills", new BiomeJungleHills());
        a(23, "jungle_edge", new BiomeJungleEdge());
        a(24, "deep_ocean", new BiomeDeepOcean());
        a(25, "stone_shore", new BiomeStoneBeach());
        a(26, "snowy_beach", new BiomeColdBeach());
        a(27, "birch_forest", new BiomeBirchForest());
        a(28, "birch_forest_hills", new BiomeBirchForestHills());
        a(29, "dark_forest", new BiomeRoofedForest());
        a(30, "snowy_taiga", new BiomeColdTaiga());
        a(31, "snowy_taiga_hills", new BiomeColdTaigaHills());
        a(32, "giant_tree_taiga", new BiomeMegaTaiga());
        a(33, "giant_tree_taiga_hills", new BiomeMegaTaigaHills());
        a(34, "wooded_mountains", new BiomeExtremeHillsWithTrees());
        a(35, "savanna", new BiomeSavanna());
        a(36, "savanna_plateau", new BiomeSavannaPlateau());
        a(37, "badlands", new BiomeMesa());
        a(38, "wooded_badlands_plateau", new BiomeMesaPlataeu());
        a(39, "badlands_plateau", new BiomeMesaPlataeuClear());
        a(40, "small_end_islands", new BiomeTheEndFloatingIslands());
        a(41, "end_midlands", new BiomeTheEndMediumIsland());
        a(42, "end_highlands", new BiomeTheEndHighIsland());
        a(43, "end_barrens", new BiomeTheEndBarrenIsland());
        a(44, "warm_ocean", new BiomeWarmOcean());
        a(45, "lukewarm_ocean", new BiomeLukewarmOcean());
        a(46, "cold_ocean", new BiomeColdOcean());
        a(47, "deep_warm_ocean", new BiomeWarmDeepOcean());
        a(48, "deep_lukewarm_ocean", new BiomeLukewarmDeepOcean());
        a(49, "deep_cold_ocean", new BiomeColdDeepOcean());
        a(50, "deep_frozen_ocean", new BiomeFrozenDeepOcean());
        a(127, "the_void", new BiomeVoid());
        a(129, "sunflower_plains", new BiomeSunflowerPlains());
        a(130, "desert_lakes", new BiomeDesertMutated());
        a(131, "gravelly_mountains", new BiomeExtremeHillsMutated());
        a(132, "flower_forest", new BiomeFlowerForest());
        a(133, "taiga_mountains", new BiomeTaigaMutated());
        a(134, "swamp_hills", new BiomeSwamplandMutated());
        a(140, "ice_spikes", new BiomeIcePlainsSpikes());
        a(149, "modified_jungle", new BiomeJungleMutated());
        a(151, "modified_jungle_edge", new BiomeJungleEdgeMutated());
        a(155, "tall_birch_forest", new BiomeBirchForestMutated());
        a(156, "tall_birch_hills", new BiomeBirchForestHillsMutated());
        a(157, "dark_forest_hills", new BiomeRoofedForestMutated());
        a(158, "snowy_taiga_mountains", new BiomeColdTaigaMutated());
        a(160, "giant_spruce_taiga", new BiomeMegaSpruceTaiga());
        a(161, "giant_spruce_taiga_hills", new BiomeRedwoodTaigaHillsMutated());
        a(162, "modified_gravelly_mountains", new BiomeExtremeHillsWithTreesMutated());
        a(163, "shattered_savanna", new BiomeSavannaMutated());
        a(164, "shattered_savanna_plateau", new BiomeSavannaPlateauMutated());
        a(165, "eroded_badlands", new BiomeMesaBryce());
        a(166, "modified_wooded_badlands_plateau", new BiomeMesaPlateauMutated());
        a(167, "modified_badlands_plateau", new BiomeMesaPlateauClearMutated());
        Collections.addAll(BiomeBase.aH, new BiomeBase[] { Biomes.a, Biomes.c, Biomes.d, Biomes.e, Biomes.f, Biomes.g, Biomes.h, Biomes.i, Biomes.m, Biomes.n, Biomes.o, Biomes.p, Biomes.q, Biomes.r, Biomes.s, Biomes.t, Biomes.u, Biomes.w, Biomes.x, Biomes.y, Biomes.z, Biomes.A, Biomes.B, Biomes.C, Biomes.D, Biomes.E, Biomes.F, Biomes.G, Biomes.H, Biomes.I, Biomes.J, Biomes.K, Biomes.L, Biomes.M, Biomes.N, Biomes.O});
    }

    private static void a(int i, String s, BiomeBase biomebase) {
        BiomeBase.REGISTRY_ID.a(i, new MinecraftKey(s), biomebase);
        if (biomebase.b()) {
            BiomeBase.aI.a(biomebase, a((BiomeBase) BiomeBase.REGISTRY_ID.get(new MinecraftKey(biomebase.aT))));
        }

    }

    public static class a {

        @Nullable
        private WorldGenSurfaceComposite<?> a;
        @Nullable
        private BiomeBase.Precipitation b;
        @Nullable
        private BiomeBase.Geography c;
        @Nullable
        private Float d;
        @Nullable
        private Float e;
        @Nullable
        private Float f;
        @Nullable
        private Float g;
        @Nullable
        private Integer h;
        @Nullable
        private Integer i;
        @Nullable
        private String j;

        public a() {}

        public BiomeBase.a a(WorldGenSurfaceComposite<?> worldgensurfacecomposite) {
            this.a = worldgensurfacecomposite;
            return this;
        }

        public BiomeBase.a a(BiomeBase.Precipitation biomebase_precipitation) {
            this.b = biomebase_precipitation;
            return this;
        }

        public BiomeBase.a a(BiomeBase.Geography biomebase_geography) {
            this.c = biomebase_geography;
            return this;
        }

        public BiomeBase.a a(float f) {
            this.d = Float.valueOf(f);
            return this;
        }

        public BiomeBase.a b(float f) {
            this.e = Float.valueOf(f);
            return this;
        }

        public BiomeBase.a c(float f) {
            this.f = Float.valueOf(f);
            return this;
        }

        public BiomeBase.a d(float f) {
            this.g = Float.valueOf(f);
            return this;
        }

        public BiomeBase.a a(int i) {
            this.h = Integer.valueOf(i);
            return this;
        }

        public BiomeBase.a b(int i) {
            this.i = Integer.valueOf(i);
            return this;
        }

        public BiomeBase.a a(@Nullable String s) {
            this.j = s;
            return this;
        }

        public String toString() {
            return "BiomeBuilder{\nsurfaceBuilder=" + this.a + ",\nprecipitation=" + this.b + ",\nbiomeCategory=" + this.c + ",\ndepth=" + this.d + ",\nscale=" + this.e + ",\ntemperature=" + this.f + ",\ndownfall=" + this.g + ",\nwaterColor=" + this.h + ",\nwaterFogColor=" + this.i + ",\nparent=\'" + this.j + '\'' + "\n" + '}';
        }
    }

    public static class BiomeMeta extends WeightedRandom.WeightedRandomChoice {

        public EntityTypes<? extends EntityInsentient> b;
        public int c;
        public int d;

        public BiomeMeta(EntityTypes<? extends EntityInsentient> entitytypes, int i, int j, int k) {
            super(i);
            this.b = entitytypes;
            this.c = j;
            this.d = k;
        }

        public String toString() {
            return EntityTypes.getName(this.b) + "*(" + this.c + "-" + this.d + "):" + this.a;
        }
    }

    public static enum Precipitation {

        NONE, RAIN, SNOW;

        private Precipitation() {}
    }

    public static enum Geography {

        NONE, TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, THEEND, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM, NETHER;

        private Geography() {}
    }

    public static enum EnumTemperature {

        OCEAN, COLD, MEDIUM, WARM;

        private EnumTemperature() {}
    }
}

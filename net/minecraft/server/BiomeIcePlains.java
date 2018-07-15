package net.minecraft.server;

import com.google.common.collect.Lists;

public final class BiomeIcePlains extends BiomeBase {

    public BiomeIcePlains() {
        super((new BiomeBase.a()).a(new WorldGenSurfaceComposite(BiomeIcePlains.av, BiomeIcePlains.aj)).a(BiomeBase.Precipitation.SNOW).a(BiomeBase.Geography.ICY).a(0.125F).b(0.05F).c(0.0F).d(0.5F).a(4159204).b(329011).a((String) null));
        this.a(WorldGenerator.j, (WorldGenFeatureConfiguration) (new WorldGenFeatureIglooConfiguration()));
        this.a(WorldGenerator.f, (WorldGenFeatureConfiguration) (new WorldGenMineshaftConfiguration(0.004D, WorldGenMineshaft.Type.NORMAL)));
        this.a(WorldGenerator.m, (WorldGenFeatureConfiguration) (new WorldGenFeatureStrongholdConfiguration()));
        this.a(WorldGenStage.Features.AIR, a((WorldGenCarver) BiomeIcePlains.b, (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.14285715F))));
        this.a(WorldGenStage.Features.AIR, a((WorldGenCarver) BiomeIcePlains.d, (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.02F))));
        this.a();
        this.a(WorldGenStage.Decoration.LOCAL_MODIFICATIONS, a(WorldGenerator.am, new WorldGenFeatureLakeConfiguration(Blocks.WATER), BiomeIcePlains.L, new WorldGenDecoratorLakeChanceConfiguration(4)));
        this.a(WorldGenStage.Decoration.LOCAL_MODIFICATIONS, a(WorldGenerator.am, new WorldGenFeatureLakeConfiguration(Blocks.LAVA), BiomeIcePlains.K, new WorldGenDecoratorLakeChanceConfiguration(80)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_STRUCTURES, a(WorldGenerator.ad, WorldGenFeatureConfiguration.e, BiomeIcePlains.M, new WorldGenDecoratorDungeonConfiguration(8)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.DIRT.getBlockData(), 33), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 256)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.GRAVEL.getBlockData(), 33), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(8, 0, 0, 256)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.GRANITE.getBlockData(), 33), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 80)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.DIORITE.getBlockData(), 33), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 80)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.ANDESITE.getBlockData(), 33), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 80)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.COAL_ORE.getBlockData(), 17), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(20, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.IRON_ORE.getBlockData(), 9), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(20, 0, 0, 64)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.GOLD_ORE.getBlockData(), 9), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(2, 0, 0, 32)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.REDSTONE_ORE.getBlockData(), 8), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(8, 0, 0, 16)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.DIAMOND_ORE.getBlockData(), 8), BiomeIcePlains.u, new WorldGenFeatureChanceDecoratorCountConfiguration(1, 0, 0, 16)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.a, Blocks.LAPIS_ORE.getBlockData(), 7), BiomeIcePlains.B, new WorldGenDecoratorHeightAverageConfiguration(1, 16, 16)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.ai, new WorldGenFeatureCircleConfiguration(Blocks.SAND, 7, 2, Lists.newArrayList(new Block[] { Blocks.DIRT, Blocks.GRASS_BLOCK})), BiomeIcePlains.h, new WorldGenDecoratorFrequencyConfiguration(3)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.ai, new WorldGenFeatureCircleConfiguration(Blocks.CLAY, 4, 1, Lists.newArrayList(new Block[] { Blocks.DIRT, Blocks.CLAY})), BiomeIcePlains.h, new WorldGenDecoratorFrequencyConfiguration(1)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_ORES, a(WorldGenerator.ai, new WorldGenFeatureCircleConfiguration(Blocks.GRAVEL, 6, 2, Lists.newArrayList(new Block[] { Blocks.DIRT, Blocks.GRASS_BLOCK})), BiomeIcePlains.h, new WorldGenDecoratorFrequencyConfiguration(1)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.A, WorldGenFeatureConfiguration.e, BiomeIcePlains.t, new WorldGenDecoratorFrequencyExtraChanceConfiguration(0, 0.1F, 1)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, (WorldGenFeatureComposite) a(WorldGenerator.G, BiomeIcePlains.i, new WorldGenDecoratorFrequencyConfiguration(2)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.M, new WorldGenFeatureTallGrassConfiguration(Blocks.GRASS.getBlockData()), BiomeIcePlains.j, new WorldGenDecoratorFrequencyConfiguration(1)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), BiomeIcePlains.q, new WorldGenDecoratorChanceConfiguration(4)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), BiomeIcePlains.q, new WorldGenDecoratorChanceConfiguration(8)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.Z, WorldGenFeatureConfiguration.e, BiomeIcePlains.j, new WorldGenDecoratorFrequencyConfiguration(10)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.Y, WorldGenFeatureConfiguration.e, BiomeIcePlains.q, new WorldGenDecoratorChanceConfiguration(32)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.c), BiomeIcePlains.v, new WorldGenFeatureChanceDecoratorCountConfiguration(50, 8, 8, 256)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.e), BiomeIcePlains.w, new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));
        this.a(WorldGenStage.Decoration.TOP_LAYER_MODIFICATION, a(WorldGenerator.aa, WorldGenFeatureConfiguration.e, BiomeIcePlains.o, WorldGenFeatureDecoratorConfiguration.e));
        this.a(EnumCreatureType.CREATURE, new BiomeBase.BiomeMeta(EntityTypes.RABBIT, 10, 2, 3));
        this.a(EnumCreatureType.CREATURE, new BiomeBase.BiomeMeta(EntityTypes.POLAR_BEAR, 1, 1, 2));
        this.a(EnumCreatureType.AMBIENT, new BiomeBase.BiomeMeta(EntityTypes.BAT, 10, 8, 8));
        this.a(EnumCreatureType.WATER_CREATURE, new BiomeBase.BiomeMeta(EntityTypes.SQUID, 10, 1, 2));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.SPIDER, 100, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ZOMBIE, 95, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ZOMBIE_VILLAGER, 5, 1, 1));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.CREEPER, 100, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.SLIME, 100, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ENDERMAN, 10, 1, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.WITCH, 5, 1, 1));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.SKELETON, 20, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.STRAY, 80, 4, 4));
    }

    public float e() {
        return 0.07F;
    }
}

package net.minecraft.server;

public final class BiomeHell extends BiomeBase {

    protected BiomeHell() {
        super((new BiomeBase.a()).a(new WorldGenSurfaceComposite(BiomeHell.aF, BiomeHell.at)).a(BiomeBase.Precipitation.NONE).a(BiomeBase.Geography.NETHER).a(0.1F).b(0.2F).c(2.0F).d(0.0F).a(4159204).b(329011).a((String) null));
        this.a(WorldGenerator.p, (WorldGenFeatureConfiguration) (new WorldGenNetherConfiguration()));
        this.a(WorldGenStage.Features.AIR, a((WorldGenCarver) BiomeHell.c, (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.2F))));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.e), BiomeHell.w, new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), BiomeHell.q, new WorldGenDecoratorChanceConfiguration(4)));
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), BiomeHell.q, new WorldGenDecoratorChanceConfiguration(8)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.p, new WorldGenNetherConfiguration(), BiomeHell.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(false), BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.S, WorldGenFeatureConfiguration.e, BiomeHell.H, new WorldGenDecoratorFrequencyConfiguration(10)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.W, WorldGenFeatureConfiguration.e, BiomeHell.P, new WorldGenDecoratorFrequencyConfiguration(10)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.W, WorldGenFeatureConfiguration.e, BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), BiomeHell.y, new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), BiomeHell.y, new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14), BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK), Blocks.MAGMA_BLOCK.getBlockData(), 33), BiomeHell.I, new WorldGenDecoratorFrequencyConfiguration(4)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(true), BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.GHAST, 50, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ZOMBIE_PIGMAN, 100, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.MAGMA_CUBE, 2, 4, 4));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ENDERMAN, 1, 4, 4));
    }
}

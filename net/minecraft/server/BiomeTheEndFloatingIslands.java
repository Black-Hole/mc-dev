package net.minecraft.server;

public class BiomeTheEndFloatingIslands extends BiomeBase {

    public BiomeTheEndFloatingIslands() {
        super((new BiomeBase.a()).a(new WorldGenSurfaceComposite(BiomeTheEndFloatingIslands.av, BiomeTheEndFloatingIslands.au)).a(BiomeBase.Precipitation.NONE).a(BiomeBase.Geography.THEEND).a(0.1F).b(0.2F).c(0.5F).d(0.5F).a(4159204).b(329011).a((String) null));
        this.a(WorldGenStage.Decoration.RAW_GENERATION, a(WorldGenerator.av, WorldGenFeatureConfiguration.e, BiomeTheEndFloatingIslands.R, WorldGenFeatureDecoratorConfiguration.e));
        this.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, a(WorldGenerator.q, new WorldGenEndCityConfiguration(), BiomeTheEndFloatingIslands.n, WorldGenFeatureDecoratorConfiguration.e));
        this.a(EnumCreatureType.MONSTER, new BiomeBase.BiomeMeta(EntityTypes.ENDERMAN, 10, 4, 4));
    }
}

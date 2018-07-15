package net.minecraft.server;

import java.util.Random;

public class WorldGenDecoratorChunkCenter extends WorldGenDecorator<WorldGenFeatureDecoratorEmptyConfiguration> {

    public WorldGenDecoratorChunkCenter() {}

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureDecoratorEmptyConfiguration worldgenfeaturedecoratoremptyconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(8, 0, 8), c0);
        return true;
    }
}

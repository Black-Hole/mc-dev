package net.minecraft.world.level.levelgen.carver;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class CarvingContext implements WorldGenerationContext {

    private final ChunkGenerator generator;

    public CarvingContext(ChunkGenerator chunkgenerator) {
        this.generator = chunkgenerator;
    }

    @Override
    public int a() {
        return this.generator.getMinY();
    }

    @Override
    public int b() {
        return this.generator.getGenerationDepth();
    }
}

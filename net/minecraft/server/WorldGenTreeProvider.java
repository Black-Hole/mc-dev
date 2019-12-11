package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public abstract class WorldGenTreeProvider {

    public WorldGenTreeProvider() {}

    @Nullable
    protected abstract WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> b(Random random);

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<?> chunkgenerator, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> worldgenfeatureconfigured = this.b(random);

        if (worldgenfeatureconfigured == null) {
            return false;
        } else {
            generatoraccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 4);
            ((WorldGenFeatureSmallTreeConfigurationConfiguration) worldgenfeatureconfigured.c).a();
            if (worldgenfeatureconfigured.a(generatoraccess, chunkgenerator, random, blockposition)) {
                return true;
            } else {
                generatoraccess.setTypeAndData(blockposition, iblockdata, 4);
                return false;
            }
        }
    }
}

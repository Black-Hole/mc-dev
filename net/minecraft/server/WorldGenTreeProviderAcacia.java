package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenTreeProviderAcacia extends WorldGenTreeProvider {

    public WorldGenTreeProviderAcacia() {}

    @Nullable
    @Override
    protected WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> b(Random random) {
        return WorldGenerator.ACACIA_TREE.b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.ACACIA_TREE);
    }
}

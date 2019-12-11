package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenTreeProviderBirch extends WorldGenTreeProvider {

    public WorldGenTreeProviderBirch() {}

    @Nullable
    @Override
    protected WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> b(Random random) {
        return WorldGenerator.NORMAL_TREE.b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.BIRCH_TREE);
    }
}

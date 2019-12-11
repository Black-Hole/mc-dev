package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenTreeProviderOak extends WorldGenTreeProvider {

    public WorldGenTreeProviderOak() {}

    @Nullable
    @Override
    protected WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> b(Random random) {
        return random.nextInt(10) == 0 ? WorldGenerator.FANCY_TREE.b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.FANCY_TREE) : WorldGenerator.NORMAL_TREE.b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.NORMAL_TREE);
    }
}

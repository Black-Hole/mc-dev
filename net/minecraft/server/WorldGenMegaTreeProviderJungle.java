package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenMegaTreeProviderJungle extends WorldGenMegaTreeProvider {

    public WorldGenMegaTreeProviderJungle() {}

    @Nullable
    @Override
    protected WorldGenFeatureConfigured<WorldGenFeatureSmallTreeConfigurationConfiguration, ?> b(Random random) {
        return (new WorldGenTrees(WorldGenFeatureSmallTreeConfigurationConfiguration::a)).b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.JUNGLE_TREE_NOVINE);
    }

    @Nullable
    @Override
    protected WorldGenFeatureConfigured<WorldGenMegaTreeConfiguration, ?> a(Random random) {
        return WorldGenerator.MEGA_JUNGLE_TREE.b((WorldGenFeatureConfiguration) BiomeDecoratorGroups.MEGA_JUNGLE_TREE);
    }
}

package net.minecraft.server;

import java.util.Random;

public class WorldGenEndIsland extends WorldGenerator {

    public WorldGenEndIsland() {}

    public boolean generate(World world, Random random, BlockPosition blockposition) {
        int i = random.nextInt(3) + 4;
        float f = (float) i;

        for (int j = 0; f > 0.5F; --j) {
            for (int k = MathHelper.d(-f); k <= MathHelper.f(f); ++k) {
                for (int l = MathHelper.d(-f); l <= MathHelper.f(f); ++l) {
                    if ((float) (k * k + l * l) <= (f + 1.0F) * (f + 1.0F)) {
                        this.a(world, blockposition.a(k, j, l), Blocks.END_STONE.getBlockData());
                    }
                }
            }

            f = (float) ((double) f - ((double) random.nextInt(2) + 0.5D));
        }

        return true;
    }
}

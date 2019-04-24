package net.minecraft.server;

import javax.annotation.Nullable;

public class ChunkCache implements IIBlockAccess {

    protected final int a;
    protected final int b;
    protected final IChunkAccess[][] c;
    protected boolean d;
    protected final World e;

    public ChunkCache(World world, BlockPosition blockposition, BlockPosition blockposition1) {
        this.e = world;
        this.a = blockposition.getX() >> 4;
        this.b = blockposition.getZ() >> 4;
        int i = blockposition1.getX() >> 4;
        int j = blockposition1.getZ() >> 4;

        this.c = new IChunkAccess[i - this.a + 1][j - this.b + 1];
        this.d = true;

        int k;
        int l;

        for (k = this.a; k <= i; ++k) {
            for (l = this.b; l <= j; ++l) {
                this.c[k - this.a][l - this.b] = world.getChunkAt(k, l, ChunkStatus.FULL, false);
            }
        }

        for (k = blockposition.getX() >> 4; k <= blockposition1.getX() >> 4; ++k) {
            for (l = blockposition.getZ() >> 4; l <= blockposition1.getZ() >> 4; ++l) {
                IChunkAccess ichunkaccess = this.c[k - this.a][l - this.b];

                if (ichunkaccess != null && !ichunkaccess.a(blockposition.getY(), blockposition1.getY())) {
                    this.d = false;
                    return;
                }
            }
        }

    }

    @Nullable
    private IChunkAccess e(BlockPosition blockposition) {
        int i = (blockposition.getX() >> 4) - this.a;
        int j = (blockposition.getZ() >> 4) - this.b;

        return i >= 0 && i < this.c.length && j >= 0 && j < this.c[i].length ? this.c[i][j] : null;
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPosition blockposition) {
        IChunkAccess ichunkaccess = this.e(blockposition);

        return ichunkaccess == null ? null : ichunkaccess.getTileEntity(blockposition);
    }

    @Override
    public IBlockData getType(BlockPosition blockposition) {
        if (World.isInsideWorld(blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            IChunkAccess ichunkaccess = this.e(blockposition);

            return ichunkaccess != null ? ichunkaccess.getType(blockposition) : Blocks.BEDROCK.getBlockData();
        }
    }

    @Override
    public Fluid getFluid(BlockPosition blockposition) {
        if (World.isInsideWorld(blockposition)) {
            return FluidTypes.EMPTY.i();
        } else {
            IChunkAccess ichunkaccess = this.e(blockposition);

            return ichunkaccess != null ? ichunkaccess.getFluid(blockposition) : FluidTypes.EMPTY.i();
        }
    }

    @Override
    public BiomeBase getBiome(BlockPosition blockposition) {
        IChunkAccess ichunkaccess = this.e(blockposition);

        return ichunkaccess == null ? Biomes.PLAINS : ichunkaccess.getBiome(blockposition);
    }

    @Override
    public int getBrightness(EnumSkyBlock enumskyblock, BlockPosition blockposition) {
        return this.e.getBrightness(enumskyblock, blockposition);
    }
}

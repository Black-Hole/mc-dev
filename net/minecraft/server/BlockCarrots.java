package net.minecraft.server;

public class BlockCarrots extends BlockCrops {

    private static final VoxelShape[] a = new VoxelShape[] { Block.a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.a(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)};

    public BlockCarrots(Block.Info block_info) {
        super(block_info);
    }

    protected IMaterial e() {
        return Items.CARROT;
    }

    protected IMaterial f() {
        return Items.CARROT;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockCarrots.a[((Integer) iblockdata.get(this.b())).intValue()];
    }
}

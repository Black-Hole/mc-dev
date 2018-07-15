package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockStemAttached extends BlockPlant {

    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private final BlockStemmed b;
    private static final Map<EnumDirection, VoxelShape> c = Maps.newEnumMap(ImmutableMap.of(EnumDirection.SOUTH, Block.a(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 16.0D), EnumDirection.WEST, Block.a(0.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D), EnumDirection.NORTH, Block.a(6.0D, 0.0D, 0.0D, 10.0D, 10.0D, 10.0D), EnumDirection.EAST, Block.a(6.0D, 0.0D, 6.0D, 16.0D, 10.0D, 10.0D)));

    protected BlockStemAttached(BlockStemmed blockstemmed, Block.Info block_info) {
        super(block_info);
        this.v((IBlockData) ((IBlockData) this.blockStateList.getBlockData()).set(BlockStemAttached.a, EnumDirection.NORTH));
        this.b = blockstemmed;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return (VoxelShape) BlockStemAttached.c.get(iblockdata.get(BlockStemAttached.a));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return iblockdata1.getBlock() != this.b && enumdirection == iblockdata.get(BlockStemAttached.a) ? (IBlockData) this.b.b().getBlockData().set(BlockStem.AGE, Integer.valueOf(7)) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.getBlock() == Blocks.FARMLAND;
    }

    protected Item b() {
        return this.b == Blocks.PUMPKIN ? Items.PUMPKIN_SEEDS : (this.b == Blocks.MELON ? Items.MELON_SEEDS : Items.AIR);
    }

    public IMaterial getDropType(IBlockData iblockdata, World world, BlockPosition blockposition, int i) {
        return Items.AIR;
    }

    public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(this.b());
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData) iblockdata.set(BlockStemAttached.a, enumblockrotation.a((EnumDirection) iblockdata.get(BlockStemAttached.a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection) iblockdata.get(BlockStemAttached.a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
        blockstatelist_a.a(new IBlockState[] { BlockStemAttached.a});
    }
}

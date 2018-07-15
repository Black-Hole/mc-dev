package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class BlockCoralFan extends Block {

    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, Block.a(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), EnumDirection.SOUTH, Block.a(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), EnumDirection.WEST, Block.a(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), EnumDirection.EAST, Block.a(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));

    protected BlockCoralFan(Block.Info block_info) {
        super(block_info);
        this.v((IBlockData) ((IBlockData) this.blockStateList.getBlockData()).set(BlockCoralFan.a, EnumDirection.NORTH));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return (VoxelShape) BlockCoralFan.b.get(iblockdata.get(BlockCoralFan.a));
    }

    public boolean a(IBlockData iblockdata) {
        return false;
    }

    public Fluid t(IBlockData iblockdata) {
        return FluidTypes.c.a(false);
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumBlockFaceShape a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition, EnumDirection enumdirection) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData) iblockdata.set(BlockCoralFan.a, enumblockrotation.a((EnumDirection) iblockdata.get(BlockCoralFan.a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection) iblockdata.get(BlockCoralFan.a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
        blockstatelist_a.a(new IBlockState[] { BlockCoralFan.a});
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection.opposite() == iblockdata.get(BlockCoralFan.a) && !iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            generatoraccess.H().a(blockposition, FluidTypes.c, FluidTypes.c.a((IWorldReader) generatoraccess));
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockCoralFan.a);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        IBlockData iblockdata1 = iworldreader.getType(blockposition1);

        return iblockdata1.c(iworldreader, blockposition1, enumdirection) == EnumBlockFaceShape.SOLID && !b(iblockdata1.getBlock());
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = this.getBlockData();
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        Fluid fluid = world.b(blockposition);

        if (fluid.c() != FluidTypes.c) {
            return null;
        } else {
            EnumDirection[] aenumdirection = blockactioncontext.e();
            EnumDirection[] aenumdirection1 = aenumdirection;
            int i = aenumdirection.length;

            for (int j = 0; j < i; ++j) {
                EnumDirection enumdirection = aenumdirection1[j];

                if (enumdirection.k().c()) {
                    EnumDirection enumdirection1 = enumdirection.opposite();

                    iblockdata = (IBlockData) iblockdata.set(BlockCoralFan.a, enumdirection1);
                    if (iblockdata.canPlace(world, blockposition)) {
                        return iblockdata;
                    }
                }
            }

            return null;
        }
    }
}

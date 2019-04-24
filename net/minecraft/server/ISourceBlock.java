package net.minecraft.server;

public interface ISourceBlock extends ILocationSource {

    @Override
    double getX();

    @Override
    double getY();

    @Override
    double getZ();

    BlockPosition getBlockPosition();

    IBlockData e();

    <T extends TileEntity> T getTileEntity();
}

package net.minecraft.server;

public class TileEntityLightDetector extends TileEntity implements ITickable {

    public TileEntityLightDetector() {
        super(TileEntityTypes.q);
    }

    public void Y_() {
        if (this.world != null && !this.world.isClientSide && this.world.getTime() % 20L == 0L) {
            IBlockData iblockdata = this.getBlock();
            Block block = iblockdata.getBlock();

            if (block instanceof BlockDaylightDetector) {
                BlockDaylightDetector.b(iblockdata, this.world, this.position);
            }
        }

    }
}

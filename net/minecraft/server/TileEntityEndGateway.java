package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityEndGateway extends TileEntity implements ITickable {

    private static final Logger a = LogManager.getLogger();
    private long f = 0L;
    private int g = 0;
    private BlockPosition h;
    private boolean i;

    public TileEntityEndGateway() {}

    public void save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setLong("Age", this.f);
        if (this.h != null) {
            nbttagcompound.set("ExitPortal", GameProfileSerializer.a(this.h));
        }

        if (this.i) {
            nbttagcompound.setBoolean("ExactTeleport", this.i);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.f = nbttagcompound.getLong("Age");
        if (nbttagcompound.hasKeyOfType("ExitPortal", 10)) {
            this.h = GameProfileSerializer.c(nbttagcompound.getCompound("ExitPortal"));
        }

        this.i = nbttagcompound.getBoolean("ExactTeleport");
    }

    public void c() {
        boolean flag = this.b();
        boolean flag1 = this.d();

        ++this.f;
        if (flag1) {
            --this.g;
        } else if (!this.world.isClientSide) {
            List list = this.world.a(Entity.class, new AxisAlignedBB(this.getPosition()));

            if (!list.isEmpty()) {
                this.a((Entity) list.get(0));
            }
        }

        if (flag != this.b() || flag1 != this.d()) {
            this.update();
        }

    }

    public boolean b() {
        return this.f < 200L;
    }

    public boolean d() {
        return this.g > 0;
    }

    public Packet<?> getUpdatePacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.save(nbttagcompound);
        return new PacketPlayOutTileEntityData(this.position, 8, nbttagcompound);
    }

    public void h() {
        if (!this.world.isClientSide) {
            this.g = 20;
            this.world.playBlockAction(this.getPosition(), this.getBlock(), 1, 0);
            this.update();
        }

    }

    public boolean c(int i, int j) {
        if (i == 1) {
            this.g = 20;
            return true;
        } else {
            return super.c(i, j);
        }
    }

    public void a(Entity entity) {
        if (!this.world.isClientSide && !this.d()) {
            this.g = 100;
            if (this.h == null && this.world.worldProvider instanceof WorldProviderTheEnd) {
                this.k();
            }

            if (this.h != null) {
                BlockPosition blockposition = this.i ? this.h : this.j();

                entity.enderTeleportTo((double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D);
            }

            this.h();
        }
    }

    private BlockPosition j() {
        BlockPosition blockposition = a(this.world, this.h, 5, false);

        TileEntityEndGateway.a.debug("Best exit position for portal at " + this.h + " is " + blockposition);
        return blockposition.up();
    }

    private void k() {
        Vec3D vec3d = (new Vec3D((double) this.getPosition().getX(), 0.0D, (double) this.getPosition().getZ())).a();
        Vec3D vec3d1 = vec3d.a(1024.0D);

        int i;

        for (i = 16; a(this.world, vec3d1).g() > 0 && i-- > 0; vec3d1 = vec3d1.e(vec3d.a(-16.0D))) {
            TileEntityEndGateway.a.debug("Skipping backwards past nonempty chunk at " + vec3d1);
        }

        for (i = 16; a(this.world, vec3d1).g() == 0 && i-- > 0; vec3d1 = vec3d1.e(vec3d.a(16.0D))) {
            TileEntityEndGateway.a.debug("Skipping forward past empty chunk at " + vec3d1);
        }

        TileEntityEndGateway.a.debug("Found chunk at " + vec3d1);
        Chunk chunk = a(this.world, vec3d1);

        this.h = a(chunk);
        if (this.h == null) {
            this.h = new BlockPosition(vec3d1.x + 0.5D, 75.0D, vec3d1.z + 0.5D);
            TileEntityEndGateway.a.debug("Failed to find suitable block, settling on " + this.h);
            (new WorldGenEndIsland()).generate(this.world, new Random(this.h.asLong()), this.h);
        } else {
            TileEntityEndGateway.a.debug("Found block at " + this.h);
        }

        this.h = a(this.world, this.h, 16, true);
        TileEntityEndGateway.a.debug("Creating portal at " + this.h);
        this.h = this.h.up(10);
        this.b(this.h);
        this.update();
    }

    private static BlockPosition a(World world, BlockPosition blockposition, int i, boolean flag) {
        BlockPosition blockposition1 = null;

        for (int j = -i; j <= i; ++j) {
            for (int k = -i; k <= i; ++k) {
                if (j != 0 || k != 0 || flag) {
                    for (int l = 255; l > (blockposition1 == null ? 0 : blockposition1.getY()); --l) {
                        BlockPosition blockposition2 = new BlockPosition(blockposition.getX() + j, l, blockposition.getZ() + k);
                        IBlockData iblockdata = world.getType(blockposition2);

                        if (iblockdata.k() && (flag || iblockdata.getBlock() != Blocks.BEDROCK)) {
                            blockposition1 = blockposition2;
                            break;
                        }
                    }
                }
            }
        }

        return blockposition1 == null ? blockposition : blockposition1;
    }

    private static Chunk a(World world, Vec3D vec3d) {
        return world.getChunkAt(MathHelper.floor(vec3d.x / 16.0D), MathHelper.floor(vec3d.z / 16.0D));
    }

    private static BlockPosition a(Chunk chunk) {
        BlockPosition blockposition = new BlockPosition(chunk.locX * 16, 30, chunk.locZ * 16);
        int i = chunk.g() + 16 - 1;
        BlockPosition blockposition1 = new BlockPosition(chunk.locX * 16 + 16 - 1, i, chunk.locZ * 16 + 16 - 1);
        BlockPosition blockposition2 = null;
        double d0 = 0.0D;
        Iterator iterator = BlockPosition.a(blockposition, blockposition1).iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition3 = (BlockPosition) iterator.next();
            IBlockData iblockdata = chunk.getBlockData(blockposition3);

            if (iblockdata.getBlock() == Blocks.END_STONE && !chunk.getBlockData(blockposition3.up(1)).k() && !chunk.getBlockData(blockposition3.up(2)).k()) {
                double d1 = blockposition3.f(0.0D, 0.0D, 0.0D);

                if (blockposition2 == null || d1 < d0) {
                    blockposition2 = blockposition3;
                    d0 = d1;
                }
            }
        }

        return blockposition2;
    }

    private void b(BlockPosition blockposition) {
        (new WorldGenEndGateway()).generate(this.world, new Random(), blockposition);
        TileEntity tileentity = this.world.getTileEntity(blockposition);

        if (tileentity instanceof TileEntityEndGateway) {
            TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway) tileentity;

            tileentityendgateway.h = new BlockPosition(this.getPosition());
            tileentityendgateway.update();
        } else {
            TileEntityEndGateway.a.warn("Couldn\'t save exit portal at " + blockposition);
        }

    }
}

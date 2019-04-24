package net.minecraft.server;

import java.util.Arrays;
import javax.annotation.Nullable;

public abstract class LightEngineLayer<M extends LightEngineStorageArray<M>, S extends LightEngineStorage<M>> extends LightEngineGraph implements LightEngineLayerEventListener {

    private static final EnumDirection[] d = EnumDirection.values();
    protected final ILightAccess a;
    protected final EnumSkyBlock b;
    protected final S c;
    private boolean e;
    private final BlockPosition.MutableBlockPosition f = new BlockPosition.MutableBlockPosition();
    private final BlockPosition.MutableBlockPosition g = new BlockPosition.MutableBlockPosition();
    private final BlockPosition.MutableBlockPosition h = new BlockPosition.MutableBlockPosition();
    private final long[] i = new long[2];
    private final IBlockAccess[] j = new IBlockAccess[2];

    public LightEngineLayer(ILightAccess ilightaccess, EnumSkyBlock enumskyblock, S s0) {
        super(16, 256, 8192);
        this.a = ilightaccess;
        this.b = enumskyblock;
        this.c = s0;
        this.c();
    }

    @Override
    protected void f(long i) {
        this.c.c();
        if (this.c.g(SectionPosition.e(i))) {
            super.f(i);
        }

    }

    @Nullable
    private IBlockAccess a(int i, int j) {
        long k = ChunkCoordIntPair.pair(i, j);

        for (int l = 0; l < 2; ++l) {
            if (k == this.i[l]) {
                return this.j[l];
            }
        }

        IBlockAccess iblockaccess = this.a.b(i, j);

        for (int i1 = 1; i1 > 0; --i1) {
            this.i[i1] = this.i[i1 - 1];
            this.j[i1] = this.j[i1 - 1];
        }

        this.i[0] = k;
        this.j[0] = iblockaccess;
        return iblockaccess;
    }

    private void c() {
        Arrays.fill(this.i, ChunkCoordIntPair.a);
        Arrays.fill(this.j, (Object) null);
    }

    protected int a(long i, long j) {
        this.c.c();
        this.f.g(i);
        this.g.g(j);
        int k = SectionPosition.a(this.f.getX());
        int l = SectionPosition.a(this.f.getZ());
        int i1 = SectionPosition.a(this.g.getX());
        int j1 = SectionPosition.a(this.g.getZ());
        IBlockAccess iblockaccess = this.a(i1, j1);

        if (iblockaccess == null) {
            return 16;
        } else {
            IBlockData iblockdata = iblockaccess.getType(this.g);
            IBlockAccess iblockaccess1 = this.a.getWorld();
            int k1 = iblockdata.b(iblockaccess1, (BlockPosition) this.g);

            if (!iblockdata.g() && k1 >= 15) {
                return 16;
            } else {
                IBlockAccess iblockaccess2;

                if (k == i1 && l == j1) {
                    iblockaccess2 = iblockaccess;
                } else {
                    iblockaccess2 = this.a(k, l);
                }

                if (iblockaccess2 == null) {
                    return 16;
                } else {
                    int l1 = Integer.signum(this.g.getX() - this.f.getX());
                    int i2 = Integer.signum(this.g.getY() - this.f.getY());
                    int j2 = Integer.signum(this.g.getZ() - this.f.getZ());
                    EnumDirection enumdirection = EnumDirection.a((BlockPosition) this.h.d(l1, i2, j2));

                    if (enumdirection == null) {
                        return 16;
                    } else {
                        IBlockData iblockdata1 = iblockaccess2.getType(this.f);

                        return a(iblockaccess1, iblockdata1, this.f, iblockdata, this.g, enumdirection, k1);
                    }
                }
            }
        }
    }

    public static int a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition, IBlockData iblockdata1, BlockPosition blockposition1, EnumDirection enumdirection, int i) {
        boolean flag = iblockdata.o() && iblockdata.g();
        boolean flag1 = iblockdata1.o() && iblockdata1.g();

        if (!flag && !flag1) {
            return i;
        } else {
            VoxelShape voxelshape = flag ? iblockdata.j(iblockaccess, blockposition) : VoxelShapes.a();
            VoxelShape voxelshape1 = flag1 ? iblockdata1.j(iblockaccess, blockposition1) : VoxelShapes.a();

            return VoxelShapes.b(voxelshape, voxelshape1, enumdirection) ? 16 : i;
        }
    }

    @Override
    protected boolean a(long i) {
        return i == Long.MAX_VALUE;
    }

    @Override
    protected int a(long i, long j, int k) {
        return 0;
    }

    @Override
    protected int c(long i) {
        return i == Long.MAX_VALUE ? 0 : 15 - this.c.h(i);
    }

    protected int a(NibbleArray nibblearray, long i) {
        return 15 - nibblearray.a(SectionPosition.b(BlockPosition.b(i)), SectionPosition.b(BlockPosition.c(i)), SectionPosition.b(BlockPosition.d(i)));
    }

    @Override
    protected void a(long i, int j) {
        this.c.b(i, Math.min(15, 15 - j));
    }

    @Override
    protected int b(long i, long j, int k) {
        return 0;
    }

    public boolean a() {
        return this.b() || this.c.b() || this.c.a();
    }

    public int a(int i, boolean flag, boolean flag1) {
        if (!this.e) {
            if (this.c.b()) {
                i = this.c.b(i);
                if (i == 0) {
                    return i;
                }
            }

            this.c.a(this, flag, flag1);
        }

        this.e = true;
        if (this.b()) {
            i = this.b(i);
            this.c();
            if (i == 0) {
                return i;
            }
        }

        this.e = false;
        this.c.d();
        return i;
    }

    protected void a(long i, NibbleArray nibblearray) {
        this.c.a(i, nibblearray);
    }

    @Nullable
    @Override
    public NibbleArray a(SectionPosition sectionposition) {
        return this.c.a(sectionposition.v(), false);
    }

    @Override
    public int b(BlockPosition blockposition) {
        return this.c.d(blockposition.asLong());
    }

    public void a(BlockPosition blockposition) {
        long i = blockposition.asLong();

        this.f(i);
        EnumDirection[] aenumdirection = LightEngineLayer.d;
        int j = aenumdirection.length;

        for (int k = 0; k < j; ++k) {
            EnumDirection enumdirection = aenumdirection[k];

            this.f(BlockPosition.a(i, enumdirection));
        }

    }

    public void a(BlockPosition blockposition, int i) {}

    @Override
    public void a(SectionPosition sectionposition, boolean flag) {
        this.c.c(sectionposition.v(), flag);
    }

    public void a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
        long i = SectionPosition.f(SectionPosition.b(chunkcoordintpair.x, 0, chunkcoordintpair.z));

        this.c.b(i, flag);
    }
}

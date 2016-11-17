package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class TileEntityPiston extends TileEntity implements ITickable {

    private IBlockData a;
    private EnumDirection f;
    private boolean g;
    private boolean h;
    private static final ThreadLocal<Boolean> i = new ThreadLocal() {
        protected Boolean a() {
            return Boolean.FALSE;
        }

        protected Object initialValue() {
            return this.a();
        }
    };
    private float j;
    private float k;

    public TileEntityPiston() {}

    public TileEntityPiston(IBlockData iblockdata, EnumDirection enumdirection, boolean flag, boolean flag1) {
        this.a = iblockdata;
        this.f = enumdirection;
        this.g = flag;
        this.h = flag1;
    }

    public IBlockData a() {
        return this.a;
    }

    public NBTTagCompound d() {
        return this.save(new NBTTagCompound());
    }

    public int v() {
        return 0;
    }

    public boolean e() {
        return this.g;
    }

    public EnumDirection f() {
        return this.f;
    }

    public boolean h() {
        return this.h;
    }

    private float e(float f) {
        return this.g ? f - 1.0F : 1.0F - f;
    }

    public AxisAlignedBB a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return this.a(iblockaccess, blockposition, this.j).b(this.a(iblockaccess, blockposition, this.k));
    }

    public AxisAlignedBB a(IBlockAccess iblockaccess, BlockPosition blockposition, float f) {
        f = this.e(f);
        IBlockData iblockdata = this.j();

        return iblockdata.d(iblockaccess, blockposition).d((double) (f * (float) this.f.getAdjacentX()), (double) (f * (float) this.f.getAdjacentY()), (double) (f * (float) this.f.getAdjacentZ()));
    }

    private IBlockData j() {
        return !this.e() && this.h() ? Blocks.PISTON_HEAD.getBlockData().set(BlockPistonExtension.TYPE, this.a.getBlock() == Blocks.STICKY_PISTON ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT).set(BlockPistonExtension.FACING, this.a.get(BlockPiston.FACING)) : this.a;
    }

    private void f(float f) {
        EnumDirection enumdirection = this.g ? this.f : this.f.opposite();
        double d0 = (double) (f - this.j);
        ArrayList arraylist = Lists.newArrayList();

        this.j().a(this.world, BlockPosition.ZERO, new AxisAlignedBB(BlockPosition.ZERO), arraylist, (Entity) null);
        if (!arraylist.isEmpty()) {
            AxisAlignedBB axisalignedbb = this.a(new AxisAlignedBB(BlockPosition.ZERO));
            List list = this.world.getEntities((Entity) null, this.a(axisalignedbb, enumdirection, d0).b(axisalignedbb));

            if (!list.isEmpty()) {
                boolean flag = this.a.getBlock() == Blocks.SLIME;

                for (int i = 0; i < list.size(); ++i) {
                    Entity entity = (Entity) list.get(i);

                    if (entity.o_() != EnumPistonReaction.IGNORE) {
                        if (flag) {
                            switch (enumdirection.k()) {
                            case X:
                                entity.motX = (double) enumdirection.getAdjacentX();
                                break;

                            case Y:
                                entity.motY = (double) enumdirection.getAdjacentY();
                                break;

                            case Z:
                                entity.motZ = (double) enumdirection.getAdjacentZ();
                            }
                        }

                        double d1 = 0.0D;

                        for (int j = 0; j < arraylist.size(); ++j) {
                            AxisAlignedBB axisalignedbb1 = this.a(this.a((AxisAlignedBB) arraylist.get(j)), enumdirection, d0);
                            AxisAlignedBB axisalignedbb2 = entity.getBoundingBox();

                            if (axisalignedbb1.c(axisalignedbb2)) {
                                d1 = Math.max(d1, this.a(axisalignedbb1, enumdirection, axisalignedbb2));
                                if (d1 >= d0) {
                                    break;
                                }
                            }
                        }

                        if (d1 > 0.0D) {
                            d1 = Math.min(d1, d0) + 0.01D;
                            TileEntityPiston.i.set(Boolean.valueOf(true));
                            entity.move(EnumMoveType.PISTON, d1 * (double) enumdirection.getAdjacentX(), d1 * (double) enumdirection.getAdjacentY(), d1 * (double) enumdirection.getAdjacentZ());
                            TileEntityPiston.i.set(Boolean.valueOf(false));
                            if (!this.g && this.h) {
                                this.a(entity, enumdirection);
                            }
                        }
                    }
                }

            }
        }
    }

    private double a(AxisAlignedBB axisalignedbb, EnumDirection enumdirection, AxisAlignedBB axisalignedbb1) {
        switch (enumdirection.k()) {
        case X:
            return b(axisalignedbb, enumdirection, axisalignedbb1);

        case Y:
        default:
            return c(axisalignedbb, enumdirection, axisalignedbb1);

        case Z:
            return d(axisalignedbb, enumdirection, axisalignedbb1);
        }
    }

    private AxisAlignedBB a(AxisAlignedBB axisalignedbb) {
        double d0 = (double) this.e(this.j);

        return axisalignedbb.d((double) this.position.getX() + d0 * (double) this.f.getAdjacentX(), (double) this.position.getY() + d0 * (double) this.f.getAdjacentY(), (double) this.position.getZ() + d0 * (double) this.f.getAdjacentZ());
    }

    private AxisAlignedBB a(AxisAlignedBB axisalignedbb, EnumDirection enumdirection, double d0) {
        double d1 = d0 * (double) enumdirection.c().a();
        double d2 = Math.min(d1, 0.0D);
        double d3 = Math.max(d1, 0.0D);

        switch (enumdirection) {
        case WEST:
            return new AxisAlignedBB(axisalignedbb.a + d2, axisalignedbb.b, axisalignedbb.c, axisalignedbb.a + d3, axisalignedbb.e, axisalignedbb.f);

        case EAST:
            return new AxisAlignedBB(axisalignedbb.d + d2, axisalignedbb.b, axisalignedbb.c, axisalignedbb.d + d3, axisalignedbb.e, axisalignedbb.f);

        case DOWN:
            return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b + d2, axisalignedbb.c, axisalignedbb.d, axisalignedbb.b + d3, axisalignedbb.f);

        case UP:
        default:
            return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.e + d2, axisalignedbb.c, axisalignedbb.d, axisalignedbb.e + d3, axisalignedbb.f);

        case NORTH:
            return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c + d2, axisalignedbb.d, axisalignedbb.e, axisalignedbb.c + d3);

        case SOUTH:
            return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f + d2, axisalignedbb.d, axisalignedbb.e, axisalignedbb.f + d3);
        }
    }

    private void a(Entity entity, EnumDirection enumdirection) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox();
        AxisAlignedBB axisalignedbb1 = Block.j.a(this.position);

        if (axisalignedbb.c(axisalignedbb1)) {
            EnumDirection enumdirection1 = enumdirection.opposite();
            double d0 = this.a(axisalignedbb1, enumdirection1, axisalignedbb) + 0.01D;
            double d1 = this.a(axisalignedbb1, enumdirection1, axisalignedbb.a(axisalignedbb1)) + 0.01D;

            if (Math.abs(d0 - d1) < 0.01D) {
                TileEntityPiston.i.set(Boolean.valueOf(true));
                entity.move(EnumMoveType.PISTON, d0 * (double) enumdirection1.getAdjacentX(), d0 * (double) enumdirection1.getAdjacentY(), d0 * (double) enumdirection1.getAdjacentZ());
                TileEntityPiston.i.set(Boolean.valueOf(false));
            }
        }

    }

    private static double b(AxisAlignedBB axisalignedbb, EnumDirection enumdirection, AxisAlignedBB axisalignedbb1) {
        return enumdirection.c() == EnumDirection.EnumAxisDirection.POSITIVE ? axisalignedbb.d - axisalignedbb1.a : axisalignedbb1.d - axisalignedbb.a;
    }

    private static double c(AxisAlignedBB axisalignedbb, EnumDirection enumdirection, AxisAlignedBB axisalignedbb1) {
        return enumdirection.c() == EnumDirection.EnumAxisDirection.POSITIVE ? axisalignedbb.e - axisalignedbb1.b : axisalignedbb1.e - axisalignedbb.b;
    }

    private static double d(AxisAlignedBB axisalignedbb, EnumDirection enumdirection, AxisAlignedBB axisalignedbb1) {
        return enumdirection.c() == EnumDirection.EnumAxisDirection.POSITIVE ? axisalignedbb.f - axisalignedbb1.c : axisalignedbb1.f - axisalignedbb.c;
    }

    public void i() {
        if (this.k < 1.0F && this.world != null) {
            this.j = 1.0F;
            this.k = this.j;
            this.world.s(this.position);
            this.z();
            if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION) {
                this.world.setTypeAndData(this.position, this.a, 3);
                this.world.a(this.position, this.a.getBlock(), this.position);
            }
        }

    }

    public void F_() {
        this.k = this.j;
        if (this.k >= 1.0F) {
            this.world.s(this.position);
            this.z();
            if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION) {
                this.world.setTypeAndData(this.position, this.a, 3);
                this.world.a(this.position, this.a.getBlock(), this.position);
            }

        } else {
            float f = this.j + 0.5F;

            this.f(f);
            this.j = f;
            if (this.j >= 1.0F) {
                this.j = 1.0F;
            }

        }
    }

    public static void a(DataConverterManager dataconvertermanager) {}

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = Block.getById(nbttagcompound.getInt("blockId")).fromLegacyData(nbttagcompound.getInt("blockData"));
        this.f = EnumDirection.fromType1(nbttagcompound.getInt("facing"));
        this.j = nbttagcompound.getFloat("progress");
        this.k = this.j;
        this.g = nbttagcompound.getBoolean("extending");
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setInt("blockId", Block.getId(this.a.getBlock()));
        nbttagcompound.setInt("blockData", this.a.getBlock().toLegacyData(this.a));
        nbttagcompound.setInt("facing", this.f.a());
        nbttagcompound.setFloat("progress", this.k);
        nbttagcompound.setBoolean("extending", this.g);
        return nbttagcompound;
    }

    public void a(World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, @Nullable Entity entity) {
        if (!this.e() && this.h()) {
            this.a.set(BlockPiston.EXTENDED, Boolean.valueOf(true)).a(world, blockposition, axisalignedbb, list, entity);
        }

        if ((double) this.j >= 1.0D || !((Boolean) TileEntityPiston.i.get()).booleanValue()) {
            int i = list.size();
            IBlockData iblockdata;

            if (this.h()) {
                iblockdata = Blocks.PISTON_HEAD.getBlockData().set(BlockPistonExtension.FACING, this.f).set(BlockPistonExtension.SHORT, Boolean.valueOf(this.g != 1.0F - this.j < 0.25F));
            } else {
                iblockdata = this.a;
            }

            float f = this.e(this.j);
            double d0 = (double) ((float) this.f.getAdjacentX() * f);
            double d1 = (double) ((float) this.f.getAdjacentY() * f);
            double d2 = (double) ((float) this.f.getAdjacentZ() * f);

            iblockdata.a(world, blockposition, axisalignedbb.d(-d0, -d1, -d2), list, entity);

            for (int j = i; j < list.size(); ++j) {
                list.set(j, ((AxisAlignedBB) list.get(j)).d(d0, d1, d2));
            }

        }
    }
}

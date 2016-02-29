package net.minecraft.server;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;

public class PathfinderNormal extends PathfinderAbstract {

    private float j;

    public PathfinderNormal() {}

    public void a(IBlockAccess iblockaccess, EntityInsentient entityinsentient) {
        super.a(iblockaccess, entityinsentient);
        this.j = entityinsentient.a(PathType.WATER);
    }

    public void a() {
        super.a();
        this.b.a(PathType.WATER, this.j);
    }

    public PathPoint b() {
        int i;
        BlockPosition blockposition;

        if (this.e() && this.b.isInWater()) {
            i = (int) this.b.getBoundingBox().b;
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ));

            for (Block block = this.a.getType(blockposition_mutableblockposition).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.a.getType(blockposition_mutableblockposition).getBlock()) {
                ++i;
                blockposition_mutableblockposition.c(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ));
            }
        } else if (!this.b.onGround) {
            for (blockposition = new BlockPosition(this.b); (this.a.getType(blockposition).getMaterial() == Material.AIR || this.a.getType(blockposition).getBlock().b(this.a, blockposition)) && blockposition.getY() > 0; blockposition = blockposition.down()) {
                ;
            }

            i = blockposition.up().getY();
        } else {
            i = MathHelper.floor(this.b.getBoundingBox().b + 0.5D);
        }

        blockposition = new BlockPosition(this.b);
        PathType pathtype = this.a(this.b, blockposition.getX(), i, blockposition.getZ());

        if (this.b.a(pathtype) < 0.0F) {
            HashSet hashset = new HashSet();

            hashset.add(new BlockPosition(this.b.getBoundingBox().a, (double) i, this.b.getBoundingBox().c));
            hashset.add(new BlockPosition(this.b.getBoundingBox().a, (double) i, this.b.getBoundingBox().f));
            hashset.add(new BlockPosition(this.b.getBoundingBox().d, (double) i, this.b.getBoundingBox().c));
            hashset.add(new BlockPosition(this.b.getBoundingBox().d, (double) i, this.b.getBoundingBox().f));
            Iterator iterator = hashset.iterator();

            while (iterator.hasNext()) {
                BlockPosition blockposition1 = (BlockPosition) iterator.next();
                PathType pathtype1 = this.a(this.b, blockposition1);

                if (this.b.a(pathtype1) >= 0.0F) {
                    return this.a(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
                }
            }
        }

        return this.a(blockposition.getX(), i, blockposition.getZ());
    }

    public PathPoint a(double d0, double d1, double d2) {
        return this.a(MathHelper.floor(d0 - (double) (this.b.width / 2.0F)), MathHelper.floor(d1), MathHelper.floor(d2 - (double) (this.b.width / 2.0F)));
    }

    public int a(PathPoint[] apathpoint, PathPoint pathpoint, PathPoint pathpoint1, float f) {
        int i = 0;
        byte b0 = 0;
        PathType pathtype = this.a(this.b, pathpoint.a, pathpoint.b + 1, pathpoint.c);

        if (this.b.a(pathtype) >= 0.0F) {
            b0 = 1;
        }

        BlockPosition blockposition = (new BlockPosition(pathpoint.a, pathpoint.b, pathpoint.c)).down();
        double d0 = (double) pathpoint.b - (1.0D - this.a.getType(blockposition).c(this.a, blockposition).e);
        PathPoint pathpoint2 = this.a(pathpoint.a, pathpoint.b, pathpoint.c + 1, b0, d0, EnumDirection.SOUTH);
        PathPoint pathpoint3 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c, b0, d0, EnumDirection.WEST);
        PathPoint pathpoint4 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c, b0, d0, EnumDirection.EAST);
        PathPoint pathpoint5 = this.a(pathpoint.a, pathpoint.b, pathpoint.c - 1, b0, d0, EnumDirection.NORTH);

        if (pathpoint2 != null && !pathpoint2.i && pathpoint2.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint2;
        }

        if (pathpoint3 != null && !pathpoint3.i && pathpoint3.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint3;
        }

        if (pathpoint4 != null && !pathpoint4.i && pathpoint4.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint4;
        }

        if (pathpoint5 != null && !pathpoint5.i && pathpoint5.a(pathpoint1) < f) {
            apathpoint[i++] = pathpoint5;
        }

        boolean flag = pathpoint5 == null || pathpoint5.m == PathType.OPEN || pathpoint5.l != 0.0F;
        boolean flag1 = pathpoint2 == null || pathpoint2.m == PathType.OPEN || pathpoint2.l != 0.0F;
        boolean flag2 = pathpoint4 == null || pathpoint4.m == PathType.OPEN || pathpoint4.l != 0.0F;
        boolean flag3 = pathpoint3 == null || pathpoint3.m == PathType.OPEN || pathpoint3.l != 0.0F;
        PathPoint pathpoint6;

        if (flag && flag3) {
            pathpoint6 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c - 1, b0, d0, EnumDirection.NORTH);
            if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint6;
            }
        }

        if (flag && flag2) {
            pathpoint6 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c - 1, b0, d0, EnumDirection.NORTH);
            if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint6;
            }
        }

        if (flag1 && flag3) {
            pathpoint6 = this.a(pathpoint.a - 1, pathpoint.b, pathpoint.c + 1, b0, d0, EnumDirection.SOUTH);
            if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint6;
            }
        }

        if (flag1 && flag2) {
            pathpoint6 = this.a(pathpoint.a + 1, pathpoint.b, pathpoint.c + 1, b0, d0, EnumDirection.SOUTH);
            if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint1) < f) {
                apathpoint[i++] = pathpoint6;
            }
        }

        return i;
    }

    private PathPoint a(int i, int j, int k, int l, double d0, EnumDirection enumdirection) {
        PathPoint pathpoint = null;
        BlockPosition blockposition = new BlockPosition(i, j, k);
        BlockPosition blockposition1 = blockposition.down();
        double d1 = (double) j - (1.0D - this.a.getType(blockposition1).c(this.a, blockposition1).e);

        if (d1 - d0 > 1.0D) {
            return null;
        } else {
            PathType pathtype = this.a(this.b, i, j, k);
            float f = this.b.a(pathtype);
            double d2 = (double) this.b.width / 2.0D;

            if (f >= 0.0F) {
                pathpoint = this.a(i, j, k);
                pathpoint.m = pathtype;
                pathpoint.l = Math.max(pathpoint.l, f);
            }

            if (pathtype == PathType.WALKABLE) {
                return pathpoint;
            } else {
                if (pathpoint == null && l > 0 && pathtype != PathType.FENCE && pathtype != PathType.TRAPDOOR) {
                    pathpoint = this.a(i, j + 1, k, l - 1, d0, enumdirection);
                    if (pathpoint != null && (pathpoint.m == PathType.OPEN || pathpoint.m == PathType.WALKABLE)) {
                        double d3 = (double) (i - enumdirection.getAdjacentX()) + 0.5D;
                        double d4 = (double) (k - enumdirection.getAdjacentZ()) + 0.5D;
                        AxisAlignedBB axisalignedbb = new AxisAlignedBB(d3 - d2, (double) j + 0.001D, d4 - d2, d3 + d2, (double) ((float) j + this.b.length), d4 + d2);
                        AxisAlignedBB axisalignedbb1 = this.a.getType(blockposition).c(this.a, blockposition);
                        AxisAlignedBB axisalignedbb2 = axisalignedbb.a(0.0D, axisalignedbb1.e - 0.002D, 0.0D);

                        if (this.b.world.b(axisalignedbb2)) {
                            pathpoint = null;
                        }
                    }
                }

                if (pathtype == PathType.OPEN) {
                    AxisAlignedBB axisalignedbb3 = new AxisAlignedBB((double) i - d2 + 0.5D, (double) j + 0.001D, (double) k - d2 + 0.5D, (double) i + d2 + 0.5D, (double) ((float) j + this.b.length), (double) k + d2 + 0.5D);

                    if (this.b.world.b(axisalignedbb3)) {
                        return null;
                    }

                    int i1 = 0;

                    while (j > 0 && pathtype == PathType.OPEN) {
                        --j;
                        if (i1++ >= this.b.aW()) {
                            return null;
                        }

                        pathtype = this.a(this.b, i, j, k);
                        f = this.b.a(pathtype);
                        if (pathtype != PathType.OPEN && f >= 0.0F) {
                            pathpoint = this.a(i, j, k);
                            pathpoint.m = pathtype;
                            pathpoint.l = Math.max(pathpoint.l, f);
                            break;
                        }

                        if (f < 0.0F) {
                            return null;
                        }
                    }
                }

                return pathpoint;
            }
        }
    }

    public PathType a(IBlockAccess iblockaccess, int i, int j, int k, EntityInsentient entityinsentient, int l, int i1, int j1, boolean flag, boolean flag1) {
        EnumSet enumset = EnumSet.noneOf(PathType.class);
        PathType pathtype = PathType.BLOCKED;
        double d0 = (double) entityinsentient.width / 2.0D;
        BlockPosition blockposition = new BlockPosition(entityinsentient);

        for (int k1 = i; k1 < i + l; ++k1) {
            for (int l1 = j; l1 < j + i1; ++l1) {
                for (int i2 = k; i2 < k + j1; ++i2) {
                    PathType pathtype1 = a(iblockaccess, k1, l1, i2);

                    if (pathtype1 == PathType.DOOR_WOOD_CLOSED && flag && flag1) {
                        pathtype1 = PathType.WALKABLE;
                    }

                    if (pathtype1 == PathType.DOOR_OPEN && !flag1) {
                        pathtype1 = PathType.BLOCKED;
                    }

                    if (pathtype1 == PathType.RAIL && !(iblockaccess.getType(blockposition).getBlock() instanceof BlockMinecartTrackAbstract) && !(iblockaccess.getType(blockposition.down()).getBlock() instanceof BlockMinecartTrackAbstract)) {
                        pathtype1 = PathType.FENCE;
                    }

                    if (k1 == i && l1 == j && i2 == k) {
                        pathtype = pathtype1;
                    }

                    if (l1 > j && pathtype1 != PathType.OPEN) {
                        AxisAlignedBB axisalignedbb = new AxisAlignedBB((double) k1 - d0 + 0.5D, (double) j + 0.001D, (double) i2 - d0 + 0.5D, (double) k1 + d0 + 0.5D, (double) ((float) j + entityinsentient.length), (double) i2 + d0 + 0.5D);

                        if (!entityinsentient.world.b(axisalignedbb)) {
                            pathtype1 = PathType.OPEN;
                        }
                    }

                    enumset.add(pathtype1);
                }
            }
        }

        if (enumset.contains(PathType.FENCE)) {
            return PathType.FENCE;
        } else {
            PathType pathtype2 = PathType.BLOCKED;
            Iterator iterator = enumset.iterator();

            while (iterator.hasNext()) {
                PathType pathtype3 = (PathType) iterator.next();

                if (entityinsentient.a(pathtype3) < 0.0F) {
                    return pathtype3;
                }

                if (entityinsentient.a(pathtype3) >= entityinsentient.a(pathtype2)) {
                    pathtype2 = pathtype3;
                }
            }

            if (pathtype == PathType.OPEN && entityinsentient.a(pathtype2) == 0.0F) {
                return PathType.OPEN;
            } else {
                return pathtype2;
            }
        }
    }

    private PathType a(EntityInsentient entityinsentient, BlockPosition blockposition) {
        return this.a(this.a, blockposition.getX(), blockposition.getY(), blockposition.getZ(), entityinsentient, this.d, this.e, this.f, this.d(), this.c());
    }

    private PathType a(EntityInsentient entityinsentient, int i, int j, int k) {
        return this.a(this.a, i, j, k, entityinsentient, this.d, this.e, this.f, this.d(), this.c());
    }

    public static PathType a(IBlockAccess iblockaccess, int i, int j, int k) {
        BlockPosition blockposition = new BlockPosition(i, j, k);
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        Block block = iblockdata.getBlock();
        Material material = iblockdata.getMaterial();
        PathType pathtype = PathType.BLOCKED;

        if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY) {
            if (block == Blocks.FIRE) {
                return PathType.DAMAGE_FIRE;
            } else if (block == Blocks.CACTUS) {
                return PathType.DAMAGE_CACTUS;
            } else if (block instanceof BlockDoor && material == Material.WOOD && !((Boolean) iblockdata.get(BlockDoor.OPEN)).booleanValue()) {
                return PathType.DOOR_WOOD_CLOSED;
            } else if (block instanceof BlockDoor && material == Material.ORE && !((Boolean) iblockdata.get(BlockDoor.OPEN)).booleanValue()) {
                return PathType.DOOR_IRON_CLOSED;
            } else if (block instanceof BlockDoor && ((Boolean) iblockdata.get(BlockDoor.OPEN)).booleanValue()) {
                return PathType.DOOR_OPEN;
            } else if (block instanceof BlockMinecartTrackAbstract) {
                return PathType.RAIL;
            } else if (!(block instanceof BlockFence) && !(block instanceof BlockCobbleWall) && (!(block instanceof BlockFenceGate) || ((Boolean) iblockdata.get(BlockFenceGate.OPEN)).booleanValue())) {
                if (material == Material.AIR) {
                    pathtype = PathType.OPEN;
                } else {
                    if (material == Material.WATER) {
                        return PathType.WATER;
                    }

                    if (material == Material.LAVA) {
                        return PathType.LAVA;
                    }
                }

                if (block.b(iblockaccess, blockposition) && pathtype == PathType.BLOCKED) {
                    pathtype = PathType.OPEN;
                }

                if (pathtype == PathType.OPEN && j >= 1) {
                    PathType pathtype1 = a(iblockaccess, i, j - 1, k);

                    pathtype = pathtype1 != PathType.WALKABLE && pathtype1 != PathType.OPEN && pathtype1 != PathType.WATER && pathtype1 != PathType.LAVA ? PathType.WALKABLE : PathType.OPEN;
                }

                if (pathtype == PathType.WALKABLE) {
                    for (int l = i - 1; l <= i + 1; ++l) {
                        for (int i1 = k - 1; i1 <= k + 1; ++i1) {
                            if (l != i || i1 != k) {
                                Block block1 = iblockaccess.getType(new BlockPosition(l, j, i1)).getBlock();

                                if (block1 == Blocks.CACTUS) {
                                    pathtype = PathType.DANGER_CACTUS;
                                } else if (block1 == Blocks.FIRE) {
                                    pathtype = PathType.DANGER_FIRE;
                                }
                            }
                        }
                    }
                }

                return pathtype;
            } else {
                return PathType.FENCE;
            }
        } else {
            return PathType.TRAPDOOR;
        }
    }
}

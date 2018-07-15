package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenMineshaftPieces {

    public static void a() {
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftCorridor.class, "MSCorridor");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftCross.class, "MSCrossing");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftRoom.class, "MSRoom");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftStairs.class, "MSStairs");
    }

    private static WorldGenMineshaftPieces.c a(List<StructurePiece> list, Random random, int i, int j, int k, @Nullable EnumDirection enumdirection, int l, WorldGenMineshaft.Type worldgenmineshaft_type) {
        int i1 = random.nextInt(100);
        StructureBoundingBox structureboundingbox;

        if (i1 >= 80) {
            structureboundingbox = WorldGenMineshaftPieces.WorldGenMineshaftCross.a(list, random, i, j, k, enumdirection);
            if (structureboundingbox != null) {
                return new WorldGenMineshaftPieces.WorldGenMineshaftCross(l, random, structureboundingbox, enumdirection, worldgenmineshaft_type);
            }
        } else if (i1 >= 70) {
            structureboundingbox = WorldGenMineshaftPieces.WorldGenMineshaftStairs.a(list, random, i, j, k, enumdirection);
            if (structureboundingbox != null) {
                return new WorldGenMineshaftPieces.WorldGenMineshaftStairs(l, random, structureboundingbox, enumdirection, worldgenmineshaft_type);
            }
        } else {
            structureboundingbox = WorldGenMineshaftPieces.WorldGenMineshaftCorridor.a(list, random, i, j, k, enumdirection);
            if (structureboundingbox != null) {
                return new WorldGenMineshaftPieces.WorldGenMineshaftCorridor(l, random, structureboundingbox, enumdirection, worldgenmineshaft_type);
            }
        }

        return null;
    }

    private static WorldGenMineshaftPieces.c b(StructurePiece structurepiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
        if (l > 8) {
            return null;
        } else if (Math.abs(i - structurepiece.d().a) <= 80 && Math.abs(k - structurepiece.d().c) <= 80) {
            WorldGenMineshaft.Type worldgenmineshaft_type = ((WorldGenMineshaftPieces.c) structurepiece).a;
            WorldGenMineshaftPieces.c worldgenmineshaftpieces_c = a(list, random, i, j, k, enumdirection, l + 1, worldgenmineshaft_type);

            if (worldgenmineshaftpieces_c != null) {
                list.add(worldgenmineshaftpieces_c);
                worldgenmineshaftpieces_c.a(structurepiece, list, random);
            }

            return worldgenmineshaftpieces_c;
        } else {
            return null;
        }
    }

    public static class WorldGenMineshaftStairs extends WorldGenMineshaftPieces.c {

        public WorldGenMineshaftStairs() {}

        public WorldGenMineshaftStairs(int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection, WorldGenMineshaft.Type worldgenmineshaft_type) {
            super(i, worldgenmineshaft_type);
            this.a(enumdirection);
            this.m = structureboundingbox;
        }

        public static StructureBoundingBox a(List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection) {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j - 5, k, i, j + 3 - 1, k);

            switch (enumdirection) {
            case NORTH:
            default:
                structureboundingbox.d = i + 3 - 1;
                structureboundingbox.c = k - 8;
                break;

            case SOUTH:
                structureboundingbox.d = i + 3 - 1;
                structureboundingbox.f = k + 8;
                break;

            case WEST:
                structureboundingbox.a = i - 8;
                structureboundingbox.f = k + 3 - 1;
                break;

            case EAST:
                structureboundingbox.d = i + 8;
                structureboundingbox.f = k + 3 - 1;
            }

            return StructurePiece.a(list, structureboundingbox) != null ? null : structureboundingbox;
        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            int i = this.e();
            EnumDirection enumdirection = this.f();

            if (enumdirection != null) {
                switch (enumdirection) {
                case NORTH:
                default:
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b, this.m.c - 1, EnumDirection.NORTH, i);
                    break;

                case SOUTH:
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b, this.m.f + 1, EnumDirection.SOUTH, i);
                    break;

                case WEST:
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b, this.m.c, EnumDirection.WEST, i);
                    break;

                case EAST:
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b, this.m.c, EnumDirection.EAST, i);
                }
            }

        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair chunkcoordintpair) {
            if (this.a((IBlockAccess) generatoraccess, structureboundingbox)) {
                return false;
            } else {
                this.a(generatoraccess, structureboundingbox, 0, 5, 0, 2, 7, 1, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, false);
                this.a(generatoraccess, structureboundingbox, 0, 0, 7, 2, 2, 8, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, false);

                for (int i = 0; i < 5; ++i) {
                    this.a(generatoraccess, structureboundingbox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, WorldGenMineshaftPieces.WorldGenMineshaftStairs.l, false);
                }

                return true;
            }
        }
    }

    public static class WorldGenMineshaftCross extends WorldGenMineshaftPieces.c {

        private EnumDirection b;
        private boolean c;

        public WorldGenMineshaftCross() {}

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setBoolean("tf", this.c);
            nbttagcompound.setInt("D", this.b.get2DRotationValue());
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.c = nbttagcompound.getBoolean("tf");
            this.b = EnumDirection.fromType2(nbttagcompound.getInt("D"));
        }

        public WorldGenMineshaftCross(int i, Random random, StructureBoundingBox structureboundingbox, @Nullable EnumDirection enumdirection, WorldGenMineshaft.Type worldgenmineshaft_type) {
            super(i, worldgenmineshaft_type);
            this.b = enumdirection;
            this.m = structureboundingbox;
            this.c = structureboundingbox.d() > 3;
        }

        public static StructureBoundingBox a(List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection) {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 3 - 1, k);

            if (random.nextInt(4) == 0) {
                structureboundingbox.e += 4;
            }

            switch (enumdirection) {
            case NORTH:
            default:
                structureboundingbox.a = i - 1;
                structureboundingbox.d = i + 3;
                structureboundingbox.c = k - 4;
                break;

            case SOUTH:
                structureboundingbox.a = i - 1;
                structureboundingbox.d = i + 3;
                structureboundingbox.f = k + 3 + 1;
                break;

            case WEST:
                structureboundingbox.a = i - 4;
                structureboundingbox.c = k - 1;
                structureboundingbox.f = k + 3;
                break;

            case EAST:
                structureboundingbox.d = i + 3 + 1;
                structureboundingbox.c = k - 1;
                structureboundingbox.f = k + 3;
            }

            return StructurePiece.a(list, structureboundingbox) != null ? null : structureboundingbox;
        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            int i = this.e();

            switch (this.b) {
            case NORTH:
            default:
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.c - 1, EnumDirection.NORTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b, this.m.c + 1, EnumDirection.WEST, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b, this.m.c + 1, EnumDirection.EAST, i);
                break;

            case SOUTH:
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.f + 1, EnumDirection.SOUTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b, this.m.c + 1, EnumDirection.WEST, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b, this.m.c + 1, EnumDirection.EAST, i);
                break;

            case WEST:
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.c - 1, EnumDirection.NORTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.f + 1, EnumDirection.SOUTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b, this.m.c + 1, EnumDirection.WEST, i);
                break;

            case EAST:
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.c - 1, EnumDirection.NORTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b, this.m.f + 1, EnumDirection.SOUTH, i);
                WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b, this.m.c + 1, EnumDirection.EAST, i);
            }

            if (this.c) {
                if (random.nextBoolean()) {
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b + 3 + 1, this.m.c - 1, EnumDirection.NORTH, i);
                }

                if (random.nextBoolean()) {
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b + 3 + 1, this.m.c + 1, EnumDirection.WEST, i);
                }

                if (random.nextBoolean()) {
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b + 3 + 1, this.m.c + 1, EnumDirection.EAST, i);
                }

                if (random.nextBoolean()) {
                    WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + 1, this.m.b + 3 + 1, this.m.f + 1, EnumDirection.SOUTH, i);
                }
            }

        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair chunkcoordintpair) {
            if (this.a((IBlockAccess) generatoraccess, structureboundingbox)) {
                return false;
            } else {
                IBlockData iblockdata = this.ad_();

                if (this.c) {
                    this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.b, this.m.c, this.m.d - 1, this.m.b + 3 - 1, this.m.f, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                    this.a(generatoraccess, structureboundingbox, this.m.a, this.m.b, this.m.c + 1, this.m.d, this.m.b + 3 - 1, this.m.f - 1, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                    this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.e - 2, this.m.c, this.m.d - 1, this.m.e, this.m.f, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                    this.a(generatoraccess, structureboundingbox, this.m.a, this.m.e - 2, this.m.c + 1, this.m.d, this.m.e, this.m.f - 1, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                    this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.b + 3, this.m.c + 1, this.m.d - 1, this.m.b + 3, this.m.f - 1, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.b, this.m.c, this.m.d - 1, this.m.e, this.m.f, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                    this.a(generatoraccess, structureboundingbox, this.m.a, this.m.b, this.m.c + 1, this.m.d, this.m.e, this.m.f - 1, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
                }

                this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.b, this.m.c + 1, this.m.e);
                this.a(generatoraccess, structureboundingbox, this.m.a + 1, this.m.b, this.m.f - 1, this.m.e);
                this.a(generatoraccess, structureboundingbox, this.m.d - 1, this.m.b, this.m.c + 1, this.m.e);
                this.a(generatoraccess, structureboundingbox, this.m.d - 1, this.m.b, this.m.f - 1, this.m.e);

                for (int i = this.m.a; i <= this.m.d; ++i) {
                    for (int j = this.m.c; j <= this.m.f; ++j) {
                        if (this.a((IBlockAccess) generatoraccess, i, this.m.b - 1, j, structureboundingbox).isAir() && this.a((IWorldReader) generatoraccess, i, this.m.b - 1, j, structureboundingbox)) {
                            this.a(generatoraccess, iblockdata, i, this.m.b - 1, j, structureboundingbox);
                        }
                    }
                }

                return true;
            }
        }

        private void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
            if (!this.a((IBlockAccess) generatoraccess, i, l + 1, k, structureboundingbox).isAir()) {
                this.a(generatoraccess, structureboundingbox, i, j, k, i, l, k, this.ad_(), WorldGenMineshaftPieces.WorldGenMineshaftCross.l, false);
            }

        }
    }

    public static class WorldGenMineshaftCorridor extends WorldGenMineshaftPieces.c {

        private boolean b;
        private boolean c;
        private boolean d;
        private int e;

        public WorldGenMineshaftCorridor() {}

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setBoolean("hr", this.b);
            nbttagcompound.setBoolean("sc", this.c);
            nbttagcompound.setBoolean("hps", this.d);
            nbttagcompound.setInt("Num", this.e);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            this.b = nbttagcompound.getBoolean("hr");
            this.c = nbttagcompound.getBoolean("sc");
            this.d = nbttagcompound.getBoolean("hps");
            this.e = nbttagcompound.getInt("Num");
        }

        public WorldGenMineshaftCorridor(int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection, WorldGenMineshaft.Type worldgenmineshaft_type) {
            super(i, worldgenmineshaft_type);
            this.a(enumdirection);
            this.m = structureboundingbox;
            this.b = random.nextInt(3) == 0;
            this.c = !this.b && random.nextInt(23) == 0;
            if (this.f().k() == EnumDirection.EnumAxis.Z) {
                this.e = structureboundingbox.e() / 5;
            } else {
                this.e = structureboundingbox.c() / 5;
            }

        }

        public static StructureBoundingBox a(List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection) {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 3 - 1, k);

            int l;

            for (l = random.nextInt(3) + 2; l > 0; --l) {
                int i1 = l * 5;

                switch (enumdirection) {
                case NORTH:
                default:
                    structureboundingbox.d = i + 3 - 1;
                    structureboundingbox.c = k - (i1 - 1);
                    break;

                case SOUTH:
                    structureboundingbox.d = i + 3 - 1;
                    structureboundingbox.f = k + i1 - 1;
                    break;

                case WEST:
                    structureboundingbox.a = i - (i1 - 1);
                    structureboundingbox.f = k + 3 - 1;
                    break;

                case EAST:
                    structureboundingbox.d = i + i1 - 1;
                    structureboundingbox.f = k + 3 - 1;
                }

                if (StructurePiece.a(list, structureboundingbox) == null) {
                    break;
                }
            }

            return l > 0 ? structureboundingbox : null;
        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            int i = this.e();
            int j = random.nextInt(4);
            EnumDirection enumdirection = this.f();

            if (enumdirection != null) {
                switch (enumdirection) {
                case NORTH:
                default:
                    if (j <= 1) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b - 1 + random.nextInt(3), this.m.c - 1, enumdirection, i);
                    } else if (j == 2) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b - 1 + random.nextInt(3), this.m.c, EnumDirection.WEST, i);
                    } else {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b - 1 + random.nextInt(3), this.m.c, EnumDirection.EAST, i);
                    }
                    break;

                case SOUTH:
                    if (j <= 1) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b - 1 + random.nextInt(3), this.m.f + 1, enumdirection, i);
                    } else if (j == 2) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b - 1 + random.nextInt(3), this.m.f - 3, EnumDirection.WEST, i);
                    } else {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b - 1 + random.nextInt(3), this.m.f - 3, EnumDirection.EAST, i);
                    }
                    break;

                case WEST:
                    if (j <= 1) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b - 1 + random.nextInt(3), this.m.c, enumdirection, i);
                    } else if (j == 2) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b - 1 + random.nextInt(3), this.m.c - 1, EnumDirection.NORTH, i);
                    } else {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a, this.m.b - 1 + random.nextInt(3), this.m.f + 1, EnumDirection.SOUTH, i);
                    }
                    break;

                case EAST:
                    if (j <= 1) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b - 1 + random.nextInt(3), this.m.c, enumdirection, i);
                    } else if (j == 2) {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d - 3, this.m.b - 1 + random.nextInt(3), this.m.c - 1, EnumDirection.NORTH, i);
                    } else {
                        WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d - 3, this.m.b - 1 + random.nextInt(3), this.m.f + 1, EnumDirection.SOUTH, i);
                    }
                }
            }

            if (i < 8) {
                int k;
                int l;

                if (enumdirection != EnumDirection.NORTH && enumdirection != EnumDirection.SOUTH) {
                    for (k = this.m.a + 3; k + 3 <= this.m.d; k += 5) {
                        l = random.nextInt(5);
                        if (l == 0) {
                            WorldGenMineshaftPieces.b(structurepiece, list, random, k, this.m.b, this.m.c - 1, EnumDirection.NORTH, i + 1);
                        } else if (l == 1) {
                            WorldGenMineshaftPieces.b(structurepiece, list, random, k, this.m.b, this.m.f + 1, EnumDirection.SOUTH, i + 1);
                        }
                    }
                } else {
                    for (k = this.m.c + 3; k + 3 <= this.m.f; k += 5) {
                        l = random.nextInt(5);
                        if (l == 0) {
                            WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b, k, EnumDirection.WEST, i + 1);
                        } else if (l == 1) {
                            WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b, k, EnumDirection.EAST, i + 1);
                        }
                    }
                }
            }

        }

        protected boolean a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, Random random, int i, int j, int k, MinecraftKey minecraftkey) {
            BlockPosition blockposition = new BlockPosition(this.a(i, k), this.d(j), this.b(i, k));

            if (structureboundingbox.b((BaseBlockPosition) blockposition) && generatoraccess.getType(blockposition).isAir() && !generatoraccess.getType(blockposition.down()).isAir()) {
                IBlockData iblockdata = (IBlockData) Blocks.RAIL.getBlockData().set(BlockMinecartTrack.SHAPE, random.nextBoolean() ? BlockPropertyTrackPosition.NORTH_SOUTH : BlockPropertyTrackPosition.EAST_WEST);

                this.a(generatoraccess, iblockdata, i, j, k, structureboundingbox);
                EntityMinecartChest entityminecartchest = new EntityMinecartChest(generatoraccess.getMinecraftWorld(), (double) ((float) blockposition.getX() + 0.5F), (double) ((float) blockposition.getY() + 0.5F), (double) ((float) blockposition.getZ() + 0.5F));

                entityminecartchest.a(minecraftkey, random.nextLong());
                generatoraccess.addEntity(entityminecartchest);
                return true;
            } else {
                return false;
            }
        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair chunkcoordintpair) {
            if (this.a((IBlockAccess) generatoraccess, structureboundingbox)) {
                return false;
            } else {
                boolean flag = false;
                boolean flag1 = true;
                boolean flag2 = false;
                boolean flag3 = true;
                int i = this.e * 5 - 1;
                IBlockData iblockdata = this.ad_();

                this.a(generatoraccess, structureboundingbox, 0, 0, 0, 2, 1, i, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                this.a(generatoraccess, structureboundingbox, random, 0.8F, 0, 2, 0, 2, 2, i, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false, false);
                if (this.c) {
                    this.a(generatoraccess, structureboundingbox, random, 0.6F, 0, 0, 0, 2, 1, i, Blocks.COBWEB.getBlockData(), WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false, true);
                }

                int j;
                int k;

                for (j = 0; j < this.e; ++j) {
                    k = 2 + j * 5;
                    this.a(generatoraccess, structureboundingbox, 0, 0, k, 2, 2, random);
                    this.a(generatoraccess, structureboundingbox, random, 0.1F, 0, 2, k - 1);
                    this.a(generatoraccess, structureboundingbox, random, 0.1F, 2, 2, k - 1);
                    this.a(generatoraccess, structureboundingbox, random, 0.1F, 0, 2, k + 1);
                    this.a(generatoraccess, structureboundingbox, random, 0.1F, 2, 2, k + 1);
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, 0, 2, k - 2);
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, 2, 2, k - 2);
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, 0, 2, k + 2);
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, 2, 2, k + 2);
                    if (random.nextInt(100) == 0) {
                        this.a(generatoraccess, structureboundingbox, random, 2, 0, k - 1, LootTables.f);
                    }

                    if (random.nextInt(100) == 0) {
                        this.a(generatoraccess, structureboundingbox, random, 0, 0, k + 1, LootTables.f);
                    }

                    if (this.c && !this.d) {
                        int l = this.d(0);
                        int i1 = k - 1 + random.nextInt(3);
                        int j1 = this.a(1, i1);
                        int k1 = this.b(1, i1);
                        BlockPosition blockposition = new BlockPosition(j1, l, k1);

                        if (structureboundingbox.b((BaseBlockPosition) blockposition) && this.a((IWorldReader) generatoraccess, 1, 0, i1, structureboundingbox)) {
                            this.d = true;
                            generatoraccess.setTypeAndData(blockposition, Blocks.SPAWNER.getBlockData(), 2);
                            TileEntity tileentity = generatoraccess.getTileEntity(blockposition);

                            if (tileentity instanceof TileEntityMobSpawner) {
                                ((TileEntityMobSpawner) tileentity).getSpawner().setMobName(EntityTypes.CAVE_SPIDER);
                            }
                        }
                    }
                }

                for (j = 0; j <= 2; ++j) {
                    for (k = 0; k <= i; ++k) {
                        boolean flag4 = true;
                        IBlockData iblockdata1 = this.a((IBlockAccess) generatoraccess, j, -1, k, structureboundingbox);

                        if (iblockdata1.isAir() && this.a((IWorldReader) generatoraccess, j, -1, k, structureboundingbox)) {
                            boolean flag5 = true;

                            this.a(generatoraccess, iblockdata, j, -1, k, structureboundingbox);
                        }
                    }
                }

                if (this.b) {
                    IBlockData iblockdata2 = (IBlockData) Blocks.RAIL.getBlockData().set(BlockMinecartTrack.SHAPE, BlockPropertyTrackPosition.NORTH_SOUTH);

                    for (k = 0; k <= i; ++k) {
                        IBlockData iblockdata3 = this.a((IBlockAccess) generatoraccess, 1, -1, k, structureboundingbox);

                        if (!iblockdata3.isAir() && iblockdata3.f(generatoraccess, new BlockPosition(this.a(1, k), this.d(-1), this.b(1, k)))) {
                            float f = this.a((IWorldReader) generatoraccess, 1, 0, k, structureboundingbox) ? 0.7F : 0.9F;

                            this.a(generatoraccess, structureboundingbox, random, f, 1, 0, k, iblockdata2);
                        }
                    }
                }

                return true;
            }
        }

        private void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, Random random) {
            if (this.a(generatoraccess, structureboundingbox, i, i1, l, k)) {
                IBlockData iblockdata = this.ad_();
                IBlockData iblockdata1 = this.b();

                this.a(generatoraccess, structureboundingbox, i, j, k, i, l - 1, k, (IBlockData) iblockdata1.set(BlockFence.WEST, Boolean.valueOf(true)), WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                this.a(generatoraccess, structureboundingbox, i1, j, k, i1, l - 1, k, (IBlockData) iblockdata1.set(BlockFence.EAST, Boolean.valueOf(true)), WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                if (random.nextInt(4) == 0) {
                    this.a(generatoraccess, structureboundingbox, i, l, k, i, l, k, iblockdata, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                    this.a(generatoraccess, structureboundingbox, i1, l, k, i1, l, k, iblockdata, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                } else {
                    this.a(generatoraccess, structureboundingbox, i, l, k, i1, l, k, iblockdata, WorldGenMineshaftPieces.WorldGenMineshaftCorridor.l, false);
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, i + 1, l, k - 1, (IBlockData) Blocks.WALL_TORCH.getBlockData().set(BlockTorchWall.a, EnumDirection.NORTH));
                    this.a(generatoraccess, structureboundingbox, random, 0.05F, i + 1, l, k + 1, (IBlockData) Blocks.WALL_TORCH.getBlockData().set(BlockTorchWall.a, EnumDirection.SOUTH));
                }

            }
        }

        private void a(GeneratorAccess generatoraccess, StructureBoundingBox structureboundingbox, Random random, float f, int i, int j, int k) {
            if (this.a((IWorldReader) generatoraccess, i, j, k, structureboundingbox)) {
                this.a(generatoraccess, structureboundingbox, random, f, i, j, k, Blocks.COBWEB.getBlockData());
            }

        }
    }

    public static class WorldGenMineshaftRoom extends WorldGenMineshaftPieces.c {

        private final List<StructureBoundingBox> b = Lists.newLinkedList();

        public WorldGenMineshaftRoom() {}

        public WorldGenMineshaftRoom(int i, Random random, int j, int k, WorldGenMineshaft.Type worldgenmineshaft_type) {
            super(i, worldgenmineshaft_type);
            this.a = worldgenmineshaft_type;
            this.m = new StructureBoundingBox(j, 50, k, j + 7 + random.nextInt(6), 54 + random.nextInt(6), k + 7 + random.nextInt(6));
        }

        public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
            int i = this.e();
            int j = this.m.d() - 3 - 1;

            if (j <= 0) {
                j = 1;
            }

            int k;
            WorldGenMineshaftPieces.c worldgenmineshaftpieces_c;
            StructureBoundingBox structureboundingbox;

            for (k = 0; k < this.m.c(); k += 4) {
                k += random.nextInt(this.m.c());
                if (k + 3 > this.m.c()) {
                    break;
                }

                worldgenmineshaftpieces_c = WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + k, this.m.b + random.nextInt(j) + 1, this.m.c - 1, EnumDirection.NORTH, i);
                if (worldgenmineshaftpieces_c != null) {
                    structureboundingbox = worldgenmineshaftpieces_c.d();
                    this.b.add(new StructureBoundingBox(structureboundingbox.a, structureboundingbox.b, this.m.c, structureboundingbox.d, structureboundingbox.e, this.m.c + 1));
                }
            }

            for (k = 0; k < this.m.c(); k += 4) {
                k += random.nextInt(this.m.c());
                if (k + 3 > this.m.c()) {
                    break;
                }

                worldgenmineshaftpieces_c = WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a + k, this.m.b + random.nextInt(j) + 1, this.m.f + 1, EnumDirection.SOUTH, i);
                if (worldgenmineshaftpieces_c != null) {
                    structureboundingbox = worldgenmineshaftpieces_c.d();
                    this.b.add(new StructureBoundingBox(structureboundingbox.a, structureboundingbox.b, this.m.f - 1, structureboundingbox.d, structureboundingbox.e, this.m.f));
                }
            }

            for (k = 0; k < this.m.e(); k += 4) {
                k += random.nextInt(this.m.e());
                if (k + 3 > this.m.e()) {
                    break;
                }

                worldgenmineshaftpieces_c = WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.a - 1, this.m.b + random.nextInt(j) + 1, this.m.c + k, EnumDirection.WEST, i);
                if (worldgenmineshaftpieces_c != null) {
                    structureboundingbox = worldgenmineshaftpieces_c.d();
                    this.b.add(new StructureBoundingBox(this.m.a, structureboundingbox.b, structureboundingbox.c, this.m.a + 1, structureboundingbox.e, structureboundingbox.f));
                }
            }

            for (k = 0; k < this.m.e(); k += 4) {
                k += random.nextInt(this.m.e());
                if (k + 3 > this.m.e()) {
                    break;
                }

                worldgenmineshaftpieces_c = WorldGenMineshaftPieces.b(structurepiece, list, random, this.m.d + 1, this.m.b + random.nextInt(j) + 1, this.m.c + k, EnumDirection.EAST, i);
                if (worldgenmineshaftpieces_c != null) {
                    structureboundingbox = worldgenmineshaftpieces_c.d();
                    this.b.add(new StructureBoundingBox(this.m.d - 1, structureboundingbox.b, structureboundingbox.c, this.m.d, structureboundingbox.e, structureboundingbox.f));
                }
            }

        }

        public boolean a(GeneratorAccess generatoraccess, Random random, StructureBoundingBox structureboundingbox, ChunkCoordIntPair chunkcoordintpair) {
            if (this.a((IBlockAccess) generatoraccess, structureboundingbox)) {
                return false;
            } else {
                this.a(generatoraccess, structureboundingbox, this.m.a, this.m.b, this.m.c, this.m.d, this.m.b, this.m.f, Blocks.DIRT.getBlockData(), WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, true);
                this.a(generatoraccess, structureboundingbox, this.m.a, this.m.b + 1, this.m.c, this.m.d, Math.min(this.m.b + 3, this.m.e), this.m.f, WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, false);
                Iterator iterator = this.b.iterator();

                while (iterator.hasNext()) {
                    StructureBoundingBox structureboundingbox1 = (StructureBoundingBox) iterator.next();

                    this.a(generatoraccess, structureboundingbox, structureboundingbox1.a, structureboundingbox1.e - 2, structureboundingbox1.c, structureboundingbox1.d, structureboundingbox1.e, structureboundingbox1.f, WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, false);
                }

                this.a(generatoraccess, structureboundingbox, this.m.a, this.m.b + 4, this.m.c, this.m.d, this.m.e, this.m.f, WorldGenMineshaftPieces.WorldGenMineshaftRoom.l, false);
                return true;
            }
        }

        public void a(int i, int j, int k) {
            super.a(i, j, k);
            Iterator iterator = this.b.iterator();

            while (iterator.hasNext()) {
                StructureBoundingBox structureboundingbox = (StructureBoundingBox) iterator.next();

                structureboundingbox.a(i, j, k);
            }

        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            NBTTagList nbttaglist = new NBTTagList();
            Iterator iterator = this.b.iterator();

            while (iterator.hasNext()) {
                StructureBoundingBox structureboundingbox = (StructureBoundingBox) iterator.next();

                nbttaglist.add((NBTBase) structureboundingbox.g());
            }

            nbttagcompound.set("Entrances", nbttaglist);
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            super.a(nbttagcompound, definedstructuremanager);
            NBTTagList nbttaglist = nbttagcompound.getList("Entrances", 11);

            for (int i = 0; i < nbttaglist.size(); ++i) {
                this.b.add(new StructureBoundingBox(nbttaglist.i(i)));
            }

        }
    }

    abstract static class c extends StructurePiece {

        protected WorldGenMineshaft.Type a;

        public c() {}

        public c(int i, WorldGenMineshaft.Type worldgenmineshaft_type) {
            super(i);
            this.a = worldgenmineshaft_type;
        }

        protected void a(NBTTagCompound nbttagcompound) {
            nbttagcompound.setInt("MST", this.a.ordinal());
        }

        protected void a(NBTTagCompound nbttagcompound, DefinedStructureManager definedstructuremanager) {
            this.a = WorldGenMineshaft.Type.a(nbttagcompound.getInt("MST"));
        }

        protected IBlockData ad_() {
            switch (this.a) {
            case NORMAL:
            default:
                return Blocks.OAK_PLANKS.getBlockData();

            case MESA:
                return Blocks.DARK_OAK_PLANKS.getBlockData();
            }
        }

        protected IBlockData b() {
            switch (this.a) {
            case NORMAL:
            default:
                return Blocks.OAK_FENCE.getBlockData();

            case MESA:
                return Blocks.DARK_OAK_FENCE.getBlockData();
            }
        }

        protected boolean a(IBlockAccess iblockaccess, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
            for (int i1 = i; i1 <= j; ++i1) {
                if (this.a(iblockaccess, i1, k + 1, l, structureboundingbox).isAir()) {
                    return false;
                }
            }

            return true;
        }
    }
}

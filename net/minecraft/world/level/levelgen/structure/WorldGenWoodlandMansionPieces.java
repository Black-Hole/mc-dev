package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.monster.EntityIllagerAbstract;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.storage.loot.LootTables;

public class WorldGenWoodlandMansionPieces {

    public WorldGenWoodlandMansionPieces() {}

    public static void a(DefinedStructureManager definedstructuremanager, BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<WorldGenWoodlandMansionPieces.i> list, Random random) {
        WorldGenWoodlandMansionPieces.c worldgenwoodlandmansionpieces_c = new WorldGenWoodlandMansionPieces.c(random);
        WorldGenWoodlandMansionPieces.d worldgenwoodlandmansionpieces_d = new WorldGenWoodlandMansionPieces.d(definedstructuremanager, random);

        worldgenwoodlandmansionpieces_d.a(blockposition, enumblockrotation, list, worldgenwoodlandmansionpieces_c);
    }

    public static void a(String[] astring) {
        Random random = new Random();
        long i = random.nextLong();

        System.out.println("Seed: " + i);
        random.setSeed(i);
        WorldGenWoodlandMansionPieces.c worldgenwoodlandmansionpieces_c = new WorldGenWoodlandMansionPieces.c(random);

        worldgenwoodlandmansionpieces_c.a();
    }

    private static class c {

        private static final int DEFAULT_SIZE = 11;
        private static final int CLEAR = 0;
        private static final int CORRIDOR = 1;
        private static final int ROOM = 2;
        private static final int START_ROOM = 3;
        private static final int TEST_ROOM = 4;
        private static final int BLOCKED = 5;
        private static final int ROOM_1x1 = 65536;
        private static final int ROOM_1x2 = 131072;
        private static final int ROOM_2x2 = 262144;
        private static final int ROOM_ORIGIN_FLAG = 1048576;
        private static final int ROOM_DOOR_FLAG = 2097152;
        private static final int ROOM_STAIRS_FLAG = 4194304;
        private static final int ROOM_CORRIDOR_FLAG = 8388608;
        private static final int ROOM_TYPE_MASK = 983040;
        private static final int ROOM_ID_MASK = 65535;
        private final Random random;
        final WorldGenWoodlandMansionPieces.g baseGrid;
        final WorldGenWoodlandMansionPieces.g thirdFloorGrid;
        final WorldGenWoodlandMansionPieces.g[] floorRooms;
        final int entranceX;
        final int entranceY;

        public c(Random random) {
            this.random = random;
            boolean flag = true;

            this.entranceX = 7;
            this.entranceY = 4;
            this.baseGrid = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.baseGrid.a(this.entranceX, this.entranceY, this.entranceX + 1, this.entranceY + 1, 3);
            this.baseGrid.a(this.entranceX - 1, this.entranceY, this.entranceX - 1, this.entranceY + 1, 2);
            this.baseGrid.a(this.entranceX + 2, this.entranceY - 2, this.entranceX + 3, this.entranceY + 3, 5);
            this.baseGrid.a(this.entranceX + 1, this.entranceY - 2, this.entranceX + 1, this.entranceY - 1, 1);
            this.baseGrid.a(this.entranceX + 1, this.entranceY + 2, this.entranceX + 1, this.entranceY + 3, 1);
            this.baseGrid.a(this.entranceX - 1, this.entranceY - 1, 1);
            this.baseGrid.a(this.entranceX - 1, this.entranceY + 2, 1);
            this.baseGrid.a(0, 0, 11, 1, 5);
            this.baseGrid.a(0, 9, 11, 11, 5);
            this.a(this.baseGrid, this.entranceX, this.entranceY - 2, EnumDirection.WEST, 6);
            this.a(this.baseGrid, this.entranceX, this.entranceY + 3, EnumDirection.WEST, 6);
            this.a(this.baseGrid, this.entranceX - 2, this.entranceY - 1, EnumDirection.WEST, 3);
            this.a(this.baseGrid, this.entranceX - 2, this.entranceY + 2, EnumDirection.WEST, 3);

            while (this.a(this.baseGrid)) {
                ;
            }

            this.floorRooms = new WorldGenWoodlandMansionPieces.g[3];
            this.floorRooms[0] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.floorRooms[1] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.floorRooms[2] = new WorldGenWoodlandMansionPieces.g(11, 11, 5);
            this.a(this.baseGrid, this.floorRooms[0]);
            this.a(this.baseGrid, this.floorRooms[1]);
            this.floorRooms[0].a(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
            this.floorRooms[1].a(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
            this.thirdFloorGrid = new WorldGenWoodlandMansionPieces.g(this.baseGrid.width, this.baseGrid.height, 5);
            this.b();
            this.a(this.thirdFloorGrid, this.floorRooms[2]);
        }

        public static boolean a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, int i, int j) {
            int k = worldgenwoodlandmansionpieces_g.a(i, j);

            return k == 1 || k == 2 || k == 3 || k == 4;
        }

        public boolean a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, int i, int j, int k, int l) {
            return (this.floorRooms[k].a(i, j) & '\uffff') == l;
        }

        @Nullable
        public EnumDirection b(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, int i, int j, int k, int l) {
            Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

            EnumDirection enumdirection;

            do {
                if (!iterator.hasNext()) {
                    return null;
                }

                enumdirection = (EnumDirection) iterator.next();
            } while (!this.a(worldgenwoodlandmansionpieces_g, i + enumdirection.getAdjacentX(), j + enumdirection.getAdjacentZ(), k, l));

            return enumdirection;
        }

        private void a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, int i, int j, EnumDirection enumdirection, int k) {
            if (k > 0) {
                worldgenwoodlandmansionpieces_g.a(i, j, 1);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection.getAdjacentX(), j + enumdirection.getAdjacentZ(), 0, 1);

                EnumDirection enumdirection1;

                for (int l = 0; l < 8; ++l) {
                    enumdirection1 = EnumDirection.fromType2(this.random.nextInt(4));
                    if (enumdirection1 != enumdirection.opposite() && (enumdirection1 != EnumDirection.EAST || !this.random.nextBoolean())) {
                        int i1 = i + enumdirection.getAdjacentX();
                        int j1 = j + enumdirection.getAdjacentZ();

                        if (worldgenwoodlandmansionpieces_g.a(i1 + enumdirection1.getAdjacentX(), j1 + enumdirection1.getAdjacentZ()) == 0 && worldgenwoodlandmansionpieces_g.a(i1 + enumdirection1.getAdjacentX() * 2, j1 + enumdirection1.getAdjacentZ() * 2) == 0) {
                            this.a(worldgenwoodlandmansionpieces_g, i + enumdirection.getAdjacentX() + enumdirection1.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection1.getAdjacentZ(), enumdirection1, k - 1);
                            break;
                        }
                    }
                }

                EnumDirection enumdirection2 = enumdirection.g();

                enumdirection1 = enumdirection.h();
                worldgenwoodlandmansionpieces_g.a(i + enumdirection2.getAdjacentX(), j + enumdirection2.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection1.getAdjacentX(), j + enumdirection1.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection.getAdjacentX() + enumdirection2.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection2.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection.getAdjacentX() + enumdirection1.getAdjacentX(), j + enumdirection.getAdjacentZ() + enumdirection1.getAdjacentZ(), 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection.getAdjacentX() * 2, j + enumdirection.getAdjacentZ() * 2, 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection2.getAdjacentX() * 2, j + enumdirection2.getAdjacentZ() * 2, 0, 2);
                worldgenwoodlandmansionpieces_g.a(i + enumdirection1.getAdjacentX() * 2, j + enumdirection1.getAdjacentZ() * 2, 0, 2);
            }
        }

        private boolean a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g) {
            boolean flag = false;

            for (int i = 0; i < worldgenwoodlandmansionpieces_g.height; ++i) {
                for (int j = 0; j < worldgenwoodlandmansionpieces_g.width; ++j) {
                    if (worldgenwoodlandmansionpieces_g.a(j, i) == 0) {
                        byte b0 = 0;
                        int k = b0 + (a(worldgenwoodlandmansionpieces_g, j + 1, i) ? 1 : 0);

                        k += a(worldgenwoodlandmansionpieces_g, j - 1, i) ? 1 : 0;
                        k += a(worldgenwoodlandmansionpieces_g, j, i + 1) ? 1 : 0;
                        k += a(worldgenwoodlandmansionpieces_g, j, i - 1) ? 1 : 0;
                        if (k >= 3) {
                            worldgenwoodlandmansionpieces_g.a(j, i, 2);
                            flag = true;
                        } else if (k == 2) {
                            byte b1 = 0;
                            int l = b1 + (a(worldgenwoodlandmansionpieces_g, j + 1, i + 1) ? 1 : 0);

                            l += a(worldgenwoodlandmansionpieces_g, j - 1, i + 1) ? 1 : 0;
                            l += a(worldgenwoodlandmansionpieces_g, j + 1, i - 1) ? 1 : 0;
                            l += a(worldgenwoodlandmansionpieces_g, j - 1, i - 1) ? 1 : 0;
                            if (l <= 1) {
                                worldgenwoodlandmansionpieces_g.a(j, i, 2);
                                flag = true;
                            }
                        }
                    }
                }
            }

            return flag;
        }

        private void b() {
            List<Tuple<Integer, Integer>> list = Lists.newArrayList();
            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g = this.floorRooms[1];

            int i;
            int j;

            for (int k = 0; k < this.thirdFloorGrid.height; ++k) {
                for (i = 0; i < this.thirdFloorGrid.width; ++i) {
                    int l = worldgenwoodlandmansionpieces_g.a(i, k);

                    j = l & 983040;
                    if (j == 131072 && (l & 2097152) == 2097152) {
                        list.add(new Tuple<>(i, k));
                    }
                }
            }

            if (list.isEmpty()) {
                this.thirdFloorGrid.a(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
            } else {
                Tuple<Integer, Integer> tuple = (Tuple) list.get(this.random.nextInt(list.size()));

                i = worldgenwoodlandmansionpieces_g.a((Integer) tuple.a(), (Integer) tuple.b());
                worldgenwoodlandmansionpieces_g.a((Integer) tuple.a(), (Integer) tuple.b(), i | 4194304);
                EnumDirection enumdirection = this.b(this.baseGrid, (Integer) tuple.a(), (Integer) tuple.b(), 1, i & '\uffff');

                j = (Integer) tuple.a() + enumdirection.getAdjacentX();
                int i1 = (Integer) tuple.b() + enumdirection.getAdjacentZ();

                for (int j1 = 0; j1 < this.thirdFloorGrid.height; ++j1) {
                    for (int k1 = 0; k1 < this.thirdFloorGrid.width; ++k1) {
                        if (!a(this.baseGrid, k1, j1)) {
                            this.thirdFloorGrid.a(k1, j1, 5);
                        } else if (k1 == (Integer) tuple.a() && j1 == (Integer) tuple.b()) {
                            this.thirdFloorGrid.a(k1, j1, 3);
                        } else if (k1 == j && j1 == i1) {
                            this.thirdFloorGrid.a(k1, j1, 3);
                            this.floorRooms[2].a(k1, j1, 8388608);
                        }
                    }
                }

                List<EnumDirection> list1 = Lists.newArrayList();
                Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumDirection enumdirection1 = (EnumDirection) iterator.next();

                    if (this.thirdFloorGrid.a(j + enumdirection1.getAdjacentX(), i1 + enumdirection1.getAdjacentZ()) == 0) {
                        list1.add(enumdirection1);
                    }
                }

                if (list1.isEmpty()) {
                    this.thirdFloorGrid.a(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
                    worldgenwoodlandmansionpieces_g.a((Integer) tuple.a(), (Integer) tuple.b(), i);
                } else {
                    EnumDirection enumdirection2 = (EnumDirection) list1.get(this.random.nextInt(list1.size()));

                    this.a(this.thirdFloorGrid, j + enumdirection2.getAdjacentX(), i1 + enumdirection2.getAdjacentZ(), enumdirection2, 4);

                    while (this.a(this.thirdFloorGrid)) {
                        ;
                    }

                }
            }
        }

        private void a(WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g1) {
            List<Tuple<Integer, Integer>> list = Lists.newArrayList();

            int i;

            for (i = 0; i < worldgenwoodlandmansionpieces_g.height; ++i) {
                for (int j = 0; j < worldgenwoodlandmansionpieces_g.width; ++j) {
                    if (worldgenwoodlandmansionpieces_g.a(j, i) == 2) {
                        list.add(new Tuple<>(j, i));
                    }
                }
            }

            Collections.shuffle(list, this.random);
            i = 10;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Tuple<Integer, Integer> tuple = (Tuple) iterator.next();
                int k = (Integer) tuple.a();
                int l = (Integer) tuple.b();

                if (worldgenwoodlandmansionpieces_g1.a(k, l) == 0) {
                    int i1 = k;
                    int j1 = k;
                    int k1 = l;
                    int l1 = l;
                    int i2 = 65536;

                    if (worldgenwoodlandmansionpieces_g1.a(k + 1, l) == 0 && worldgenwoodlandmansionpieces_g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces_g1.a(k + 1, l + 1) == 0 && worldgenwoodlandmansionpieces_g.a(k + 1, l) == 2 && worldgenwoodlandmansionpieces_g.a(k, l + 1) == 2 && worldgenwoodlandmansionpieces_g.a(k + 1, l + 1) == 2) {
                        j1 = k + 1;
                        l1 = l + 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces_g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces_g1.a(k - 1, l + 1) == 0 && worldgenwoodlandmansionpieces_g.a(k - 1, l) == 2 && worldgenwoodlandmansionpieces_g.a(k, l + 1) == 2 && worldgenwoodlandmansionpieces_g.a(k - 1, l + 1) == 2) {
                        i1 = k - 1;
                        l1 = l + 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces_g1.a(k, l - 1) == 0 && worldgenwoodlandmansionpieces_g1.a(k - 1, l - 1) == 0 && worldgenwoodlandmansionpieces_g.a(k - 1, l) == 2 && worldgenwoodlandmansionpieces_g.a(k, l - 1) == 2 && worldgenwoodlandmansionpieces_g.a(k - 1, l - 1) == 2) {
                        i1 = k - 1;
                        k1 = l - 1;
                        i2 = 262144;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k + 1, l) == 0 && worldgenwoodlandmansionpieces_g.a(k + 1, l) == 2) {
                        j1 = k + 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k, l + 1) == 0 && worldgenwoodlandmansionpieces_g.a(k, l + 1) == 2) {
                        l1 = l + 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k - 1, l) == 0 && worldgenwoodlandmansionpieces_g.a(k - 1, l) == 2) {
                        i1 = k - 1;
                        i2 = 131072;
                    } else if (worldgenwoodlandmansionpieces_g1.a(k, l - 1) == 0 && worldgenwoodlandmansionpieces_g.a(k, l - 1) == 2) {
                        k1 = l - 1;
                        i2 = 131072;
                    }

                    int j2 = this.random.nextBoolean() ? i1 : j1;
                    int k2 = this.random.nextBoolean() ? k1 : l1;
                    int l2 = 2097152;

                    if (!worldgenwoodlandmansionpieces_g.b(j2, k2, 1)) {
                        j2 = j2 == i1 ? j1 : i1;
                        k2 = k2 == k1 ? l1 : k1;
                        if (!worldgenwoodlandmansionpieces_g.b(j2, k2, 1)) {
                            k2 = k2 == k1 ? l1 : k1;
                            if (!worldgenwoodlandmansionpieces_g.b(j2, k2, 1)) {
                                j2 = j2 == i1 ? j1 : i1;
                                k2 = k2 == k1 ? l1 : k1;
                                if (!worldgenwoodlandmansionpieces_g.b(j2, k2, 1)) {
                                    l2 = 0;
                                    j2 = i1;
                                    k2 = k1;
                                }
                            }
                        }
                    }

                    for (int i3 = k1; i3 <= l1; ++i3) {
                        for (int j3 = i1; j3 <= j1; ++j3) {
                            if (j3 == j2 && i3 == k2) {
                                worldgenwoodlandmansionpieces_g1.a(j3, i3, 1048576 | l2 | i2 | i);
                            } else {
                                worldgenwoodlandmansionpieces_g1.a(j3, i3, i2 | i);
                            }
                        }
                    }

                    ++i;
                }
            }

        }

        public void a() {
            for (int i = 0; i < 2; ++i) {
                WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g = i == 0 ? this.baseGrid : this.thirdFloorGrid;

                for (int j = 0; j < worldgenwoodlandmansionpieces_g.height; ++j) {
                    for (int k = 0; k < worldgenwoodlandmansionpieces_g.width; ++k) {
                        int l = worldgenwoodlandmansionpieces_g.a(k, j);

                        if (l == 1) {
                            System.out.print("+");
                        } else if (l == 4) {
                            System.out.print("x");
                        } else if (l == 2) {
                            System.out.print("X");
                        } else if (l == 3) {
                            System.out.print("O");
                        } else if (l == 5) {
                            System.out.print("#");
                        } else {
                            System.out.print(" ");
                        }
                    }

                    System.out.println("");
                }

                System.out.println("");
            }

        }
    }

    private static class d {

        private final DefinedStructureManager structureManager;
        private final Random random;
        private int startX;
        private int startY;

        public d(DefinedStructureManager definedstructuremanager, Random random) {
            this.structureManager = definedstructuremanager;
            this.random = random;
        }

        public void a(BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.c worldgenwoodlandmansionpieces_c) {
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e = new WorldGenWoodlandMansionPieces.e();

            worldgenwoodlandmansionpieces_e.position = blockposition;
            worldgenwoodlandmansionpieces_e.rotation = enumblockrotation;
            worldgenwoodlandmansionpieces_e.wallType = "wall_flat";
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e1 = new WorldGenWoodlandMansionPieces.e();

            this.a(list, worldgenwoodlandmansionpieces_e);
            worldgenwoodlandmansionpieces_e1.position = worldgenwoodlandmansionpieces_e.position.up(8);
            worldgenwoodlandmansionpieces_e1.rotation = worldgenwoodlandmansionpieces_e.rotation;
            worldgenwoodlandmansionpieces_e1.wallType = "wall_window";
            if (!list.isEmpty()) {
                ;
            }

            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g = worldgenwoodlandmansionpieces_c.baseGrid;
            WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g1 = worldgenwoodlandmansionpieces_c.thirdFloorGrid;

            this.startX = worldgenwoodlandmansionpieces_c.entranceX + 1;
            this.startY = worldgenwoodlandmansionpieces_c.entranceY + 1;
            int i = worldgenwoodlandmansionpieces_c.entranceX + 1;
            int j = worldgenwoodlandmansionpieces_c.entranceY;

            this.a(list, worldgenwoodlandmansionpieces_e, worldgenwoodlandmansionpieces_g, EnumDirection.SOUTH, this.startX, this.startY, i, j);
            this.a(list, worldgenwoodlandmansionpieces_e1, worldgenwoodlandmansionpieces_g, EnumDirection.SOUTH, this.startX, this.startY, i, j);
            WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e2 = new WorldGenWoodlandMansionPieces.e();

            worldgenwoodlandmansionpieces_e2.position = worldgenwoodlandmansionpieces_e.position.up(19);
            worldgenwoodlandmansionpieces_e2.rotation = worldgenwoodlandmansionpieces_e.rotation;
            worldgenwoodlandmansionpieces_e2.wallType = "wall_window";
            boolean flag = false;

            int k;

            for (int l = 0; l < worldgenwoodlandmansionpieces_g1.height && !flag; ++l) {
                for (k = worldgenwoodlandmansionpieces_g1.width - 1; k >= 0 && !flag; --k) {
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g1, k, l)) {
                        worldgenwoodlandmansionpieces_e2.position = worldgenwoodlandmansionpieces_e2.position.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (l - this.startY) * 8);
                        worldgenwoodlandmansionpieces_e2.position = worldgenwoodlandmansionpieces_e2.position.shift(enumblockrotation.a(EnumDirection.EAST), (k - this.startX) * 8);
                        this.b(list, worldgenwoodlandmansionpieces_e2);
                        this.a(list, worldgenwoodlandmansionpieces_e2, worldgenwoodlandmansionpieces_g1, EnumDirection.SOUTH, k, l, k, l);
                        flag = true;
                    }
                }
            }

            this.a(list, blockposition.up(16), enumblockrotation, worldgenwoodlandmansionpieces_g, worldgenwoodlandmansionpieces_g1);
            this.a(list, blockposition.up(27), enumblockrotation, worldgenwoodlandmansionpieces_g1, (WorldGenWoodlandMansionPieces.g) null);
            if (!list.isEmpty()) {
                ;
            }

            WorldGenWoodlandMansionPieces.b[] aworldgenwoodlandmansionpieces_b = new WorldGenWoodlandMansionPieces.b[]{new WorldGenWoodlandMansionPieces.a(), new WorldGenWoodlandMansionPieces.f(), new WorldGenWoodlandMansionPieces.h()};

            for (k = 0; k < 3; ++k) {
                BlockPosition blockposition1 = blockposition.up(8 * k + (k == 2 ? 3 : 0));
                WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g2 = worldgenwoodlandmansionpieces_c.floorRooms[k];
                WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g3 = k == 2 ? worldgenwoodlandmansionpieces_g1 : worldgenwoodlandmansionpieces_g;
                String s = k == 0 ? "carpet_south_1" : "carpet_south_2";
                String s1 = k == 0 ? "carpet_west_1" : "carpet_west_2";

                for (int i1 = 0; i1 < worldgenwoodlandmansionpieces_g3.height; ++i1) {
                    for (int j1 = 0; j1 < worldgenwoodlandmansionpieces_g3.width; ++j1) {
                        if (worldgenwoodlandmansionpieces_g3.a(j1, i1) == 1) {
                            BlockPosition blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i1 - this.startY) * 8);

                            blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.EAST), (j1 - this.startX) * 8);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "corridor_floor", blockposition2, enumblockrotation));
                            if (worldgenwoodlandmansionpieces_g3.a(j1, i1 - 1) == 1 || (worldgenwoodlandmansionpieces_g2.a(j1, i1 - 1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "carpet_north", blockposition2.shift(enumblockrotation.a(EnumDirection.EAST), 1).up(), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces_g3.a(j1 + 1, i1) == 1 || (worldgenwoodlandmansionpieces_g2.a(j1 + 1, i1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "carpet_east", blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 1).shift(enumblockrotation.a(EnumDirection.EAST), 5).up(), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces_g3.a(j1, i1 + 1) == 1 || (worldgenwoodlandmansionpieces_g2.a(j1, i1 + 1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, s, blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 5).shift(enumblockrotation.a(EnumDirection.WEST), 1), enumblockrotation));
                            }

                            if (worldgenwoodlandmansionpieces_g3.a(j1 - 1, i1) == 1 || (worldgenwoodlandmansionpieces_g2.a(j1 - 1, i1) & 8388608) == 8388608) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, s1, blockposition2.shift(enumblockrotation.a(EnumDirection.WEST), 1).shift(enumblockrotation.a(EnumDirection.NORTH), 1), enumblockrotation));
                            }
                        }
                    }
                }

                String s2 = k == 0 ? "indoors_wall_1" : "indoors_wall_2";
                String s3 = k == 0 ? "indoors_door_1" : "indoors_door_2";
                List<EnumDirection> list1 = Lists.newArrayList();

                for (int k1 = 0; k1 < worldgenwoodlandmansionpieces_g3.height; ++k1) {
                    for (int l1 = 0; l1 < worldgenwoodlandmansionpieces_g3.width; ++l1) {
                        boolean flag1 = k == 2 && worldgenwoodlandmansionpieces_g3.a(l1, k1) == 3;

                        if (worldgenwoodlandmansionpieces_g3.a(l1, k1) == 2 || flag1) {
                            int i2 = worldgenwoodlandmansionpieces_g2.a(l1, k1);
                            int j2 = i2 & 983040;
                            int k2 = i2 & '\uffff';

                            flag1 = flag1 && (i2 & 8388608) == 8388608;
                            list1.clear();
                            if ((i2 & 2097152) == 2097152) {
                                Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                                while (iterator.hasNext()) {
                                    EnumDirection enumdirection = (EnumDirection) iterator.next();

                                    if (worldgenwoodlandmansionpieces_g3.a(l1 + enumdirection.getAdjacentX(), k1 + enumdirection.getAdjacentZ()) == 1) {
                                        list1.add(enumdirection);
                                    }
                                }
                            }

                            EnumDirection enumdirection1 = null;

                            if (!list1.isEmpty()) {
                                enumdirection1 = (EnumDirection) list1.get(this.random.nextInt(list1.size()));
                            } else if ((i2 & 1048576) == 1048576) {
                                enumdirection1 = EnumDirection.UP;
                            }

                            BlockPosition blockposition3 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (k1 - this.startY) * 8);

                            blockposition3 = blockposition3.shift(enumblockrotation.a(EnumDirection.EAST), -1 + (l1 - this.startX) * 8);
                            if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g3, l1 - 1, k1) && !worldgenwoodlandmansionpieces_c.a(worldgenwoodlandmansionpieces_g3, l1 - 1, k1, k, k2)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, enumdirection1 == EnumDirection.WEST ? s3 : s2, blockposition3, enumblockrotation));
                            }

                            BlockPosition blockposition4;

                            if (worldgenwoodlandmansionpieces_g3.a(l1 + 1, k1) == 1 && !flag1) {
                                blockposition4 = blockposition3.shift(enumblockrotation.a(EnumDirection.EAST), 8);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, enumdirection1 == EnumDirection.EAST ? s3 : s2, blockposition4, enumblockrotation));
                            }

                            if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g3, l1, k1 + 1) && !worldgenwoodlandmansionpieces_c.a(worldgenwoodlandmansionpieces_g3, l1, k1 + 1, k, k2)) {
                                blockposition4 = blockposition3.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                blockposition4 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, enumdirection1 == EnumDirection.SOUTH ? s3 : s2, blockposition4, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (worldgenwoodlandmansionpieces_g3.a(l1, k1 - 1) == 1 && !flag1) {
                                blockposition4 = blockposition3.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                blockposition4 = blockposition4.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, enumdirection1 == EnumDirection.NORTH ? s3 : s2, blockposition4, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (j2 == 65536) {
                                this.a(list, blockposition3, enumblockrotation, enumdirection1, aworldgenwoodlandmansionpieces_b[k]);
                            } else {
                                EnumDirection enumdirection2;

                                if (j2 == 131072 && enumdirection1 != null) {
                                    enumdirection2 = worldgenwoodlandmansionpieces_c.b(worldgenwoodlandmansionpieces_g3, l1, k1, k, k2);
                                    boolean flag2 = (i2 & 4194304) == 4194304;

                                    this.a(list, blockposition3, enumblockrotation, enumdirection2, enumdirection1, aworldgenwoodlandmansionpieces_b[k], flag2);
                                } else if (j2 == 262144 && enumdirection1 != null && enumdirection1 != EnumDirection.UP) {
                                    enumdirection2 = enumdirection1.g();
                                    if (!worldgenwoodlandmansionpieces_c.a(worldgenwoodlandmansionpieces_g3, l1 + enumdirection2.getAdjacentX(), k1 + enumdirection2.getAdjacentZ(), k, k2)) {
                                        enumdirection2 = enumdirection2.opposite();
                                    }

                                    this.a(list, blockposition3, enumblockrotation, enumdirection2, enumdirection1, aworldgenwoodlandmansionpieces_b[k]);
                                } else if (j2 == 262144 && enumdirection1 == EnumDirection.UP) {
                                    this.a(list, blockposition3, enumblockrotation, aworldgenwoodlandmansionpieces_b[k]);
                                }
                            }
                        }
                    }
                }
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, EnumDirection enumdirection, int i, int j, int k, int l) {
            int i1 = i;
            int j1 = j;
            EnumDirection enumdirection1 = enumdirection;

            do {
                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, i1 + enumdirection.getAdjacentX(), j1 + enumdirection.getAdjacentZ())) {
                    this.c(list, worldgenwoodlandmansionpieces_e);
                    enumdirection = enumdirection.g();
                    if (i1 != k || j1 != l || enumdirection1 != enumdirection) {
                        this.b(list, worldgenwoodlandmansionpieces_e);
                    }
                } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, i1 + enumdirection.getAdjacentX(), j1 + enumdirection.getAdjacentZ()) && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, i1 + enumdirection.getAdjacentX() + enumdirection.h().getAdjacentX(), j1 + enumdirection.getAdjacentZ() + enumdirection.h().getAdjacentZ())) {
                    this.d(list, worldgenwoodlandmansionpieces_e);
                    i1 += enumdirection.getAdjacentX();
                    j1 += enumdirection.getAdjacentZ();
                    enumdirection = enumdirection.h();
                } else {
                    i1 += enumdirection.getAdjacentX();
                    j1 += enumdirection.getAdjacentZ();
                    if (i1 != k || j1 != l || enumdirection1 != enumdirection) {
                        this.b(list, worldgenwoodlandmansionpieces_e);
                    }
                }
            } while (i1 != k || j1 != l || enumdirection1 != enumdirection);

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g, @Nullable WorldGenWoodlandMansionPieces.g worldgenwoodlandmansionpieces_g1) {
            BlockPosition blockposition1;
            int i;
            int j;
            boolean flag;
            BlockPosition blockposition2;

            for (i = 0; i < worldgenwoodlandmansionpieces_g.height; ++i) {
                for (j = 0; j < worldgenwoodlandmansionpieces_g.width; ++j) {
                    blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i - this.startY) * 8);
                    blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), (j - this.startX) * 8);
                    flag = worldgenwoodlandmansionpieces_g1 != null && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g1, j, i);
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i) && !flag) {
                        list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof", blockposition1.up(3), enumblockrotation));
                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_front", blockposition2, enumblockrotation));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 0);
                            blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_front", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_front", blockposition2, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_front", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                        }
                    }
                }
            }

            if (worldgenwoodlandmansionpieces_g1 != null) {
                for (i = 0; i < worldgenwoodlandmansionpieces_g.height; ++i) {
                    for (j = 0; j < worldgenwoodlandmansionpieces_g.width; ++j) {
                        blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i - this.startY) * 8);
                        blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), (j - this.startX) * 8);
                        flag = WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g1, j, i);
                        if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i) && flag) {
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i)) {
                                blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall", blockposition2, enumblockrotation));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i)) {
                                blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                                blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                                blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 0);
                                blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall", blockposition2, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                                blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                                blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i)) {
                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                                    blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                                    blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.NORTH), 2);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall_corner", blockposition2, enumblockrotation));
                                }

                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                                    blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 8);
                                    blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 7);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall_corner", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                                }
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i)) {
                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                                    blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 2);
                                    blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.NORTH), 1);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall_corner", blockposition2, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                                }

                                if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                                    blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.WEST), 1);
                                    blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 8);
                                    list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "small_wall_corner", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                                }
                            }
                        }
                    }
                }
            }

            for (i = 0; i < worldgenwoodlandmansionpieces_g.height; ++i) {
                for (j = 0; j < worldgenwoodlandmansionpieces_g.width; ++j) {
                    blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.SOUTH), 8 + (i - this.startY) * 8);
                    blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), (j - this.startX) * 8);
                    flag = worldgenwoodlandmansionpieces_g1 != null && WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g1, j, i);
                    if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i) && !flag) {
                        BlockPosition blockposition3;

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 6);
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                                blockposition3 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_corner", blockposition3, enumblockrotation));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i + 1)) {
                                blockposition3 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 5);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_inner_corner", blockposition3, enumblockrotation));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_corner", blockposition2, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j + 1, i - 1)) {
                                blockposition3 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 9);
                                blockposition3 = blockposition3.shift(enumblockrotation.a(EnumDirection.NORTH), 2);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_inner_corner", blockposition3, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            }
                        }

                        if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i)) {
                            blockposition2 = blockposition1.shift(enumblockrotation.a(EnumDirection.EAST), 0);
                            blockposition2 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 0);
                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i + 1)) {
                                blockposition3 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_corner", blockposition3, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i + 1)) {
                                blockposition3 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 8);
                                blockposition3 = blockposition3.shift(enumblockrotation.a(EnumDirection.WEST), 3);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_inner_corner", blockposition3, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j, i - 1)) {
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_corner", blockposition2, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            } else if (WorldGenWoodlandMansionPieces.c.a(worldgenwoodlandmansionpieces_g, j - 1, i - 1)) {
                                blockposition3 = blockposition2.shift(enumblockrotation.a(EnumDirection.SOUTH), 1);
                                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "roof_inner_corner", blockposition3, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
                            }
                        }
                    }
                }
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e) {
            EnumDirection enumdirection = worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.WEST);

            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "entrance", worldgenwoodlandmansionpieces_e.position.shift(enumdirection, 9), worldgenwoodlandmansionpieces_e.rotation));
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.SOUTH), 16);
        }

        private void b(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e) {
            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_e.wallType, worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.EAST), 7), worldgenwoodlandmansionpieces_e.rotation));
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.SOUTH), 8);
        }

        private void c(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e) {
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.SOUTH), -1);
            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, "wall_corner", worldgenwoodlandmansionpieces_e.position, worldgenwoodlandmansionpieces_e.rotation));
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.SOUTH), -7);
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.WEST), -6);
            worldgenwoodlandmansionpieces_e.rotation = worldgenwoodlandmansionpieces_e.rotation.a(EnumBlockRotation.CLOCKWISE_90);
        }

        private void d(List<WorldGenWoodlandMansionPieces.i> list, WorldGenWoodlandMansionPieces.e worldgenwoodlandmansionpieces_e) {
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.SOUTH), 6);
            worldgenwoodlandmansionpieces_e.position = worldgenwoodlandmansionpieces_e.position.shift(worldgenwoodlandmansionpieces_e.rotation.a(EnumDirection.EAST), 8);
            worldgenwoodlandmansionpieces_e.rotation = worldgenwoodlandmansionpieces_e.rotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces_b) {
            EnumBlockRotation enumblockrotation1 = EnumBlockRotation.NONE;
            String s = worldgenwoodlandmansionpieces_b.a(this.random);

            if (enumdirection != EnumDirection.EAST) {
                if (enumdirection == EnumDirection.NORTH) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
                } else if (enumdirection == EnumDirection.WEST) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.CLOCKWISE_180);
                } else if (enumdirection == EnumDirection.SOUTH) {
                    enumblockrotation1 = enumblockrotation1.a(EnumBlockRotation.CLOCKWISE_90);
                } else {
                    s = worldgenwoodlandmansionpieces_b.b(this.random);
                }
            }

            BlockPosition blockposition1 = DefinedStructure.a(new BlockPosition(1, 0, 0), EnumBlockMirror.NONE, enumblockrotation1, 7, 7);

            enumblockrotation1 = enumblockrotation1.a(enumblockrotation);
            blockposition1 = blockposition1.a(enumblockrotation);
            BlockPosition blockposition2 = blockposition.c(blockposition1.getX(), 0, blockposition1.getZ());

            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, s, blockposition2, enumblockrotation1));
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, EnumDirection enumdirection1, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces_b, boolean flag) {
            BlockPosition blockposition1;

            if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.SOUTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation));
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.NORTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation, EnumBlockMirror.LEFT_RIGHT));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.NORTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.SOUTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation, EnumBlockMirror.FRONT_BACK));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.EAST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90), EnumBlockMirror.LEFT_RIGHT));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.WEST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.WEST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90), EnumBlockMirror.FRONT_BACK));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.EAST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.a(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.NORTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.NORTH), 8);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.b(this.random, flag), blockposition1, enumblockrotation));
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.SOUTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 7);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 14);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.b(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180)));
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.EAST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 15);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.b(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.WEST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.WEST), 7);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), 6);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.b(this.random, flag), blockposition1, enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.UP && enumdirection == EnumDirection.EAST) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 15);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.c(this.random), blockposition1, enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90)));
            } else if (enumdirection1 == EnumDirection.UP && enumdirection == EnumDirection.SOUTH) {
                blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);
                blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.NORTH), 0);
                list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.c(this.random), blockposition1, enumblockrotation));
            }

        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumDirection enumdirection, EnumDirection enumdirection1, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces_b) {
            byte b0 = 0;
            byte b1 = 0;
            EnumBlockRotation enumblockrotation1 = enumblockrotation;
            EnumBlockMirror enumblockmirror = EnumBlockMirror.NONE;

            if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.SOUTH) {
                b0 = -7;
            } else if (enumdirection1 == EnumDirection.EAST && enumdirection == EnumDirection.NORTH) {
                b0 = -7;
                b1 = 6;
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.EAST) {
                b0 = 1;
                b1 = 14;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
            } else if (enumdirection1 == EnumDirection.NORTH && enumdirection == EnumDirection.WEST) {
                b0 = 7;
                b1 = 14;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.COUNTERCLOCKWISE_90);
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.WEST) {
                b0 = 7;
                b1 = -8;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90);
            } else if (enumdirection1 == EnumDirection.SOUTH && enumdirection == EnumDirection.EAST) {
                b0 = 1;
                b1 = -8;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_90);
                enumblockmirror = EnumBlockMirror.LEFT_RIGHT;
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.NORTH) {
                b0 = 15;
                b1 = 6;
                enumblockrotation1 = enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180);
            } else if (enumdirection1 == EnumDirection.WEST && enumdirection == EnumDirection.SOUTH) {
                b0 = 15;
                enumblockmirror = EnumBlockMirror.FRONT_BACK;
            }

            BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), b0);

            blockposition1 = blockposition1.shift(enumblockrotation.a(EnumDirection.SOUTH), b1);
            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.d(this.random), blockposition1, enumblockrotation1, enumblockmirror));
        }

        private void a(List<WorldGenWoodlandMansionPieces.i> list, BlockPosition blockposition, EnumBlockRotation enumblockrotation, WorldGenWoodlandMansionPieces.b worldgenwoodlandmansionpieces_b) {
            BlockPosition blockposition1 = blockposition.shift(enumblockrotation.a(EnumDirection.EAST), 1);

            list.add(new WorldGenWoodlandMansionPieces.i(this.structureManager, worldgenwoodlandmansionpieces_b.e(this.random), blockposition1, enumblockrotation, EnumBlockMirror.NONE));
        }
    }

    private static class h extends WorldGenWoodlandMansionPieces.f {

        h() {}
    }

    private static class f extends WorldGenWoodlandMansionPieces.b {

        f() {}

        @Override
        public String a(Random random) {
            int i = random.nextInt(4);

            return "1x1_b" + (i + 1);
        }

        @Override
        public String b(Random random) {
            int i = random.nextInt(4);

            return "1x1_as" + (i + 1);
        }

        @Override
        public String a(Random random, boolean flag) {
            if (flag) {
                return "1x2_c_stairs";
            } else {
                int i = random.nextInt(4);

                return "1x2_c" + (i + 1);
            }
        }

        @Override
        public String b(Random random, boolean flag) {
            if (flag) {
                return "1x2_d_stairs";
            } else {
                int i = random.nextInt(5);

                return "1x2_d" + (i + 1);
            }
        }

        @Override
        public String c(Random random) {
            int i = random.nextInt(1);

            return "1x2_se" + (i + 1);
        }

        @Override
        public String d(Random random) {
            int i = random.nextInt(5);

            return "2x2_b" + (i + 1);
        }

        @Override
        public String e(Random random) {
            return "2x2_s1";
        }
    }

    private static class a extends WorldGenWoodlandMansionPieces.b {

        a() {}

        @Override
        public String a(Random random) {
            int i = random.nextInt(5);

            return "1x1_a" + (i + 1);
        }

        @Override
        public String b(Random random) {
            int i = random.nextInt(4);

            return "1x1_as" + (i + 1);
        }

        @Override
        public String a(Random random, boolean flag) {
            int i = random.nextInt(9);

            return "1x2_a" + (i + 1);
        }

        @Override
        public String b(Random random, boolean flag) {
            int i = random.nextInt(5);

            return "1x2_b" + (i + 1);
        }

        @Override
        public String c(Random random) {
            int i = random.nextInt(2);

            return "1x2_s" + (i + 1);
        }

        @Override
        public String d(Random random) {
            int i = random.nextInt(4);

            return "2x2_a" + (i + 1);
        }

        @Override
        public String e(Random random) {
            return "2x2_s1";
        }
    }

    private abstract static class b {

        b() {}

        public abstract String a(Random random);

        public abstract String b(Random random);

        public abstract String a(Random random, boolean flag);

        public abstract String b(Random random, boolean flag);

        public abstract String c(Random random);

        public abstract String d(Random random);

        public abstract String e(Random random);
    }

    private static class g {

        private final int[][] grid;
        final int width;
        final int height;
        private final int valueIfOutside;

        public g(int i, int j, int k) {
            this.width = i;
            this.height = j;
            this.valueIfOutside = k;
            this.grid = new int[i][j];
        }

        public void a(int i, int j, int k) {
            if (i >= 0 && i < this.width && j >= 0 && j < this.height) {
                this.grid[i][j] = k;
            }

        }

        public void a(int i, int j, int k, int l, int i1) {
            for (int j1 = j; j1 <= l; ++j1) {
                for (int k1 = i; k1 <= k; ++k1) {
                    this.a(k1, j1, i1);
                }
            }

        }

        public int a(int i, int j) {
            return i >= 0 && i < this.width && j >= 0 && j < this.height ? this.grid[i][j] : this.valueIfOutside;
        }

        public void a(int i, int j, int k, int l) {
            if (this.a(i, j) == k) {
                this.a(i, j, l);
            }

        }

        public boolean b(int i, int j, int k) {
            return this.a(i - 1, j) == k || this.a(i + 1, j) == k || this.a(i, j + 1) == k || this.a(i, j - 1) == k;
        }
    }

    private static class e {

        public EnumBlockRotation rotation;
        public BlockPosition position;
        public String wallType;

        e() {}
    }

    public static class i extends DefinedStructurePiece {

        public i(DefinedStructureManager definedstructuremanager, String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation) {
            this(definedstructuremanager, s, blockposition, enumblockrotation, EnumBlockMirror.NONE);
        }

        public i(DefinedStructureManager definedstructuremanager, String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation, EnumBlockMirror enumblockmirror) {
            super(WorldGenFeatureStructurePieceType.WOODLAND_MANSION_PIECE, 0, definedstructuremanager, a(s), s, a(enumblockmirror, enumblockrotation), blockposition);
        }

        public i(WorldServer worldserver, NBTTagCompound nbttagcompound) {
            super(WorldGenFeatureStructurePieceType.WOODLAND_MANSION_PIECE, nbttagcompound, worldserver, (minecraftkey) -> {
                return a(EnumBlockMirror.valueOf(nbttagcompound.getString("Mi")), EnumBlockRotation.valueOf(nbttagcompound.getString("Rot")));
            });
        }

        @Override
        protected MinecraftKey a() {
            return a(this.templateName);
        }

        private static MinecraftKey a(String s) {
            return new MinecraftKey("woodland_mansion/" + s);
        }

        private static DefinedStructureInfo a(EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation) {
            return (new DefinedStructureInfo()).a(true).a(enumblockrotation).a(enumblockmirror).a((DefinedStructureProcessor) DefinedStructureProcessorBlockIgnore.STRUCTURE_BLOCK);
        }

        @Override
        protected void a(WorldServer worldserver, NBTTagCompound nbttagcompound) {
            super.a(worldserver, nbttagcompound);
            nbttagcompound.setString("Rot", this.placeSettings.d().name());
            nbttagcompound.setString("Mi", this.placeSettings.c().name());
        }

        @Override
        protected void a(String s, BlockPosition blockposition, WorldAccess worldaccess, Random random, StructureBoundingBox structureboundingbox) {
            if (s.startsWith("Chest")) {
                EnumBlockRotation enumblockrotation = this.placeSettings.d();
                IBlockData iblockdata = Blocks.CHEST.getBlockData();

                if ("ChestWest".equals(s)) {
                    iblockdata = (IBlockData) iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.WEST));
                } else if ("ChestEast".equals(s)) {
                    iblockdata = (IBlockData) iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.EAST));
                } else if ("ChestSouth".equals(s)) {
                    iblockdata = (IBlockData) iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.SOUTH));
                } else if ("ChestNorth".equals(s)) {
                    iblockdata = (IBlockData) iblockdata.set(BlockChest.FACING, enumblockrotation.a(EnumDirection.NORTH));
                }

                this.a(worldaccess, structureboundingbox, random, blockposition, LootTables.WOODLAND_MANSION, iblockdata);
            } else {
                byte b0 = -1;

                switch (s.hashCode()) {
                    case -1505748702:
                        if (s.equals("Warrior")) {
                            b0 = 1;
                        }
                        break;
                    case 2390418:
                        if (s.equals("Mage")) {
                            b0 = 0;
                        }
                }

                EntityIllagerAbstract entityillagerabstract;

                switch (b0) {
                    case 0:
                        entityillagerabstract = (EntityIllagerAbstract) EntityTypes.EVOKER.a((World) worldaccess.getLevel());
                        break;
                    case 1:
                        entityillagerabstract = (EntityIllagerAbstract) EntityTypes.VINDICATOR.a((World) worldaccess.getLevel());
                        break;
                    default:
                        return;
                }

                entityillagerabstract.setPersistent();
                entityillagerabstract.setPositionRotation(blockposition, 0.0F, 0.0F);
                entityillagerabstract.prepare(worldaccess, worldaccess.getDamageScaler(entityillagerabstract.getChunkCoordinates()), EnumMobSpawn.STRUCTURE, (GroupDataEntity) null, (NBTTagCompound) null);
                worldaccess.addAllEntities(entityillagerabstract);
                worldaccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
            }

        }
    }
}

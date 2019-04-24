package net.minecraft.server;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MobSpawnerPatrol {

    private static final List<MobSpawnerPatrol.a> a = Arrays.asList(new MobSpawnerPatrol.a(EntityTypes.PILLAGER, 80), new MobSpawnerPatrol.a(EntityTypes.VINDICATOR, 20));
    private int b;

    public MobSpawnerPatrol() {}

    public int a(WorldServer worldserver, boolean flag, boolean flag1) {
        if (!flag) {
            return 0;
        } else {
            Random random = worldserver.random;

            --this.b;
            if (this.b > 0) {
                return 0;
            } else {
                this.b += 6000 + random.nextInt(1200);
                long i = worldserver.getDayTime() / 24000L;

                if (i >= 5L && worldserver.J()) {
                    if (random.nextInt(5) != 0) {
                        return 0;
                    } else {
                        int j = worldserver.getPlayers().size();

                        if (j < 1) {
                            return 0;
                        } else {
                            EntityHuman entityhuman = (EntityHuman) worldserver.getPlayers().get(random.nextInt(j));

                            if (entityhuman.isSpectator()) {
                                return 0;
                            } else if (worldserver.b_(entityhuman.getChunkCoordinates())) {
                                return 0;
                            } else {
                                int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                BlockPosition blockposition = (new BlockPosition(entityhuman)).b(k, 0, l);

                                if (!worldserver.isAreaLoaded(blockposition.getX() - 10, blockposition.getY() - 10, blockposition.getZ() - 10, blockposition.getX() + 10, blockposition.getY() + 10, blockposition.getZ() + 10)) {
                                    return 0;
                                } else {
                                    BiomeBase biomebase = worldserver.getBiome(blockposition);
                                    BiomeBase.Geography biomebase_geography = biomebase.o();

                                    if (biomebase_geography != BiomeBase.Geography.PLAINS && biomebase_geography != BiomeBase.Geography.TAIGA && biomebase_geography != BiomeBase.Geography.DESERT && biomebase_geography != BiomeBase.Geography.SAVANNA) {
                                        return 0;
                                    } else {
                                        int i1 = 1;

                                        this.a(worldserver, blockposition, random, true);
                                        int j1 = (int) Math.ceil((double) worldserver.getDamageScaler(blockposition).b());

                                        for (int k1 = 0; k1 < j1; ++k1) {
                                            ++i1;
                                            this.a(worldserver, blockposition, random, false);
                                        }

                                        return i1;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return 0;
                }
            }
        }
    }

    private void a(World world, BlockPosition blockposition, Random random, boolean flag) {
        MobSpawnerPatrol.a mobspawnerpatrol_a = (MobSpawnerPatrol.a) WeightedRandom.a(random, MobSpawnerPatrol.a);
        EntityMonsterPatrolling entitymonsterpatrolling = (EntityMonsterPatrolling) mobspawnerpatrol_a.b.a(world);

        if (entitymonsterpatrolling != null) {
            double d0 = (double) (blockposition.getX() + random.nextInt(5) - random.nextInt(5));
            double d1 = (double) (blockposition.getZ() + random.nextInt(5) - random.nextInt(5));
            BlockPosition blockposition1 = entitymonsterpatrolling.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPosition(d0, (double) blockposition.getY(), d1));

            if (flag) {
                entitymonsterpatrolling.setPatrolLeader(true);
                entitymonsterpatrolling.ee();
            }

            entitymonsterpatrolling.setPosition((double) blockposition1.getX(), (double) blockposition1.getY(), (double) blockposition1.getZ());
            entitymonsterpatrolling.prepare(world, world.getDamageScaler(blockposition1), EnumMobSpawn.PATROL, (GroupDataEntity) null, (NBTTagCompound) null);
            world.addEntity(entitymonsterpatrolling);
        }

    }

    public static class a extends WeightedRandom.WeightedRandomChoice {

        public final EntityTypes<? extends EntityMonsterPatrolling> b;

        public a(EntityTypes<? extends EntityMonsterPatrolling> entitytypes, int i) {
            super(i);
            this.b = entitytypes;
        }
    }
}

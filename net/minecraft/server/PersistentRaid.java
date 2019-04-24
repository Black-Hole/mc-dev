package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;

public class PersistentRaid extends PersistentBase {

    private final Map<Integer, Raid> a = Maps.newHashMap();
    private final WorldServer b;
    private int c;
    private int d;

    public PersistentRaid(WorldServer worldserver) {
        super(a(worldserver.worldProvider));
        this.b = worldserver;
        this.c = 1;
        this.b();
    }

    public Raid a(int i) {
        return (Raid) this.a.get(i);
    }

    public void a() {
        ++this.d;
        Iterator iterator = this.a.values().iterator();

        while (iterator.hasNext()) {
            Raid raid = (Raid) iterator.next();

            if (raid.d()) {
                iterator.remove();
                this.b();
            } else {
                raid.p();
            }
        }

        if (this.d % 200 == 0) {
            this.b();
        }

    }

    public static boolean a(EntityRaider entityraider, Raid raid) {
        return entityraider != null && raid != null && raid.i() != null ? entityraider.isAlive() && entityraider.ej() && entityraider.cv() <= 2400 && entityraider.world.getWorldProvider().getDimensionManager() == raid.i().getWorldProvider().getDimensionManager() : false;
    }

    public static boolean a(EntityLiving entityliving, BlockPosition blockposition, int i) {
        return blockposition.m(new BlockPosition(entityliving.locX, entityliving.locY, entityliving.locZ)) < (double) (i * i + 24);
    }

    @Nullable
    public Raid a(EntityPlayer entityplayer) {
        if (entityplayer.isSpectator()) {
            return null;
        } else {
            DimensionManager dimensionmanager = entityplayer.world.getWorldProvider().getDimensionManager();

            if (dimensionmanager == DimensionManager.NETHER) {
                return null;
            } else {
                BlockPosition blockposition = new BlockPosition(entityplayer);
                Optional<BlockPosition> optional = this.b.B().b((villageplacetype) -> {
                    return villageplacetype == VillagePlaceType.r;
                }, Objects::nonNull, blockposition, 15, VillagePlace.Occupancy.ANY);

                if (!optional.isPresent()) {
                    optional = Optional.of(blockposition);
                }

                Raid raid = this.a(entityplayer.getWorldServer(), (BlockPosition) optional.get());
                boolean flag = false;

                if (!raid.k()) {
                    if (!this.a.containsKey(raid.u())) {
                        this.a.put(raid.u(), raid);
                    }

                    flag = true;
                } else if (raid.n() < raid.m()) {
                    flag = true;
                } else {
                    entityplayer.removeEffect(MobEffects.BAD_OMEN);
                    entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, (byte) 43));
                }

                if (flag) {
                    raid.a((EntityHuman) entityplayer);
                    entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, (byte) 43));
                    if (!raid.c()) {
                        entityplayer.a(StatisticList.RAID_TRIGGER);
                        CriterionTriggers.I.a(entityplayer);
                    }
                }

                this.b();
                return raid;
            }
        }
    }

    private Raid a(WorldServer worldserver, BlockPosition blockposition) {
        Raid raid = worldserver.c_(blockposition);

        return raid != null ? raid : new Raid(this.e(), worldserver, blockposition);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        this.c = nbttagcompound.getInt("NextAvailableID");
        this.d = nbttagcompound.getInt("Tick");
        NBTTagList nbttaglist = nbttagcompound.getList("Raids", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(i);
            Raid raid = new Raid(this.b, nbttagcompound1);

            this.a.put(raid.u(), raid);
        }

    }

    @Override
    public NBTTagCompound b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("NextAvailableID", this.c);
        nbttagcompound.setInt("Tick", this.d);
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = this.a.values().iterator();

        while (iterator.hasNext()) {
            Raid raid = (Raid) iterator.next();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            raid.a(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }

        nbttagcompound.set("Raids", nbttaglist);
        return nbttagcompound;
    }

    public static String a(WorldProvider worldprovider) {
        return "raids" + worldprovider.getDimensionManager().c();
    }

    private int e() {
        return ++this.c;
    }

    @Nullable
    public Raid a(BlockPosition blockposition) {
        Raid raid = null;
        double d0 = 2.147483647E9D;
        Iterator iterator = this.a.values().iterator();

        while (iterator.hasNext()) {
            Raid raid1 = (Raid) iterator.next();
            double d1 = raid1.t().m(blockposition);

            if (raid1.v() && d1 < d0) {
                raid = raid1;
                d0 = d1;
            }
        }

        return raid;
    }
}

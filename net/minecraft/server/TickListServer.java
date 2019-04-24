package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TickListServer<T> implements TickList<T> {

    protected final Predicate<T> a;
    protected final Function<T, MinecraftKey> b;
    protected final Function<MinecraftKey, T> c;
    protected final Set<NextTickListEntry<T>> nextTickListHash = Sets.newHashSet();
    protected final TreeSet<NextTickListEntry<T>> nextTickList = new TreeSet();
    private final WorldServer f;
    private final List<NextTickListEntry<T>> g = Lists.newArrayList();
    private final Consumer<NextTickListEntry<T>> h;

    public TickListServer(WorldServer worldserver, Predicate<T> predicate, Function<T, MinecraftKey> function, Function<MinecraftKey, T> function1, Consumer<NextTickListEntry<T>> consumer) {
        this.a = predicate;
        this.b = function;
        this.c = function1;
        this.f = worldserver;
        this.h = consumer;
    }

    public void a() {
        int i = this.nextTickList.size();

        if (i != this.nextTickListHash.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        } else {
            if (i > 65536) {
                i = 65536;
            }

            this.f.getMethodProfiler().enter("cleaning");

            NextTickListEntry nextticklistentry;

            for (int j = 0; j < i; ++j) {
                nextticklistentry = (NextTickListEntry) this.nextTickList.first();
                if (nextticklistentry.b > this.f.getTime()) {
                    break;
                }

                this.nextTickList.remove(nextticklistentry);
                this.nextTickListHash.remove(nextticklistentry);
                this.g.add(nextticklistentry);
            }

            this.f.getMethodProfiler().exit();
            this.f.getMethodProfiler().enter("ticking");
            Iterator iterator = this.g.iterator();

            while (iterator.hasNext()) {
                nextticklistentry = (NextTickListEntry) iterator.next();
                iterator.remove();
                if (this.f.isLoaded(nextticklistentry.a)) {
                    try {
                        this.h.accept(nextticklistentry);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");

                        CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, (IBlockData) null);
                        throw new ReportedException(crashreport);
                    }
                } else {
                    this.a(nextticklistentry.a, nextticklistentry.a(), 0);
                }
            }

            this.f.getMethodProfiler().exit();
            this.g.clear();
        }
    }

    @Override
    public boolean b(BlockPosition blockposition, T t0) {
        return this.g.contains(new NextTickListEntry<>(blockposition, t0));
    }

    @Override
    public void a(Stream<NextTickListEntry<T>> stream) {
        stream.forEach(this::a);
    }

    public List<NextTickListEntry<T>> a(boolean flag, ChunkCoordIntPair chunkcoordintpair) {
        int i = (chunkcoordintpair.x << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkcoordintpair.z << 4) - 2;
        int l = k + 16 + 2;

        return this.a(new StructureBoundingBox(i, 0, k, j, 256, l), flag);
    }

    public List<NextTickListEntry<T>> a(StructureBoundingBox structureboundingbox, boolean flag) {
        List<NextTickListEntry<T>> list = null;

        for (int i = 0; i < 2; ++i) {
            Iterator iterator;

            if (i == 0) {
                iterator = this.nextTickList.iterator();
            } else {
                iterator = this.g.iterator();
            }

            while (iterator.hasNext()) {
                NextTickListEntry<T> nextticklistentry = (NextTickListEntry) iterator.next();
                BlockPosition blockposition = nextticklistentry.a;

                if (blockposition.getX() >= structureboundingbox.a && blockposition.getX() < structureboundingbox.d && blockposition.getZ() >= structureboundingbox.c && blockposition.getZ() < structureboundingbox.f) {
                    if (flag) {
                        if (i == 0) {
                            this.nextTickListHash.remove(nextticklistentry);
                        }

                        iterator.remove();
                    }

                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(nextticklistentry);
                }
            }
        }

        return (List) (list == null ? Collections.emptyList() : list);
    }

    public void a(StructureBoundingBox structureboundingbox, BlockPosition blockposition) {
        List<NextTickListEntry<T>> list = this.a(structureboundingbox, false);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            NextTickListEntry<T> nextticklistentry = (NextTickListEntry) iterator.next();

            if (structureboundingbox.b((BaseBlockPosition) nextticklistentry.a)) {
                BlockPosition blockposition1 = nextticklistentry.a.a((BaseBlockPosition) blockposition);

                this.b(blockposition1, nextticklistentry.a(), (int) (nextticklistentry.b - this.f.getWorldData().getTime()), nextticklistentry.c);
            }
        }

    }

    public NBTTagList a(ChunkCoordIntPair chunkcoordintpair) {
        List<NextTickListEntry<T>> list = this.a(false, chunkcoordintpair);

        return a(this.b, list, this.f.getTime());
    }

    public static <T> NBTTagList a(Function<T, MinecraftKey> function, Iterable<NextTickListEntry<T>> iterable, long i) {
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            NextTickListEntry<T> nextticklistentry = (NextTickListEntry) iterator.next();
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            nbttagcompound.setString("i", ((MinecraftKey) function.apply(nextticklistentry.a())).toString());
            nbttagcompound.setInt("x", nextticklistentry.a.getX());
            nbttagcompound.setInt("y", nextticklistentry.a.getY());
            nbttagcompound.setInt("z", nextticklistentry.a.getZ());
            nbttagcompound.setInt("t", (int) (nextticklistentry.b - i));
            nbttagcompound.setInt("p", nextticklistentry.c.a());
            nbttaglist.add(nbttagcompound);
        }

        return nbttaglist;
    }

    public void a(NBTTagList nbttaglist) {
        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            T t0 = this.c.apply(new MinecraftKey(nbttagcompound.getString("i")));

            if (t0 != null) {
                this.b(new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z")), t0, nbttagcompound.getInt("t"), TickListPriority.a(nbttagcompound.getInt("p")));
            }
        }

    }

    @Override
    public boolean a(BlockPosition blockposition, T t0) {
        return this.nextTickListHash.contains(new NextTickListEntry<>(blockposition, t0));
    }

    @Override
    public void a(BlockPosition blockposition, T t0, int i, TickListPriority ticklistpriority) {
        if (!this.a.test(t0)) {
            this.c(blockposition, t0, i, ticklistpriority);
        }
    }

    protected void b(BlockPosition blockposition, T t0, int i, TickListPriority ticklistpriority) {
        if (!this.a.test(t0)) {
            this.c(blockposition, t0, i, ticklistpriority);
        }

    }

    private void c(BlockPosition blockposition, T t0, int i, TickListPriority ticklistpriority) {
        NextTickListEntry<T> nextticklistentry = new NextTickListEntry<>(blockposition, t0, (long) i + this.f.getTime(), ticklistpriority);

        this.a(nextticklistentry);
    }

    private void a(NextTickListEntry<T> nextticklistentry) {
        if (!this.nextTickListHash.contains(nextticklistentry)) {
            this.nextTickListHash.add(nextticklistentry);
            this.nextTickList.add(nextticklistentry);
        }

    }
}

package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class TickListChunk<T> implements TickList<T> {

    protected final Set<NextTickListEntry<T>> a = Sets.newHashSet();
    private final Function<T, MinecraftKey> b;

    public TickListChunk(Function<T, MinecraftKey> function, List<NextTickListEntry<T>> list) {
        this.b = function;
        this.a.addAll(list);
    }

    @Override
    public boolean a(BlockPosition blockposition, T t0) {
        return false;
    }

    @Override
    public void a(BlockPosition blockposition, T t0, int i, TickListPriority ticklistpriority) {
        this.a.add(new NextTickListEntry<>(blockposition, t0, (long) i, ticklistpriority));
    }

    @Override
    public boolean b(BlockPosition blockposition, T t0) {
        return false;
    }

    @Override
    public void a(Stream<NextTickListEntry<T>> stream) {
        Set set = this.a;

        this.a.getClass();
        stream.forEach(set::add);
    }

    public Stream<NextTickListEntry<T>> a() {
        return this.a.stream();
    }

    public NBTTagList a(long i) {
        return TickListServer.a(this.b, this.a, i);
    }
}

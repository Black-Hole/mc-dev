package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Stream;

public final class PlayerMap {

    private final Set<EntityPlayer> a = Sets.newHashSet();
    private final Set<EntityPlayer> b = Sets.newHashSet();

    public PlayerMap() {}

    public Stream<EntityPlayer> a(long i) {
        return this.a.stream();
    }

    public void a(long i, EntityPlayer entityplayer, boolean flag) {
        (flag ? this.b : this.a).add(entityplayer);
    }

    public void a(long i, EntityPlayer entityplayer) {
        this.a.remove(entityplayer);
        this.b.remove(entityplayer);
    }

    public void a(EntityPlayer entityplayer) {
        this.b.add(entityplayer);
        this.a.remove(entityplayer);
    }

    public void b(EntityPlayer entityplayer) {
        this.b.remove(entityplayer);
        this.a.add(entityplayer);
    }

    public boolean c(EntityPlayer entityplayer) {
        return !this.a.contains(entityplayer);
    }

    public void a(long i, long j, EntityPlayer entityplayer) {}
}

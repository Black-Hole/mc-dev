package net.minecraft.server;

import java.util.Comparator;

public class TicketType<T> {

    private final String h;
    private final Comparator<T> i;
    public static final TicketType<Unit> START = a("start", (unit, unit1) -> {
        return 0;
    });
    public static final TicketType<Unit> DRAGON = a("dragon", (unit, unit1) -> {
        return 0;
    });
    public static final TicketType<ChunkCoordIntPair> PLAYER = a("player", Comparator.comparingLong(ChunkCoordIntPair::pair));
    public static final TicketType<ChunkCoordIntPair> FORCED = a("forced", Comparator.comparingLong(ChunkCoordIntPair::pair));
    public static final TicketType<ChunkCoordIntPair> LIGHT = a("light", Comparator.comparingLong(ChunkCoordIntPair::pair));
    public static final TicketType<BlockPosition2D> PORTAL = a("portal", Comparator.comparingLong(BlockPosition2D::b));
    public static final TicketType<ChunkCoordIntPair> UNKNOWN = a("unknown", Comparator.comparingLong(ChunkCoordIntPair::pair));

    public static <T> TicketType<T> a(String s, Comparator<T> comparator) {
        return new TicketType<>(s, comparator);
    }

    protected TicketType(String s, Comparator<T> comparator) {
        this.h = s;
        this.i = comparator;
    }

    public String toString() {
        return this.h;
    }

    public Comparator<T> a() {
        return this.i;
    }
}

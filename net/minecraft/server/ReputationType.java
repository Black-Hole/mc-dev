package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum ReputationType {

    MAJOR_NEGATIVE("major_negative", -5, 100, 1, 10), MINOR_NEGATIVE("minor_negative", -1, 200, 2, 20), MINOR_POSITIVE("minor_positive", 1, 200, 2, 20), MAJOR_POSITIVE("major_positive", 5, 100, 1, 10), TRADING("trading", 1, 25, 2, 20), GOLEM("golem", 1, 100, 1, 1);

    public final String g;
    public final int h;
    public final int i;
    public final int j;
    public final int k;
    private static final Map<String, ReputationType> l = (Map) Stream.of(values()).collect(ImmutableMap.toImmutableMap((reputationtype) -> {
        return reputationtype.g;
    }, Function.identity()));

    private ReputationType(String s, int i, int j, int k, int l) {
        this.g = s;
        this.h = i;
        this.i = j;
        this.j = k;
        this.k = l;
    }

    @Nullable
    public static ReputationType a(String s) {
        return (ReputationType) ReputationType.l.get(s);
    }
}

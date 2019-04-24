package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reputation {

    private final Map<UUID, Reputation.a> a = Maps.newHashMap();

    public Reputation() {}

    private Stream<Reputation.b> b() {
        return this.a.entrySet().stream().flatMap((entry) -> {
            return ((Reputation.a) entry.getValue()).a((UUID) entry.getKey());
        });
    }

    private Collection<Reputation.b> a(Random random, int i) {
        List<Reputation.b> list = (List) this.b().collect(Collectors.toList());

        if (list.isEmpty()) {
            return Collections.emptyList();
        } else {
            int[] aint = new int[list.size()];
            int j = 0;

            for (int k = 0; k < list.size(); ++k) {
                Reputation.b reputation_b = (Reputation.b) list.get(k);

                j += Math.abs(reputation_b.a());
                aint[k] = j - 1;
            }

            Set<Reputation.b> set = Sets.newIdentityHashSet();

            for (int l = 0; l < i; ++l) {
                int i1 = random.nextInt(j);
                int j1 = Arrays.binarySearch(aint, i1);

                set.add(list.get(j1 < 0 ? -j1 - 1 : j1));
            }

            return set;
        }
    }

    private Reputation.a a(UUID uuid) {
        return (Reputation.a) this.a.computeIfAbsent(uuid, (uuid1) -> {
            return new Reputation.a();
        });
    }

    public void a(Reputation reputation, Random random, int i) {
        Collection<Reputation.b> collection = reputation.a(random, i);

        collection.forEach((reputation_b) -> {
            int j = reputation_b.c - reputation_b.b.k;

            if (j > 2) {
                this.a(reputation_b.a).a.mergeInt(reputation_b.b, j, Reputation::a);
            }

        });
    }

    public int a(UUID uuid, Predicate<ReputationType> predicate) {
        Reputation.a reputation_a = (Reputation.a) this.a.get(uuid);

        return reputation_a != null ? reputation_a.a(predicate) : 0;
    }

    public long a(ReputationType reputationtype, DoublePredicate doublepredicate) {
        return this.a.values().stream().filter((reputation_a) -> {
            return doublepredicate.test((double) (reputation_a.a.getOrDefault(reputationtype, 0) * reputationtype.h));
        }).count();
    }

    public void a(UUID uuid, ReputationType reputationtype, int i) {
        this.a(uuid).a.mergeInt(reputationtype, i, (integer, integer1) -> {
            return this.a(reputationtype, integer, integer1);
        });
    }

    public <T> Dynamic<T> a(DynamicOps<T> dynamicops) {
        return new Dynamic(dynamicops, dynamicops.createList(this.b().map((reputation_b) -> {
            return reputation_b.a(dynamicops);
        }).map(Dynamic::getValue)));
    }

    public void a(Dynamic<?> dynamic) {
        dynamic.asStream().map(Reputation.b::a).flatMap(SystemUtils::a).forEach((reputation_b) -> {
            this.a(reputation_b.a).a.put(reputation_b.b, reputation_b.c);
        });
    }

    private static int a(int i, int j) {
        return Math.max(i, j);
    }

    private int a(ReputationType reputationtype, int i, int j) {
        int k = i + j;

        return k > reputationtype.i ? Math.max(reputationtype.i, i) : k;
    }

    static class a {

        private final Object2IntMap<ReputationType> a;

        private a() {
            this.a = new Object2IntOpenHashMap();
        }

        public int a(Predicate<ReputationType> predicate) {
            return this.a.object2IntEntrySet().stream().filter((it_unimi_dsi_fastutil_objects_object2intmap_entry) -> {
                return predicate.test(it_unimi_dsi_fastutil_objects_object2intmap_entry.getKey());
            }).mapToInt((it_unimi_dsi_fastutil_objects_object2intmap_entry) -> {
                return it_unimi_dsi_fastutil_objects_object2intmap_entry.getIntValue() * ((ReputationType) it_unimi_dsi_fastutil_objects_object2intmap_entry.getKey()).h;
            }).sum();
        }

        public Stream<Reputation.b> a(UUID uuid) {
            return this.a.object2IntEntrySet().stream().map((it_unimi_dsi_fastutil_objects_object2intmap_entry) -> {
                return new Reputation.b(uuid, (ReputationType) it_unimi_dsi_fastutil_objects_object2intmap_entry.getKey(), it_unimi_dsi_fastutil_objects_object2intmap_entry.getIntValue());
            });
        }
    }

    static class b {

        public final UUID a;
        public final ReputationType b;
        public final int c;

        public b(UUID uuid, ReputationType reputationtype, int i) {
            this.a = uuid;
            this.b = reputationtype;
            this.c = i;
        }

        public int a() {
            return this.c * this.b.h;
        }

        public String toString() {
            return "GossipEntry{target=" + this.a + ", type=" + this.b + ", value=" + this.c + '}';
        }

        public <T> Dynamic<T> a(DynamicOps<T> dynamicops) {
            return SystemUtils.a("Target", this.a, new Dynamic(dynamicops, dynamicops.createMap(ImmutableMap.of(dynamicops.createString("Type"), dynamicops.createString(this.b.g), dynamicops.createString("Value"), dynamicops.createInt(this.c)))));
        }

        public static Optional<Reputation.b> a(Dynamic<?> dynamic) {
            return dynamic.get("Type").asString().map(ReputationType::a).flatMap((reputationtype) -> {
                return SystemUtils.a("Target", dynamic).flatMap((uuid) -> {
                    return dynamic.get("Value").asNumber().map((number) -> {
                        return new Reputation.b(uuid, reputationtype, number.intValue());
                    });
                });
            });
        }
    }
}

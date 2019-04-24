package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class BehaviorController<E extends EntityLiving> implements MinecraftSerializable {

    private final Map<MemoryModuleType<?>, Optional<?>> a = Maps.newHashMap();
    private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> b = Maps.newLinkedHashMap();
    private final Map<Integer, Map<Activity, Set<Behavior<? super E>>>> c = Maps.newTreeMap();
    private Schedule d;
    private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> e;
    private Set<Activity> f;
    private final Set<Activity> g;
    private Activity h;
    private long i;

    public <T> BehaviorController(Collection<MemoryModuleType<?>> collection, Collection<SensorType<? extends Sensor<? super E>>> collection1, Dynamic<T> dynamic) {
        this.d = Schedule.a;
        this.e = Maps.newHashMap();
        this.f = Sets.newHashSet();
        this.g = Sets.newHashSet();
        this.h = Activity.b;
        this.i = -9999L;
        collection.forEach((memorymoduletype) -> {
            Optional optional = (Optional) this.a.put(memorymoduletype, Optional.empty());
        });
        collection1.forEach((sensortype) -> {
            Sensor sensor = (Sensor) this.b.put(sensortype, sensortype.a());
        });
        this.b.values().forEach((sensor) -> {
            Iterator iterator = sensor.a().iterator();

            while (iterator.hasNext()) {
                MemoryModuleType<?> memorymoduletype = (MemoryModuleType) iterator.next();

                this.a.put(memorymoduletype, Optional.empty());
            }

        });
        Iterator iterator = dynamic.get("memories").asMap(Function.identity(), Function.identity()).entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<Dynamic<T>, Dynamic<T>> entry = (Entry) iterator.next();

            this.a((MemoryModuleType) IRegistry.MEMORY_MODULE_TYPE.get(new MinecraftKey(((Dynamic) entry.getKey()).asString(""))), (Dynamic) entry.getValue());
        }

    }

    public boolean a(MemoryModuleType<?> memorymoduletype) {
        return this.a(memorymoduletype, MemoryStatus.VALUE_PRESENT);
    }

    private <T, U> void a(MemoryModuleType<U> memorymoduletype, Dynamic<T> dynamic) {
        this.a(memorymoduletype, ((Function) memorymoduletype.b().orElseThrow(RuntimeException::new)).apply(dynamic));
    }

    public void b(MemoryModuleType<?> memorymoduletype) {
        this.a(memorymoduletype, Optional.empty());
    }

    public <U> void a(MemoryModuleType<U> memorymoduletype, @Nullable U u0) {
        this.a(memorymoduletype, Optional.ofNullable(u0));
    }

    public <U> void a(MemoryModuleType<U> memorymoduletype, Optional<U> optional) {
        if (this.a.containsKey(memorymoduletype)) {
            if (optional.isPresent() && this.a(optional.get())) {
                this.b(memorymoduletype);
            } else {
                this.a.put(memorymoduletype, optional);
            }
        }

    }

    public <U> Optional<U> c(MemoryModuleType<U> memorymoduletype) {
        return (Optional) this.a.get(memorymoduletype);
    }

    public boolean a(MemoryModuleType<?> memorymoduletype, MemoryStatus memorystatus) {
        Optional<?> optional = (Optional) this.a.get(memorymoduletype);

        return optional == null ? false : memorystatus == MemoryStatus.REGISTERED || memorystatus == MemoryStatus.VALUE_PRESENT && optional.isPresent() || memorystatus == MemoryStatus.VALUE_ABSENT && !optional.isPresent();
    }

    public Schedule b() {
        return this.d;
    }

    public void a(Schedule schedule) {
        this.d = schedule;
    }

    public void a(Set<Activity> set) {
        this.f = set;
    }

    @Deprecated
    public Stream<Behavior<? super E>> d() {
        return this.c.values().stream().flatMap((map) -> {
            return map.values().stream();
        }).flatMap(Collection::stream).filter((behavior) -> {
            return behavior.b() == Behavior.Status.RUNNING;
        });
    }

    public void a(Activity activity) {
        this.g.clear();
        this.g.addAll(this.f);
        boolean flag = this.e.keySet().contains(activity) && this.d(activity);

        this.g.add(flag ? activity : this.h);
    }

    public void a(long i, long j) {
        if (j - this.i > 20L) {
            this.i = j;
            Activity activity = this.b().a((int) (i % 24000L));

            if (!this.g.contains(activity)) {
                this.a(activity);
            }
        }

    }

    public void b(Activity activity) {
        this.h = activity;
    }

    public void a(Activity activity, ImmutableList<Pair<Integer, ? extends Behavior<? super E>>> immutablelist) {
        this.a(activity, immutablelist, (Set) ImmutableSet.of());
    }

    public void a(Activity activity, ImmutableList<Pair<Integer, ? extends Behavior<? super E>>> immutablelist, Set<Pair<MemoryModuleType<?>, MemoryStatus>> set) {
        this.e.put(activity, set);
        immutablelist.forEach((pair) -> {
            ((Set) ((Map) this.c.computeIfAbsent(pair.getFirst(), (integer) -> {
                return Maps.newHashMap();
            })).computeIfAbsent(activity, (activity1) -> {
                return Sets.newLinkedHashSet();
            })).add(pair.getSecond());
        });
    }

    public boolean c(Activity activity) {
        return this.g.contains(activity);
    }

    public BehaviorController<E> f() {
        BehaviorController<E> behaviorcontroller = new BehaviorController<>(this.a.keySet(), this.b.keySet(), new Dynamic(DynamicOpsNBT.a, new NBTTagCompound()));

        this.a.forEach((memorymoduletype, optional) -> {
            optional.ifPresent((object) -> {
                Optional optional1 = (Optional) behaviorcontroller.a.put(memorymoduletype, Optional.of(object));
            });
        });
        return behaviorcontroller;
    }

    public void a(WorldServer worldserver, E e0) {
        this.c(worldserver, e0);
        this.d(worldserver, e0);
        this.e(worldserver, e0);
    }

    public void b(WorldServer worldserver, E e0) {
        long i = e0.world.getTime();

        this.d().forEach((behavior) -> {
            behavior.e(worldserver, e0, i);
        });
    }

    @Override
    public <T> T a(DynamicOps<T> dynamicops) {
        T t0 = dynamicops.createMap((Map) this.a.entrySet().stream().filter((entry) -> {
            return ((MemoryModuleType) entry.getKey()).b().isPresent() && ((Optional) entry.getValue()).isPresent();
        }).map((entry) -> {
            return Pair.of(dynamicops.createString(((MemoryModuleType) entry.getKey()).a().toString()), ((MinecraftSerializable) ((Optional) entry.getValue()).get()).a(dynamicops));
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));

        return dynamicops.createMap(ImmutableMap.of(dynamicops.createString("memories"), t0));
    }

    private void c(WorldServer worldserver, E e0) {
        this.b.values().forEach((sensor) -> {
            sensor.b(worldserver, e0);
        });
    }

    private void d(WorldServer worldserver, E e0) {
        long i = worldserver.getTime();

        this.c.values().stream().flatMap((map) -> {
            return map.entrySet().stream();
        }).filter((entry) -> {
            return this.g.contains(entry.getKey());
        }).map(Entry::getValue).flatMap(Collection::stream).filter((behavior) -> {
            return behavior.b() == Behavior.Status.STOPPED;
        }).forEach((behavior) -> {
            behavior.b(worldserver, e0, i);
        });
    }

    private void e(WorldServer worldserver, E e0) {
        long i = worldserver.getTime();

        this.d().forEach((behavior) -> {
            behavior.c(worldserver, e0, i);
        });
    }

    private boolean d(Activity activity) {
        return ((Set) this.e.get(activity)).stream().allMatch((pair) -> {
            MemoryModuleType<?> memorymoduletype = (MemoryModuleType) pair.getFirst();
            MemoryStatus memorystatus = (MemoryStatus) pair.getSecond();

            return this.a(memorymoduletype, memorystatus);
        });
    }

    private boolean a(Object object) {
        return object instanceof Collection && ((Collection) object).isEmpty();
    }
}

package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Schedule {

    public static final Schedule a = a("empty").a(0, Activity.b).a();
    public static final Schedule b = a("simple").a(5000, Activity.c).a(11000, Activity.e).a();
    public static final Schedule c = a("villager_baby").a(10, Activity.b).a(3000, Activity.d).a(6000, Activity.b).a(10000, Activity.d).a(12000, Activity.e).a();
    public static final Schedule d = a("villager_default").a(10, Activity.b).a(2000, Activity.c).a(9000, Activity.f).a(11000, Activity.b).a(12000, Activity.e).a();
    private final Map<Activity, ScheduleActivity> e = Maps.newHashMap();

    public Schedule() {}

    protected static ScheduleBuilder a(String s) {
        Schedule schedule = (Schedule) IRegistry.SCHEDULE.a(new MinecraftKey(s), (Object) (new Schedule()));

        return new ScheduleBuilder(schedule);
    }

    protected void a(Activity activity) {
        if (!this.e.containsKey(activity)) {
            this.e.put(activity, new ScheduleActivity());
        }

    }

    protected ScheduleActivity b(Activity activity) {
        return (ScheduleActivity) this.e.get(activity);
    }

    protected List<ScheduleActivity> c(Activity activity) {
        return (List) this.e.entrySet().stream().filter((entry) -> {
            return entry.getKey() != activity;
        }).map(Entry::getValue).collect(Collectors.toList());
    }

    public Activity a(int i) {
        return (Activity) this.e.entrySet().stream().max(Comparator.comparingDouble((entry) -> {
            return (double) ((ScheduleActivity) entry.getValue()).a(i);
        })).map(Entry::getKey).orElse(Activity.b);
    }
}

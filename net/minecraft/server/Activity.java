package net.minecraft.server;

public class Activity {

    public static final Activity a = a("core");
    public static final Activity b = a("idle");
    public static final Activity c = a("work");
    public static final Activity d = a("play");
    public static final Activity e = a("rest");
    public static final Activity f = a("meet");
    public static final Activity g = a("panic");
    public static final Activity h = a("raid");
    public static final Activity i = a("pre_raid");
    public static final Activity j = a("hide");
    private final String k;

    private Activity(String s) {
        this.k = s;
    }

    public String a() {
        return this.k;
    }

    private static Activity a(String s) {
        return (Activity) IRegistry.ACTIVITY.a(new MinecraftKey(s), (Object) (new Activity(s)));
    }

    public String toString() {
        return this.a();
    }
}

package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MethodProfiler implements GameProfilerFillerActive {

    private static final long a = Duration.ofMillis(100L).toNanos();
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<String> c = Lists.newArrayList();
    private final List<Long> d = Lists.newArrayList();
    private final Map<String, Long> e = Maps.newHashMap();
    private final IntSupplier f;
    private final long g;
    private final int h;
    private String i = "";
    private boolean j;

    public MethodProfiler(long i, IntSupplier intsupplier) {
        this.g = i;
        this.h = intsupplier.getAsInt();
        this.f = intsupplier;
    }

    @Override
    public void a() {
        if (this.j) {
            MethodProfiler.LOGGER.error("Profiler tick already started - missing endTick()?");
        } else {
            this.j = true;
            this.i = "";
            this.c.clear();
            this.enter("root");
        }
    }

    @Override
    public void b() {
        if (!this.j) {
            MethodProfiler.LOGGER.error("Profiler tick already ended - missing startTick()?");
        } else {
            this.exit();
            this.j = false;
            if (!this.i.isEmpty()) {
                MethodProfiler.LOGGER.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", this.i);
            }

        }
    }

    @Override
    public void enter(String s) {
        if (!this.j) {
            MethodProfiler.LOGGER.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", s);
        } else {
            if (!this.i.isEmpty()) {
                this.i = this.i + ".";
            }

            this.i = this.i + s;
            this.c.add(this.i);
            this.d.add(SystemUtils.getMonotonicNanos());
        }
    }

    @Override
    public void a(Supplier<String> supplier) {
        this.enter((String) supplier.get());
    }

    @Override
    public void exit() {
        if (!this.j) {
            MethodProfiler.LOGGER.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
        } else if (this.d.isEmpty()) {
            MethodProfiler.LOGGER.error("Tried to pop one too many times! Mismatched push() and pop()?");
        } else {
            long i = SystemUtils.getMonotonicNanos();
            long j = (Long) this.d.remove(this.d.size() - 1);

            this.c.remove(this.c.size() - 1);
            long k = i - j;

            if (this.e.containsKey(this.i)) {
                this.e.put(this.i, (Long) this.e.get(this.i) + k);
            } else {
                this.e.put(this.i, k);
            }

            if (k > MethodProfiler.a) {
                MethodProfiler.LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", this.i, (double) k / 1000000.0D);
            }

            this.i = this.c.isEmpty() ? "" : (String) this.c.get(this.c.size() - 1);
        }
    }

    @Override
    public void exitEnter(String s) {
        this.exit();
        this.enter(s);
    }

    @Override
    public MethodProfilerResults d() {
        return new MethodProfilerResultsFilled(this.e, this.g, this.h, SystemUtils.getMonotonicNanos(), this.f.getAsInt());
    }
}

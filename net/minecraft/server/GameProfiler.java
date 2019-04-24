package net.minecraft.server;

import java.time.Duration;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class GameProfiler implements GameProfilerFiller {

    private static final long a = Duration.ofMillis(300L).toNanos();
    private final IntSupplier b;
    private final GameProfiler.b c = new GameProfiler.b();
    private final GameProfiler.b d = new GameProfiler.b();

    public GameProfiler(IntSupplier intsupplier) {
        this.b = intsupplier;
    }

    public GameProfiler.a d() {
        return this.c;
    }

    @Override
    public void a() {
        this.c.a.a();
        this.d.a.a();
    }

    @Override
    public void b() {
        this.c.a.b();
        this.d.a.b();
    }

    @Override
    public void enter(String s) {
        this.c.a.enter(s);
        this.d.a.enter(s);
    }

    @Override
    public void a(Supplier<String> supplier) {
        this.c.a.a(supplier);
        this.d.a.a(supplier);
    }

    @Override
    public void exit() {
        this.c.a.exit();
        this.d.a.exit();
    }

    @Override
    public void exitEnter(String s) {
        this.c.a.exitEnter(s);
        this.d.a.exitEnter(s);
    }

    class b implements GameProfiler.a {

        protected GameProfilerFillerActive a;

        private b() {
            this.a = GameProfilerDisabled.a;
        }

        @Override
        public boolean a() {
            return this.a != GameProfilerDisabled.a;
        }

        @Override
        public MethodProfilerResults b() {
            MethodProfilerResults methodprofilerresults = this.a.d();

            this.a = GameProfilerDisabled.a;
            return methodprofilerresults;
        }

        @Override
        public void d() {
            if (this.a == GameProfilerDisabled.a) {
                this.a = new MethodProfiler(SystemUtils.getMonotonicNanos(), GameProfiler.this.b);
            }

        }
    }

    public interface a {

        boolean a();

        MethodProfilerResults b();

        void d();
    }
}

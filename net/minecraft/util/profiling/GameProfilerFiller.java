package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;

public interface GameProfilerFiller {

    String ROOT = "root";

    void a();

    void b();

    void enter(String s);

    void a(Supplier<String> supplier);

    void exit();

    void exitEnter(String s);

    void b(Supplier<String> supplier);

    void a(MetricCategory metriccategory);

    void c(String s);

    void c(Supplier<String> supplier);

    static GameProfilerFiller a(final GameProfilerFiller gameprofilerfiller, final GameProfilerFiller gameprofilerfiller1) {
        return gameprofilerfiller == GameProfilerDisabled.INSTANCE ? gameprofilerfiller1 : (gameprofilerfiller1 == GameProfilerDisabled.INSTANCE ? gameprofilerfiller : new GameProfilerFiller() {
            @Override
            public void a() {
                gameprofilerfiller.a();
                gameprofilerfiller1.a();
            }

            @Override
            public void b() {
                gameprofilerfiller.b();
                gameprofilerfiller1.b();
            }

            @Override
            public void enter(String s) {
                gameprofilerfiller.enter(s);
                gameprofilerfiller1.enter(s);
            }

            @Override
            public void a(Supplier<String> supplier) {
                gameprofilerfiller.a(supplier);
                gameprofilerfiller1.a(supplier);
            }

            @Override
            public void a(MetricCategory metriccategory) {
                gameprofilerfiller.a(metriccategory);
                gameprofilerfiller1.a(metriccategory);
            }

            @Override
            public void exit() {
                gameprofilerfiller.exit();
                gameprofilerfiller1.exit();
            }

            @Override
            public void exitEnter(String s) {
                gameprofilerfiller.exitEnter(s);
                gameprofilerfiller1.exitEnter(s);
            }

            @Override
            public void b(Supplier<String> supplier) {
                gameprofilerfiller.b(supplier);
                gameprofilerfiller1.b(supplier);
            }

            @Override
            public void c(String s) {
                gameprofilerfiller.c(s);
                gameprofilerfiller1.c(s);
            }

            @Override
            public void c(Supplier<String> supplier) {
                gameprofilerfiller.c(supplier);
                gameprofilerfiller1.c(supplier);
            }
        });
    }
}

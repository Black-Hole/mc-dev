package net.minecraft.server;

import java.io.File;

public interface MethodProfilerResults {

    boolean a(File file);

    long a();

    int b();

    long c();

    int d();

    default long f() {
        return this.c() - this.a();
    }

    default int g() {
        return this.d() - this.b();
    }

    String e();
}

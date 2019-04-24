package net.minecraft.server;

public final class MethodProfilerResultsField implements Comparable<MethodProfilerResultsField> {

    public final double a;
    public final double b;
    public final String c;

    public MethodProfilerResultsField(String s, double d0, double d1) {
        this.c = s;
        this.a = d0;
        this.b = d1;
    }

    public int compareTo(MethodProfilerResultsField methodprofilerresultsfield) {
        return methodprofilerresultsfield.a < this.a ? -1 : (methodprofilerresultsfield.a > this.a ? 1 : methodprofilerresultsfield.c.compareTo(this.c));
    }
}

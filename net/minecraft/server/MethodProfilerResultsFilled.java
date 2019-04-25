package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MethodProfilerResultsFilled implements MethodProfilerResults {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, Long> b;
    private final long c;
    private final int d;
    private final long e;
    private final int f;

    public MethodProfilerResultsFilled(Map<String, Long> map, long i, int j, long k, int l) {
        this.b = map;
        this.c = i;
        this.d = j;
        this.e = k;
        this.f = l;
    }

    public List<MethodProfilerResultsField> a(String s) {
        long i = this.b.containsKey("root") ? (Long) this.b.get("root") : 0L;
        long j = this.b.containsKey(s) ? (Long) this.b.get(s) : -1L;
        List<MethodProfilerResultsField> list = Lists.newArrayList();

        if (!s.isEmpty()) {
            s = s + ".";
        }

        long k = 0L;
        Iterator iterator = this.b.keySet().iterator();

        while (iterator.hasNext()) {
            String s1 = (String) iterator.next();

            if (s1.length() > s.length() && s1.startsWith(s) && s1.indexOf(".", s.length() + 1) < 0) {
                k += (Long) this.b.get(s1);
            }
        }

        float f = (float) k;

        if (k < j) {
            k = j;
        }

        if (i < k) {
            i = k;
        }

        Iterator iterator1 = this.b.keySet().iterator();

        String s2;

        while (iterator1.hasNext()) {
            s2 = (String) iterator1.next();
            if (s2.length() > s.length() && s2.startsWith(s) && s2.indexOf(".", s.length() + 1) < 0) {
                long l = (Long) this.b.get(s2);
                double d0 = (double) l * 100.0D / (double) k;
                double d1 = (double) l * 100.0D / (double) i;
                String s3 = s2.substring(s.length());

                list.add(new MethodProfilerResultsField(s3, d0, d1));
            }
        }

        iterator1 = this.b.keySet().iterator();

        while (iterator1.hasNext()) {
            s2 = (String) iterator1.next();
            this.b.put(s2, (Long) this.b.get(s2) * 999L / 1000L);
        }

        if ((float) k > f) {
            list.add(new MethodProfilerResultsField("unspecified", (double) ((float) k - f) * 100.0D / (double) k, (double) ((float) k - f) * 100.0D / (double) i));
        }

        Collections.sort(list);
        list.add(0, new MethodProfilerResultsField(s, 100.0D, (double) k * 100.0D / (double) i));
        return list;
    }

    @Override
    public long a() {
        return this.c;
    }

    @Override
    public int b() {
        return this.d;
    }

    @Override
    public long c() {
        return this.e;
    }

    @Override
    public int d() {
        return this.f;
    }

    @Override
    public boolean a(File file) {
        file.getParentFile().mkdirs();
        OutputStreamWriter outputstreamwriter = null;

        boolean flag;

        try {
            outputstreamwriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            outputstreamwriter.write(this.a(this.f(), this.g()));
            boolean flag1 = true;

            return flag1;
        } catch (Throwable throwable) {
            MethodProfilerResultsFilled.LOGGER.error("Could not save profiler results to {}", file, throwable);
            flag = false;
        } finally {
            IOUtils.closeQuietly(outputstreamwriter);
        }

        return flag;
    }

    protected String a(long i, int j) {
        StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append("---- Minecraft Profiler Results ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(h());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time span: ").append(i / 1000000L).append(" ms\n");
        stringbuilder.append("Tick span: ").append(j).append(" ticks\n");
        stringbuilder.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", (float) j / ((float) i / 1.0E9F))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.a(0, "root", stringbuilder);
        stringbuilder.append("--- END PROFILE DUMP ---\n\n");
        return stringbuilder.toString();
    }

    @Override
    public String e() {
        StringBuilder stringbuilder = new StringBuilder();

        this.a(0, "root", stringbuilder);
        return stringbuilder.toString();
    }

    private void a(int i, String s, StringBuilder stringbuilder) {
        List<MethodProfilerResultsField> list = this.a(s);

        if (list.size() >= 3) {
            for (int j = 1; j < list.size(); ++j) {
                MethodProfilerResultsField methodprofilerresultsfield = (MethodProfilerResultsField) list.get(j);

                stringbuilder.append(String.format("[%02d] ", i));

                for (int k = 0; k < i; ++k) {
                    stringbuilder.append("|   ");
                }

                stringbuilder.append(methodprofilerresultsfield.c).append(" - ").append(String.format(Locale.ROOT, "%.2f", methodprofilerresultsfield.a)).append("%/").append(String.format(Locale.ROOT, "%.2f", methodprofilerresultsfield.b)).append("%\n");
                if (!"unspecified".equals(methodprofilerresultsfield.c)) {
                    try {
                        this.a(i + 1, s + "." + methodprofilerresultsfield.c, stringbuilder);
                    } catch (Exception exception) {
                        stringbuilder.append("[[ EXCEPTION ").append(exception).append(" ]]");
                    }
                }
            }

        }
    }

    private static String h() {
        String[] astring = new String[] { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};

        try {
            return astring[(int) (SystemUtils.getMonotonicNanos() % (long) astring.length)];
        } catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }
}

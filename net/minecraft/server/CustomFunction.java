package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class CustomFunction {

    private final CustomFunctionData a;
    private final CustomFunction.c[] b;

    public CustomFunction(CustomFunctionData customfunctiondata, CustomFunction.c[] acustomfunction_c) {
        this.a = customfunctiondata;
        this.b = acustomfunction_c;
    }

    public int a(ICommandListener icommandlistener) {
        ArrayDeque arraydeque = new ArrayDeque(Arrays.asList(this.b));
        int i = this.a.c();
        int j = 0;

        do {
            if (arraydeque.isEmpty()) {
                return j;
            }

            CustomFunction.c customfunction_c = (CustomFunction.c) arraydeque.removeFirst();

            customfunction_c.a(this.a, icommandlistener, arraydeque);
            ++j;
        } while (j < i);

        return j;
    }

    public static CustomFunction a(CustomFunctionData customfunctiondata, List<String> list) {
        ArrayList arraylist = Lists.newArrayListWithCapacity(list.size());
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            s = s.trim();
            if (s.startsWith("/")) {
                s = s.substring(1);
            }

            if (!s.startsWith("/") && !s.startsWith("#") && !s.isEmpty()) {
                String[] astring = s.split(" ");

                if ("function".equals(astring[0]) && astring.length == 2) {
                    arraylist.add(new CustomFunction.d(new MinecraftKey(astring[1]), null));
                } else {
                    arraylist.add(new CustomFunction.b(s, null));
                }
            }
        }

        return new CustomFunction(customfunctiondata, (CustomFunction.c[]) arraylist.toArray(new CustomFunction.c[arraylist.size()]));
    }

    public CustomFunction.c[] a() {
        return this.b;
    }

    public static class a {

        public static final CustomFunction.a a = new CustomFunction.a((MinecraftKey) null);
        private final MinecraftKey b;
        private boolean c;
        private CustomFunction d;

        public a(@Nullable MinecraftKey minecraftkey) {
            this.b = minecraftkey;
        }

        @Nullable
        public CustomFunction a(CustomFunctionData customfunctiondata) {
            if (!this.c) {
                if (this.b != null) {
                    this.d = customfunctiondata.a(this.b);
                }

                this.c = true;
            }

            return this.d;
        }

        public String toString() {
            return String.valueOf(this.b);
        }
    }

    static class d implements CustomFunction.c {

        private final CustomFunction.a a;

        private d(MinecraftKey minecraftkey) {
            this.a = new CustomFunction.a(minecraftkey);
        }

        public void a(CustomFunctionData customfunctiondata, ICommandListener icommandlistener, Deque<CustomFunction.c> deque) {
            CustomFunction customfunction = this.a.a(customfunctiondata);

            if (customfunction != null) {
                CustomFunction.c[] acustomfunction_c = customfunction.a();

                for (int i = acustomfunction_c.length - 1; i >= 0; --i) {
                    deque.addFirst(acustomfunction_c[i]);
                }
            }

        }

        d(MinecraftKey minecraftkey, Object object) {
            this(minecraftkey);
        }
    }

    static class b implements CustomFunction.c {

        private final String a;

        private b(String s) {
            this.a = s;
        }

        public void a(CustomFunctionData customfunctiondata, ICommandListener icommandlistener, Deque<CustomFunction.c> deque) {
            customfunctiondata.a().b(icommandlistener, this.a);
        }

        b(String s, Object object) {
            this(s);
        }
    }

    interface c {

        void a(CustomFunctionData customfunctiondata, ICommandListener icommandlistener, Deque<CustomFunction.c> deque);
    }
}

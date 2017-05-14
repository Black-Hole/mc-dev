package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {

    private final Advancement a;
    private final AdvancementDisplay b;
    private final AdvancementRewards c;
    private final MinecraftKey d;
    private final Map<String, Criterion> e;
    private final String[][] f;
    private final Set<Advancement> g = Sets.newLinkedHashSet();
    private final IChatBaseComponent h;

    public Advancement(MinecraftKey minecraftkey, @Nullable Advancement advancement, @Nullable AdvancementDisplay advancementdisplay, AdvancementRewards advancementrewards, Map<String, Criterion> map, String[][] astring) {
        this.d = minecraftkey;
        this.b = advancementdisplay;
        this.e = ImmutableMap.copyOf(map);
        this.a = advancement;
        this.c = advancementrewards;
        this.f = astring;
        if (advancement != null) {
            advancement.a(this);
        }

        if (advancementdisplay == null) {
            this.h = new ChatComponentText(minecraftkey.toString());
        } else {
            this.h = new ChatComponentText("[");
            this.h.getChatModifier().setColor(advancementdisplay.e().c());
            IChatBaseComponent ichatbasecomponent = advancementdisplay.a().f();
            IChatBaseComponent ichatbasecomponent1 = ichatbasecomponent.f();

            ichatbasecomponent1.a("\n");
            ichatbasecomponent1.addSibling(advancementdisplay.b());
            ichatbasecomponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_TEXT, ichatbasecomponent1));
            this.h.addSibling(ichatbasecomponent);
            this.h.a("]");
        }

    }

    public Advancement.SerializedAdvancement a() {
        return new Advancement.SerializedAdvancement(this.a == null ? null : this.a.getName(), this.b, this.c, this.e, this.f);
    }

    @Nullable
    public Advancement b() {
        return this.a;
    }

    @Nullable
    public AdvancementDisplay c() {
        return this.b;
    }

    public AdvancementRewards d() {
        return this.c;
    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.getName() + ", parent=" + (this.a == null ? "null" : this.a.getName()) + ", display=" + this.b + ", rewards=" + this.c + ", criteria=" + this.e + ", requirements=" + Arrays.deepToString(this.f) + '}';
    }

    public Iterable<Advancement> e() {
        return this.g;
    }

    public Map<String, Criterion> getCriteria() {
        return this.e;
    }

    public void a(Advancement advancement) {
        this.g.add(advancement);
    }

    public MinecraftKey getName() {
        return this.d;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Advancement)) {
            return false;
        } else {
            Advancement advancement = (Advancement) object;

            return this.d.equals(advancement.d);
        }
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    public String[][] i() {
        return this.f;
    }

    public IChatBaseComponent j() {
        return this.h;
    }

    public static class SerializedAdvancement {

        private final MinecraftKey a;
        private Advancement b;
        private final AdvancementDisplay c;
        private final AdvancementRewards d;
        private final Map<String, Criterion> e;
        private final String[][] f;

        SerializedAdvancement(@Nullable MinecraftKey minecraftkey, @Nullable AdvancementDisplay advancementdisplay, AdvancementRewards advancementrewards, Map<String, Criterion> map, String[][] astring) {
            this.a = minecraftkey;
            this.c = advancementdisplay;
            this.d = advancementrewards;
            this.e = map;
            this.f = astring;
        }

        public boolean a(Function<MinecraftKey, Advancement> function) {
            if (this.a == null) {
                return true;
            } else {
                this.b = (Advancement) function.apply(this.a);
                return this.b != null;
            }
        }

        public Advancement a(MinecraftKey minecraftkey) {
            return new Advancement(minecraftkey, this.b, this.c, this.d, this.e, this.f);
        }

        public void a(PacketDataSerializer packetdataserializer) {
            if (this.a == null) {
                packetdataserializer.writeBoolean(false);
            } else {
                packetdataserializer.writeBoolean(true);
                packetdataserializer.a(this.a);
            }

            if (this.c == null) {
                packetdataserializer.writeBoolean(false);
            } else {
                packetdataserializer.writeBoolean(true);
                this.c.a(packetdataserializer);
            }

            Criterion.a(this.e, packetdataserializer);
            packetdataserializer.d(this.f.length);
            String[][] astring = this.f;
            int i = astring.length;

            for (int j = 0; j < i; ++j) {
                String[] astring1 = astring[j];

                packetdataserializer.d(astring1.length);
                String[] astring2 = astring1;
                int k = astring1.length;

                for (int l = 0; l < k; ++l) {
                    String s = astring2[l];

                    packetdataserializer.a(s);
                }
            }

        }

        public String toString() {
            return "Task Advancement{parentId=" + this.a + ", display=" + this.c + ", rewards=" + this.d + ", criteria=" + this.e + ", requirements=" + Arrays.deepToString(this.f) + '}';
        }

        public static Advancement.SerializedAdvancement a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
            MinecraftKey minecraftkey = jsonobject.has("parent") ? new MinecraftKey(ChatDeserializer.h(jsonobject, "parent")) : null;
            AdvancementDisplay advancementdisplay = jsonobject.has("display") ? AdvancementDisplay.a(ChatDeserializer.t(jsonobject, "display"), jsondeserializationcontext) : null;
            AdvancementRewards advancementrewards = (AdvancementRewards) ChatDeserializer.a(jsonobject, "rewards", AdvancementRewards.a, jsondeserializationcontext, AdvancementRewards.class);
            Map map = Criterion.b(ChatDeserializer.t(jsonobject, "criteria"), jsondeserializationcontext);

            if (map.isEmpty()) {
                throw new JsonSyntaxException("Advancement criteria cannot be empty");
            } else {
                JsonArray jsonarray = ChatDeserializer.a(jsonobject, "requirements", new JsonArray());
                String[][] astring = new String[jsonarray.size()][];

                int i;

                for (int j = 0; j < jsonarray.size(); ++j) {
                    JsonArray jsonarray1 = ChatDeserializer.n(jsonarray.get(j), "requirements[" + j + "]");

                    astring[j] = new String[jsonarray1.size()];

                    for (i = 0; i < jsonarray1.size(); ++i) {
                        astring[j][i] = ChatDeserializer.a(jsonarray1.get(i), "requirements[" + j + "][" + i + "]");
                    }
                }

                if (astring.length == 0) {
                    astring = new String[][] { (String[]) map.keySet().toArray(new String[0])};
                }

                String[][] astring1 = astring;
                int k = astring.length;

                int l;

                for (i = 0; i < k; ++i) {
                    String[] astring2 = astring1[i];

                    if (astring2.length == 0 && map.isEmpty()) {
                        throw new JsonSyntaxException("Requirement entry cannot be empty");
                    }

                    String[] astring3 = astring2;

                    l = astring2.length;

                    for (int i1 = 0; i1 < l; ++i1) {
                        String s = astring3[i1];

                        if (!map.containsKey(s)) {
                            throw new JsonSyntaxException("Unknown required criterion \'" + s + "\'");
                        }
                    }
                }

                Iterator iterator = map.keySet().iterator();

                while (iterator.hasNext()) {
                    String s1 = (String) iterator.next();
                    boolean flag = false;
                    String[][] astring4 = astring;
                    int j1 = astring.length;

                    l = 0;

                    while (true) {
                        if (l < j1) {
                            String[] astring5 = astring4[l];

                            if (!ArrayUtils.contains(astring5, s1)) {
                                ++l;
                                continue;
                            }

                            flag = true;
                        }

                        if (!flag) {
                            throw new JsonSyntaxException("Criterion \'" + s1 + "\' isn\'t a requirement for completion. This isn\'t supported behaviour, all criteria must be required.");
                        }
                        break;
                    }
                }

                return new Advancement.SerializedAdvancement(minecraftkey, advancementdisplay, advancementrewards, map, astring);
            }
        }

        public static Advancement.SerializedAdvancement b(PacketDataSerializer packetdataserializer) {
            MinecraftKey minecraftkey = packetdataserializer.readBoolean() ? packetdataserializer.l() : null;
            AdvancementDisplay advancementdisplay = packetdataserializer.readBoolean() ? AdvancementDisplay.b(packetdataserializer) : null;
            Map map = Criterion.c(packetdataserializer);
            String[][] astring = new String[packetdataserializer.g()][];

            for (int i = 0; i < astring.length; ++i) {
                astring[i] = new String[packetdataserializer.g()];

                for (int j = 0; j < astring[i].length; ++j) {
                    astring[i][j] = packetdataserializer.e(32767);
                }
            }

            return new Advancement.SerializedAdvancement(minecraftkey, advancementdisplay, AdvancementRewards.a, map, astring);
        }
    }
}

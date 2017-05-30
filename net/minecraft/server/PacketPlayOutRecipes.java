package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PacketPlayOutRecipes implements Packet<PacketListenerPlayOut> {

    private PacketPlayOutRecipes.a a;
    private List<IRecipe> b;
    private List<IRecipe> c;
    private boolean d;
    private boolean e;

    public PacketPlayOutRecipes() {}

    public PacketPlayOutRecipes(PacketPlayOutRecipes.a packetplayoutrecipes_a, List<IRecipe> list, List<IRecipe> list1, boolean flag, boolean flag1) {
        this.a = packetplayoutrecipes_a;
        this.b = list;
        this.c = list1;
        this.d = flag;
        this.e = flag1;
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (PacketPlayOutRecipes.a) packetdataserializer.a(PacketPlayOutRecipes.a.class);
        this.d = packetdataserializer.readBoolean();
        this.e = packetdataserializer.readBoolean();
        int i = packetdataserializer.g();

        this.b = Lists.newArrayList();

        int j;

        for (j = 0; j < i; ++j) {
            this.b.add(CraftingManager.a(packetdataserializer.g()));
        }

        if (this.a == PacketPlayOutRecipes.a.a) {
            i = packetdataserializer.g();
            this.c = Lists.newArrayList();

            for (j = 0; j < i; ++j) {
                this.c.add(CraftingManager.a(packetdataserializer.g()));
            }
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum) this.a);
        packetdataserializer.writeBoolean(this.d);
        packetdataserializer.writeBoolean(this.e);
        packetdataserializer.d(this.b.size());
        Iterator iterator = this.b.iterator();

        IRecipe irecipe;

        while (iterator.hasNext()) {
            irecipe = (IRecipe) iterator.next();
            packetdataserializer.d(CraftingManager.a(irecipe));
        }

        if (this.a == PacketPlayOutRecipes.a.a) {
            packetdataserializer.d(this.c.size());
            iterator = this.c.iterator();

            while (iterator.hasNext()) {
                irecipe = (IRecipe) iterator.next();
                packetdataserializer.d(CraftingManager.a(irecipe));
            }
        }

    }

    public static enum a {

        a, b, c;

        private a() {}
    }
}

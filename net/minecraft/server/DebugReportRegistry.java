package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Iterator;

public class DebugReportRegistry implements DebugReportProvider {

    private static final Gson b = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator c;

    public DebugReportRegistry(DebugReportGenerator debugreportgenerator) {
        this.c = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) throws IOException {
        JsonObject jsonobject = new JsonObject();

        IRegistry.f.keySet().forEach((minecraftkey) -> {
            jsonobject.add(minecraftkey.toString(), a((IRegistryWritable) IRegistry.f.get(minecraftkey)));
        });
        java.nio.file.Path java_nio_file_path = this.c.b().resolve("reports/registries.json");

        DebugReportProvider.a(DebugReportRegistry.b, hashcache, jsonobject, java_nio_file_path);
    }

    private static <T> JsonElement a(IRegistryWritable<T> iregistrywritable) {
        JsonObject jsonobject = new JsonObject();

        if (iregistrywritable instanceof RegistryBlocks) {
            MinecraftKey minecraftkey = ((RegistryBlocks) iregistrywritable).a();

            jsonobject.addProperty("default", minecraftkey.toString());
        }

        int i = IRegistry.f.a((Object) iregistrywritable);

        jsonobject.addProperty("protocol_id", i);
        JsonObject jsonobject1 = new JsonObject();
        Iterator iterator = iregistrywritable.keySet().iterator();

        while (iterator.hasNext()) {
            MinecraftKey minecraftkey1 = (MinecraftKey) iterator.next();
            T t0 = iregistrywritable.get(minecraftkey1);
            int j = iregistrywritable.a(t0);
            JsonObject jsonobject2 = new JsonObject();

            jsonobject2.addProperty("protocol_id", j);
            jsonobject1.add(minecraftkey1.toString(), jsonobject2);
        }

        jsonobject.add("entries", jsonobject1);
        return jsonobject;
    }

    @Override
    public String a() {
        return "Registry Dump";
    }
}

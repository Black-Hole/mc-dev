package net.minecraft.server;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Iterator;

public class DebugReportBlocks implements DebugReportProvider {

    private static final Gson b = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator c;

    public DebugReportBlocks(DebugReportGenerator debugreportgenerator) {
        this.c = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) throws IOException {
        JsonObject jsonobject = new JsonObject();
        Iterator iterator = IRegistry.BLOCK.iterator();

        while (iterator.hasNext()) {
            Block block = (Block) iterator.next();
            MinecraftKey minecraftkey = IRegistry.BLOCK.getKey(block);
            JsonObject jsonobject1 = new JsonObject();
            BlockStateList<Block, IBlockData> blockstatelist = block.getStates();

            if (!blockstatelist.d().isEmpty()) {
                JsonObject jsonobject2 = new JsonObject();
                Iterator iterator1 = blockstatelist.d().iterator();

                while (iterator1.hasNext()) {
                    IBlockState<?> iblockstate = (IBlockState) iterator1.next();
                    JsonArray jsonarray = new JsonArray();
                    Iterator iterator2 = iblockstate.d().iterator();

                    while (iterator2.hasNext()) {
                        Comparable<?> comparable = (Comparable) iterator2.next();

                        jsonarray.add(SystemUtils.a(iblockstate, (Object) comparable));
                    }

                    jsonobject2.add(iblockstate.a(), jsonarray);
                }

                jsonobject1.add("properties", jsonobject2);
            }

            JsonArray jsonarray1 = new JsonArray();

            JsonObject jsonobject3;

            for (UnmodifiableIterator unmodifiableiterator = blockstatelist.a().iterator(); unmodifiableiterator.hasNext(); jsonarray1.add(jsonobject3)) {
                IBlockData iblockdata = (IBlockData) unmodifiableiterator.next();

                jsonobject3 = new JsonObject();
                JsonObject jsonobject4 = new JsonObject();
                Iterator iterator3 = blockstatelist.d().iterator();

                while (iterator3.hasNext()) {
                    IBlockState<?> iblockstate1 = (IBlockState) iterator3.next();

                    jsonobject4.addProperty(iblockstate1.a(), SystemUtils.a(iblockstate1, (Object) iblockdata.get(iblockstate1)));
                }

                if (jsonobject4.size() > 0) {
                    jsonobject3.add("properties", jsonobject4);
                }

                jsonobject3.addProperty("id", Block.getCombinedId(iblockdata));
                if (iblockdata == block.getBlockData()) {
                    jsonobject3.addProperty("default", true);
                }
            }

            jsonobject1.add("states", jsonarray1);
            jsonobject.add(minecraftkey.toString(), jsonobject1);
        }

        java.nio.file.Path java_nio_file_path = this.c.b().resolve("reports/blocks.json");

        DebugReportProvider.a(DebugReportBlocks.b, hashcache, jsonobject, java_nio_file_path);
    }

    @Override
    public String a() {
        return "Block List";
    }
}

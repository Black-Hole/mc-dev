package net.minecraft.server;

import com.google.common.base.Charsets;
import com.mojang.datafixers.DataFixer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Iterator;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;

public class DebugReportStructure implements DebugReportProvider {

    private final DebugReportGenerator b;

    public DebugReportStructure(DebugReportGenerator debugreportgenerator) {
        this.b = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) throws IOException {
        Iterator iterator = this.b.a().iterator();

        while (iterator.hasNext()) {
            java.nio.file.Path java_nio_file_path = (java.nio.file.Path) iterator.next();
            java.nio.file.Path java_nio_file_path1 = java_nio_file_path.resolve("data/minecraft/structures/");

            if (Files.isDirectory(java_nio_file_path1, new LinkOption[0])) {
                a(DataConverterRegistry.a(), java_nio_file_path1);
            }
        }

    }

    @Override
    public String a() {
        return "Structure validator";
    }

    private static void a(DataFixer datafixer, java.nio.file.Path java_nio_file_path) throws IOException {
        Stream<java.nio.file.Path> stream = Files.walk(java_nio_file_path);
        Throwable throwable = null;

        try {
            stream.forEach((java_nio_file_path1) -> {
                if (Files.isRegularFile(java_nio_file_path1, new LinkOption[0])) {
                    b(datafixer, java_nio_file_path1);
                }

            });
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (stream != null) {
                if (throwable != null) {
                    try {
                        stream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    stream.close();
                }
            }

        }

    }

    private static void b(DataFixer datafixer, java.nio.file.Path java_nio_file_path) {
        try {
            String s = java_nio_file_path.getFileName().toString();

            if (s.endsWith(".snbt")) {
                c(datafixer, java_nio_file_path);
            } else {
                if (!s.endsWith(".nbt")) {
                    throw new IllegalArgumentException("Unrecognized format of file");
                }

                d(datafixer, java_nio_file_path);
            }

        } catch (Exception exception) {
            throw new DebugReportStructure.a(java_nio_file_path, exception);
        }
    }

    private static void c(DataFixer datafixer, java.nio.file.Path java_nio_file_path) throws Exception {
        InputStream inputstream = Files.newInputStream(java_nio_file_path);
        Throwable throwable = null;

        NBTTagCompound nbttagcompound;

        try {
            String s = IOUtils.toString(inputstream, Charsets.UTF_8);

            nbttagcompound = MojangsonParser.parse(s);
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (inputstream != null) {
                if (throwable != null) {
                    try {
                        inputstream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    inputstream.close();
                }
            }

        }

        a(datafixer, a(nbttagcompound));
    }

    private static void d(DataFixer datafixer, java.nio.file.Path java_nio_file_path) throws Exception {
        InputStream inputstream = Files.newInputStream(java_nio_file_path);
        Throwable throwable = null;

        NBTTagCompound nbttagcompound;

        try {
            nbttagcompound = NBTCompressedStreamTools.a(inputstream);
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (inputstream != null) {
                if (throwable != null) {
                    try {
                        inputstream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    inputstream.close();
                }
            }

        }

        a(datafixer, a(nbttagcompound));
    }

    private static NBTTagCompound a(NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKeyOfType("DataVersion", 99)) {
            nbttagcompound.setInt("DataVersion", 500);
        }

        return nbttagcompound;
    }

    private static NBTTagCompound a(DataFixer datafixer, NBTTagCompound nbttagcompound) {
        DefinedStructure definedstructure = new DefinedStructure();

        definedstructure.b(GameProfileSerializer.a(datafixer, DataFixTypes.STRUCTURE, nbttagcompound, nbttagcompound.getInt("DataVersion")));
        return definedstructure.a(new NBTTagCompound());
    }

    static class a extends RuntimeException {

        public a(java.nio.file.Path java_nio_file_path, Throwable throwable) {
            super("Failed to process file: " + java_nio_file_path.toAbsolutePath().toString(), throwable);
        }
    }
}

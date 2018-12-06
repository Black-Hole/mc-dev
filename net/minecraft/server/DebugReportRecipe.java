package net.minecraft.server;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportRecipe implements DebugReportProvider {

    private static final Logger b = LogManager.getLogger();
    private static final Gson c = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator d;

    public DebugReportRecipe(DebugReportGenerator debugreportgenerator) {
        this.d = debugreportgenerator;
    }

    public void a(HashCache hashcache) throws IOException {
        java.nio.file.Path java_nio_file_path = this.d.b();
        HashSet hashset = Sets.newHashSet();

        this.a((debugreportrecipedata) -> {
            if (!set.add(debugreportrecipedata.b())) {
                throw new IllegalStateException("Duplicate recipe " + debugreportrecipedata.b());
            } else {
                this.a(hashcache, debugreportrecipedata.a(), java_nio_file_path.resolve("data/" + debugreportrecipedata.b().b() + "/recipes/" + debugreportrecipedata.b().getKey() + ".json"));
                JsonObject jsonobject = debugreportrecipedata.c();

                if (jsonobject != null) {
                    this.b(hashcache, jsonobject, java_nio_file_path.resolve("data/" + debugreportrecipedata.b().b() + "/advancements/" + debugreportrecipedata.d().getKey() + ".json"));
                }

            }
        });
        this.b(hashcache, Advancement.SerializedAdvancement.a().a("impossible", (CriterionInstance) (new CriterionTriggerImpossible.a())).b(), java_nio_file_path.resolve("data/minecraft/advancements/recipes/root.json"));
    }

    private void a(HashCache hashcache, JsonObject jsonobject, java.nio.file.Path java_nio_file_path) {
        try {
            String s = DebugReportRecipe.c.toJson(jsonobject);
            String s1 = DebugReportRecipe.a.hashUnencodedChars(s).toString();

            if (!Objects.equals(hashcache.a(java_nio_file_path), s1) || !Files.exists(java_nio_file_path, new LinkOption[0])) {
                Files.createDirectories(java_nio_file_path.getParent(), new FileAttribute[0]);
                BufferedWriter bufferedwriter = Files.newBufferedWriter(java_nio_file_path, new OpenOption[0]);
                Throwable throwable = null;

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    if (bufferedwriter != null) {
                        if (throwable != null) {
                            try {
                                bufferedwriter.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        } else {
                            bufferedwriter.close();
                        }
                    }

                }
            }

            hashcache.a(java_nio_file_path, s1);
        } catch (IOException ioexception) {
            DebugReportRecipe.b.error("Couldn't save recipe {}", java_nio_file_path, ioexception);
        }

    }

    private void b(HashCache hashcache, JsonObject jsonobject, java.nio.file.Path java_nio_file_path) {
        try {
            String s = DebugReportRecipe.c.toJson(jsonobject);
            String s1 = DebugReportRecipe.a.hashUnencodedChars(s).toString();

            if (!Objects.equals(hashcache.a(java_nio_file_path), s1) || !Files.exists(java_nio_file_path, new LinkOption[0])) {
                Files.createDirectories(java_nio_file_path.getParent(), new FileAttribute[0]);
                BufferedWriter bufferedwriter = Files.newBufferedWriter(java_nio_file_path, new OpenOption[0]);
                Throwable throwable = null;

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    if (bufferedwriter != null) {
                        if (throwable != null) {
                            try {
                                bufferedwriter.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        } else {
                            bufferedwriter.close();
                        }
                    }

                }
            }

            hashcache.a(java_nio_file_path, s1);
        } catch (IOException ioexception) {
            DebugReportRecipe.b.error("Couldn't save recipe advancement {}", java_nio_file_path, ioexception);
        }

    }

    private void a(Consumer<DebugReportRecipeData> consumer) {
        DebugReportRecipeShaped.a(Blocks.ACACIA_WOOD, 3).a('#', (IMaterial) Blocks.ACACIA_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_LOG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.ACACIA_BOAT).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.ACACIA_BUTTON).b(Blocks.ACACIA_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_DOOR, 3).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.ACACIA_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.ACACIA_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.ACACIA_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.ACACIA_PLANKS, 4).a(TagsItem.ACACIA_LOGS).a("planks").a("has_logs", (CriterionInstance) this.a(TagsItem.ACACIA_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.ACACIA_PRESSURE_PLATE).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_SLAB, 6).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_STAIRS, 4).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACACIA_TRAPDOOR, 2).a('#', (IMaterial) Blocks.ACACIA_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.ACACIA_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ACTIVATOR_RAIL, 6).a('#', (IMaterial) Blocks.REDSTONE_TORCH).a('S', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("XSX").a("X#X").a("XSX").a("has_rail", (CriterionInstance) this.a((IMaterial) Blocks.RAIL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.ANDESITE, 2).b(Blocks.DIORITE).b(Blocks.COBBLESTONE).a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.DIORITE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.ANVIL).a('I', (IMaterial) Blocks.IRON_BLOCK).a('i', (IMaterial) Items.IRON_INGOT).a("III").a(" i ").a("iii").a("has_iron_block", (CriterionInstance) this.a((IMaterial) Blocks.IRON_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.ARMOR_STAND).a('/', (IMaterial) Items.STICK).a('_', (IMaterial) Blocks.STONE_SLAB).a("///").a(" / ").a("/_/").a("has_stone_slab", (CriterionInstance) this.a((IMaterial) Blocks.STONE_SLAB)).a(consumer);
        DebugReportRecipeShaped.a(Items.ARROW, 4).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.FLINT).a('Y', (IMaterial) Items.FEATHER).a("X").a("#").a("Y").a("has_feather", (CriterionInstance) this.a((IMaterial) Items.FEATHER)).a("has_flint", (CriterionInstance) this.a((IMaterial) Items.FLINT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BEACON).a('S', (IMaterial) Items.NETHER_STAR).a('G', (IMaterial) Blocks.GLASS).a('O', (IMaterial) Blocks.OBSIDIAN).a("GGG").a("GSG").a("OOO").a("has_nether_star", (CriterionInstance) this.a((IMaterial) Items.NETHER_STAR)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BEETROOT_SOUP).a('B', (IMaterial) Items.BOWL).a('O', (IMaterial) Items.BEETROOT).a("OOO").a("OOO").a(" B ").a("has_beetroot", (CriterionInstance) this.a((IMaterial) Items.BEETROOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_WOOD, 3).a('#', (IMaterial) Blocks.BIRCH_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_LOG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BIRCH_BOAT).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BIRCH_BUTTON).b(Blocks.BIRCH_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_DOOR, 3).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.BIRCH_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BIRCH_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.BIRCH_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BIRCH_PLANKS, 4).a(TagsItem.BIRCH_LOGS).a("planks").a("has_log", (CriterionInstance) this.a(TagsItem.BIRCH_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BIRCH_PRESSURE_PLATE).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_SLAB, 6).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_STAIRS, 4).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BIRCH_TRAPDOOR, 2).a('#', (IMaterial) Blocks.BIRCH_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.BIRCH_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BLACK_BANNER).a('#', (IMaterial) Blocks.BLACK_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_black_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BLACK_BED).a('#', (IMaterial) Blocks.BLACK_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_black_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BLACK_BED).b(Items.WHITE_BED).b(Items.INK_SAC).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "black_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BLACK_CARPET, 3).a('#', (IMaterial) Blocks.BLACK_WOOL).a("##").b("carpet").a("has_black_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLACK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BLACK_CONCRETE_POWDER, 8).b(Items.INK_SAC).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.INK_SAC).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.BLACK_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLACK_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.INK_SAC).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BLACK_WOOL).b(Items.INK_SAC).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BLAZE_POWDER, 2).b(Items.BLAZE_ROD).a("has_blaze_rod", (CriterionInstance) this.a((IMaterial) Items.BLAZE_ROD)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BLUE_BANNER).a('#', (IMaterial) Blocks.BLUE_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BLUE_BED).a('#', (IMaterial) Blocks.BLUE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BLUE_BED).b(Items.WHITE_BED).b(Items.LAPIS_LAZULI).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "blue_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BLUE_CARPET, 3).a('#', (IMaterial) Blocks.BLUE_WOOL).a("##").b("carpet").a("has_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BLUE_CONCRETE_POWDER, 8).b(Items.LAPIS_LAZULI).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BLUE_ICE).a('#', (IMaterial) Blocks.PACKED_ICE).a("###").a("###").a("###").a("has_at_least_9_packed_ice", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Blocks.PACKED_ICE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.LAPIS_LAZULI).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.BLUE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BLUE_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.LAPIS_LAZULI).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BLUE_WOOL).b(Items.LAPIS_LAZULI).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.OAK_BOAT).a('#', (IMaterial) Blocks.OAK_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        Item item = Items.BONE_MEAL;

        DebugReportRecipeShaped.a((IMaterial) Blocks.BONE_BLOCK).a('X', (IMaterial) Items.BONE_MEAL).a("XXX").a("XXX").a("XXX").a("has_at_least_9_bonemeal", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), item)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BONE_MEAL, 3).b(Items.BONE).a("bonemeal").a("has_bone", (CriterionInstance) this.a((IMaterial) Items.BONE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BONE_MEAL, 9).b(Blocks.BONE_BLOCK).a("bonemeal").a("has_at_least_9_bonemeal", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.BONE_MEAL)).a("has_bone_block", (CriterionInstance) this.a((IMaterial) Blocks.BONE_BLOCK)).a(consumer, "bone_meal_from_bone_block");
        DebugReportRecipeShapeless.a((IMaterial) Items.BOOK).b(Items.PAPER, 3).b(Items.LEATHER).a("has_paper", (CriterionInstance) this.a((IMaterial) Items.PAPER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BOOKSHELF).a('#', TagsItem.PLANKS).a('X', (IMaterial) Items.BOOK).a("###").a("XXX").a("###").a("has_book", (CriterionInstance) this.a((IMaterial) Items.BOOK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BOW).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.STRING).a(" #X").a("# X").a(" #X").a("has_string", (CriterionInstance) this.a((IMaterial) Items.STRING)).a(consumer);
        DebugReportRecipeShaped.a(Items.BOWL, 4).a('#', TagsItem.PLANKS).a("# #").a(" # ").a("has_brown_mushroom", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_MUSHROOM)).a("has_red_mushroom", (CriterionInstance) this.a((IMaterial) Blocks.RED_MUSHROOM)).a("has_mushroom_stew", (CriterionInstance) this.a((IMaterial) Items.MUSHROOM_STEW)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BREAD).a('#', (IMaterial) Items.WHEAT).a("###").a("has_wheat", (CriterionInstance) this.a((IMaterial) Items.WHEAT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BREWING_STAND).a('B', (IMaterial) Items.BLAZE_ROD).a('#', (IMaterial) Blocks.COBBLESTONE).a(" B ").a("###").a("has_blaze_rod", (CriterionInstance) this.a((IMaterial) Items.BLAZE_ROD)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.BRICKS).a('#', (IMaterial) Items.BRICK).a("##").a("##").a("has_brick", (CriterionInstance) this.a((IMaterial) Items.BRICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BRICK_SLAB, 6).a('#', (IMaterial) Blocks.BRICKS).a("###").a("has_brick_block", (CriterionInstance) this.a((IMaterial) Blocks.BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BRICK_STAIRS, 4).a('#', (IMaterial) Blocks.BRICKS).a("#  ").a("## ").a("###").a("has_brick_block", (CriterionInstance) this.a((IMaterial) Blocks.BRICKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BROWN_BANNER).a('#', (IMaterial) Blocks.BROWN_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_brown_wool", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BROWN_BED).a('#', (IMaterial) Blocks.BROWN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_brown_wool", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.BROWN_BED).b(Items.WHITE_BED).b(Items.COCOA_BEANS).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "brown_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.BROWN_CARPET, 3).a('#', (IMaterial) Blocks.BROWN_WOOL).a("##").b("carpet").a("has_brown_wool", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BROWN_CONCRETE_POWDER, 8).b(Items.COCOA_BEANS).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.COCOA_BEANS).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.BROWN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.BROWN_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.COCOA_BEANS).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.BROWN_WOOL).b(Items.COCOA_BEANS).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.BUCKET).a('#', (IMaterial) Items.IRON_INGOT).a("# #").a(" # ").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CAKE).a('A', (IMaterial) Items.MILK_BUCKET).a('B', (IMaterial) Items.SUGAR).a('C', (IMaterial) Items.WHEAT).a('E', (IMaterial) Items.EGG).a("AAA").a("BEB").a("CCC").a("has_egg", (CriterionInstance) this.a((IMaterial) Items.EGG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.CARROT_ON_A_STICK).a('#', (IMaterial) Items.FISHING_ROD).a('X', (IMaterial) Items.CARROT).a("# ").a(" X").a("has_carrot", (CriterionInstance) this.a((IMaterial) Items.CARROT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CAULDRON).a('#', (IMaterial) Items.IRON_INGOT).a("# #").a("# #").a("###").a("has_water_bucket", (CriterionInstance) this.a((IMaterial) Items.WATER_BUCKET)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CHEST).a('#', TagsItem.PLANKS).a("###").a("# #").a("###").a("has_lots_of_items", (CriterionInstance) (new CriterionTriggerInventoryChanged.b(CriterionConditionValue.d.b(10), CriterionConditionValue.d.e, CriterionConditionValue.d.e, new CriterionConditionItem[0]))).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.CHEST_MINECART).a('A', (IMaterial) Blocks.CHEST).a('B', (IMaterial) Items.MINECART).a("A").a("B").a("has_minecart", (CriterionInstance) this.a((IMaterial) Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CHISELED_QUARTZ_BLOCK).a('#', (IMaterial) Blocks.QUARTZ_SLAB).a("#").a("#").a("has_chiseled_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CHISELED_STONE_BRICKS).a('#', (IMaterial) Blocks.STONE_BRICK_SLAB).a("#").a("#").a("has_stone_bricks", (CriterionInstance) this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CLAY).a('#', (IMaterial) Items.CLAY_BALL).a("##").a("##").a("has_clay_ball", (CriterionInstance) this.a((IMaterial) Items.CLAY_BALL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.CLOCK).a('#', (IMaterial) Items.GOLD_INGOT).a('X', (IMaterial) Items.REDSTONE).a(" # ").a("#X#").a(" # ").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.COAL, 9).b(Blocks.COAL_BLOCK).a("has_at_least_9_coal", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.COAL)).a("has_coal_block", (CriterionInstance) this.a((IMaterial) Blocks.COAL_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.COAL_BLOCK).a('#', (IMaterial) Items.COAL).a("###").a("###").a("###").a("has_at_least_9_coal", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.COAL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COARSE_DIRT, 4).a('D', (IMaterial) Blocks.DIRT).a('G', (IMaterial) Blocks.GRAVEL).a("DG").a("GD").a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_SLAB, 6).a('#', (IMaterial) Blocks.COBBLESTONE).a("###").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_WALL, 6).a('#', (IMaterial) Blocks.COBBLESTONE).a("###").a("###").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.COMPARATOR).a('#', (IMaterial) Blocks.REDSTONE_TORCH).a('X', (IMaterial) Items.QUARTZ).a('I', (IMaterial) Blocks.STONE).a(" # ").a("#X#").a("III").a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.COMPASS).a('#', (IMaterial) Items.IRON_INGOT).a('X', (IMaterial) Items.REDSTONE).a(" # ").a("#X#").a(" # ").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Items.COOKIE, 8).a('#', (IMaterial) Items.WHEAT).a('X', (IMaterial) Items.COCOA_BEANS).a("#X#").a("has_cocoa", (CriterionInstance) this.a((IMaterial) Items.COCOA_BEANS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CRAFTING_TABLE).a('#', TagsItem.PLANKS).a("##").a("##").a("has_planks", (CriterionInstance) this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CHISELED_RED_SANDSTONE).a('#', (IMaterial) Blocks.RED_SANDSTONE_SLAB).a("#").a("#").a("has_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CHISELED_SANDSTONE).a('#', (IMaterial) Blocks.SANDSTONE_SLAB).a("#").a("#").a("has_stone_slab", (CriterionInstance) this.a((IMaterial) Blocks.SANDSTONE_SLAB)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.CYAN_BANNER).a('#', (IMaterial) Blocks.CYAN_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_cyan_wool", (CriterionInstance) this.a((IMaterial) Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.CYAN_BED).a('#', (IMaterial) Blocks.CYAN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_cyan_wool", (CriterionInstance) this.a((IMaterial) Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.CYAN_BED).b(Items.WHITE_BED).b(Items.CYAN_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "cyan_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.CYAN_CARPET, 3).a('#', (IMaterial) Blocks.CYAN_WOOL).a("##").b("carpet").a("has_cyan_wool", (CriterionInstance) this.a((IMaterial) Blocks.CYAN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.CYAN_CONCRETE_POWDER, 8).b(Items.CYAN_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.CYAN_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.CACTUS_GREEN).a("has_green_dye", (CriterionInstance) this.a((IMaterial) Items.CACTUS_GREEN)).a("has_lapis", (CriterionInstance) this.a((IMaterial) Items.LAPIS_LAZULI)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.CYAN_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.CYAN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CYAN_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.CYAN_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.CYAN_WOOL).b(Items.CYAN_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_WOOD, 3).a('#', (IMaterial) Blocks.DARK_OAK_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_LOG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DARK_OAK_BOAT).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.DARK_OAK_BUTTON).b(Blocks.DARK_OAK_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_DOOR, 3).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.DARK_OAK_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DARK_OAK_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.DARK_OAK_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.DARK_OAK_PLANKS, 4).a(TagsItem.DARK_OAK_LOGS).a("planks").a("has_logs", (CriterionInstance) this.a(TagsItem.DARK_OAK_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DARK_OAK_PRESSURE_PLATE).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_SLAB, 6).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_STAIRS, 4).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_OAK_TRAPDOOR, 2).a('#', (IMaterial) Blocks.DARK_OAK_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.DARK_OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DARK_PRISMARINE).a('S', (IMaterial) Items.PRISMARINE_SHARD).a('I', (IMaterial) Items.INK_SAC).a("SSS").a("SIS").a("SSS").a("has_prismarine_shard", (CriterionInstance) this.a((IMaterial) Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_STAIRS, 4).a('#', (IMaterial) Blocks.PRISMARINE).a("#  ").a("## ").a("###").a("has_prismarine", (CriterionInstance) this.a((IMaterial) Blocks.PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_BRICK_STAIRS, 4).a('#', (IMaterial) Blocks.PRISMARINE_BRICKS).a("#  ").a("## ").a("###").a("has_prismarine_bricks", (CriterionInstance) this.a((IMaterial) Blocks.PRISMARINE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_PRISMARINE_STAIRS, 4).a('#', (IMaterial) Blocks.DARK_PRISMARINE).a("#  ").a("## ").a("###").a("has_dark_prismarine", (CriterionInstance) this.a((IMaterial) Blocks.DARK_PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DAYLIGHT_DETECTOR).a('Q', (IMaterial) Items.QUARTZ).a('G', (IMaterial) Blocks.GLASS).a('W', RecipeItemStack.a(TagsItem.WOODEN_SLABS)).a("GGG").a("QQQ").a("WWW").a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DETECTOR_RAIL, 6).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Blocks.STONE_PRESSURE_PLATE).a('X', (IMaterial) Items.IRON_INGOT).a("X X").a("X#X").a("XRX").a("has_rail", (CriterionInstance) this.a((IMaterial) Blocks.RAIL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.DIAMOND, 9).b(Blocks.DIAMOND_BLOCK).a("has_at_least_9_diamond", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.DIAMOND)).a("has_diamond_block", (CriterionInstance) this.a((IMaterial) Blocks.DIAMOND_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_AXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.DIAMOND).a("XX").a("X#").a(" #").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DIAMOND_BLOCK).a('#', (IMaterial) Items.DIAMOND).a("###").a("###").a("###").a("has_at_least_9_diamond", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_BOOTS).a('X', (IMaterial) Items.DIAMOND).a("X X").a("X X").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_CHESTPLATE).a('X', (IMaterial) Items.DIAMOND).a("X X").a("XXX").a("XXX").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_HELMET).a('X', (IMaterial) Items.DIAMOND).a("XXX").a("X X").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_HOE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.DIAMOND).a("XX").a(" #").a(" #").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_LEGGINGS).a('X', (IMaterial) Items.DIAMOND).a("XXX").a("X X").a("X X").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_PICKAXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.DIAMOND).a("XXX").a(" # ").a(" # ").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_SHOVEL).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.DIAMOND).a("X").a("#").a("#").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.DIAMOND_SWORD).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.DIAMOND).a("X").a("X").a("#").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DIORITE, 2).a('Q', (IMaterial) Items.QUARTZ).a('C', (IMaterial) Blocks.COBBLESTONE).a("CQ").a("QC").a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DISPENSER).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Blocks.COBBLESTONE).a('X', (IMaterial) Items.BOW).a("###").a("#X#").a("#R#").a("has_bow", (CriterionInstance) this.a((IMaterial) Items.BOW)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.DROPPER).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Blocks.COBBLESTONE).a("###").a("# #").a("#R#").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.EMERALD, 9).b(Blocks.EMERALD_BLOCK).a("has_at_least_9_emerald", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.EMERALD)).a("has_emerald_block", (CriterionInstance) this.a((IMaterial) Blocks.EMERALD_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.EMERALD_BLOCK).a('#', (IMaterial) Items.EMERALD).a("###").a("###").a("###").a("has_at_least_9_emerald", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.EMERALD)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.ENCHANTING_TABLE).a('B', (IMaterial) Items.BOOK).a('#', (IMaterial) Blocks.OBSIDIAN).a('D', (IMaterial) Items.DIAMOND).a(" B ").a("D#D").a("###").a("has_obsidian", (CriterionInstance) this.a((IMaterial) Blocks.OBSIDIAN)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.ENDER_CHEST).a('#', (IMaterial) Blocks.OBSIDIAN).a('E', (IMaterial) Items.ENDER_EYE).a("###").a("#E#").a("###").a("has_ender_eye", (CriterionInstance) this.a((IMaterial) Items.ENDER_EYE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.ENDER_EYE).b(Items.ENDER_PEARL).b(Items.BLAZE_POWDER).a("has_blaze_powder", (CriterionInstance) this.a((IMaterial) Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.END_STONE_BRICKS, 4).a('#', (IMaterial) Blocks.END_STONE).a("##").a("##").a("has_end_stone", (CriterionInstance) this.a((IMaterial) Blocks.END_STONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.END_CRYSTAL).a('T', (IMaterial) Items.GHAST_TEAR).a('E', (IMaterial) Items.ENDER_EYE).a('G', (IMaterial) Blocks.GLASS).a("GGG").a("GEG").a("GTG").a("has_ender_eye", (CriterionInstance) this.a((IMaterial) Items.ENDER_EYE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.END_ROD, 4).a('#', (IMaterial) Items.POPPED_CHORUS_FRUIT).a('/', (IMaterial) Items.BLAZE_ROD).a("/").a("#").a("has_chorus_fruit_popped", (CriterionInstance) this.a((IMaterial) Items.POPPED_CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.OAK_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.OAK_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.OAK_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.FERMENTED_SPIDER_EYE).b(Items.SPIDER_EYE).b(Blocks.BROWN_MUSHROOM).b(Items.SUGAR).a("has_spider_eye", (CriterionInstance) this.a((IMaterial) Items.SPIDER_EYE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.FIRE_CHARGE, 3).b(Items.GUNPOWDER).b(Items.BLAZE_POWDER).a(RecipeItemStack.a(Items.COAL, Items.CHARCOAL)).a("has_blaze_powder", (CriterionInstance) this.a((IMaterial) Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.FISHING_ROD).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.STRING).a("  #").a(" #X").a("# X").a("has_string", (CriterionInstance) this.a((IMaterial) Items.STRING)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.FLINT_AND_STEEL).b(Items.IRON_INGOT).b(Items.FLINT).a("has_flint", (CriterionInstance) this.a((IMaterial) Items.FLINT)).a("has_obsidian", (CriterionInstance) this.a((IMaterial) Blocks.OBSIDIAN)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.FLOWER_POT).a('#', (IMaterial) Items.BRICK).a("# #").a(" # ").a("has_brick", (CriterionInstance) this.a((IMaterial) Items.BRICK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.FURNACE).a('#', (IMaterial) Blocks.COBBLESTONE).a("###").a("# #").a("###").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.FURNACE_MINECART).a('A', (IMaterial) Blocks.FURNACE).a('B', (IMaterial) Items.MINECART).a("A").a("B").a("has_minecart", (CriterionInstance) this.a((IMaterial) Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Items.GLASS_BOTTLE, 3).a('#', (IMaterial) Blocks.GLASS).a("# #").a(" # ").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GLASS_PANE, 16).a('#', (IMaterial) Blocks.GLASS).a("###").a("###").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.GLOWSTONE).a('#', (IMaterial) Items.GLOWSTONE_DUST).a("##").a("##").a("has_glowstone_dust", (CriterionInstance) this.a((IMaterial) Items.GLOWSTONE_DUST)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_APPLE).a('#', (IMaterial) Items.GOLD_INGOT).a('X', (IMaterial) Items.APPLE).a("###").a("#X#").a("###").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_AXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("XX").a("X#").a(" #").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_BOOTS).a('X', (IMaterial) Items.GOLD_INGOT).a("X X").a("X X").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_CARROT).a('#', (IMaterial) Items.GOLD_NUGGET).a('X', (IMaterial) Items.CARROT).a("###").a("#X#").a("###").a("has_gold_nugget", (CriterionInstance) this.a((IMaterial) Items.GOLD_NUGGET)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_CHESTPLATE).a('X', (IMaterial) Items.GOLD_INGOT).a("X X").a("XXX").a("XXX").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_HELMET).a('X', (IMaterial) Items.GOLD_INGOT).a("XXX").a("X X").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_HOE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("XX").a(" #").a(" #").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_LEGGINGS).a('X', (IMaterial) Items.GOLD_INGOT).a("XXX").a("X X").a("X X").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_PICKAXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("XXX").a(" # ").a(" # ").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POWERED_RAIL, 6).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("X X").a("X#X").a("XRX").a("has_rail", (CriterionInstance) this.a((IMaterial) Blocks.RAIL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_SHOVEL).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("X").a("#").a("#").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GOLDEN_SWORD).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.GOLD_INGOT).a("X").a("X").a("#").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.GOLD_BLOCK).a('#', (IMaterial) Items.GOLD_INGOT).a("###").a("###").a("###").a("has_at_least_9_gold_ingot", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.GOLD_INGOT, 9).b(Blocks.GOLD_BLOCK).a("gold_ingot").a("has_at_least_9_gold_ingot", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.GOLD_INGOT)).a("has_gold_block", (CriterionInstance) this.a((IMaterial) Blocks.GOLD_BLOCK)).a(consumer, "gold_ingot_from_gold_block");
        DebugReportRecipeShaped.a((IMaterial) Items.GOLD_INGOT).a('#', (IMaterial) Items.GOLD_NUGGET).a("###").a("###").a("###").b("gold_ingot").a("has_at_least_9_gold_nugget", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.GOLD_NUGGET)).a(consumer, "gold_ingot_from_nuggets");
        DebugReportRecipeShapeless.a((IMaterial) Items.GOLD_NUGGET, 9).b(Items.GOLD_INGOT).a("has_at_least_9_gold_nugget", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.GOLD_NUGGET)).a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.GRANITE).b(Blocks.DIORITE).b(Items.QUARTZ).a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GRAY_BANNER).a('#', (IMaterial) Blocks.GRAY_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GRAY_BED).a('#', (IMaterial) Blocks.GRAY_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.GRAY_BED).b(Items.WHITE_BED).b(Items.GRAY_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "gray_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.GRAY_CARPET, 3).a('#', (IMaterial) Blocks.GRAY_WOOL).a("##").b("carpet").a("has_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.GRAY_CONCRETE_POWDER, 8).b(Items.GRAY_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.GRAY_DYE, 2).b(Items.INK_SAC).b(Items.BONE_MEAL).a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a("has_black_dye", (CriterionInstance) this.a((IMaterial) Items.INK_SAC)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.GRAY_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.GRAY_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GRAY_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.GRAY_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.GRAY_WOOL).b(Items.GRAY_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GREEN_BANNER).a('#', (IMaterial) Blocks.GREEN_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_green_wool", (CriterionInstance) this.a((IMaterial) Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GREEN_BED).a('#', (IMaterial) Blocks.GREEN_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_green_wool", (CriterionInstance) this.a((IMaterial) Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.GREEN_BED).b(Items.WHITE_BED).b(Items.CACTUS_GREEN).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "green_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.GREEN_CARPET, 3).a('#', (IMaterial) Blocks.GREEN_WOOL).a("##").b("carpet").a("has_green_wool", (CriterionInstance) this.a((IMaterial) Blocks.GREEN_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.GREEN_CONCRETE_POWDER, 8).b(Items.CACTUS_GREEN).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.CACTUS_GREEN).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.GREEN_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.GREEN_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.CACTUS_GREEN).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.GREEN_WOOL).b(Items.CACTUS_GREEN).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.HAY_BLOCK).a('#', (IMaterial) Items.WHEAT).a("###").a("###").a("###").a("has_at_least_9_wheat", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.WHEAT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).a('#', (IMaterial) Items.IRON_INGOT).a("##").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.HOPPER).a('C', (IMaterial) Blocks.CHEST).a('I', (IMaterial) Items.IRON_INGOT).a("I I").a("ICI").a(" I ").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.HOPPER_MINECART).a('A', (IMaterial) Blocks.HOPPER).a('B', (IMaterial) Items.MINECART).a("A").a("B").a("has_minecart", (CriterionInstance) this.a((IMaterial) Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_AXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("XX").a("X#").a(" #").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_BARS, 16).a('#', (IMaterial) Items.IRON_INGOT).a("###").a("###").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.IRON_BLOCK).a('#', (IMaterial) Items.IRON_INGOT).a("###").a("###").a("###").a("has_at_least_9_iron_ingot", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_BOOTS).a('X', (IMaterial) Items.IRON_INGOT).a("X X").a("X X").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_CHESTPLATE).a('X', (IMaterial) Items.IRON_INGOT).a("X X").a("XXX").a("XXX").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.IRON_DOOR, 3).a('#', (IMaterial) Items.IRON_INGOT).a("##").a("##").a("##").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_HELMET).a('X', (IMaterial) Items.IRON_INGOT).a("XXX").a("X X").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_HOE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("XX").a(" #").a(" #").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.IRON_INGOT, 9).b(Blocks.IRON_BLOCK).a("iron_ingot").a("has_at_least_9_iron_ingot", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.IRON_INGOT)).a("has_iron_block", (CriterionInstance) this.a((IMaterial) Blocks.IRON_BLOCK)).a(consumer, "iron_ingot_from_iron_block");
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_INGOT).a('#', (IMaterial) Items.IRON_NUGGET).a("###").a("###").a("###").b("iron_ingot").a("has_at_least_9_iron_nugget", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.IRON_NUGGET)).a(consumer, "iron_ingot_from_nuggets");
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_LEGGINGS).a('X', (IMaterial) Items.IRON_INGOT).a("XXX").a("X X").a("X X").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.IRON_NUGGET, 9).b(Items.IRON_INGOT).a("has_at_least_9_iron_nugget", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.IRON_NUGGET)).a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_PICKAXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("XXX").a(" # ").a(" # ").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_SHOVEL).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("X").a("#").a("#").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.IRON_SWORD).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("X").a("X").a("#").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.IRON_TRAPDOOR).a('#', (IMaterial) Items.IRON_INGOT).a("##").a("##").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.ITEM_FRAME).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.LEATHER).a("###").a("#X#").a("###").a("has_leather", (CriterionInstance) this.a((IMaterial) Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.JUKEBOX).a('#', TagsItem.PLANKS).a('X', (IMaterial) Items.DIAMOND).a("###").a("#X#").a("###").a("has_diamond", (CriterionInstance) this.a((IMaterial) Items.DIAMOND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_WOOD, 3).a('#', (IMaterial) Blocks.JUNGLE_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_LOG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.JUNGLE_BOAT).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.JUNGLE_BUTTON).b(Blocks.JUNGLE_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_DOOR, 3).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.JUNGLE_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.JUNGLE_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.JUNGLE_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.JUNGLE_PLANKS, 4).a(TagsItem.JUNGLE_LOGS).a("planks").a("has_log", (CriterionInstance) this.a(TagsItem.JUNGLE_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.JUNGLE_PRESSURE_PLATE).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_SLAB, 6).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_STAIRS, 4).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.JUNGLE_TRAPDOOR, 2).a('#', (IMaterial) Blocks.JUNGLE_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.JUNGLE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LADDER, 3).a('#', (IMaterial) Items.STICK).a("# #").a("###").a("# #").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.LAPIS_BLOCK).a('#', (IMaterial) Items.LAPIS_LAZULI).a("###").a("###").a("###").a("has_at_least_9_lapis", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.LAPIS_LAZULI)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LAPIS_LAZULI, 9).b(Blocks.LAPIS_BLOCK).a("has_at_least_9_lapis", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.LAPIS_LAZULI)).a("has_lapis_block", (CriterionInstance) this.a((IMaterial) Blocks.LAPIS_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Items.LEAD, 2).a('~', (IMaterial) Items.STRING).a('O', (IMaterial) Items.SLIME_BALL).a("~~ ").a("~O ").a("  ~").a("has_slime_ball", (CriterionInstance) this.a((IMaterial) Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LEATHER).a('#', (IMaterial) Items.RABBIT_HIDE).a("##").a("##").a("has_rabbit_hide", (CriterionInstance) this.a((IMaterial) Items.RABBIT_HIDE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LEATHER_BOOTS).a('X', (IMaterial) Items.LEATHER).a("X X").a("X X").a("has_leather", (CriterionInstance) this.a((IMaterial) Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LEATHER_CHESTPLATE).a('X', (IMaterial) Items.LEATHER).a("X X").a("XXX").a("XXX").a("has_leather", (CriterionInstance) this.a((IMaterial) Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LEATHER_HELMET).a('X', (IMaterial) Items.LEATHER).a("XXX").a("X X").a("has_leather", (CriterionInstance) this.a((IMaterial) Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LEATHER_LEGGINGS).a('X', (IMaterial) Items.LEATHER).a("XXX").a("X X").a("X X").a("has_leather", (CriterionInstance) this.a((IMaterial) Items.LEATHER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.LEVER).a('#', (IMaterial) Blocks.COBBLESTONE).a('X', (IMaterial) Items.STICK).a("X").a("#").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIGHT_BLUE_BANNER).a('#', (IMaterial) Blocks.LIGHT_BLUE_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_light_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIGHT_BLUE_BED).a('#', (IMaterial) Blocks.LIGHT_BLUE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_light_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_BLUE_BED).b(Items.WHITE_BED).b(Items.LIGHT_BLUE_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "light_blue_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_CARPET, 3).a('#', (IMaterial) Blocks.LIGHT_BLUE_WOOL).a("##").b("carpet").a("has_light_blue_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_BLUE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIGHT_BLUE_CONCRETE_POWDER, 8).b(Items.LIGHT_BLUE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_BLUE_DYE).b(Blocks.BLUE_ORCHID).a("light_blue_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.BLUE_ORCHID)).a(consumer, "light_blue_dye_from_blue_orchid");
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_BLUE_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.BONE_MEAL).a("light_blue_dye").a("has_lapis", (CriterionInstance) this.a((IMaterial) Items.LAPIS_LAZULI)).a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a(consumer, "light_blue_dye_from_lapis_bonemeal");
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.LIGHT_BLUE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.LIGHT_BLUE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_BLUE_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.LIGHT_BLUE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIGHT_BLUE_WOOL).b(Items.LIGHT_BLUE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIGHT_GRAY_BANNER).a('#', (IMaterial) Blocks.LIGHT_GRAY_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_light_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIGHT_GRAY_BED).a('#', (IMaterial) Blocks.LIGHT_GRAY_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_light_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_BED).b(Items.WHITE_BED).b(Items.LIGHT_GRAY_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "light_gray_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_CARPET, 3).a('#', (IMaterial) Blocks.LIGHT_GRAY_WOOL).a("##").b("carpet").a("has_light_gray_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_GRAY_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIGHT_GRAY_CONCRETE_POWDER, 8).b(Items.LIGHT_GRAY_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_DYE).b(Blocks.AZURE_BLUET).a("light_gray_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.AZURE_BLUET)).a(consumer, "light_gray_dye_from_azure_bluet");
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_DYE, 2).b(Items.GRAY_DYE).b(Items.BONE_MEAL).a("light_gray_dye").a("has_gray_dye", (CriterionInstance) this.a((IMaterial) Items.GRAY_DYE)).a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a(consumer, "light_gray_dye_from_gray_bonemeal");
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_DYE, 3).b(Items.INK_SAC).b(Items.BONE_MEAL, 2).a("light_gray_dye").a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a("has_black_dye", (CriterionInstance) this.a((IMaterial) Items.INK_SAC)).a(consumer, "light_gray_dye_from_ink_bonemeal");
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_DYE).b(Blocks.OXEYE_DAISY).a("light_gray_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.OXEYE_DAISY)).a(consumer, "light_gray_dye_from_oxeye_daisy");
        DebugReportRecipeShapeless.a((IMaterial) Items.LIGHT_GRAY_DYE).b(Blocks.WHITE_TULIP).a("light_gray_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_TULIP)).a(consumer, "light_gray_dye_from_white_tulip");
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.LIGHT_GRAY_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.LIGHT_GRAY_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIGHT_GRAY_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.LIGHT_GRAY_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIGHT_GRAY_WOOL).b(Items.LIGHT_GRAY_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).a('#', (IMaterial) Items.GOLD_INGOT).a("##").a("has_gold_ingot", (CriterionInstance) this.a((IMaterial) Items.GOLD_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIME_BANNER).a('#', (IMaterial) Blocks.LIME_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_lime_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.LIME_BED).a('#', (IMaterial) Blocks.LIME_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_lime_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIME_BED).b(Items.WHITE_BED).b(Items.LIME_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "lime_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.LIME_CARPET, 3).a('#', (IMaterial) Blocks.LIME_WOOL).a("##").b("carpet").a("has_lime_wool", (CriterionInstance) this.a((IMaterial) Blocks.LIME_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIME_CONCRETE_POWDER, 8).b(Items.LIME_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.LIME_DYE, 2).b(Items.CACTUS_GREEN).b(Items.BONE_MEAL).a("has_green_dye", (CriterionInstance) this.a((IMaterial) Items.CACTUS_GREEN)).a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.LIME_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.LIME_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.LIME_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.LIME_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.LIME_WOOL).b(Items.LIME_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.JACK_O_LANTERN).a('A', (IMaterial) Blocks.CARVED_PUMPKIN).a('B', (IMaterial) Blocks.TORCH).a("A").a("B").a("has_carved_pumpkin", (CriterionInstance) this.a((IMaterial) Blocks.CARVED_PUMPKIN)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.MAGENTA_BANNER).a('#', (IMaterial) Blocks.MAGENTA_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_magenta_wool", (CriterionInstance) this.a((IMaterial) Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.MAGENTA_BED).a('#', (IMaterial) Blocks.MAGENTA_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_magenta_wool", (CriterionInstance) this.a((IMaterial) Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_BED).b(Items.WHITE_BED).b(Items.MAGENTA_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "magenta_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.MAGENTA_CARPET, 3).a('#', (IMaterial) Blocks.MAGENTA_WOOL).a("##").b("carpet").a("has_magenta_wool", (CriterionInstance) this.a((IMaterial) Blocks.MAGENTA_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.MAGENTA_CONCRETE_POWDER, 8).b(Items.MAGENTA_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_DYE).b(Blocks.ALLIUM).a("magenta_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.ALLIUM)).a(consumer, "magenta_dye_from_allium");
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_DYE, 4).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED, 2).b(Items.BONE_MEAL).a("magenta_dye").a("has_lapis", (CriterionInstance) this.a((IMaterial) Items.LAPIS_LAZULI)).a("has_rose_red", (CriterionInstance) this.a((IMaterial) Items.ROSE_RED)).a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a(consumer, "magenta_dye_from_lapis_ink_bonemeal");
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_DYE, 3).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED).b(Items.PINK_DYE).a("magenta_dye").a("has_pink_dye", (CriterionInstance) this.a((IMaterial) Items.PINK_DYE)).a("has_lapis", (CriterionInstance) this.a((IMaterial) Items.LAPIS_LAZULI)).a("has_red_dye", (CriterionInstance) this.a((IMaterial) Items.ROSE_RED)).a(consumer, "magenta_dye_from_lapis_red_pink");
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_DYE, 2).b(Blocks.LILAC).a("magenta_dye").a("has_double_plant", (CriterionInstance) this.a((IMaterial) Blocks.LILAC)).a(consumer, "magenta_dye_from_lilac");
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGENTA_DYE, 2).b(Items.PURPLE_DYE).b(Items.PINK_DYE).a("magenta_dye").a("has_pink_dye", (CriterionInstance) this.a((IMaterial) Items.PINK_DYE)).a("has_purple_dye", (CriterionInstance) this.a((IMaterial) Items.PURPLE_DYE)).a(consumer, "magenta_dye_from_purple_and_pink");
        DebugReportRecipeShaped.a(Blocks.MAGENTA_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.MAGENTA_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MAGENTA_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.MAGENTA_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MAGENTA_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.MAGENTA_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.MAGENTA_WOOL).b(Items.MAGENTA_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.MAGMA_BLOCK).a('#', (IMaterial) Items.MAGMA_CREAM).a("##").a("##").a("has_magma_cream", (CriterionInstance) this.a((IMaterial) Items.MAGMA_CREAM)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.MAGMA_CREAM).b(Items.BLAZE_POWDER).b(Items.SLIME_BALL).a("has_blaze_powder", (CriterionInstance) this.a((IMaterial) Items.BLAZE_POWDER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.MAP).a('#', (IMaterial) Items.PAPER).a('X', (IMaterial) Items.COMPASS).a("###").a("#X#").a("###").a("has_compass", (CriterionInstance) this.a((IMaterial) Items.COMPASS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.MELON).a('M', (IMaterial) Items.MELON_SLICE).a("MMM").a("MMM").a("MMM").a("has_melon", (CriterionInstance) this.a((IMaterial) Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.MELON_SEEDS).b(Items.MELON_SLICE).a("has_melon", (CriterionInstance) this.a((IMaterial) Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.MINECART).a('#', (IMaterial) Items.IRON_INGOT).a("# #").a("###").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.MOSSY_COBBLESTONE).b(Blocks.COBBLESTONE).b(Blocks.VINE).a("has_vine", (CriterionInstance) this.a((IMaterial) Blocks.VINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.MOSSY_COBBLESTONE_WALL, 6).a('#', (IMaterial) Blocks.MOSSY_COBBLESTONE).a("###").a("###").a("has_mossy_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.MOSSY_COBBLESTONE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.MOSSY_STONE_BRICKS).b(Blocks.STONE_BRICKS).b(Blocks.VINE).a("has_mossy_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.MOSSY_COBBLESTONE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.MUSHROOM_STEW).b(Blocks.BROWN_MUSHROOM).b(Blocks.RED_MUSHROOM).b(Items.BOWL).a("has_mushroom_stew", (CriterionInstance) this.a((IMaterial) Items.MUSHROOM_STEW)).a("has_bowl", (CriterionInstance) this.a((IMaterial) Items.BOWL)).a("has_brown_mushroom", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_MUSHROOM)).a("has_red_mushroom", (CriterionInstance) this.a((IMaterial) Blocks.RED_MUSHROOM)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.NETHER_BRICKS).a('N', (IMaterial) Items.NETHER_BRICK).a("NN").a("NN").a("has_netherbrick", (CriterionInstance) this.a((IMaterial) Items.NETHER_BRICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_FENCE, 6).a('#', (IMaterial) Blocks.NETHER_BRICKS).a("###").a("###").a("has_nether_brick", (CriterionInstance) this.a((IMaterial) Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_SLAB, 6).a('#', (IMaterial) Blocks.NETHER_BRICKS).a("###").a("has_nether_brick", (CriterionInstance) this.a((IMaterial) Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.NETHER_BRICK_STAIRS, 4).a('#', (IMaterial) Blocks.NETHER_BRICKS).a("#  ").a("## ").a("###").a("has_nether_brick", (CriterionInstance) this.a((IMaterial) Blocks.NETHER_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.NETHER_WART_BLOCK).a('#', (IMaterial) Items.NETHER_WART).a("###").a("###").a("###").a("has_nether_wart", (CriterionInstance) this.a((IMaterial) Items.NETHER_WART)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.NOTE_BLOCK).a('#', TagsItem.PLANKS).a('X', (IMaterial) Items.REDSTONE).a("###").a("#X#").a("###").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_WOOD, 3).a('#', (IMaterial) Blocks.OAK_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.OAK_LOG)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.OAK_BUTTON).b(Blocks.OAK_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.OAK_PLANKS, 4).a(TagsItem.OAK_LOGS).a("planks").a("has_log", (CriterionInstance) this.a(TagsItem.OAK_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.OAK_PRESSURE_PLATE).a('#', (IMaterial) Blocks.OAK_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_SLAB, 6).a('#', (IMaterial) Blocks.OAK_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_STAIRS, 4).a('#', (IMaterial) Blocks.OAK_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_TRAPDOOR, 2).a('#', (IMaterial) Blocks.OAK_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.OBSERVER).a('Q', (IMaterial) Items.QUARTZ).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Blocks.COBBLESTONE).a("###").a("RRQ").a("###").a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.ORANGE_BANNER).a('#', (IMaterial) Blocks.ORANGE_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_orange_wool", (CriterionInstance) this.a((IMaterial) Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.ORANGE_BED).a('#', (IMaterial) Blocks.ORANGE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_orange_wool", (CriterionInstance) this.a((IMaterial) Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.ORANGE_BED).b(Items.WHITE_BED).b(Items.ORANGE_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "orange_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.ORANGE_CARPET, 3).a('#', (IMaterial) Blocks.ORANGE_WOOL).a("##").b("carpet").a("has_orange_wool", (CriterionInstance) this.a((IMaterial) Blocks.ORANGE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.ORANGE_CONCRETE_POWDER, 8).b(Items.ORANGE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.ORANGE_DYE).b(Blocks.ORANGE_TULIP).a("orange_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.ORANGE_TULIP)).a(consumer, "orange_dye_from_orange_tulip");
        DebugReportRecipeShapeless.a((IMaterial) Items.ORANGE_DYE, 2).b(Items.ROSE_RED).b(Items.DANDELION_YELLOW).a("orange_dye").a("has_red_dye", (CriterionInstance) this.a((IMaterial) Items.ROSE_RED)).a("has_yellow_dye", (CriterionInstance) this.a((IMaterial) Items.DANDELION_YELLOW)).a(consumer, "orange_dye_from_red_yellow");
        DebugReportRecipeShaped.a(Blocks.ORANGE_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.ORANGE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ORANGE_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.ORANGE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.ORANGE_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.ORANGE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.ORANGE_WOOL).b(Items.ORANGE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.PAINTING).a('#', (IMaterial) Items.STICK).a('X', RecipeItemStack.a(TagsItem.WOOL)).a("###").a("#X#").a("###").a("has_wool", (CriterionInstance) this.a(TagsItem.WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Items.PAPER, 3).a('#', (IMaterial) Blocks.SUGAR_CANE).a("###").a("has_reeds", (CriterionInstance) this.a((IMaterial) Blocks.SUGAR_CANE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_PILLAR, 2).a('#', (IMaterial) Blocks.QUARTZ_BLOCK).a("#").a("#").a("has_chiseled_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.PACKED_ICE).b(Blocks.ICE, 9).a("has_at_least_9_ice", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Blocks.ICE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.PINK_BANNER).a('#', (IMaterial) Blocks.PINK_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_pink_wool", (CriterionInstance) this.a((IMaterial) Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.PINK_BED).a('#', (IMaterial) Blocks.PINK_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_pink_wool", (CriterionInstance) this.a((IMaterial) Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PINK_BED).b(Items.WHITE_BED).b(Items.PINK_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "pink_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.PINK_CARPET, 3).a('#', (IMaterial) Blocks.PINK_WOOL).a("##").b("carpet").a("has_pink_wool", (CriterionInstance) this.a((IMaterial) Blocks.PINK_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.PINK_CONCRETE_POWDER, 8).b(Items.PINK_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PINK_DYE, 2).b(Blocks.PEONY).a("pink_dye").a("has_double_plant", (CriterionInstance) this.a((IMaterial) Blocks.PEONY)).a(consumer, "pink_dye_from_peony");
        DebugReportRecipeShapeless.a((IMaterial) Items.PINK_DYE).b(Blocks.PINK_TULIP).a("pink_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.PINK_TULIP)).a(consumer, "pink_dye_from_pink_tulip");
        DebugReportRecipeShapeless.a((IMaterial) Items.PINK_DYE, 2).b(Items.ROSE_RED).b(Items.BONE_MEAL).a("pink_dye").a("has_bonemeal", (CriterionInstance) this.a((IMaterial) Items.BONE_MEAL)).a("has_red_dye", (CriterionInstance) this.a((IMaterial) Items.ROSE_RED)).a(consumer, "pink_dye_from_red_bonemeal");
        DebugReportRecipeShaped.a(Blocks.PINK_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.PINK_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PINK_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.PINK_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PINK_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.PINK_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.PINK_WOOL).b(Items.PINK_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.PISTON).a('R', (IMaterial) Items.REDSTONE).a('#', (IMaterial) Blocks.COBBLESTONE).a('T', TagsItem.PLANKS).a('X', (IMaterial) Items.IRON_INGOT).a("TTT").a("#X#").a("#R#").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_GRANITE, 4).a('S', (IMaterial) Blocks.GRANITE).a("SS").a("SS").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.GRANITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_DIORITE, 4).a('S', (IMaterial) Blocks.DIORITE).a("SS").a("SS").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.DIORITE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.POLISHED_ANDESITE, 4).a('S', (IMaterial) Blocks.ANDESITE).a("SS").a("SS").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.ANDESITE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.PRISMARINE).a('S', (IMaterial) Items.PRISMARINE_SHARD).a("SS").a("SS").a("has_prismarine_shard", (CriterionInstance) this.a((IMaterial) Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.PRISMARINE_BRICKS).a('S', (IMaterial) Items.PRISMARINE_SHARD).a("SSS").a("SSS").a("SSS").a("has_prismarine_shard", (CriterionInstance) this.a((IMaterial) Items.PRISMARINE_SHARD)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_SLAB, 6).a('#', (IMaterial) Blocks.PRISMARINE).a("###").a("has_prismarine", (CriterionInstance) this.a((IMaterial) Blocks.PRISMARINE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PRISMARINE_BRICK_SLAB, 6).a('#', (IMaterial) Blocks.PRISMARINE_BRICKS).a("###").a("has_prismarine_bricks", (CriterionInstance) this.a((IMaterial) Blocks.PRISMARINE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.DARK_PRISMARINE_SLAB, 6).a('#', (IMaterial) Blocks.DARK_PRISMARINE).a("###").a("has_dark_prismarine", (CriterionInstance) this.a((IMaterial) Blocks.DARK_PRISMARINE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PUMPKIN_PIE).b(Blocks.PUMPKIN).b(Items.SUGAR).b(Items.EGG).a("has_carved_pumpkin", (CriterionInstance) this.a((IMaterial) Blocks.CARVED_PUMPKIN)).a("has_pumpkin", (CriterionInstance) this.a((IMaterial) Blocks.PUMPKIN)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PUMPKIN_SEEDS, 4).b(Blocks.PUMPKIN).a("has_pumpkin", (CriterionInstance) this.a((IMaterial) Blocks.PUMPKIN)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.PURPLE_BANNER).a('#', (IMaterial) Blocks.PURPLE_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_purple_wool", (CriterionInstance) this.a((IMaterial) Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.PURPLE_BED).a('#', (IMaterial) Blocks.PURPLE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_purple_wool", (CriterionInstance) this.a((IMaterial) Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PURPLE_BED).b(Items.WHITE_BED).b(Items.PURPLE_DYE).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "purple_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.PURPLE_CARPET, 3).a('#', (IMaterial) Blocks.PURPLE_WOOL).a("##").b("carpet").a("has_purple_wool", (CriterionInstance) this.a((IMaterial) Blocks.PURPLE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.PURPLE_CONCRETE_POWDER, 8).b(Items.PURPLE_DYE).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.PURPLE_DYE, 2).b(Items.LAPIS_LAZULI).b(Items.ROSE_RED).a("has_lapis", (CriterionInstance) this.a((IMaterial) Items.LAPIS_LAZULI)).a("has_red_dye", (CriterionInstance) this.a((IMaterial) Items.ROSE_RED)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SHULKER_BOX).a('#', (IMaterial) Blocks.CHEST).a('-', (IMaterial) Items.SHULKER_SHELL).a("-").a("#").a("-").a("has_shulker_shell", (CriterionInstance) this.a((IMaterial) Items.SHULKER_SHELL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.PURPLE_DYE).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.PURPLE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPLE_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.PURPLE_DYE).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.PURPLE_WOOL).b(Items.PURPLE_DYE).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_BLOCK, 4).a('F', (IMaterial) Items.POPPED_CHORUS_FRUIT).a("FF").a("FF").a("has_chorus_fruit_popped", (CriterionInstance) this.a((IMaterial) Items.POPPED_CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.PURPUR_PILLAR).a('#', (IMaterial) Blocks.PURPUR_SLAB).a("#").a("#").a("has_purpur_block", (CriterionInstance) this.a((IMaterial) Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_SLAB, 6).a('#', RecipeItemStack.a(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).a("###").a("has_purpur_block", (CriterionInstance) this.a((IMaterial) Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.PURPUR_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).a("#  ").a("## ").a("###").a("has_purpur_block", (CriterionInstance) this.a((IMaterial) Blocks.PURPUR_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.QUARTZ_BLOCK).a('#', (IMaterial) Items.QUARTZ).a("##").a("##").a("has_quartz", (CriterionInstance) this.a((IMaterial) Items.QUARTZ)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_SLAB, 6).a('#', RecipeItemStack.a(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).a("###").a("has_chiseled_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.QUARTZ_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).a("#  ").a("## ").a("###").a("has_chiseled_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_QUARTZ_BLOCK)).a("has_quartz_block", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_BLOCK)).a("has_quartz_pillar", (CriterionInstance) this.a((IMaterial) Blocks.QUARTZ_PILLAR)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.RABBIT_STEW).a('P', (IMaterial) Items.BAKED_POTATO).a('R', (IMaterial) Items.COOKED_RABBIT).a('B', (IMaterial) Items.BOWL).a('C', (IMaterial) Items.CARROT).a('M', (IMaterial) Blocks.BROWN_MUSHROOM).a(" R ").a("CPM").a(" B ").b("rabbit_stew").a("has_cooked_rabbit", (CriterionInstance) this.a((IMaterial) Items.COOKED_RABBIT)).a(consumer, "rabbit_stew_from_brown_mushroom");
        DebugReportRecipeShaped.a((IMaterial) Items.RABBIT_STEW).a('P', (IMaterial) Items.BAKED_POTATO).a('R', (IMaterial) Items.COOKED_RABBIT).a('B', (IMaterial) Items.BOWL).a('C', (IMaterial) Items.CARROT).a('D', (IMaterial) Blocks.RED_MUSHROOM).a(" R ").a("CPD").a(" B ").b("rabbit_stew").a("has_cooked_rabbit", (CriterionInstance) this.a((IMaterial) Items.COOKED_RABBIT)).a(consumer, "rabbit_stew_from_red_mushroom");
        DebugReportRecipeShaped.a(Blocks.RAIL, 16).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.IRON_INGOT).a("X X").a("X#X").a("X X").a("has_minecart", (CriterionInstance) this.a((IMaterial) Items.MINECART)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.REDSTONE, 9).b(Blocks.REDSTONE_BLOCK).a("has_redstone_block", (CriterionInstance) this.a((IMaterial) Blocks.REDSTONE_BLOCK)).a("has_at_least_9_redstone", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.REDSTONE_BLOCK).a('#', (IMaterial) Items.REDSTONE).a("###").a("###").a("###").a("has_at_least_9_redstone", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.REDSTONE_LAMP).a('R', (IMaterial) Items.REDSTONE).a('G', (IMaterial) Blocks.GLOWSTONE).a(" R ").a("RGR").a(" R ").a("has_glowstone", (CriterionInstance) this.a((IMaterial) Blocks.GLOWSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.REDSTONE_TORCH).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Items.REDSTONE).a("X").a("#").a("has_redstone", (CriterionInstance) this.a((IMaterial) Items.REDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.RED_BANNER).a('#', (IMaterial) Blocks.RED_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_red_wool", (CriterionInstance) this.a((IMaterial) Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.RED_BED).a('#', (IMaterial) Blocks.RED_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_red_wool", (CriterionInstance) this.a((IMaterial) Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.RED_BED).b(Items.WHITE_BED).b(Items.ROSE_RED).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "red_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.RED_CARPET, 3).a('#', (IMaterial) Blocks.RED_WOOL).a("##").b("carpet").a("has_red_wool", (CriterionInstance) this.a((IMaterial) Blocks.RED_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.RED_CONCRETE_POWDER, 8).b(Items.ROSE_RED).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.ROSE_RED).b(Items.BEETROOT).a("red_dye").a("has_beetroot", (CriterionInstance) this.a((IMaterial) Items.BEETROOT)).a(consumer, "red_dye_from_beetroot");
        DebugReportRecipeShapeless.a((IMaterial) Items.ROSE_RED).b(Blocks.POPPY).a("red_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.POPPY)).a(consumer, "red_dye_from_poppy");
        DebugReportRecipeShapeless.a((IMaterial) Items.ROSE_RED, 2).b(Blocks.ROSE_BUSH).a("red_dye").a("has_double_plant", (CriterionInstance) this.a((IMaterial) Blocks.ROSE_BUSH)).a(consumer, "red_dye_from_rose_bush");
        DebugReportRecipeShapeless.a((IMaterial) Items.ROSE_RED).b(Blocks.RED_TULIP).a("red_dye").a("has_red_flower", (CriterionInstance) this.a((IMaterial) Blocks.RED_TULIP)).a(consumer, "red_dye_from_tulip");
        DebugReportRecipeShaped.a((IMaterial) Blocks.RED_NETHER_BRICKS).a('W', (IMaterial) Items.NETHER_WART).a('N', (IMaterial) Items.NETHER_BRICK).a("NW").a("WN").a("has_nether_wart", (CriterionInstance) this.a((IMaterial) Items.NETHER_WART)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.RED_SANDSTONE).a('#', (IMaterial) Blocks.RED_SAND).a("##").a("##").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.RED_SAND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_SANDSTONE_SLAB, 6).a('#', RecipeItemStack.a(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).a("###").a("has_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_SANDSTONE_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).a("#  ").a("## ").a("###").a("has_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.RED_SANDSTONE)).a("has_chiseled_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_RED_SANDSTONE)).a("has_cut_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CUT_RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.ROSE_RED).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.RED_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.RED_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.ROSE_RED).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.RED_WOOL).b(Items.ROSE_RED).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.REPEATER).a('#', (IMaterial) Blocks.REDSTONE_TORCH).a('X', (IMaterial) Items.REDSTONE).a('I', (IMaterial) Blocks.STONE).a("#X#").a("III").a("has_redstone_torch", (CriterionInstance) this.a((IMaterial) Blocks.REDSTONE_TORCH)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SANDSTONE).a('#', (IMaterial) Blocks.SAND).a("##").a("##").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SANDSTONE_SLAB, 6).a('#', RecipeItemStack.a(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).a("###").a("has_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.SANDSTONE)).a("has_chiseled_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_SANDSTONE)).a("has_cut_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CUT_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SANDSTONE_STAIRS, 4).a('#', RecipeItemStack.a(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).a("#  ").a("## ").a("###").a("has_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.SANDSTONE)).a("has_chiseled_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CHISELED_SANDSTONE)).a("has_cut_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.CUT_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SEA_LANTERN).a('S', (IMaterial) Items.PRISMARINE_SHARD).a('C', (IMaterial) Items.PRISMARINE_CRYSTALS).a("SCS").a("CCC").a("SCS").a("has_prismarine_crystals", (CriterionInstance) this.a((IMaterial) Items.PRISMARINE_CRYSTALS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.SHEARS).a('#', (IMaterial) Items.IRON_INGOT).a(" #").a("# ").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.SHIELD).a('W', TagsItem.PLANKS).a('o', (IMaterial) Items.IRON_INGOT).a("WoW").a("WWW").a(" W ").a("has_iron_ingot", (CriterionInstance) this.a((IMaterial) Items.IRON_INGOT)).a(consumer);
        DebugReportRecipeShaped.a(Items.SIGN, 3).a('#', TagsItem.PLANKS).a('X', (IMaterial) Items.STICK).a("###").a("###").a(" X ").a("has_planks", (CriterionInstance) this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SLIME_BLOCK).a('#', (IMaterial) Items.SLIME_BALL).a("###").a("###").a("###").a("has_at_least_9_slime_ball", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.SLIME_BALL, 9).b(Blocks.SLIME_BLOCK).a("has_at_least_9_slime_ball", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.SLIME_BALL)).a("has_slime", (CriterionInstance) this.a((IMaterial) Blocks.SLIME_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CUT_RED_SANDSTONE, 4).a('#', (IMaterial) Blocks.RED_SANDSTONE).a("##").a("##").a("has_red_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.RED_SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.CUT_SANDSTONE, 4).a('#', (IMaterial) Blocks.SANDSTONE).a("##").a("##").a("has_sandstone", (CriterionInstance) this.a((IMaterial) Blocks.SANDSTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SNOW_BLOCK).a('#', (IMaterial) Items.SNOWBALL).a("##").a("##").a("has_snowball", (CriterionInstance) this.a((IMaterial) Items.SNOWBALL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SNOW, 6).a('#', (IMaterial) Blocks.SNOW_BLOCK).a("###").a("has_snowball", (CriterionInstance) this.a((IMaterial) Items.SNOWBALL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.GLISTERING_MELON_SLICE).a('#', (IMaterial) Items.GOLD_NUGGET).a('X', (IMaterial) Items.MELON_SLICE).a("###").a("#X#").a("###").a("has_melon", (CriterionInstance) this.a((IMaterial) Items.MELON_SLICE)).a(consumer);
        DebugReportRecipeShaped.a(Items.SPECTRAL_ARROW, 2).a('#', (IMaterial) Items.GLOWSTONE_DUST).a('X', (IMaterial) Items.ARROW).a(" # ").a("#X#").a(" # ").a("has_glowstone_dust", (CriterionInstance) this.a((IMaterial) Items.GLOWSTONE_DUST)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_WOOD, 3).a('#', (IMaterial) Blocks.SPRUCE_LOG).a("##").a("##").b("bark").a("has_log", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_LOG)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.SPRUCE_BOAT).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("# #").a("###").b("boat").a("in_water", (CriterionInstance) this.a(Blocks.WATER)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.SPRUCE_BUTTON).b(Blocks.SPRUCE_PLANKS).a("wooden_button").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_DOOR, 3).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_FENCE, 3).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.SPRUCE_PLANKS).a("W#W").a("W#W").b("wooden_fence").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SPRUCE_FENCE_GATE).a('#', (IMaterial) Items.STICK).a('W', (IMaterial) Blocks.SPRUCE_PLANKS).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.SPRUCE_PLANKS, 4).a(TagsItem.SPRUCE_LOGS).a("planks").a("has_log", (CriterionInstance) this.a(TagsItem.SPRUCE_LOGS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.SPRUCE_PRESSURE_PLATE).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("##").b("wooden_pressure_plate").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_SLAB, 6).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("###").b("wooden_slab").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_STAIRS, 4).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.SPRUCE_TRAPDOOR, 2).a('#', (IMaterial) Blocks.SPRUCE_PLANKS).a("###").a("###").b("wooden_trapdoor").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.SPRUCE_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a(Items.STICK, 4).a('#', TagsItem.PLANKS).a("#").a("#").a("has_planks", (CriterionInstance) this.a(TagsItem.PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.STICKY_PISTON).a('P', (IMaterial) Blocks.PISTON).a('S', (IMaterial) Items.SLIME_BALL).a("S").a("P").a("has_slime_ball", (CriterionInstance) this.a((IMaterial) Items.SLIME_BALL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICKS, 4).a('#', (IMaterial) Blocks.STONE).a("##").a("##").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.STONE_AXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Blocks.COBBLESTONE).a("XX").a("X#").a(" #").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICK_SLAB, 6).a('#', RecipeItemStack.a(TagsItem.STONE_BRICKS)).a("###").a("has_stone_bricks", (CriterionInstance) this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_BRICK_STAIRS, 4).a('#', RecipeItemStack.a(TagsItem.STONE_BRICKS)).a("#  ").a("## ").a("###").a("has_stone_bricks", (CriterionInstance) this.a(TagsItem.STONE_BRICKS)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.STONE_BUTTON).b(Blocks.STONE).a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.STONE_HOE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Blocks.COBBLESTONE).a("XX").a(" #").a(" #").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.STONE_PICKAXE).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Blocks.COBBLESTONE).a("XXX").a(" # ").a(" # ").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.STONE_PRESSURE_PLATE).a('#', (IMaterial) Blocks.STONE).a("##").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.STONE_SHOVEL).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Blocks.COBBLESTONE).a("X").a("#").a("#").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.STONE_SLAB, 6).a('#', (IMaterial) Blocks.STONE).a("###").a("has_stone", (CriterionInstance) this.a((IMaterial) Blocks.STONE)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.COBBLESTONE_STAIRS, 4).a('#', (IMaterial) Blocks.COBBLESTONE).a("#  ").a("## ").a("###").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.STONE_SWORD).a('#', (IMaterial) Items.STICK).a('X', (IMaterial) Blocks.COBBLESTONE).a("X").a("X").a("#").a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.WHITE_WOOL).a('#', (IMaterial) Items.STRING).a("##").a("##").a("has_string", (CriterionInstance) this.a((IMaterial) Items.STRING)).a(consumer, "white_wool_from_string");
        DebugReportRecipeShapeless.a((IMaterial) Items.SUGAR).b(Blocks.SUGAR_CANE).a("has_reeds", (CriterionInstance) this.a((IMaterial) Blocks.SUGAR_CANE)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.TNT).a('#', RecipeItemStack.a(Blocks.SAND, Blocks.RED_SAND)).a('X', (IMaterial) Items.GUNPOWDER).a("X#X").a("#X#").a("X#X").a("has_gunpowder", (CriterionInstance) this.a((IMaterial) Items.GUNPOWDER)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.TNT_MINECART).a('A', (IMaterial) Blocks.TNT).a('B', (IMaterial) Items.MINECART).a("A").a("B").a("has_minecart", (CriterionInstance) this.a((IMaterial) Items.MINECART)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.TORCH, 4).a('#', (IMaterial) Items.STICK).a('X', RecipeItemStack.a(Items.COAL, Items.CHARCOAL)).a("X").a("#").a("has_stone_pickaxe", (CriterionInstance) this.a((IMaterial) Items.STONE_PICKAXE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.TRAPPED_CHEST).b(Blocks.CHEST).b(Blocks.TRIPWIRE_HOOK).a("has_tripwire_hook", (CriterionInstance) this.a((IMaterial) Blocks.TRIPWIRE_HOOK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.TRIPWIRE_HOOK, 2).a('#', TagsItem.PLANKS).a('S', (IMaterial) Items.STICK).a('I', (IMaterial) Items.IRON_INGOT).a("I").a("S").a("#").a("has_string", (CriterionInstance) this.a((IMaterial) Items.STRING)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.TURTLE_HELMET).a('X', (IMaterial) Items.SCUTE).a("XXX").a("X X").a("has_scute", (CriterionInstance) this.a((IMaterial) Items.SCUTE)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.WHEAT, 9).b(Blocks.HAY_BLOCK).a("has_at_least_9_wheat", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.WHEAT)).a("has_hay_block", (CriterionInstance) this.a((IMaterial) Blocks.HAY_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WHITE_BANNER).a('#', (IMaterial) Blocks.WHITE_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WHITE_BED).a('#', (IMaterial) Blocks.WHITE_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_CARPET, 3).a('#', (IMaterial) Blocks.WHITE_WOOL).a("##").b("carpet").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.WHITE_CONCRETE_POWDER, 8).b(Items.BONE_MEAL).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.BONE_MEAL).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.WHITE_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.WHITE_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.BONE_MEAL).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WOODEN_AXE).a('#', (IMaterial) Items.STICK).a('X', TagsItem.PLANKS).a("XX").a("X#").a(" #").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.OAK_DOOR, 3).a('#', (IMaterial) Blocks.OAK_PLANKS).a("##").a("##").a("##").b("wooden_door").a("has_planks", (CriterionInstance) this.a((IMaterial) Blocks.OAK_PLANKS)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WOODEN_HOE).a('#', (IMaterial) Items.STICK).a('X', TagsItem.PLANKS).a("XX").a(" #").a(" #").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WOODEN_PICKAXE).a('#', (IMaterial) Items.STICK).a('X', TagsItem.PLANKS).a("XXX").a(" # ").a(" # ").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WOODEN_SHOVEL).a('#', (IMaterial) Items.STICK).a('X', TagsItem.PLANKS).a("X").a("#").a("#").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.WOODEN_SWORD).a('#', (IMaterial) Items.STICK).a('X', TagsItem.PLANKS).a("X").a("X").a("#").a("has_stick", (CriterionInstance) this.a((IMaterial) Items.STICK)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.WRITABLE_BOOK).b(Items.BOOK).b(Items.INK_SAC).b(Items.FEATHER).a("has_book", (CriterionInstance) this.a((IMaterial) Items.BOOK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.YELLOW_BANNER).a('#', (IMaterial) Blocks.YELLOW_WOOL).a('|', (IMaterial) Items.STICK).a("###").a("###").a(" | ").b("banner").a("has_yellow_wool", (CriterionInstance) this.a((IMaterial) Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Items.YELLOW_BED).a('#', (IMaterial) Blocks.YELLOW_WOOL).a('X', TagsItem.PLANKS).a("###").a("XXX").b("bed").a("has_yellow_wool", (CriterionInstance) this.a((IMaterial) Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.YELLOW_BED).b(Items.WHITE_BED).b(Items.DANDELION_YELLOW).a("dyed_bed").a("has_bed", (CriterionInstance) this.a((IMaterial) Items.WHITE_BED)).a(consumer, "yellow_bed_from_white_bed");
        DebugReportRecipeShaped.a(Blocks.YELLOW_CARPET, 3).a('#', (IMaterial) Blocks.YELLOW_WOOL).a("##").b("carpet").a("has_yellow_wool", (CriterionInstance) this.a((IMaterial) Blocks.YELLOW_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.YELLOW_CONCRETE_POWDER, 8).b(Items.DANDELION_YELLOW).b(Blocks.SAND, 4).b(Blocks.GRAVEL, 4).a("concrete_powder").a("has_sand", (CriterionInstance) this.a((IMaterial) Blocks.SAND)).a("has_gravel", (CriterionInstance) this.a((IMaterial) Blocks.GRAVEL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.DANDELION_YELLOW).b(Blocks.DANDELION).a("yellow_dye").a("has_yellow_flower", (CriterionInstance) this.a((IMaterial) Blocks.DANDELION)).a(consumer, "yellow_dye_from_dandelion");
        DebugReportRecipeShapeless.a((IMaterial) Items.DANDELION_YELLOW, 2).b(Blocks.SUNFLOWER).a("yellow_dye").a("has_double_plant", (CriterionInstance) this.a((IMaterial) Blocks.SUNFLOWER)).a(consumer, "yellow_dye_from_sunflower");
        DebugReportRecipeShaped.a(Blocks.YELLOW_STAINED_GLASS, 8).a('#', (IMaterial) Blocks.GLASS).a('X', (IMaterial) Items.DANDELION_YELLOW).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.YELLOW_STAINED_GLASS_PANE, 16).a('#', (IMaterial) Blocks.YELLOW_STAINED_GLASS).a("###").a("###").b("stained_glass_pane").a("has_glass", (CriterionInstance) this.a((IMaterial) Blocks.GLASS)).a(consumer);
        DebugReportRecipeShaped.a(Blocks.YELLOW_TERRACOTTA, 8).a('#', (IMaterial) Blocks.TERRACOTTA).a('X', (IMaterial) Items.DANDELION_YELLOW).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.TERRACOTTA)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.YELLOW_WOOL).b(Items.DANDELION_YELLOW).b(Blocks.WHITE_WOOL).a("wool").a("has_white_wool", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_WOOL)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Items.DRIED_KELP, 9).b(Blocks.DRIED_KELP_BLOCK).a("has_at_least_9_dried_kelp", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.DRIED_KELP)).a("has_dried_kelp_block", (CriterionInstance) this.a((IMaterial) Blocks.DRIED_KELP_BLOCK)).a(consumer);
        DebugReportRecipeShapeless.a((IMaterial) Blocks.DRIED_KELP_BLOCK).b(Items.DRIED_KELP, 9).a("has_at_least_9_dried_kelp", (CriterionInstance) this.a(CriterionConditionValue.d.b(9), Items.DRIED_KELP)).a("has_dried_kelp_block", (CriterionInstance) this.a((IMaterial) Blocks.DRIED_KELP_BLOCK)).a(consumer);
        DebugReportRecipeShaped.a((IMaterial) Blocks.CONDUIT).a('#', (IMaterial) Items.NAUTILUS_SHELL).a('X', (IMaterial) Items.HEART_OF_THE_SEA).a("###").a("#X#").a("###").a("has_nautilus_core", (CriterionInstance) this.a((IMaterial) Items.HEART_OF_THE_SEA)).a("has_nautilus_shell", (CriterionInstance) this.a((IMaterial) Items.NAUTILUS_SHELL)).a(consumer);
        DebugReportRecipeSpecial.a(RecipeSerializers.c).a(consumer, "armor_dye");
        DebugReportRecipeSpecial.a(RecipeSerializers.m).a(consumer, "banner_add_pattern");
        DebugReportRecipeSpecial.a(RecipeSerializers.l).a(consumer, "banner_duplicate");
        DebugReportRecipeSpecial.a(RecipeSerializers.d).a(consumer, "book_cloning");
        DebugReportRecipeSpecial.a(RecipeSerializers.g).a(consumer, "firework_rocket");
        DebugReportRecipeSpecial.a(RecipeSerializers.h).a(consumer, "firework_star");
        DebugReportRecipeSpecial.a(RecipeSerializers.i).a(consumer, "firework_star_fade");
        DebugReportRecipeSpecial.a(RecipeSerializers.e).a(consumer, "map_cloning");
        DebugReportRecipeSpecial.a(RecipeSerializers.f).a(consumer, "map_extending");
        DebugReportRecipeSpecial.a(RecipeSerializers.j).a(consumer, "repair_item");
        DebugReportRecipeSpecial.a(RecipeSerializers.n).a(consumer, "shield_decoration");
        DebugReportRecipeSpecial.a(RecipeSerializers.o).a(consumer, "shulker_box_coloring");
        DebugReportRecipeSpecial.a(RecipeSerializers.k).a(consumer, "tipped_arrow");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.POTATO), Items.BAKED_POTATO, 0.35F, 200).a("has_potato", (CriterionInstance) this.a((IMaterial) Items.POTATO)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CLAY_BALL), Items.BRICK, 0.3F, 200).a("has_clay_ball", (CriterionInstance) this.a((IMaterial) Items.CLAY_BALL)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(TagsItem.LOGS), Items.CHARCOAL, 0.15F, 200).a("has_log", (CriterionInstance) this.a(TagsItem.LOGS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT, 0.1F, 200).a("has_chorus_fruit", (CriterionInstance) this.a((IMaterial) Items.CHORUS_FRUIT)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.COAL_ORE.getItem()), Items.COAL, 0.1F, 200).a("has_coal_ore", (CriterionInstance) this.a((IMaterial) Blocks.COAL_ORE)).a(consumer, "coal_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.BEEF), Items.COOKED_BEEF, 0.35F, 200).a("has_beef", (CriterionInstance) this.a((IMaterial) Items.BEEF)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, 200).a("has_chicken", (CriterionInstance) this.a((IMaterial) Items.CHICKEN)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.COD), Items.COOKED_COD, 0.35F, 200).a("has_cod", (CriterionInstance) this.a((IMaterial) Items.COD)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.KELP), Items.DRIED_KELP, 0.1F, 200).a("has_kelp", (CriterionInstance) this.a((IMaterial) Blocks.KELP)).a(consumer, "dried_kelp_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.SALMON), Items.COOKED_SALMON, 0.35F, 200).a("has_salmon", (CriterionInstance) this.a((IMaterial) Items.SALMON)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, 200).a("has_mutton", (CriterionInstance) this.a((IMaterial) Items.MUTTON)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, 200).a("has_porkchop", (CriterionInstance) this.a((IMaterial) Items.PORKCHOP)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, 200).a("has_rabbit", (CriterionInstance) this.a((IMaterial) Items.RABBIT)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.DIAMOND_ORE.getItem()), Items.DIAMOND, 1.0F, 200).a("has_diamond_ore", (CriterionInstance) this.a((IMaterial) Blocks.DIAMOND_ORE)).a(consumer, "diamond_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LAPIS_ORE.getItem()), Items.LAPIS_LAZULI, 0.2F, 200).a("has_lapis_ore", (CriterionInstance) this.a((IMaterial) Blocks.LAPIS_ORE)).a(consumer, "lapis_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.EMERALD_ORE.getItem()), Items.EMERALD, 1.0F, 200).a("has_emerald_ore", (CriterionInstance) this.a((IMaterial) Blocks.EMERALD_ORE)).a(consumer, "emerald_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(TagsItem.SAND), Blocks.GLASS.getItem(), 0.1F, 200).a("has_sand", (CriterionInstance) this.a(TagsItem.SAND)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GOLD_ORE.getItem()), Items.GOLD_INGOT, 1.0F, 200).a("has_gold_ore", (CriterionInstance) this.a((IMaterial) Blocks.GOLD_ORE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.SEA_PICKLE.getItem()), Items.LIME_DYE, 0.1F, 200).a("has_sea_pickle", (CriterionInstance) this.a((IMaterial) Blocks.SEA_PICKLE)).a(consumer, "lime_dye_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CACTUS.getItem()), Items.CACTUS_GREEN, 1.0F, 200).a("has_cactus", (CriterionInstance) this.a((IMaterial) Blocks.CACTUS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1F, 200).a("has_golden_pickaxe", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_PICKAXE)).a("has_golden_shovel", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_SHOVEL)).a("has_golden_axe", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_AXE)).a("has_golden_hoe", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_HOE)).a("has_golden_sword", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_SWORD)).a("has_golden_helmet", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_HELMET)).a("has_golden_chestplate", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_CHESTPLATE)).a("has_golden_leggings", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_LEGGINGS)).a("has_golden_boots", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_BOOTS)).a("has_golden_horse_armor", (CriterionInstance) this.a((IMaterial) Items.GOLDEN_HORSE_ARMOR)).a(consumer, "gold_nugget_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1F, 200).a("has_iron_pickaxe", (CriterionInstance) this.a((IMaterial) Items.IRON_PICKAXE)).a("has_iron_shovel", (CriterionInstance) this.a((IMaterial) Items.IRON_SHOVEL)).a("has_iron_axe", (CriterionInstance) this.a((IMaterial) Items.IRON_AXE)).a("has_iron_hoe", (CriterionInstance) this.a((IMaterial) Items.IRON_HOE)).a("has_iron_sword", (CriterionInstance) this.a((IMaterial) Items.IRON_SWORD)).a("has_iron_helmet", (CriterionInstance) this.a((IMaterial) Items.IRON_HELMET)).a("has_iron_chestplate", (CriterionInstance) this.a((IMaterial) Items.IRON_CHESTPLATE)).a("has_iron_leggings", (CriterionInstance) this.a((IMaterial) Items.IRON_LEGGINGS)).a("has_iron_boots", (CriterionInstance) this.a((IMaterial) Items.IRON_BOOTS)).a("has_iron_horse_armor", (CriterionInstance) this.a((IMaterial) Items.IRON_HORSE_ARMOR)).a("has_chainmail_helmet", (CriterionInstance) this.a((IMaterial) Items.CHAINMAIL_HELMET)).a("has_chainmail_chestplate", (CriterionInstance) this.a((IMaterial) Items.CHAINMAIL_CHESTPLATE)).a("has_chainmail_leggings", (CriterionInstance) this.a((IMaterial) Items.CHAINMAIL_LEGGINGS)).a("has_chainmail_boots", (CriterionInstance) this.a((IMaterial) Items.CHAINMAIL_BOOTS)).a(consumer, "iron_nugget_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.IRON_ORE.getItem()), Items.IRON_INGOT, 0.7F, 200).a("has_iron_ore", (CriterionInstance) this.a((IMaterial) Blocks.IRON_ORE.getItem())).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CLAY), Blocks.TERRACOTTA.getItem(), 0.35F, 200).a("has_clay_block", (CriterionInstance) this.a((IMaterial) Blocks.CLAY)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1F, 200).a("has_netherrack", (CriterionInstance) this.a((IMaterial) Blocks.NETHERRACK)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 200).a("has_nether_quartz_ore", (CriterionInstance) this.a((IMaterial) Blocks.NETHER_QUARTZ_ORE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7F, 200).a("has_redstone_ore", (CriterionInstance) this.a((IMaterial) Blocks.REDSTONE_ORE)).a(consumer, "redstone_from_smelting");
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.WET_SPONGE), Blocks.SPONGE.getItem(), 0.15F, 200).a("has_wet_sponge", (CriterionInstance) this.a((IMaterial) Blocks.WET_SPONGE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.COBBLESTONE), Blocks.STONE.getItem(), 0.1F, 200).a("has_cobblestone", (CriterionInstance) this.a((IMaterial) Blocks.COBBLESTONE)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.getItem(), 0.1F, 200).a("has_stone_bricks", (CriterionInstance) this.a((IMaterial) Blocks.STONE_BRICKS)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_black_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.BLACK_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_blue_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.BLUE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_brown_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.BROWN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_cyan_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.CYAN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_gray_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.GRAY_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_green_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.GREEN_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_light_blue_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_BLUE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_light_gray_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.LIGHT_GRAY_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_lime_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.LIME_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_magenta_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.MAGENTA_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_orange_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.ORANGE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_pink_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.PINK_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_purple_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.PURPLE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_red_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.RED_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_white_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.WHITE_TERRACOTTA)).a(consumer);
        DebugReportRecipeFurnace.a(RecipeItemStack.a(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA.getItem(), 0.1F, 200).a("has_yellow_terracotta", (CriterionInstance) this.a((IMaterial) Blocks.YELLOW_TERRACOTTA)).a(consumer);
    }

    private CriterionTriggerEnterBlock.b a(Block block) {
        return new CriterionTriggerEnterBlock.b(block, (Map) null);
    }

    private CriterionTriggerInventoryChanged.b a(CriterionConditionValue.d criterionconditionvalue_d, IMaterial imaterial) {
        return this.a(CriterionConditionItem.a.a().a(imaterial).a(criterionconditionvalue_d).b());
    }

    private CriterionTriggerInventoryChanged.b a(IMaterial imaterial) {
        return this.a(CriterionConditionItem.a.a().a(imaterial).b());
    }

    private CriterionTriggerInventoryChanged.b a(Tag<Item> tag) {
        return this.a(CriterionConditionItem.a.a().a(tag).b());
    }

    private CriterionTriggerInventoryChanged.b a(CriterionConditionItem... acriterionconditionitem) {
        return new CriterionTriggerInventoryChanged.b(CriterionConditionValue.d.e, CriterionConditionValue.d.e, CriterionConditionValue.d.e, acriterionconditionitem);
    }

    public String a() {
        return "Recipes";
    }
}

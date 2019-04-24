package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class DebugReportLootBlock implements Consumer<BiConsumer<MinecraftKey, LootTable.a>> {

    private static final LootItemCondition.a a = LootItemConditionMatchTool.a(CriterionConditionItem.a.a().a(new CriterionConditionEnchantments(Enchantments.SILK_TOUCH, CriterionConditionValue.IntegerRange.b(1))));
    private static final LootItemCondition.a b = DebugReportLootBlock.a.a();
    private static final LootItemCondition.a c = LootItemConditionMatchTool.a(CriterionConditionItem.a.a().a((IMaterial) Items.SHEARS));
    private static final LootItemCondition.a d = DebugReportLootBlock.c.a(DebugReportLootBlock.a);
    private static final LootItemCondition.a e = DebugReportLootBlock.d.a();
    private static final Set<Item> f = (Set) Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IMaterial::getItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] g = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] h = new float[] { 0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    private final Map<MinecraftKey, LootTable.a> i = Maps.newHashMap();

    public DebugReportLootBlock() {}

    private static <T> T a(IMaterial imaterial, LootItemFunctionUser<T> lootitemfunctionuser) {
        return !DebugReportLootBlock.f.contains(imaterial.getItem()) ? lootitemfunctionuser.b(LootItemFunctionExplosionDecay.b()) : lootitemfunctionuser.c();
    }

    private static <T> T a(IMaterial imaterial, LootItemConditionUser<T> lootitemconditionuser) {
        return !DebugReportLootBlock.f.contains(imaterial.getItem()) ? lootitemconditionuser.b(LootItemConditionSurvivesExplosion.b()) : lootitemconditionuser.c();
    }

    private static LootTable.a a(IMaterial imaterial) {
        return LootTable.b().a((LootSelector.a) a(imaterial, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a(imaterial))));
    }

    private static LootTable.a a(Block block, LootItemCondition.a lootitemcondition_a, LootEntryAbstract.a<?> lootentryabstract_a) {
        return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) ((LootSelectorEntry.a) LootItem.a((IMaterial) block).b(lootitemcondition_a)).a(lootentryabstract_a)));
    }

    private static LootTable.a a(Block block, LootEntryAbstract.a<?> lootentryabstract_a) {
        return a(block, DebugReportLootBlock.a, lootentryabstract_a);
    }

    private static LootTable.a b(Block block, LootEntryAbstract.a<?> lootentryabstract_a) {
        return a(block, DebugReportLootBlock.c, lootentryabstract_a);
    }

    private static LootTable.a c(Block block, LootEntryAbstract.a<?> lootentryabstract_a) {
        return a(block, DebugReportLootBlock.d, lootentryabstract_a);
    }

    private static LootTable.a b(Block block, IMaterial imaterial) {
        return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a(imaterial)));
    }

    private static LootTable.a a(IMaterial imaterial, LootValue lootvalue) {
        return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) a(imaterial, (LootItemFunctionUser) LootItem.a(imaterial).b((LootItemFunction.a) LootItemFunctionSetCount.a(lootvalue)))));
    }

    private static LootTable.a a(Block block, IMaterial imaterial, LootValue lootvalue) {
        return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a(imaterial).b((LootItemFunction.a) LootItemFunctionSetCount.a(lootvalue))));
    }

    private static LootTable.a b(IMaterial imaterial) {
        return LootTable.b().a(LootSelector.a().b(DebugReportLootBlock.a).a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a(imaterial)));
    }

    private static LootTable.a c(IMaterial imaterial) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) Blocks.FLOWER_POT, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Blocks.FLOWER_POT)))).a((LootSelector.a) a(imaterial, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a(imaterial))));
    }

    private static LootTable.a d(Block block) {
        return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(2)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStepAbstract.a, BlockPropertySlabType.DOUBLE))))));
    }

    private static <T extends Comparable<T>> LootTable.a a(Block block, IBlockState<T> iblockstate, T t0) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a(LootItem.a((IMaterial) block).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(iblockstate, t0)))));
    }

    private static LootTable.a e(Block block) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionCopyName.a(LootItemFunctionCopyName.Source.BLOCK_ENTITY)))));
    }

    private static LootTable.a f(Block block) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionCopyName.a(LootItemFunctionCopyName.Source.BLOCK_ENTITY)).b((LootItemFunction.a) LootItemFunctionCopyNBT.a(LootItemFunctionCopyNBT.Source.BLOCK_ENTITY).a("Lock", "BlockEntityTag.Lock").a("LootTable", "BlockEntityTag.LootTable").a("LootTableSeed", "BlockEntityTag.LootTableSeed")).b((LootItemFunction.a) LootItemFunctionSetContents.b().a(LootSelectorDynamic.a(BlockShulkerBox.b))))));
    }

    private static LootTable.a g(Block block) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionCopyName.a(LootItemFunctionCopyName.Source.BLOCK_ENTITY)).b((LootItemFunction.a) LootItemFunctionCopyNBT.a(LootItemFunctionCopyNBT.Source.BLOCK_ENTITY).a("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.a a(Block block, Item item) {
        return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) item).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS))));
    }

    private static LootTable.a c(Block block, IMaterial imaterial) {
        return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a(imaterial).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(-6.0F, 2.0F))).b((LootItemFunction.a) LootItemFunctionLimitCount.a(LootIntegerLimit.a(0)))));
    }

    private static LootTable.a h(Block block) {
        return b(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.WHEAT_SEEDS).b(LootItemConditionRandomChance.a(0.125F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 2))));
    }

    private static LootTable.a b(Block block, Item item) {
        return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemFunctionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) item).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.06666667F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 0))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.13333334F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 1))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.2F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 2))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.26666668F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 3))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.33333334F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 4))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.4F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 5))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.46666667F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 6))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBinomial.a(3, 0.53333336F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockStem.AGE, 7))))));
    }

    private static LootTable.a d(IMaterial imaterial) {
        return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).b(DebugReportLootBlock.c).a((LootEntryAbstract.a) LootItem.a(imaterial)));
    }

    private static LootTable.a a(Block block, Block block1, float... afloat) {
        return c(block, ((LootSelectorEntry.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) block1))).b(LootItemConditionTableBonus.a(Enchantments.LOOT_BONUS_BLOCKS, afloat))).a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).b(DebugReportLootBlock.e).a(((LootSelectorEntry.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.STICK).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(1.0F, 2.0F))))).b(LootItemConditionTableBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    private static LootTable.a b(Block block, Block block1, float... afloat) {
        return a(block, block1, afloat).a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).b(DebugReportLootBlock.e).a(((LootSelectorEntry.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) Items.APPLE))).b(LootItemConditionTableBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    private static LootTable.a a(Block block, Item item, Item item1, LootItemCondition.a lootitemcondition_a) {
        return (LootTable.a) a((IMaterial) block, (LootItemFunctionUser) LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) ((LootSelectorEntry.a) LootItem.a((IMaterial) item).b(lootitemcondition_a)).a(LootItem.a((IMaterial) item1)))).a(LootSelector.a().b(lootitemcondition_a).a((LootEntryAbstract.a) LootItem.a((IMaterial) item1).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.5714286F, 3)))));
    }

    public static LootTable.a a() {
        return LootTable.b();
    }

    public void accept(BiConsumer<MinecraftKey, LootTable.a> biconsumer) {
        this.c(Blocks.GRANITE);
        this.c(Blocks.POLISHED_GRANITE);
        this.c(Blocks.DIORITE);
        this.c(Blocks.POLISHED_DIORITE);
        this.c(Blocks.ANDESITE);
        this.c(Blocks.POLISHED_ANDESITE);
        this.c(Blocks.DIRT);
        this.c(Blocks.COARSE_DIRT);
        this.c(Blocks.COBBLESTONE);
        this.c(Blocks.OAK_PLANKS);
        this.c(Blocks.SPRUCE_PLANKS);
        this.c(Blocks.BIRCH_PLANKS);
        this.c(Blocks.JUNGLE_PLANKS);
        this.c(Blocks.ACACIA_PLANKS);
        this.c(Blocks.DARK_OAK_PLANKS);
        this.c(Blocks.OAK_SAPLING);
        this.c(Blocks.SPRUCE_SAPLING);
        this.c(Blocks.BIRCH_SAPLING);
        this.c(Blocks.JUNGLE_SAPLING);
        this.c(Blocks.ACACIA_SAPLING);
        this.c(Blocks.DARK_OAK_SAPLING);
        this.c(Blocks.SAND);
        this.c(Blocks.RED_SAND);
        this.c(Blocks.GOLD_ORE);
        this.c(Blocks.IRON_ORE);
        this.c(Blocks.OAK_LOG);
        this.c(Blocks.SPRUCE_LOG);
        this.c(Blocks.BIRCH_LOG);
        this.c(Blocks.JUNGLE_LOG);
        this.c(Blocks.ACACIA_LOG);
        this.c(Blocks.DARK_OAK_LOG);
        this.c(Blocks.STRIPPED_SPRUCE_LOG);
        this.c(Blocks.STRIPPED_BIRCH_LOG);
        this.c(Blocks.STRIPPED_JUNGLE_LOG);
        this.c(Blocks.STRIPPED_ACACIA_LOG);
        this.c(Blocks.STRIPPED_DARK_OAK_LOG);
        this.c(Blocks.STRIPPED_OAK_LOG);
        this.c(Blocks.OAK_WOOD);
        this.c(Blocks.SPRUCE_WOOD);
        this.c(Blocks.BIRCH_WOOD);
        this.c(Blocks.JUNGLE_WOOD);
        this.c(Blocks.ACACIA_WOOD);
        this.c(Blocks.DARK_OAK_WOOD);
        this.c(Blocks.STRIPPED_OAK_WOOD);
        this.c(Blocks.STRIPPED_SPRUCE_WOOD);
        this.c(Blocks.STRIPPED_BIRCH_WOOD);
        this.c(Blocks.STRIPPED_JUNGLE_WOOD);
        this.c(Blocks.STRIPPED_ACACIA_WOOD);
        this.c(Blocks.STRIPPED_DARK_OAK_WOOD);
        this.c(Blocks.SPONGE);
        this.c(Blocks.WET_SPONGE);
        this.c(Blocks.LAPIS_BLOCK);
        this.c(Blocks.SANDSTONE);
        this.c(Blocks.CHISELED_SANDSTONE);
        this.c(Blocks.CUT_SANDSTONE);
        this.c(Blocks.NOTE_BLOCK);
        this.c(Blocks.POWERED_RAIL);
        this.c(Blocks.DETECTOR_RAIL);
        this.c(Blocks.STICKY_PISTON);
        this.c(Blocks.PISTON);
        this.c(Blocks.WHITE_WOOL);
        this.c(Blocks.ORANGE_WOOL);
        this.c(Blocks.MAGENTA_WOOL);
        this.c(Blocks.LIGHT_BLUE_WOOL);
        this.c(Blocks.YELLOW_WOOL);
        this.c(Blocks.LIME_WOOL);
        this.c(Blocks.PINK_WOOL);
        this.c(Blocks.GRAY_WOOL);
        this.c(Blocks.LIGHT_GRAY_WOOL);
        this.c(Blocks.CYAN_WOOL);
        this.c(Blocks.PURPLE_WOOL);
        this.c(Blocks.BLUE_WOOL);
        this.c(Blocks.BROWN_WOOL);
        this.c(Blocks.GREEN_WOOL);
        this.c(Blocks.RED_WOOL);
        this.c(Blocks.BLACK_WOOL);
        this.c(Blocks.DANDELION);
        this.c(Blocks.POPPY);
        this.c(Blocks.BLUE_ORCHID);
        this.c(Blocks.ALLIUM);
        this.c(Blocks.AZURE_BLUET);
        this.c(Blocks.RED_TULIP);
        this.c(Blocks.ORANGE_TULIP);
        this.c(Blocks.WHITE_TULIP);
        this.c(Blocks.PINK_TULIP);
        this.c(Blocks.OXEYE_DAISY);
        this.c(Blocks.CORNFLOWER);
        this.c(Blocks.WITHER_ROSE);
        this.c(Blocks.LILY_OF_THE_VALLEY);
        this.c(Blocks.BROWN_MUSHROOM);
        this.c(Blocks.RED_MUSHROOM);
        this.c(Blocks.GOLD_BLOCK);
        this.c(Blocks.IRON_BLOCK);
        this.c(Blocks.BRICKS);
        this.c(Blocks.MOSSY_COBBLESTONE);
        this.c(Blocks.OBSIDIAN);
        this.c(Blocks.TORCH);
        this.c(Blocks.OAK_STAIRS);
        this.c(Blocks.REDSTONE_WIRE);
        this.c(Blocks.DIAMOND_BLOCK);
        this.c(Blocks.CRAFTING_TABLE);
        this.c(Blocks.OAK_SIGN);
        this.c(Blocks.SPRUCE_SIGN);
        this.c(Blocks.BIRCH_SIGN);
        this.c(Blocks.ACACIA_SIGN);
        this.c(Blocks.JUNGLE_SIGN);
        this.c(Blocks.DARK_OAK_SIGN);
        this.c(Blocks.LADDER);
        this.c(Blocks.RAIL);
        this.c(Blocks.COBBLESTONE_STAIRS);
        this.c(Blocks.LEVER);
        this.c(Blocks.STONE_PRESSURE_PLATE);
        this.c(Blocks.OAK_PRESSURE_PLATE);
        this.c(Blocks.SPRUCE_PRESSURE_PLATE);
        this.c(Blocks.BIRCH_PRESSURE_PLATE);
        this.c(Blocks.JUNGLE_PRESSURE_PLATE);
        this.c(Blocks.ACACIA_PRESSURE_PLATE);
        this.c(Blocks.DARK_OAK_PRESSURE_PLATE);
        this.c(Blocks.REDSTONE_TORCH);
        this.c(Blocks.STONE_BUTTON);
        this.c(Blocks.CACTUS);
        this.c(Blocks.SUGAR_CANE);
        this.c(Blocks.JUKEBOX);
        this.c(Blocks.OAK_FENCE);
        this.c(Blocks.PUMPKIN);
        this.c(Blocks.NETHERRACK);
        this.c(Blocks.SOUL_SAND);
        this.c(Blocks.CARVED_PUMPKIN);
        this.c(Blocks.JACK_O_LANTERN);
        this.c(Blocks.REPEATER);
        this.c(Blocks.OAK_TRAPDOOR);
        this.c(Blocks.SPRUCE_TRAPDOOR);
        this.c(Blocks.BIRCH_TRAPDOOR);
        this.c(Blocks.JUNGLE_TRAPDOOR);
        this.c(Blocks.ACACIA_TRAPDOOR);
        this.c(Blocks.DARK_OAK_TRAPDOOR);
        this.c(Blocks.STONE_BRICKS);
        this.c(Blocks.MOSSY_STONE_BRICKS);
        this.c(Blocks.CRACKED_STONE_BRICKS);
        this.c(Blocks.CHISELED_STONE_BRICKS);
        this.c(Blocks.IRON_BARS);
        this.c(Blocks.OAK_FENCE_GATE);
        this.c(Blocks.BRICK_STAIRS);
        this.c(Blocks.STONE_BRICK_STAIRS);
        this.c(Blocks.LILY_PAD);
        this.c(Blocks.NETHER_BRICKS);
        this.c(Blocks.NETHER_BRICK_FENCE);
        this.c(Blocks.NETHER_BRICK_STAIRS);
        this.c(Blocks.CAULDRON);
        this.c(Blocks.END_STONE);
        this.c(Blocks.REDSTONE_LAMP);
        this.c(Blocks.SANDSTONE_STAIRS);
        this.c(Blocks.TRIPWIRE_HOOK);
        this.c(Blocks.EMERALD_BLOCK);
        this.c(Blocks.SPRUCE_STAIRS);
        this.c(Blocks.BIRCH_STAIRS);
        this.c(Blocks.JUNGLE_STAIRS);
        this.c(Blocks.COBBLESTONE_WALL);
        this.c(Blocks.MOSSY_COBBLESTONE_WALL);
        this.c(Blocks.FLOWER_POT);
        this.c(Blocks.OAK_BUTTON);
        this.c(Blocks.SPRUCE_BUTTON);
        this.c(Blocks.BIRCH_BUTTON);
        this.c(Blocks.JUNGLE_BUTTON);
        this.c(Blocks.ACACIA_BUTTON);
        this.c(Blocks.DARK_OAK_BUTTON);
        this.c(Blocks.SKELETON_SKULL);
        this.c(Blocks.WITHER_SKELETON_SKULL);
        this.c(Blocks.ZOMBIE_HEAD);
        this.c(Blocks.CREEPER_HEAD);
        this.c(Blocks.DRAGON_HEAD);
        this.c(Blocks.ANVIL);
        this.c(Blocks.CHIPPED_ANVIL);
        this.c(Blocks.DAMAGED_ANVIL);
        this.c(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
        this.c(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        this.c(Blocks.COMPARATOR);
        this.c(Blocks.DAYLIGHT_DETECTOR);
        this.c(Blocks.REDSTONE_BLOCK);
        this.c(Blocks.QUARTZ_BLOCK);
        this.c(Blocks.CHISELED_QUARTZ_BLOCK);
        this.c(Blocks.QUARTZ_PILLAR);
        this.c(Blocks.QUARTZ_STAIRS);
        this.c(Blocks.ACTIVATOR_RAIL);
        this.c(Blocks.WHITE_TERRACOTTA);
        this.c(Blocks.ORANGE_TERRACOTTA);
        this.c(Blocks.MAGENTA_TERRACOTTA);
        this.c(Blocks.LIGHT_BLUE_TERRACOTTA);
        this.c(Blocks.YELLOW_TERRACOTTA);
        this.c(Blocks.LIME_TERRACOTTA);
        this.c(Blocks.PINK_TERRACOTTA);
        this.c(Blocks.GRAY_TERRACOTTA);
        this.c(Blocks.LIGHT_GRAY_TERRACOTTA);
        this.c(Blocks.CYAN_TERRACOTTA);
        this.c(Blocks.PURPLE_TERRACOTTA);
        this.c(Blocks.BLUE_TERRACOTTA);
        this.c(Blocks.BROWN_TERRACOTTA);
        this.c(Blocks.GREEN_TERRACOTTA);
        this.c(Blocks.RED_TERRACOTTA);
        this.c(Blocks.BLACK_TERRACOTTA);
        this.c(Blocks.ACACIA_STAIRS);
        this.c(Blocks.DARK_OAK_STAIRS);
        this.c(Blocks.SLIME_BLOCK);
        this.c(Blocks.IRON_TRAPDOOR);
        this.c(Blocks.PRISMARINE);
        this.c(Blocks.PRISMARINE_BRICKS);
        this.c(Blocks.DARK_PRISMARINE);
        this.c(Blocks.PRISMARINE_STAIRS);
        this.c(Blocks.PRISMARINE_BRICK_STAIRS);
        this.c(Blocks.DARK_PRISMARINE_STAIRS);
        this.c(Blocks.HAY_BLOCK);
        this.c(Blocks.WHITE_CARPET);
        this.c(Blocks.ORANGE_CARPET);
        this.c(Blocks.MAGENTA_CARPET);
        this.c(Blocks.LIGHT_BLUE_CARPET);
        this.c(Blocks.YELLOW_CARPET);
        this.c(Blocks.LIME_CARPET);
        this.c(Blocks.PINK_CARPET);
        this.c(Blocks.GRAY_CARPET);
        this.c(Blocks.LIGHT_GRAY_CARPET);
        this.c(Blocks.CYAN_CARPET);
        this.c(Blocks.PURPLE_CARPET);
        this.c(Blocks.BLUE_CARPET);
        this.c(Blocks.BROWN_CARPET);
        this.c(Blocks.GREEN_CARPET);
        this.c(Blocks.RED_CARPET);
        this.c(Blocks.BLACK_CARPET);
        this.c(Blocks.TERRACOTTA);
        this.c(Blocks.COAL_BLOCK);
        this.c(Blocks.RED_SANDSTONE);
        this.c(Blocks.CHISELED_RED_SANDSTONE);
        this.c(Blocks.CUT_RED_SANDSTONE);
        this.c(Blocks.RED_SANDSTONE_STAIRS);
        this.c(Blocks.SMOOTH_STONE);
        this.c(Blocks.SMOOTH_SANDSTONE);
        this.c(Blocks.SMOOTH_QUARTZ);
        this.c(Blocks.SMOOTH_RED_SANDSTONE);
        this.c(Blocks.SPRUCE_FENCE_GATE);
        this.c(Blocks.BIRCH_FENCE_GATE);
        this.c(Blocks.JUNGLE_FENCE_GATE);
        this.c(Blocks.ACACIA_FENCE_GATE);
        this.c(Blocks.DARK_OAK_FENCE_GATE);
        this.c(Blocks.SPRUCE_FENCE);
        this.c(Blocks.BIRCH_FENCE);
        this.c(Blocks.JUNGLE_FENCE);
        this.c(Blocks.ACACIA_FENCE);
        this.c(Blocks.DARK_OAK_FENCE);
        this.c(Blocks.END_ROD);
        this.c(Blocks.PURPUR_BLOCK);
        this.c(Blocks.PURPUR_PILLAR);
        this.c(Blocks.PURPUR_STAIRS);
        this.c(Blocks.END_STONE_BRICKS);
        this.c(Blocks.MAGMA_BLOCK);
        this.c(Blocks.NETHER_WART_BLOCK);
        this.c(Blocks.RED_NETHER_BRICKS);
        this.c(Blocks.BONE_BLOCK);
        this.c(Blocks.OBSERVER);
        this.c(Blocks.WHITE_GLAZED_TERRACOTTA);
        this.c(Blocks.ORANGE_GLAZED_TERRACOTTA);
        this.c(Blocks.MAGENTA_GLAZED_TERRACOTTA);
        this.c(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
        this.c(Blocks.YELLOW_GLAZED_TERRACOTTA);
        this.c(Blocks.LIME_GLAZED_TERRACOTTA);
        this.c(Blocks.PINK_GLAZED_TERRACOTTA);
        this.c(Blocks.GRAY_GLAZED_TERRACOTTA);
        this.c(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
        this.c(Blocks.CYAN_GLAZED_TERRACOTTA);
        this.c(Blocks.PURPLE_GLAZED_TERRACOTTA);
        this.c(Blocks.BLUE_GLAZED_TERRACOTTA);
        this.c(Blocks.BROWN_GLAZED_TERRACOTTA);
        this.c(Blocks.GREEN_GLAZED_TERRACOTTA);
        this.c(Blocks.RED_GLAZED_TERRACOTTA);
        this.c(Blocks.BLACK_GLAZED_TERRACOTTA);
        this.c(Blocks.WHITE_CONCRETE);
        this.c(Blocks.ORANGE_CONCRETE);
        this.c(Blocks.MAGENTA_CONCRETE);
        this.c(Blocks.LIGHT_BLUE_CONCRETE);
        this.c(Blocks.YELLOW_CONCRETE);
        this.c(Blocks.LIME_CONCRETE);
        this.c(Blocks.PINK_CONCRETE);
        this.c(Blocks.GRAY_CONCRETE);
        this.c(Blocks.LIGHT_GRAY_CONCRETE);
        this.c(Blocks.CYAN_CONCRETE);
        this.c(Blocks.PURPLE_CONCRETE);
        this.c(Blocks.BLUE_CONCRETE);
        this.c(Blocks.BROWN_CONCRETE);
        this.c(Blocks.GREEN_CONCRETE);
        this.c(Blocks.RED_CONCRETE);
        this.c(Blocks.BLACK_CONCRETE);
        this.c(Blocks.WHITE_CONCRETE_POWDER);
        this.c(Blocks.ORANGE_CONCRETE_POWDER);
        this.c(Blocks.MAGENTA_CONCRETE_POWDER);
        this.c(Blocks.LIGHT_BLUE_CONCRETE_POWDER);
        this.c(Blocks.YELLOW_CONCRETE_POWDER);
        this.c(Blocks.LIME_CONCRETE_POWDER);
        this.c(Blocks.PINK_CONCRETE_POWDER);
        this.c(Blocks.GRAY_CONCRETE_POWDER);
        this.c(Blocks.LIGHT_GRAY_CONCRETE_POWDER);
        this.c(Blocks.CYAN_CONCRETE_POWDER);
        this.c(Blocks.PURPLE_CONCRETE_POWDER);
        this.c(Blocks.BLUE_CONCRETE_POWDER);
        this.c(Blocks.BROWN_CONCRETE_POWDER);
        this.c(Blocks.GREEN_CONCRETE_POWDER);
        this.c(Blocks.RED_CONCRETE_POWDER);
        this.c(Blocks.BLACK_CONCRETE_POWDER);
        this.c(Blocks.KELP);
        this.c(Blocks.DRIED_KELP_BLOCK);
        this.c(Blocks.DEAD_TUBE_CORAL_BLOCK);
        this.c(Blocks.DEAD_BRAIN_CORAL_BLOCK);
        this.c(Blocks.DEAD_BUBBLE_CORAL_BLOCK);
        this.c(Blocks.DEAD_FIRE_CORAL_BLOCK);
        this.c(Blocks.DEAD_HORN_CORAL_BLOCK);
        this.c(Blocks.CONDUIT);
        this.c(Blocks.DRAGON_EGG);
        this.c(Blocks.BAMBOO);
        this.c(Blocks.POLISHED_GRANITE_STAIRS);
        this.c(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
        this.c(Blocks.MOSSY_STONE_BRICK_STAIRS);
        this.c(Blocks.POLISHED_DIORITE_STAIRS);
        this.c(Blocks.MOSSY_COBBLESTONE_STAIRS);
        this.c(Blocks.END_STONE_BRICK_STAIRS);
        this.c(Blocks.STONE_STAIRS);
        this.c(Blocks.SMOOTH_SANDSTONE_STAIRS);
        this.c(Blocks.SMOOTH_QUARTZ_STAIRS);
        this.c(Blocks.GRANITE_STAIRS);
        this.c(Blocks.ANDESITE_STAIRS);
        this.c(Blocks.RED_NETHER_BRICK_STAIRS);
        this.c(Blocks.POLISHED_ANDESITE_STAIRS);
        this.c(Blocks.DIORITE_STAIRS);
        this.c(Blocks.BRICK_WALL);
        this.c(Blocks.PRISMARINE_WALL);
        this.c(Blocks.RED_SANDSTONE_WALL);
        this.c(Blocks.MOSSY_STONE_BRICK_WALL);
        this.c(Blocks.GRANITE_WALL);
        this.c(Blocks.STONE_BRICK_WALL);
        this.c(Blocks.NETHER_BRICK_WALL);
        this.c(Blocks.ANDESITE_WALL);
        this.c(Blocks.RED_NETHER_BRICK_WALL);
        this.c(Blocks.SANDSTONE_WALL);
        this.c(Blocks.END_STONE_BRICK_WALL);
        this.c(Blocks.DIORITE_WALL);
        this.c(Blocks.LOOM);
        this.c(Blocks.SCAFFOLDING);
        this.a(Blocks.FARMLAND, (IMaterial) Blocks.DIRT);
        this.a(Blocks.TRIPWIRE, (IMaterial) Items.STRING);
        this.a(Blocks.GRASS_PATH, (IMaterial) Blocks.DIRT);
        this.a(Blocks.KELP_PLANT, (IMaterial) Blocks.KELP);
        this.a(Blocks.BAMBOO_SAPLING, (IMaterial) Blocks.BAMBOO);
        this.a(Blocks.STONE, (block) -> {
            return b(block, (IMaterial) Blocks.COBBLESTONE);
        });
        this.a(Blocks.GRASS_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DIRT);
        });
        this.a(Blocks.PODZOL, (block) -> {
            return b(block, (IMaterial) Blocks.DIRT);
        });
        this.a(Blocks.MYCELIUM, (block) -> {
            return b(block, (IMaterial) Blocks.DIRT);
        });
        this.a(Blocks.TUBE_CORAL_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DEAD_TUBE_CORAL_BLOCK);
        });
        this.a(Blocks.BRAIN_CORAL_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DEAD_BRAIN_CORAL_BLOCK);
        });
        this.a(Blocks.BUBBLE_CORAL_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DEAD_BUBBLE_CORAL_BLOCK);
        });
        this.a(Blocks.FIRE_CORAL_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DEAD_FIRE_CORAL_BLOCK);
        });
        this.a(Blocks.HORN_CORAL_BLOCK, (block) -> {
            return b(block, (IMaterial) Blocks.DEAD_HORN_CORAL_BLOCK);
        });
        this.a(Blocks.BOOKSHELF, (block) -> {
            return a(block, (IMaterial) Items.BOOK, (LootValue) LootValueConstant.a(3));
        });
        this.a(Blocks.CLAY, (block) -> {
            return a(block, (IMaterial) Items.CLAY_BALL, (LootValue) LootValueConstant.a(4));
        });
        this.a(Blocks.ENDER_CHEST, (block) -> {
            return a(block, (IMaterial) Blocks.OBSIDIAN, (LootValue) LootValueConstant.a(8));
        });
        this.a(Blocks.SNOW_BLOCK, (block) -> {
            return a(block, (IMaterial) Items.SNOWBALL, (LootValue) LootValueConstant.a(4));
        });
        this.a(Blocks.CHORUS_PLANT, a((IMaterial) Items.CHORUS_FRUIT, (LootValue) LootValueBounds.a(0.0F, 1.0F)));
        this.a(Blocks.POTTED_OAK_SAPLING);
        this.a(Blocks.POTTED_SPRUCE_SAPLING);
        this.a(Blocks.POTTED_BIRCH_SAPLING);
        this.a(Blocks.POTTED_JUNGLE_SAPLING);
        this.a(Blocks.POTTED_ACACIA_SAPLING);
        this.a(Blocks.POTTED_DARK_OAK_SAPLING);
        this.a(Blocks.POTTED_FERN);
        this.a(Blocks.POTTED_DANDELION);
        this.a(Blocks.POTTED_POPPY);
        this.a(Blocks.POTTED_BLUE_ORCHID);
        this.a(Blocks.POTTED_ALLIUM);
        this.a(Blocks.POTTED_AZURE_BLUET);
        this.a(Blocks.POTTED_RED_TULIP);
        this.a(Blocks.POTTED_ORANGE_TULIP);
        this.a(Blocks.POTTED_WHITE_TULIP);
        this.a(Blocks.POTTED_PINK_TULIP);
        this.a(Blocks.POTTED_OXEYE_DAISY);
        this.a(Blocks.POTTED_CORNFLOWER);
        this.a(Blocks.POTTED_LILY_OF_THE_VALLEY);
        this.a(Blocks.POTTED_WITHER_ROSE);
        this.a(Blocks.POTTED_RED_MUSHROOM);
        this.a(Blocks.POTTED_BROWN_MUSHROOM);
        this.a(Blocks.POTTED_DEAD_BUSH);
        this.a(Blocks.POTTED_CACTUS);
        this.a(Blocks.POTTED_BAMBOO);
        this.a(Blocks.ACACIA_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.BIRCH_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.COBBLESTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.DARK_OAK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.DARK_PRISMARINE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.JUNGLE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.NETHER_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.OAK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.PETRIFIED_OAK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.PRISMARINE_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.PRISMARINE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.PURPUR_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.QUARTZ_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.RED_SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.CUT_RED_SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.CUT_SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SPRUCE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.STONE_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.STONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SMOOTH_STONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.POLISHED_GRANITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SMOOTH_RED_SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.MOSSY_STONE_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.POLISHED_DIORITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.MOSSY_COBBLESTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.END_STONE_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SMOOTH_SANDSTONE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.SMOOTH_QUARTZ_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.GRANITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.ANDESITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.RED_NETHER_BRICK_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.POLISHED_ANDESITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.DIORITE_SLAB, DebugReportLootBlock::d);
        this.a(Blocks.ACACIA_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.BIRCH_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.DARK_OAK_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.IRON_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.JUNGLE_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.OAK_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.SPRUCE_DOOR, (block) -> {
            return a(block, (IBlockState) BlockDoor.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.BLACK_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.BLUE_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.BROWN_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.CYAN_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.GRAY_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.GREEN_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.LIGHT_BLUE_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.LIGHT_GRAY_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.LIME_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.MAGENTA_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.PURPLE_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.ORANGE_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.PINK_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.RED_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.WHITE_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.YELLOW_BED, (block) -> {
            return a(block, (IBlockState) BlockBed.PART, (Comparable) BlockPropertyBedPart.HEAD);
        });
        this.a(Blocks.LILAC, (block) -> {
            return a(block, (IBlockState) BlockTallPlant.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.SUNFLOWER, (block) -> {
            return a(block, (IBlockState) BlockTallPlant.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.PEONY, (block) -> {
            return a(block, (IBlockState) BlockTallPlant.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.ROSE_BUSH, (block) -> {
            return a(block, (IBlockState) BlockTallPlant.HALF, (Comparable) BlockPropertyDoubleBlockHalf.LOWER);
        });
        this.a(Blocks.TNT, (block) -> {
            return a(block, (IBlockState) BlockTNT.a, (Comparable) false);
        });
        this.a(Blocks.COCOA, (block) -> {
            return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.COCOA_BEANS).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(3)).b(LootItemConditionBlockStateProperty.a(block).a(BlockCocoa.AGE, 2))))));
        });
        this.a(Blocks.SEA_PICKLE, (block) -> {
            return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(2)).b(LootItemConditionBlockStateProperty.a(block).a(BlockSeaPickle.a, 2))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(3)).b(LootItemConditionBlockStateProperty.a(block).a(BlockSeaPickle.a, 3))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(4)).b(LootItemConditionBlockStateProperty.a(block).a(BlockSeaPickle.a, 4))))));
        });
        this.a(Blocks.COMPOSTER, (block) -> {
            return LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.iX)))).a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.BONE_MEAL)).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockComposter.a, 8)));
        });
        this.a(Blocks.BEACON, DebugReportLootBlock::e);
        this.a(Blocks.BREWING_STAND, DebugReportLootBlock::e);
        this.a(Blocks.CHEST, DebugReportLootBlock::e);
        this.a(Blocks.DISPENSER, DebugReportLootBlock::e);
        this.a(Blocks.DROPPER, DebugReportLootBlock::e);
        this.a(Blocks.ENCHANTING_TABLE, DebugReportLootBlock::e);
        this.a(Blocks.FURNACE, DebugReportLootBlock::e);
        this.a(Blocks.HOPPER, DebugReportLootBlock::e);
        this.a(Blocks.TRAPPED_CHEST, DebugReportLootBlock::e);
        this.a(Blocks.SMOKER, DebugReportLootBlock::e);
        this.a(Blocks.BLAST_FURNACE, DebugReportLootBlock::e);
        this.a(Blocks.BARREL, DebugReportLootBlock::e);
        this.a(Blocks.CARTOGRAPHY_TABLE, DebugReportLootBlock::e);
        this.a(Blocks.FLETCHING_TABLE, DebugReportLootBlock::e);
        this.a(Blocks.GRINDSTONE, DebugReportLootBlock::e);
        this.a(Blocks.LECTERN, DebugReportLootBlock::e);
        this.a(Blocks.SMITHING_TABLE, DebugReportLootBlock::e);
        this.a(Blocks.STONECUTTER, DebugReportLootBlock::e);
        this.a(Blocks.BELL, DebugReportLootBlock::a);
        this.a(Blocks.LANTERN, DebugReportLootBlock::a);
        this.a(Blocks.SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.BLACK_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.BLUE_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.BROWN_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.CYAN_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.GRAY_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.GREEN_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.LIGHT_BLUE_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.LIGHT_GRAY_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.LIME_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.MAGENTA_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.ORANGE_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.PINK_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.PURPLE_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.RED_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.WHITE_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.YELLOW_SHULKER_BOX, DebugReportLootBlock::f);
        this.a(Blocks.BLACK_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.BLUE_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.BROWN_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.CYAN_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.GRAY_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.GREEN_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.LIGHT_BLUE_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.LIGHT_GRAY_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.LIME_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.MAGENTA_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.ORANGE_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.PINK_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.PURPLE_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.RED_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.WHITE_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.YELLOW_BANNER, DebugReportLootBlock::g);
        this.a(Blocks.PLAYER_HEAD, (block) -> {
            return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemConditionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionCopyNBT.a(LootItemFunctionCopyNBT.Source.BLOCK_ENTITY).a("Owner", "SkullOwner")))));
        });
        this.a(Blocks.BIRCH_LEAVES, (block) -> {
            return a(block, Blocks.BIRCH_SAPLING, DebugReportLootBlock.g);
        });
        this.a(Blocks.ACACIA_LEAVES, (block) -> {
            return a(block, Blocks.ACACIA_SAPLING, DebugReportLootBlock.g);
        });
        this.a(Blocks.JUNGLE_LEAVES, (block) -> {
            return a(block, Blocks.JUNGLE_SAPLING, DebugReportLootBlock.h);
        });
        this.a(Blocks.SPRUCE_LEAVES, (block) -> {
            return a(block, Blocks.SPRUCE_SAPLING, DebugReportLootBlock.g);
        });
        this.a(Blocks.OAK_LEAVES, (block) -> {
            return b(block, Blocks.OAK_SAPLING, DebugReportLootBlock.g);
        });
        this.a(Blocks.DARK_OAK_LEAVES, (block) -> {
            return b(block, Blocks.DARK_OAK_SAPLING, DebugReportLootBlock.g);
        });
        LootItemConditionBlockStateProperty.a lootitemconditionblockstateproperty_a = LootItemConditionBlockStateProperty.a(Blocks.BEETROOTS).a(BlockBeetroot.a, 3);

        this.a(Blocks.BEETROOTS, (block) -> {
            return a(block, Items.BEETROOT, Items.BEETROOT_SEEDS, lootitemconditionblockstateproperty_a);
        });
        LootItemConditionBlockStateProperty.a lootitemconditionblockstateproperty_a1 = LootItemConditionBlockStateProperty.a(Blocks.WHEAT).a(BlockCrops.AGE, 7);

        this.a(Blocks.WHEAT, (block) -> {
            return a(block, Items.WHEAT, Items.WHEAT_SEEDS, lootitemconditionblockstateproperty_a1);
        });
        LootItemConditionBlockStateProperty.a lootitemconditionblockstateproperty_a2 = LootItemConditionBlockStateProperty.a(Blocks.CARROTS).a(BlockCarrots.AGE, 7);

        this.a(Blocks.CARROTS, (block) -> {
            return (LootTable.a) a((IMaterial) block, (LootItemFunctionUser) LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.CARROT))).a(LootSelector.a().b(lootitemconditionblockstateproperty_a2).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.CARROT).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.5714286F, 3)))));
        });
        LootItemConditionBlockStateProperty.a lootitemconditionblockstateproperty_a3 = LootItemConditionBlockStateProperty.a(Blocks.POTATOES).a(BlockPotatoes.AGE, 7);

        this.a(Blocks.POTATOES, (block) -> {
            return (LootTable.a) a((IMaterial) block, (LootItemFunctionUser) LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.POTATO))).a(LootSelector.a().b(lootitemconditionblockstateproperty_a3).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.POTATO).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.5714286F, 3)))).a(LootSelector.a().b(lootitemconditionblockstateproperty_a3).a(LootItem.a((IMaterial) Items.POISONOUS_POTATO).b(LootItemConditionRandomChance.a(0.02F)))));
        });
        this.a(Blocks.SWEET_BERRY_BUSH, (block) -> {
            return (LootTable.a) a((IMaterial) block, (LootItemFunctionUser) LootTable.b().a(LootSelector.a().b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(Blocks.SWEET_BERRY_BUSH).a(BlockSweetBerryBush.a, 3)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.SWEET_BERRIES)).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(2.0F, 3.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS))).a(LootSelector.a().b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(Blocks.SWEET_BERRY_BUSH).a(BlockSweetBerryBush.a, 2)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.SWEET_BERRIES)).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(1.0F, 2.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS))));
        });
        this.a(Blocks.BROWN_MUSHROOM_BLOCK, (block) -> {
            return c(block, (IMaterial) Blocks.BROWN_MUSHROOM);
        });
        this.a(Blocks.RED_MUSHROOM_BLOCK, (block) -> {
            return c(block, (IMaterial) Blocks.RED_MUSHROOM);
        });
        this.a(Blocks.COAL_ORE, (block) -> {
            return a(block, Items.COAL);
        });
        this.a(Blocks.EMERALD_ORE, (block) -> {
            return a(block, Items.EMERALD);
        });
        this.a(Blocks.NETHER_QUARTZ_ORE, (block) -> {
            return a(block, Items.QUARTZ);
        });
        this.a(Blocks.DIAMOND_ORE, (block) -> {
            return a(block, Items.DIAMOND);
        });
        this.a(Blocks.LAPIS_ORE, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.LAPIS_LAZULI).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(4.0F, 9.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.a(Enchantments.LOOT_BONUS_BLOCKS))));
        });
        this.a(Blocks.COBWEB, (block) -> {
            return c(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) Items.STRING)));
        });
        this.a(Blocks.DEAD_BUSH, (block) -> {
            return b(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.STICK).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(0.0F, 2.0F)))));
        });
        this.a(Blocks.SEAGRASS, DebugReportLootBlock::d);
        this.a(Blocks.VINE, DebugReportLootBlock::d);
        this.a(Blocks.TALL_SEAGRASS, d((IMaterial) Blocks.SEAGRASS));
        this.a(Blocks.LARGE_FERN, d((IMaterial) Blocks.FERN));
        this.a(Blocks.TALL_GRASS, (block) -> {
            return b(Blocks.GRASS, ((LootSelectorEntry.a) ((LootSelectorEntry.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) Items.WHEAT_SEEDS))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockTallPlant.HALF, BlockPropertyDoubleBlockHalf.LOWER))).b(LootItemConditionRandomChance.a(0.125F)));
        });
        this.a(Blocks.MELON_STEM, (block) -> {
            return b(block, Items.MELON_SEEDS);
        });
        this.a(Blocks.PUMPKIN_STEM, (block) -> {
            return b(block, Items.PUMPKIN_SEEDS);
        });
        this.a(Blocks.CHORUS_FLOWER, (block) -> {
            return LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a(((LootSelectorEntry.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) block))).b(LootItemConditionEntityProperty.a(LootTableInfo.EntityTarget.THIS))));
        });
        this.a(Blocks.FERN, DebugReportLootBlock::h);
        this.a(Blocks.GRASS, DebugReportLootBlock::h);
        this.a(Blocks.GLOWSTONE, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.GLOWSTONE_DUST).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(2.0F, 4.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS)).b((LootItemFunction.a) LootItemFunctionLimitCount.a(LootIntegerLimit.a(1, 4)))));
        });
        this.a(Blocks.MELON, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.MELON_SLICE).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(3.0F, 7.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS)).b((LootItemFunction.a) LootItemFunctionLimitCount.a(LootIntegerLimit.b(9)))));
        });
        this.a(Blocks.REDSTONE_ORE, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.REDSTONE).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(4.0F, 5.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS))));
        });
        this.a(Blocks.SEA_LANTERN, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemFunctionUser) LootItem.a((IMaterial) Items.PRISMARINE_CRYSTALS).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(2.0F, 3.0F))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS)).b((LootItemFunction.a) LootItemFunctionLimitCount.a(LootIntegerLimit.a(1, 5)))));
        });
        this.a(Blocks.NETHER_WART, (block) -> {
            return LootTable.b().a((LootSelector.a) a((IMaterial) block, (LootItemFunctionUser) LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.NETHER_WART).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueBounds.a(2.0F, 4.0F)).b(LootItemConditionBlockStateProperty.a(block).a(BlockNetherWart.AGE, 3))).b((LootItemFunction.a) LootItemFunctionApplyBonus.b(Enchantments.LOOT_BONUS_BLOCKS).b(LootItemConditionBlockStateProperty.a(block).a(BlockNetherWart.AGE, 3))))));
        });
        this.a(Blocks.SNOW, (block) -> {
            return LootTable.b().a(LootSelector.a().b(LootItemConditionEntityProperty.a(LootTableInfo.EntityTarget.THIS)).a((LootEntryAbstract.a) LootEntryAlternatives.a(LootEntryAlternatives.a(LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 1)), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 2))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(2))), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 3))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(3))), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 4))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(4))), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 5))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(5))), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 6))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(6))), ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 7))).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(7))), LootItem.a((IMaterial) Items.SNOWBALL).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(8)))).b(DebugReportLootBlock.b), LootEntryAlternatives.a(LootItem.a((IMaterial) block).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 1)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(2))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 2)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(3))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 3)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(4))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 4)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(5))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 5)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(6))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 6)), LootItem.a((IMaterial) block).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(7))).b((LootItemCondition.a) LootItemConditionBlockStateProperty.a(block).a(BlockSnow.LAYERS, 7)), LootItem.a((IMaterial) Blocks.SNOW_BLOCK)))));
        });
        this.a(Blocks.GRAVEL, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemConditionUser) ((LootSelectorEntry.a) LootItem.a((IMaterial) Items.FLINT).b(LootItemConditionTableBonus.a(Enchantments.LOOT_BONUS_BLOCKS, 0.1F, 0.14285715F, 0.25F, 1.0F))).a(LootItem.a((IMaterial) block))));
        });
        this.a(Blocks.CAMPFIRE, (block) -> {
            return a(block, (LootEntryAbstract.a) a((IMaterial) block, (LootItemConditionUser) LootItem.a((IMaterial) Items.CHARCOAL).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(2)))));
        });
        this.b(Blocks.GLASS);
        this.b(Blocks.WHITE_STAINED_GLASS);
        this.b(Blocks.ORANGE_STAINED_GLASS);
        this.b(Blocks.MAGENTA_STAINED_GLASS);
        this.b(Blocks.LIGHT_BLUE_STAINED_GLASS);
        this.b(Blocks.YELLOW_STAINED_GLASS);
        this.b(Blocks.LIME_STAINED_GLASS);
        this.b(Blocks.PINK_STAINED_GLASS);
        this.b(Blocks.GRAY_STAINED_GLASS);
        this.b(Blocks.LIGHT_GRAY_STAINED_GLASS);
        this.b(Blocks.CYAN_STAINED_GLASS);
        this.b(Blocks.PURPLE_STAINED_GLASS);
        this.b(Blocks.BLUE_STAINED_GLASS);
        this.b(Blocks.BROWN_STAINED_GLASS);
        this.b(Blocks.GREEN_STAINED_GLASS);
        this.b(Blocks.RED_STAINED_GLASS);
        this.b(Blocks.BLACK_STAINED_GLASS);
        this.b(Blocks.GLASS_PANE);
        this.b(Blocks.WHITE_STAINED_GLASS_PANE);
        this.b(Blocks.ORANGE_STAINED_GLASS_PANE);
        this.b(Blocks.MAGENTA_STAINED_GLASS_PANE);
        this.b(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.b(Blocks.YELLOW_STAINED_GLASS_PANE);
        this.b(Blocks.LIME_STAINED_GLASS_PANE);
        this.b(Blocks.PINK_STAINED_GLASS_PANE);
        this.b(Blocks.GRAY_STAINED_GLASS_PANE);
        this.b(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
        this.b(Blocks.CYAN_STAINED_GLASS_PANE);
        this.b(Blocks.PURPLE_STAINED_GLASS_PANE);
        this.b(Blocks.BLUE_STAINED_GLASS_PANE);
        this.b(Blocks.BROWN_STAINED_GLASS_PANE);
        this.b(Blocks.GREEN_STAINED_GLASS_PANE);
        this.b(Blocks.RED_STAINED_GLASS_PANE);
        this.b(Blocks.BLACK_STAINED_GLASS_PANE);
        this.b(Blocks.ICE);
        this.b(Blocks.PACKED_ICE);
        this.b(Blocks.BLUE_ICE);
        this.b(Blocks.TURTLE_EGG);
        this.b(Blocks.MUSHROOM_STEM);
        this.b(Blocks.DEAD_TUBE_CORAL);
        this.b(Blocks.DEAD_BRAIN_CORAL);
        this.b(Blocks.DEAD_BUBBLE_CORAL);
        this.b(Blocks.DEAD_FIRE_CORAL);
        this.b(Blocks.DEAD_HORN_CORAL);
        this.b(Blocks.TUBE_CORAL);
        this.b(Blocks.BRAIN_CORAL);
        this.b(Blocks.BUBBLE_CORAL);
        this.b(Blocks.FIRE_CORAL);
        this.b(Blocks.HORN_CORAL);
        this.b(Blocks.DEAD_TUBE_CORAL_FAN);
        this.b(Blocks.DEAD_BRAIN_CORAL_FAN);
        this.b(Blocks.DEAD_BUBBLE_CORAL_FAN);
        this.b(Blocks.DEAD_FIRE_CORAL_FAN);
        this.b(Blocks.DEAD_HORN_CORAL_FAN);
        this.b(Blocks.TUBE_CORAL_FAN);
        this.b(Blocks.BRAIN_CORAL_FAN);
        this.b(Blocks.BUBBLE_CORAL_FAN);
        this.b(Blocks.FIRE_CORAL_FAN);
        this.b(Blocks.HORN_CORAL_FAN);
        this.a(Blocks.INFESTED_STONE, Blocks.STONE);
        this.a(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
        this.a(Blocks.INFESTED_STONE_BRICKS, Blocks.STONE_BRICKS);
        this.a(Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
        this.a(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        this.a(Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        this.a(Blocks.CAKE, a());
        this.a(Blocks.ATTACHED_PUMPKIN_STEM, a());
        this.a(Blocks.ATTACHED_MELON_STEM, a());
        this.a(Blocks.FROSTED_ICE, a());
        this.a(Blocks.SPAWNER, a());
        Set<MinecraftKey> set = Sets.newHashSet();
        Iterator iterator = IRegistry.BLOCK.iterator();

        while (iterator.hasNext()) {
            Block block = (Block) iterator.next();
            MinecraftKey minecraftkey = block.i();

            if (minecraftkey != LootTables.a && set.add(minecraftkey)) {
                LootTable.a loottable_a = (LootTable.a) this.i.remove(minecraftkey);

                if (loottable_a == null) {
                    throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", minecraftkey, IRegistry.BLOCK.getKey(block)));
                }

                biconsumer.accept(minecraftkey, loottable_a);
            }
        }

        if (!this.i.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.i.keySet());
        }
    }

    public void a(Block block) {
        this.a(block, (block1) -> {
            return c((IMaterial) ((BlockFlowerPot) block1).d());
        });
    }

    public void a(Block block, Block block1) {
        this.a(block, b((IMaterial) block1));
    }

    public void a(Block block, IMaterial imaterial) {
        this.a(block, a(imaterial));
    }

    public void b(Block block) {
        this.a(block, block);
    }

    public void c(Block block) {
        this.a(block, (IMaterial) block);
    }

    private void a(Block block, Function<Block, LootTable.a> function) {
        this.a(block, (LootTable.a) function.apply(block));
    }

    private void a(Block block, LootTable.a loottable_a) {
        this.i.put(block.i(), loottable_a);
    }
}

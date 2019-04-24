package net.minecraft.server;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DebugReportLootFishing implements Consumer<BiConsumer<MinecraftKey, LootTable.a>> {

    public static final LootItemCondition.a a = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.JUNGLE));
    public static final LootItemCondition.a b = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.JUNGLE_HILLS));
    public static final LootItemCondition.a c = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.JUNGLE_EDGE));
    public static final LootItemCondition.a d = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.BAMBOO_JUNGLE));
    public static final LootItemCondition.a e = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.MODIFIED_JUNGLE));
    public static final LootItemCondition.a f = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.MODIFIED_JUNGLE_EDGE));
    public static final LootItemCondition.a g = LootItemConditionLocationCheck.a((new CriterionConditionLocation.a()).a(Biomes.BAMBOO_JUNGLE_HILLS));

    public DebugReportLootFishing() {}

    public void accept(BiConsumer<MinecraftKey, LootTable.a> biconsumer) {
        biconsumer.accept(LootTables.ab, LootTable.b().a(LootSelector.a().a((LootValue) LootValueConstant.a(1)).a((LootEntryAbstract.a) LootSelectorLootTable.a(LootTables.ac).a(10).b(-2)).a((LootEntryAbstract.a) LootSelectorLootTable.a(LootTables.ad).a(5).b(2)).a((LootEntryAbstract.a) LootSelectorLootTable.a(LootTables.ae).a(85).b(-1))));
        biconsumer.accept(LootTables.ae, LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.COD).a(60)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.SALMON).a(25)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.TROPICAL_FISH).a(2)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.PUFFERFISH).a(13))));
        biconsumer.accept(LootTables.ac, LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.LEATHER_BOOTS).a(10).b((LootItemFunction.a) LootItemFunctionSetDamage.a(LootValueBounds.a(0.0F, 0.9F)))).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.LEATHER).a(10)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.BONE).a(10)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.POTION).a(10).b((LootItemFunction.a) LootItemFunctionSetTag.a((NBTTagCompound) SystemUtils.a((Object) (new NBTTagCompound()), (nbttagcompound) -> {
            nbttagcompound.setString("Potion", "minecraft:water");
        })))).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.STRING).a(5)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.FISHING_ROD).a(2).b((LootItemFunction.a) LootItemFunctionSetDamage.a(LootValueBounds.a(0.0F, 0.9F)))).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.BOWL).a(10)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.STICK).a(5)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.INK_SAC).a(1).b((LootItemFunction.a) LootItemFunctionSetCount.a((LootValue) LootValueConstant.a(10)))).a((LootEntryAbstract.a) LootItem.a((IMaterial) Blocks.TRIPWIRE_HOOK).a(10)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.ROTTEN_FLESH).a(10)).a((LootEntryAbstract.a) ((LootSelectorEntry.a) LootItem.a((IMaterial) Blocks.BAMBOO).b((LootItemCondition.a) DebugReportLootFishing.a.a(DebugReportLootFishing.b).a(DebugReportLootFishing.c).a(DebugReportLootFishing.d).a(DebugReportLootFishing.e).a(DebugReportLootFishing.f).a(DebugReportLootFishing.g))).a(10))));
        biconsumer.accept(LootTables.ad, LootTable.b().a(LootSelector.a().a((LootEntryAbstract.a) LootItem.a((IMaterial) Blocks.LILY_PAD)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.NAME_TAG)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.SADDLE)).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.BOW).b((LootItemFunction.a) LootItemFunctionSetDamage.a(LootValueBounds.a(0.0F, 0.25F))).b((LootItemFunction.a) LootEnchantLevel.a((LootValue) LootValueConstant.a(30)).e())).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.FISHING_ROD).b((LootItemFunction.a) LootItemFunctionSetDamage.a(LootValueBounds.a(0.0F, 0.25F))).b((LootItemFunction.a) LootEnchantLevel.a((LootValue) LootValueConstant.a(30)).e())).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.BOOK).b((LootItemFunction.a) LootEnchantLevel.a((LootValue) LootValueConstant.a(30)).e())).a((LootEntryAbstract.a) LootItem.a((IMaterial) Items.NAUTILUS_SHELL))));
    }
}

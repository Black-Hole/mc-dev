package net.minecraft.server;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommandReplaceItem {

    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.replaceitem.block.failed", new Object[0]));
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.replaceitem.slot.inapplicable", new Object[] { object});
    });
    private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("commands.replaceitem.entity.failed", new Object[] { object, object1});
    });

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> com_mojang_brigadier_commanddispatcher) {
        com_mojang_brigadier_commanddispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) ((LiteralArgumentBuilder) CommandDispatcher.a("replaceitem").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("block").then(CommandDispatcher.a("pos", (ArgumentType) ArgumentPosition.a()).then(CommandDispatcher.a("slot", (ArgumentType) ArgumentInventorySlot.a()).then(((RequiredArgumentBuilder) CommandDispatcher.a("item", (ArgumentType) ArgumentItemStack.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper) commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(1, false));
        })).then(CommandDispatcher.a("count", (ArgumentType) IntegerArgumentType.integer(1, 64)).executes((commandcontext) -> {
            return a((CommandListenerWrapper) commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(IntegerArgumentType.getInteger(commandcontext, "count"), true));
        }))))))).then(CommandDispatcher.a("entity").then(CommandDispatcher.a("targets", (ArgumentType) ArgumentEntity.b()).then(CommandDispatcher.a("slot", (ArgumentType) ArgumentInventorySlot.a()).then(((RequiredArgumentBuilder) CommandDispatcher.a("item", (ArgumentType) ArgumentItemStack.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper) commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(1, false));
        })).then(CommandDispatcher.a("count", (ArgumentType) IntegerArgumentType.integer(1, 64)).executes((commandcontext) -> {
            return a((CommandListenerWrapper) commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(IntegerArgumentType.getInteger(commandcontext, "count"), true));
        })))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, int i, ItemStack itemstack) throws CommandSyntaxException {
        TileEntity tileentity = commandlistenerwrapper.getWorld().getTileEntity(blockposition);

        if (!(tileentity instanceof IInventory)) {
            throw CommandReplaceItem.a.create();
        } else {
            IInventory iinventory = (IInventory) tileentity;

            if (i >= 0 && i < iinventory.getSize()) {
                iinventory.setItem(i, itemstack);
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.block.success", new Object[] { Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ()), itemstack.A()}), true);
                return 1;
            } else {
                throw CommandReplaceItem.b.create(Integer.valueOf(i));
            }
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection, int i, ItemStack itemstack) throws CommandSyntaxException {
        int j = 0;
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).defaultContainer.b();
            }

            if (entity.c(i, itemstack.cloneItemStack())) {
                ++j;
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer) entity).defaultContainer.b();
                }
            }
        }

        if (j == 0) {
            throw CommandReplaceItem.c.create(itemstack.A(), Integer.valueOf(i));
        } else {
            if (collection.size() == 1) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.entity.success.single", new Object[] { ((Entity) collection.iterator().next()).getScoreboardDisplayName(), itemstack.A()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.entity.success.multiple", new Object[] { Integer.valueOf(collection.size()), itemstack.A()}), true);
            }

            return j;
        }
    }
}

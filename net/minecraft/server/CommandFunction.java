package net.minecraft.server;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandFunction extends CommandAbstract {

    public CommandFunction() {}

    public String getCommand() {
        return "function";
    }

    public int a() {
        return 2;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.function.usage";
    }

    public void execute(final MinecraftServer minecraftserver, final ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length != 1) {
            throw new ExceptionUsage("commands.function.usage", new Object[0]);
        } else {
            MinecraftKey minecraftkey = new MinecraftKey(astring[0]);
            CustomFunction customfunction = minecraftserver.aL().a(minecraftkey);

            if (customfunction == null) {
                throw new CommandException("commands.function.unknown", new Object[] { minecraftkey});
            } else {
                int i = customfunction.a(new CommandListenerWrapper(icommandlistener) {
                    public boolean a(int i, String s) {
                        return super.a(i, s) && minecraftserver.aL().g().a(i, s);
                    }
                });

                a(icommandlistener, (ICommand) this, "commands.function.success", new Object[] { minecraftkey, Integer.valueOf(i)});
            }
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, (Collection) minecraftserver.aL().d().keySet()) : Collections.emptyList();
    }
}

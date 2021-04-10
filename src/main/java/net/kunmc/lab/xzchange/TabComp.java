package net.kunmc.lab.xzchange;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComp implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        List<String> S = new ArrayList<>();
        if(cmd.getName().equals("xzChange")) {
            if (args.length == 1) {
                S.add("set");
                S.add("start");
                S.add("stop");
                S.add("help");
                return S;
            } else if (args.length == 2 && args[0].equals("set")) {
                S.add("<seconds>");
                return S;
            }
        }
        return new ArrayList<>();
    }

}

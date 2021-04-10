package net.kunmc.lab.xzchange;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabComp implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        List<String> S = new ArrayList<>();
        if(cmd.getName().equals("xzChange")) {
            if (args.length == 1) {
                return Stream.of("start","stop","stop","help","set").filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            } else if (args.length == 2 && args[0].equals("set")) {
                S.add("<seconds>");
                return S;
            }
        }
        return new ArrayList<>();
    }

}

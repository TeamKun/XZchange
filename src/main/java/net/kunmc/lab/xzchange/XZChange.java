package net.kunmc.lab.xzchange;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class XZChange extends JavaPlugin {

    public int time ;
    public boolean game = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveConfig();
        this.getCommand("xzChange").setExecutor(this);
        this.getCommand("xzChange").setTabCompleter(new TabComp());
        time = getConfig().getInt("intervaltime");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equals("xzChange")){
            if(args.length==1){
                if(args[0].equals("start")){
                    sender.sendMessage(ChatColor.GREEN+"ゲームを開始します");
                    game = true;
                    gameLogic(time);
                }else if(args[0].equals("stop")){
                    if(game) {
                        sender.sendMessage(ChatColor.GREEN + "実行中のゲームに終了命令を出しました");
                        game = false;
                    }else{
                        sender.sendMessage(ChatColor.RED + "実行中のゲームはありません");
                    }
                }else if(args[0].equals("help")){
                    sender.sendMessage(ChatColor.AQUA+"/xzChange start");
                    sender.sendMessage("ゲームの開始");
                    sender.sendMessage(ChatColor.AQUA+"/xzChange stop");
                    sender.sendMessage("ゲームの終了");
                    sender.sendMessage(ChatColor.AQUA+"/xzChange set <seconds>");
                    sender.sendMessage("TPのインターバルの設定(単位:秒)");
                    sender.sendMessage(ChatColor.AQUA+"/xzChange help");
                    sender.sendMessage("本プラグインのコマンド一覧の表示");
                }else{
                    sender.sendMessage(ChatColor.YELLOW+"コマンドの形式が異なります！/xzChange help でコマンド一覧を確認できます。");
                }
            }
            else if(args.length==2){
                if(args[0].equals("set")){
                    if(args[1].matches("[+-]?\\d*(\\.\\d+)?")){
                        if(Integer.parseInt(args[1])>0){
                            time = Integer.parseInt(args[1]);
                            sender.sendMessage(ChatColor.GREEN+"インターバルを"+args[1]+"秒に変更しました！");
                        }else{
                            sender.sendMessage(ChatColor.RED+"0より大きい数値を入力してください");
                        }
                    }else{
                        sender.sendMessage(ChatColor.RED+"引数には数字を入力してください");
                    }
                }else{
                    sender.sendMessage(ChatColor.YELLOW+"コマンドの形式が異なります！/xzChange help でコマンド一覧を確認できます。");
                }
            }else{
                sender.sendMessage(ChatColor.YELLOW+"コマンドの形式が異なります！/xzChange help でコマンド一覧を確認できます。");
            }
        }
        return false;
    }

    public void gameLogic(int t){
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendActionBar("残り"+t+"秒でTPします");
        });
        new BukkitRunnable(){
            int count = t-1;
            @Override
            public void run() {
                if(game) {
                    if (count == 0) {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.sendActionBar(ChatColor.GOLD + "TPします！");
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 1);
                            Location loc = player.getLocation();
                            loc.set(player.getLocation().getZ(),player.getLocation().getY(),player.getLocation().getX());
                            if(player.getGameMode() != GameMode.SPECTATOR) {
                                player.teleport(loc);
                            }
                        });
                        count = t;
                    } else if (count <= 5) {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.sendActionBar(ChatColor.AQUA + "残り" + count + "秒でTPします");
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                        });
                    } else {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.sendActionBar("残り" + count + "秒でTPします");
                        });
                    }
                    count--;
                }else{
                    this.cancel();

                }
            }
        }.runTaskTimer(this,0,20);
    }
}

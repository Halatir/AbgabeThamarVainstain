import net.minecraft.server.v1_7_R4.BlockMobSpawner;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Thamar on 02.06.2015.
 */
public class Main extends JavaPlugin implements Listener {
    public static Logger log = Logger.getLogger("Minecraft");

    public void onLoad(){
        log.info("[ThamarAb] Loaded...");
    }

    public void onEnable(){
        log.info("[ThamarAb] Starting up...");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable(){
        log.info("[ThamarAb] Stopping...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("Tunnel")) {
            if (sender instanceof Player) {
                Player p=(Player) sender;
                Location l=p.getLocation();
                List<Entity> near = p.getNearbyEntities(l.getX(),l.getY(),l.getZ());

                for(Entity target: near) {
                    Vector Y= new Vector(0,5,0);
                    //if(target instanceof Player){}    falls nur player geschmissern werden sollen
                    target.setVelocity(Y);

                }

            }
        }
        else if(command.getName().equalsIgnoreCase("FortuneWheel")){
            if(command.getName().equalsIgnoreCase("FortuneWheel")) {
                //Check if its a player
                if (sender instanceof Player) {
                    //get the Player and her lcation
                    Player p=(Player) sender;
                    Location l=p.getLocation();
                    //add 5 to the y of the location
                    l.setY(l.getX() + 2);


                }
            }
        }

        return true;
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event){
        Player p=event.getPlayer();
        if(p.getItemInHand().getType()== Material.DIRT) {
            BlockIterator BI = new BlockIterator(p,5);
            while(BI.hasNext()==true){
                Block b=BI.next();
                if(b.getType()== Material.WOOL){

                    break;

                }
            }

        }
    }




}

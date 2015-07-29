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
                World w=p.getWorld();

                //Modify the location in x and z+0,5
                l.setX(((int) l.getX()) + 0.5);
                l.setZ(((int) l.getZ()) + 0.5);
                p.teleport(l);

                Block a;
               for(int i=1 ;i<4; i++){
                   l.setY(((int)l.getY())-1);
                     a = w.getBlockAt((int) l.getX(), (int) l.getY(), (int) l.getZ());
                    a.setType(Material.AIR);
               }


                Vector v=new Vector((int)l.getX(),(int)l.getY(),(int)l.getZ());
                    //Direction festlegen
                float f= p.getLocation().getYaw();
                Vector Yaw=getDirection(f);


                Tunnelbau(w,v,Yaw, l);



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
      /*  Player p=event.getPlayer();
        if(p.getItemInHand().getType()== Material.DIRT) {
            BlockIterator BI = new BlockIterator(p,5);
            while(BI.hasNext()==true){
                Block b=BI.next();
                if(b.getType()== Material.WOOL){

                    break;

                }
            }

        }*/
    }

    public Vector getDirection(float f){
        Vector direction=new Vector(0,0,0);
        if(f>=315.0 || f<45.0 ) {
            direction= new Vector(0,0,1);
        } else if(f>=135.0 && f<=225.0){
            direction=new Vector(0,0,-1);
        } else if(f>=45.0 && f<135.0){
            direction=new Vector(-1,0,0);
        } else {
            direction=new Vector(1,0,0);
        }
        return direction;
    }


    public void Tunnelbau(World w,Vector v,Vector d, Location l){

        BlockIterator BI = new BlockIterator(w, v, d, 0,15);
        while (BI.hasNext() == true) {

            Block b = BI.next();
            Block b2=w.getBlockAt(b.getX(),(b.getY()+1),b.getZ());

                b.setType(Material.AIR);
                b2.setType(Material.AIR);
                b2=w.getBlockAt(b.getX(),(b.getY()-1),b.getZ());
                b2.setType(Material.DIRT);

                if(d.getZ()==1 || d.getZ()==-1) {
                    if(d.getZ()==1) {
                        b2 = w.getBlockAt(b.getX(), (b.getY() + 2), (b.getZ() + 1));
                        b2.setType(Material.DIRT);
                    }
                    else{
                        b2 = w.getBlockAt(b.getX(), (b.getY() + 2), (b.getZ() - 1));
                        b2.setType(Material.DIRT);
                    }
                    b2 = w.getBlockAt((b.getX() + 1), b.getY(), b.getZ());
                    b2.setType(Material.DIRT);
                    b2 = w.getBlockAt((b.getX() - 1), b.getY(), b.getZ());
                    b2.setType(Material.DIRT);
                    b2 = w.getBlockAt((b.getX() + 1), (b.getY() + 1), b.getZ());
                    b2.setType(Material.DIRT);
                    b2 = w.getBlockAt((b.getX() - 1), (b.getY() + 1), b.getZ());
                    b2.setType(Material.DIRT);
                }
                else if(d.getX()==1 || d.getX()==-1) {
                    if(d.getZ()==1) {
                        b2 = w.getBlockAt((b.getX()+1), (b.getY() + 2), b.getZ());
                        b2.setType(Material.DIRT);
                    }
                    else{
                        b2 = w.getBlockAt((b.getX()-1), (b.getY() + 2), b.getZ());
                        b2.setType(Material.DIRT);
                    }
                    b2=w.getBlockAt(b.getX(),b.getY(),(b.getZ()+1));
                    b2.setType(Material.DIRT);
                    b2=w.getBlockAt((b.getX()-1),b.getY(),(b.getZ()-1));
                    b2.setType(Material.DIRT);
                    b2=w.getBlockAt(b.getX(),(b.getY()+1),(b.getZ()+1));
                    b2.setType(Material.DIRT);
                    b2=w.getBlockAt((b.getX()-1),(b.getY()+1),(b.getZ()-1));
                    b2.setType(Material.DIRT);
                }

        }

    }


}

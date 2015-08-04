import net.minecraft.server.v1_7_R4.BlockMobSpawner;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftItemFrame;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    //Liste zum Speichern aller Glückstradmittelpunkte
    ArrayList<Location> locs = new ArrayList<Location>();
    // Liste mit Dropitems
    Material [] D = new Material[50];


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("Tunnel")) {
            //Testen, ob der Sender ein Player ist
            if (sender instanceof Player) {
                Player p=(Player) sender;
                //Testen, ob der Player das erwünschte Objekt in der hand hält(Eisen-Spitzhacke)
                if(p.getItemInHand().getType()== Material.IRON_PICKAXE) {
                    //Locations für den Player und den Beginn des Tunnels
                    Location l = p.getLocation();
                    Location lp = p.getLocation();

                    World w = p.getWorld();

                    //geringes versetzen des Players
                    lp.setZ(((int) l.getZ()) + 0.5);
                    p.teleport(lp);

                    //Das Loch als Einstieg für den Tunnel
                    for (int i = 1; i < 4; i++) {
                        Block a;
                        l.setY(((int) l.getY()) - 1);
                        a = w.getBlockAt((int) l.getX(), (int) l.getY(), (int) l.getZ());
                        a.setType(Material.AIR);
                    }

                    //Vector der Position, an dem der Tunnel beginnen soll
                    Vector start = new Vector((int) l.getX(), (int) l.getY(), (int) l.getZ());
                    //Richtung festlegen
                    float f = p.getLocation().getYaw();
                    Vector Yaw = getDirection(f);

                    //Der Tunnel wird gebaut
                    Tunnelbau(w, start, Yaw);
                }
            }
        }
        else if(command.getName().equalsIgnoreCase("FortuneWheel")){
            if(command.getName().equalsIgnoreCase("FortuneWheel")) {
                //Check if its a player
                if (sender instanceof Player) {
                    //get the Player
                    Player p=(Player) sender;
                    //Ein Glücksrad wird gebaut
                   locs.add(Gluecksradbau(p).getLocation());


                    D [0]= Material.BAKED_POTATO;    D[8]= Material.DIAMOND;            D [15]= Material.DIAMOND_CHESTPLATE;
                    D [2]= Material.BOW;             D[9]= Material.EXP_BOTTLE;         D [16]= Material.EYE_OF_ENDER;
                    D [3]= Material.DIAMOND_BARDING; D [10]= Material.DIAMOND_SWORD;    D [17]= Material.GOLD_AXE;
                    D [4]= Material.DIAMOND_BOOTS;   D [11]= Material.DIAMOND_AXE;      D [18]= Material.GOLD_BARDING;
                    D [5]= Material.EMERALD;         D [12]= Material.DIAMOND_PICKAXE;  D [19]= Material.DIAMOND_HELMET;
                    D [6]= Material.ENCHANTED_BOOK;  D [13]= Material.DIAMOND_LEGGINGS; D [20]= Material.GOLD_BOOTS;
                    D [7]= Material.ENDER_PEARL;     D [14]= Material.DIAMOND_HOE;      D [22]= Material.GOLD_CHESTPLATE;
                    D [29]= Material.GOLD_HELMET;    D [26]= Material.GOLDEN_APPLE;     D [23]= Material.DIAMOND_HOE;
                    D [30]= Material.GOLD_HOE;       D [27]= Material.IRON_AXE;         D [24]= Material.DIAMOND_HOE;
                    D [31]= Material.GOLD_LEGGINGS;  D [28]= Material.IRON_BARDING;     D [25]= Material.IRON_BOOTS;
                    D [32]= Material.GOLD_SWORD;     D [35]= Material.IRON_HELMET;      D [38]= Material.IRON_CHESTPLATE;
                    D [33]= Material.GOLD_AXE;       D [36]= Material.IRON_SWORD;       D [21]= Material.IRON_HOE;
                    D [34]= Material.GOLD_PICKAXE;   D [37]= Material.IRON_PICKAXE;     D [39]= Material.MONSTER_EGG;
                    D [40]= Material.STONE_SWORD;    D [44]= Material.STONE_PLATE;      D [47]= Material.STONE_AXE;
                    D [41]= Material.WOOD_SWORD;     D [45]= Material.BRICK;            D [48]= Material.BONE;
                    D [42]= Material.COAL;           D [46]= Material.BREAD;            D [49]= Material.FLINT;
                    D [43]= Material.POTION;
                }
            }
        }

        return true;
    }

    @EventHandler
    //Anfügen eines Datenspeichers unter dem Schlüsselwertes "LastTime" falls nicht vorhanden
    public void onPlayerLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(!p.hasMetadata("LastTime"))
        p.setMetadata("LastTime", new FixedMetadataValue(this,0));
    }
    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event){
        //Testen ob Linksklick ausgeführt wurde
        if (event.getAction()==Action.LEFT_CLICK_BLOCK) {
            //speichern der notwendigen Informationen
            Player p = event.getPlayer();
            Block I = event.getClickedBlock();
            Location l=I.getLocation();

            //Überprüfen, ob der Block Mittelpunkt eines Rades ist
            if (locs.contains(l)) {


                //Überprüfen ob er noch aus Obsidian besteht
                if (I.getType() == Material.OBSIDIAN) {

                    // Auslesen der letzten und der aktuellen "Drehzeit"
                    Long LastT = p.getMetadata("LastTime").get(0).asLong();
                    Long currentT = System.currentTimeMillis() / 1000L;

                    //Testen, ob die vergangene Zeit bereits lang genug ist.7200 für 2 Stunden
                    if ((currentT - LastT) > 2700) {

                        //Neuer Zeitstempel wird auf dem Player abgelegt
                        p.setMetadata("LastTime", new FixedMetadataValue(this, System.currentTimeMillis() / 1000L));
                        Random r = new Random();

                        //Festlegen, auf welcher Seite das Item fallen soll
                        Vector dd =getDirection(p.getLocation().getYaw());
                        if(dd.getX()==1){l.setX(l.getX() - 1);}
                        if( dd.getZ()==1){l.setZ(l.getZ()-1);}
                        if(dd.getX()==-1){l.setX(l.getX()+1);}
                        if( dd.getZ()==-1){l.setZ(l.getZ()+1);}

                        //Fallen des Items
                        ItemStack drop = new ItemStack(D[r.nextInt(49)]);
                        p.getWorld().dropItem(l, drop);

                    }
                    else {
                       //Ausgabe, du ahst noch nicht ange genug gewartet//
                        Bukkit.getServer().broadcast("Du darfst noch nicht erneut am Glücksrad drehen","");
                    }
                }
                else {

                    //Wenn der Block in der Liste der Glücksradblöcke ist, aber bereits zerstört wurde, wird er nun aus der Liste entfernt
                    locs.remove(l);
                }

            }
        }
    }



    //Funktion zum erhalten der Blickrichtung
    public Vector getDirection(float f){
        Vector direction=new Vector(0,0,0);
        if(f>=315.0 || f<45.0 ) {
            direction= new Vector(0,0,1);
        } else if(f>=135.0 && f<=225.0){
            direction=new Vector(0,0,-1);
        } else if(f>=45.0 && f<135.0){
            direction=new Vector(-1,0,0);
        } else if(f>225.0 && f<315.0){
            direction=new Vector(1,0,0);
        }
        return direction;
    }


    public void Tunnelbau(World w,Vector v,Vector d){

        BlockIterator BI = new BlockIterator(w, v, d, 0,20);
        Block b=w.getBlockAt(1,1,1);

        while (BI.hasNext() == true) {

            //Die zwei Blöcke, die entfernt werden sollen
            b = BI.next();
            Block b2=w.getBlockAt(b.getX(), (b.getY() + 1), b.getZ());

            //sie werden in Luftblöcke umgewandelt
            b.setType(Material.AIR);
            b2.setType(Material.AIR);
            //wähle den Block darunter und verwandle ihn in Erde (Notwendig für Sand/Wasser/Lava/Kies)
            b2=w.getBlockAt(b.getX(),(b.getY()-1),b.getZ());
            b2.setType(Material.DIRT);


            //Tunnelbau in Z-Richtung(positiv oder negativ)
            if(d.getZ()==1 || d.getZ()==-1) {
                if(d.getZ()==1) {
                    //Decke des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                    b2 = w.getBlockAt(b.getX(), (b.getY() + 2), (b.getZ() + 1));
                    b2.setType(Material.DIRT);
                }
                else{
                    //Decke des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                    b2 = w.getBlockAt(b.getX(), (b.getY() + 2), (b.getZ() - 1));
                    b2.setType(Material.DIRT);
                }
                //Seitenwände des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                b2 = w.getBlockAt((b.getX() + 1), b.getY(), b.getZ());
                b2.setType(Material.DIRT);
                b2 = w.getBlockAt((b.getX() - 1), b.getY(), b.getZ());
                b2.setType(Material.DIRT);
                b2 = w.getBlockAt((b.getX() + 1), (b.getY() + 1), b.getZ());
                b2.setType(Material.DIRT);
                b2 = w.getBlockAt((b.getX() - 1), (b.getY() + 1), b.getZ());
                b2.setType(Material.DIRT);
            }
            //Tunnelbau in X-Richtung(positiv oder negativ)
            else if(d.getX()==1 || d.getX()==-1) {
                if(d.getX()==1) {
                    //Decke des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                    b2 = w.getBlockAt((b.getX()+1), (b.getY() + 2), b.getZ());
                    b2.setType(Material.DIRT);
                }
                else{
                    //Decke des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                    b2 = w.getBlockAt((b.getX()-1), (b.getY() + 2), b.getZ());
                    b2.setType(Material.DIRT);
                }
                //Seitenwände des Tunnels(Notwendig für Sand/Wasser/Lava/Kies)
                b2=w.getBlockAt(b.getX(),b.getY(),(b.getZ()+1));
                b2.setType(Material.DIRT);
                b2=w.getBlockAt(b.getX(),b.getY(),(b.getZ()-1));
                b2.setType(Material.DIRT);
                b2=w.getBlockAt(b.getX(),(b.getY()+1),(b.getZ()+1));
                b2.setType(Material.DIRT);
                b2=w.getBlockAt(b.getX(),(b.getY()+1),(b.getZ()-1));
                b2.setType(Material.DIRT);
            }

        }
        //Ausgang
        Block b3;
        //Richtung wird festgelegt, in die der Ausgang gebaut wird
        int z=0;
        int x=0;
        if(d.getZ()==1){z=1;x=0;}
        else if(d.getZ()==-1){z=-1;x=0;}
        else if(d.getX()==1){z=0;x=1;}
        else if(d.getX()==-1) {z=0;x=-1;}

        //Treppenschleife(Läuft, bis Luft erreicht wird)(Wasser wird hier nicht berücksichtigt, könnte zur endlosschleife führen)
        b3 = w.getBlockAt((b.getX()+x),(b.getY()+1),(b.getZ()+z));
        b=w.getBlockAt(b.getX(),(b.getY()+1),b.getZ());
        while(b3.getType()!=Material.AIR){
            b3.setType(Material.AIR);
            b=w.getBlockAt(b.getX(),(b.getY()+1),b.getZ());
            b.setType(Material.AIR);
            b=w.getBlockAt((b.getX()+x),b.getY(),(b.getZ()+z));
            b.setType(Material.AIR);
            b3=w.getBlockAt((b3.getX()+x),(b3.getY()+1),(b3.getZ()+z));

        }
    }


    public Block Gluecksradbau(Player p){
        //Speichern der Notwendigen Informationen
        World w1=p.getWorld();
        Location l=p.getLocation();
        float di=l.getYaw();
        Vector d=getDirection(di);
        Block g=w1.getBlockAt(l);



        if(d.getX()==1 || d.getX()==-1){
            //verschiebe das Glücksrad zwei Blöcke vor den Erbauer in Blickrichtung
            if(d.getX()==1){
                l.setX(l.getX() + 2);
            }
            else if(d.getX()==-1) {
                l.setX(l.getX() - 2);
            }

            //Aufbau des Rades unterteilt und gelbe, rote Bloöcke, dann der Mittelblock
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setY(l.getY() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setY(l.getY() - 1);
            l.setZ(l.getZ() - 1);
             g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setZ(l.getZ() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);


            l.setY(l.getY() - 1);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setZ(l.getZ() - 2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setY(l.getY() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setZ(l.getZ() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setZ(l.getZ()-1);
            l.setY(l.getY() - 1);
            g=w1.getBlockAt(l);
            g.setType(Material.OBSIDIAN);

            //Rückgabe des Obsidianblockes zum abspeichern
            return g;
        }


        else if(d.getZ()==1 || d.getZ()==-1) {
            if(d.getZ()==1) {
                l.setZ(l.getZ() + 2);
            } else if(d.getZ()==-1){
                l.setZ(l.getZ() - 2);
            }

            //Aufbau des Rades unterteilt und gelbe, rote Bloöcke, dann der Mittelblock
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setY(l.getY() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setY(l.getY() - 1);
            l.setX(l.getX()-1);
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);

            l.setX(l.getX() + 2);
            g=w1.getBlockAt(l);
            g.setType(Material.BRICK);


            l.setY(l.getY()-1);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setX(l.getX()-2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setY(l.getY()+2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setX(l.getX()+2);
            g=w1.getBlockAt(l);
            g.setType(Material.SPONGE);

            l.setX(l.getX()-1);
            l.setY(l.getY()-1);
            g=w1.getBlockAt(l);
            g.setType(Material.OBSIDIAN);

            //Rückgabe des Obsidianblockes zum abspeichern
            return g;
        }

    return g;
    }


}


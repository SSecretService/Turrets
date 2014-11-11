package me.ssh.secretservice.turretsReloaded;

import com.codelanx.nations.Nations;
import me.ssh.secretservice.turretsReloaded.commands.TurretCommand;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfigManager;
import me.ssh.secretservice.turretsReloaded.database.TurretDatabase;
import me.ssh.secretservice.turretsReloaded.database.YAMLTurretDatabase;
import me.ssh.secretservice.turretsReloaded.listener.CacheListener;
import me.ssh.secretservice.turretsReloaded.listener.InteractionEvent;
import me.ssh.secretservice.turretsReloaded.targeting.MobAssessor;
import me.ssh.secretservice.turretsReloaded.targeting.TargetAssessor;
import me.ssh.secretservice.turretsReloaded.utils.BlockLocation;
import me.ssh.secretservice.turretsReloaded.utils.Lang;
import me.ssh.secretservice.turretsReloaded.utils.NationsUtil;
import me.ssh.secretservice.turretsReloaded.utils.Upgrade;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class TurretsReloaded
  extends JavaPlugin
  implements Listener
{
  private final Map<BlockLocation, Turret> turrets = new HashMap();
  
  public List<TargetAssessor> getTargetAssessors()
  {
    return this.targetAssessors;
  }
  
  private final List<TargetAssessor> targetAssessors = new ArrayList();
  
  public Economy getEconomy()
  {
    return this.economy;
  }
  
  private Economy economy = null;
  
  public Map<UUID, TurretOwner> getTurretOwner()
  {
    return this.turretOwner;
  }
  
  private Map<UUID, TurretOwner> turretOwner = new HashMap();
  private SimpleConfig conf;
  private SimpleConfig turretConf;
  private SimpleConfig turretOwnerConf;
  private SimpleConfigManager man;
  
  public SimpleConfig getConf()
  {
    return this.conf;
  }
  
  public SimpleConfig getTurretConf()
  {
    return this.turretConf;
  }
  
  public SimpleConfig getTurretOwnerConf()
  {
    return this.turretOwnerConf;
  }
  
  public SimpleConfigManager getMan()
  {
    return this.man;
  }
  
  public Set<UUID> getPlayerCache()
  {
    return this.playerCache;
  }
  
  private Set<UUID> playerCache = new HashSet();
  
  public List<Integer> getTurretPrices()
  {
    return this.turretPrices;
  }
  
  private List<Integer> turretPrices = new ArrayList();
  private boolean reload;
  
  public boolean isReload()
  {
    return this.reload;
  }
  
  public boolean isHasNations()
  {
    return this.hasNations;
  }
  
  private boolean hasNations = false;
  private TurretDatabase turretDatabase;
  private Upgrade upgrade;
  private List<TurretOwner> remove;
  private List<UUID> adminRemove;
  
  public TurretDatabase getTurretDatabase()
  {
    return this.turretDatabase;
  }
  
  public Upgrade getUpgrade()
  {
    return this.upgrade;
  }
  
  public List<TurretOwner> getRemove()
  {
    return this.remove;
  }
  
  public List<UUID> getAdminRemove()
  {
    return this.adminRemove;
  }
  
  public TurretsReloaded()
  {
    this.targetAssessors.add(new MobAssessor());
  }
  
  public void onEnable()
  {
    if (!setupEconomy())
    {
      System.out.println("You need to have an Economy Plugin installed in order to use this Plugin. Shutting down...");
      Bukkit.getPluginManager().disablePlugin(this);
    }
    else
    {
      try
      {
        Lang.init(this);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      NationsUtil.init(Nations.getInstance());
      setupConfig();
      registerListeners();
      loadTurretOwners();
      registerCommand();
      this.remove = new ArrayList();
      this.adminRemove = new ArrayList();
      this.upgrade = new Upgrade(this);
      
      this.hasNations = getServer().getPluginManager().isPluginEnabled("Nations");
      if (this.hasNations) {
        getLogger().info("Nations Support Enabled");
      }
      getServer().getScheduler().runTaskLater(this, new Runnable()
      {
        public void run()
        {
          try
          {
            TurretsReloaded.this.loadAndSpawnTurrets();
            TurretsReloaded.this.getLogger().info("Turrets loaded and spawned.");
          }
          catch (IOException e)
          {
            TurretsReloaded.this.getLogger().log(Level.SEVERE, "Failed to load turrets", e);
          }
          TurretsReloaded.this.getLogger().log(Level.INFO, "Total number of turrets: {0}", Integer.valueOf(TurretsReloaded.this.turrets.size()));
        }
      }, 2L);
    }
  }
  
  private void registerCommand()
  {
    getCommand("turret").setExecutor(new TurretCommand(this));
  }
  
  private boolean setupEconomy()
  {
    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      this.economy = ((Economy)economyProvider.getProvider());
    }
    return this.economy != null;
  }
  
  private void setupConfig()
  {
    this.man = new SimpleConfigManager(this);
    File ref = new File(getDataFolder(), "config.yml");
    if (!ref.exists()) {
      saveResource("config.yml", true);
    }
    this.conf = this.man.getNewConfig("config.yml");
    
    this.turretOwnerConf = this.man.getNewConfig("turretOwners.yml");
    
    this.turretDatabase = new YAMLTurretDatabase(new File(getDataFolder(), "turrets.yml"), this);
    

    this.turretPrices = ((List)this.conf.get("settings.turrets.prices"));
  }
  
  private void registerListeners()
  {
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new InteractionEvent(this), this);
    pm.registerEvents(new CacheListener(this), this);
    pm.registerEvents(this, this);
  }
  
  private void loadAndSpawnTurrets()
    throws IOException
  {
    Collection<Turret> dbTurrets = null;
    try
    {
      dbTurrets = this.turretDatabase.loadTurrets();
    }
    catch (IOException localIOException) {}
    if (dbTurrets == null) {
      return;
    }
    for (Turret turret : dbTurrets) {
      if (!this.turrets.containsKey(turret.getLocation()))
      {
        this.turrets.put(turret.getLocation(), turret);
        turret.spawn();
      }
    }
  }
  
  private void despawnAndSaveTurrets()
    throws IOException
  {
    Iterator<Map.Entry<BlockLocation, Turret>> it = this.turrets.entrySet().iterator();
    this.turretDatabase.saveTurrets(this.turrets.values());
    while (it.hasNext())
    {
      Map.Entry<BlockLocation, Turret> entry = (Map.Entry)it.next();
      Turret turret = (Turret)entry.getValue();
      turret.despawn();
      it.remove();
    }
  }
  
  public TurretOwner getTurretOwner(UUID uuid)
  {
    return (TurretOwner)this.turretOwner.get(uuid);
  }
  
  public boolean turretOwnerExists(UUID uuid)
  {
    return this.turretOwner.containsKey(uuid);
  }
  
  public void addTurretOwner(TurretOwner owner)
  {
    this.turretOwner.put(owner.getUuid(), owner);
  }
  
  public void addUserToCache(Player p)
  {
    this.playerCache.add(p.getUniqueId());
  }
  
  public void removeUserFromCache(Player p)
  {
    this.playerCache.remove(p.getUniqueId());
  }
  
  public void setReload(boolean value)
  {
    this.reload = value;
  }
  
  public boolean hasFactions()
  {
    return this.hasNations;
  }
  
  public void saveTurretOwners()
  {
    Set<UUID> ownerList = this.turretOwner.keySet();
    for (UUID ownerUUID : ownerList)
    {
      String owner = ownerUUID.toString();
      ConfigurationSection ownerConfig = this.turretOwnerConf.createSection(owner);
      ownerConfig.set("maxTurretsAllowed", Integer.valueOf(((TurretOwner)this.turretOwner.get(ownerUUID)).getMaxTurretsAllowed()));
      ownerConfig.set("currentlyOwnedTurrets", Integer.valueOf(((TurretOwner)this.turretOwner.get(ownerUUID)).getNumTurretsOwned()));
      ownerConfig.set("ownerName", ((TurretOwner)this.turretOwner.get(ownerUUID)).getName());
      this.turretOwnerConf.saveConfig();
    }
  }
  
  public void loadTurretOwners()
  {
    Set<String> ownerList = this.turretOwnerConf.getKeys();
    if (!ownerList.isEmpty()) {
      for (String owner : ownerList)
      {
        ConfigurationSection ownerConfig = this.turretOwnerConf.getConfigurationSection(owner);
        int maxTurretsAllowed = ownerConfig.getInt("maxTurretsAllowed");
        int ownedTurrets = ownerConfig.getInt("currentlyOwnedTurrets");
        String ownerName = ownerConfig.getString("ownerName");
        TurretOwner turretOwnerObject = new TurretOwner(UUID.fromString(owner), ownerName, ownedTurrets, maxTurretsAllowed);
        this.turretOwner.put(UUID.fromString(owner), turretOwnerObject);
      }
    }
  }
  
  public Collection<Turret> getTurrets()
  {
    return Collections.unmodifiableCollection(this.turrets.values());
  }
  
  public Map<BlockLocation, Turret> getTurretMap()
  {
    return this.turrets;
  }
  
  public Turret getTurret(BlockLocation postLocation)
  {
    return (Turret)this.turrets.get(postLocation);
  }
  
  public void addTurret(Turret turret)
  {
    this.turrets.put(turret.getLocation(), turret);
  }
  
  public Economy getEco()
  {
    return this.economy;
  }
  
  @EventHandler
  public void preCommand(PlayerCommandPreprocessEvent e)
  {
    if (e.getMessage().startsWith("/stop"))
    {
      try
      {
        despawnAndSaveTurrets();
        getLogger().info("Despawned and saved turrets.");
      }
      catch (IOException es)
      {
        getLogger().log(Level.SEVERE, "Failed to save turrets", es);
      }
      saveTurretOwners();
      
      this.playerCache.clear();
      this.turretOwner.clear();
      
      this.playerCache = null;
      this.turretOwner = null;
      this.economy = null;
      this.conf = null;
      this.turretConf = null;
      this.turretOwnerConf = null;
      
      Bukkit.dispatchCommand(e.getPlayer(), "stop");
    }
  }
  
  @EventHandler
  public void consoleCommand(ServerCommandEvent e)
  {
    if (e.getCommand().equals("stop"))
    {
      try
      {
        despawnAndSaveTurrets();
        getLogger().info("Despawned and saved turrets.");
      }
      catch (IOException es)
      {
        getLogger().log(Level.SEVERE, "Failed to save turrets", es);
      }
      saveTurretOwners();
      
      this.playerCache.clear();
      this.turretOwner.clear();
      
      this.playerCache = null;
      this.turretOwner = null;
      this.economy = null;
      this.conf = null;
      this.turretConf = null;
      this.turretOwnerConf = null;
    }
  }
  
  public void addRemove(TurretOwner owner)
  {
    this.remove.add(owner);
  }
  
  public void remRemove(TurretOwner owner)
  {
    this.remove.remove(owner);
  }
  
  public void addAdminRemove(UUID uuid)
  {
    this.adminRemove.add(uuid);
  }
  
  public void remAdminRemove(UUID owner)
  {
    this.remove.remove(owner);
  }
  
  public void onDisable() {}
}

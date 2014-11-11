package me.ssh.secretservice.turretsReloaded.database;

import me.ssh.secretservice.turretsReloaded.Turret;
import me.ssh.secretservice.turretsReloaded.TurretOwner;
import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import me.ssh.secretservice.turretsReloaded.utils.BlockLocation;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLTurretDatabase
  implements TurretDatabase
{
  private static final String TURRETS_PATH = "turrets";
  private static final String LOCATION_PATH = "location";
  private static final String OWNER_PATH = "owner";
  private static final String OWNER_NAME_PATH = "owner_name";
  private static final String TURRET_TIER_PATH = "turret_tier";
  private static final String FIRING_INTERVAL_ARROW_PATH = "firing_interval_arrow_tier";
  private static final String FIRING_INTERVAL_FIRE_PATH = "firing_interval_fire_tier";
  private static final String RANGE_ARROW_PATH = "range_arrow_tier";
  private static final String RANGE_FIRE_PATH = "range_fire_tier";
  private static final String ACCURACY_ARROW_PATH = "accuracy_arrow_tier";
  private static final String ACCURACY_FIRE_PATH = "accuracy_fire_tier";
  private static final String FIRE_ARROW_PATH = "firearrow";
  private static final String SLOWNESS_UPGRADE = "slownessUpgrade_tier";
  private static final String BLINDNESS_UPGRADE = "blindnessUpgrade_tier";
  private static final String NATION_NAME = "nation_name";
  private final File file;
  private final TurretsReloaded plugin;
  private final YamlConfiguration backing;
  
  public YAMLTurretDatabase(File file, TurretsReloaded plugin)
  {
    this.file = file;
    this.plugin = plugin;
    this.backing = new YamlConfiguration();
  }
  
  public Collection<Turret> loadTurrets()
    throws IOException
  {
    if (!this.file.exists()) {
      this.file.getParentFile().mkdirs();
    }
    try
    {
      this.backing.load(this.file);
    }
    catch (InvalidConfigurationException e)
    {
      throw new IOException("Backing file is corrupt.");
    }
    ConfigurationSection turretSections = this.backing.getConfigurationSection("turrets");
    
    Set<String> turretIDs = turretSections.getKeys(false);
    List<Turret> turrets = new ArrayList();
    Server server = this.plugin.getServer();
    SimpleConfig conf = this.plugin.getConf();
    
    List<Double> rangeArrow = (List<Double>) conf.getList("settings.basic.values.ArrowTurret.range");
    List<Integer> fireRateArrow = (List<Integer>) conf.getList("settings.basic.values.ArrowTurret.fireRate");
    List<?> accuracyAr = conf.getList("settings.basic.values.ArrowTurret.accuracy");
    List<Float> accuracyArrow = new ArrayList();
    for (Object o : accuracyAr) {
      if ((o instanceof Double))
      {
        double d = ((Double)o).doubleValue();
        float f = (float)d;
        accuracyArrow.add(Float.valueOf(f));
      }
    }
    List<Double> rangeFireBall = (List<Double>) conf.getList("settings.basic.values.FireBallTurret.range");
    List<Integer> fireRateFireBall = (List<Integer>) conf.getList("settings.basic.values.FireBallTurret.fireRate");
    
    List<?> accuracyFire = conf.getList("settings.basic.values.FireBallTurret.accuracy");
    List<Float> accuracyFireBall = new ArrayList();
    for (Object o : accuracyFire) {
      if ((o instanceof Double))
      {
        double d = ((Double)o).doubleValue();
        float f = (float)d;
        accuracyFireBall.add(Float.valueOf(f));
      }
    }
    for (String turretID : turretIDs)
    {
      ConfigurationSection turretSection = turretSections.getConfigurationSection(turretID);
      BlockLocation location = BlockLocation.loadFromConfigSection(turretSection, "location", server);
      String owner = turretSection.getString("owner");
      String ownerName = turretSection.getString("owner_name");
      String nationName = turretSection.getString("nation_name");
      Turret turret = new Turret(location, UUID.fromString(owner), ownerName, nationName, this.plugin);
      turrets.add(turret);
      this.plugin.getTurretOwner(turret.getOwnerUUID()).addTurret(turret);
      
      turret.setTurretTier(turretSection.getInt("turret_tier"));
      turret.setFirintIntervallTierArrow(turretSection.getInt("firing_interval_arrow_tier", 0));
      turret.setRangeTierArrow(turretSection.getInt("range_arrow_tier", 0));
      turret.setAccuracyTierFire(turretSection.getInt("accuracy_arrow_tier", 0));
      
      turret.setFireIntervallTierFire(turretSection.getInt("firing_interval_fire_tier", 0));
      turret.setRangeTierFire(turretSection.getInt("range_fire_tier", 0));
      turret.setAccuracyTierArrow(turretSection.getInt("accuracy_fire_tier", 0));
      
      turret.setSlownessTier(turretSection.getInt("slownessUpgrade_tier"));
      turret.setBlindnessTier(turretSection.getInt("blindnessUpgrade_tier"));
      turret.setFireArrow(turretSection.getBoolean("firearrow"));
      
      turret.setRangeArrow(((Double)rangeArrow.get(turret.getRangeTierArrow())).doubleValue());
      turret.setFiringIntervallArrow(((Integer)fireRateArrow.get(turret.getFirintIntervallTierArrow())).intValue());
      turret.setAccuracyArrow(((Float)accuracyArrow.get(turret.getAccuracyTierArrow())).floatValue());
      
      turret.setRangeFire(((Double)rangeFireBall.get(turret.getRangeTierFire())).doubleValue());
      turret.setFireIntervallFire(((Integer)fireRateFireBall.get(turret.getFireIntervallTierFire())).intValue());
      turret.setAccuracyFire(((Float)accuracyFireBall.get(turret.getAccuracyTierFire())).floatValue());
      
      turret.setNation(turretSection.getString("nation_name"));
    }
    return turrets;
  }
  
  public void saveTurrets(Collection<Turret> turrets)
    throws IOException
  {
    ConfigurationSection turretSections = this.backing.createSection("turrets");
    int id = 0;
    for (Turret turret : turrets)
    {
      TurretOwner turretOwner = this.plugin.getTurretOwner(turret.getOwnerUUID());
      if (turretOwner != null) {
        turretOwner.removeTurret(turret);
      }
      ConfigurationSection turretSection = turretSections.createSection("t" + id);
      turret.getLocation().saveToConfigSection(turretSection, "location");
      turretSection.set("owner", turret.getOwnerUUID().toString());
      turretSection.set("owner_name", turret.getOwnerName());
      turretSection.set("turret_tier", Integer.valueOf(turret.getTurretTier()));
      turretSection.set("firing_interval_arrow_tier", Integer.valueOf(turret.getFirintIntervallTierArrow()));
      turretSection.set("firing_interval_fire_tier", Integer.valueOf(turret.getFireIntervallTierFire()));
      
      turretSection.set("range_fire_tier", Integer.valueOf(turret.getRangeTierFire()));
      turretSection.set("range_arrow_tier", Integer.valueOf(turret.getRangeTierArrow()));
      
      turretSection.set("accuracy_arrow_tier", Integer.valueOf(turret.getAccuracyTierArrow()));
      turretSection.set("accuracy_fire_tier", Integer.valueOf(turret.getAccuracyTierFire()));
      
      turretSection.set("slownessUpgrade_tier", Integer.valueOf(turret.getSlownessTier()));
      turretSection.set("blindnessUpgrade_tier", Integer.valueOf(turret.getBlindnessTier()));
      turretSection.set("firearrow", Boolean.valueOf(turret.isFireArrow()));
      turretSection.set("nation_name", turret.getNation());
      id++;
    }
    this.backing.save(this.file);
  }
}

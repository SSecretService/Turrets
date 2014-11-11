package me.ssh.secretservice.turretsReloaded;

import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import me.ssh.secretservice.turretsReloaded.nms.EntityTurret;
import me.ssh.secretservice.turretsReloaded.targeting.TargetAssessor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.ssh.secretservice.turretsReloaded.utils.BlockLocation;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Turret
  implements ProjectileSource
{
  private final BlockLocation location;
  private final TurretsReloaded plugin;
  private UUID ownerUUID;
  private String ownerName;
  private EntityTurret entity;
  private int firintIntervallTierArrow;
  private int rangeTierArrow;
  private int accuracyTierArrow;
  private int fireIntervallTierFire;
  private int rangeTierFire;
  private int accuracyTierFire;
  private int slownessTier;
  private int blindnessTier;
  private int firingIntervallArrow;
  private int fireIntervallFire;
  private double rangeArrow;
  private double rangeFire;
  private float accuracyArrow;
  private float accuracyFire;
  
  public BlockLocation getLocation()
  {
    return this.location;
  }
  
  public TurretsReloaded getPlugin()
  {
    return this.plugin;
  }
  
  public UUID getOwnerUUID()
  {
    return this.ownerUUID;
  }
  
  public void setOwnerUUID(UUID ownerUUID)
  {
    this.ownerUUID = ownerUUID;
  }
  
  public String getOwnerName()
  {
    return this.ownerName;
  }
  
  public void setOwnerName(String ownerName)
  {
    this.ownerName = ownerName;
  }
  
  public EntityTurret getEntity()
  {
    return this.entity;
  }
  
  public int getAccuracyTierArrow()
  {
    return this.accuracyTierArrow;
  }
  
  public int getFireIntervallTierFire()
  {
    return this.fireIntervallTierFire;
  }
  
  public int getFirintIntervallTierArrow()
  {
    return this.firintIntervallTierArrow;
  }
  
  public int getRangeTierFire()
  {
    return this.rangeTierFire;
  }
  
  public int getAccuracyTierFire()
  {
    return this.accuracyTierFire;
  }
  
  public int getSlownessTier()
  {
    return this.slownessTier;
  }
  
  public int getBlindnessTier()
  {
    return this.blindnessTier;
  }
  
  public int getFireIntervallFire()
  {
    return this.fireIntervallFire;
  }
  
  public int getRangeTierArrow()
  {
    return this.rangeTierArrow;
  }
  
  public int getFiringIntervallArrow()
  {
    return this.firingIntervallArrow;
  }
  
  public void setAccuracyTierArrow(int accuracyTierArrow)
  {
    this.accuracyTierArrow = accuracyTierArrow;
  }
  
  public void setRangeTierArrow(int rangeTierArrow)
  {
    this.rangeTierArrow = rangeTierArrow;
  }
  
  public void setFirintIntervallTierArrow(int firintIntervallTierArrow)
  {
    this.firintIntervallTierArrow = firintIntervallTierArrow;
  }
  
  public void setRangeTierFire(int rangeTierFire)
  {
    this.rangeTierFire = rangeTierFire;
  }
  
  public void setFireIntervallFire(int fireIntervallFire)
  {
    this.fireIntervallFire = fireIntervallFire;
  }
  
  public void setFiringIntervallArrow(int firingIntervallArrow)
  {
    this.firingIntervallArrow = firingIntervallArrow;
  }
  
  public void setBlindnessTier(int blindnessTier)
  {
    this.blindnessTier = blindnessTier;
  }
  
  public void setSlownessTier(int slownessTier)
  {
    this.slownessTier = slownessTier;
  }
  
  public void setAccuracyTierFire(int accuracyTierFire)
  {
    this.accuracyTierFire = accuracyTierFire;
  }
  
  public void setFireIntervallTierFire(int fireIntervallTierFire)
  {
    this.fireIntervallTierFire = fireIntervallTierFire;
  }
  
  public double getRangeFire()
  {
    return this.rangeFire;
  }
  
  public double getRangeArrow()
  {
    return this.rangeArrow;
  }
  
  public void setRangeFire(double rangeFire)
  {
    this.rangeFire = rangeFire;
  }
  
  public void setRangeArrow(double rangeArrow)
  {
    this.rangeArrow = rangeArrow;
  }
  
  public float getAccuracyFire()
  {
    return this.accuracyFire;
  }
  
  public float getAccuracyArrow()
  {
    return this.accuracyArrow;
  }
  
  public void setAccuracyFire(float accuracyFire)
  {
    this.accuracyFire = accuracyFire;
  }
  
  public void setAccuracyArrow(float accuracyArrow)
  {
    this.accuracyArrow = accuracyArrow;
  }
  
  public int getTurretTier()
  {
    return this.turretTier;
  }
  
  private int turretTier = 0;
  private boolean fireArrow;
  private TurretOwner turretOwner;
  private String nation;
  SimpleConfig conf;
  
  public boolean isFireArrow()
  {
    return this.fireArrow;
  }
  
  public void setFireArrow(boolean fireArrow)
  {
    this.fireArrow = fireArrow;
  }
  
  public TurretOwner getTurretOwner()
  {
    return this.turretOwner;
  }
  
  public String getNation()
  {
    return this.nation;
  }
  
  public void setNation(String nation)
  {
    this.nation = nation;
  }
  
  public Turret(BlockLocation loc, UUID uuid, String name, String nationName, TurretsReloaded reloaded)
  {
    this.location = loc;
    this.ownerUUID = uuid;
    this.plugin = reloaded;
    this.entity = new EntityTurret(this, this.location.getWorld(), this.location.getX() + 0.5D, this.location.getY() + 1.3D, this.location.getZ() + 0.5D);
    
    this.conf = this.plugin.getConf();
    
    this.turretOwner = this.plugin.getTurretOwner(uuid);
    this.nation = nationName;
    this.ownerName = name;
    
    update();
  }
  
  public List<TargetAssessor> getTargetAssessors()
  {
    return this.plugin.getTargetAssessors();
  }
  
  public void update()
  {
    List<Double> rangeArrowList = (List<Double>) this.conf.getList("settings.basic.values.ArrowTurret.range");
    List<Integer> fireRateArrowList = (List<Integer>) this.conf.getList("settings.basic.values.ArrowTurret.fireRate");
    List<?> accuracyAr = this.conf.getList("settings.basic.values.ArrowTurret.accuracy");
    List<Float> accuracyArrowList = new ArrayList();
    for (Object o : accuracyAr) {
      if ((o instanceof Double))
      {
        double d = ((Double)o).doubleValue();
        float f = (float)d;
        accuracyArrowList.add(Float.valueOf(f));
      }
    }
    List<Double> rangeFireBallList = (List<Double>) this.conf.getList("settings.basic.values.FireBallTurret.range");
    List<Integer> fireRateFireBallList = (List<Integer>) this.conf.getList("settings.basic.values.FireBallTurret.fireRate");
    
    List<?> accuracyFireList = this.conf.getList("settings.basic.values.FireBallTurret.accuracy");
    List<Float> accuracyFireBallList = new ArrayList();
    for (Object o : accuracyFireList) {
      if ((o instanceof Double))
      {
        double d = ((Double)o).doubleValue();
        float f = (float)d;
        accuracyFireBallList.add(Float.valueOf(f));
      }
    }
    setRangeArrow(((Double)rangeArrowList.get(this.rangeTierArrow)).doubleValue());
    this.rangeFire = ((Double)rangeFireBallList.get(this.rangeTierFire)).doubleValue();
    
    this.accuracyArrow = ((Float)accuracyArrowList.get(this.accuracyTierArrow)).floatValue();
    this.accuracyFire = ((Float)accuracyFireBallList.get(this.accuracyTierFire)).floatValue();
    
    this.fireIntervallFire = ((Integer)fireRateFireBallList.get(this.fireIntervallTierFire)).intValue();
    this.firingIntervallArrow = ((Integer)fireRateArrowList.get(this.firintIntervallTierArrow)).intValue();
  }
  
  public void setTurretTier(int i)
  {
    this.turretTier = i;
  }
  
  public Material getAmmo()
  {
    Material ammo;
    switch (this.turretTier)
    {
    case 0: 
      ammo = Material.ARROW;
      break;
    case 1: 
      ammo = Material.SNOW_BALL;
      break;
    case 2: 
      ammo = Material.FIREBALL;
      break;
    case 3: 
      ammo = Material.ENDER_PEARL;
      break;
    default: 
      ammo = Material.ARROW;
    }
    return ammo;
  }
  
  public void despawn()
  {
    ((CraftWorld)this.location.getWorld()).getHandle().removeEntity(this.entity);
    this.entity.dead = true;
  }
  
  public void spawn()
  {
    if (!this.location.getLocation().getChunk().isLoaded()) {
      this.location.getLocation().getChunk().load();
    }
    ((CraftWorld)this.location.getWorld()).getHandle().addEntity(this.entity);
  }
  
  public OfflinePlayer getShooter()
  {
    return Bukkit.getOfflinePlayer(this.ownerName);
  }
  
  public <T extends Projectile> T launchProjectile(Class<? extends T> aClass)
  {
    return null;
  }
  
  public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector)
  {
    return null;
  }
}

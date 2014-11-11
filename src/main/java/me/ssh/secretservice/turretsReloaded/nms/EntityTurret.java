package me.ssh.secretservice.turretsReloaded.nms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.ssh.secretservice.turretsReloaded.Turret;
import me.ssh.secretservice.turretsReloaded.TurretOwner;
import me.ssh.secretservice.turretsReloaded.TurretUpgrade;
import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import me.ssh.secretservice.turretsReloaded.targeting.TargetAssessment;
import me.ssh.secretservice.turretsReloaded.targeting.TargetAssessor;
import me.ssh.secretservice.turretsReloaded.utils.NationsUtil;
import me.ssh.secretservice.turretsReloaded.utils.RandomUtils;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.EntityArrow;
import net.minecraft.server.v1_7_R4.EntityEnderCrystal;
import net.minecraft.server.v1_7_R4.EntityEnderPearl;
import net.minecraft.server.v1_7_R4.EntitySmallFireball;
import net.minecraft.server.v1_7_R4.EntitySnowball;
import net.minecraft.server.v1_7_R4.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEnderPearl;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSmallFireball;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.NumberConversions;

public class EntityTurret
  extends EntityEnderCrystal
  implements InventoryHolder
{
  private static final double REBOUND = 0.1D;
  private static final double ITEM_SPAWN_DISTANCE = 1.2D;
  private final Turret turret;
  private final org.bukkit.World bukkitWorld;
  private final double pivotX;
  private final double pivotY;
  private final double pivotZ;
  private org.bukkit.entity.Entity target;
  
  public Turret getTurret()
  {
    return this.turret;
  }
  
  private int firingCooldown = 0;
  private int targetSearchCooldown = 0;
  private int targetSearchInterval = 10;
  private int turretLookMatchShooterCD = 0;
  private TurretsReloaded plugin;
  private int fireIntervallSlowness;
  private int rangeSlowness;
  private float accuracySlowness;
  private int fireIntervallBlindness;
  private int rangeBlindness;
  private float accuracyBlindness;
  
  public EntityTurret(Turret turret, org.bukkit.World world, double pivotX, double pivotY, double pivotZ)
  {
    super(((CraftWorld)world).getHandle());
    this.turret = turret;
    this.bukkitWorld = world;
    this.pivotX = pivotX;
    this.pivotY = pivotY;
    this.pivotZ = pivotZ;
    this.plugin = turret.getPlugin();
    setPosition(this.pivotX, this.pivotY, this.pivotZ);
    SimpleConfig conf = this.plugin.getConf();
    this.fireIntervallSlowness = conf.getInt("settings.basic.values.SlownessTurret.fireRate");
    this.rangeSlowness = conf.getInt("settings.basic.values.SlownessTurret.range");
    double accuracySlownessDouble = conf.getDouble("settings.basic.values.SlownessTurret.accuracy");
    this.accuracySlowness = ((float)accuracySlownessDouble);
    this.fireIntervallBlindness = conf.getInt("settings.basic.values.BlindnessTurret.fireRate");
    this.rangeBlindness = conf.getInt("settings.basic.values.BlindnessTurret.range");
    double accuracyBlindnessDouble = conf.getDouble("settings.basic.values.BlindnessTurret.accuracy");
    this.accuracyBlindness = ((float)accuracyBlindnessDouble);
  }
  
  public boolean damageEntity(DamageSource damageSource, int damage)
  {
    net.minecraft.server.v1_7_R4.Entity nmsDamager = damageSource.getEntity();
    if (nmsDamager != null)
    {
      org.bukkit.entity.Entity damager = nmsDamager.getBukkitEntity();
      if ((damager instanceof LivingEntity)) {
        return super.damageEntity(damageSource, damage);
      }
      return true;
    }
    return super.damageEntity(damageSource, damage);
  }
  
  public void h()
  {
    setOnFire(0);
    if (this.locY < -64.0D) {
      G();
    }
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    
    this.motX = ((this.pivotX - this.locX) * 0.1D);
    this.motY = ((this.pivotY - this.locY) * 0.1D);
    this.motZ = ((this.pivotZ - this.locZ) * 0.1D);
    move(this.motX, this.motY, this.motZ);
    
    int firingInterval = 0;
    double range = 20.0D;
    float accuracy = 1.0F;
    switch (this.turret.getTurretTier())
    {
    case 0: 
      firingInterval = this.turret.getFiringIntervallArrow();
      range = this.turret.getRangeArrow();
      accuracy = this.turret.getAccuracyArrow();
      break;
    case 1: 
      firingInterval = this.fireIntervallSlowness;
      range = this.rangeSlowness;
      accuracy = this.accuracySlowness;
    case 2: 
      firingInterval = this.turret.getFireIntervallFire();
      range = this.turret.getRangeFire();
      accuracy = this.turret.getAccuracyFire();
      break;
    case 3: 
      firingInterval = this.fireIntervallBlindness;
      range = this.rangeBlindness;
      accuracy = this.accuracyBlindness;
    }
    if ((this.target != null) && (this.targetSearchCooldown == 0) && ((this.target instanceof LivingEntity)))
    {
      List<LivingEntity> curTarget = new ArrayList();
      curTarget.add((LivingEntity)this.target);
      filterTargets(curTarget);
      if (curTarget.size() > 0) {
        this.target = ((org.bukkit.entity.Entity)curTarget.get(0));
      } else {
        this.target = null;
      }
    }
    if ((this.target == null) && (this.targetSearchCooldown == 0))
    {
      org.bukkit.entity.Entity foundTarget = findTarget(range);
      if (foundTarget != null) {
        this.target = foundTarget;
      } else {
        this.targetSearchCooldown = this.targetSearchInterval;
      }
    }
    if (this.targetSearchCooldown > 0) {
      this.targetSearchCooldown -= 1;
    }
    boolean lockedOn = false;
    if (this.target != null)
    {
      net.minecraft.server.v1_7_R4.Entity nmsTarget = ((CraftEntity)this.target).getHandle();
      if (canSee(nmsTarget))
      {
        net.minecraft.server.v1_7_R4.World targetWorld = nmsTarget.world;
        double x = nmsTarget.locX;
        double y = nmsTarget.locY + nmsTarget.getHeadHeight();
        double z = nmsTarget.locZ;
        if ((targetWorld == this.world) && (!this.target.isDead()))
        {
          double dx = x - this.pivotX;
          double dy = y - this.pivotY;
          double dz = z - this.pivotZ;
          double distanceSquared = dx * dx + dy * dy + dz * dz;
          if (distanceSquared <= range * range)
          {
            lookAt(x, y, z);
            lockedOn = true;
          }
          else
          {
            this.target = null;
          }
        }
        else
        {
          this.target = null;
        }
      }
      else
      {
        this.target = null;
      }
    }
    b(this.yaw, this.pitch);
    this.motY = 0.0D;
    if ((lockedOn) && (this.firingCooldown == 0))
    {
      fireItemStack(accuracy, this.target);
      this.firingCooldown = firingInterval;
    }
    if (this.firingCooldown > 0) {
      this.firingCooldown -= 1;
    }
  }
  
  public org.bukkit.entity.Entity findTarget(double range)
  {
    List<net.minecraft.server.v1_7_R4.Entity> nmsEntities = this.world.getEntities(this, this.boundingBox.grow(range, range, range));
    List<LivingEntity> targets = new ArrayList();
    double rangeSquared = range * range;
    for (net.minecraft.server.v1_7_R4.Entity nmsEntity : nmsEntities) {
      if (nmsEntity != this)
      {
        double dx = nmsEntity.locX - this.locX;
        double dy = nmsEntity.locY - this.locY;
        double dz = nmsEntity.locZ - this.locZ;
        double distanceSquared = dx * dx + dy * dy + dz * dz;
        if (distanceSquared <= rangeSquared)
        {
          org.bukkit.entity.Entity entity = nmsEntity.getBukkitEntity();
          if ((entity instanceof LivingEntity)) {
            targets.add((LivingEntity)entity);
          }
        }
      }
    }
    if (targets.isEmpty()) {
      return null;
    }
    filterTargets(targets);
    while (!targets.isEmpty())
    {
      LivingEntity possibleTarget = (LivingEntity) RandomUtils.randomElement(targets, this.random);
      net.minecraft.server.v1_7_R4.Entity nmsPossibleTarget = ((CraftEntity)possibleTarget).getHandle();
      if (canSee(nmsPossibleTarget)) {
        return possibleTarget;
      }
      targets.remove(possibleTarget);
    }
    return null;
  }
  
  public void lookAt(double x, double y, double z)
  {
    double dx = -(x - this.locX);
    double dy = y - this.locY;
    double dz = -(z - this.locZ);
    double dh = Math.sqrt(dx * dx + dz * dz);
    this.yaw = ((float)Math.atan2(dz, dx) * 180.0F / 3.141593F);
    this.pitch = ((float)-Math.atan(dy / dh) * 180.0F / 3.141593F);
  }
  
  private void filterTargets(List<LivingEntity> targets)
  {
    Iterator<LivingEntity> it = targets.iterator();
    while (it.hasNext())
    {
      LivingEntity mob = (LivingEntity)it.next();
      TargetAssessment assessment = assessTarget(mob);
      if (assessment == TargetAssessment.EITHER) {
        if ((mob instanceof Player))
        {
          Player playerTarget = (Player)mob;
          
          TurretOwner turretOwner = getTurret().getTurretOwner();
          if (turretOwner.getPlayer() != playerTarget)
          {
            boolean isHostileTarget;
            if (this.plugin.hasFactions()) {
              isHostileTarget = NationsUtil.nationsCheck(this.turret.getNation(), playerTarget).booleanValue();
            } else {
              isHostileTarget = true;
            }
            if (playerTarget.getName().equalsIgnoreCase(Bukkit.getOfflinePlayer(this.turret.getOwnerName()).getName())) {
              isHostileTarget = false;
            }
            if (isHostileTarget) {
              assessment = TargetAssessment.HOSTILE;
            } else {
              assessment = TargetAssessment.NOT_HOSTILE;
            }
          }
          else
          {
            assessment = TargetAssessment.NOT_HOSTILE;
          }
        }
        else
        {
          assessment = TargetAssessment.NOT_HOSTILE;
        }
      }
      if (assessment != TargetAssessment.HOSTILE) {
        it.remove();
      }
    }
  }
  
  private TargetAssessment assessTarget(LivingEntity mob)
  {
    TargetAssessment overallAssessment = TargetAssessment.MEH;
    for (TargetAssessor assessor : this.turret.getTargetAssessors())
    {
      TargetAssessment assessment = assessor.assessMob(mob);
      if (assessment != TargetAssessment.MEH) {
        overallAssessment = assessment;
      }
    }
    return overallAssessment;
  }
  
  public void fireItemStack(float accuracy, org.bukkit.entity.Entity target)
  {
    ItemStack itemStack = new ItemStack(this.turret.getAmmo(), 1);
    fireItemStack(itemStack, accuracy, target);
  }
  
  public void fireItemStack(ItemStack itemStack, float accuracy, org.bukkit.entity.Entity target)
  {
    int blockX = NumberConversions.floor(this.locX);
    int blockY = NumberConversions.floor(this.locY);
    int blockZ = NumberConversions.floor(this.locZ);
    if (itemStack != null)
    {
      double rYaw = (this.yaw - 90.0D) * 3.141592653589793D / 180.0D;
      double rPitch = this.pitch * 3.141592653589793D / 180.0D;
      double factorX = Math.sin(rYaw) * -Math.cos(rPitch);
      double factorY = -Math.sin(rPitch);
      double factorZ = Math.cos(rYaw) * Math.cos(rPitch);
      double towardsX = target.getLocation().getX();
      double towardsZ = target.getLocation().getZ();
      double testingX = 0.0D;
      double testingZ = 0.0D;
      if (towardsX < this.locX) {
        testingX = -0.5D;
      } else {
        testingX = 0.5D;
      }
      if (towardsZ < this.locZ) {
        testingZ = -0.5D;
      } else {
        testingZ = 0.5D;
      }
      double itemX = this.locX + testingX - 1.0D * factorX;
      double itemY = this.locY + 1.2D * factorY;
      double itemZ = this.locZ + testingZ - 1.0D * factorZ;
      factorX = -factorX;
      factorZ = -factorZ;
      
      double dx = target.getLocation().getX() - this.locX;
      double dy = target.getLocation().getY() - this.locY;
      double dz = target.getLocation().getZ() - this.locZ;
      double distanceSquared = dx * dx + dy * dy + dz * dz;
      double speedD = Math.sqrt(distanceSquared);
      float speed = (float)speedD / 5.0F;
      switch (itemStack.getType().ordinal())
      {
      case 1: 
        EntityArrow entityArrow = new EntityArrow(this.world, itemX, itemY, itemZ);
        entityArrow.getBukkitEntity().setMetadata("no_pickup", new FixedMetadataValue(getTurret().getPlugin(), Boolean.valueOf(true)));
        if (this.turret.isFireArrow()) {
          entityArrow.setOnFire(1);
        }
        entityArrow.shoot(factorX, factorY, factorZ, speed, accuracy);
        entityArrow.fromPlayer = 1;
        this.world.addEntity(entityArrow);
        ((CraftArrow)entityArrow.getBukkitEntity()).setShooter(this.turret);
        this.world.triggerEffect(1002, blockX, blockY, blockZ, 0);
        break;
      case 2: 
        EntitySnowball entitySnowball = new EntitySnowball(this.world, itemX, itemY, itemZ);
        entitySnowball.shoot(factorX, factorY, factorZ, speed, accuracy);
        this.world.addEntity(entitySnowball);
        ((CraftSnowball)entitySnowball.getBukkitEntity()).setShooter(this.turret);
        this.world.triggerEffect(1002, blockX, blockY, blockZ, 0);
        break;
      case 3: 
        EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.world, itemX, itemY, itemZ, factorX, factorY, factorZ);
        this.world.addEntity(entitySmallFireball);
        ((CraftSmallFireball)entitySmallFireball.getBukkitEntity()).setShooter(this.turret);
        this.world.triggerEffect(1009, blockX, blockY, blockZ, 0);
        
        break;
      case 4: 
        EntityEnderPearl enderPearl = new EntityEnderPearl(this.world);
        enderPearl.shoot(factorX, factorY, factorZ, speed, accuracy);
        this.world.addEntity(enderPearl);
        ((CraftEnderPearl)enderPearl.getBukkitEntity()).setShooter(this.turret);
        this.world.triggerEffect(1002, blockX, blockY, blockZ, 0);
      }
      this.world.triggerEffect(2000, blockX, blockY, blockZ, 0);
    }
    else
    {
      this.world.triggerEffect(1001, blockX, blockY, blockZ, 0);
    }
  }
  
  private boolean canSee(net.minecraft.server.v1_7_R4.Entity nmsEntity)
  {
    return this.world.rayTrace(Vec3D.a(this.locX, this.locY + getHeadHeight(), this.locZ), Vec3D.a(nmsEntity.locX, nmsEntity.locY + nmsEntity.getHeadHeight(), nmsEntity.locZ), false, false, false) == null;
  }
  
  public Inventory getInventory()
  {
    return TurretUpgrade.openTierInventory(this.turret, this.plugin);
  }
  
  public Inventory getArrowUpdates()
  {
    return TurretUpgrade.openArrowUpgrades(this.turret, this.plugin);
  }
  
  public Inventory getSlownessUpgrades()
  {
    return TurretUpgrade.openSlownessUpgrades(this.turret, this.plugin);
  }
  
  public Inventory getFireballUpgrades()
  {
    return TurretUpgrade.openFireballUpgrades(this.turret, this.plugin);
  }
  
  public Inventory getBlindnessUpgrades()
  {
    return TurretUpgrade.openBlindnessUpgrades(this.turret, this.plugin);
  }
}

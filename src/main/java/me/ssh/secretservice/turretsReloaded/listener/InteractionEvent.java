package me.ssh.secretservice.turretsReloaded.listener;

import com.codelanx.nations.Nations;
import com.codelanx.nations.chunk.ChunkService;
import com.codelanx.nations.econ.NationEconomy;
import com.codelanx.nations.econ.NationEconomy.ChargeStatus;
import com.codelanx.nations.nation.Nation;
import com.codelanx.nations.nation.NationManager;
import com.codelanx.nations.serialize.NChunk;
import me.ssh.secretservice.turretsReloaded.Turret;
import me.ssh.secretservice.turretsReloaded.TurretOwner;
import me.ssh.secretservice.turretsReloaded.TurretUpgrade;
import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import me.ssh.secretservice.turretsReloaded.nms.EntityTurret;
import me.ssh.secretservice.turretsReloaded.nms.MyEnderPearl;
import me.ssh.secretservice.turretsReloaded.utils.BlockLocation;
import me.ssh.secretservice.turretsReloaded.utils.Lang;
import me.ssh.secretservice.turretsReloaded.utils.Upgrade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_7_R4.EntityEnderCrystal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEnderCrystal;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class InteractionEvent
  implements Listener
{
  private TurretsReloaded plugin;
  private SimpleConfig conf;
  
  public InteractionEvent(TurretsReloaded pl)
  {
    this.plugin = pl;
    this.conf = pl.getConf();
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
  public void interact(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
      (p.getItemInHand().getType().equals(Material.FENCE)))
    {
      ItemStack item = p.getItemInHand();
      if ((item.hasItemMeta()) && (item.getItemMeta().hasDisplayName()) && (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', this.conf.getString("settings.basic.item.name")))))
      {
        World world = p.getLocation().getWorld();
        if ((world.equals(Nations.getInstance().getNationManager().getNation(p).getWorld())) && 
          (ChunkService.canEdit(new NChunk(p.getLocation().getChunk()), p)))
        {
          BlockLocation loc = new BlockLocation(p.getLocation().getWorld(), e.getClickedBlock().getX(), e.getClickedBlock().getY() + 1, e.getClickedBlock().getZ());
          TurretOwner owner = this.plugin.getTurretOwner(p.getUniqueId());
          String natioName = Nations.getInstance().getNationManager().getNation(p).getName();
          Turret turret = new Turret(loc, p.getUniqueId(), p.getName(), natioName, this.plugin);
          owner.addTurret(turret);
          owner.setNumTurretsOwned(owner.getNumTurretsOwned() + 1);
          if (item.getAmount() > 1)
          {
            item.setAmount(item.getAmount() - 1);
            p.setItemInHand(item);
          }
          else
          {
            p.setItemInHand(new ItemStack(Material.AIR));
          }
          p.updateInventory();
          this.plugin.addTurret(turret);
          turret.spawn();
          Lang.sendMessage(p, Lang.LEGACY_TURRET_CREATED, new Object[0]);
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void interactEntity(PlayerInteractEntityEvent e)
  {
    Player p = e.getPlayer();
    if (e.getRightClicked().getType().equals(EntityType.ENDER_CRYSTAL))
    {
      EntityEnderCrystal nmsEnderCrystal = ((CraftEnderCrystal)e.getRightClicked()).getHandle();
      if ((nmsEnderCrystal instanceof EntityTurret))
      {
        EntityTurret entityTurret = (EntityTurret)nmsEnderCrystal;
        Turret turret = entityTurret.getTurret();
        if (p.getUniqueId().equals(turret.getOwnerUUID()))
        {
          if (this.plugin.getRemove().contains(this.plugin.getTurretOwner().get(p.getUniqueId())))
          {
            turret.despawn();
            this.plugin.getTurrets().remove(turret);
            Lang.sendMessage(p, Lang.REMOVE_TURRET, new Object[0]);
            this.plugin.remRemove((TurretOwner)this.plugin.getTurretOwner().get(p.getUniqueId()));
          }
          else
          {
            Inventory inv = TurretUpgrade.openTierInventory(turret, this.plugin);
            p.openInventory(inv);
          }
        }
        else if ((p.hasPermission("turrets.forceremove")) && 
          (this.plugin.getAdminRemove().contains(p.getUniqueId())))
        {
          turret.despawn();
          this.plugin.getTurrets().remove(turret);
          Lang.sendMessage(p, Lang.REMOVE_TURRET, new Object[0]);
          this.plugin.remAdminRemove(p.getUniqueId());
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void inventoryEvent(InventoryClickEvent e)
  {
    if ((e.getInventory().getHolder() instanceof EntityTurret))
    {
      final EntityTurret turr = (EntityTurret)e.getInventory().getHolder();
      if (e.getInventory().getName().equals("Turret Upgrades"))
      {
        if ((e.getCurrentItem().hasItemMeta()) && (e.getCurrentItem().getItemMeta().hasDisplayName()))
        {
          String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
          List<String> possibleNames = new ArrayList();
          possibleNames.add(getString("upgrades.items.turretTier.ArrowTier.displayName"));
          possibleNames.add(getString("upgrades.items.turretTier.SlownessTier.displayName"));
          possibleNames.add(getString("upgrades.items.turretTier.FireballTier.displayName"));
          possibleNames.add(getString("upgrades.items.turretTier.BlindnessTier.displayName"));
          if (e.getCurrentItem().getDurability() == 14)
          {
            Player p = (Player)e.getWhoClicked();
            List<Integer> prices = this.plugin.getUpgrade().getTurretTierPrices();
            if (((String)possibleNames.get(0)).equals(displayName))
            {
              if (Nations.getInstance().getEconomy().canCharge(p, ((Integer)prices.get(0)).intValue()).getStatus())
              {
                turr.getTurret().setTurretTier(0);
                p.closeInventory();
                p.openInventory(TurretUpgrade.openTierInventory(turr.getTurret(), this.plugin));
                Nations.getInstance().getEconomy().charge(p, ((Integer)prices.get(0)).intValue());
              }
            }
            else if (((String)possibleNames.get(1)).equals(displayName))
            {
              if (Nations.getInstance().getEconomy().canCharge(p, ((Integer)prices.get(1)).intValue()).getStatus())
              {
                turr.getTurret().setTurretTier(1);
                p.closeInventory();
                p.openInventory(TurretUpgrade.openTierInventory(turr.getTurret(), this.plugin));
                Nations.getInstance().getEconomy().charge(p, ((Integer)prices.get(1)).intValue());
              }
            }
            else if (((String)possibleNames.get(2)).equals(displayName))
            {
              if (Nations.getInstance().getEconomy().canCharge(p, ((Integer)prices.get(2)).intValue()).getStatus())
              {
                turr.getTurret().setTurretTier(2);
                p.closeInventory();
                p.openInventory(TurretUpgrade.openTierInventory(turr.getTurret(), this.plugin));
                Nations.getInstance().getEconomy().charge(p, ((Integer)prices.get(2)).intValue());
              }
            }
            else if ((((String)possibleNames.get(3)).equals(displayName)) && 
              (Nations.getInstance().getEconomy().canCharge(p, ((Integer)prices.get(3)).intValue()).getStatus()))
            {
              turr.getTurret().setTurretTier(3);
              p.closeInventory();
              p.openInventory(TurretUpgrade.openTierInventory(turr.getTurret(), this.plugin));
              Nations.getInstance().getEconomy().charge(p, ((Integer)prices.get(3)).intValue());
            }
          }
          else
          {
            Player p = (Player)e.getWhoClicked();
            if (((String)possibleNames.get(0)).equals(displayName)) {
              e.getWhoClicked().openInventory(turr.getArrowUpdates());
            } else if (((String)possibleNames.get(1)).equals(displayName)) {
              e.getWhoClicked().openInventory(turr.getSlownessUpgrades());
            } else if (((String)possibleNames.get(2)).equals(displayName)) {
              e.getWhoClicked().openInventory(turr.getFireballUpgrades());
            } else if (((String)possibleNames.get(3)).equals(displayName)) {
              e.getWhoClicked().openInventory(turr.getBlindnessUpgrades());
            }
          }
          e.setCancelled(true);
        }
      }
      else if (e.getInventory().getName().equals("Arrow Upgrades"))
      {
        if (e.getCurrentItem().getDurability() == 14)
        {
          final Player p = (Player)e.getWhoClicked();
          String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
          List<String> possibleNamesForRange = new ArrayList();
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.one.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.two.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.three.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.four.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.five.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.six.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.seven.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.eight.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.nine.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.ArrowTurret.rangeTier.ten.displayName"));
          
          List<String> possibleNamesForAccuracy = new ArrayList();
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.one.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.two.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.three.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.four.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.five.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.six.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.seven.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.eight.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.nine.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.ArrowTurret.accuracyTier.ten.displayName"));
          
          List<String> possibleNamesForFireRate = new ArrayList();
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.one.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.two.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.three.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.four.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.five.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.six.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.seven.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.eight.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.nine.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.ArrowTurret.fireRateTier.ten.displayName"));
          if (possibleNamesForAccuracy.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretAccuracyArrowPrices();
            double price = ((Integer)prices.get(turr.getTurret().getAccuracyTierArrow())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setAccuracyTierArrow(turr.getTurret().getAccuracyTierArrow() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openArrowUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
          else if (possibleNamesForRange.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretRangeArrowPrices();
            double price = ((Integer)prices.get(turr.getTurret().getRangeTierArrow())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setRangeTierArrow(turr.getTurret().getRangeTierArrow() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openArrowUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
          else if (possibleNamesForFireRate.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretFirerateArrowPrices();
            double price = ((Integer)prices.get(turr.getTurret().getFirintIntervallTierArrow())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setFirintIntervallTierArrow(turr.getTurret().getFirintIntervallTierArrow() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openArrowUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
          else if (displayName.equals(getString("upgrades.items.ArrowTurret.fireUpgrade.displayName")))
          {
            if (Nations.getInstance().getEconomy().canCharge(p, this.plugin.getUpgrade().getTurretFireArrowPrice()).getStatus())
            {
              turr.getTurret().setFireArrow(true);
              Nations.getInstance().getEconomy().charge(p, this.plugin.getUpgrade().getTurretFireArrowPrice());
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openArrowUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
        }
        e.setCancelled(true);
      }
      else if (e.getInventory().getName().equals("Fireball Upgrades"))
      {
        if (e.getCurrentItem().getDurability() == 14)
        {
          final Player p = (Player)e.getWhoClicked();
          String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
          List<String> possibleNamesForRange = new ArrayList();
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.one.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.two.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.three.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.four.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.five.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.six.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.seven.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.eight.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.nine.displayName"));
          possibleNamesForRange.add(getString("upgrades.items.FireBallTurret.rangeTier.ten.displayName"));
          
          List<String> possibleNamesForAccuracy = new ArrayList();
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.one.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.two.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.three.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.four.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.five.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.six.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.seven.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.eight.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.nine.displayName"));
          possibleNamesForAccuracy.add(getString("upgrades.items.FireBallTurret.accuracyTier.ten.displayName"));
          
          List<String> possibleNamesForFireRate = new ArrayList();
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.one.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.two.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.three.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.four.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.five.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.six.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.seven.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.eight.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.nine.displayName"));
          possibleNamesForFireRate.add(getString("upgrades.items.FireBallTurret.fireRateTier.ten.displayName"));
          if (possibleNamesForAccuracy.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretAccuracyFirePrices();
            double price = ((Integer)prices.get(turr.getTurret().getAccuracyTierFire())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setAccuracyTierFire(turr.getTurret().getAccuracyTierFire() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openFireballUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
          else if (possibleNamesForRange.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretRangeFirePrices();
            double price = ((Integer)prices.get(turr.getTurret().getRangeTierFire())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setRangeTierFire(turr.getTurret().getRangeTierFire() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openFireballUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
          else if (possibleNamesForFireRate.contains(displayName))
          {
            List<Integer> prices = this.plugin.getUpgrade().getTurretFirerateFirePrices();
            double price = ((Integer)prices.get(turr.getTurret().getFireIntervallTierFire())).intValue();
            if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
            {
              turr.getTurret().setFireIntervallTierFire(turr.getTurret().getFireIntervallTierFire() + 1);
              Nations.getInstance().getEconomy().charge(p, price);
              p.closeInventory();
              Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
              {
                public void run()
                {
                  p.openInventory(TurretUpgrade.openFireballUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
                }
              }, 1L);
              

              turr.getTurret().update();
            }
            else
            {
              Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
            }
          }
        }
        e.setCancelled(true);
      }
      else if (e.getInventory().getName().equals("Slowness Upgrades"))
      {
        if (e.getCurrentItem().getDurability() == 14)
        {
          final Player p = (Player)e.getWhoClicked();
          List<Integer> prices = this.plugin.getUpgrade().getTurretSlownessPrices();
          double price = ((Integer)prices.get(turr.getTurret().getSlownessTier())).intValue();
          if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
          {
            turr.getTurret().setSlownessTier(turr.getTurret().getSlownessTier() + 1);
            Nations.getInstance().getEconomy().charge(p, price);
            p.closeInventory();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
              public void run()
              {
                p.openInventory(TurretUpgrade.openSlownessUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
              }
            }, 1L);
            

            turr.getTurret().update();
          }
          else
          {
            Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
          }
        }
        e.setCancelled(true);
      }
      else if (e.getInventory().getName().equals("Blindness Upgrades"))
      {
        if (e.getCurrentItem().getDurability() == 14)
        {
          final Player p = (Player)e.getWhoClicked();
          List<Integer> prices = this.plugin.getUpgrade().getTurretBlindessPrices();
          double price = ((Integer)prices.get(turr.getTurret().getBlindnessTier())).intValue();
          if (Nations.getInstance().getEconomy().canCharge(p, price).getStatus())
          {
            turr.getTurret().setBlindnessTier(turr.getTurret().getBlindnessTier() + 1);
            Nations.getInstance().getEconomy().charge(p, price);
            p.closeInventory();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
              public void run()
              {
                p.openInventory(TurretUpgrade.openBlindnessUpgrades(turr.getTurret(), InteractionEvent.this.plugin));
              }
            }, 1L);
            

            turr.getTurret().update();
          }
          else
          {
            Lang.sendMessage(p, Lang.LEGACY_UPGRADE_NOT_ENOUGH_MONEY, new Object[0]);
          }
        }
        e.setCancelled(true);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onChunkUnload(ChunkUnloadEvent event)
  {
    for (Turret t : this.plugin.getTurrets()) {
      if (t.getLocation().getLocation().getChunk().equals(event.getChunk())) {
        event.setCancelled(true);
      }
    }
  }
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
  {
    if ((e.getEntity() instanceof CraftEnderCrystal))
    {
      EntityEnderCrystal nmsEnderCrystal = ((CraftEnderCrystal)e.getEntity()).getHandle();
      if ((nmsEnderCrystal instanceof EntityTurret))
      {
        e.setCancelled(true);
        return;
      }
    }
    if ((this.plugin.hasFactions()) && 
      ((e.getEntity() instanceof Player)) && 
      ((e.getDamager() instanceof Projectile)))
    {
      Projectile projectile = (Projectile)e.getDamager();
      if ((projectile.getShooter() instanceof Turret))
      {
        Turret turret = (Turret)projectile.getShooter();
        Nation nation1 = Nations.getInstance().getNationManager().getNation(Bukkit.getOfflinePlayer(turret.getOwnerName()).getName());
        Nation nation2 = Nations.getInstance().getNationManager().getNation(((Player)e.getEntity()).getName());
        if ((projectile instanceof Arrow))
        {
          if (turret.getTurretOwner().getPlayer().equals(e.getEntity())) {
            e.setCancelled(true);
          }
        }
        else if ((projectile instanceof Snowball))
        {
          if (turret.getTurretOwner().getPlayer().equals(e.getEntity()))
          {
            e.setCancelled(true);
          }
          else
          {
            List<?> list = this.plugin.getConf().getList("settings.basic.values.SlownessTurret.slownessDuration");
            int tier = turret.getSlownessTier();
            int time = 0;
            
            Object o = list.get(tier);
            if ((o instanceof Integer))
            {
              int i = ((Integer)o).intValue();
              time = i;
            }
            Player p = (Player)e.getEntity();
            
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 1));
          }
        }
        else if ((projectile instanceof Fireball))
        {
          if (turret.getTurretOwner().getPlayer().equals(e.getEntity())) {
            e.setCancelled(true);
          }
        }
        else if ((projectile instanceof MyEnderPearl)) {
          if (turret.getTurretOwner().getPlayer().equals(e.getEntity()))
          {
            e.setCancelled(true);
          }
          else
          {
            List<?> list = this.plugin.getConf().getList("settings.basic.values.BlindnessTurret.BlindnessDuration");
            int tier = turret.getSlownessTier();
            int time = 0;
            
            Object o = list.get(tier);
            if ((o instanceof Integer))
            {
              int i = ((Integer)o).intValue();
              time = i;
            }
            Player p = (Player)e.getEntity();
            
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time * 20, 1));
          }
        }
        if ((nation1 != null) && (nation1.equals(nation2))) {
          e.setCancelled(true);
        }
      }
    }
  }
  
  private String getString(String path)
  {
    return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', this.conf.getString(path)));
  }
}

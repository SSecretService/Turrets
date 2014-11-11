package me.ssh.secretservice.turretsReloaded.commands;


import com.codelanx.nations.Nations;
import me.ssh.secretservice.turretsReloaded.TurretOwner;
import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import me.ssh.secretservice.turretsReloaded.utils.Lang;
import java.util.ArrayList;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

public class TurretCommand
  implements CommandExecutor
{
  private Economy eco;
  private SimpleConfig conf;
  private TurretsReloaded plugin;
  private ItemStack turretPost;
  
  public TurretCommand(TurretsReloaded pl)
  {
    this.plugin = pl;
    this.eco = pl.getEco();
    this.conf = pl.getConf();
    
    this.turretPost = new ItemStack(Material.FENCE);
    ItemMeta meta = this.turretPost.getItemMeta();
    this.turretPost.setAmount(1);
    
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.conf.getString("settings.basic.item.name")));
    List<?> loreList = this.conf.getList("settings.basic.item.lore");
    List<String> defStringList = new ArrayList();
    for (Object loreListObject : loreList) {
      if ((loreListObject instanceof String)) {
        defStringList.add(ChatColor.translateAlternateColorCodes('&', loreListObject.toString()));
      }
    }
    meta.setLore(defStringList);
    this.turretPost.setItemMeta(meta);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((cmd.getName().equalsIgnoreCase("turret")) && ((sender instanceof Player)) && (sender.hasPermission("turrets.usage")))
    {
      final Player p = (Player)sender;
      if (Nations.getInstance().getNationManager().getNation(p) == null)
      {
        Lang.sendMessage(p, Lang.LEGACY_NO_NATION, new Object[0]);
        return true;
      }
      if (args.length == 0)
      {
        TurretOwner owner;
        if (this.plugin.turretOwnerExists(p.getUniqueId()))
        {
          owner = this.plugin.getTurretOwner(p.getUniqueId());
        }
        else
        {
          int maxAmmount = this.conf.getInt("settings.turretOwners.maxTurretsPerBasicPlayer");
          if (p.hasPermission("turrets.advanced")) {
            maxAmmount = this.conf.getInt("settings.turretOwners.maxTurretsPerAdvPlayer");
          }
          owner = new TurretOwner(p.getUniqueId(), p.getName(), 0, maxAmmount);
          this.plugin.addTurretOwner(owner);
        }
        if (owner.getNumTurretsOwned() < owner.getMaxTurretsAllowed())
        {
          if (Nations.getInstance().getEconomy().canCharge(p, ((Integer)this.plugin.getTurretPrices().get(owner.getNumTurretsOwned())).intValue()).getStatus())
          {
            Nations.getInstance().getEconomy().charge(p, ((Integer)this.plugin.getTurretPrices().get(owner.getNumTurretsOwned())).intValue());
            p.getInventory().addItem(new ItemStack[] { this.turretPost });
            owner.setNumTurretsOwned(owner.getNumTurretsOwned() + 1);
            Lang.sendMessage(p, Lang.LEGACY_TURRET_BOUGHT, new Object[0]);
          }
          else
          {
            Lang.sendMessage(p, Lang.LEGACY_TURRET_NOT_ENOUGH_MONEY, new Object[0]);
          }
        }
        else {
          Lang.sendMessage(p, Lang.LEGACY_TURRET_MAXIMUM_REACHED, new Object[0]);
        }
        return true;
      }
      if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("remove"))
        {
          if (p.hasPermission("turrets.forceremove"))
          {
            this.plugin.addAdminRemove(p.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
              public void run()
              {
                TurretCommand.this.plugin.remAdminRemove(p.getUniqueId());
              }
            }, 600L);
          }
          else if (this.plugin.turretOwnerExists(p.getUniqueId()))
          {
            final TurretOwner owner = this.plugin.getTurretOwner(p.getUniqueId());
            this.plugin.addRemove(owner);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
              public void run()
              {
                TurretCommand.this.plugin.remRemove(owner);
              }
            }, 600L);
          }
          Lang.sendMessage(p, Lang.REMOVE_TURRET_TIMER, new Object[0]);
        }
        return true;
      }
    }
    return false;
  }
}

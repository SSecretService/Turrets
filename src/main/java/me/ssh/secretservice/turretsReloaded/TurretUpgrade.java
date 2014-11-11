package me.ssh.secretservice.turretsReloaded;

import me.ssh.secretservice.turretsReloaded.utils.Upgrade;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class TurretUpgrade
{
  private static Wool greenWool = new Wool(DyeColor.GREEN);
  
  public static Inventory openTierInventory(Turret turret, TurretsReloaded pl)
  {
    Inventory inv = Bukkit.createInventory(turret.getEntity(), 9, "Turret Upgrades");
    List<ItemStack> itemStackList = pl.getUpgrade().getTurretTiers();
    for (int i = 0; i < itemStackList.size(); i++) {
      if (turret.getTurretTier() == i)
      {
        ItemStack newItemStack = greenWool.toItemStack();
        ItemMeta newItemMeta = ((ItemStack)itemStackList.get(i)).getItemMeta();
        newItemStack.setItemMeta(newItemMeta);
        inv.addItem(new ItemStack[] { newItemStack });
      }
      else
      {
        inv.addItem(new ItemStack[] { (ItemStack)itemStackList.get(i) });
      }
    }
    return inv;
  }
  
  public static Inventory openSlownessUpgrades(Turret turret, TurretsReloaded pl)
  {
    Inventory inv = Bukkit.createInventory(turret.getEntity(), 9, "Slowness Upgrades");
    ItemStack currentSlownessLevel = greenWool.toItemStack();
    ItemMeta currentMeta = ((ItemStack)pl.getUpgrade().getTurretSlowness().get(turret.getSlownessTier())).getItemMeta();
    currentSlownessLevel.setItemMeta(currentMeta);
    
    ItemStack nextSlownessLevel = (ItemStack)pl.getUpgrade().getTurretSlowness().get(turret.getSlownessTier() + 1);
    
    inv.addItem(new ItemStack[] { currentSlownessLevel });
    inv.addItem(new ItemStack[] { nextSlownessLevel });
    
    return inv;
  }
  
  public static Inventory openBlindnessUpgrades(Turret turret, TurretsReloaded pl)
  {
    Inventory inv = Bukkit.createInventory(turret.getEntity(), 9, "Blindness Upgrades");
    ItemStack currentBlindnessMeta = greenWool.toItemStack();
    ItemMeta currentMeta = ((ItemStack)pl.getUpgrade().getTurretBlindess().get(turret.getBlindnessTier())).getItemMeta();
    currentBlindnessMeta.setItemMeta(currentMeta);
    
    ItemStack nextSlownessLevel = (ItemStack)pl.getUpgrade().getTurretBlindess().get(turret.getBlindnessTier() + 1);
    
    inv.addItem(new ItemStack[] { currentBlindnessMeta });
    inv.addItem(new ItemStack[] { nextSlownessLevel });
    
    return inv;
  }
  
  public static Inventory openFireballUpgrades(Turret turret, TurretsReloaded pl)
  {
    Inventory inv = Bukkit.createInventory(turret.getEntity(), 9, "Fireball Upgrades");
    List<ItemStack> accuracyList = pl.getUpgrade().getTurretAccuracyFire();
    List<ItemStack> fireRateList = pl.getUpgrade().getTurretFirerateFire();
    List<ItemStack> rangeList = pl.getUpgrade().getTurretRangeFire();
    

    ItemStack currentAccuracyLevel = greenWool.toItemStack();
    ItemMeta currentAccracyMeta = ((ItemStack)accuracyList.get(turret.getAccuracyTierFire())).getItemMeta();
    currentAccuracyLevel.setItemMeta(currentAccracyMeta);
    
    ItemStack nextAccuracyLevel = (ItemStack)accuracyList.get(turret.getAccuracyTierFire() + 1);
    
    inv.setItem(0, currentAccuracyLevel);
    inv.setItem(1, nextAccuracyLevel);
    

    ItemStack currentFireRate = greenWool.toItemStack();
    ItemMeta currentFireRateMeta = ((ItemStack)fireRateList.get(turret.getFireIntervallTierFire())).getItemMeta();
    currentFireRate.setItemMeta(currentFireRateMeta);
    
    ItemStack nextFireRate = (ItemStack)fireRateList.get(turret.getFireIntervallTierFire() + 1);
    
    inv.setItem(3, currentFireRate);
    inv.setItem(4, nextFireRate);
    

    ItemStack currentRange = greenWool.toItemStack();
    ItemMeta currentRangeMeta = ((ItemStack)rangeList.get(turret.getRangeTierFire())).getItemMeta();
    currentRange.setItemMeta(currentRangeMeta);
    
    ItemStack nextRange = (ItemStack)rangeList.get(turret.getRangeTierFire() + 1);
    
    inv.setItem(6, currentRange);
    inv.setItem(7, nextRange);
    
    return inv;
  }
  
  public static Inventory openArrowUpgrades(Turret turret, TurretsReloaded pl)
  {
    Inventory inv = Bukkit.createInventory(turret.getEntity(), 9, "Arrow Upgrades");
    List<ItemStack> accuracyList = pl.getUpgrade().getTurretAccuracyArrow();
    List<ItemStack> fireRateList = pl.getUpgrade().getTurretFirerateArrow();
    List<ItemStack> rangeList = pl.getUpgrade().getTurretRangeArrow();
    ItemStack fireArrowStack = pl.getUpgrade().getTurretFireArrow();
    

    ItemStack currentAccuracyLevel = greenWool.toItemStack();
    ItemMeta currentAccracyMeta = ((ItemStack)accuracyList.get(turret.getAccuracyTierArrow())).getItemMeta();
    currentAccuracyLevel.setItemMeta(currentAccracyMeta);
    
    ItemStack nextAccuracyLevel = new ItemStack(Material.AIR);
    if (turret.getAccuracyTierArrow() + 1 < accuracyList.size()) {
      nextAccuracyLevel = (ItemStack)accuracyList.get(turret.getAccuracyTierArrow() + 1);
    }
    inv.setItem(0, currentAccuracyLevel);
    inv.setItem(1, nextAccuracyLevel);
    

    ItemStack currentFireRate = greenWool.toItemStack();
    ItemMeta currentFireRateMeta = ((ItemStack)fireRateList.get(turret.getFirintIntervallTierArrow())).getItemMeta();
    currentFireRate.setItemMeta(currentFireRateMeta);
    
    ItemStack nextFireRate = new ItemStack(Material.AIR);
    if (turret.getFirintIntervallTierArrow() + 1 < fireRateList.size()) {
      nextFireRate = (ItemStack)fireRateList.get(turret.getFirintIntervallTierArrow() + 1);
    }
    inv.setItem(3, currentFireRate);
    inv.setItem(4, nextFireRate);
    

    ItemStack currentRange = greenWool.toItemStack();
    ItemMeta currentRangeMeta = ((ItemStack)rangeList.get(turret.getRangeTierArrow())).getItemMeta();
    currentRange.setItemMeta(currentRangeMeta);
    
    ItemStack nextRange = new ItemStack(Material.AIR);
    if (turret.getRangeTierArrow() + 1 < rangeList.size()) {
      nextRange = (ItemStack)rangeList.get(turret.getRangeTierArrow() + 1);
    }
    inv.setItem(6, currentRange);
    inv.setItem(7, nextRange);
    if (turret.isFireArrow())
    {
      ItemStack fireArrow = greenWool.toItemStack();
      ItemMeta fireArrowMeta = fireArrowStack.getItemMeta();
      fireArrow.setItemMeta(fireArrowMeta);
      inv.setItem(8, fireArrow);
    }
    else
    {
      inv.setItem(8, fireArrowStack);
    }
    return inv;
  }
}

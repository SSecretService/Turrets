package me.ssh.secretservice.turretsReloaded.utils;


import java.util.ArrayList;
import java.util.List;

import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import me.ssh.secretservice.turretsReloaded.config.SimpleConfig;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class Upgrade
{
  private List<ItemStack> turretTiers;
  private List<ItemStack> turretRangeArrow;
  private List<ItemStack> turretAccuracyArrow;
  private List<ItemStack> turretFirerateArrow;
  private List<ItemStack> turretRangeFire;
  private List<ItemStack> turretAccuracyFire;
  private List<ItemStack> turretFirerateFire;
  private List<ItemStack> turretSlowness;
  private List<ItemStack> turretBlindess;
  private List<Integer> turretRangeArrowPrices;
  private List<Integer> turretAccuracyArrowPrices;
  private List<Integer> turretFirerateArrowPrices;
  private List<Integer> turretRangeFirePrices;
  private List<Integer> turretAccuracyFirePrices;
  private List<Integer> turretFirerateFirePrices;
  private List<Integer> turretSlownessPrices;
  private List<Integer> turretBlindessPrices;
  private List<Integer> turretTierPrices;
  private ItemStack turretFireArrow;
  private int turretFireArrowPrice;
  private SimpleConfig conf;
  private Wool redWool;
  
  public List<ItemStack> getTurretAccuracyArrow()
  {
    return this.turretAccuracyArrow;
  }
  
  public List<ItemStack> getTurretFirerateArrow()
  {
    return this.turretFirerateArrow;
  }
  
  public List<ItemStack> getTurretRangeArrow()
  {
    return this.turretRangeArrow;
  }
  
  public List<ItemStack> getTurretTiers()
  {
    return this.turretTiers;
  }
  
  public List<ItemStack> getTurretAccuracyFire()
  {
    return this.turretAccuracyFire;
  }
  
  public List<ItemStack> getTurretFirerateFire()
  {
    return this.turretFirerateFire;
  }
  
  public List<ItemStack> getTurretSlowness()
  {
    return this.turretSlowness;
  }
  
  public List<ItemStack> getTurretBlindess()
  {
    return this.turretBlindess;
  }
  
  public List<ItemStack> getTurretRangeFire()
  {
    return this.turretRangeFire;
  }
  
  public List<Integer> getTurretAccuracyArrowPrices()
  {
    return this.turretAccuracyArrowPrices;
  }
  
  public List<Integer> getTurretFirerateArrowPrices()
  {
    return this.turretFirerateArrowPrices;
  }
  
  public List<Integer> getTurretRangeFirePrices()
  {
    return this.turretRangeFirePrices;
  }
  
  public List<Integer> getTurretAccuracyFirePrices()
  {
    return this.turretAccuracyFirePrices;
  }
  
  public List<Integer> getTurretFirerateFirePrices()
  {
    return this.turretFirerateFirePrices;
  }
  
  public List<Integer> getTurretSlownessPrices()
  {
    return this.turretSlownessPrices;
  }
  
  public List<Integer> getTurretBlindessPrices()
  {
    return this.turretBlindessPrices;
  }
  
  public List<Integer> getTurretTierPrices()
  {
    return this.turretTierPrices;
  }
  
  public List<Integer> getTurretRangeArrowPrices()
  {
    return this.turretRangeArrowPrices;
  }
  
  public ItemStack getTurretFireArrow()
  {
    return this.turretFireArrow;
  }
  
  public int getTurretFireArrowPrice()
  {
    return this.turretFireArrowPrice;
  }
  
  public Upgrade(TurretsReloaded pl)
  {
    this.redWool = new Wool(DyeColor.RED);
    this.conf = pl.getConf();
    this.turretTiers = new ArrayList();
    this.turretRangeArrow = new ArrayList();
    this.turretAccuracyArrow = new ArrayList();
    this.turretFirerateArrow = new ArrayList();
    this.turretRangeFire = new ArrayList();
    this.turretAccuracyFire = new ArrayList();
    this.turretFirerateFire = new ArrayList();
    this.turretSlowness = new ArrayList();
    this.turretBlindess = new ArrayList();
    
    this.turretRangeArrowPrices = new ArrayList();
    this.turretAccuracyArrowPrices = new ArrayList();
    this.turretFirerateArrowPrices = new ArrayList();
    this.turretRangeFirePrices = new ArrayList();
    this.turretAccuracyFirePrices = new ArrayList();
    this.turretFirerateFirePrices = new ArrayList();
    this.turretSlownessPrices = new ArrayList();
    this.turretBlindessPrices = new ArrayList();
    this.turretTierPrices = new ArrayList();
    
    init();
  }
  
  private void init()
  {
    setupTurretTiers();
    setupTurretRangeArrow();
    setupTurretAccuracyArrow();
    setupTurretFirerateArrow();
    setupFireArrow();
    
    setupSlowness();
    
    setupTurretRangeFire();
    setupTurretAccuracyFire();
    setupTurretFirerateFire();
    
    setupBlindness();
    
    setupPrices();
  }
  
  private void setupPrices()
  {
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.two")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.three")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.four")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.five")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.six")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.seven")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.eight")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.nine")));
    this.turretRangeArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.rangeTier.ten")));
    
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.two")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.three")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.four")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.five")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.six")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.seven")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.eight")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.nine")));
    this.turretAccuracyArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.accuracyTier.ten")));
    
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.two")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.three")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.four")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.five")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.six")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.seven")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.eight")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.nine")));
    this.turretFirerateArrowPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.ArrowTurret.fireRateTier.ten")));
    
    this.turretFireArrowPrice = this.conf.getInt("upgrades.costs.ArrowTurret.fireUpgrade");
    

    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.two")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.three")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.four")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.five")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.six")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.seven")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.eight")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.nine")));
    this.turretRangeFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.rangeTier.ten")));
    
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.two")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.three")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.four")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.five")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.six")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.seven")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.eight")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.nine")));
    this.turretAccuracyFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.accuracyTier.ten")));
    
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.two")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.three")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.four")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.five")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.six")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.seven")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.eight")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.nine")));
    this.turretFirerateFirePrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.FireballTurret.fireRateTier.ten")));
    

    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.two")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.three")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.four")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.five")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.six")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.seven")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.eight")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.nine")));
    this.turretSlownessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.SlownessTurret.slownessUpgrade.ten")));
    

    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.two")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.three")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.four")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.five")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.six")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.seven")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.eight")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.nine")));
    this.turretBlindessPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.BlindnessTurret.blindnessUpgrade.ten")));
    

    this.turretTierPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.turretTier.ArrowTier")));
    this.turretTierPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.turretTier.SlownessTier")));
    this.turretTierPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.turretTier.FireballTier")));
    this.turretTierPrices.add(Integer.valueOf(this.conf.getInt("upgrades.costs.turretTier.BlindnessTier")));
  }
  
  private void setupSlowness()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.SlownessTurret.slownessUpgrade.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.SlownessTurret.slownessUpgrade.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretSlowness.add(one);
    this.turretSlowness.add(two);
    this.turretSlowness.add(three);
    this.turretSlowness.add(four);
    this.turretSlowness.add(five);
    this.turretSlowness.add(six);
    this.turretSlowness.add(seven);
    this.turretSlowness.add(eight);
    this.turretSlowness.add(nine);
    this.turretSlowness.add(ten);
  }
  
  private void setupBlindness()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.BlindnessTurret.blindnessUpgrade.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.BlindnessTurret.blindnessUpgrade.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretBlindess.add(one);
    this.turretBlindess.add(two);
    this.turretBlindess.add(three);
    this.turretBlindess.add(four);
    this.turretBlindess.add(five);
    this.turretBlindess.add(six);
    this.turretBlindess.add(seven);
    this.turretBlindess.add(eight);
    this.turretBlindess.add(nine);
    this.turretBlindess.add(ten);
  }
  
  private void setupFireArrow()
  {
    ItemStack fireArrow = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = fireArrow.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireUpgrade.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireUpgrade.lore"));
    fireArrow.setItemMeta(oneItemMeta);
    
    this.turretFireArrow = fireArrow;
  }
  
  private void setupTurretFirerateFire()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.fireRateTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.fireRateTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretFirerateArrow.add(one);
    this.turretFirerateArrow.add(two);
    this.turretFirerateArrow.add(three);
    this.turretFirerateArrow.add(four);
    this.turretFirerateArrow.add(five);
    this.turretFirerateArrow.add(six);
    this.turretFirerateArrow.add(seven);
    this.turretFirerateArrow.add(eight);
    this.turretFirerateArrow.add(nine);
    this.turretFirerateArrow.add(ten);
  }
  
  private void setupTurretAccuracyArrow()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.accuracyTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.accuracyTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretAccuracyArrow.add(one);
    this.turretAccuracyArrow.add(two);
    this.turretAccuracyArrow.add(three);
    this.turretAccuracyArrow.add(four);
    this.turretAccuracyArrow.add(five);
    this.turretAccuracyArrow.add(six);
    this.turretAccuracyArrow.add(seven);
    this.turretAccuracyArrow.add(eight);
    this.turretAccuracyArrow.add(nine);
    this.turretAccuracyArrow.add(ten);
  }
  
  private void setupTurretRangeArrow()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.ArrowTurret.rangeTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.ArrowTurret.rangeTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretRangeArrow.add(one);
    this.turretRangeArrow.add(two);
    this.turretRangeArrow.add(three);
    this.turretRangeArrow.add(four);
    this.turretRangeArrow.add(five);
    this.turretRangeArrow.add(six);
    this.turretRangeArrow.add(seven);
    this.turretRangeArrow.add(eight);
    this.turretRangeArrow.add(nine);
    this.turretRangeArrow.add(ten);
  }
  
  private void setupTurretRangeFire()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.rangeTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.rangeTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretRangeFire.add(one);
    this.turretRangeFire.add(two);
    this.turretRangeFire.add(three);
    this.turretRangeFire.add(four);
    this.turretRangeFire.add(five);
    this.turretRangeFire.add(six);
    this.turretRangeFire.add(seven);
    this.turretRangeFire.add(eight);
    this.turretRangeFire.add(nine);
    this.turretRangeFire.add(ten);
  }
  
  private void setupTurretAccuracyFire()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.accuracyTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.accuracyTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretAccuracyFire.add(one);
    this.turretAccuracyFire.add(two);
    this.turretAccuracyFire.add(three);
    this.turretAccuracyFire.add(four);
    this.turretAccuracyFire.add(five);
    this.turretAccuracyFire.add(six);
    this.turretAccuracyFire.add(seven);
    this.turretAccuracyFire.add(eight);
    this.turretAccuracyFire.add(nine);
    this.turretAccuracyFire.add(ten);
  }
  
  private void setupTurretFirerateArrow()
  {
    ItemStack one = this.redWool.toItemStack();
    ItemStack two = this.redWool.toItemStack();
    ItemStack three = this.redWool.toItemStack();
    ItemStack four = this.redWool.toItemStack();
    ItemStack five = this.redWool.toItemStack();
    ItemStack six = this.redWool.toItemStack();
    ItemStack seven = this.redWool.toItemStack();
    ItemStack eight = this.redWool.toItemStack();
    ItemStack nine = this.redWool.toItemStack();
    ItemStack ten = this.redWool.toItemStack();
    
    ItemMeta oneItemMeta = one.getItemMeta();
    oneItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.one.displayName"));
    oneItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.one.lore"));
    one.setItemMeta(oneItemMeta);
    
    ItemMeta twoItemMeta = two.getItemMeta();
    twoItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.two.displayName"));
    twoItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.two.lore"));
    two.setItemMeta(twoItemMeta);
    
    ItemMeta threeItemMeta = three.getItemMeta();
    threeItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.three.displayName"));
    threeItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.three.lore"));
    three.setItemMeta(threeItemMeta);
    
    ItemMeta fourItemMeta = four.getItemMeta();
    fourItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.four.displayName"));
    fourItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.four.lore"));
    four.setItemMeta(fourItemMeta);
    
    ItemMeta fiveItemMeta = five.getItemMeta();
    fiveItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.five.displayName"));
    fiveItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.five.lore"));
    five.setItemMeta(fiveItemMeta);
    
    ItemMeta sixItemMeta = six.getItemMeta();
    sixItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.six.displayName"));
    sixItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.six.lore"));
    six.setItemMeta(sixItemMeta);
    
    ItemMeta sevenItemMeta = seven.getItemMeta();
    sevenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.seven.displayName"));
    sevenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.seven.lore"));
    seven.setItemMeta(sevenItemMeta);
    
    ItemMeta eightItemMeta = eight.getItemMeta();
    eightItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.eight.displayName"));
    eightItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.eight.lore"));
    eight.setItemMeta(eightItemMeta);
    
    ItemMeta nineItemMeta = nine.getItemMeta();
    nineItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.nine.displayName"));
    nineItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.nine.lore"));
    nine.setItemMeta(nineItemMeta);
    
    ItemMeta tenItemMeta = ten.getItemMeta();
    tenItemMeta.setDisplayName(getString("upgrades.items.FireBallTurret.fireRateTier.ten.displayName"));
    tenItemMeta.setLore(getStringList("upgrades.items.FireBallTurret.fireRateTier.ten.lore"));
    ten.setItemMeta(tenItemMeta);
    
    this.turretFirerateFire.add(one);
    this.turretFirerateFire.add(two);
    this.turretFirerateFire.add(three);
    this.turretFirerateFire.add(four);
    this.turretFirerateFire.add(five);
    this.turretFirerateFire.add(six);
    this.turretFirerateFire.add(seven);
    this.turretFirerateFire.add(eight);
    this.turretFirerateFire.add(nine);
    this.turretFirerateFire.add(ten);
  }
  
  private void setupTurretTiers()
  {
    ItemStack arrowTier = this.redWool.toItemStack();
    ItemStack slownessTier = this.redWool.toItemStack();
    ItemStack fireballTier = this.redWool.toItemStack();
    ItemStack blindnessTier = this.redWool.toItemStack();
    
    ItemMeta arrowTierMeta = arrowTier.getItemMeta();
    arrowTierMeta.setDisplayName(getString("upgrades.items.turretTier.ArrowTier.displayName"));
    arrowTierMeta.setLore(getStringList("upgrades.items.turretTier.ArrowTier.lore"));
    arrowTier.setItemMeta(arrowTierMeta);
    
    ItemMeta slownessTierMeta = slownessTier.getItemMeta();
    slownessTierMeta.setDisplayName(getString("upgrades.items.turretTier.SlownessTier.displayName"));
    slownessTierMeta.setLore(getStringList("upgrades.items.turretTier.SlownessTier.lore"));
    slownessTier.setItemMeta(slownessTierMeta);
    
    ItemMeta fireballTierMeta = fireballTier.getItemMeta();
    fireballTierMeta.setDisplayName(getString("upgrades.items.turretTier.FireballTier.displayName"));
    fireballTierMeta.setLore(getStringList("upgrades.items.turretTier.FireballTier.lore"));
    fireballTier.setItemMeta(fireballTierMeta);
    
    ItemMeta blindnessTierMeta = blindnessTier.getItemMeta();
    blindnessTierMeta.setDisplayName(getString("upgrades.items.turretTier.BlindnessTier.displayName"));
    blindnessTierMeta.setLore(getStringList("upgrades.items.turretTier.BlindnessTier.lore"));
    blindnessTier.setItemMeta(blindnessTierMeta);
    
    this.turretTiers.add(arrowTier);
    this.turretTiers.add(slownessTier);
    this.turretTiers.add(fireballTier);
    this.turretTiers.add(blindnessTier);
  }
  
  private String getString(String path)
  {
    return ChatColor.translateAlternateColorCodes('&', this.conf.getString(path));
  }
  
  private List<String> getStringList(String path)
  {
    List<?> list = this.conf.getList(path);
    List<String> lore = new ArrayList();
    for (Object o : list) {
      if ((o instanceof String)) {
        lore.add(ChatColor.translateAlternateColorCodes('&', o.toString()));
      }
    }
    return lore;
  }
}

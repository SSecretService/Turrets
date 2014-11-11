package me.ssh.secretservice.turretsReloaded;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TurretOwner
{
  private UUID uuid;
  private String name;
  private int numTurretsOwned;
  private int maxTurretsAllowed;
  private List<Turret> turretsOwned;
  
  public UUID getUuid()
  {
    return this.uuid;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getNumTurretsOwned()
  {
    return this.numTurretsOwned;
  }
  
  public void setNumTurretsOwned(int numTurretsOwned)
  {
    this.numTurretsOwned = numTurretsOwned;
  }
  
  public int getMaxTurretsAllowed()
  {
    return this.maxTurretsAllowed;
  }
  
  public TurretOwner(UUID uuid, String name, int owned, int allowed)
  {
    this.uuid = uuid;
    this.numTurretsOwned = owned;
    this.maxTurretsAllowed = allowed;
    this.name = name;
  }
  
  public Player getPlayer()
  {
    return Bukkit.getPlayer(this.name);
  }
  
  public OfflinePlayer getOfflinePlayer()
  {
    return Bukkit.getOfflinePlayer(this.name);
  }
  
  public void addTurret(Turret turr)
  {
    if (this.turretsOwned == null) {
      this.turretsOwned = new ArrayList();
    }
    this.turretsOwned.add(turr);
  }
  
  public void removeTurret(Turret tur)
  {
    this.turretsOwned.remove(tur);
  }
  
  public boolean isOnline(TurretsReloaded pl)
  {
    return pl.getPlayerCache().contains(this.uuid);
  }
}

package me.ssh.secretservice.turretsReloaded.listener;

import com.codelanx.nations.event.ReloadEvent;
import me.ssh.secretservice.turretsReloaded.TurretsReloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CacheListener
  implements Listener
{
  private TurretsReloaded plugin;
  
  public CacheListener(TurretsReloaded pl)
  {
    this.plugin = pl;
  }
  
  @EventHandler
  public void addCache(PlayerLoginEvent e)
  {
    this.plugin.addUserToCache(e.getPlayer());
  }
  
  @EventHandler
  public void removeCache(PlayerQuitEvent e)
  {
    this.plugin.removeUserFromCache(e.getPlayer());
  }
  
  @EventHandler
  public void removeCacheSecond(PlayerKickEvent e)
  {
    this.plugin.removeUserFromCache(e.getPlayer());
  }
  
  @EventHandler
  public void reloadCache(ReloadEvent e)
  {
    this.plugin.setReload(true);
  }
}

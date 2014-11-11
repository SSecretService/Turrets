package me.ssh.secretservice.turretsReloaded.utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public enum Lang
{
  BLOCKUTIL_PITCH("blockutil.pitch", "Pitch: %.2f"),  BLOCKUTIL_YAW("blockutil.yaw", "Yaw: %.2f"),  BLOCKUTIL_DIST_X("blockutil.distance-x", "ploc.x - target.x = %d"),  BLOCKUTIL_DIST_Y("blockutil.distance-y", "ploc.y - target.y = %d"),  BLOCKUTIL_DIST_Z("blockutil.distance-z", "ploc.z - target.z = %d"),  COMMAND_AMMO_USAGE_UNLIM("command.ammo.usage.unlimited", "Click a turret to give it unlimited ammo"),  COMMAND_AMMO_USAGE_LIM("command.ammo.usage.limited", "Click a turret to make it use an ammo box"),  COMMAND_AMMO_BOX_ADD("command.ammo.box.add", "Select turret to add ammo box to"),  COMMAND_AMMO_BOX_REM("command.ammo.box.remove", "Select turret to remove ammo box from"),  COMMAND_AMMO_TYPE_ALL("command.ammo.type.all", "All unlimited turrets set to use %s"),  COMMAND_AMMO_TYPE_ALL_NA("command.ammo.type.all-none", "You have no unlimited turrets!"),  COMMAND_AMMO_TYPE_CLICK("command.ammo.type.click", "Click the turret you would like to change"),  COMMAND_CANCEL_DONE("command.cancel.done", "Turret command cancelled!"),  COMMAND_CANCEL_NONE("command.cancel.none", "No turret commands to cancel!"),  COMMAND_SAVE_DONE("command.save.done", "Turrets saved to database"),  COMMAND_SAVE_ERROR("command.save.error", "Error saving commands to database"),  COMMAND_ACTIVATE("command.activate", "Right click the turret to activate"),  COMMAND_DEACTIVATE("command.deactivate", "Right click the turret to deactivate"),  COMMAND_BUSY("command.busy", "You are currently executing another command!"),  ENTITY_MOUNTED("entity.mounted", "Mounted turret!"),  ENTITY_DISMOUNTED("entity.dismounted", "Dismounted turret!"),  LISTENER_INTERACT_DENY_NOTOWNED("listener.interact.deny.not-owned", "You do not own this turret!"),  LISTENER_INTERACT_CHEST("listener.interact.chest.click", "Right click a chest to link"),  LISTENER_INTERACT_AMMO_LINKED("listener.interact.ammo.linked", "Ammo box linked to turret!"),  LISTENER_INTERACT_AMMO_LINKFAIL("listener.interact.ammo.link-failure", "Ammo box linking failed! Already linked?"),  LISTENER_INTERACT_UNCHEST("listener.interact.unchest", "Right click a chest to unlink"),  LISTENER_INTERACT_AMMO_UNLINKED("listener.interact.ammo.unlinked", "Ammo box unlinked from turret!"),  LISTENER_INTERACT_AMMO_UNLINKFAIL("listener.interact.ammo.unlink-failure", "Ammo box unlinking failed! Already unlinked?"),  LISTENER_INTERACT_AMMO_MODDED("listener.interact.ammo.modded", "Turret ammo modified"),  LISTENER_INTERACT_TURRET_ACTIVE("listener.interact.turret.active", "Turret activated"),  LISTENER_INTERACT_TURRET_DEACTIVE("listener.interact.turret.deactivate", "Turret deactivated"),  LISTENER_INTERACT_AMMO_TYPE("listener.interact.ammo.type", "Turret ammo type changed to %s"),  LISTENER_INTERACT_TURRET_MAX("listener.interact.turrets.max", "You already have the maximum number of turrets"),  LISTENER_INTERACT_CHEST_DETACH("listener.interact.chest.detach", "Chest detached!"),  LISTENER_INTERACT_CHEST_DETACHFAIL("listener.interact.chest.detach-fail", "Failed to detech chest!"),  LISTENER_INTERACT_UPGRADE("listener.interact.upgrade", "Turret upgraded!"),  LEGACY_TURRET_CREATED("legacy.turret-created", "Turret created!"),  LEGACY_TURRET_DESTROY("legacy.turret-destroyed", "Turret destroyed!"),  LEGACY_TURRET_CANNOT_BUILD("legacy.turret-cannot-build", "Cannot build a turret here!"),  LEGACY_TURRET_NOT_CREATED("legacy.turret-not-created", "Turret could not be created!"),  LEGACY_TURRET_BOUGHT("legacy.turret-bought", "Turret has been bought"),  LEGACY_TURRET_MAXIMUM_REACHED("legacy.turret-maximum-reached", "You reached the max ammount of Turrets"),  LEGACY_TURRET_NOT_ENOUGH_MONEY("legacy.turret-not-enough-money", "You do not have enough money!"),  LEGACY_NO_NATION("legacy.no-nation", "You have to be in a nation!"),  LEGACY_UPGRADE_NOT_ENOUGH_MONEY("legacy.upgrade-not-enough-money", "You do not have enough money for this Upgrade!"),  REMOVE_TURRET_TIMER("remove.turret-timer", "Rightclick within the next 30 seconds the tower you want to remove"),  REMOVE_TURRET("remove.turret", "Turret succesfully removed");
  
  private static FileConfiguration yaml;
  private final String def;
  private final String path;
  
  private Lang(String path, String def)
  {
    this.path = path;
    this.def = def;
  }
  
  public String format(Object... args)
  {
    return __(String.format(yaml.getString(this.path, this.def), args));
  }
  
  public String pluralFormat(int amount, Object... args)
  {
    String repl = yaml.getString(this.path);
    repl = repl.replaceAll("\\{PLURALA (.*)\\|(.*)\\}", "are " + amount + " $2");
    repl = repl.replaceAll("\\{PLURAL (.*)\\|(.*)\\}", amount == 1 ? "$1" : "$2");
    return __(String.format(repl, args));
  }
  
  public static String __(String color)
  {
    return ChatColor.translateAlternateColorCodes('&', color);
  }
  
  public static void init(Plugin plugin)
    throws IOException
  {
    File ref = new File(plugin.getDataFolder(), "lang.yml");
    if (!ref.exists()) {
      plugin.saveResource("lang.yml", true);
    }
    yaml = YamlConfiguration.loadConfiguration(ref);
    for (Lang l : values()) {
      if (!yaml.isSet(l.getPath())) {
        yaml.set(l.getPath(), l.getDefault());
      }
    }
    yaml.save(ref);
  }
  
  public static void sendMessage(CommandSender target, String message)
  {
    target.sendMessage(com.codelanx.nations.lang.Lang.FORMAT.format(new Object[] { message }));
  }
  
  public static void sendRawMessage(CommandSender target, String message)
  {
    target.sendMessage(__(message));
  }
  
  public static void sendMessage(CommandSender target, Lang message, Object... args)
  {
    String s = com.codelanx.nations.lang.Lang.FORMAT.format(new Object[] { message.format(args) });
    if (!s.isEmpty()) {
      target.sendMessage(s);
    }
  }
  
  public static void sendRawMessage(CommandSender target, Lang message, Object... args)
  {
    String s = __(message.format(args));
    if (!s.isEmpty()) {
      target.sendMessage(s);
    }
  }
  
  private String getPath()
  {
    return this.path;
  }
  
  private String getDefault()
  {
    return this.def;
  }
}

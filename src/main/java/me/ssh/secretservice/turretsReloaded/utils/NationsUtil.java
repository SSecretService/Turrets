package me.ssh.secretservice.turretsReloaded.utils;

import com.codelanx.nations.Nations;
import com.codelanx.nations.nation.NationManager;
import org.bukkit.entity.Player;

public class NationsUtil
{
  private static Nations nationsPlugin;
  
  public static void init(Nations nations)
  {
    nationsPlugin = nations;
  }
  
  public static Boolean nationsCheck(String nationNameAttacker, Player defend)
  {
    if ((nationNameAttacker != null) && (defend != null))
    {
      Nations nations = Nations.getInstance();
      return Boolean.valueOf(!nations.getNationManager().getNation(nationNameAttacker).equals(nations.getNationManager().getNation(defend)));
    }
    return Boolean.valueOf(true);
  }
}

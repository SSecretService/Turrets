package me.ssh.secretservice.turretsReloaded.targeting;

import me.ssh.secretservice.turretsReloaded.utils.MobAlignment;
import org.bukkit.entity.LivingEntity;

public class MobAssessor
  implements TargetAssessor
{
  public TargetAssessment assessMob(LivingEntity mob)
  {
    if (MobAlignment.isHostile(mob)) {
      return TargetAssessment.HOSTILE;
    }
    if (MobAlignment.isEither(mob)) {
      return TargetAssessment.EITHER;
    }
    return TargetAssessment.MEH;
  }
}

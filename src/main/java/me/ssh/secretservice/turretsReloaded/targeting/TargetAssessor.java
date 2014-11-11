package me.ssh.secretservice.turretsReloaded.targeting;

import org.bukkit.entity.LivingEntity;

public abstract interface TargetAssessor
{
  public abstract TargetAssessment assessMob(LivingEntity paramLivingEntity);
}

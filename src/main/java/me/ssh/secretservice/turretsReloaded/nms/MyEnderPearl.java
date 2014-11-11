package me.ssh.secretservice.turretsReloaded.nms;

import net.minecraft.server.v1_7_R4.EntityEnderPearl;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.World;

public class MyEnderPearl
  extends EntityEnderPearl
{
  public MyEnderPearl(World world)
  {
    super(world);
  }
  
  protected void a(MovingObjectPosition movingObjectPosition) {}
}

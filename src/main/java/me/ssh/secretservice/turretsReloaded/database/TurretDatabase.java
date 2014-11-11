package me.ssh.secretservice.turretsReloaded.database;


import me.ssh.secretservice.turretsReloaded.Turret;

import java.io.IOException;
import java.util.Collection;

public abstract interface TurretDatabase
{
  public abstract Collection<Turret> loadTurrets()
    throws IOException;
  
  public abstract void saveTurrets(Collection<Turret> paramCollection)
    throws IOException;
}

package me.ssh.secretservice.turretsReloaded.utils;

import java.util.Collection;
import java.util.Random;

public final class RandomUtils
{
  public static <T> T randomElement(Collection<T> collection, Random random)
  {
    int index = random.nextInt(collection.size());
    int i = 0;
    for (T element : collection)
    {
      if (i == index) {
        return element;
      }
      i++;
    }
    return null;
  }
}

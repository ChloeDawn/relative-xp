/*
 * Copyright 2021 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sapphic.relativexp;

import net.minecraft.world.entity.player.Player;

public final class XpMth {
  private XpMth() {
    throw new UnsupportedOperationException();
  }

  /**
   * Calculates the experience points required to reach the given level from level 0
   *
   * @param level The level to be reached
   * @return The total sum of experience points
   */
  public static int pointsForLevel(final int level) {
    return pointsBetweenLevels(0, level);
  }

  /**
   * Calculates the experience points required to reach the target level from the starting level
   *
   * @param startingLevel The level to start from
   * @param targetLevel The level to be reached
   * @return The total sum of experience points
   * @see Player#getXpNeededForNextLevel()
   */
  public static int pointsBetweenLevels(final int startingLevel, final int targetLevel) {
    if (targetLevel < startingLevel) {
      throw new IllegalArgumentException("Target level must be greater than starting level");
    }

    int currLevel = startingLevel;
    int xp = 0;

    while (currLevel < targetLevel) {
      if (currLevel >= 30) {
        xp += 112 + ((currLevel - 30) * 9);
      } else if (currLevel >= 15) {
        xp += 37 + ((currLevel - 15) * 5);
      } else {
        xp += 7 + (currLevel * 2);
      }

      ++currLevel;
    }

    return xp;
  }
}

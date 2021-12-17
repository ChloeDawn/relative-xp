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

package dev.sapphic.relativexp.mixin;

import dev.sapphic.relativexp.XpMth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
abstract class AnvilMenuMixin extends ItemCombinerMenu {
  AnvilMenuMixin(
      final MenuType<?> type,
      final int containerId,
      final Inventory inventory,
      final ContainerLevelAccess access) {
    super(type, containerId, inventory, access);
  }

  @Redirect(
      method =
          "onTake("
              + "Lnet/minecraft/world/entity/player/Player;"
              + "Lnet/minecraft/world/item/ItemStack;"
              + ")V",
      require = 1,
      allow = 1,
      at =
          @At(
              value = "INVOKE",
              opcode = Opcodes.INVOKEVIRTUAL,
              target =
                  "Lnet/minecraft/world/entity/player/Player;"
                      + "giveExperienceLevels("
                      + "I"
                      + ")V"))
  private void takeExperiencePoints(final Player player, final int cost) {
    // Unary minus the given level cost as it is negative when we receive it
    player.giveExperiencePoints(-XpMth.pointsForLevel(-cost));
  }
}

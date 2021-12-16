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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentMenu.class)
abstract class EnchantmentMenuMixin extends AbstractContainerMenu {
  EnchantmentMenuMixin(final MenuType<?> type, final int containerId) {
    super(type, containerId);
  }

  @Redirect(
      method =
          "lambda$clickMenuButton$1("
              + "Lnet/minecraft/world/item/ItemStack;"
              + "I"
              + "Lnet/minecraft/world/entity/player/Player;"
              + "I"
              + "Lnet/minecraft/world/item/ItemStack;"
              + "Lnet/minecraft/world/level/Level;"
              + "Lnet/minecraft/core/BlockPos;"
              + ")V",
      require = 1,
      allow = 1,
      at =
          @At(
              value = "INVOKE",
              opcode = Opcodes.INVOKEVIRTUAL,
              target =
                  "Lnet/minecraft/world/entity/player/Player;"
                      + "onEnchantmentPerformed("
                      + "Lnet/minecraft/world/item/ItemStack;"
                      + "I"
                      + ")V"))
  private void takeExperiencePoints(final Player player, final ItemStack stack, final int levels) {
    // The level cost is determined by (cost index + 1) so we reverse the arithmetic.
    // No need to shadow this field as it is declared public in the target class.
    final int minLevel = ((EnchantmentMenu) (Object) this).costs[levels - 1];
    final int xpCost = XpMth.pointsBetweenLevels(minLevel - levels, minLevel);

    player.giveExperiencePoints(-xpCost);

    // Pass 0 levels as we do not want the game to act on it. Effectively just resets the
    // enchantment seed, but we want be compatible with any mods that hook this method.
    player.onEnchantmentPerformed(stack, 0);
  }
}

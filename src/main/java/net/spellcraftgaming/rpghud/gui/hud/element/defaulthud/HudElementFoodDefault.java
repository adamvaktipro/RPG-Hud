package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodDefault extends HudElementBarred {

	public HudElementFoodDefault() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int stamina = this.mc.thePlayer.getFoodStats().getFoodLevel();
		int posX = this.settings.render_player_face ? 49 : 24;
		int posY = this.settings.render_player_face ? 26 : 18;
		ItemStack itemMain = this.mc.thePlayer.getHeldItem();
		if ((itemMain != null && itemMain.getItem() instanceof ItemFood) && this.mc.thePlayer.getFoodStats().needFood() && this.settings.show_hunger_preview) {
			float value;
			value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > 20)
				bonusHunger = 20;
			int colorPreview = offsetColor(this.settings.color_stamina, OFFSET_PREVIEW);
			drawCustomBar(posX, posY, 110, 12, bonusHunger / 20.0D * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
		}
		if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
			drawCustomBar(posX, posY, 110, 12, stamina / 20.0D * 100.0D, -1, -1, this.settings.color_hunger, offsetColorPercent(this.settings.color_hunger, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, stamina / 20.0D * 100.0D, -1, -1, this.settings.color_stamina, offsetColorPercent(this.settings.color_stamina, OFFSET_PERCENT));
		}
		String staminaString = stamina + "/" + "20";
		if (this.settings.show_numbers_stamina)
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, posX + 55, posY + 2, -1);
	}

}

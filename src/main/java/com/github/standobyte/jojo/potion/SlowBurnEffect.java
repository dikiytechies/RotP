package com.github.standobyte.jojo.potion;

import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class SlowBurnEffect extends StatusEffect {
    public SlowBurnEffect() {
        super(EffectType.HARMFUL, 0xee5d2d);
        addAttributeModifier(Attributes.ARMOR, "93b39a68-5597-4b5f-a9c7-890d5415ba8d", -1, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "93b39a68-5597-4b5f-a9c7-890d5415ba8d", -0.5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int transferedAmplifier = amplifier - 6;
        if (transferedAmplifier < 0) {
            if (entity.getRemainingFireTicks() % 20 == 0 && (entity.getRemainingFireTicks() % (20 * (Math.abs(transferedAmplifier) + 1)) != 0)) {
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() - 2);
            }
        /*if (amplifier <= 1) {
            if (entity.getRemainingFireTicks() + 10 % 20 == 0 && entity.getRemainingFireTicks() + 10 % (60 - 20 * amplifier) != 0) {
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() - 1);
                storedTicks[19] = true;
            }
            if (entity.getRemainingFireTicks() + 12 % 20 == 0 && entity.getRemainingFireTicks() + 12 % (60 - 20 * amplifier) != 0) {
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
                for (int i = 0; i < 20; i++) {
                    if (storedTicks[0]) {
                        storedTicks[0] = false;
                    }
                    if (storedTicks[i]) {
                        storedTicks[i-1] = storedTicks[i];
                        storedTicks[i] = false;
                    }
                }
            }*/
        } else if (transferedAmplifier != 0) {
            int k = (20 / transferedAmplifier) != 0 ? (20 / transferedAmplifier) : 1; //time before proc
            for (int i = 0; i < transferedAmplifier; i++) {
                if ((entity.getRemainingFireTicks() + 10 - k * (i + 1)) % 20 == 0) {
                    DamageUtil.hurtThroughInvulTicks(entity, DamageSource.ON_FIRE, 1.0f);
                }
            }
        }
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


}

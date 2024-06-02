package com.github.standobyte.jojo.potion;

import com.github.standobyte.jojo.util.mod.JojoModUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class FearEffect extends StatusEffect implements IApplicableEffect {
    public FearEffect() {
        super(EffectType.HARMFUL, 0x8E7CC3);
    }
    protected float AttributeMultiplier = 1.0f;
    protected int currentTick = 0;


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        AttributeMultiplier = (float) (Math.sin((double) currentTick / 10) >= 0 ? Math.sin((double) currentTick / 10) : Math.sin((double) currentTick / 10) * 2);
        if ((double) currentTick / 10 >= 2 * Math.PI) {
            currentTick = 0;
        } else {
            currentTick++;
        }
        System.out.println((double) currentTick / 10);
        if (currentTick % 4 == 0) {
            removeAttributeModifiers(entity, entity.getAttributes(), amplifier);
            addAttributeModifier(Attributes.MOVEMENT_SPEED, "529f3f29-a3b7-4c35-9b2b-52ea64a962f6", (double) currentTick / 10, AttributeModifier.Operation.ADDITION);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isApplicable(LivingEntity entity) { return !JojoModUtil.isUndead(entity); }
}

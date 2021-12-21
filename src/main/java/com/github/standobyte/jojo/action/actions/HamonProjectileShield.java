package com.github.standobyte.jojo.action.actions;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.HamonProjectileShieldEntity;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HamonProjectileShield extends HamonAction {

    public HamonProjectileShield(Builder builder) {
        super(builder);
    }
    
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, LivingEntity performer, INonStandPower power, ActionTarget target) {
        ItemStack heldItemStack = performer.getMainHandItem();
        if (!heldItemStack.isEmpty()) {
            return conditionMessage("hand");
        }
        return ActionConditionResult.POSITIVE;
    }
    
    @Override
    public void onStartedHolding(World world, LivingEntity user, INonStandPower power, ActionTarget target, boolean requirementsFulfilled) {
        if (!world.isClientSide() && requirementsFulfilled) {
            world.addFreshEntity(new HamonProjectileShieldEntity(world, user));
        }
    }

}

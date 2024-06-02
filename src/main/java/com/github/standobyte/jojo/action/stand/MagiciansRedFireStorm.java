package com.github.standobyte.jojo.action.stand;


import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;

public class MagiciansRedFireStorm extends StandEntityAction {
    public MagiciansRedFireStorm(StandEntityAction.Builder builder) { super(builder); }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (power.getStamina() == 0.0f) return ActionConditionResult.NEGATIVE;
        return ActionConditionResult.POSITIVE;
    }
}

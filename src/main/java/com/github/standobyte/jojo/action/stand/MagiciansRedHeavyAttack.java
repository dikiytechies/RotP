package com.github.standobyte.jojo.action.stand;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class MagiciansRedHeavyAttack extends StandEntityHeavyAttack {
    public MagiciansRedHeavyAttack(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        super.standPerform(world, standEntity, userPower, task);
        if (task.getTarget().getEntity() instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) task.getTarget().getEntity();
            targetEntity.addEffect(new EffectInstance(ModStatusEffects.SLOWBURN.get(), 200,
                    targetEntity.getEffect(ModStatusEffects.SLOWBURN.get()) != null? targetEntity.getEffect(ModStatusEffects.SLOWBURN.get()).getAmplifier() + 4: 4,
                    false, false, true));
            userPower.addLearningProgressPoints(this, getMaxTrainingPoints(userPower) / 40 * 4);
        }
    }
}

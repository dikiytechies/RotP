package com.github.standobyte.jojo.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class MagiciansRedLightAttack extends StandEntityLightAttack {
    public MagiciansRedLightAttack(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        super.standPerform(world, standEntity, userPower, task);
        if (task.getTarget().getEntity() instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) task.getTarget().getEntity();
            targetEntity.addEffect(new EffectInstance(ModStatusEffects.SLOWBURN.get(), 200,
                    targetEntity.getEffect(ModStatusEffects.SLOWBURN.get()) != null? targetEntity.getEffect(ModStatusEffects.SLOWBURN.get()).getAmplifier() + 1: 0,
                    false, false, true));
        }
    }
}

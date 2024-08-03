package com.github.standobyte.jojo.action.stand;


import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.MRFlameEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.entity.stand.stands.MagiciansRedEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.Random;

public class MagiciansRedFireStorm extends StandEntityAction {

    private final LazySupplier<ResourceLocation> inactiveTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_inactive"));

    public MagiciansRedFireStorm(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (power.getStamina() == 0.0f) return ActionConditionResult.NEGATIVE;
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (standEntity instanceof MagiciansRedEntity && !world.isClientSide()) {
            boolean isCasting = ((MagiciansRedEntity) standEntity).isCastingFirestorm();
            ((MagiciansRedEntity) standEntity).setCastingFirestorm(!isCasting);
        }
    }

    @Override
    public ResourceLocation getIconTexturePath(@Nullable IStandPower power) {
        if (power != null) {
            StandEntity stand = (StandEntity) power.getStandManifestation();
            if (stand instanceof MagiciansRedEntity && ((MagiciansRedEntity) stand).isCastingFirestorm()) {
                return inactiveTex.get();
            } else {
                return super.getIconTexturePath(power);
            }
        }
        return null;
    }
}

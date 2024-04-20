package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.capability.entity.power.NonStandCapProvider;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.vampirism.ModVampirismActions;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VampirismVision extends VampirismAction{
    public VampirismVision(NonStandAction.Builder builder) { super(builder); }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        power.getTypeSpecificData(ModPowers.VAMPIRISM.get()).ifPresent(
                vampirism -> vampirism.setNightVisionActive(!vampirism.isNightVisionActive()));
        System.out.println(power.getUser().getCapability(NonStandCapProvider.NON_STAND_CAP).resolve().map(
                playerData -> playerData.getTypeSpecificData(ModPowers.VAMPIRISM.get()).map(
                        vampirism -> vampirism.isNightVisionActive()).orElse(false)
        ).orElse(false));
    }

    private final LazySupplier<ResourceLocation> visionTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_active"));

    @Override
    public ResourceLocation getIconTexturePath(@Nullable INonStandPower power) {
        if (power != null && power.getTypeSpecificData(ModPowers.VAMPIRISM.get()).get().isNightVisionActive()) {
            return visionTex.get();
        }
        else {
            return super.getIconTexturePath(power);
        }
    }

    @Override
    protected int maxCuringStage() { return 3; }
}

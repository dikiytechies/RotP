package com.github.standobyte.jojo.power.impl.nonstand.type.hamon.skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.non_stand.HamonAction;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.layout.ActionsLayout;

import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class AbstractHamonSkill extends ForgeRegistryEntry<AbstractHamonSkill> {
    private final RewardType rewardType;
    private final Object2BooleanMap<Supplier<? extends HamonAction>> rewardActions;
    private final List<Supplier<? extends AbstractHamonSkill>> requiredSkills;

    protected AbstractHamonSkill(AbstractBuilder builder) {
        this.rewardType = builder.rewardType;
        this.rewardActions = builder.rewardActions;
        this.requiredSkills = builder.requiredSkills;
    }
    
    public RewardType getRewardType() {
        return rewardType;
    }
    
    public Stream<HamonAction> getRewardActions() {
        return rewardActions.keySet().stream().map(Supplier::get);
    }
    
    public Stream<HamonAction> getRewardActions(boolean addedToHud) {
        return rewardActions.object2BooleanEntrySet().stream()
                .filter(entry -> entry.getBooleanValue() == addedToHud)
                .map(Map.Entry::getKey)
                .map(Supplier::get);
    }
    
    public boolean isUnlockedByDefault() {
        return false;
    }
    
    public boolean requiresTeacher() {
        return false;
    }
    
    public Stream<AbstractHamonSkill> getRequiredSkills() {
        return requiredSkills.stream().map(Supplier::get);
    }
    
    public abstract SkillType getSkillType();
    
    public boolean isBaseSkill() {
        return getSkillType() == SkillType.BASE;
    }
    
    public void learnNewSkill(HamonData hamon, LivingEntity user) {
        hamon.addHamonSkill(user, this, true, true);
    }
    
    public IFormattableTextComponent getNameTranslated() {
        return new TranslationTextComponent("hamonSkill." + getName() + ".name");
    }
    
    public IFormattableTextComponent getDescTranslated() {
        return new TranslationTextComponent("hamonSkill." + getName() + ".desc");
    }
    
    private String translationKey = null;
    public String getName() {
        if (translationKey == null) {
            ResourceLocation regName = getRegistryName();
            translationKey = regName.getPath();
            if (!JojoMod.MOD_ID.equals(regName.getNamespace())) {
                translationKey = regName.getNamespace() + "." + translationKey;
            }
        }
        return this.translationKey;
    }
    
    
    
    
    
    public enum RewardType {
        ATTACK("attack", ActionsLayout.Hotbar.LEFT_CLICK),
        ABILITY("ability", ActionsLayout.Hotbar.RIGHT_CLICK),
        PASSIVE("passive", null),
        ITEM("item", null);
        
        private final ITextComponent name;
        private final ActionsLayout.Hotbar hotbar;
        
        private RewardType(String key, ActionsLayout.Hotbar hotbar) {
            this.name = new TranslationTextComponent("hamon.skill_type." + key).withStyle(TextFormatting.ITALIC);
            this.hotbar = hotbar;
        }
        
        public ITextComponent getName() {
            return name;
        }
        
        @Nullable
        public ActionsLayout.Hotbar getDefaultHotbar() {
            return hotbar;
        }
    }
    
    public enum SkillType {
        BASE,
        TECHNIQUE,
        CHARACTER_TECHNIQUE;
    }
    
    
    
    protected static class AbstractBuilder {
        protected final RewardType rewardType;
        protected Object2BooleanMap<Supplier<? extends HamonAction>> rewardActions = new Object2BooleanArrayMap<>();
        protected final List<Supplier<? extends AbstractHamonSkill>> requiredSkills = new ArrayList<>();
        
        public AbstractBuilder(RewardType rewardType) {
            this.rewardType = rewardType;
        }
    }
}

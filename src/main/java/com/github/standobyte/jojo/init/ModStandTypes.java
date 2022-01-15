package com.github.standobyte.jojo.init;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.actions.StandAction;
import com.github.standobyte.jojo.power.IPowerType;
import com.github.standobyte.jojo.power.stand.stats.StandStatsV2;
import com.github.standobyte.jojo.power.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.stand.type.StandType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModStandTypes {
    private static final ITextComponent PART_3_NAME = new TranslationTextComponent("jojo.part.sc").withStyle(TextFormatting.DARK_PURPLE);
//    private static final ITextComponent PART_4_NAME = new TranslationTextComponent("jojo.part.diu").withStyle(TextFormatting.RED);
//    private static final ITextComponent PART_5_NAME = new TranslationTextComponent("jojo.part.gw").withStyle(TextFormatting.GOLD);
//    private static final ITextComponent PART_6_NAME = new TranslationTextComponent("jojo.part.so").withStyle(TextFormatting.AQUA);
//    
//    private static final ITextComponent PART_7_NAME = new TranslationTextComponent("jojo.part.sbr").withStyle(TextFormatting.LIGHT_PURPLE);
//    private static final ITextComponent PART_8_NAME = new TranslationTextComponent("jojo.part.jjl").withStyle(TextFormatting.WHITE);
    
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), JojoMod.MOD_ID);
    
    // FIXME actual default stat values
    public static final RegistryObject<EntityStandType<TimeStopperStandStats>> STAR_PLATINUM = STANDS.register("star_platinum", 
            () -> new EntityStandType<>(6, 0xB000B0, PART_3_NAME,
                    new StandAction[] {ModActions.STAR_PLATINUM_PUNCH.get(), ModActions.STAR_PLATINUM_BARRAGE.get(), ModActions.STAR_PLATINUM_STAR_FINGER.get()},
                    new StandAction[] {ModActions.STAR_PLATINUM_BLOCK.get(), ModActions.STAR_PLATINUM_ZOOM.get(), ModActions.STAR_PLATINUM_TIME_STOP.get()},
                    ModSounds.JOTARO_STAR_PLATINUM, 
                    TimeStopperStandStats.class, new TimeStopperStandStats.Builder()
                    .power(16.0)
                    .speed(16.0)
                    .range(2.0, 10.0)
                    .durability(6.0)
                    .precision(1.0)
                    .maxTimeStopTicks(100, 180)
                    .build(), 
                    ModEntityTypes.STAR_PLATINUM));
    
    public static final RegistryObject<EntityStandType<TimeStopperStandStats>> THE_WORLD = STANDS.register("the_world", 
            () -> new EntityStandType<>(6, 0xFFD000, PART_3_NAME,
                    new StandAction[] {ModActions.THE_WORLD_PUNCH.get(), ModActions.THE_WORLD_BARRAGE.get()},
                    new StandAction[] {ModActions.THE_WORLD_BLOCK.get(), ModActions.THE_WORLD_TIME_STOP.get(), ModActions.THE_WORLD_ROAD_ROLLER.get()},
                    ModSounds.DIO_THE_WORLD, 
                    TimeStopperStandStats.class, new TimeStopperStandStats.Builder()
                    .power(16.0)
                    .speed(16.0)
                    .range(2.0, 10.0)
                    .durability(6.0)
                    .precision(1.0)
                    .maxTimeStopTicks(100, 180)
                    .build(), 
                    ModEntityTypes.THE_WORLD));
    
    public static final RegistryObject<EntityStandType<StandStatsV2>> HIEROPHANT_GREEN = STANDS.register("hierophant_green", 
            () -> new EntityStandType<>(4, 0x00B000, PART_3_NAME,
                    new StandAction[] {ModActions.HIEROPHANT_GREEN_STRING_ATTACK.get(), ModActions.HIEROPHANT_GREEN_EMERALD_SPLASH.get()},
                    new StandAction[] {ModActions.HIEROPHANT_GREEN_BLOCK.get(), ModActions.HIEROPHANT_GREEN_GRAPPLE.get(), ModActions.HIEROPHANT_GREEN_BARRIER.get()},
                    ModSounds.KAKYOIN_HIEROPHANT_GREEN, 
                    StandStatsV2.class, new StandStatsV2.Builder()
                    .power(6.0)
                    .speed(10.0)
                    .range(50.0)
                    .durability(3.0)
                    .precision(0.4)
                    .build(), 
                    ModEntityTypes.HIEROPHANT_GREEN));
    
    public static final RegistryObject<EntityStandType<StandStatsV2>> SILVER_CHARIOT = STANDS.register("silver_chariot", 
            () -> new EntityStandType<>(5, 0xBEC8D6, PART_3_NAME,
                    new StandAction[] {ModActions.SILVER_CHARIOT_ATTACK.get(), ModActions.SILVER_CHARIOT_BARRAGE.get(), ModActions.SILVER_CHARIOT_RAPIER_LAUNCH.get()},
                    new StandAction[] {ModActions.SILVER_CHARIOT_BLOCK.get(), ModActions.SILVER_CHARIOT_TAKE_OFF_ARMOR.get()},
                    ModSounds.POLNAREFF_SILVER_CHARIOT, 
                    StandStatsV2.class, new StandStatsV2.Builder()
                    .power(7.0)
                    .speed(14.0)
                    .range(10.0, 10.0)
                    .durability(4.0)
                    .precision(1.0)
                    .build(), 
                    ModEntityTypes.SILVER_CHARIOT));
    
    public static final RegistryObject<EntityStandType<StandStatsV2>> MAGICIANS_RED = STANDS.register("magicians_red", 
            () -> new EntityStandType<>(5, 0xFF6A00, PART_3_NAME,
                    new StandAction[] {ModActions.MAGICIANS_RED_PUNCH.get(), ModActions.MAGICIANS_RED_FLAME_BURST.get(), ModActions.MAGICIANS_RED_FIREBALL.get(), ModActions.MAGICIANS_RED_CROSSFIRE_HURRICANE.get()},
                    new StandAction[] {ModActions.MAGICIANS_RED_BLOCK.get(), ModActions.MAGICIANS_RED_RED_BIND.get(), ModActions.MAGICIANS_RED_DETECTOR.get()},
                    ModSounds.AVDOL_MAGICIANS_RED, 
                    StandStatsV2.class, new StandStatsV2.Builder()
                    .power(8.0)
                    .speed(8.0)
                    .range(5.0)
                    .durability(5.0)
                    .precision(0.2)
                    .build(), 
                    ModEntityTypes.MAGICIANS_RED));
    
    
    
    public static class Registry {
        private static Supplier<IForgeRegistry<StandType<?>>> REGISTRY_SUPPLIER = null;
        
        public static void initRegistry() {
            if (REGISTRY_SUPPLIER == null) {
                REGISTRY_SUPPLIER = ModStandTypes.STANDS.makeRegistry("stand_type", () -> new RegistryBuilder<>());
            }
        }
        
        public static IForgeRegistry<StandType<?>> getRegistry() {
            return REGISTRY_SUPPLIER.get();
        }
        
        @Nonnull
        public static String getKeyAsString(StandType<?> standType) {
            ResourceLocation resourceLocation = getRegistry().getKey(standType);
            if (resourceLocation == null) {
               return IPowerType.NO_POWER_NAME;
            }
            return resourceLocation.toString();
        }
    }
}

package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.Hunter;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupVillagers {
    
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister
            .create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, NeoVillagersHunter.MODID);
    
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
            .create(BuiltInRegistries.VILLAGER_PROFESSION, NeoVillagersHunter.MODID);
    
    public static final DeferredHolder<PoiType, PoiType> HUNTER_POI = SetupVillagers.POI_TYPES.register(
            Hunter.ENTITY_POI_NAME,
            () -> new PoiType(ImmutableSet.copyOf(SetupBlocks.HUNTING.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    
    public static final DeferredHolder<VillagerProfession, VillagerProfession> HUNTER = SetupVillagers.VILLAGER_PROFESSIONS
            .register(Hunter.ENTITY_NAME,
                    () -> new VillagerProfession(Hunter.ENTITY_NAME, x -> x.is(SetupVillagers.HUNTER_POI.getKey()),
                            x -> x.is(SetupVillagers.HUNTER_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_FLETCHER));
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private SetupVillagers() {
    }
}

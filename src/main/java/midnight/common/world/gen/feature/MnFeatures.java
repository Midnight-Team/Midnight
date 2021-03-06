/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.gen.feature;

import java.util.ArrayList;

import java.util.List;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.block.SuavisBlock;
import midnight.common.block.VioleafBlock;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class MnFeatures {
    private static final List<Feature<?>> FEATURES = new ArrayList<>();

    public static final Feature<DeadLogFeatureConfig> DEAD_LOG = register("dead_log", new DeadLogFeature());
    public static final Feature<TwoBlockStateProvidingFeatureConfig> NIGHTSTONE_BOULDER = register("nightstone_boulder", new BoulderFeature(TwoBlockStateProvidingFeatureConfig.CODEC));
    public static final Feature<TwoBlockStateProvidingFeatureConfig> CRYSTAL_CLUSTER = register("crystal_cluster", new CrystalClusterFeature(TwoBlockStateProvidingFeatureConfig.CODEC, 3, 4));
    public static final Feature<TwoBlockStateProvidingFeatureConfig> CRYSTAL_SPIRE = register("crystal_spire", new CrystalClusterFeature(TwoBlockStateProvidingFeatureConfig.CODEC, 4, 13));
    public static final Feature<TwoBlockStateProvidingFeatureConfig> HUGE_CRYSTAL_SPIRE = register("huge_crystal_spire", new CrystalClusterFeature(TwoBlockStateProvidingFeatureConfig.CODEC, 5, 25));

    public static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
        FEATURES.forEach(registry::register);
    }

    private static <FC extends IFeatureConfig, F extends Feature<FC>> F register(String id, F feature) {
        feature.setRegistryName(Midnight.id(id));
        FEATURES.add(feature);
        return feature;
    }
    
    public static final class ConfiguredFeatures {
    	
    	private static <C extends IFeatureConfig, F extends Feature<C>> ConfiguredFeature<C, F> register(String id, ConfiguredFeature<C, F> feature) {
    		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, Midnight.id(id), feature); 
        }
    	
    	// To-Do: Java rng bad, causes features to generate very clumpy. Fix by giving each feature a different number of .chance(1) configs OR find out if fixed in 1.17.
    	public static final ConfiguredFeature<?, ?> NIGHTSTONE_BOULDER = register("nightstone_boulder",
    			MnFeatures.NIGHTSTONE_BOULDER.configured(new TwoBlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(MnBlocks.NIGHTSTONE.defaultBlockState()), new SimpleBlockStateProvider(MnBlocks.NIGHTSTONE.defaultBlockState())))
    			.decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE)
    			.chance(4));
    	
    	public static final ConfiguredFeature<?, ?> CRYSTAL_CLUSTER = register("crystal_cluster",
    			MnFeatures.CRYSTAL_CLUSTER.configured(new TwoBlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL_ROCK.defaultBlockState()), new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL.defaultBlockState())))
    			.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    			.count(4));
    	
    	public static final ConfiguredFeature<?, ?> CRYSTAL_SPIRE = register("crystal_spire",
    			MnFeatures.CRYSTAL_SPIRE.configured(new TwoBlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL_ROCK.defaultBlockState()), new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL.defaultBlockState())))
    			.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    			.count(2));
    	
    	public static final ConfiguredFeature<?, ?> HUGE_CRYSTAL_SPIRE = register("huge_crystal_spire",
    			MnFeatures.HUGE_CRYSTAL_SPIRE.configured(new TwoBlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL_ROCK.defaultBlockState()), new SimpleBlockStateProvider(MnBlocks.BLOOMCRYSTAL.defaultBlockState())))
    			.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    			.squared());
    	
    	public static final ConfiguredFeature<?, ?> GRASS_PLAIN = register("grass_plain",
    	        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8, 5, 10))));
    	
    	public static final ConfiguredFeature<?, ?> GRASS_FOREST = register("grass_forest",
    	        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2));
    	
    	public static final ConfiguredFeature<?, ?> TALL_GRASS_PLAIN = register("tall_grass_plain",
    			MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.defaultBlockState(), 32)
    			.decorated(Features.Placements.ADD_32)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE)
    			.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8, 0, 7))));
    	
    	public static final ConfiguredFeature<?, ?> TALL_GRASS_FOREST = register("tall_grass_forest",
    			MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.defaultBlockState(), 32)
    			.decorated(Features.Placements.ADD_32)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE));
    	
    	public static final ConfiguredFeature<?, ?> GHOST_PLANTS = register("ghost_plants",
    	        MnFeatureFactory.simplePatch(MnBlocks.GHOST_PLANT.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(3)
    	        .chance(3));
    			
    	public static final ConfiguredFeature<?, ?> GLOB_FUNGUS = register("glob_fungus",
    			MnFeatureFactory.simplePatch(MnBlocks.GLOB_FUNGUS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(4)
    	        .chance(4));
    	
    	public static final ConfiguredFeature<?, ?> BRISTLY_GRASS = register("bristly_grass",
    			MnFeatureFactory.simplePatch(MnBlocks.BRISTLY_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(4)
    	        .chance(7));
    	
    	public static final ConfiguredFeature<?, ?> CRYSTALOTUS = register("crystalotus",
    	        Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MnBlocks.CRYSTALOTUS.defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build())
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .chance(6));
    	
    	public static final ConfiguredFeature<?, ?> VIOLEAF = register("violeaf",
    	        MnFeatureFactory.simplePatch(MnBlocks.VIOLEAF.defaultBlockState().setValue(VioleafBlock.GROWN, true), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2));
    	
    	public static final ConfiguredFeature<?, ?> TENDRILWEED = register("tendrilweed",
    	        MnFeatureFactory.simplePatch(MnBlocks.TENDRILWEED.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2)
    	        .chance(7));
    	
    	public static final ConfiguredFeature<?, ?> CRYSTAL_FLOWERS = register("crystal_flowers",
    	        MnFeatureFactory.simplePatch(MnBlocks.CRYSTAL_FLOWER.defaultBlockState(), 48)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2));
    	
    	public static final ConfiguredFeature<?, ?> BOGSHROOM = register("bogshroom",
    	        MnFeatureFactory.simplePatch(MnBlocks.BOGSHROOM.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .chance(3));

    	public static final ConfiguredFeature<?, ?> TALL_BOGSHROOM = register("tall_bogshroom",
    	        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_BOGSHROOM.defaultBlockState(), 32)
    	        .decorated(Features.Placements.ADD_32)
    	        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    	        .chance(13));
    	
    	public static final ConfiguredFeature<?, ?> BOGWEED = register("bogweed",
    	        MnFeatureFactory.simplePatch(MnBlocks.BOGWEED.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .chance(2));
    	
    	public static final ConfiguredFeature<?, ?> SUAVIS_COMMON = register("suavis_common",
    	        MnFeatureFactory.simplePatch(MnBlocks.SUAVIS.defaultBlockState().setValue(SuavisBlock.STAGE, 3), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(8));
    	
    	public static final ConfiguredFeature<?, ?> SUAVIS_SPARSE = register("suavis_sparse",
    	        MnFeatureFactory.simplePatch(MnBlocks.SUAVIS.defaultBlockState().setValue(SuavisBlock.STAGE, 3), 4)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE)
    	        .chance(4));
    		
    	public static final ConfiguredFeature<?, ?> DEAD_LOG = register("dead_log",
    			MnFeatureFactory.deadLog(MnBlocks.DEAD_WOOD_LOG)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE)
    			.chance(2));
    	
    	public static final ConfiguredFeature<?, ?> DARK_PEARL_ORE = register("dark_pearl_ore",
    			Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.MIDNIGHT_STONE, MnBlocks.DARK_PEARL_ORE.defaultBlockState(), 14))
    			.range(56)
    			.squared()
    			.count(8));
    	
    	public static final ConfiguredFeature<?, ?> EBONITE_ORE = register("ebonite_ore",
    			Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.MIDNIGHT_STONE, MnBlocks.EBONITE_ORE.defaultBlockState(), 6))
    			.range(24)
    			.squared()
    			.count(4));
    	
    	public static final ConfiguredFeature<?, ?> NAGRILITE_ORE = register("nagrilite_ore",
    			Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.MIDNIGHT_STONE, MnBlocks.NAGRILITE_ORE.defaultBlockState(), 4))
    			.range(24)
    			.squared()
    			.count(4));
    	
    	public static final ConfiguredFeature<?, ?> TENEBRUM_ORE = register("tenebrum_ore",
    			Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.MIDNIGHT_STONE, MnBlocks.TENEBRUM_ORE.defaultBlockState(), 4))
    			.range(56)
    			.squared()
    			.count(6));
    }
    
    public static final class FillerBlockType {
        public static final RuleTest MIDNIGHT_STONE = new BlockMatchRuleTest(MnBlocks.NIGHTSTONE);
    }
}

package com.mcmoddev.orespawn.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.SpawnEntry;
import com.mcmoddev.orespawn.impl.features.DefaultFeatureGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

public class SpawnEntryImpl implements SpawnEntry {
	private final JsonObject paramStore; 
    private final IBlockState state;
    private final List<Biome> biomes;
    private final IFeature generator;
    private final IBlockState blockRep;
    private final static String sz = "size";
    private final static String var = "variation";
    private final static String freq = "frequency";
    private final static String mny = "minHeight";
    private final static String mxy = "maxHeight";
    
    public SpawnEntryImpl(IBlockState state, int size, int variation, float frequency, int minHeight, int maxHeight, Biome[] biomes) {
    	this.paramStore = new JsonObject();
    	this.paramStore.addProperty(sz, size);
    	this.paramStore.addProperty(var, variation);
    	this.paramStore.addProperty(freq, frequency);
    	this.paramStore.addProperty(mny, minHeight);
    	this.paramStore.addProperty(mxy, maxHeight);
        this.state = state;
        this.blockRep = null;
        
        if( biomes == null || biomes.length == 0 ) {
        	this.biomes = Collections.<Biome>emptyList();;
        } else {
        	this.biomes = new ArrayList<>();
        	for( Biome b : biomes ) {
        		this.biomes.add(b);
        	}
        }
        this.generator = new DefaultFeatureGenerator();
//        OreSpawn.LOGGER.fatal("OreSpawnAPI: registering block "+state.getBlock()+" for generation");
    }

    public SpawnEntryImpl(IBlockState state, int size, int variation, float frequency, int minHeight, int maxHeight,
			Biome[] biomes, IFeature featureGen, IBlockState blockRep) {
    	this.paramStore = new JsonObject();
    	this.paramStore.addProperty(sz, size);
    	this.paramStore.addProperty(var, variation);
    	this.paramStore.addProperty(freq, frequency);
    	this.paramStore.addProperty(mny, minHeight);
    	this.paramStore.addProperty(mxy, maxHeight);
    	this.generator = featureGen;
    	this.blockRep = blockRep;
    	this.state = state;
    	
        if( biomes == null || biomes.length == 0 ) {
        	this.biomes = Collections.<Biome>emptyList();
        } else {
        	this.biomes = new ArrayList<>();
        	for( Biome b : biomes ) {
        		this.biomes.add(b);
        	}
        }
	}

	public SpawnEntryImpl(IBlockState state, JsonObject parameters, Biome[] biomes, IFeature featureGen,
			IBlockState blockRep) {
		this.paramStore = parameters.getAsJsonObject();
    	this.generator = featureGen;
    	this.blockRep = blockRep;
    	this.state = state;
    	
        if( biomes == null || biomes.length == 0 ) {
        	this.biomes = Collections.<Biome>emptyList();
        } else {
        	this.biomes = new ArrayList<>();
        	for( Biome b : biomes ) {
        		this.biomes.add(b);
        	}
        }
	}

	@Override
    public IBlockState getState() {
        return this.state;
    }

    @Override
    public int getSize() {
        return this.paramStore.get(sz).getAsInt();
    }

    @Override
    public int getVariation() {
        return this.paramStore.get(var).getAsInt();
    }

    @Override
    public float getFrequency() {
        return this.paramStore.get(freq).getAsFloat();
    }

    @Override
    public int getMinHeight() {
        return this.paramStore.get(mny).getAsInt();
    }

    @Override
    public int getMaxHeight() {
        return this.paramStore.get(mxy).getAsInt();
    }

    @Override
    public List<Biome> getBiomes() {
        return Collections.unmodifiableList(this.biomes);
    }
    
    @Override
    public IFeature getFeatureGen() {
    	return this.generator;
    }
    
    @Override
    public JsonObject getParameters() {
    	return this.paramStore.getAsJsonObject();
    }
    
    @Override
    public IBlockState getReplacement() {
    	return this.blockRep;
    }
}
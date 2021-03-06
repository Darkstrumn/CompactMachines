package org.dave.compactmachines3.world;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.IChunkGenerator;
import org.dave.compactmachines3.misc.ConfigurationHandler;
import org.dave.compactmachines3.world.tools.DimensionTools;

public class WorldProviderMachines extends WorldProvider {
    public WorldProviderMachines() {
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionTools.baseType;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorMachines(this.world);
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return -5;
    }

    @Override
    public WorldBorder createWorldBorder() {
        return new WorldBorderMachines();
    }

    @Override
    public boolean doesWaterVaporize() {
        return ConfigurationHandler.CompatSettings.doesWaterVaporize;
    }

    @Override
    public Biome getBiomeForCoords(BlockPos pos) {
        if(world == null) {
            return Biomes.PLAINS;
        }

        return super.getBiomeForCoords(pos);
    }
}
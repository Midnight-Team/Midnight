/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.dimension;

import com.google.common.collect.ImmutableMap;
import midnight.common.world.dimension.AbstractMnDimension;
import midnight.common.world.dimension.MnDimension;
import midnight.common.world.dimension.TheMidnightDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;

import java.util.Map;

/**
 * This class registers and stores the list of Midnight dimensions. This used to
 * be {@code MnDimensions} job, but it has since been moved here since all
 * dimension logic can be centeralized here.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
public final class DimensionUtil {
    public static final Map<MnDimension, AbstractMnDimension> DIMENSIONS =
        ImmutableMap.<MnDimension, AbstractMnDimension>builder()
                    .put(MnDimension.THE_MIDNIGHT, new TheMidnightDimension())
                    .build();

    private DimensionUtil() {
    }

    public static void registerDimensions(SimpleRegistry<Dimension> registry, Registry<Biome> biomeReg, Registry<DimensionSettings> settingsReg, long seed) {
        DIMENSIONS.forEach((identifier, dim) -> dim.createDefaultDimensionOptions(registry, biomeReg, settingsReg, seed));
    }

    public static boolean isInDimension(World world, MnDimension identifer) {
        return world.dimension().equals(identifer.getKey());
    }

    public static World getDimensionInServer(MinecraftServer server, MnDimension identifer) {
        return server.getLevel(identifer.getKey());
    }
}

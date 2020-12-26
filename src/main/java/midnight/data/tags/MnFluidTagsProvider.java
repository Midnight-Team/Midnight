/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

/**
 * Generator for fluid tag JSONs.
 */
public class MnFluidTagsProvider extends AbstractTagProvider<Fluid> {
    public MnFluidTagsProvider(DataGenerator gen) {
        super(gen, Registry.FLUID);
    }

    @Override
    protected void configure() {
        //
        //
        //  REGISTER FLUID TAGS HERE
        //
        //
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/fluids/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight/FluidTags";
    }
}
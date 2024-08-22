package com.Miguel_dev.Create_Vehicular_Works.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class vehicle_parts_Depot extends Block {

    // Definir la forma del bloque (hitbox)
    private static final VoxelShape BLOCK_SHAPE = Block.box(
        -8.0, 0.0,
        -8.0, 24.0,
        16.0, 24.0
    );

    // Constructor del bloque con sus propiedades
    public vehicle_parts_Depot(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BLOCK_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BLOCK_SHAPE;
    }
}

package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import com.Miguel_dev.Create_Vehicular_Works.CVW_BlockEntityTypes;
import com.Miguel_dev.Create_Vehicular_Works.CVW_Shapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;


public class VehiclePartsMakerBlock extends HorizontalKineticBlock implements IBE<VehiclePartsMakerBlockEntity> {
    
	public static final VoxelShape VEHICLE_PARTS_MAKER_SHAPE = CVW_Shapes.shape(0,0,0,16,12,16).build();



	public VehiclePartsMakerBlock(Properties properties) {
		super(properties);
	}

	
	@Override
	public @NotNull VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return VEHICLE_PARTS_MAKER_SHAPE;
	}
	
	@Override
	public @NotNull InteractionResult use(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
		if (!player.getItemInHand(handIn).isEmpty())
			return InteractionResult.PASS;
		if (worldIn.isClientSide)
			return InteractionResult.SUCCESS;

		withBlockEntityDo(worldIn, pos, vehiclepartsmaker -> {
			boolean emptyOutput = true;
			ItemStackHandler inv = vehiclepartsmaker.inventory;
			for (int slot = 0; slot < inv.getSlotCount(); slot++) {
				ItemStack stackInSlot = inv.getStackInSlot(slot);
				if (!stackInSlot.isEmpty())
					emptyOutput = false;
				player.getInventory().placeItemBackInInventory(stackInSlot);
				inv.setStackInSlot(slot, ItemStack.EMPTY);
			}

			if (emptyOutput) {
				inv = vehiclepartsmaker.inventory;
				for (int slot = 0; slot < inv.getSlotCount(); slot++) {
					player.getInventory().placeItemBackInInventory(inv.getStackInSlot(slot));
					inv.setStackInSlot(slot, ItemStack.EMPTY);
				}
			}

			vehiclepartsmaker.setChanged();
			vehiclepartsmaker.sendData();
		});

		return InteractionResult.SUCCESS;
	}

	/*@Override
	public void updateEntityAfterFallOn(@Nonnull BlockGetter worldIn, @Nonnull Entity entityIn) {
		super.updateEntityAfterFallOn(worldIn, entityIn);

		if (entityIn.level().isClientSide)
			return;
		if (!(entityIn instanceof ItemEntity))
			return;
		if (!entityIn.isAlive())
			return;

		VehiclePartsMakerBlockEntity vehiclepartsmaker = null;
		for (BlockPos pos : Iterate.hereAndBelow(entityIn.blockPosition())) {
			vehiclepartsmaker = getBlockEntity(worldIn, pos);
		}
		if (vehiclepartsmaker == null)
			return;

		BlockPos pos = entityIn.blockPosition().below();
		VehiclePartsMakerBlockEntity be = (VehiclePartsMakerBlockEntity) entityIn.level().getBlockEntity(pos);
		if (be == null) return;
		if (be.getSpeed() == 0) return;
		be.insertItem((ItemEntity) entityIn);

	}*/

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		super.updateEntityAfterFallOn(worldIn, entityIn);
		if (!(entityIn instanceof ItemEntity))
			return;
		if (entityIn.level().isClientSide)
			return;

		BlockPos pos = entityIn.blockPosition();
		withBlockEntityDo(entityIn.level(), pos, be -> {
			if (be.getSpeed() == 0)
				return;
			be.insertItem((ItemEntity) entityIn);
		});
	}

	/*@Override
	public void onRemove(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			withBlockEntityDo(worldIn, pos, te -> {
				ItemHelper.dropContents(worldIn, pos, te.);
				ItemHelper.dropContents(worldIn, pos, te.outputInv);
			});

			worldIn.removeBlockEntity(pos);
		}
	}*/

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction prefferedSide = getPreferredHorizontalFacing(context);
		if (prefferedSide != null)
			return defaultBlockState().setValue(HORIZONTAL_FACING, prefferedSide);
		return super.getStateForPlacement(context);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(HORIZONTAL_FACING)
			.getAxis();
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == state.getValue(HORIZONTAL_FACING)
			.getAxis();
	}

	@Override
	public BlockEntityType<? extends VehiclePartsMakerBlockEntity> getBlockEntityType() {
		return CVW_BlockEntityTypes.VEHICLE_PARTS_MAKER.get();
	}

	@Override
	public Class<VehiclePartsMakerBlockEntity> getBlockEntityClass() {
		return VehiclePartsMakerBlockEntity.class;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CVW_BlockEntityTypes.VEHICLE_PARTS_MAKER.create(pos, state);
	}
}
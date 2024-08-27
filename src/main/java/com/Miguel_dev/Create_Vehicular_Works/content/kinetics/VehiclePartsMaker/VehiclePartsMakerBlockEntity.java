package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;

import com.Miguel_dev.Create_Vehicular_Works.CVW_RecipeTypes;
import com.Miguel_dev.Create_Vehicular_Works.CVW_SoundEvents;
import com.Miguel_dev.Create_Vehicular_Works.CVW_main;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import io.github.fabricators_of_create.porting_lib.util.NBTSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VehiclePartsMakerBlockEntity extends KineticBlockEntity implements SidedStorageBlockEntity {
    private static final Object partsmakingRecipesKey = new Object();
	public static final Supplier<RecipeType<?>> woodcuttingRecipeType =
		Suppliers.memoize(() -> BuiltInRegistries.RECIPE_TYPE.get(new ResourceLocation("druidcraft", "woodcutting")));
	
	public ProcessingInventory inventory;
	private int recipeIndex;
	private FilteringBehaviour filtering;
	//List<Recipe> recipes = this.level.getRecipeManager().getAllRecipesFor(CVW_RecipeTypes.simpleType(CVW_main.resourceLocation("create_vehicular_works")));
	private boolean soundverifier = false;

	private ItemStack playEvent;

	public VehiclePartsMakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		inventory = new ProcessingInventory(this::start).withSlotLimit(!AllConfigs.server().recipes.bulkCutting.get());
		inventory.remainingTime = -1;
		recipeIndex = 0;
		playEvent = ItemStack.EMPTY;
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		filtering = new FilteringBehaviour(this, new VehiclePartsMakerFilterSlot()).forRecipes();
		behaviours.add(filtering);
		behaviours.add(new DirectBeltInputBehaviour(this));
		registerAwardables(behaviours, AllAdvancements.SAW_PROCESSING); //<- REMEMBER TO ADD ADVANCEMENT
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		compound.put("Inventory", inventory.serializeNBT());
		compound.putInt("RecipeIndex", recipeIndex);
		super.write(compound, clientPacket);

		if (!clientPacket || playEvent.isEmpty())
			return;
		compound.put("PlayEvent", NBTSerializer.serializeNBT(playEvent));
		playEvent = ItemStack.EMPTY;
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		inventory.deserializeNBT(compound.getCompound("Inventory"));
		recipeIndex = compound.getInt("RecipeIndex");
		if (compound.contains("PlayEvent"))
			playEvent = ItemStack.of(compound.getCompound("PlayEvent"));
	}

	@Override
	protected AABB createRenderBoundingBox() {
		return new AABB(worldPosition).inflate(.125f);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void tickAudio() {
		super.tickAudio();
		if (getSpeed() == 0)
			return;

		if (!playEvent.isEmpty()) {
			spawnEventParticles(playEvent);
			playEvent = ItemStack.EMPTY;
			CVW_SoundEvents.VEHICLE_PARTS_MAKER_ACTIVATE.playAt(level, worldPosition, 3, 1, true);
			return;
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (getSpeed() == 0)
			return;
		if (inventory.remainingTime == -1) {
			if (!inventory.isEmpty() && !inventory.appliedRecipe)
				start(inventory.getStackInSlot(0));
			return;
		}

		float processingSpeed = Mth.clamp(Math.abs(getSpeed()) / 24, 1, 128);
		inventory.remainingTime -= processingSpeed;

		if (inventory.remainingTime > 0)
			spawnParticles(inventory.getStackInSlot(0));

		if (inventory.remainingTime < 5 && !inventory.appliedRecipe) {
			if (level.isClientSide && !isVirtual())
				return;
			playEvent = inventory.getStackInSlot(0);
			applyRecipe();
			inventory.appliedRecipe = true;
			inventory.recipeDuration = 20;
			inventory.remainingTime = 20;
			sendData();
			return;
		}

		Vec3 itemMovement = getItemMovementVec();
		Direction itemMovementFacing = Direction.getNearest(itemMovement.x, itemMovement.y, itemMovement.z);
		if (inventory.remainingTime > 0)
			return;
		inventory.remainingTime = 0;

		for (int slot = 0; slot < inventory.getSlotCount(); slot++) {
			ItemStack stack = inventory.getStackInSlot(slot);
			if (stack.isEmpty())
				continue;
			ItemStack tryExportingToBeltFunnel = getBehaviour(DirectBeltInputBehaviour.TYPE)
				.tryExportingToBeltFunnel(stack, itemMovementFacing.getOpposite(), false);
			if (tryExportingToBeltFunnel != null) {
				if (tryExportingToBeltFunnel.getCount() != stack.getCount()) {
					inventory.setStackInSlot(slot, tryExportingToBeltFunnel);
					notifyUpdate();
					return;
				}
				if (!tryExportingToBeltFunnel.isEmpty())
					return;
			}
		}

		BlockPos nextPos = worldPosition.offset(BlockPos.containing(itemMovement));
		DirectBeltInputBehaviour behaviour = BlockEntityBehaviour.get(level, nextPos, DirectBeltInputBehaviour.TYPE);
		if (behaviour != null) {
			boolean changed = false;
			if (!behaviour.canInsertFromSide(itemMovementFacing))
				return;
			if (level.isClientSide && !isVirtual())
				return;
			for (int slot = 0; slot < inventory.getSlotCount(); slot++) {
				ItemStack stack = inventory.getStackInSlot(slot);
				if (stack.isEmpty())
					continue;
				ItemStack remainder = behaviour.handleInsertion(stack, itemMovementFacing, false);
				if (ItemStack.matches(remainder, stack))
					continue;
				inventory.setStackInSlot(slot, remainder);
				changed = true;
			}
			if (changed) {
				setChanged();
				sendData();
			}
			return;
		}

		// Eject Items
		Vec3 outPos = VecHelper.getCenterOf(worldPosition)
			.add(itemMovement.scale(.5f)
				.add(0, .5, 0));
		Vec3 outMotion = itemMovement.scale(.0625)
			.add(0, .125, 0);
		for (int slot = 0; slot < inventory.getSlotCount(); slot++) {
			ItemStack stack = inventory.getStackInSlot(slot);
			if (stack.isEmpty())
				continue;
			ItemEntity entityIn = new ItemEntity(level, outPos.x, outPos.y, outPos.z, stack);
			entityIn.setDeltaMovement(outMotion);
			level.addFreshEntity(entityIn);
		}
		inventory.clear();
		level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
		inventory.remainingTime = -1;
		sendData();
	} 

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	public void destroy() {
		super.destroy();
		ItemHelper.dropContents(level, worldPosition, inventory);
	}

	@Nullable
	@Override
	public Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
		return face == Direction.DOWN ? null : inventory;
	}

	protected void spawnEventParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		ParticleOptions particleData = null;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);

		RandomSource r = level.random;
		Vec3 v = VecHelper.getCenterOf(this.worldPosition)
			.add(0, 5 / 16f, 0);
		for (int i = 0; i < 10; i++) {
			Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), r, .125f);
			level.addParticle(particleData, v.x, v.y, v.z, m.x, m.y, m.y);
		}
	}

	protected void spawnParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		ParticleOptions particleData = null;
		float speed = 1;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else {
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);
			speed = .125f;
		}

		RandomSource r = level.random;
		Vec3 vec = getItemMovementVec();
		Vec3 pos = VecHelper.getCenterOf(this.worldPosition);
		float offset = inventory.recipeDuration != 0 ? (float) (inventory.remainingTime) / inventory.recipeDuration : 0;
		offset /= 2;
		if (inventory.appliedRecipe)
			offset -= .5f;
		level.addParticle(particleData, pos.x() + -vec.x * offset, pos.y() + .45f, pos.z() + -vec.z * offset,
			-vec.x * speed, r.nextFloat() * speed, -vec.z * speed);
	}

	public Vec3 getItemMovementVec() {
		boolean alongX = !getBlockState().getValue(VehiclePartsMakerBlock.AXIS_ALONG_FIRST_COORDINATE);
		int offset = getSpeed() < 0 ? -1 : 1;
		return new Vec3(offset * (alongX ? -1 : 0), 0, offset * (alongX ? 0 : 1));
	}

	private void applyRecipe() {
		List<? extends Recipe<?>> recipes = getRecipes();
		if (recipes.isEmpty())
			return;
		if (recipeIndex >= recipes.size())
			recipeIndex = 0;

		Recipe<?> recipe = recipes.get(recipeIndex);

		int rolls = inventory.getStackInSlot(0)
			.getCount();
		inventory.clear();

		List<ItemStack> list = new ArrayList<>();
		for (int roll = 0; roll < rolls; roll++) {
			List<ItemStack> results = new LinkedList<ItemStack>();
			if (recipe instanceof PartsMakingRecipe)
				results = ((PartsMakingRecipe) recipe).rollResults();
			for (int i = 0; i < results.size(); i++) {
				ItemStack stack = results.get(i);
				ItemHelper.addToList(stack, list);
			}
		}

		for (int slot = 0; slot < list.size() && slot + 1 < inventory.getSlotCount(); slot++)
			inventory.setStackInSlot(slot + 1, list.get(slot));

		award(AllAdvancements.SAW_PROCESSING);
	}

	private List<? extends Recipe<?>> getRecipes() {
		Optional<PartsMakingRecipe> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, inventory.getStackInSlot(0),
			CVW_RecipeTypes.PARTSMAKING.getType(), PartsMakingRecipe.class);
		if (assemblyRecipe.isPresent() && filtering.test(assemblyRecipe.get()
			.getResultItem(level.registryAccess())))
			return ImmutableList.of(assemblyRecipe.get());

		Predicate<Recipe<?>> types = RecipeConditions.isOfType(CVW_RecipeTypes.PARTSMAKING.getType(),
			AllConfigs.server().recipes.allowWoodcuttingOnSaw.get() ? woodcuttingRecipeType.get() : null);

		List<Recipe<?>> startedSearch = RecipeFinder.get(partsmakingRecipesKey, level, types);
		return startedSearch.stream()
			.filter(RecipeConditions.outputMatchesFilter(filtering))
			.filter(RecipeConditions.firstIngredientMatches(inventory.getStackInSlot(0)))
			.filter(r -> !CVW_RecipeTypes.shouldIgnoreInAutomation(r))
			.collect(Collectors.toList());
	}

	public void insertItem(ItemEntity entity) {
		if (!inventory.isEmpty())
			return;
		if (!entity.isAlive())
			return;
		if (level.isClientSide)
			return;

		inventory.clear();
		try (Transaction t = TransferUtil.getTransaction()) {
			ItemStack contained = entity.getItem();
			long inserted = inventory.insert(ItemVariant.of(contained), contained.getCount(), t);
			if (contained.getCount() == inserted)
				entity.discard();
			else
				entity.setItem(ItemHandlerHelper.copyStackWithSize(contained, (int) (contained.getCount() - inserted)));
			t.commit();
		}
	}

	public void start(ItemStack inserted) {
		if (inventory.isEmpty())
			return;
		if (level.isClientSide && !isVirtual())
			return;

		List<? extends Recipe<?>> recipes = getRecipes();
		boolean valid = !recipes.isEmpty();
		int time = 50;

		if (recipes.isEmpty()) {
			inventory.remainingTime = inventory.recipeDuration = 10;
			inventory.appliedRecipe = false;
			sendData();
			return;
		}

		if (valid) {
			recipeIndex++;
			if (recipeIndex >= recipes.size())
				recipeIndex = 0;
			soundverifier = valid;
		}

		Recipe<?> recipe = recipes.get(recipeIndex);
		if (recipe instanceof PartsMakingRecipe) {
			time = ((PartsMakingRecipe) recipe).getProcessingDuration();
		}

		inventory.remainingTime = time * Math.max(1, (inserted.getCount() / 5));
		inventory.recipeDuration = inventory.remainingTime;
		inventory.appliedRecipe = false;
		sendData();

		/*for (int i = 0; i < recipelist.size(); i++){
			CVW_main.LOGGER.info(recipelist.get(i).toString());
		} */
		
	}
}
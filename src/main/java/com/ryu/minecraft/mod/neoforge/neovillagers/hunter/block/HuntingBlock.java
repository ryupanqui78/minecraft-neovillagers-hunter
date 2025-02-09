package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.block;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.HuntingMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HuntingBlock extends Block {
    
    public static final String BLOCK_NAME = "hunting_table";
    public static final MapCodec<HuntingBlock> CODEC = BlockBehaviour.simpleCodec(HuntingBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    private static final Component CONTAINER_TITLE = Component.translatable("screen.container.hunting");
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    
    public HuntingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HuntingBlock.FACING, Direction.NORTH));
    }
    
    @Override
    protected MapCodec<HuntingBlock> codec() {
        return HuntingBlock.CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(HuntingBlock.FACING);
    }
    
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, playerInv, pAccess) -> new HuntingMenu(pContainerId, playerInv,
                ContainerLevelAccess.create(pLevel, pPos)), HuntingBlock.CONTAINER_TITLE);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return HuntingBlock.SHAPE;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HuntingBlock.FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide && (pPlayer instanceof ServerPlayer)) {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
    
}

package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

/**
 * Created by codew on 4/05/2016.
 */
class ChestBlock extends Block
{
    {
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setUnlocalizedName(ExampleMod.MODID + "." + ExampleMod.blockName);
        setRegistryName(ExampleMod.blockId);
    }

    public ChestBlock() {super(Material.WOOD);}

    @Override
    public ExtendedBlockState createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty[]{ExampleMod.FACING, Properties.StaticProperty}, new IUnlistedProperty[]{Properties.AnimationProperty});
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(ExampleMod.FACING, BlockPistonBase.getFacingFromEntity(pos, placer));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(ExampleMod.FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing) state.getValue(ExampleMod.FACING)).getIndex();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new ChestTileEntity();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(Properties.StaticProperty, true);
    }

        /*@Override
        public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof Chest && state instanceof IExtendedBlockState)
            {
                return ((Chest)te).getState((IExtendedBlockState)state);
            }
            return super.getExtendedState(state, world, pos);
        }*/

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ChestTileEntity)
            {
                ((ChestTileEntity) te).click(player.isSneaking());
            }
        }
        return true;
    }
}
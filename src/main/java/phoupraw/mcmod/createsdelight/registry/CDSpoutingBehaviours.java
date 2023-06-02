package phoupraw.mcmod.createsdelight.registry;

public final class CDSpoutingBehaviours {
    //public static final BlockSpoutingBehaviour PAN = new BlockSpoutingBehaviour() {
    //    @Override
    //    public long fillBlock(World world, BlockPos pos, SpoutTileEntity spout, FluidStack availableFluid, boolean simulate) {
    //        var blockEntity = world.getBlockEntity(pos);
    //        if (!(blockEntity instanceof PanBlockEntity pan)) return 0;
    //        try (var transaction = Transaction.openOuter()) {
    //            long amount = pan.getTank().getCapability().insert(availableFluid.getType(), availableFluid.getAmount(), transaction);
    //            if (amount == 0) return 0;
    //            ((SpoutExtra) spout).setBottomY(1 / 16.0);
    //            if (!simulate) transaction.commit();
    //            return amount;
    //        }
    //    }
    //};
    //public static final BlockSpoutingBehaviour SMART_DARIN = new BlockSpoutingBehaviour() {
    //    @Override
    //    public long fillBlock(World world, BlockPos pos, SpoutTileEntity spout, FluidStack availableFluid, boolean simulate) {
    //        var blockEntity = world.getBlockEntity(pos);
    //        if (!(blockEntity instanceof SmartDrainBlockEntity drain)) return 0;
    //        try (var transaction = TransferUtil.getTransaction()) {
    //            long amount = drain.getBehaviour(SmartFluidTankBehaviour.TYPE).getCapability().insert(availableFluid.getType(), availableFluid.getAmount(), transaction);
    //            if (amount == 0) return 0;
    //            ((SpoutExtra) spout).setBottomY(2 / 16.0);
    //            if (!simulate) transaction.commit();
    //            return amount;
    //        }
    //    }
    //};
    static {
        //BlockSpoutingBehaviour.addCustomSpoutInteraction(CDIdentifiers.PAN, PAN);
        //BlockSpoutingBehaviour.addCustomSpoutInteraction(CDIdentifiers.SMART_DRAIN, SMART_DARIN);
    }
    private CDSpoutingBehaviours() {
    }

    public interface SpoutExtra {
        double getBottomY();
        void setBottomY(double y);
    }
}

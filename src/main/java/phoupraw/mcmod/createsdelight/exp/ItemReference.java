package phoupraw.mcmod.createsdelight.exp;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

import java.util.Map;
public interface ItemReference {
    ItemVariant getVariant();
    void setVariant(ItemVariant variant);
//    ItemVariant getLazyVariant();  void setLazyVariant(ItemVariant variant);
    //    BlockState getStatedBlock();
//    void setStatedBlock(BlockState statedBlock);
    /**
     @return 与此物品紧邻的其它物品
     */
    Map<ItemVariant, Integer> getItems();
    /**
     @return 此物品所浸入的流体
     */
    Map<FluidVariant, Long> getFluids();
    /**
     @return 此物品所在环境的温度，单位；开尔文
     */
    double getTemperature();
    /**
     @return 此物品所在环境的压强，单位：帕斯卡
     */
    double getPressure();


}

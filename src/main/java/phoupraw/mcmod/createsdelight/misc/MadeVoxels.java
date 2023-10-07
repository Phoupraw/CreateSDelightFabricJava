package phoupraw.mcmod.createsdelight.misc;

import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.Map;
import java.util.TreeMap;

public class MadeVoxels {
    /**
     {@link Map#of}是无序的，不会按照你写的顺序来，所以得套个{@link TreeMap}保证每次类加载时顺序保持一致
     */
    public static final Map<String, String> PREDEFINED = new TreeMap<>(Map.of(
      "brownie", "{voxelRecord:{pallete:[\"createsdelight:chocolate_block\",\"createsdelight:cream\"],size:{X:16,Y:16,Z:16},gzip:[B;31B,-117B,8B,0B,0B,0B,0B,0B,0B,-1B,-19B,-108B,75B,14B,-128B,32B,16B,67B,-89B,-36B,-1B,-48B,-118B,-63B,5B,3B,12B,31B,33B,74B,-20B,-37B,53B,-95B,-112B,-50B,7B,17B,66B,-56B,-97B,65B,-32B,-42B,46B,80B,-46B,-64B,37B,49B,-22B,-9B,94B,68B,-9B,117B,-6B,-3B,97B,-61B,-81B,-11B,-20B,-4B,105B,61B,94B,-15B,23B,-13B,38B,-7B,117B,41B,-108B,-34B,45B,63B,42B,-2B,92B,-2B,40B,-78B,-46B,-125B,-17B,63B,-103B,-1B,25B,-7B,-37B,-5B,47B,-10B,8B,-52B,-98B,-65B,-43B,-11B,-21B,-49B,111B,47B,-64B,-41B,-6B,-73B,36B,-65B,-79B,0B,-69B,-19B,127B,-51B,-97B,-101B,127B,107B,4B,-6B,-21B,127B,118B,0B,-21B,-2B,-17B,-90B,-9B,93B,57B,111B,54B,63B,33B,-124B,40B,14B,65B,-80B,-74B,-100B,0B,16B,0B,0B]}}",
      "minecraft", "{voxelRecord:{gzip:[B;31B,-117B,8B,0B,0B,0B,0B,0B,0B,-1B,-19B,-105B,75B,14B,-128B,32B,12B,68B,109B,-121B,-5B,-97B,-39B,-74B,-126B,-33B,5B,26B,18B,-57B,-128B,53B,16B,39B,-84B,94B,59B,13B,101B,-102B,6B,-113B,116B,-120B,-25B,90B,53B,-91B,109B,-71B,86B,0B,121B,11B,-115B,53B,-14B,-71B,22B,-27B,-102B,29B,-51B,-4B,123B,-87B,55B,116B,-48B,-53B,-102B,15B,118B,-76B,-14B,-41B,-14B,-127B,-67B,-122B,-99B,-25B,-54B,91B,14B,4B,29B,-14B,-97B,121B,-81B,-4B,34B,-111B,-125B,78B,-8B,107B,-11B,-66B,104B,-25B,14B,-13B,-37B,7B,54B,61B,-125B,-33B,-68B,-81B,-26B,1B,-11B,31B,54B,-3B,-5B,-3B,-17B,-123B,55B,11B,68B,19B,-4B,-4B,108B,122B,-118B,-1B,17B,-2B,-57B,-88B,-3B,15B,-51B,75B,122B,-32B,63B,-21B,-22B,-3B,39B,-53B,-12B,-77B,-116B,-127B,108B,122B,-50B,-4B,19B,3B,-112B,-13B,119B,120B,-1B,-33B,-101B,127B,-117B,7B,-40B,-12B,-52B,-9B,15B,62B,-15B,-2B,25B,60B,102B,-22B,-128B,-19B,64B,0B,16B,0B,0B],pallete:[\"createsdelight:apple_jam\",\"createsdelight:butter_block\",\"createsdelight:cream\",\"createsdelight:wheat_cake_base_block\"],size:{X:16,Y:16,Z:16}}}"
    ));
    public static String getTranslationKey(String cakeName) {
        return CSDIdentifiers.MADE_VOXEL.toTranslationKey() + "." + cakeName;
    }
}

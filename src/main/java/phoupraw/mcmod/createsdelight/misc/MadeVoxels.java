package phoupraw.mcmod.createsdelight.misc;

import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.Map;

public class MadeVoxels {
    public static final Map<String, String> PREDEFINED = Map.of(
      "brownie", "{voxelRecord:{pallete:[\"createsdelight:chocolate_block\",\"createsdelight:cream\"],size:{X:16,Y:16,Z:16},gzip:[B;31B,-117B,8B,0B,0B,0B,0B,0B,0B,-1B,-19B,-108B,75B,14B,-128B,32B,16B,67B,-89B,-36B,-1B,-48B,-118B,-63B,5B,3B,12B,31B,33B,74B,-20B,-37B,53B,-95B,-112B,-50B,7B,17B,66B,-56B,-97B,65B,-32B,-42B,46B,80B,-46B,-64B,37B,49B,-22B,-9B,94B,68B,-9B,117B,-6B,-3B,97B,-61B,-81B,-11B,-20B,-4B,105B,61B,94B,-15B,23B,-13B,38B,-7B,117B,41B,-108B,-34B,45B,63B,42B,-2B,92B,-2B,40B,-78B,-46B,-125B,-17B,63B,-103B,-1B,25B,-7B,-37B,-5B,47B,-10B,8B,-52B,-98B,-65B,-43B,-11B,-21B,-49B,111B,47B,-64B,-41B,-6B,-73B,36B,-65B,-79B,0B,-69B,-19B,127B,-51B,-97B,-101B,127B,107B,4B,-6B,-21B,127B,118B,0B,-21B,-2B,-17B,-90B,-9B,93B,57B,111B,54B,63B,33B,-124B,40B,14B,65B,-80B,-74B,-100B,0B,16B,0B,0B]}}",
      "minecraft", "{voxelRecord:{pallete:[\"createsdelight:apple_jam\",\"createsdelight:cream\",\"createsdelight:wheat_cake_base_block\"],size:{X:16,Y:16,Z:16},gzip:[B;31B,-117B,8B,0B,0B,0B,0B,0B,0B,-1B,-19B,-105B,-37B,10B,-128B,48B,12B,67B,-105B,-10B,-1B,-1B,89B,-68B,-52B,85B,16B,-76B,19B,41B,-92B,61B,-32B,67B,30B,79B,-119B,-112B,-75B,-106B,28B,-67B,-32B,-49B,34B,-86B,-29B,91B,-77B,-27B,57B,71B,-13B,-39B,-33B,70B,121B,-111B,55B,64B,-29B,-17B,-66B,-57B,9B,-112B,-45B,31B,96B,-14B,-9B,-9B,-65B,-5B,115B,-12B,127B,-14B,-1B,7B,-117B,-65B,-5B,30B,-69B,-2B,113B,-128B,104B,-5B,-14B,-1B,-35B,-9B,-50B,-97B,-88B,-1B,115B,-2B,125B,4B,68B,-37B,7B,-12B,31B,99B,-3B,-92B,-12B,-81B,-3B,107B,22B,112B,-76B,125B,-67B,127B,-110B,-77B,0B,53B,-19B,-124B,57B,0B,16B,0B,0B]}}"
    );
    public static String getTranslationKey(String cakeName) {
        return CSDIdentifiers.MADE_VOXEL.toTranslationKey() + "." + cakeName;
    }
}

# 0.1.0-pre9

## Fix

- Incompactible with Immersive Weathering.
- Crashes on launch at dedicated server.

# 0.1.0-pre8

## New

### Jelly Beans Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.
  - Leaves jellye beans.

### Sweet Berries Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.
  - Stackable.

### Gateau Basque

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.

### Multi-layers Sweet Berries Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.
  - Leaves 3 layers of sweet berries cakes.

### Brownie

- Directly edible.
- Placable and edible.

### Apple Cream Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.

### Apple Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.

### Carrot Cream Cake

- Directly edible.
- Placable and edible.
  - Drops itself when destroyed.

### Pumpkin Seeds Oil

- From milling pumpkin seeds.
- Able to replace sunflower seeds oil.
- Drinkible.

### Iron Bowl (Experimental)

- In creative mode, able to hold item like bundle.
- Hold fluid.
- For baking.

### Oven (Experimental)

- For baking.
- Needs blaze burner.
- Support item IO at every side.
- Bakes item or fluid in iron bowl.

### Other (Experimental)

- Add many items and fluids, still in development. Textures lost is normal.

## Changes

- Types of fluid ingredients of mixing recipe increases from 2 to 4.
- Types of ingredient fluids which basin can hold increases from 2 to 4.
- Types of fluid ingredients of milling recipe increases from 2 to 4.
- Millstone can hold fluid, which can be inserted or extracted from up side. It outputs fluid to nearby basin and stops working when storing too much fluid.
- Crushing wheel controller can hold fluid, which can't be inserted or extracted. It outputs fluid to basin or item drain below and stops working when storing too much fluid.
- Pumpkin seed oil can now be used in formulations that previously only used sunflower seed oil.
- Delete the recipe that compats sunflower to get its oil.
- Sunflower seeds oil is drinkable.

## Fix

- (Bug of Create) Ingredients and results of recipes in sequenced assembly can't be looked up by REI.
- (Bug of Create) REI will throw an exception if sequenced assembly contains more than one filling step.
- (Bug of Create) Fluid tag ingredient of basin recipe only shows its first type of fluid in REI.
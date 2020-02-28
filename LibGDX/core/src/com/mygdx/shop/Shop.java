package com.mygdx.shop;

import com.mygdx.extras.Inventory;

public class Shop {
    /*
    TODO Be able to update inventory
        -update mask count/ fluid (heal/burn)
        -update food count
     */

    public final float FLUID_PER_PURCHASE = 10f;
    public final int MASK_PER_PURCHASE = 1;
    public final int COST_PER_MASK = 10;
    public final int COST_PER_FLUID = 20;

    private Inventory inventory;

    public Shop(){
        inventory.getInventoryInstance();
    }

    public void buyHealingFluid(){
        inventory.changeHealingFluid(FLUID_PER_PURCHASE);
        inventory.changeFoodAmount(COST_PER_FLUID);
    }

    public void buyBurningFluid(){
        inventory.changeBurningFluid(FLUID_PER_PURCHASE);
        inventory.changeFoodAmount(COST_PER_FLUID);
    }

    public void buyMasks(){
        inventory.changeNumberOfMasks(MASK_PER_PURCHASE);
        inventory.changeFoodAmount(COST_PER_MASK);
    }
}

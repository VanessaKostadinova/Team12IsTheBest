package com.mygdx.shop;

import com.mygdx.extras.PermanetPlayer;

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

    private PermanetPlayer permanetPlayer;

    public Shop(){
        permanetPlayer.getPermanentPlayerInstance();
    }

    public void buyHealingFluid(){
        permanetPlayer.changeHealingFluid(FLUID_PER_PURCHASE);
        permanetPlayer.changeFoodAmount(COST_PER_FLUID);
    }

    public void buyBurningFluid(){
        permanetPlayer.changeBurningFluid(FLUID_PER_PURCHASE);
        permanetPlayer.changeFoodAmount(COST_PER_FLUID);
    }

    public void buyMasks(){
        permanetPlayer.changeNumberOfMasks(MASK_PER_PURCHASE);
        permanetPlayer.changeFoodAmount(COST_PER_MASK);
    }
}

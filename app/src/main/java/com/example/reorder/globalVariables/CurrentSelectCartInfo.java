package com.example.reorder.globalVariables;

import com.example.reorder.info.Cart;

public class CurrentSelectCartInfo {
    private static Cart cart =new Cart();

    public static Cart getCart() {
        return cart;
    }

    public static void setCart(Cart cart) {
        CurrentSelectCartInfo.cart = cart;
    }
}
package com.example.reorder.Result;

import com.example.reorder.info.CartInfo;

import java.util.List;

public class CartResult {
    int result;
    List<CartInfo> cartInfo;

    public CartResult(int result, List<CartInfo> cartInfo) {
        this.result = result;
        this.cartInfo = cartInfo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<CartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }
}

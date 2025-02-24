package com.itheima.reggie.service;

import com.itheima.reggie.entity.ShoppingCart;
import java.util.List;
import java.util.Map;

/**
 * Shopping Cart Service Interface (Hibernate Version)
 */
public interface ShoppingCartService {
    ShoppingCart addItem(ShoppingCart shoppingCart);
    List<ShoppingCart> getCartItemsByUserId(Long userId);
    void clearCart(Long userId);

    Map<String, Object> getShoppingCartPage(int page, int size);

}

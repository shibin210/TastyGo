package com.itheima.reggie.controller;

import com.itheima.reggie.common._BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Shopping Cart Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setUserId(_BaseContext.getCurrentId());
        ShoppingCart savedItem = shoppingCartService.addItem(shoppingCart);
        return R.success(savedItem);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        List<ShoppingCart> cartItems = shoppingCartService.getCartItemsByUserId(_BaseContext.getCurrentId());
        return R.success(cartItems);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        shoppingCartService.clearCart(_BaseContext.getCurrentId());
        return R.success("Shopping cart cleared");
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page     The page number (starting from 1 on the frontend, but adjusted to start from 0 internally).
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of categories.
     */
    @GetMapping("/page")
    public R<Map<String, Object>> getCategoryPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // Call the service layer to retrieve paginated data
        Map<String, Object> pageInfo = shoppingCartService.getShoppingCartPage(page, pageSize);
        return R.success(pageInfo);
    }
}

package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.CartDTO;
import com.bridgelabz.bookstorebackend.dto.ResponseDTO;
import com.bridgelabz.bookstorebackend.model.Cart;

import java.util.Optional;

public interface ICartService {

    ResponseDTO getCartDetails();


    Optional<Cart> getCartDetailsById(Integer cartId);

    Cart insertItems(CartDTO cartdto);

    Optional<Cart> deleteCartItemById(Integer cartId);

    Optional<Cart> deleteCartItemByIdAndQuantity(Integer cartId, Integer Quantity);

    Cart updateRecordById(Integer cartId, CartDTO cartDTO);

    Cart updateQuantity(Integer id, Integer quantity);
}

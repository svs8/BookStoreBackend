package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.CartDTO;
import com.bridgelabz.bookstorebackend.dto.ResponseDTO;
import com.bridgelabz.bookstorebackend.model.Book;
import com.bridgelabz.bookstorebackend.model.Cart;
import com.bridgelabz.bookstorebackend.model.UserRegistration;
import com.bridgelabz.bookstorebackend.repository.BookStoreCartRepository;
import com.bridgelabz.bookstorebackend.repository.BookStoreRepository;
import com.bridgelabz.bookstorebackend.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService implements ICartService {

    @Autowired
    BookStoreRepository bookStoreRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    BookStoreCartRepository bookStoreCartRepository;

    @Override
    public Cart insertItems(CartDTO cartdto) {
        Optional<Book> book = bookStoreRepository.findById(cartdto.getBookId());
        Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(cartdto.getUserId());
        if (book.isPresent() && userRegistration.isPresent()) {
            Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get());
            if (cartdto.getQuantity() <= newCart.getQuantity() && cartdto.getQuantity()>0  ) {
                book.get().setQuantity((book.get().getQuantity()) - (cartdto.getQuantity()));
                bookStoreRepository.save(book.get());
                bookStoreCartRepository.save(newCart);
                return newCart;
            }
            return null;
        } else {
           return null;
        }
    }


    @Override
    public ResponseDTO getCartDetails() {
        List<Cart> getCartDetails=bookStoreCartRepository.findAll();
        ResponseDTO dto= new ResponseDTO();
        if(getCartDetails.isEmpty()){
            String   message=" the cart is empty!!!";
            dto.setMessage(message);
            dto.setData(0);
            return dto;
        }
        else {
            dto.setMessage("the list of cart items is sucussfully retrived");
            dto.setData(getCartDetails);
            return dto;
        }
    }



    @Override
    public Optional<Cart> getCartDetailsById(Integer cartId) {
        Optional<Cart> getCartData=bookStoreCartRepository.findById(cartId);
        if (getCartData.isPresent()){
            return getCartData;
        }
        else {
            return Optional.empty(); //Didn't find any record for this particular cartId
        }
    }

    @Override
    public Optional<Cart> deleteCartItemById(Integer cartId) {
        Optional<Cart> deleteData=bookStoreCartRepository.findById(cartId);
        if (deleteData.isPresent()){
            bookStoreCartRepository.deleteById(cartId);
            return deleteData;
        }
        else {
            return Optional.empty();// Did not get any cart for specific cart id
        }

    }

    @Override
    public Optional<Cart> deleteCartItemByIdAndQuantity(Integer cartId,Integer quantity) {
        Optional<Cart> deleteData=bookStoreCartRepository.findById(cartId);
        if (deleteData.isPresent()){
            Optional<Book>  book = bookStoreRepository.findById(deleteData.get().getBook().getBookId());
            System.out.println("book.get().getQuantity() + quantity"+(book.get().getQuantity() + quantity));
            book.get().setQuantity(book.get().getQuantity() + quantity);
            bookStoreRepository.save(book.get());
            bookStoreCartRepository.deleteById(cartId);
            return deleteData;
        }
        else {
            return Optional.empty();// Did not get any cart for specific cart id
        }

    }

    @Override
    public Cart updateRecordById(Integer cartId, CartDTO cartDTO) {
        Optional<Cart> cart = bookStoreCartRepository.findById(cartId);
        Optional<Book> book = bookStoreRepository.findById(cartDTO.getBookId());
        Optional<UserRegistration> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if (cart.isPresent()) {
            if (book.isPresent() && user.isPresent()) {
                Cart newCart = new Cart(cartId, cartDTO.getQuantity(), book.get(), user.get());
                if (cartDTO.getQuantity() <= newCart.getQuantity()) {
                    book.get().setQuantity((book.get().getQuantity()) - (cartDTO.getQuantity()));
                    bookStoreRepository.save(book.get());
                    bookStoreCartRepository.save(newCart);
                    return newCart;
                }
                return null;// when quantity more
            }
            return null;//book or user is not present
        }
        return null;// cart is not there
    }


    @Override
    public Cart updateQuantity(Integer id, Integer quantity) {
        Optional<Cart> cart = bookStoreCartRepository.findById(id);
        if (cart.isPresent()) {//Checking if cart is present
            Optional<Book> book = bookStoreRepository.findById(cart.get().getBook().getBookId());
            if ((book.get().getQuantity()) < 0) {//Making sure the book quantity in the home is not going les than 0
                book.get().setQuantity(0);
                bookStoreRepository.save(book.get());
                return null;
            }
            if ((book.get().getQuantity()) >= 0) {
                book.get().setQuantity(book.get().getQuantity() - (quantity - cart.get().getQuantity()));
                cart.get().setQuantity(quantity);
                bookStoreCartRepository.save(cart.get());
                bookStoreRepository.save(book.get());
                return cart.get();
            } else {
                return null;//No sufficient quantity
            }
        } else {
            return null;//cart dosent exist
        }
    }
}

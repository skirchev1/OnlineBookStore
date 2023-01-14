package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.domain.models.view.BookViewModel;
import com.svilen.onlinebookstore.domain.models.view.ShoppingCart;
import com.svilen.onlinebookstore.error.BookNotFoundException;
import com.svilen.onlinebookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-book")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, int quantity, HttpSession httpSession) throws BookNotFoundException {

        this.initCart(httpSession);

        BookViewModel bookViewModel = this.modelMapper
                .map(this.bookService.findBookById(id), BookViewModel.class);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBookViewModel(bookViewModel);
        shoppingCart.setQuantity(quantity);

        this.addItemToCart(shoppingCart, httpSession);

        return redirect("/home");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession httpSession) {
        this.initCart(httpSession);
        modelAndView.addObject("total", this.calcTotal(httpSession));

        return view("/cart-details", modelAndView);
    }

    @DeleteMapping("/remove-book")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeFromCartConfirm(String id, HttpSession httpSession) {
        this.removeItemFromCart(id, httpSession);

        return redirect("/cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(HttpSession httpSession) {

        httpSession.setAttribute("shopping-cart", null);
        return redirect("/cart/details");
    }



    private void initCart(HttpSession httpSession) {
        if (httpSession.getAttribute("shopping-cart") == null) {
            httpSession.setAttribute("shopping-cart", new ArrayList<>());
        }
    }

    private void addItemToCart(ShoppingCart item, HttpSession httpSession) {
        for (ShoppingCart shoppingCart : (List<ShoppingCart>) httpSession.getAttribute("shopping-cart")) {
            if (shoppingCart.getBookViewModel().getId()
                    .equals(item.getBookViewModel().getId())) {

                shoppingCart.setQuantity(shoppingCart.getQuantity() + item.getQuantity());
                return;
            }
        }

        ((List<ShoppingCart>) httpSession.getAttribute("shopping-cart"))
                .add(item);

    }

    private BigDecimal calcTotal(HttpSession httpSession) {
        List<ShoppingCart> list = (List<ShoppingCart>) httpSession.getAttribute("shopping-cart");

        return list
                .stream()
                .map(i -> BigDecimal.valueOf(i.getQuantity() * 1.0).multiply(i.getBookViewModel().getPrice()).setScale(2, RoundingMode.CEILING))
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);

    }

    private void removeItemFromCart(String id, HttpSession httpSession) {
        var list = ((List<ShoppingCart>) httpSession.getAttribute("shopping-cart"))
                .stream()
                .filter(b -> !b.getBookViewModel().getId().equals(id))
                .collect(Collectors.toList());

        httpSession.setAttribute("shopping-cart", list);
    }
}

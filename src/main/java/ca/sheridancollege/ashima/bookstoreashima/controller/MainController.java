package ca.sheridancollege.ashima.bookstoreashima.controller;

import ca.sheridancollege.ashima.bookstoreashima.beans.Book;
import ca.sheridancollege.ashima.bookstoreashima.beans.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    private final HashMap<String, Book> bookDatabase = new HashMap<>(); // Placeholder book database


    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<String, Order>());
        }
        model.addAttribute("books", new ArrayList<Book>(bookDatabase.values()));
        return "index";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam String isbn, @RequestParam int quantity, HttpSession session) {
        HashMap<String, Order> cart = (HashMap<String, Order>) session.getAttribute("cart");
        if (cart.containsKey(isbn)) {
            cart.get(isbn).setQuantity(quantity);
            session.setAttribute("cart", cart);
            return "redirect:/";
        }
        Order order = new Order();
        order.setQuantity(quantity);
        Book book = bookDatabase.get(isbn);
        if (book != null) {
            order.setBook(book);
            cart.put(isbn, order);
            session.setAttribute("cart", cart);
        }
        return "redirect:/";
    }

    @GetMapping("/viewCart")
    public String getCart(Model model, HttpSession session) {
        HashMap<String, Order> cart = (HashMap<String, Order>) session.getAttribute("cart");
        model.addAttribute("orders", new ArrayList<Order>(cart.values()));
        return "viewCart";
    }

    @GetMapping("/placeOrder")
    public String placeOrder(HttpSession session) {
        session.setAttribute("cart", null);
        return "orderPlaced";
    }

    @PostMapping("/deleteOrder/{isbn}")
    public String deleteOrder(@PathVariable String isbn, HttpSession session) {
        HashMap<String, Order> cart = (HashMap<String, Order>) session.getAttribute("cart");
        cart.remove(isbn);
        return "redirect:/viewCart";
    }

    @PostMapping("/updateOrder")
    public String updateOrder(@RequestParam String isbn, @RequestParam int quantity, HttpSession session) {
        HashMap<String, Order> cart = (HashMap<String, Order>) session.getAttribute("cart");
        cart.get(isbn).setQuantity(quantity);
        session.setAttribute("cart", cart);
        return "redirect:/viewCart";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        bookDatabase.put(book.getIsbn(), book);
        return "redirect:/";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book) {
        bookDatabase.put(book.getIsbn(), book);
        return "redirect:/";
    }

    @PostMapping("/deleteBook/{isbn}")
    public String deleteBook(@PathVariable String isbn) {
        bookDatabase.remove(isbn);
        return "redirect:/";
    }

    @GetMapping("/addBook")
    public String getAddBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @GetMapping("/updateBook/{isbn}")
    public String viewBook(@PathVariable String isbn, Model model) {
        Book book = bookDatabase.get(isbn);
        if (book != null) {
            model.addAttribute("book", book);
            return "updateBook";
        }
        return "redirect:/";
    }

    @GetMapping("/deleteBook/{isbn}")
    public String deleteBookPage(@PathVariable String isbn, Model model) {
        model.addAttribute("isbn", isbn);
        return "deleteBook";
    }
}

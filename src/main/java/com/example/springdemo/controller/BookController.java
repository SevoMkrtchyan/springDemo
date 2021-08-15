package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/books")
    public String getBooks(ModelMap modelMap){
        List<Book> books = bookRepository.findAll();
        List<User> users = userRepository.findAll();
        modelMap.addAttribute("books",books);
        modelMap.addAttribute("users",users);
       modelMap.addAttribute("book",new Book());
       return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            return "redirect:/";
        }
        modelMap.addAttribute("book", book.get());
        return "singleBook";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book")Book book){
        Date date = new Date(System.currentTimeMillis());
        if (book != null){
            Optional<User> user = userRepository.findById(book.getUser().getId());
            book.setCreatedDate(date);
            book.setUser(user.get());
            bookRepository.save(book);
        return "redirect:/books";
        }
        return "redirect:/books";
    }


}

package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.Hashtag;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.HashtagRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/books")
    public String getBooks(ModelMap modelMap){
        List<Book> books = bookRepository.findAll();
        List<User> users = userRepository.findAll();
        modelMap.addAttribute("books",books);
        modelMap.addAttribute("users",users);
       modelMap.addAttribute("book",new Book());
       modelMap.addAttribute("hashtags",hashtagRepository.findAll());
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
    public String saveBook(@ModelAttribute("book")Book book,
                           @RequestParam ("picture")MultipartFile multipartFile,
                           @RequestParam("hashtags")List<Hashtag> hashtags) throws IOException {
        if (book != null){
            Optional<User> user = userRepository.findById(book.getUser().getId());
            String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
            book.setHashtags(hashtags);
            book.setCreatedDate(new Date());
            book.setPicUrl(picUrl);
            book.setUser(user.get());
            bookRepository.save(book);
        return "redirect:/books";
        }
        return "redirect:/books";
    }


}

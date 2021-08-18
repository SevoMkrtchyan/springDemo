package com.example.springdemo.repository;


import com.example.springdemo.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Integer> {


}

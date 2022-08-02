package com.example.webcomic.project.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webcomic.project.demo.model.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long>{

}
package com.example.webcomic.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webcomic.project.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}

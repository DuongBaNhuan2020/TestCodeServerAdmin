package com.example.webcomic.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webcomic.project.demo.model.Comic;

public interface ComicRepository extends JpaRepository<Comic, Long>{

}

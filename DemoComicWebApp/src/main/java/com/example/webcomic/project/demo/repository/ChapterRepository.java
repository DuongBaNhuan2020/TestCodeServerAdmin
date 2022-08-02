package com.example.webcomic.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webcomic.project.demo.model.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long>{

}
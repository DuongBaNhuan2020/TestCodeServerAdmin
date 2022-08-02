package com.example.webcomic.project.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
	
import com.example.webcomic.project.demo.exeption.ResourceNotFoundException;
import com.example.webcomic.project.demo.model.Chapter;
import com.example.webcomic.project.demo.model.Comic;
import com.example.webcomic.project.demo.model.Comment;
import com.example.webcomic.project.demo.model.Likes;
import com.example.webcomic.project.demo.repository.ChapterRepository;
import com.example.webcomic.project.demo.repository.ComicRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test/all")
public class ChapterController {
	@Autowired
	private ChapterRepository chapterRepository;
	@Autowired
	private ComicRepository comicRepository;
	
	@GetMapping("/chapters")
	public List<Chapter> getAllComics() {
		return chapterRepository.findAll();
	}
	@GetMapping("/chapters/{id}")
	public ResponseEntity<Chapter> getChaperById(@PathVariable(value = "id") Long chapterId)
		throws ResourceNotFoundException {
			Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + chapterId));
			return ResponseEntity.ok().body(chapter);
	}
	@DeleteMapping("/chapters/{id}")
	public Map<String, Boolean> deleteChapter(@PathVariable(value = "id") Long chapterId)
			throws ResourceNotFoundException {
		Chapter chapter = chapterRepository.findById(chapterId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + chapterId));

		chapterRepository.delete(chapter);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}

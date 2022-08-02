package com.example.webcomic.project.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webcomic.project.demo.exeption.ResourceNotFoundException;
import com.example.webcomic.project.demo.model.Chapter;
import com.example.webcomic.project.demo.model.Comic;
import com.example.webcomic.project.demo.model.Likes;
import com.example.webcomic.project.demo.repository.LikesRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test/all")
public class LikesController {
	@Autowired
	private LikesRepository likesRepository;
	
	@GetMapping("/likes")
	public List<Likes> getAllComics() {
		return likesRepository.findAll();
	}
	@GetMapping("/likes/{id}")
	public ResponseEntity<Likes> getLikseById(@PathVariable(value = "id") Long likesId)
		throws ResourceNotFoundException {
			Likes like = likesRepository.findById(likesId).orElseThrow(() -> new ResourceNotFoundException("Likes not found for this id :: " + likesId));
			return ResponseEntity.ok().body(like);
	}
	@DeleteMapping("/likes/{id}")
	public Map<String, Boolean> deleteLikes(@PathVariable(value = "id") Long likesId)
			throws ResourceNotFoundException {
		Likes likes = likesRepository.findById(likesId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + likesId));

		likesRepository.delete(likes);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

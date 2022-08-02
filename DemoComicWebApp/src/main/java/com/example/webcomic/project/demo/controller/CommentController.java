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
import com.example.webcomic.project.demo.model.Comment;
import com.example.webcomic.project.demo.repository.CommentRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test/all")
public class CommentController {
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("/comments")
	public List<Comment> getAllComics() {
		return commentRepository.findAll();
	}
	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long commentId)
		throws ResourceNotFoundException {
			Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + commentId));
			return ResponseEntity.ok().body(comment);
	}
	@DeleteMapping("/comments/{id}")
	public Map<String, Boolean> deleteComment(@PathVariable(value = "id") Long commentId)
			throws ResourceNotFoundException {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + commentId));

		commentRepository.delete(comment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

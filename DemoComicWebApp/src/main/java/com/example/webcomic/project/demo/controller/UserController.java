package com.example.webcomic.project.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.webcomic.project.demo.exeption.ResourceNotFoundException;
import com.example.webcomic.project.demo.model.Chapter;
import com.example.webcomic.project.demo.model.Comic;
import com.example.webcomic.project.demo.model.Comment;
import com.example.webcomic.project.demo.model.Likes;
import com.example.webcomic.project.demo.model.Role;
import com.example.webcomic.project.demo.model.User;
import com.example.webcomic.project.demo.repository.RoleRepository;
import com.example.webcomic.project.demo.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test/all")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
//	@Autowired
//	private PasswordEncoder encoder;
//	
//	@PostMapping("register")
//	public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody Register registerRequest){
//		if(userRepository.existsByEmail(registerRequest.getEmail())) {
//			return new ResponseEntity<>(new ResponseMessage("Email is already taken!"), HttpStatus.BAD_REQUEST);
//		}
//		// Create user account
//		User user = new User(registerRequest.getEmail(), registerRequest.getName(), 
//				encoder.encode(registerRequest.getPassword()));
//		Set<String> rolesInRequest = registerRequest.getRole();
//		Set<Role> roles = new HashSet<>();
//		
//		rolesInRequest.forEach(role -> {
//			switch(role) {
//			case "admin":
//				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//				roles.add(adminRole);
//				break;
//			default:
//				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//				roles.add(userRole);
//				break;
//			}
//		});
//		user.setRole_id(roles);
//		userRepository.save(user);
//		return new ResponseEntity<>(new ResponseMessage("User register successfully!"), HttpStatus.OK);
//	}
	
	@GetMapping("/users")
	public List<User> getAllComics() {
		return userRepository.findAll();
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
		throws ResourceNotFoundException {
			User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
			return ResponseEntity.ok().body(user);
	}
	@PostMapping("/users")
	public User createUser( @Valid @RequestBody User usersDetails) {
		Calendar a=Calendar.getInstance();
		List<Comment> comments=new ArrayList<Comment>();
		List<Likes> likes=new ArrayList<Likes>();
		
		usersDetails.setDate_created(a);
		usersDetails.setListComment(comments);
		usersDetails.setListLikes(likes);
		System.out.println("co vao tao user");
		return userRepository.save(usersDetails);
	}
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User usersDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + userId));
		
		user.setAvatar(usersDetails.getAvatar());
		user.setName(usersDetails.getName());
		user.setEmail(usersDetails.getEmail());
		user.setPassword(usersDetails.getPassword());
		user.setDate_created(usersDetails.getDate_created());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	//Comment
	@GetMapping("users/{id}/comments")
	public List<Comment> getListCommentByUserId(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return user.getListComment();
	}
	//Like
	@GetMapping("users/{id}/likes")
	public List<Likes> getListLikeByUserId(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return user.getListLikes();
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> loginUser(@RequestBody User userData){
//		User user = userRepository.findByEmail(userData.getEmail());
//		if(user.getPassword().equals(userData.getPassword())) {
//			return ResponseEntity.ok(user);
//		}
//		return (ResponseEntity<?>) ResponseEntity.internalServerError();
//	}
}

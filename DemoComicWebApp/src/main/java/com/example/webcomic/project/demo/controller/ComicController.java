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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.webcomic.project.demo.exeption.ResourceNotFoundException;
import com.example.webcomic.project.demo.model.Chapter;
import com.example.webcomic.project.demo.model.Comic;
import com.example.webcomic.project.demo.model.Comment;
import com.example.webcomic.project.demo.model.Likes;
import com.example.webcomic.project.demo.model.User;
import com.example.webcomic.project.demo.repository.ChapterRepository;
import com.example.webcomic.project.demo.repository.ComicRepository;
import com.example.webcomic.project.demo.repository.UserRepository;
@RestController
@RequestMapping("/api/test/all")
public class ComicController implements WebMvcConfigurer{
	@Autowired
	private ComicRepository comicRepository;
	@Autowired
	private ChapterRepository chapterRepository;
	@Autowired
	private UserRepository userRepository;
	//This
	@GetMapping("/comics")
	public List<Comic> getAllComics() {
		return comicRepository.findAll();
	}
	@GetMapping("/comics/{id}")
	public ResponseEntity<Comic> getComicById(@PathVariable(value = "id") Long comicId)
		throws ResourceNotFoundException {
			Comic comic = comicRepository.findById(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
			return ResponseEntity.ok().body(comic);
	}
	@PostMapping("/comics")
	public Comic createComic(@Valid @RequestBody Comic comic) {
//		Calendar a=Calendar.getInstance();
//		List<Chapter> chapters=new ArrayList<Chapter>();
//		List<Comment> comments=new ArrayList<Comment>();
//		List<Likes> likes=new ArrayList<Likes>();
//		Comic comic=new Comic(4, "comic4", "Jenny", "Vietnam", "Comic", a, "this is link cover image", "this discription", chapters, comments, likes,true,true,10);
//		Chapter chapter1=new Chapter(5,comic, "chapter5", a, "this is chapter5 content",comments );
//		comic.addChapter(chapter1);
		return comicRepository.save(comic);	
	}
	@DeleteMapping("/comics/{id}")
	public Map<String, Boolean> deleteComic(@PathVariable(value = "id") Long comicId)
			throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		System.out.println("co vao delete");
		comicRepository.delete(comic);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@PutMapping("/comics/{id}")
	public ResponseEntity<Comic> updateComic(@PathVariable(value = "id") Long comicId, @Valid @RequestBody Comic comicDetails) throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		
		comic.setCover_image(comicDetails.getCover_image());
		comic.setName(comicDetails.getName());
		comic.setAuthor(comicDetails.getAuthor());
		comic.setNation(comicDetails.getNation());
		comic.setChapter_number(comicDetails.getChapter_number());
		System.out.println("gia tri cua datecreated"+comicDetails.getDate_created());
		comic.setDate_created(comicDetails.getDate_created());
		comic.setCategory(comicDetails.getCategory());
		comic.setDisplay(comicDetails.isDisplay());
		comic.setFull(comicDetails.isFull());
		comic.setDescription(comicDetails.getDescription());
		
		final Comic updatedComic = comicRepository.save(comic);
		return ResponseEntity.ok(updatedComic);
	}
	//Chapter
	@GetMapping("/comics/{id}/chapters")
	public List<Chapter> getAllChaptersByIdComic(@PathVariable(value = "id") Long comicId) throws ResourceNotFoundException{
		Comic comic = comicRepository.findById(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		return comic.getListChapter();
	}
	@PutMapping("/comics/chapters/{id}/{chapter_id}")
	public ResponseEntity<Comic> updateChapter(@PathVariable(value = "id") Long comicId,@PathVariable(value = "chapter_id") Long chaper_Id, @Valid @RequestBody Chapter chapterDetails) throws ResourceNotFoundException {
		System.out.println("Gia tri cua comic id la: "+comicId);
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		chapterDetails.setComic(comic);
		comic.updateChapter(chaper_Id, chapterDetails);		
		
		System.out.println("gia tri chapter cua commit"+comic.getListChapter().get(0).getContent());
		final Comic updatedComic = comicRepository.save(comic);
		return ResponseEntity.ok(updatedComic);
	}
	@PutMapping("/comics/chapters/{id}")
	public ResponseEntity<Comic> addChapter(@PathVariable(value = "id") Long comicId,@Valid @RequestBody Chapter chapterDetails) throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		
		chapterDetails.setComic(comic);
		comic.addChapter(chapterDetails);		
		
		System.out.println("gia tri chapter cua commit"+comic.getListChapter().get(0).getContent());
		final Comic addedChapterComic = comicRepository.save(comic);
		return ResponseEntity.ok(addedChapterComic);
	}
	
	//Comment
	@GetMapping("/comics/comments/{id}")
	public List<Comment> getAllCommentByComicId(@PathVariable(value = "id") Long comicId) throws ResourceNotFoundException{
		Comic comic = comicRepository.findById(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		return comic.getListComment();
	}
	@GetMapping("/comics/comments/{id}/{chapter_id}")
	public List<Comment> getAllCommentByComicIdChapterId(@PathVariable(value = "id") Long comicId,@PathVariable(value = "chapter_id") Long chaper_Id) throws ResourceNotFoundException{
		Comic comic = comicRepository.findById(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		Chapter chapter=comic.findChapterById(chaper_Id);
		return chapter.getListComment();
	}
	@PutMapping("/comics/comments/{id}/{chapter_id}/{user_id}")
	public ResponseEntity<Comic> addComment(@PathVariable(value = "id") Long comicId,@PathVariable(value = "chapter_id") Long chaper_Id,@PathVariable(value = "user_id") Long user_Id, @Valid @RequestBody Comment commentDetails) throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		Chapter chapter = chapterRepository.findById(chaper_Id)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + chaper_Id));
		User user = userRepository.findById(user_Id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + user_Id));
		
		Calendar a=Calendar.getInstance();
		
		commentDetails.setComic(comic);
		commentDetails.setChapter(chapter);
		commentDetails.setUser(user);
		commentDetails.setDate_created(a);
		
		comic.addComment(commentDetails);		
		
		final Comic addedChapterComic = comicRepository.save(comic);
		return ResponseEntity.ok(addedChapterComic);
	}
	@PutMapping("/comics/comments/{id}/{chapter_id}/{comment_id}/{user_id}")
	public ResponseEntity<Comic> updateComment(@PathVariable(value = "id") Long comicId,@PathVariable(value = "chapter_id") Long chaper_Id,@PathVariable(value = "comment_id") Long comment_Id,@PathVariable(value = "user_id") Long user_Id, @Valid @RequestBody Comment commentDetails) throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		Chapter chapter = chapterRepository.findById(chaper_Id)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter not found for this id :: " + chaper_Id));
		User user = userRepository.findById(user_Id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + user_Id));
		
		Calendar a=Calendar.getInstance();
		
		commentDetails.setComic(comic);
		commentDetails.setChapter(chapter);
		commentDetails.setUser(user);
		commentDetails.setDate_created(a);
		
		comic.updateComment(comment_Id, commentDetails);		
		
		final Comic addedChapterComic = comicRepository.save(comic);
		return ResponseEntity.ok(addedChapterComic);
	}
	
	//Likes
	@GetMapping("/comics/likes/{id}")
	public List<Likes> getAllLikeByComicId(@PathVariable(value = "id") Long comicId) throws ResourceNotFoundException{
		Comic comic = comicRepository.findById(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		return comic.getListLikes();
	}
	@PutMapping("/comics/likes/{id}/{user_id}")
	public ResponseEntity<Comic> addLikes(@PathVariable(value = "id") Long comicId,@PathVariable(value = "user_id") Long user_Id, @Valid @RequestBody Likes likeDetails) throws ResourceNotFoundException {
		Comic comic = comicRepository.findById(comicId)
				.orElseThrow(() -> new ResourceNotFoundException("Comic not found for this id :: " + comicId));
		User user = userRepository.findById(user_Id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + user_Id));
		
		Calendar a=Calendar.getInstance();
		likeDetails.setComic(comic);
		likeDetails.setUser(user);
		likeDetails.setDate_created(a);
		
		comic.addLikes(likeDetails);		
		
		final Comic addedChapterComic = comicRepository.save(comic);
		return ResponseEntity.ok(addedChapterComic);
	}
	
	//Allow method
	@Override
     public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/api/v1/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");


     }
}
	

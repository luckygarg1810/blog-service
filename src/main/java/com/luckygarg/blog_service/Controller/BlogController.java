package com.luckygarg.blog_service.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckygarg.blog_service.Entity.Blog;
import com.luckygarg.blog_service.Service.BlogService;
import com.luckygarg.blog_service.Service.SummarizeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blogs")
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private SummarizeService summarize;

	@PostMapping("/create")
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog, HttpServletRequest request) {
		Blog createdBlog = blogService.addBlog(blog, request);
		return ResponseEntity.ok(createdBlog);
	}

	@GetMapping
	public Page<Blog> getAllBlogs(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, HttpServletRequest request) {
		return blogService.getAllBlogs(page, size, request);
	}

	@GetMapping("/{id}")
	public Optional<Blog> getBlogById(@PathVariable Long id) {
		return blogService.getBlogById(id);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog, HttpServletRequest request) {
		Blog updatedBlog = blogService.updateBlog(id, blog, request);
		return ResponseEntity.ok(updatedBlog);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBlog(@PathVariable Long id, HttpServletRequest request) {
		blogService.deleteBlog(id, request);
		return ResponseEntity.ok("Blog Deleted Successfully");
	}
	
	@GetMapping("/{id}/summary")
	public ResponseEntity<Map<String, String>> summarizeBlog(@PathVariable Long id){
		Optional<Blog> blog = blogService.getBlogById(id);
		
		if(blog.isPresent()) {
			return ResponseEntity.ok(summarize.generateSummary(blog.get().getContent()));
		}else {
			  return ResponseEntity.badRequest().body(Map.of("error", "Blog not found."));
        }
	}

}

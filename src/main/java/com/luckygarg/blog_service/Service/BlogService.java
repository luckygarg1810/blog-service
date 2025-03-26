package com.luckygarg.blog_service.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.luckygarg.blog_service.Entity.Blog;
import com.luckygarg.blog_service.Entity.User;
import com.luckygarg.blog_service.Repository.BlogRepository;
import com.luckygarg.blog_service.Repository.UserRepository;
import com.luckygarg.blog_service.Security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserRepository userRepository;

	
	@CacheEvict(value = {"blogs", "blogsContent"}, allEntries = true)
	public Blog addBlog(Blog blog,  HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.getUsernameFromToken(token);
        
        User author = userRepository.findByUsername(username)
        		 .orElseThrow(() -> new RuntimeException("User not found"));
        blog.setAuthor(author.getFullName());
		return blogRepository.save(blog);
	}

	@Cacheable(value = "blogsByUser", key = "#request.getHeader('Authorization')")
	public Page<Blog> getAllBlogs(int page, int size,HttpServletRequest request) {
		 String token = request.getHeader("Authorization").replace("Bearer ", "");
	        String username = jwtTokenProvider.getUsernameFromToken(token);
	        
	        User loggedInUser = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        System.out.println("Fetching blogs from database...");
		return blogRepository.findByAuthor(loggedInUser.getFullName(), PageRequest.of(page, size));
	}

	@Cacheable(value = "blogs", key = "#id")
	public Optional<Blog> getBlogById(Long id) {
		   System.out.println("Fetching blog from database...");
		return blogRepository.findById(id);
	}

	 @CacheEvict(value = {"blogs", "blogsContent", "blogsByUser"}, key = "#id")
	public void deleteBlog(Long id, HttpServletRequest request) {
		 String token = request.getHeader("Authorization").replace("Bearer ", "");
	        String username = jwtTokenProvider.getUsernameFromToken(token);
	        User loggedInUser = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        Blog existingBlog = blogRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Blog not found"));      
	        
	        if (!existingBlog.getAuthor().equals(loggedInUser.getFullName())) {
	            throw new RuntimeException("Unauthorized: You can only delete your own blog");
	        }

		 blogRepository.deleteById(id);
	}
	
	@CacheEvict(value = {"blogs", "blogsContent", "blogsByUser"}, key = "#id")
	public Blog updateBlog(Long id, Blog updatedBlog, HttpServletRequest request) {
		 String token = request.getHeader("Authorization").replace("Bearer ", "");
	        String username = jwtTokenProvider.getUsernameFromToken(token);
	        User loggedInUser = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        Blog existingBlog = blogRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Blog not found"));      
	        
	        if (!existingBlog.getAuthor().equals(loggedInUser.getFullName())) {
	            throw new RuntimeException("Unauthorized: You can only update your own blog");
	        }

			existingBlog.setTitle(updatedBlog.getTitle());
			existingBlog.setContent(updatedBlog.getContent());
			existingBlog.setAuthor(loggedInUser.getFullName());
			return blogRepository.save(existingBlog);
	}
}

package com.luckygarg.blog_service.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckygarg.blog_service.Entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
	Page<Blog> findByAuthor(String author, Pageable pageable);
}

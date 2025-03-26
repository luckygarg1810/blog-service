package com.luckygarg.blog_service.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class User {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String fullName;
	    
	    @Column(unique = true, nullable = false)
	    private String username;

	    @Column(nullable = false)
	    private String password;
	    
	    @Column(nullable = false, unique = true)
	    private String email;
	    
	    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Blog> blogs;
	    
	    public User() {}

	    public User(String fullName ,String email,String username, String password) {
	        this.username = username;
	        this.password = password;
	        this.email = email;
	        this.fullName = fullName;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public List<Blog> getBlogs() {
			return blogs;
		}

		public void setBlogs(List<Blog> blogs) {
			this.blogs = blogs;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
	    
	    

}

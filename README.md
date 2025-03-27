# _Blog Service_


## 📌 Project Overview

* ##### This is a Spring Boot-based Blog Service that provides user authentication, blog creation, updation, deletion, and also include a simple AI powered Summarizer . The backend is secured with JWT authentication, and Redis is used for caching. The application follows a layered architecture, making it scalable and maintainable.

## 🛠 Tech Stack

- ##### Backend:  Java, Spring Boot

- ##### Security: JWT Authentication, Spring Security

- ##### Database: MySQL

- ##### AI Integration: Gemini API Key

- ##### Caching: Redis Caching

- ##### Build Tool: Maven

- ##### Deployment: Docker,  AWS Elastic Beanstalk

## Blog Service Architecture

#### This flowchart represents the architecture of the blog service.

![Blog Service Architecture](https://github.com/luckygarg1810/blog-service/blob/master/Architecture.png)


## 🚀 Setup Instructions

### 1️⃣ Prerequisites

#### Ensure you have the following installed:

- ##### Java 17+

- ##### Maven

- ##### MySQL Dockerised Container (Configured in application.properties)

- ##### Redis (Running on default port 6379)

- ##### Docker

## 2️⃣ Installation & Running the Project

- #### Clone the repository:

```bash
git clone https://github.com/luckygarg1810/blog-service
cd blog-service
```

- #### Configure the database in src/main/resources/application.properties:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

# Redis Configuration
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379

```

- #### Run the application:

```bash
mvn clean install
mvn spring-boot:run
```

## 🏛 Architecture & Application Flow

### 1️⃣ Layered Architecture

- ##### The application follows a standard layered architecture:

- ##### Controller Layer: Handles HTTP requests and responses.

- ##### Service Layer: Contains business logic.

- ##### Repository Layer: Communicates with the database.

- ##### Security Layer: Manages authentication and authorization.

- ##### Configuration Layer: Handles external configurations such as Redis.

### 2️⃣ Application Flow

#### 1. User Authentication:

- ##### A new user registers via /api/auth/register.

- ##### Logs in via /api/auth/login and receives a JWT token.

- ##### The token is used for accessing protected resources.

#### 2. Blog Management:

- ##### Authenticated users can create, update, fetch, and delete blog posts.

- ##### Blog data is stored in a MySQL database.

#### 3. Caching with Redis:

- ##### Frequently accessed blog data is cached for better performance.

#### 4. Summarization Service:

- ##### Summarizes blog content via /blogs/{id}/summary.

## 🔑 Authentication & Security

- ##### This service uses JWT authentication. To access protected endpoints, you must:

- ##### Register a user.

- ##### Log in to receive a JWT token.

- ##### Pass the token in the Authorization header as Bearer <token> for authenticated requests.

## 🔥 API Endpoints

### 📝 Authentication

#### 1️⃣ Register a new user

##### Endpoint: 
```http
POST /api/auth/register
 ```

```http
{
  "username": "newuser",
  "email": "user@example.com",
  "password": "securepassword",
  "fullName" : "New User"
}
```

#### 2️⃣ Login & Get Token

##### Endpoint:
```http
POST /api/auth/login
```

```http
{
  "username": "newuser",
  "password": "securepassword"
}

```

##### Response:

```http
{
  "token": "jwt-token-string",
  "userId": 1
}
```

### 📝 Blog Management

#### 3️⃣ Create a new blog post

##### Endpoint: 
```http
POST /blogs/create
```
##### Requires: JWT Token

```http
{
  "title": "My First Blog",
  "content": "This is the content of the blog post."
}
```

#### 4️⃣ Fetch all blog posts

##### Endpoint: 
```http
GET /blogs?page=0&size=5
```

#### 5️⃣ Fetch a single blog post

##### Endpoint: 
```http
GET /blogs/{id}
```

#### 6️⃣ Update a blog post

##### Endpoint: 
```http
PUT /blogs/update/{id}
```
##### Requires: JWT Token

```http
{
  "title": "Updated Title",
  "content": "Updated content."
}
```

#### 7️⃣ Delete a blog post

##### Endpoint: 
```http
DELETE /blogs/{id}
```
##### Requires: JWT Token

### 📝 Summarization Service

- ##### The SummarizeService provides automatic summarization of blog posts.

#### 8️⃣ Summarize a blog post

##### Endpoint: 
```http
GET /blogs/{id}/summary
```

##### Requires: JWT Token

##### Response:
```http
{
  "summary": "This is the summarized content of the blog post."
}
```

## 🤝 Contributors

#### Lucky Garg - Developer 

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/lucky-garg/)

## 🚀 About Me

#### Aspiring Full Stack Developer | B.Tech CSE '26

##### I’m passionate about building impactful Projects using Java, Spring Boot, React.js, and has a strong foundation in Data Structures & Algorithms. Currently diving deeper into Spring Boot, I’m also focused on learning AWS Deployment, AI integration, Docker, and cloud Technologies. Always eager to learn and grow, I strive to bring value through innovation and continuous improvement.


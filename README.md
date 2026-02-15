# Task Sphere - Project Management Application

A production-ready REST API for project and task management built with Spring Boot. This application allows users to create projects, manage tasks, assign them to team members, and collaborate through comments.

## 🚀 Features

### Authentication & Security
- **JWT-based Authentication**: Secure token-based authentication system
- **Password Encryption**: Passwords are encrypted using BCrypt
- **Role-based Access Control**: Ready for role-based permissions (currently all users have USER role)
- **Secure Endpoints**: All endpoints except authentication are protected

### User Management
- User registration with email validation
- User login with JWT token generation
- User profile management (CRUD operations)
- Password validation and encryption

### Project Management
- Create projects with name and description
- Assign project ownership to users
- View all projects owned by a user
- Update project details
- Delete projects (with cascade deletion of tasks)

### Task Management
- Create tasks within projects
- Assign tasks to users
- Set task deadlines
- Track task status (PENDING, IN_PROGRESS, COMPLETED)
- View tasks by project or by assigned user
- Update task details and status
- Delete tasks

### Task Comments
- Add comments to tasks
- View all comments for a task (ordered by creation time)
- Update comment content
- Delete comments

### API Documentation
- **Swagger UI**: Interactive API documentation at `/swagger-ui.html`
- **OpenAPI Specification**: JSON specification at `/v3/api-docs`
- Complete endpoint documentation with request/response examples

### Error Handling
- Centralized exception handling
- Meaningful error messages
- Proper HTTP status codes
- Validation error details

## 📋 Technology Stack

- **Framework**: Spring Boot 3.5.6
- **Java Version**: 17
- **Database**: PostgreSQL 14
- **ORM**: JPA/Hibernate
- **Database Migration**: Flyway
- **Security**: Spring Security with JWT
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Validation**: Jakarta Validation

## 🗄️ Database Schema

### Users Table
- `id` (BIGSERIAL PRIMARY KEY)
- `name` (VARCHAR(100) NOT NULL)
- `email` (VARCHAR(100) UNIQUE NOT NULL)
- `password` (VARCHAR(255) NOT NULL - encrypted)

### Projects Table
- `id` (BIGSERIAL PRIMARY KEY)
- `name` (VARCHAR(255) NOT NULL)
- `description` (TEXT)
- `owner_id` (BIGINT NOT NULL - Foreign Key to Users)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Tasks Table
- `id` (BIGSERIAL PRIMARY KEY)
- `title` (VARCHAR(255) NOT NULL)
- `description` (TEXT)
- `status` (VARCHAR(20) - PENDING, IN_PROGRESS, COMPLETED)
- `deadline` (TIMESTAMP)
- `project_id` (BIGINT NOT NULL - Foreign Key to Projects)
- `user_id` (BIGINT NOT NULL - Foreign Key to Users - assigned user)

### Comments Table
- `id` (BIGSERIAL PRIMARY KEY)
- `content` (VARCHAR(1000) NOT NULL)
- `task_id` (BIGINT NOT NULL - Foreign Key to Tasks)
- `user_id` (BIGINT NOT NULL - Foreign Key to Users - author)
- `created_at` (TIMESTAMP)

## 🔌 API Endpoints

### Authentication Endpoints

#### Register User
```
POST /api/auth/register
Content-Type: application/json

Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```

### User Endpoints (Requires Authentication)

#### Get All Users
```
GET /api/users
Authorization: Bearer {token}
```

#### Get User by ID
```
GET /api/users/{id}
Authorization: Bearer {token}
```

#### Create User
```
POST /api/users
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "password": "password123"
}
```

#### Update User
```
PUT /api/users/{id}
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "password": "newpassword123"  // Optional
}
```

#### Delete User
```
DELETE /api/users/{id}
Authorization: Bearer {token}
```

### Project Endpoints (Requires Authentication)

#### Create Project
```
POST /api/projects
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "name": "Website Redesign",
  "description": "Complete redesign of company website",
  "ownerId": 1
}
```

#### Get Projects by User
```
GET /api/projects/user/{userId}
Authorization: Bearer {token}
```

#### Update Project
```
PUT /api/projects/project/{projectId}
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "id": 1,
  "name": "Updated Project Name",
  "description": "Updated description",
  "ownerId": 1  // Optional
}
```

#### Delete Project
```
DELETE /api/projects/{id}
Authorization: Bearer {token}
```

### Task Endpoints (Requires Authentication)

#### Create Task
```
POST /api/tasks
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "title": "Design Homepage",
  "description": "Create new homepage design",
  "projectId": 1,
  "assignedToId": 2,
  "deadline": "2024-12-31T23:59:59"
}
```

#### Get All Tasks
```
GET /api/tasks
Authorization: Bearer {token}
```

#### Get Task by ID
```
GET /api/tasks/{id}
Authorization: Bearer {token}
```

#### Update Task
```
PUT /api/tasks/{id}
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "title": "Updated Task Title",  // Optional
  "description": "Updated description",  // Optional
  "status": "IN_PROGRESS",  // Optional: PENDING, IN_PROGRESS, COMPLETED
  "deadline": "2024-12-31T23:59:59",  // Optional
  "assignedToId": 3  // Optional
}
```

#### Delete Task
```
DELETE /api/tasks/{id}
Authorization: Bearer {token}
```

#### Get Tasks by Project
```
GET /api/tasks/project/{projectId}
Authorization: Bearer {token}
```

#### Get Tasks by User
```
GET /api/tasks/user/{userId}
Authorization: Bearer {token}
```

### Comment Endpoints (Requires Authentication)

#### Add Comment
```
POST /api/comments
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "content": "This task is progressing well!",
  "taskId": 1,
  "userId": 1
}
```

#### Get Comments by Task
```
GET /api/comments/task/{taskId}
Authorization: Bearer {token}
```

#### Update Comment
```
PUT /api/comments/{id}
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "content": "Updated comment content"
}
```

#### Delete Comment
```
DELETE /api/comments/{id}
Authorization: Bearer {token}
```

## 🔐 Authentication

All endpoints except `/api/auth/**` require authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer {your_jwt_token}
```

JWT tokens expire after 24 hours (86400000 milliseconds).

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (or use Maven Wrapper)
- Docker and Docker Compose (for database)
- PostgreSQL 14 (if not using Docker)

### Installation Steps

1. **Start the Database**
   ```bash
   docker-compose -f src/main/java/com/iamskrai/task_sphere/infra/docker-compose.yaml up -d
   ```

2. **Build the Application**
   ```bash
   ./mvnw.cmd clean install
   ```

3. **Run the Application**
   ```bash
   ./mvnw.cmd spring-boot:run
   ```

4. **Access Swagger UI**
   - Open: http://localhost:8080/swagger-ui.html

### Database Configuration

The application uses PostgreSQL with the following default configuration:
- **Database**: `taskscheduler`
- **Username**: `taskuser`
- **Password**: `taskpass`
- **Port**: `5432`

You can modify these in `src/main/resources/application.properties`.

## 📝 Validation Rules

### User Registration/Update
- Name: Required, 2-100 characters
- Email: Required, valid email format, unique
- Password: Required, minimum 6 characters

### Project Creation/Update
- Name: Required
- Owner ID: Required

### Task Creation/Update
- Title: Required
- Project ID: Required
- Assigned User ID: Required
- Status: Must be one of: PENDING, IN_PROGRESS, COMPLETED

### Comment Creation/Update
- Content: Required, maximum 1000 characters
- Task ID: Required
- User ID: Required

## 🛠️ Error Handling

The application provides structured error responses:

```json
{
  "timestamp": "2024-01-01T12:00:00Z",
  "status": 404,
  "error": "Resource Not Found",
  "message": "User not found with id : 999",
  "path": "/api/users/999"
}
```

### Common HTTP Status Codes
- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `400 Bad Request`: Validation error or bad input
- `401 Unauthorized`: Missing or invalid authentication token
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## 📊 Database Migrations

The application uses Flyway for database migrations. Migration files are located in:
```
src/main/resources/db/migration/
```

Migrations are automatically applied on application startup:
- `V1__Create_users_table.sql`
- `V2__Create_projects_table.sql`
- `V3__Create_tasks_table.sql`
- `V4__Create_comments_table.sql`
- `V5__Add_indexes.sql`

## 🔍 Logging

The application logs at different levels:
- **INFO**: General application information
- **DEBUG**: Detailed debugging information (for `com.iamskrai.task_sphere` package)

Logs are formatted with timestamps and can be viewed in the console.

## 🧪 Testing the API

### Using Swagger UI
1. Navigate to http://localhost:8080/swagger-ui.html
2. Click "Authorize" button
3. Enter your JWT token: `Bearer {your_token}`
4. Test any endpoint directly from the UI

### Using cURL

**Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

**Create Project (with token):**
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{"name":"My Project","description":"Project description","ownerId":1}'
```

## 📦 Project Structure

```
src/main/java/com/iamskrai/task_sphere/
├── auth/                    # Authentication & Security
│   ├── config/              # Security configuration
│   ├── dto/                 # Auth DTOs
│   ├── AuthController.java
│   ├── AuthService.java
│   ├── JwtTokenProvider.java
│   └── JwtAuthenticationFilter.java
├── user/                    # User management
│   ├── dto/                 # User DTOs
│   ├── entity/             # User entity
│   ├── UserController.java
│   ├── UserService.java
│   └── UserRepository.java
├── project/                 # Project management
│   ├── dto/                 # Project DTOs
│   ├── entity/              # Project entity
│   ├── ProjectController.java
│   ├── ProjectService.java
│   └── ProjectRepository.java
├── task/                    # Task management
│   ├── dto/                 # Task DTOs
│   ├── entity/              # Task entity
│   ├── TaskController.java
│   ├── TaskService.java
│   ├── TaskRepository.java
│   └── TaskStatus.java
├── TaskComment/             # Comment management
│   ├── dto/                 # Comment DTOs
│   ├── TaskCommentController.java
│   ├── TaskCommentService.java
│   └── TaskCommentRepository.java
├── exception/               # Exception handling
│   ├── dto/                # Error response DTOs
│   ├── GlobalExceptionHandler.java
│   └── Custom exceptions
├── config/                  # Configuration classes
│   └── OpenApiConfig.java
└── TaskSphereApplication.java
```

## 🔄 Future Enhancements

Potential features for future development:
- Project members (many-to-many relationship)
- Task priorities and tags
- File attachments
- Activity logs/audit trail
- Email notifications
- Task assignments to multiple users
- Project templates
- Advanced search and filtering
- Pagination for list endpoints
- Role-based access control (ADMIN, USER roles)

## 📄 License

This project is part of a learning exercise for distributed systems with Spring Boot.

## 👥 Support

For issues or questions, please refer to the API documentation at `/swagger-ui.html` or check the application logs for detailed error messages.

---

**Version**: 1.0.0  
**Last Updated**: 2024


# Kanban Task Manager Backend

A modern, scalable backend service for a collaborative kanban-style project management system built with Spring Boot and GraphQL.

## 🚀 Features

### Board Management
- Create, update, and delete kanban boards
- Customizable board columns with drag-and-drop positioning
- Board color themes and descriptions
- Multi-user collaboration with role-based access control

### Task Management
- Complete CRUD operations for tasks
- Task priorities (High/Medium/Low) and statuses (TODO/DOING/DONE)
- Drag-and-drop task positioning between columns
- Due dates and time estimation tracking
- Task assignments to team members
- Flexible tagging system
- File attachments support

### Advanced Capabilities
- **Subtasks**: Break down complex tasks into manageable components
- **Comments**: Task-level discussions and collaboration
- **Real-time Updates**: Event-driven architecture with Kafka
- **GraphQL Federation**: Microservice-ready architecture

## 🛠 Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.4
- **API**: GraphQL with Netflix DGS Framework
- **Database**: PostgreSQL with Flyway migrations
- **Messaging**: Apache Kafka
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Additional Libraries**:
  - MapStruct for object mapping
  - Lombok for boilerplate reduction
  - SpringDoc OpenAPI for documentation
  - Spring Data JPA for data access

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+ (or use included Maven wrapper)
- Docker and Docker Compose
- PostgreSQL (if running without Docker)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd backend
```

### 2. Start Dependencies
```bash
# Start PostgreSQL and other services
docker-compose up -d
```

### 3. Run Database Migrations
```bash
./mvnw flyway:migrate
```

### 4. Start the Application
```bash
./mvnw spring-boot:run
```

The application will be available at:
- **API Endpoint**: http://localhost:8085/graphql
- **GraphiQL Interface**: http://localhost:8085/graphiql

## 🐳 Docker Setup

### Using Docker Compose
```bash
# Build and start all services
docker-compose up --build

# Start in detached mode
docker-compose up -d --build
```

### Manual Docker Build
```bash
# Build the image
docker build -t kanban-backend .

# Run the container
docker run -p 8085:8080 kanban-backend
```

## 📊 Database Configuration

### Default Configuration
- **Host**: localhost
- **Port**: 5434
- **Database**: kanban_db
- **Username**: postgres
- **Password**: secret123

### Environment Variables
You can override database settings using environment variables:
```bash
export SPRING_POSTGRES_URL=jdbc:postgresql://localhost:5434/kanban_db
export SPRING_POSTGRES_USER=postgres
export SPRING_POSTGRES_PASSWORD=secret123
```

## 🔧 Development

### Build the Project
```bash
./mvnw clean compile
```

### Run Tests
```bash
./mvnw test
```

### Generate GraphQL Types
GraphQL types are automatically generated from the schema during build:
```bash
./mvnw generate-sources
```

### Database Management
```bash
# Run migrations
./mvnw flyway:migrate

# Clean database (dev only)
./mvnw flyway:clean
```

## 📡 API Documentation

### GraphQL Schema
The GraphQL schema is located at `src/main/resources/schema/schema.graphqls`.

### Key Operations

#### Queries
- `getBoards`: Retrieve all boards
- `getBoardById(id)`: Get specific board
- `getTasks`: Retrieve all tasks
- `getTaskById(id)`: Get specific task
- `getTasksByColumnIdAndBoardId(columnId, boardId)`: Get tasks by column

#### Mutations
- **Boards**: `createBoard`, `updateBoard`, `deleteBoardById`
- **Tasks**: `createTask`, `updateTask`, `deleteTaskById`
- **Positioning**: `updateBoardColumnPosition`, `updateTaskPosition`

### Example Query
```graphql
query GetBoardWithTasks {
  getBoardById(id: "1") {
    id
    name
    description
    columns {
      id
      name
      position
      taskCount
    }
  }
}
```

### Example Mutation
```graphql
mutation CreateTask {
  createTask(task: {
    boardId: "1"
    createdBy: 1
    title: "New Task"
    description: "Task description"
    status: TODO
    priority: HIGH
    assignedTo: 1
    tags: ["feature", "urgent"]
    subtasks: []
  }) {
    success
    message
    task {
      id
      title
      status
    }
  }
}
```

## ⚙️ Configuration

### Application Properties
Key configuration options in `application.yml`:

```yaml
server:
  port: 8085

spring:
  datasource:
    url: jdbc:postgresql://localhost:5434/kanban_db
    username: postgres
    password: secret123
  
  kafka:
    bootstrap-servers: localhost:8081
    consumer:
      group-id: user_event_group_1
  
  graphql:
    graphiql:
      enabled: true
```

## 🔄 Event Streaming

The application uses Apache Kafka for real-time event processing:
- **Bootstrap Servers**: localhost:8081
- **Consumer Group**: user_event_group_1
- **Auto Offset Reset**: earliest

## 🏗 Project Structure

```
src/
├── main/
│   ├── java/org/abraham/kanbantaskmanager/
│   │   ├── generated/          # Auto-generated GraphQL types
│   │   ├── controllers/        # GraphQL resolvers
│   │   ├── services/          # Business logic
│   │   ├── repositories/      # Data access layer
│   │   ├── entities/          # JPA entities
│   │   └── dtos/             # Data transfer objects
│   └── resources/
│       ├── schema/
│       │   └── schema.graphqls # GraphQL schema definition
│       ├── db/migration/      # Flyway migration scripts
│       └── application.yml    # Application configuration
└── test/                      # Test files
```

## 🧪 Testing

### Running Tests
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=BoardServiceTest

# Run with coverage
./mvnw test jacoco:report
```

## 📦 Deployment

### Production Build
```bash
./mvnw clean package -DskipTests
```

### Environment Variables for Production
```bash
SPRING_POSTGRES_URL=jdbc:postgresql://prod-db:5432/kanban_prod
SPRING_POSTGRES_USER=prod_user
SPRING_POSTGRES_PASSWORD=secure_password
KAFKA_BOOTSTRAP_SERVERS=kafka-cluster:9092
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Write meaningful commit messages
- Add tests for new features

## 📝 Common Commands

### Development
```bash
# Clean and rebuild
./mvnw clean install

# Run in debug mode
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Check dependencies
./mvnw dependency:tree
```

### Database
```bash
# Reset database
./mvnw flyway:clean flyway:migrate

# Check migration status
./mvnw flyway:info
```

## 🐛 Troubleshooting

### Common Issues

1. **Port already in use**: Change the port in `application.yml` or stop the conflicting service
2. **Database connection failed**: Ensure PostgreSQL is running and credentials are correct
3. **GraphQL schema errors**: Check schema syntax in `schema.graphqls`

### Logs
Application logs are available in the console or can be configured to write to files.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Abraham Nyagar - Initial work

## 🙏 Acknowledgments

- Netflix DGS Framework for GraphQL implementation
- Spring Boot team for the excellent framework
- Apollo Federation for microservice capabilities

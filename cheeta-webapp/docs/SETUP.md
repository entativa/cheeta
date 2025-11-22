# Cheeta WebApp Setup Guide

Complete guide to get Cheeta WebApp running on your machine.

## Prerequisites

### Required
- **JDK 17 or higher** (Amazon Corretto, OpenJDK, or Oracle JDK)
- **Docker & Docker Compose** (for local dependencies)
- **Git** (for version control)

### Optional
- **IntelliJ IDEA** (recommended IDE for Kotlin/Vaadin development)
- **PostgreSQL Client** (for database inspection)

---

## Quick Start (5 Minutes)

### 1. Clone and Setup
```bash
# Run the scaffold script to generate project structure
python scaffold.py

# Navigate to project
cd cheeta-webapp

# Copy configuration files (that we created) into the project
# Copy all the Kotlin view files to their respective locations
# Copy build.gradle.kts, settings.gradle.kts, etc.
```

### 2. Start Dependencies
```bash
# Start PostgreSQL, Redis, and OneDev
docker-compose up -d

# Wait for services to be healthy (about 30 seconds)
docker-compose ps
```

### 3. Run the Application
```bash
# Make gradlew executable (Linux/Mac)
chmod +x gradlew

# Run in development mode
./gradlew bootRun

# Or on Windows
gradlew.bat bootRun
```

### 4. Open Browser
```
http://localhost:8080
```

The application will automatically open in your default browser!

---

## Detailed Setup

### Step 1: Project Structure

After running the scaffold script, your project should look like this:

```
cheeta-webapp/
├── build.gradle.kts              # Gradle build configuration
├── settings.gradle.kts           # Gradle settings
├── gradle.properties             # Gradle properties
├── docker-compose.yml            # Local development services
├── .gitignore                    # Git ignore rules
├── src/
│   ├── main/
│   │   ├── kotlin/io/cheeta/
│   │   │   ├── Application.kt    # Main entry point
│   │   │   ├── MainLayout.kt     # App shell
│   │   │   └── views/           # All view files
│   │   └── resources/
│   │       ├── application.yml   # Configuration
│   │       └── META-INF/
│   └── test/
└── frontend/                     # Vaadin frontend resources
```

### Step 2: Configure Environment

Create a `.env` file in the project root (optional, has defaults):

```bash
# Database
DB_PASSWORD=cheeta

# OneDev Integration
ONEDEV_URL=http://localhost:6610
ONEDEV_API_KEY=your-api-key-here

# AI Integration (choose one or both)
AI_PROVIDER=openai
OPENAI_API_KEY=sk-your-key-here
# ANTHROPIC_API_KEY=your-key-here

# Security
JWT_SECRET=your-super-secret-jwt-key-change-in-production
ADMIN_PASSWORD=admin123
```

### Step 3: Database Setup

The application uses PostgreSQL for data persistence.

#### Option A: Use Docker (Recommended)
```bash
docker-compose up -d postgres
```

#### Option B: Local PostgreSQL
```sql
-- Connect to PostgreSQL and run:
CREATE DATABASE cheeta;
CREATE USER cheeta WITH PASSWORD 'cheeta';
GRANT ALL PRIVILEGES ON DATABASE cheeta TO cheeta;
```

Update `application.yml` with your local database credentials.

### Step 4: OneDev Setup

Cheeta WebApp integrates with OneDev for Git operations.

#### Start OneDev
```bash
docker-compose up -d onedev
```

#### Access OneDev
```
http://localhost:6610
```

#### Get API Key
1. Login to OneDev (create admin account on first run)
2. Go to User Settings → Access Tokens
3. Generate new token
4. Copy token to `.env` file as `ONEDEV_API_KEY`

### Step 5: AI Integration (Optional)

Cheeta uses AI for code review, chat, and assistance.

#### OpenAI Setup
1. Get API key from https://platform.openai.com/api-keys
2. Add to `.env`: `OPENAI_API_KEY=sk-your-key`

#### Anthropic Claude Setup
1. Get API key from https://console.anthropic.com/
2. Add to `.env`: `ANTHROPIC_API_KEY=your-key`

### Step 6: Build and Run

#### Development Mode (Hot Reload)
```bash
./gradlew bootRun
```

Features in dev mode:
- Automatic restart on code changes
- Live reload for UI changes
- Debug logging enabled
- Vaadin debug window available

#### Production Build
```bash
./gradlew clean build -Pvaadin.productionMode
java -jar build/libs/cheeta-webapp-0.1.0-SNAPSHOT.jar
```

---

## Development Workflow

### Running the App

#### Start All Services
```bash
# Terminal 1: Start dependencies
docker-compose up

# Terminal 2: Run application
./gradlew bootRun
```

#### IntelliJ IDEA
1. Open project in IntelliJ
2. Wait for Gradle sync
3. Run `Application.kt` (right-click → Run)
4. Or use Spring Boot run configuration

### Making Changes

#### Hot Reload
- **Kotlin code changes**: Automatically reloaded by Spring DevTools
- **UI changes**: Refresh browser (Ctrl+R)
- **Configuration changes**: Restart application

#### Adding New Views
1. Create new Kotlin file in `src/main/kotlin/io/cheeta/views/yourview/`
2. Extend appropriate Vaadin component
3. Add `@Route` annotation
4. Update `MainLayout.kt` navigation if needed

```kotlin
@Route(value = "myview", layout = MainLayout::class)
@PageTitle("My View | Cheeta")
class MyView : VerticalLayout() {
    init {
        add(H1("Hello from My View!"))
    }
}
```

### Database Migrations

Hibernate auto-updates schema in development (`ddl-auto: update`).

For production, use Flyway or Liquibase:

```kotlin
// Add to build.gradle.kts
implementation("org.flywaydb:flyway-core")
```

---

## Troubleshooting

### Port Already in Use
```bash
# Check what's using port 8080
lsof -i :8080  # Mac/Linux
netstat -ano | findstr :8080  # Windows

# Kill the process or change port in application.yml
server:
  port: 8081
```

### Gradle Build Fails
```bash
# Clear Gradle cache
./gradlew clean

# Delete .gradle folder
rm -rf .gradle

# Re-run
./gradlew build
```

### Vaadin Components Not Loading
```bash
# Clear Vaadin cache
./gradlew clean vaadinClean

# Rebuild frontend
./gradlew vaadinPrepareFrontend vaadinBuildFrontend
```

### Database Connection Failed
```bash
# Check if PostgreSQL is running
docker-compose ps postgres

# Check logs
docker-compose logs postgres

# Restart PostgreSQL
docker-compose restart postgres
```

### OneDev Not Starting
```bash
# Check logs
docker-compose logs onedev

# OneDev takes 1-2 minutes to start first time
# Wait for: "Server is ready at http://localhost:6610"
```

### Out of Memory
```bash
# Increase Gradle memory in gradle.properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m
```

---

## Testing

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test
```bash
./gradlew test --tests "DashboardViewTest"
```

### Integration Tests
```bash
./gradlew integrationTest
```

---

## Building for Production

### Create Production JAR
```bash
./gradlew clean build -Pvaadin.productionMode
```

Output: `build/libs/cheeta-webapp-0.1.0-SNAPSHOT.jar`

### Create Docker Image
```bash
docker build -t cheeta-webapp:latest .
```

### Run Production Container
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/cheeta \
  -e ONEDEV_URL=http://host.docker.internal:6610 \
  -e OPENAI_API_KEY=your-key \
  cheeta-webapp:latest
```

---

## IDE Setup

### IntelliJ IDEA (Recommended)

#### Install Plugins
1. Kotlin (bundled)
2. Vaadin (optional, for enhanced support)
3. Spring Boot (bundled in Ultimate)

#### Import Project
1. File → Open
2. Select `cheeta-webapp` folder
3. Wait for Gradle sync
4. Enable annotation processing (for Spring)

#### Run Configuration
1. Run → Edit Configurations
2. Add new Spring Boot configuration
3. Main class: `io.cheeta.ApplicationKt`
4. Working directory: `$MODULE_WORKING_DIR$`

#### Code Style
The project uses Kotlin official code style. IntelliJ will auto-detect from `gradle.properties`.

### VS Code

#### Install Extensions
- Kotlin Language
- Spring Boot Extension Pack
- Gradle for Java

#### Run
```bash
# Use terminal
./gradlew bootRun
```

---

## Next Steps

Now that Cheeta is running:

1. **Explore the UI**: Navigate through Dashboard, DevFeed, AI Chat, Jobs, Profile
2. **Configure OneDev**: Set up your first repository in OneDev
3. **Add AI API Key**: Enable AI features with your OpenAI/Anthropic key
4. **Start Coding**: Build out the service layer to connect UI to OneDev
5. **Customize**: Modify themes, add features, make it yours!

### Key Files to Understand
- `Application.kt` - App entry point and Spring configuration
- `MainLayout.kt` - App shell with header, sidebar, navigation
- `views/*` - Individual page views
- `application.yml` - Configuration and settings

### Integration Priorities
1. **OneDev API Client** - Connect to OneDev backend
2. **AI Service** - Wire up OpenAI/Anthropic
3. **Authentication** - Implement user login/sessions
4. **Real-time Updates** - WebSocket for live notifications
5. **Storage** - File upload and repository management

---

## Getting Help

- **Documentation**: See `/docs` folder
- **Issues**: File on GitHub
- **Discussions**: Community forum
- **Discord**: Join our Discord server (coming soon)

---

## Contributing

We welcome contributions! See `CONTRIBUTING.md` for guidelines.

---

**Happy Coding! 🐆**

Build something amazing with Cheeta.

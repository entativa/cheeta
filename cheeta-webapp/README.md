# Cheeta WebApp

The next-generation web interface for Cheeta - an AI-powered, social DevOps platform built with Vaadin and Kotlin.

## Overview

Cheeta WebApp is a modern, type-safe web application that provides the user interface for the Cheeta platform. Built on Vaadin Flow with Kotlin, it offers a seamless developer experience with compile-time safety and JVM-native performance.

### Why Vaadin + Kotlin?

- **Type Safety**: Catch UI bugs at compile time, not in production
- **JVM Native**: Direct integration with OneDev's Java backend
- **Server-Side Rendering**: SEO-friendly, no hydration mismatches
- **No JS Toolchain**: Skip webpack, npm, and bundler complexity
- **Productive**: Fast development with hot reload and powerful IDE support

## Architecture

```
┌─────────────────────────────────────┐
│    Nginx/Traefik (Reverse Proxy)    │
└──────────────┬──────────────────────┘
               │
       ┌───────┴────────┐
       │                │
┌──────▼─────┐   ┌─────▼──────────┐
│  OneDev    │   │  Cheeta WebApp │
│  Backend   │◄──┤  (Vaadin/Kotlin)│
│  (Java)    │   │                │
└──────┬─────┘   └────────────────┘
       │
┌──────▼─────┐
│ PostgreSQL │
└────────────┘
```

### Design Philosophy

- **Separation of Concerns**: Clean separation between data (OneDev) and presentation (Vaadin)
- **API-First**: All backend communication through well-defined REST APIs
- **Gradual Migration**: New features in Vaadin first, migrate old UI incrementally
- **Feature Flags**: Toggle between old and new implementations during migration

## Features

### Current Focus (Phase 1)

- ✅ AI Chat Interface - Contextual AI assistance throughout the platform
- ✅ DevFeed - Stack Overflow + Reddit hybrid for developers
- ✅ Social Features - GitHub-style profiles, feeds, and collaboration
- ✅ Modern Dashboard - Activity overview and quick actions
- 🚧 Repository Browser - Code exploration with AI insights
- 🚧 Issue Management - Intelligent triage and tracking
- 🚧 Pull Request Workflows - AI-powered code review

### Future Phases

- Phase 2: Migration of high-traffic pages (dashboard, repo browser, issues)
- Phase 3: Settings, admin panels, and remaining features
- Phase 4: Complete migration, removal of legacy UI

## Project Structure

```
cheeta-webapp/
├── src/
│   ├── main/
│   │   ├── kotlin/io/cheeta/
│   │   │   ├── Application.kt              # Main application entry
│   │   │   ├── MainLayout.kt               # App shell layout
│   │   │   ├── config/                     # Configuration classes
│   │   │   ├── security/                   # Authentication & authorization
│   │   │   ├── api/                        # OneDev API clients
│   │   │   ├── views/                      # Vaadin views (pages)
│   │   │   │   ├── dashboard/              # Dashboard views
│   │   │   │   ├── ai/                     # AI chat interface
│   │   │   │   ├── feed/                   # DevFeed community
│   │   │   │   ├── repository/             # Code browsing
│   │   │   │   ├── issues/                 # Issue tracking
│   │   │   │   ├── pullrequests/           # PR workflows
│   │   │   │   ├── cicd/                   # CI/CD pipelines
│   │   │   │   ├── profile/                # User profiles
│   │   │   │   ├── social/                 # Social features
│   │   │   │   ├── settings/               # User settings
│   │   │   │   └── admin/                  # Admin panels
│   │   │   ├── components/                 # Reusable UI components
│   │   │   │   ├── layout/                 # Layout components
│   │   │   │   ├── cards/                  # Card components
│   │   │   │   ├── lists/                  # List components
│   │   │   │   ├── forms/                  # Form components
│   │   │   │   ├── navigation/             # Navigation components
│   │   │   │   ├── code/                   # Code display components
│   │   │   │   ├── charts/                 # Chart components
│   │   │   │   ├── dialogs/                # Dialog components
│   │   │   │   └── notifications/          # Notification components
│   │   │   ├── services/                   # Business logic services
│   │   │   │   ├── ai/                     # AI integration services
│   │   │   │   ├── feed/                   # DevFeed services
│   │   │   │   ├── notifications/          # Notification services
│   │   │   │   ├── search/                 # Search services
│   │   │   │   └── analytics/              # Analytics services
│   │   │   ├── models/                     # Domain models
│   │   │   └── utils/                      # Utility classes
│   │   └── resources/
│   │       ├── application.yml             # Application configuration
│   │       ├── META-INF/resources/         # Static resources
│   │       │   └── themes/cheeta/          # Cheeta theme
│   │       └── messages*.properties        # i18n translations
│   └── test/
│       └── kotlin/io/cheeta/               # Test files
├── frontend/                               # Frontend resources
│   ├── themes/cheeta/                      # Custom theme
│   └── styles/                             # Global styles
├── docs/                                   # Documentation
├── scripts/                                # Build & deploy scripts
└── config/                                 # Environment configs
```

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle 8.x (or use included wrapper)
- OneDev backend running (for full functionality)

### Development Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/cheeta-webapp.git
cd cheeta-webapp
```

2. Run in development mode:
```bash
./gradlew bootRun
```

3. Open browser:
```
http://localhost:8080
```

### Development Mode Features

- **Hot Reload**: Changes to Kotlin code reload automatically
- **Live Theme Editing**: CSS changes apply instantly
- **Debug Mode**: Full stack traces and debugging tools

### Building for Production

```bash
./gradlew clean build -Pvaadin.productionMode
```

The production build includes:
- Optimized JavaScript bundle
- Minified CSS
- Compiled Vaadin components
- Reduced bundle size

## Configuration

### Application Configuration

Edit `src/main/resources/application.yml`:

```yaml
cheeta:
  onedev:
    url: http://localhost:6610
    api-key: ${ONEDEV_API_KEY}
  ai:
    provider: openai
    api-key: ${OPENAI_API_KEY}
  security:
    jwt:
      secret: ${JWT_SECRET}
```

### Environment-Specific Configuration

- Development: `application-dev.yml`
- Production: `application-prod.yml`

Activate with: `--spring.profiles.active=dev`

## Development Guidelines

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable names
- Keep functions small and focused
- Write self-documenting code

### Component Development

```kotlin
@Route("example")
class ExampleView : VerticalLayout() {
    init {
        add(H1("Example View"))
        
        val button = Button("Click Me") {
            Notification.show("Clicked!")
        }
        add(button)
    }
}
```

### Service Integration

```kotlin
class ExampleService(
    private val oneDevClient: OneDevClient
) {
    suspend fun getData(): List<Item> {
        return oneDevClient.getItems()
    }
}
```

### Testing

```kotlin
@Test
fun `test example view`() {
    val view = ExampleView()
    assertEquals("Example View", view.title)
}
```

Run tests:
```bash
./gradlew test
```

## Theming

### Custom Theme

Cheeta uses a custom Vaadin theme located in `src/main/resources/META-INF/resources/themes/cheeta/`.

### Customizing Colors

Edit `styles/colors.css`:

```css
:root {
    --cheeta-primary: #007bff;
    --cheeta-secondary: #6c757d;
    --cheeta-success: #28a745;
    --cheeta-danger: #dc3545;
}
```

### Dark Mode

Dark mode styles are in `styles/dark-mode.css` and automatically apply based on user preference.

## API Integration

### OneDev Backend Integration

The webapp communicates with OneDev through REST APIs:

```kotlin
class OneDevClient(
    private val baseUrl: String,
    private val apiKey: String
) {
    suspend fun getRepositories(): List<Repository> {
        return httpClient.get("$baseUrl/api/repositories") {
            header("Authorization", "Bearer $apiKey")
        }
    }
}
```

### WebSocket Support

Real-time updates use WebSocket connections:

```kotlin
@Component
class WebSocketService {
    fun subscribe(topic: String, handler: (Message) -> Unit) {
        // WebSocket subscription logic
    }
}
```

## Deployment

### Docker

Build Docker image:
```bash
docker build -t cheeta-webapp .
```

Run container:
```bash
docker run -p 8080:8080 \
  -e ONEDEV_URL=http://onedev:6610 \
  -e ONEDEV_API_KEY=your-key \
  cheeta-webapp
```

### Docker Compose

```bash
docker-compose up -d
```

### Kubernetes

Deploy to Kubernetes:
```bash
kubectl apply -f k8s/
```

## Monitoring

### Health Checks

- Health endpoint: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

### Logging

Logs are configured in `logback.xml`. Default log level is INFO.

Change log level:
```yaml
logging:
  level:
    io.cheeta: DEBUG
```

## Performance

### Optimization Tips

- Use lazy loading for large lists
- Implement virtual scrolling
- Cache expensive computations
- Use async operations for API calls
- Optimize database queries

### Bundle Size

Production builds are optimized for size:
- Minified JavaScript
- Tree-shaken dependencies
- Compressed assets
- Lazy-loaded routes

## Troubleshooting

### Common Issues

**Issue**: Port 8080 already in use
```bash
./gradlew bootRun --args='--server.port=8081'
```

**Issue**: Vaadin components not loading
```bash
./gradlew clean vaadinClean vaadinPrepareFrontend
```

**Issue**: Hot reload not working
- Ensure you're running in development mode
- Check that Spring Boot DevTools is enabled

### Debug Mode

Enable debug logging:
```bash
./gradlew bootRun --args='--logging.level.io.cheeta=DEBUG'
```

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Development Workflow

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write tests
5. Submit a pull request

### Code Review

All changes require:
- Passing tests
- Code review approval
- No merge conflicts

## Roadmap

### Immediate (Next 3 Months)

- [ ] Complete AI chat interface
- [ ] DevFeed MVP with Q&A functionality
- [ ] Social profiles and activity feeds
- [ ] Repository browser with syntax highlighting

### Short Term (3-6 Months)

- [ ] Issue management migration
- [ ] Pull request workflows
- [ ] CI/CD pipeline views
- [ ] Advanced search functionality

### Long Term (6-12 Months)

- [ ] Complete OneDev UI migration
- [ ] Plugin system
- [ ] Mobile-optimized views
- [ ] Advanced analytics dashboard

## License

Proprietary - All Rights Reserved

This software is proprietary and confidential. Unauthorized copying, distribution, or use is strictly prohibited.

## Support

- Documentation: [docs/](docs/)
- Issues: [GitHub Issues](https://github.com/yourusername/cheeta-webapp/issues)
- Discussions: [GitHub Discussions](https://github.com/yourusername/cheeta-webapp/discussions)

## Acknowledgments

- Built on [Vaadin](https://vaadin.com/) - Modern web framework
- Powered by [Kotlin](https://kotlinlang.org/) - Better JVM language
- Integrates with [OneDev](https://github.com/theonedev/onedev) - Self-hosted DevOps platform
- Inspired by GitHub, GitLab, and Stack Overflow

---

**Built with pride by developers, for developers.**

*"I built this on Cheeta."*

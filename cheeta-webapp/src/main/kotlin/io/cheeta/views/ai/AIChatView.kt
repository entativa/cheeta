package io.cheeta.views.ai

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility
import io.cheeta.MainLayout

@Route(value = "ai", layout = MainLayout::class)
@PageTitle("AI Assistant | Cheeta")
class AIChatView : VerticalLayout() {

    private val chatArea = VerticalLayout().apply {
        setWidthFull()
        addClassName("ai-chat-messages")
        element.style.apply {
            set("flex", "1")
            set("overflow-y", "auto")
            set("padding", "var(--lumo-space-m)")
        }
    }

    private val inputArea = TextArea().apply {
        placeholder = "Ask AI anything about your code, architecture, or DevOps..."
        setWidthFull()
        element.style.apply {
            set("min-height", "100px")
        }
    }

    init {
        setSizeFull()
        isPadding = false
        addClassName("ai-chat-view")

        add(
            createHeader(),
            createSuggestionsPanel(),
            chatArea,
            createInputSection()
        )

        addWelcomeMessage()
    }

    private fun createHeader(): HorizontalLayout {
        val title = H1("AI Assistant").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val newChatButton = Button("New Chat", Icon(VaadinIcon.PLUS)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClickListener {
                chatArea.removeAll()
                addWelcomeMessage()
            }
        }

        val historyButton = Button(Icon(VaadinIcon.CLOCK)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        }

        return HorizontalLayout(title, newChatButton, historyButton).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("section-header")
            element.style.set("padding", "var(--lumo-space-m)")
        }
    }

    private fun createSuggestionsPanel(): HorizontalLayout {
        return HorizontalLayout(
            createSuggestionChip("📝 Explain this code", "/explain"),
            createSuggestionChip("🐛 Find bugs", "/review"),
            createSuggestionChip("✅ Write tests", "/test"),
            createSuggestionChip("⚡ Optimize performance", "/optimize")
        ).apply {
            setWidthFull()
            addClassName("suggestions-panel")
            element.style.apply {
                set("padding", "var(--lumo-space-m)")
                set("overflow-x", "auto")
            }
        }
    }

    private fun createSuggestionChip(text: String, command: String): Button {
        return Button(text).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            element.style.apply {
                set("white-space", "nowrap")
                set("border", "1px solid var(--lumo-contrast-10pct)")
                set("border-radius", "20px")
            }
            addClickListener {
                inputArea.value = command
                inputArea.focus()
            }
        }
    }

    private fun createInputSection(): VerticalLayout {
        val sendButton = Button("Send", Icon(VaadinIcon.PAPERPLANE)).apply {
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            addClickListener {
                val message = inputArea.value
                if (message.isNotBlank()) {
                    addUserMessage(message)
                    inputArea.clear()
                    simulateAIResponse(message)
                }
            }
        }

        val shortcuts = HorizontalLayout(
            Span("/explain").apply { addClassName("shortcut-hint") },
            Span("/review").apply { addClassName("shortcut-hint") },
            Span("/test").apply { addClassName("shortcut-hint") },
            Span("/fix").apply { addClassName("shortcut-hint") },
            Span("/optimize").apply { addClassName("shortcut-hint") }
        ).apply {
            addClassNames(
                LumoUtility.FontSize.XSMALL,
                LumoUtility.TextColor.SECONDARY
            )
            element.style.set("margin-top", "var(--lumo-space-xs)")
        }

        val inputLayout = HorizontalLayout(inputArea, sendButton).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.END
            expand(inputArea)
        }

        return VerticalLayout(shortcuts, inputLayout).apply {
            setWidthFull()
            isPadding = false
            element.style.apply {
                set("padding", "var(--lumo-space-m)")
                set("border-top", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun addWelcomeMessage() {
        val welcomeText = """
            👋 Hi! I'm your AI assistant for Cheeta.
            
            I can help you with:
            
            • **Code Reviews**: Analyze your code for bugs, security issues, and best practices
            • **Explanations**: Understand complex code and algorithms
            • **Testing**: Generate unit tests, integration tests, and test data
            • **Optimization**: Identify performance bottlenecks and suggest improvements
            • **Documentation**: Generate docs from your code
            • **Debugging**: Analyze stack traces and suggest fixes
            
            Try these commands:
            • `/explain` - Explain code or concepts
            • `/review` - Review code for issues
            • `/test` - Generate tests
            • `/fix` - Suggest bug fixes
            • `/optimize` - Performance suggestions
            
            What can I help you with today?
        """.trimIndent()

        addAIMessage(welcomeText)
    }

    private fun addUserMessage(message: String) {
        val messageDiv = createMessageBubble(message, isUser = true)
        chatArea.add(messageDiv)
        scrollToBottom()
    }

    private fun addAIMessage(message: String) {
        val messageDiv = createMessageBubble(message, isUser = false)
        chatArea.add(messageDiv)
        scrollToBottom()
    }

    private fun createMessageBubble(text: String, isUser: Boolean): Div {
        val content = if (text.contains("```")) {
            createCodeBlock(text)
        } else {
            Div(Paragraph(text))
        }

        return Div(content).apply {
            addClassName("message-bubble")
            element.style.apply {
                set("padding", "var(--lumo-space-m)")
                set("border-radius", "var(--lumo-border-radius-m)")
                set("margin-bottom", "var(--lumo-space-m)")
                set("max-width", "85%")
                if (isUser) {
                    set("background", "var(--lumo-primary-color)")
                    set("color", "var(--lumo-primary-contrast-color)")
                    set("margin-left", "auto")
                } else {
                    set("background", "var(--lumo-contrast-10pct)")
                }
            }
        }
    }

    private fun createCodeBlock(text: String): Div {
        val parts = text.split("```")
        val container = Div()

        parts.forEachIndexed { index, part ->
            if (index % 2 == 0) {
                if (part.isNotBlank()) {
                    container.add(Paragraph(part))
                }
            } else {
                val codeBlock = Pre(part.trim()).apply {
                    element.style.apply {
                        set("background", "var(--lumo-contrast-5pct)")
                        set("padding", "var(--lumo-space-s)")
                        set("border-radius", "var(--lumo-border-radius-s)")
                        set("overflow-x", "auto")
                        set("font-family", "monospace")
                        set("font-size", "13px")
                    }
                }
                container.add(codeBlock)
            }
        }

        return container
    }

    private fun simulateAIResponse(userMessage: String) {
        val response = when {
            userMessage.contains("/explain") -> """
                I'd be happy to explain! Could you share the specific code or concept you'd like me to explain?
                
                You can paste code directly, or describe what you're trying to understand.
            """.trimIndent()
            
            userMessage.contains("/review") -> """
                To review your code, please share:
                
                1. The code you want me to review
                2. Any specific concerns (performance, security, maintainability)
                3. The context (what problem it solves)
                
                I'll analyze it for:
                • Bugs and potential issues
                • Security vulnerabilities
                • Performance problems
                • Code quality and best practices
            """.trimIndent()
            
            userMessage.contains("/test") -> """
                I can help generate tests! Here's what I need:
                
                1. The code/function you want to test
                2. The testing framework you're using (JUnit, Kotest, etc.)
                3. Any edge cases you want covered
                
                I'll create comprehensive test cases including:
                • Happy path tests
                • Edge cases
                • Error handling
                • Mock data where needed
            """.trimIndent()
            
            userMessage.contains("kotlin") || userMessage.contains("Kotlin") -> """
                I see you're asking about Kotlin! I'm well-versed in Kotlin and can help with:
                
                • Coroutines and async programming
                • Extension functions and DSLs
                • Data classes and sealed classes
                • Flow and reactive programming
                • Spring Boot with Kotlin
                • Vaadin with Kotlin
                
                What specifically would you like help with?
            """.trimIndent()
            
            else -> """
                I'm processing your request: "$userMessage"
                
                This is a demo response. In production, I would:
                • Analyze your code context
                • Check your repository structure
                • Review recent commits and PRs
                • Provide intelligent, context-aware suggestions
                
                What specific help do you need?
            """.trimIndent()
        }

        addAIMessage(response)
    }

    private fun scrollToBottom() {
        chatArea.element.executeJs("this.scrollTop = this.scrollHeight")
    }
}

package io.cheeta

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.sidenav.SideNav
import com.vaadin.flow.component.sidenav.SideNavItem
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.theme.lumo.LumoUtility
import io.cheeta.views.dashboard.DashboardView
import io.cheeta.views.ai.AIChatView
import io.cheeta.views.feed.DevFeedView
import io.cheeta.views.repository.RepositoryView
import io.cheeta.views.issues.IssuesView
import io.cheeta.views.pullrequests.PullRequestsView
import io.cheeta.views.cicd.CICDView
import io.cheeta.views.profile.ProfileView
import io.cheeta.views.social.SocialView
import io.cheeta.views.settings.SettingsView

class MainLayout : AppLayout() {

    private val aiPanel = createAIPanel()
    private var aiPanelVisible = false

    init {
        createHeader()
        createDrawer()
        addClassName("main-layout")
    }

    private fun createHeader() {
        val logo = createLogo()
        val searchBar = createSearchBar()
        val headerActions = createHeaderActions()

        val header = HorizontalLayout(logo, searchBar, headerActions).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            expand(searchBar)
            setWidthFull()
            addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM
            )
        }

        addToNavbar(header)
    }

    private fun createLogo(): HorizontalLayout {
        val logo = H1("🐆 Cheeta").apply {
            addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE
            )
            addClassName("logo")
        }

        return HorizontalLayout(DrawerToggle(), logo).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("logo-container")
        }
    }

    private fun createSearchBar(): TextField {
        return TextField().apply {
            placeholder = "Search repos, issues, code, or ask AI..."
            prefixComponent = Icon(VaadinIcon.SEARCH)
            setWidthFull()
            addClassName("search-bar")
        }
    }

    private fun createHeaderActions(): HorizontalLayout {
        val newButton = Button(Icon(VaadinIcon.PLUS)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClassName("icon-button")
        }

        val notificationButton = Button(Icon(VaadinIcon.BELL)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClassName("icon-button")
            element.setAttribute("badge", "3")
        }

        val aiButton = Button(Icon(VaadinIcon.AUTOMATION)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClassName("icon-button")
            addClickListener { toggleAIPanel() }
        }

        val avatar = Avatar().apply {
            name = "User Name"
            addClassName("user-avatar")
        }

        return HorizontalLayout(newButton, notificationButton, aiButton, avatar).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("header-actions")
        }
    }

    private fun createDrawer() {
        val drawer = VerticalLayout().apply {
            setWidthFull()
            isPadding = true
            addClassName("drawer-content")
        }

        drawer.add(
            createNavigationSection("Navigation", listOf(
                SideNavItem("Dashboard", DashboardView::class.java, VaadinIcon.DASHBOARD.create()),
                SideNavItem("Repositories", RepositoryView::class.java, VaadinIcon.FOLDER.create()),
                SideNavItem("DevFeed", DevFeedView::class.java, VaadinIcon.COMMENTS.create()),
                SideNavItem("Issues", IssuesView::class.java, VaadinIcon.BUG.create()),
                SideNavItem("Pull Requests", PullRequestsView::class.java, VaadinIcon.SPLIT.create()),
                SideNavItem("CI/CD", CICDView::class.java, VaadinIcon.COGS.create()),
                SideNavItem("Jobs", null, VaadinIcon.BRIEFCASE.create()),
                SideNavItem("Starred", null, VaadinIcon.STAR.create()),
                SideNavItem("Notifications", null, VaadinIcon.BELL.create())
            )),
            createNavigationSection("Your Repos", listOf(
                SideNavItem("cheeta-webapp", null, VaadinIcon.CODE.create()),
                SideNavItem("my-portfolio", null, VaadinIcon.CODE.create()),
                SideNavItem("ai-experiments", null, VaadinIcon.CODE.create())
            )),
            createNavigationSection("Teams", listOf(
                SideNavItem("🏢 Acme Corp", null, VaadinIcon.BUILDING.create()),
                SideNavItem("👥 Open Source Team", null, VaadinIcon.USERS.create())
            ))
        )

        addToDrawer(drawer)
    }

    private fun createNavigationSection(title: String, items: List<SideNavItem>): VerticalLayout {
        val sectionTitle = Span(title).apply {
            addClassNames(
                LumoUtility.FontSize.XSMALL,
                LumoUtility.FontWeight.SEMIBOLD,
                LumoUtility.TextColor.SECONDARY
            )
            element.style.set("text-transform", "uppercase")
        }

        val nav = SideNav().apply {
            items.forEach { addItem(it) }
        }

        return VerticalLayout(sectionTitle, nav).apply {
            isPadding = false
            spacing = false
            addClassName("nav-section")
        }
    }

    private fun createAIPanel(): VerticalLayout {
        val header = HorizontalLayout(
            H3("AI Assistant").apply { addClassName("ai-panel-title") },
            Button(Icon(VaadinIcon.CLOSE)).apply {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                addClickListener { toggleAIPanel() }
            }
        ).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("ai-panel-header")
        }

        val chatArea = VerticalLayout().apply {
            setWidthFull()
            addClassName("ai-chat-area")
        }

        chatArea.add(
            createAIMessage("assistant", "Hi! I'm your AI assistant. I can help you with:\n\n" +
                "• Code reviews and suggestions\n" +
                "• Explaining complex code\n" +
                "• Finding bugs and issues\n" +
                "• Writing tests\n" +
                "• Optimizing performance\n\n" +
                "What can I help you with today?")
        )

        val inputArea = createAIInputArea { message ->
            chatArea.add(createAIMessage("user", message))
            chatArea.add(createAIMessage("assistant", "I'm processing your request..."))
        }

        return VerticalLayout(header, chatArea, inputArea).apply {
            setWidthFull()
            setHeightFull()
            setPadding(false)
            addClassName("ai-panel")
            element.style.set("position", "fixed")
            element.style.set("right", "0")
            element.style.set("top", "var(--lumo-size-xl)")
            element.style.set("width", "400px")
            element.style.set("height", "calc(100vh - var(--lumo-size-xl))")
            element.style.set("background", "var(--lumo-base-color)")
            element.style.set("border-left", "1px solid var(--lumo-contrast-10pct)")
            element.style.set("transform", "translateX(100%)")
            element.style.set("transition", "transform 0.3s ease")
            element.style.set("z-index", "100")
        }
    }

    private fun createAIMessage(type: String, content: String): Div {
        return Div().apply {
            add(Paragraph(content))
            addClassName("ai-message")
            addClassName("ai-message-$type")
            element.style.apply {
                set("padding", "var(--lumo-space-m)")
                set("border-radius", "var(--lumo-border-radius-m)")
                set("margin-bottom", "var(--lumo-space-m)")
                if (type == "user") {
                    set("background", "var(--lumo-primary-color)")
                    set("color", "var(--lumo-primary-contrast-color)")
                    set("margin-left", "auto")
                    set("max-width", "85%")
                } else {
                    set("background", "var(--lumo-contrast-10pct)")
                    set("max-width", "85%")
                }
            }
        }
    }

    private fun createAIInputArea(onSend: (String) -> Unit): HorizontalLayout {
        val input = TextField().apply {
            placeholder = "Ask AI anything..."
            setWidthFull()
        }

        val sendButton = Button(Icon(VaadinIcon.PAPERPLANE)).apply {
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            addClickListener {
                val message = input.value
                if (message.isNotBlank()) {
                    onSend(message)
                    input.clear()
                }
            }
        }

        return HorizontalLayout(input, sendButton).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            expand(input)
            addClassName("ai-input-area")
            element.style.apply {
                set("padding", "var(--lumo-space-m)")
                set("border-top", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun toggleAIPanel() {
        aiPanelVisible = !aiPanelVisible
        if (aiPanelVisible) {
            element.appendChild(aiPanel.element)
            aiPanel.element.style.set("transform", "translateX(0)")
        } else {
            aiPanel.element.style.set("transform", "translateX(100%)")
        }
    }
}

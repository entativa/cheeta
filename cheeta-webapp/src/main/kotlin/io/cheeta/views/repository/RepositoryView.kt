package io.cheeta.views.repository

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility
import io.cheeta.MainLayout

@Route(value = "repositories", layout = MainLayout::class)
@PageTitle("Repositories | Cheeta")
class RepositoryView : VerticalLayout() {

    init {
        setSizeFull()
        isPadding = true
        addClassName("repository-view")

        add(
            createHeader(),
            createFilters(),
            createRepositoryList()
        )
    }

    private fun createHeader(): HorizontalLayout {
        val title = H1("Repositories").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val newRepoButton = Button("New Repository", Icon(VaadinIcon.PLUS)).apply {
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        }

        return HorizontalLayout(title, newRepoButton).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("section-header")
        }
    }

    private fun createFilters(): HorizontalLayout {
        val searchField = TextField().apply {
            placeholder = "Find a repository..."
            prefixComponent = Icon(VaadinIcon.SEARCH)
            setWidthFull()
        }

        val typeFilter = Button("Type", Icon(VaadinIcon.CHEVRON_DOWN)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        }

        val languageFilter = Button("Language", Icon(VaadinIcon.CHEVRON_DOWN)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        }

        val sortButton = Button("Sort: Recently updated", Icon(VaadinIcon.CHEVRON_DOWN)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        }

        return HorizontalLayout(
            searchField,
            typeFilter,
            languageFilter,
            sortButton
        ).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            expand(searchField)
            addClassName("filters-bar")
        }
    }

    private fun createRepositoryList(): VerticalLayout {
        return VerticalLayout(
            createRepoCard(
                "cheeta-webapp",
                "yourusername/cheeta-webapp",
                "Modern web interface for Cheeta platform built with Vaadin and Kotlin",
                listOf("Kotlin", "Vaadin", "Spring Boot"),
                23, 5, 12, "main", true, "2 hours ago"
            ),
            createRepoCard(
                "ai-experiments",
                "yourusername/ai-experiments",
                "Testing various AI models and integrations for code analysis",
                listOf("Python", "TensorFlow", "OpenAI"),
                8, 2, 45, "dev", false, "1 day ago"
            ),
            createRepoCard(
                "my-portfolio",
                "yourusername/my-portfolio",
                "Personal portfolio website showcasing projects and experience",
                listOf("TypeScript", "React", "Next.js"),
                15, 3, 7, "main", true, "3 days ago"
            ),
            createRepoCard(
                "kotlin-utils",
                "yourusername/kotlin-utils",
                "Collection of useful Kotlin extension functions and utilities",
                listOf("Kotlin"),
                42, 8, 3, "main", true, "1 week ago"
            ),
            createRepoCard(
                "devops-scripts",
                "yourusername/devops-scripts",
                "Automation scripts for CI/CD and infrastructure management",
                listOf("Shell", "Python", "Docker"),
                19, 4, 28, "main", false, "2 weeks ago"
            )
        ).apply {
            setWidthFull()
            isPadding = false
        }
    }

    private fun createRepoCard(
        name: String,
        fullName: String,
        description: String,
        languages: List<String>,
        stars: Int,
        forks: Int,
        issues: Int,
        defaultBranch: String,
        isPublic: Boolean,
        lastUpdated: String
    ): VerticalLayout {
        val header = HorizontalLayout().apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER

            add(
                HorizontalLayout(
                    H3(name).apply {
                        addClassNames(LumoUtility.Margin.NONE)
                        element.style.set("color", "var(--lumo-primary-color)")
                        element.style.set("cursor", "pointer")
                    },
                    Span(if (isPublic) "Public" else "Private").apply {
                        element.style.apply {
                            set("padding", "2px 8px")
                            set("border-radius", "12px")
                            set("font-size", "12px")
                            set("border", "1px solid var(--lumo-contrast-20pct)")
                            set("margin-left", "8px")
                        }
                    }
                ).apply {
                    defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
                }
            )

            add(
                HorizontalLayout(
                    Button(Icon(VaadinIcon.STAR_O)).apply {
                        addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                        element.setAttribute("title", "Star")
                    },
                    Button(Icon(VaadinIcon.EYE)).apply {
                        addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                        element.setAttribute("title", "Watch")
                    },
                    Button(Icon(VaadinIcon.SPLIT)).apply {
                        addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                        element.setAttribute("title", "Fork")
                    }
                )
            )
        }

        val desc = Paragraph(description).apply {
            addClassNames(
                LumoUtility.TextColor.SECONDARY,
                LumoUtility.FontSize.SMALL
            )
            element.style.set("margin", "8px 0")
        }

        val languagesLayout = HorizontalLayout().apply {
            languages.forEach { lang ->
                add(Span(lang).apply {
                    element.style.apply {
                        set("padding", "4px 8px")
                        set("border-radius", "12px")
                        set("font-size", "12px")
                        set("background", "var(--lumo-contrast-10pct)")
                        set("margin-right", "8px")
                    }
                })
            }
        }

        val stats = HorizontalLayout(
            createStat(VaadinIcon.STAR, "$stars"),
            createStat(VaadinIcon.SPLIT, "$forks"),
            createStat(VaadinIcon.BUG, "$issues"),
            createBranchIndicator(defaultBranch),
            Span("Updated $lastUpdated").apply {
                addClassNames(
                    LumoUtility.FontSize.XSMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            }
        ).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassNames(
                LumoUtility.FontSize.XSMALL,
                LumoUtility.TextColor.SECONDARY
            )
        }

        return VerticalLayout(header, desc, languagesLayout, stats).apply {
            setWidthFull()
            addClassName("repo-card")
            element.style.apply {
                set("background", "var(--lumo-base-color)")
                set("border", "1px solid var(--lumo-contrast-10pct)")
                set("border-radius", "8px")
                set("padding", "20px")
                set("margin-bottom", "12px")
                set("cursor", "pointer")
                set("transition", "border-color 0.2s")
            }
            element.addEventListener("mouseenter") {
                element.style.set("border-color", "var(--lumo-primary-color)")
            }
            element.addEventListener("mouseleave") {
                element.style.set("border-color", "var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun createStat(icon: VaadinIcon, value: String): HorizontalLayout {
        return HorizontalLayout(
            Icon(icon),
            Span(value)
        ).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }
    }

    private fun createBranchIndicator(branch: String): HorizontalLayout {
        return HorizontalLayout(
            Icon(VaadinIcon.CODE_BRANCH),
            Span(branch)
        ).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }
    }
}

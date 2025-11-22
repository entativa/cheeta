package io.cheeta.views.dashboard

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility
import io.cheeta.MainLayout

@Route(value = "", layout = MainLayout::class)
@PageTitle("Dashboard | Cheeta")
class DashboardView : VerticalLayout() {

    init {
        setSizeFull()
        isPadding = true
        addClassName("dashboard-view")

        add(
            createHeader(),
            createDashboardGrid(),
            createRepositoriesSection()
        )
    }

    private fun createHeader(): HorizontalLayout {
        val title = H1("Dashboard").apply {
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

    private fun createDashboardGrid(): HorizontalLayout {
        return HorizontalLayout(
            createActivityCard(),
            createCICDCard(),
            createTrendingCard()
        ).apply {
            setWidthFull()
            addClassName("dashboard-grid")
        }
    }

    private fun createActivityCard(): VerticalLayout {
        val card = VerticalLayout().apply {
            addClassName("card")
            setWidthFull()
        }

        card.add(
            H3("Recent Activity").apply { 
                addClassNames(LumoUtility.Margin.NONE) 
            },
            createActivityItem(VaadinIcon.SPLIT, "Merged PR #42 in cheeta-webapp", "2 hours ago"),
            createActivityItem(VaadinIcon.COMMENT, "Commented on issue #156", "5 hours ago"),
            createActivityItem(VaadinIcon.STAR, "Starred awesome-kotlin", "1 day ago")
        )

        return card
    }

    private fun createCICDCard(): VerticalLayout {
        val card = VerticalLayout().apply {
            addClassName("card")
            setWidthFull()
        }

        card.add(
            H3("CI/CD Status").apply { 
                addClassNames(LumoUtility.Margin.NONE) 
            },
            createBuildStatus(VaadinIcon.CHECK_CIRCLE, "cheeta-webapp: Build passed", "main branch • 10 min ago", "success"),
            createBuildStatus(VaadinIcon.WARNING, "ai-experiments: Tests failing", "dev branch • 1 hour ago", "warning")
        )

        return card
    }

    private fun createTrendingCard(): VerticalLayout {
        val card = VerticalLayout().apply {
            addClassName("card")
            setWidthFull()
        }

        card.add(
            H3("Trending in Your Network").apply { 
                addClassNames(LumoUtility.Margin.NONE) 
            },
            createActivityItem(VaadinIcon.FIRE, "awesome-vaadin-components", "234 stars this week"),
            createActivityItem(VaadinIcon.ROCKET, "kotlin-ai-toolkit", "89 stars this week")
        )

        return card
    }

    private fun createActivityItem(icon: VaadinIcon, title: String, meta: String): HorizontalLayout {
        val iconDiv = Div().apply {
            add(Icon(icon))
            addClassName("activity-icon")
            element.style.apply {
                set("width", "32px")
                set("height", "32px")
                set("border-radius", "50%")
                set("background", "var(--lumo-contrast-10pct)")
                set("display", "flex")
                set("align-items", "center")
                set("justify-content", "center")
            }
        }

        val content = VerticalLayout().apply {
            isPadding = false
            spacing = false
            add(
                Span(title).apply {
                    addClassName(LumoUtility.FontSize.SMALL)
                },
                Span(meta).apply {
                    addClassNames(
                        LumoUtility.FontSize.XSMALL,
                        LumoUtility.TextColor.SECONDARY
                    )
                }
            )
        }

        return HorizontalLayout(iconDiv, content).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("activity-item")
            element.style.apply {
                set("padding", "var(--lumo-space-s) 0")
                set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun createBuildStatus(icon: VaadinIcon, title: String, meta: String, status: String): HorizontalLayout {
        val iconDiv = Div().apply {
            add(Icon(icon))
            addClassName("activity-icon")
            element.style.apply {
                set("width", "32px")
                set("height", "32px")
                set("border-radius", "50%")
                set("display", "flex")
                set("align-items", "center")
                set("justify-content", "center")
                when (status) {
                    "success" -> set("background", "var(--lumo-success-color)")
                    "warning" -> set("background", "var(--lumo-warning-color)")
                    else -> set("background", "var(--lumo-contrast-10pct)")
                }
            }
        }

        val content = VerticalLayout().apply {
            isPadding = false
            spacing = false
            add(
                Span(title).apply {
                    addClassName(LumoUtility.FontSize.SMALL)
                },
                Span(meta).apply {
                    addClassNames(
                        LumoUtility.FontSize.XSMALL,
                        LumoUtility.TextColor.SECONDARY
                    )
                }
            )
        }

        return HorizontalLayout(iconDiv, content).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("activity-item")
            element.style.apply {
                set("padding", "var(--lumo-space-s) 0")
                set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun createRepositoriesSection(): VerticalLayout {
        val header = HorizontalLayout(
            H2("Your Repositories").apply {
                addClassNames(LumoUtility.Margin.NONE)
            },
            Button("View All").apply {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            }
        ).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("section-header")
        }

        return VerticalLayout(
            header,
            createRepoCard(
                "cheeta-webapp",
                "Modern web interface for Cheeta platform",
                listOf("Kotlin", "Vaadin"),
                23, 5, "2 hours ago"
            ),
            createRepoCard(
                "ai-experiments",
                "Testing various AI models and integrations",
                listOf("Python", "AI/ML"),
                8, 2, "1 day ago"
            )
        ).apply {
            setWidthFull()
            isPadding = false
            spacing = false
        }
    }

    private fun createRepoCard(
        name: String,
        description: String,
        tags: List<String>,
        stars: Int,
        forks: Int,
        updated: String
    ): VerticalLayout {
        val header = HorizontalLayout(
            VerticalLayout(
                H3(name).apply {
                    addClassNames(LumoUtility.Margin.NONE)
                    element.style.set("color", "var(--lumo-primary-color)")
                },
                Span(description).apply {
                    addClassNames(
                        LumoUtility.FontSize.SMALL,
                        LumoUtility.TextColor.SECONDARY
                    )
                }
            ).apply {
                isPadding = false
                spacing = false
            },
            Button("Open").apply {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            }
        ).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.START
        }

        val tagsLayout = HorizontalLayout().apply {
            tags.forEach { tag ->
                add(Span(tag).apply {
                    addClassName("tag")
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
            createStat(VaadinIcon.STAR, stars.toString()),
            createStat(VaadinIcon.SPLIT, forks.toString()),
            Span("Updated $updated").apply {
                addClassNames(
                    LumoUtility.FontSize.XSMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            }
        ).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }

        return VerticalLayout(header, tagsLayout, stats).apply {
            addClassName("repo-card")
            setWidthFull()
            element.style.apply {
                set("background", "var(--lumo-base-color)")
                set("border", "1px solid var(--lumo-contrast-10pct)")
                set("border-radius", "8px")
                set("padding", "16px")
                set("margin-bottom", "12px")
                set("cursor", "pointer")
            }
        }
    }

    private fun createStat(icon: VaadinIcon, value: String): HorizontalLayout {
        return HorizontalLayout(
            Icon(icon).apply {
                addClassNames(LumoUtility.IconSize.SMALL)
            },
            Span(value)
        ).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassNames(
                LumoUtility.FontSize.XSMALL,
                LumoUtility.TextColor.SECONDARY
            )
        }
    }
}

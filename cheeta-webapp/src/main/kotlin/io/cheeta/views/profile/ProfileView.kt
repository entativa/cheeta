package io.cheeta.views.profile

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility
import io.cheeta.MainLayout

@Route(value = "profile", layout = MainLayout::class)
@PageTitle("Profile | Cheeta")
class ProfileView : VerticalLayout() {

    init {
        setSizeFull()
        isPadding = true
        addClassName("profile-view")

        add(
            createProfileHeader(),
            createTabs(),
            createProfileContent()
        )
    }

    private fun createProfileHeader(): VerticalLayout {
        val avatar = Div().apply {
            element.style.apply {
                set("width", "120px")
                set("height", "120px")
                set("border-radius", "50%")
                set("background", "linear-gradient(135deg, #667eea, #764ba2)")
                set("margin-bottom", "16px")
            }
        }

        val name = H1("Your Name").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val username = Span("@yourusername").apply {
            addClassNames(
                LumoUtility.TextColor.SECONDARY,
                LumoUtility.FontSize.LARGE
            )
        }

        val bio = Paragraph(
            "Full-stack developer passionate about building developer tools. " +
            "Working on Cheeta - the next-gen DevOps platform."
        ).apply {
            element.style.apply {
                set("max-width", "600px")
                set("text-align", "center")
            }
        }

        val stats = HorizontalLayout(
            createProfileStat("234", "Contributions"),
            createProfileStat("42", "Repositories"),
            createProfileStat("156", "Stars"),
            createProfileStat("89", "Followers"),
            createProfileStat("67", "Following")
        ).apply {
            addClassName("profile-stats")
        }

        val actions = HorizontalLayout(
            Button("Edit Profile", Icon(VaadinIcon.EDIT)).apply {
                addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            },
            Button("Share Profile", Icon(VaadinIcon.SHARE)).apply {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            }
        )

        val availability = Span("🟢 Open to opportunities").apply {
            element.style.apply {
                set("padding", "8px 16px")
                set("border-radius", "20px")
                set("background", "var(--lumo-success-color-10pct)")
                set("color", "var(--lumo-success-color)")
                set("font-weight", "500")
            }
        }

        return VerticalLayout(
            avatar,
            name,
            username,
            availability,
            bio,
            stats,
            actions
        ).apply {
            setWidthFull()
            defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("profile-header")
            element.style.apply {
                set("background", "var(--lumo-contrast-5pct)")
                set("border-radius", "8px")
                set("padding", "32px")
                set("margin-bottom", "24px")
            }
        }
    }

    private fun createProfileStat(value: String, label: String): VerticalLayout {
        return VerticalLayout(
            H2(value).apply {
                addClassNames(LumoUtility.Margin.NONE)
            },
            Span(label).apply {
                addClassNames(
                    LumoUtility.FontSize.SMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            }
        ).apply {
            defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
            isPadding = false
            spacing = false
            element.style.set("min-width", "100px")
        }
    }

    private fun createTabs(): Tabs {
        return Tabs(
            Tab(Icon(VaadinIcon.CHART_LINE), Span("Overview")),
            Tab(Icon(VaadinIcon.CODE), Span("Repositories")),
            Tab(Icon(VaadinIcon.CLOCK), Span("Activity")),
            Tab(Icon(VaadinIcon.STAR), Span("Stars")),
            Tab(Icon(VaadinIcon.TROPHY), Span("Achievements"))
        ).apply {
            selectedIndex = 0
            setWidthFull()
        }
    }

    private fun createProfileContent(): HorizontalLayout {
        val mainContent = VerticalLayout(
            createContributionGraph(),
            createRecentActivity(),
            createPinnedRepos()
        ).apply {
            setWidthFull()
        }

        val sidebar = VerticalLayout(
            createSkillsCard(),
            createExperienceCard(),
            createLinksCard()
        ).apply {
            width = "350px"
        }

        return HorizontalLayout(mainContent, sidebar).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.START
            expand(mainContent)
        }
    }

    private fun createContributionGraph(): VerticalLayout {
        val title = H2("Contribution Activity").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val graph = Div().apply {
            element.style.apply {
                set("height", "150px")
                set("background", "var(--lumo-contrast-5pct)")
                set("border-radius", "8px")
                set("display", "flex")
                set("align-items", "center")
                set("justify-content", "center")
                set("margin-top", "16px")
            }
            add(Span("📊 Contribution graph visualization").apply {
                addClassName(LumoUtility.TextColor.SECONDARY)
            })
        }

        val summary = Span("234 contributions in the last year").apply {
            addClassNames(
                LumoUtility.FontSize.SMALL,
                LumoUtility.TextColor.SECONDARY
            )
            element.style.set("margin-top", "8px")
        }

        return VerticalLayout(title, graph, summary).apply {
            setWidthFull()
            addClassName("card")
        }
    }

    private fun createRecentActivity(): VerticalLayout {
        val title = H2("Recent Activity").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        return VerticalLayout(
            title,
            createActivityItem("Merged pull request", "cheeta-webapp#42", "2 hours ago"),
            createActivityItem("Opened issue", "kotlin-utils#15", "5 hours ago"),
            createActivityItem("Starred repository", "awesome-kotlin", "1 day ago"),
            createActivityItem("Created repository", "devops-scripts", "2 days ago"),
            createActivityItem("Commented on", "ai-experiments#8", "3 days ago")
        ).apply {
            setWidthFull()
            addClassName("card")
        }
    }

    private fun createActivityItem(action: String, target: String, time: String): HorizontalLayout {
        val icon = Div().apply {
            element.style.apply {
                set("width", "32px")
                set("height", "32px")
                set("border-radius", "50%")
                set("background", "var(--lumo-contrast-10pct)")
                set("flex-shrink", "0")
            }
        }

        val content = VerticalLayout(
            Span("$action in $target").apply {
                addClassName(LumoUtility.FontSize.SMALL)
            },
            Span(time).apply {
                addClassNames(
                    LumoUtility.FontSize.XSMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            }
        ).apply {
            isPadding = false
            spacing = false
        }

        return HorizontalLayout(icon, content).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            element.style.apply {
                set("padding", "8px 0")
                set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun createPinnedRepos(): VerticalLayout {
        val header = HorizontalLayout(
            H2("Pinned Repositories").apply {
                addClassNames(LumoUtility.Margin.NONE)
            },
            Button("Customize").apply {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
            }
        ).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }

        return VerticalLayout(
            header,
            createPinnedRepoCard("cheeta-webapp", "Modern DevOps platform UI", "Kotlin", 23),
            createPinnedRepoCard("ai-experiments", "AI model integrations", "Python", 8),
            createPinnedRepoCard("kotlin-utils", "Kotlin utility library", "Kotlin", 42)
        ).apply {
            setWidthFull()
            addClassName("card")
        }
    }

    private fun createPinnedRepoCard(name: String, description: String, language: String, stars: Int): VerticalLayout {
        return VerticalLayout(
            H3(name).apply {
                addClassNames(LumoUtility.Margin.NONE)
                element.style.set("color", "var(--lumo-primary-color)")
            },
            Span(description).apply {
                addClassNames(
                    LumoUtility.FontSize.SMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            },
            HorizontalLayout(
                Span(language).apply {
                    element.style.apply {
                        set("padding", "4px 8px")
                        set("border-radius", "12px")
                        set("font-size", "12px")
                        set("background", "var(--lumo-contrast-10pct)")
                    }
                },
                HorizontalLayout(
                    Icon(VaadinIcon.STAR),
                    Span(stars.toString())
                ).apply {
                    defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
                    addClassNames(
                        LumoUtility.FontSize.SMALL,
                        LumoUtility.TextColor.SECONDARY
                    )
                }
            )
        ).apply {
            setWidthFull()
            element.style.apply {
                set("padding", "12px")
                set("border", "1px solid var(--lumo-contrast-10pct)")
                set("border-radius", "8px")
                set("margin-top", "8px")
            }
        }
    }

    private fun createSkillsCard(): VerticalLayout {
        val title = H3("Skills & Expertise").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val skills = HorizontalLayout().apply {
            setWidthFull()
            element.style.set("flex-wrap", "wrap")
            listOf(
                "Kotlin", "Java", "Python", "TypeScript",
                "Spring Boot", "Vaadin", "React", "Docker",
                "Kubernetes", "AWS", "PostgreSQL", "Git"
            ).forEach { skill ->
                add(Span(skill).apply {
                    element.style.apply {
                        set("padding", "6px 12px")
                        set("border-radius", "12px")
                        set("font-size", "12px")
                        set("background", "var(--lumo-primary-color-10pct)")
                        set("color", "var(--lumo-primary-color)")
                        set("margin", "4px")
                    }
                })
            }
        }

        return VerticalLayout(title, skills).apply {
            addClassName("card")
        }
    }

    private fun createExperienceCard(): VerticalLayout {
        val title = H3("Experience").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        return VerticalLayout(
            title,
            createExperienceItem("Senior Developer", "Current Company", "2022 - Present"),
            createExperienceItem("Full Stack Developer", "Previous Company", "2019 - 2022"),
            createExperienceItem("Junior Developer", "First Company", "2017 - 2019")
        ).apply {
            addClassName("card")
        }
    }

    private fun createExperienceItem(role: String, company: String, period: String): VerticalLayout {
        return VerticalLayout(
            Span(role).apply {
                addClassName(LumoUtility.FontWeight.SEMIBOLD)
            },
            Span(company).apply {
                addClassName(LumoUtility.FontSize.SMALL)
            },
            Span(period).apply {
                addClassNames(
                    LumoUtility.FontSize.XSMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            }
        ).apply {
            isPadding = false
            spacing = false
            element.style.apply {
                set("padding", "8px 0")
                set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
            }
        }
    }

    private fun createLinksCard(): VerticalLayout {
        val title = H3("Links").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        return VerticalLayout(
            title,
            createLink(VaadinIcon.GLOBE, "Portfolio", "yourportfolio.com"),
            createLink(VaadinIcon.TWITTER, "Twitter", "@yourusername"),
            createLink(VaadinIcon.LINK, "LinkedIn", "linkedin.com/in/you"),
            createLink(VaadinIcon.ENVELOPE, "Email", "you@example.com")
        ).apply {
            addClassName("card")
        }
    }

    private fun createLink(icon: VaadinIcon, label: String, value: String): HorizontalLayout {
        return HorizontalLayout(
            Icon(icon),
            VerticalLayout(
                Span(label).apply {
                    addClassName(LumoUtility.FontSize.SMALL)
                },
                Span(value).apply {
                    addClassNames(
                        LumoUtility.FontSize.XSMALL,
                        LumoUtility.TextColor.SECONDARY
                    )
                }
            ).apply {
                isPadding = false
                spacing = false
            }
        ).apply {
            setWidthFull()
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            element.style.apply {
                set("padding", "8px")
                set("cursor", "pointer")
            }
        }
    }
}

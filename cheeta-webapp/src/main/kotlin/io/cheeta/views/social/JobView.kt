package io.cheeta.views.social

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

@Route(value = "jobs", layout = MainLayout::class)
@PageTitle("Jobs | Cheeta")
class JobsView : VerticalLayout() {

    init {
        setSizeFull()
        isPadding = true
        addClassName("jobs-view")

        add(
            createHeader(),
            createTabs(),
            createJobsList()
        )
    }

    private fun createHeader(): HorizontalLayout {
        val title = H1("Job Board").apply {
            addClassNames(LumoUtility.Margin.NONE)
        }

        val savedJobsButton = Button("Saved Jobs", Icon(VaadinIcon.BOOKMARK)).apply {
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        }

        val postJobButton = Button("Post a Job", Icon(VaadinIcon.PLUS)).apply {
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        }

        return HorizontalLayout(title, savedJobsButton, postJobButton).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            addClassName("section-header")
        }
    }

    private fun createTabs(): Tabs {
        return Tabs(
            Tab(Icon(VaadinIcon.FIRE), Span("Featured")),
            Tab(Icon(VaadinIcon.BRIEFCASE), Span("All Jobs")),
            Tab(Icon(VaadinIcon.BUILDING), Span("Companies")),
            Tab(Icon(VaadinIcon.USER_CHECK), Span("My Applications"))
        ).apply {
            selectedIndex = 0
        }
    }

    private fun createJobsList(): VerticalLayout {
        return VerticalLayout(
            createJobCard(
                "Acme Corp",
                true,
                "10,000+ employees · Tech",
                "Senior Backend Engineer (Kotlin)",
                "$150K - $200K",
                "Remote",
                "Full-time",
                "92% Match",
                listOf("Kotlin", "Spring Boot", "Kubernetes", "PostgreSQL"),
                "2 days ago",
                23,
                true
            ),
            createJobCard(
                "StartupXYZ",
                true,
                "50-200 employees · SaaS",
                "Frontend Developer (React/TypeScript)",
                "$100K - $140K",
                "NYC / Remote",
                "Full-time",
                "78% Match",
                listOf("React", "TypeScript", "GraphQL"),
                "1 week ago",
                45,
                false
            ),
            createJobCard(
                "InnovateCo",
                true,
                "200-500 employees · FinTech",
                "DevOps Engineer",
                "$130K - $170K",
                "Remote",
                "Full-time",
                "85% Match",
                listOf("Kubernetes", "AWS", "Terraform", "Python"),
                "3 days ago",
                31,
                false
            ),
            createJobCard(
                "TechGiants Inc",
                true,
                "50,000+ employees · Enterprise",
                "Full Stack Engineer",
                "$140K - $180K",
                "San Francisco",
                "Full-time",
                "88% Match",
                listOf("Java", "React", "Microservices", "Docker"),
                "5 days ago",
                67,
                false
            )
        ).apply {
            setWidthFull()
            isPadding = false
        }
    }

    private fun createJobCard(
        companyName: String,
        isVerified: Boolean,
        companyInfo: String,
        jobTitle: String,
        salary: String,
        location: String,
        type: String,
        matchPercentage: String,
        skills: List<String>,
        postedTime: String,
        applicants: Int,
        isFeatured: Boolean
    ): VerticalLayout {
        val companyLogo = Div().apply {
            element.style.apply {
                set("width", "48px")
                set("height", "48px")
                set("border-radius", "8px")
                set("background", "linear-gradient(135deg, #667eea, #764ba2)")
            }
        }

        val companyNameSpan = Span(companyName).apply {
            addClassName(LumoUtility.FontWeight.SEMIBOLD)
        }

        if (isVerified) {
            companyNameSpan.element.insertChild(
                1,
                Span(" ✓").apply {
                    element.style.set("color", "var(--lumo-primary-color)")
                }.element
            )
        }

        val companyHeader = HorizontalLayout(
            companyLogo,
            VerticalLayout(
                companyNameSpan,
                Span(companyInfo).apply {
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
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }

        val title = H3(jobTitle).apply {
            addClassNames(LumoUtility.Margin.NONE)
            element.style.set("margin-top", "12px")
        }

        val details = HorizontalLayout(
            createDetail("💰", salary),
            createDetail("📍", location),
            createDetail("⏰", type),
            createMatchBadge(matchPercentage)
        ).apply {
            addClassNames(
                LumoUtility.FontSize.SMALL,
                LumoUtility.TextColor.SECONDARY
            )
        }

        val skillsLayout = HorizontalLayout().apply {
            skills.forEach { skill ->
                add(Span(skill).apply {
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

        val footer = HorizontalLayout(
            Span("Posted $postedTime · $applicants applicants").apply {
                addClassNames(
                    LumoUtility.FontSize.SMALL,
                    LumoUtility.TextColor.SECONDARY
                )
            },
            Button("Apply Now").apply {
                addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            }
        ).apply {
            setWidthFull()
            justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        }

        val card = VerticalLayout(companyHeader, title, details, skillsLayout, footer).apply {
            setWidthFull()
            addClassName("job-card")
            element.style.apply {
                set("background", "var(--lumo-base-color)")
                set("border", "1px solid var(--lumo-contrast-10pct)")
                set("border-radius", "8px")
                set("padding", "20px")
                set("margin-bottom", "16px")
                set("position", "relative")
                if (isFeatured) {
                    set("border-color", "var(--lumo-warning-color)")
                }
            }
        }

        if (isFeatured) {
            val featuredBadge = Span("⭐ FEATURED").apply {
                element.style.apply {
                    set("position", "absolute")
                    set("top", "-10px")
                    set("left", "20px")
                    set("background", "var(--lumo-warning-color)")
                    set("color", "var(--lumo-base-color)")
                    set("padding", "4px 12px")
                    set("border-radius", "12px")
                    set("font-size", "12px")
                    set("font-weight", "600")
                }
            }
            card.element.insertChild(0, featuredBadge.element)
        }

        return card
    }

    private fun createDetail(emoji: String, text: String): Span {
        return Span("$emoji $text").apply {
            element.style.set("margin-right", "12px")
        }
    }

    private fun createMatchBadge(percentage: String): Span {
        return Span("✅ $percentage").apply {
            element.style.apply {
                set("padding", "2px 8px")
                set("border-radius", "12px")
                set("background", "var(--lumo-success-color-10pct)")
                set("color", "var(--lumo-success-color)")
            }
        }
    }
}

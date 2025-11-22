package io.cheeta

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableAsync
@EnableScheduling
@Theme(value = "cheeta", variant = Lumo.DARK)
@PWA(
    name = "Cheeta Platform",
    shortName = "Cheeta",
    description = "AI-powered DevOps platform for modern teams",
    iconPath = "icons/icon.png",
    backgroundColor = "#0d1117",
    themeColor = "#1f6feb",
    offlinePath = "offline.html",
    offlineResources = ["images/offline.svg"]
)
@Push
class Application : SpringBootServletInitializer(), AppShellConfigurator {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

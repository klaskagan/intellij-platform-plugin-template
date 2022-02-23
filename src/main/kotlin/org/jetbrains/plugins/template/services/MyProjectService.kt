package org.jetbrains.plugins.template.services

import com.intellij.openapi.project.Project
import org.jetbrains.plugins.template.Constants.PROJECT_PROPERTY_KEY
import org.jetbrains.plugins.template.MyBundle
import org.jetbrains.plugins.template.providers.ProjectVersionFromPOMProvider
import java.util.*

class MyProjectService(project: Project) {

    private val projectVersionFromPOMProvider: ProjectVersionFromPOMProvider
    private val properties = Properties()

    init {
        println(MyBundle.message("projectService", project.name))
        projectVersionFromPOMProvider = ProjectVersionFromPOMProvider(project)
    }

    fun updateProjectVersion(project: Project) {
        val projectVersion = projectVersionFromPOMProvider.getProjectVersion(project)
        projectVersion?.also {
            addProperty(PROJECT_PROPERTY_KEY, it)
        }
    }

    fun addProperty(key: String?, value: Any?) {
        this.properties[key] = value
    }

    fun getProperties(): Properties {
        return properties
    }
}

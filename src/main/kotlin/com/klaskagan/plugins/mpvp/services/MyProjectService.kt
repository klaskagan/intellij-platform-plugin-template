package com.klaskagan.plugins.mpvp.services

import com.intellij.openapi.project.Project
import com.klaskagan.plugins.mpvp.Constants.PROJECT_PROPERTY_KEY
import com.klaskagan.plugins.mpvp.MyBundle
import com.klaskagan.plugins.mpvp.providers.ProjectVersionFromPOMProvider
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

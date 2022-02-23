package com.klaskagan.plugins.mpvp.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.klaskagan.plugins.mpvp.services.MyProjectService

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        val service = project.service<MyProjectService>()
        service.updateProjectVersion(project)
    }
}

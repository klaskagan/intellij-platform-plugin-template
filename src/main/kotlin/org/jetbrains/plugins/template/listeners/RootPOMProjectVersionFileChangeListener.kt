package org.jetbrains.plugins.template.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import org.jetbrains.plugins.template.Constants.POM_XML
import org.jetbrains.plugins.template.services.MyProjectService


/**
 * @author Viktoras Baracevicius
 */
class RootPOMProjectVersionFileChangeListener : BulkFileListener {

    override fun after(events: List<VFileEvent?>) {
        events.filter { it?.file?.canonicalPath != null }
            .filter { it!!.file!!.name == POM_XML }
            .filter { it!!.file!!.canonicalPath == getRootPOMCanonicalPath(it) }
            .forEach {
                when (it) {
                    is VFileCreateEvent -> handleContentsChanged(it)
                    is VFileContentChangeEvent -> handleContentsChanged(it)
                }
            }
    }

    private fun getRootPOMCanonicalPath(event: VFileEvent): String? {
        return findProjectForCurrentFile(event)
            ?.let {
                val rootDir = it.guessProjectDir()
                rootDir?.findChild(POM_XML)?.canonicalPath
            }
    }

    private fun handleContentsChanged(event: VFileEvent) {
        findProjectForCurrentFile(event)
            ?.let { project ->
                project.service<MyProjectService>().updateProjectVersion(project)
            }
    }

    private fun findProjectForCurrentFile(event: VFileEvent): Project? {
        return ProjectManager.getInstance().openProjects
            .find { fileBelongsToCurrentProject(event.file!!.canonicalPath!!, it) }
    }

    private fun fileBelongsToCurrentProject(
        canonicalPath: String,
        project: Project
    ) = canonicalPath.contains(project.name)

}

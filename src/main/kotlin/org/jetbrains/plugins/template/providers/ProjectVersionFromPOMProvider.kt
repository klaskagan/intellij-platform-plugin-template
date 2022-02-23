package org.jetbrains.plugins.template.providers

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.lang.StringUtils
import org.jetbrains.plugins.template.Constants.PARENT
import org.jetbrains.plugins.template.Constants.PARENT_END
import org.jetbrains.plugins.template.Constants.POM_XML
import org.jetbrains.plugins.template.Constants.REGEX
import org.jetbrains.plugins.template.Constants.VERSION
import org.jetbrains.plugins.template.Constants.VERSION_END

class ProjectVersionFromPOMProvider(private val project: Project) {

    fun getProjectVersion(project: Project = this.project): String? {
        try {
            val pomFile = project.guessProjectDir()?.findChild(POM_XML)
            return resolveProjectVersion(pomFile)?.replace(REGEX.toRegex(),StringUtils.EMPTY)
        } catch (e: Exception) {
            // ignore...
        }
        return null
    }

    private fun resolveProjectVersion(pomFile: VirtualFile?): String? {
        var projectVersion: String? = null
        try {
            var pomAsString = String(pomFile!!.contentsToByteArray())
            if (StringUtil.containsIgnoreCase(pomAsString, PARENT) && StringUtil.containsIgnoreCase(
                    pomAsString,
                    PARENT_END
                )
            ) {
                pomAsString = (pomAsString.substring(0, StringUtil.indexOfIgnoreCase(pomAsString, PARENT, 0))
                        + pomAsString.substring(StringUtil.indexOfIgnoreCase(pomAsString, PARENT_END, 0)))
            }
            if (StringUtil.containsIgnoreCase(pomAsString, VERSION) && StringUtil.containsIgnoreCase(
                    pomAsString,
                    VERSION_END
                )
            ) {
                projectVersion = pomAsString.substring(
                    StringUtil.indexOfIgnoreCase(pomAsString, VERSION, 0)
                            + VERSION.length, StringUtil.indexOfIgnoreCase(pomAsString, VERSION_END, 0)
                ).trim { it <= ' ' }
            }
            if ((StringUtils.EMPTY == projectVersion)) {
                projectVersion = null
            }
        } catch (e: Exception) {
            // ignore...
        }
        return projectVersion
    }

}


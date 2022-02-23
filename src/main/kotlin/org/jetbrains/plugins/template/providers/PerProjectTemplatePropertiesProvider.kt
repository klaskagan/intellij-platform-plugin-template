package org.jetbrains.plugins.template.providers

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.openapi.components.service
import com.intellij.psi.PsiDirectory
import org.jetbrains.plugins.template.services.MyProjectService
import java.util.*

/**
 * @author Viktoras Baracevicius
 */
class PerProjectTemplatePropertiesProvider : DefaultTemplatePropertiesProvider {

    override fun fillProperties(psiDirectory: PsiDirectory, properties: Properties) {
        val myProjectService = psiDirectory.project.service<MyProjectService>()
        properties.putAll(myProjectService.getProperties())
    }
}

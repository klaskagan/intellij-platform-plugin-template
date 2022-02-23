package com.klaskagan.plugins.mpvp.providers

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.openapi.components.service
import com.intellij.psi.PsiDirectory
import com.klaskagan.plugins.mpvp.services.MyProjectService
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

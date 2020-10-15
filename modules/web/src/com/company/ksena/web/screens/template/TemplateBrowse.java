package com.company.ksena.web.screens.template;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.template.Template;

@UiController("ksena_Template.browse")
@UiDescriptor("template-browse.xml")
@LookupComponent("templatesTable")
@LoadDataBeforeShow
public class TemplateBrowse extends StandardLookup<Template> {
}
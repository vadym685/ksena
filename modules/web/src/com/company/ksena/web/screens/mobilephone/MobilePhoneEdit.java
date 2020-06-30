package com.company.ksena.web.screens.mobilephone;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.MobilePhone;

@UiController("ksena_MobilePhone.edit")
@UiDescriptor("mobile-phone-edit.xml")
@EditedEntityContainer("mobilePhoneDc")
@LoadDataBeforeShow
public class MobilePhoneEdit extends StandardEditor<MobilePhone> {
}
package com.company.ksena.web.screens.mobilephone;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.MobilePhone;

@UiController("ksena_MobilePhone.browse")
@UiDescriptor("mobile-phone-browse.xml")
@LookupComponent("mobilePhonesTable")
@LoadDataBeforeShow
public class MobilePhoneBrowse extends StandardLookup<MobilePhone> {
}
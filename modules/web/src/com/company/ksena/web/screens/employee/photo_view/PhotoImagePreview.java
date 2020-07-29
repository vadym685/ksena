package com.company.ksena.web.screens.employee.photo_view;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("photoapp_PhotoImagePreview")
@UiDescriptor("photo-image-preview.xml")
@LoadDataBeforeShow
@EditedEntityContainer("photosDc")
public class PhotoImagePreview extends StandardEditor<FileDescriptor> {

    @Inject
    protected Image image;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    protected InstanceContainer<FileDescriptor> imageDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        image.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageDc.getItem());
    }
    
}
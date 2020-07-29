package com.company.ksena.web.screens.employee.photo_view;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.screen.MessageBundle;

public class PhotoPreviewComponentFactory {

    private final UiComponents uiComponents;
    private final MessageBundle messageBundle;

    public PhotoPreviewComponentFactory(UiComponents uiComponents, MessageBundle messageBundle) {
        this.uiComponents = uiComponents;
        this.messageBundle = messageBundle;
    }


    public Component create(FileDescriptor file) {
        GroupBoxLayout groupBoxLayout = uiComponents.create(GroupBoxLayout.class);

        groupBoxLayout.setShowAsPanel(true);
        groupBoxLayout.setWidthFull();
        groupBoxLayout.setHeightFull();
        groupBoxLayout.setStyleName("well");
        groupBoxLayout.setCaption(messageBundle.formatMessage("previewFile", file.getName()));
        if (isImage(file)){
            groupBoxLayout.add(photoImageComponent(file));
        }

        return groupBoxLayout;
    }

    private boolean isImage(FileDescriptor imageFile) {
        return imageFile.getExtension().contains("png")
                || imageFile.getExtension().contains("jpg")
                || imageFile.getExtension().contains("jpeg");
    }

    private Component photoImageComponent(FileDescriptor imageFile) {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.SCALE_DOWN);
        image.setAlignment(Component.Alignment.MIDDLE_CENTER);
        image.setWidthFull();
        image.setHeightFull();

        image.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageFile);

        return image;
    }

}

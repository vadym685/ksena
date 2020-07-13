package com.company.ksena.web.screens.employee;


import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UiController("ksena_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {

    @Inject
    private Screens screens;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadField upload;
    @Inject
    private Image image;
    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    protected InstanceContainer<Employee> employeeDc;
    @Inject
    protected CollectionPropertyContainer<FileDescriptor> imageFileDc;

    private List<FileDescriptor> newImageDescriptors = new ArrayList<>();


    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) throws FileStorageException {
        FileDescriptor imageDescriptor = upload.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(upload.getFileId(), imageDescriptor);
            FileDescriptor savedImageDescriptor = dataManager.commit(imageDescriptor);
            newImageDescriptors.add(savedImageDescriptor);
            imageFileDc.getMutableItems().add(savedImageDescriptor);
        } catch (Exception e) {

        }
      //  Set<FileDescriptor> selectedPhoto = event.;
    }




//    private void displayImage() {
//        if (getItem().getImageFile() != null) {
//            image.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getImageFile());
//            image.setVisible(true);
//        } else {
//            image.setVisible(false);
//        }}

//    @Subscribe("showUploadDialogBtn")
//    protected void onShowUploadDialogBtnClick(Button.ClickEvent event) {
//        FileUploadDialog dialog = (FileUploadDialog) screens.create("fileUploadDialog", OpenMode.DIALOG);
////        dialog.addCloseWithCommitListener(() -> {
////            UUID fileId = dialog.getFileId();
////            String fileName = dialog.getFileName();
////
////            File file = fileUploadingAPI.getFile(fileId);
////
////            FileDescriptor fileDescriptor = fileUploadingAPI.getFileDescriptor(fileId, fileName);
////            try {
////                fileUploadingAPI.putFileIntoStorage(fileId, fileDescriptor);
////                dataManager.commit(fileDescriptor);
////            } catch (FileStorageException e) {
////                throw new RuntimeException(e);
////            }
////        });
////        screens.show(dialog);
//    }
//    private void updateImageButtons(boolean enable) {
//        downloadImageBtn.setEnabled(enable);
//        clearImageBtn.setEnabled(enable);
//    }
//
//    private void displayImage() {
//        if (getItem().getImageFile() != null) {
//            image.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getImageFile());
//            image.setVisible(true);
//        } else {
//            image.setVisible(false);
//        }
//    }
}
package com.company.ksena.web.screens.employee;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.app.core.file.FileUploadDialog;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;

@UiController("ksena_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {

    @Inject
    private Screens screens;

    @Inject
    private Image image;

    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {

        System.out.print("");
    }

//    private void displayImage() {
//        if (getItem().getImageFile() != null) {
//            image.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getImageFile());
//            image.setVisible(true);
//        } else {
//            image.setVisible(false);
//        }}

    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadField upload;



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
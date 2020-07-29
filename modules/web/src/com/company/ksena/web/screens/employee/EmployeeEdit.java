package com.company.ksena.web.screens.employee;


import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.time.LocalDate;
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
    @Inject
    private DateField<LocalDate> residenceEndTimeField;


    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) throws FileStorageException {
        FileDescriptor imageDescriptor = upload.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(upload.getFileId(), imageDescriptor);
            FileDescriptor savedImageDescriptor = dataManager.commit(imageDescriptor);
            newImageDescriptors.add(savedImageDescriptor);
                   //imageFileDc.getMutableItems().add(savedImageDescriptor);
        } catch (Exception e) {

        }
      //  Set<FileDescriptor> selectedPhoto = event.;
    }

    @Subscribe("residencePermanentField")
    public void onResidencePermanentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getResidencePermanent() == false){
            residenceEndTimeField.setVisible(true);
        }
        else {
            residenceEndTimeField.setVisible(false);
        }
    }
}
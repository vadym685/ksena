package com.company.ksena.web.screens.employee;


import com.company.ksena.web.screens.employee.photo_view.PhotoPreviewComponentFactory;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.people.Employee;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@UiController("ksena_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {
//    @Inject
//    private MaskedField<String> phoneNumberField;

    @Inject
    private Screens screens;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadField upload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    protected InstanceContainer<Employee> employeeDc;
    @Inject
    private InstancePropertyContainer<FileDescriptor> imageFileDc;

    @Inject
    private HBoxLayout imageWrapperLayout;

    private List<FileDescriptor> newImageDescriptors = new ArrayList<>();
    @Inject
    private DateField<LocalDate> residenceEndTimeField;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private MaskedField<String> personalPhoneNumberField;
    @Inject
    private MaskedField<String> relativesPhoneNumberField;
//    @Inject
//    private MaskedField<String> phoneNumber2Field;
    @Inject
    private TextField<String> phoneNumberField;
    @Inject
    private TextField<String> phoneNumber2Field;

    @Subscribe("upload")
    protected void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        imageWrapperLayout.removeAll();
        FileDescriptor imageDescriptor = upload.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(upload.getFileId(), imageDescriptor);
            FileDescriptor savedImageDescriptor = dataManager.commit(imageDescriptor);
            newImageDescriptors.add(savedImageDescriptor);
            imageFileDc.setItem(savedImageDescriptor);
            imageWrapperLayout.add(photoImage(imageDescriptor));
        } catch (Exception e) {

        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        if (phoneNumberField.getValue() == null) {
            phoneNumberField.setValue("+420");

        }
        if (phoneNumber2Field.getValue() == null){
            phoneNumber2Field.setValue("+420");
        }
        if (personalPhoneNumberField.getValue() == null) {
            personalPhoneNumberField.setValue("+420");
        }
        if (relativesPhoneNumberField.getValue() == null) {
            relativesPhoneNumberField.setValue("+420");
        }

        System.out.println();
        try {
            if (Objects.nonNull(employeeDc.getItem().getImageFile())) {
                imageWrapperLayout.add(photoImage(imageFileDc.getItem()));
            }
        } catch (Exception e) {

        }
    }

    private Component photoImage(FileDescriptor file) {
        PhotoPreviewComponentFactory factory = new PhotoPreviewComponentFactory(
                uiComponents,
                messageBundle
        );

        return factory.create(file);
    }

    @Subscribe("residencePermanentField")
    public void onResidencePermanentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (this.getEditedEntity().getResidencePermanent() == false) {
            residenceEndTimeField.setVisible(true);
        } else {
            residenceEndTimeField.setVisible(false);
        }
    }

}
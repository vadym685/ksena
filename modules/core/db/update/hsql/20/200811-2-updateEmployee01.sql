alter table KSENA_EMPLOYEE add constraint FK_KSENA_EMPLOYEE_ON_IMAGE_FILE foreign key (IMAGE_FILE_ID) references SYS_FILE(ID);

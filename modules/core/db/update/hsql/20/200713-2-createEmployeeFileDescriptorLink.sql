alter table KSENA_EMPLOYEE_FILE_DESCRIPTOR_LINK add constraint FK_EMPFILDES_ON_EMPLOYEE foreign key (EMPLOYEE_ID) references KSENA_PASPORT_DATA(ID);
alter table KSENA_EMPLOYEE_FILE_DESCRIPTOR_LINK add constraint FK_EMPFILDES_ON_FILE_DESCRIPTOR foreign key (FILE_DESCRIPTOR_ID) references SYS_FILE(ID);
-- begin KSENA_PASPORT_DATA
alter table KSENA_PASPORT_DATA add constraint FK_KSENA_PASPORT_DATA_ON_MOBILE_PHONE foreign key (MOBILE_PHONE_ID) references KSENA_MOBILE_PHONE(ID)^
alter table KSENA_PASPORT_DATA add constraint FK_KSENA_PASPORT_DATA_ON_POINT foreign key (POINT_ID) references KSENA_COORDINATES(ID)^
create index IDX_KSENA_PASPORT_DATA_ON_MOBILE_PHONE on KSENA_PASPORT_DATA (MOBILE_PHONE_ID)^
create index IDX_KSENA_PASPORT_DATA_ON_POINT on KSENA_PASPORT_DATA (POINT_ID)^
-- end KSENA_PASPORT_DATA
-- begin KSENA_TASK_DOCUMENT
alter table KSENA_TASK_DOCUMENT add constraint FK_KSENA_TASK_DOCUMENT_ON_CLIENT foreign key (CLIENT_ID) references KSENA_PASPORT_DATA(ID)^
alter table KSENA_TASK_DOCUMENT add constraint FK_KSENA_TASK_DOCUMENT_ON_POINT foreign key (POINT_ID) references KSENA_COORDINATES(ID)^
create index IDX_KSENA_TASK_DOCUMENT_ON_CLIENT on KSENA_TASK_DOCUMENT (CLIENT_ID)^
create index IDX_KSENA_TASK_DOCUMENT_ON_POINT on KSENA_TASK_DOCUMENT (POINT_ID)^
-- end KSENA_TASK_DOCUMENT

-- begin KSENA_TASK_DOCUMENT_EMPLOYEE_LINK
alter table KSENA_TASK_DOCUMENT_EMPLOYEE_LINK add constraint FK_TASDOCEMP_ON_EMPLOYEE foreign key (EMPLOYEE_ID) references KSENA_PASPORT_DATA(ID)^
alter table KSENA_TASK_DOCUMENT_EMPLOYEE_LINK add constraint FK_TASDOCEMP_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID)^
-- end KSENA_TASK_DOCUMENT_EMPLOYEE_LINK
-- begin KSENA_TASK_DOCUMENT_INVENTORY_LINK
alter table KSENA_TASK_DOCUMENT_INVENTORY_LINK add constraint FK_TASDOCINV_ON_INVENTORY foreign key (INVENTORY_ID) references KSENA_INVENTORY(ID)^
alter table KSENA_TASK_DOCUMENT_INVENTORY_LINK add constraint FK_TASDOCINV_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID)^
-- end KSENA_TASK_DOCUMENT_INVENTORY_LINK
-- begin KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK
alter table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK add constraint FK_CLEPOSEXPMAT_ON_EXPENDABLE_MATERIAL foreign key (EXPENDABLE_MATERIAL_ID) references KSENA_EXPENDABLE_MATERIAL(ID)^
alter table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK add constraint FK_CLEPOSEXPMAT_ON_CLEANING_POSITION foreign key (CLEANING_POSITION_ID) references KSENA_CLEANING_POSITION(ID)^
-- end KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK
-- begin KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK
alter table KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK add constraint FK_TASDOCCLEPOS_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID)^
alter table KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK add constraint FK_TASDOCCLEPOS_ON_CLEANING_POSITION foreign key (CLEANING_POSITION_ID) references KSENA_CLEANING_POSITION(ID)^
-- end KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK
-- begin KSENA_MOBILE_PHONE
alter table KSENA_MOBILE_PHONE add constraint FK_KSENA_MOBILE_PHONE_ON_EMPLOYEE foreign key (EMPLOYEE_ID) references KSENA_PASPORT_DATA(ID)^
create index IDX_KSENA_MOBILE_PHONE_ON_EMPLOYEE on KSENA_MOBILE_PHONE (EMPLOYEE_ID)^
-- end KSENA_MOBILE_PHONE
-- begin KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK
alter table KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK add constraint FK_TASDOCDAYINT_ON_DAY_INTERVAL foreign key (DAY_INTERVAL_ID) references KSENA_DAY_INTERVAL(ID)^
alter table KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK add constraint FK_TASDOCDAYINT_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID)^
-- end KSENA_TASK_DOCUMENT_DAY_INTERVAL_LINK

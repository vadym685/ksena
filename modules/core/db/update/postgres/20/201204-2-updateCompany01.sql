alter table KSENA_COMPANY add constraint FK_KSENA_COMPANY_ON_FIELD_OF_ACTIVITY foreign key (FIELD_OF_ACTIVITY_ID) references KSENA_FIELD_OF_ACTIVITY(ID);
create index IDX_KSENA_COMPANY_ON_FIELD_OF_ACTIVITY on KSENA_COMPANY (FIELD_OF_ACTIVITY_ID);

alter table KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK add constraint FK_TASDOCCLEPOS_ON_TASK_DOCUMENT foreign key (TASK_DOCUMENT_ID) references KSENA_TASK_DOCUMENT(ID);
alter table KSENA_TASK_DOCUMENT_CLEANING_POSITION_LINK add constraint FK_TASDOCCLEPOS_ON_CLEANING_POSITION foreign key (CLEANING_POSITION_ID) references KSENA_CLEANING_POSITION(ID);
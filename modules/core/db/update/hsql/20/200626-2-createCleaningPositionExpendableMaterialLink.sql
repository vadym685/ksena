alter table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK add constraint FK_CLEPOSEXPMAT_ON_EXPENDABLE_MATERIAL foreign key (EXPENDABLE_MATERIAL_ID) references KSENA_EXPENDABLE_MATERIAL(ID);
alter table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK add constraint FK_CLEPOSEXPMAT_ON_CLEANING_POSITION foreign key (CLEANING_POSITION_ID) references KSENA_CLEANING_POSITION(ID);
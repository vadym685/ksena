alter table KSENA_CLEANING_POSITION_QUANTITY_POSITION_LINK add constraint FK_CLEPOSQUAPOS_ON_QUANTITY_POSITION foreign key (QUANTITY_POSITION_ID) references KSENA_QUANTITY_POSITION(ID);
alter table KSENA_CLEANING_POSITION_QUANTITY_POSITION_LINK add constraint FK_CLEPOSQUAPOS_ON_CLEANING_POSITION foreign key (CLEANING_POSITION_ID) references KSENA_CLEANING_POSITION(ID);
alter table KSENA_QUANTITY_POSITION alter column CLEANING_POSITION_ID rename to CLEANING_POSITION_ID__U18663 ^
alter table KSENA_QUANTITY_POSITION drop constraint FK_KSENA_QUANTITY_POSITION_ON_CLEANING_POSITION ;
drop index IDX_KSENA_QUANTITY_POSITION_ON_CLEANING_POSITION ;

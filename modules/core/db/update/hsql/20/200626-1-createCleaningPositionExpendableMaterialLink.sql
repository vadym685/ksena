create table KSENA_CLEANING_POSITION_EXPENDABLE_MATERIAL_LINK (
    EXPENDABLE_MATERIAL_ID varchar(36) not null,
    CLEANING_POSITION_ID varchar(36) not null,
    primary key (EXPENDABLE_MATERIAL_ID, CLEANING_POSITION_ID)
);
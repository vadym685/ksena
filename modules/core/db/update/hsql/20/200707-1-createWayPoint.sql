create table KSENA_WAY_POINT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    ADDRESS varchar(255),
    VOLUME_BALANCE double precision,
    ZONE_RADIUS double precision,
    COMMENT_ varchar(255),
    CONTACT_PERSON varchar(255),
    CONTACT_PHONE varchar(255),
    LAT double precision,
    LON double precision,
    TYPE_ integer not null,
    MAX_HEIGHT_OF_TRANSPORT varchar(255),
    MAX_LENGTH_OF_TRANSPORT varchar(255),
    TASK_DURATION varchar(255),
    WALKING_TIME varchar(255),
    START_TIME time not null,
    END_TIME time not null,
    --
    primary key (ID)
);
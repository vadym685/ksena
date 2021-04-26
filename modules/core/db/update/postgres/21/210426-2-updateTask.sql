alter table KSENA_TASK rename column inventory_delivery to inventory_delivery__u96388 ;
alter table KSENA_TASK add column INVENTORY_DELIVERY_REQUIRED boolean ;
alter table KSENA_TASK add column RESPONSIBLE_FOR_THE_DELIVERY_OF_INVENTORY_ID uuid ;

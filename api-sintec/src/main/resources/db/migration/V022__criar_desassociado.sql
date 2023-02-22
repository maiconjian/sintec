create table SERV_DESASSOCIADO (
   ID                   bigint              identity,
   ID_SERVICO           bigint              not null,
   ID_USUARIO           bigint              not null,
   constraint PK_SERV_DESASSOCIADO primary key (ID)
)
go

alter table SERV_DESASSOCIADO
   add constraint FK_SERV_DES_REFERENCE_CAD_SERV foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table SERV_DESASSOCIADO
   add constraint FK_SERV_DES_REFERENCE_SYS_USUARIO foreign key (ID_USUARIO)
      references SYS_USUARIO (ID)
go
create table USUARIO_REGIONAL (
   ID                   bigint              identity,
   ID_USUARIO           bigint              not null,
   ID_REGIONAL          bigint              not null,
   constraint PK_USUARIO_REGIONAL primary key (ID)
)
go

alter table USUARIO_REGIONAL
   add constraint FK_USUARIO__REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go

alter table USUARIO_REGIONAL
   add constraint FK_USUARIO__REFERENCE_SYS_USUA foreign key (ID_USUARIO)
      references SYS_USUARIO (ID)
go

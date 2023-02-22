create table PERFIL_MENU (
   ID                   bigint              identity,
   ID_PERFIL_ACESSO     bigint              not null,
   ID_MENU              bigint              not null,
   constraint PK_PERFIL_MENU primary key (ID)
)
go

alter table PERFIL_MENU
   add constraint FK_PERFIL_M_REFERENCE_CAD_MENU foreign key (ID_MENU)
      references CAD_MENU (ID)
go

alter table PERFIL_MENU
   add constraint FK_PERFIL_M_REFERENCE_SYS_PERF foreign key (ID_PERFIL_ACESSO)
      references SYS_PERFIL_ACESSO (ID)
go
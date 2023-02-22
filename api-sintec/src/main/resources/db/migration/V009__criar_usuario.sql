create table SYS_USUARIO (
   ID                   bigint              identity,
   ID_PERFIL_ACESSO     bigint              null,
   NR_CPF               varchar(20)          null unique,
   NM_NOME              varchar(200)         null,
   NR_MATRICULA         bigint               null unique,
   NM_LOGIN             varchar(50)          null unique,
   NM_SENHA             varchar(300)         null,
   DS_EMAIL             varchar(100)         null,
   NR_PIN               bigint               null,
   DT_ATUALIZACAO       datetime             null,
   ST_ATIVO             tinyint              null,
   constraint PK_SYS_USUARIO primary key (ID)
)
go

alter table SYS_USUARIO
   add constraint FK_SYS_USUA_REFERENCE_SYS_PERF foreign key (ID_PERFIL_ACESSO)
      references SYS_PERFIL_ACESSO (ID)
go

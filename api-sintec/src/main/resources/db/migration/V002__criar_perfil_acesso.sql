create table SYS_PERFIL_ACESSO (
   ID                   bigint              identity,
   NM_NOME              varchar(150)         null,
   ST_ATIVO             tinyint              null,
   DT_ATUALIZACAO       datetime             null,
   constraint PK_SYS_PERFIL_ACESSO primary key (ID)
)
go
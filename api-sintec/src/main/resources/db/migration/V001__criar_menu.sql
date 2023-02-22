create table CAD_MENU (
   ID                   bigint              identity,
   NM_NOME              varchar(100)         null,
   NM_ICONE             varchar(100)         null,
   NM_ROTA              varchar(150)         null,
   ID_MENU              bigint              null,
   NR_ORDEM_APRESENTACAO integer              null,
   DT_ATUALIZACAO       datetime             null,
   ST_ATIVO             tinyint              null,
   constraint PK_CAD_MENU primary key (ID)
)
go

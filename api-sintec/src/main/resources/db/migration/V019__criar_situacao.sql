create table CAD_SITUACAO_RACP (
   ID                   bigint              identity,
   NM_NOME              VARCHAR(100)         null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_SITUACAO_RACP primary key (ID)
)
go

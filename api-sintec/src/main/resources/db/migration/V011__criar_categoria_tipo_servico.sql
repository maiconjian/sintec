create table CAD_CATEGORIA_TIPO_SERVICO (
   ID                   BIGINT               identity,
   NM_NOME              VARCHAR(150)         null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_CATEGORIA_TIPO_SERVICO primary key (ID)
)
go
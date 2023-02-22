create table CAD_CATEGORIA_VEICULO (
   ID                   BIGINT               identity,
   NM_NOME              VARCHAR(100)         null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   constraint PK_CAD_CATEGORIA_VEICULO primary key (ID)
)
go
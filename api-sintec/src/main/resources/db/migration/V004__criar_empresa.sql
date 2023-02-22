create table CAD_EMPRESA (
   ID                   bigint              identity,
   NM_NOME              varchar(200)         null,
   NR_CNPJ              varchar(25)          null unique,
   NM_FANTASIA          varchar(100)         null,
   NM_CIDADE            varchar(100)         null,
   NM_BAIRRO            varchar(100)         null,
   NM_ENDERECO          varchar(150)         null,
   NR_INSC_MUNICIPAL    varchar(50)          null,
   DT_ATUALIZACAO       datetime             null,
   ST_ATIVO             tinyint              null,
   constraint PK_CAD_EMPRESA primary key (ID)
)
go
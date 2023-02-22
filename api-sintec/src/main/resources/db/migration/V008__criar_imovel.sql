create table CAD_IMOVEL (
   ID                   bigint              identity,
   NM_NOME              VARCHAR(200)        null,
   ID_REGIONAL          bigint              null,
   DT_ATUALIZACAO       datetime            null,
   ST_ATIVO             TINYINT             null,
   END_BAIRRO           VARCHAR(100)        null,
   END_LOGRADOURO       VARCHAR(100)        null,
   END_NUMERO           INTEGER             null,
   END_COMPLEMENTO      VARCHAR(100)        null,
   constraint PK_CAD_LOCALIDADE primary key (ID)
)
go

alter table CAD_IMOVEL
   add constraint FK_CAD_LOCA_REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go

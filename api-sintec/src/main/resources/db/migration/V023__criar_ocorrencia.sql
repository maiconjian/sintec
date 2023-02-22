create table CAD_OCORRENCIA (
   ID                   BIGINT               identity,
   DS_DESCRICAO         varchar(200)         null,
   ID_CONTRATO          BIGINT               null,
   ST_ATIVO             tinyint              null,
   constraint PK_CAD_OCORRENCIA primary key (ID)
)
go

alter table CAD_OCORRENCIA
   add constraint FK_CAD_OCOR_REFERENCE_CAD_CONT foreign key (ID_CONTRATO)
      references CAD_CONTRATO (ID)
go

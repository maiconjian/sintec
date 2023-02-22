create table CAD_TIPO_SERVICO (
   ID                            bigint               identity,
   ID_CONTRATO                   bigint               null,
   CD_CODIGO                     VARCHAR(20)          null,
   NM_NOME         		         VARCHAR(100)         null,
   NR_DIAS_AGENDAMENTO           INTEGER              null,
   ID_CATEGORIA_TIPO_SERVICO     BIGINT               null,
   DT_ATUALIZACAO                datetime             null,
   ST_ATIVO                      TINYINT              null,
   constraint PK_CAD_TIPO_SERVICO primary key (ID)
)
go

alter table CAD_TIPO_SERVICO
   add constraint FK_CAD_TIPO_REFERENCE_CAD_CONT foreign key (ID_CONTRATO)
      references CAD_CONTRATO (ID)
go

alter table CAD_TIPO_SERVICO
   add constraint FK_CAD_TIPO_REFERENCE_CAD_CATE foreign key (ID_CATEGORIA_TIPO_SERVICO)
      references CAD_CATEGORIA_TIPO_SERVICO (ID)
go

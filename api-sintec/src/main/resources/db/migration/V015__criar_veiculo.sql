create table CAD_VEICULO (
   ID                   BIGINT               identity,
   NM_PLACA             VARCHAR(50)          null,
   NM_COR               VARCHAR(50)          null,
   NR_ANO               INTEGER              null,
   NM_MODELO            VARCHAR(100)         null,
   DT_ATUALIZACAO       DATETIME             null,
   ST_ATIVO             TINYINT              null,
   DT_VENCIMENTO_DOC      DATE               null,
   ID_REGIONAL          BIGINT               not null,
   ID_CATEGORIA_VEICULO BIGINT               not null,
   ID_COLABORADOR       BIGINT               null,
   constraint PK_CAD_VEICULO primary key (ID)
)
go

alter table CAD_VEICULO
   add constraint FK_CAD_VEIC_REFERENCE_CAD_REGI foreign key (ID_REGIONAL)
      references CAD_REGIONAL (ID)
go

alter table CAD_VEICULO
   add constraint FK_CAD_VEIC_REFERENCE_CAD_COLA foreign key (ID_COLABORADOR)
      references CAD_COLABORADOR (ID)
go

alter table CAD_VEICULO
   add constraint FK_CAD_VEIC_REFERENCE_CAD_CATE foreign key (ID_CATEGORIA_VEICULO)
      references CAD_CATEGORIA_VEICULO (ID)
go

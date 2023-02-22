create table SERV_RETORNO_FOTO (
   ID                   BIGINT               identity,
   ID_SERVICO           BIGINT               null,
   ID_QUESTIONARIO      BIGINT               null,
   NM_NOME              VARCHAR(100)         null,
   DS_PATH              VARCHAR(300)         null,
   DS_OBSERVACAO        VARCHAR(300)         null,
   CD_LATITUDE          VARCHAR(100)         null,
   CD_LONGITUDE         VARCHAR(100)         null,
   FLAG_ASSINATURA      TINYINT              null,
   FLAG_RACP            TINYINT              null,
   DT_ATUALIZACAO       DATETIME             null,
   constraint PK_SERV_RETORNO_FOTO primary key (ID)
)
go

alter table SERV_RETORNO_FOTO
   add constraint FK_SERV_RET_FOTO_REFERENCE_CAD_SERV foreign key (ID_SERVICO)
      references CAD_SERVICO (ID)
go

alter table SERV_RETORNO_FOTO
   add constraint FK_SERV_RET_FOTO_REFERENCE_CAD_QUES foreign key (ID_QUESTIONARIO)
      references CAD_QUESTIONARIO (ID)
go
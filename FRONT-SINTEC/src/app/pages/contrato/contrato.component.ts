import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { ContratoService } from './../../shared/service/contrato.service';
import { EmpresaService } from './../../shared/service/empresa.service';
import { ContratoFilter } from './../../shared/filter/contrato-filter';
import { UtilityService } from './../../shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalContratoComponent } from './modal-contrato/modal-contrato.component';
import { Contrato } from 'src/app/shared/model/contrato';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.scss']
})
export class ContratoComponent implements OnInit {

  filter = new ContratoFilter();
  contrato = new Contrato();
  loading: boolean;
  listaEmpresaCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  displayedColumns: string[] = ['empresa', 'nome', 'gerente', 'email', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  msgAlteracao: string;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility: UtilityService,
    private empresaService: EmpresaService,
    private contratoService: ContratoService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading = false;
    this.utility.setTitlePage('Contrato');
    this.listaEmpresaCarregado = this.utility.carregarComboObjetolNome(this.empresaService);
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro() {
    this.filter = new ContratoFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.contratoService.pesquisar(this.filter)
      .then(response => {
          this.dataSource = new MatTableDataSource<any>(response);
          this.dataSource.paginator = this.paginator;
          this.loading = false;
          if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
      }).catch(errorCustom => {
        this.erroHandleService.handle(errorCustom);
        this.loading = false;
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalContratoComponent, {
      width: '720px',
      data: {
        id: entity.id,
        nome: entity.nome,
        vigenciaInicio: entity.vigenciaInicio,
        vigenciaFim: entity.vigenciaFim,
        email: entity.email,
        empresa: entity.empresa,
        nomeGerente: entity.nomeGerente,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          // this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(result);
        } else {
          this.incluir(result)
        }
      }
    })
  }

  novo() {
    this.contrato = new Contrato();
    this.openDialog(this.contrato);
  }

  incluir(entity: any) {
    this.loading = true;
    entity.vigenciaInicio = this.utility.formatDataBrForUs(entity.vigenciaInicio);
    entity.vigenciaFim = this.utility.formatDataBrForUs(entity.vigenciaFim);
    this.contratoService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(errorCustom => {
        this.erroHandleService.handle(errorCustom);
        this.loading = false;
      })
  }

  editar(row: any) {
    this.contrato = new Contrato();
    this.contrato = row;
    this.contrato.vigenciaInicio = this.utility.formatDataUsForBr(this.contrato.vigenciaInicio);
    this.contrato.vigenciaFim = this.utility.formatDataUsForBr(this.contrato.vigenciaFim);
    this.openDialog(this.contrato);
  }

  alterar(entity: any) {
    this.loading = true;
    this.contratoService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
        this.loading = false;
      }).catch(errorCustom => {
        this.erroHandleService.handle(errorCustom);
        this.loading = false;
      })
  }

  confirmDialog(row: any) {
    let data = {
      'acao': 0
    };
    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: data
    })
    confirmDialog.afterClosed().subscribe(result => {
      if (result.acao == 1) {
        this.alterarSituacao(row);
      }
    })
  }

  alterarSituacao(row: any) {
    this.contrato = new Contrato();
    this.contrato = row;
    if (this.contrato.ativo == 1) {
      this.contrato.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.contrato.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.contrato);

  }

}

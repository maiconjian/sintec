import { CategoriaTipoServicoService } from './../../shared/service/categoria-tipo-servico.service';
import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { TipoServico } from './../../shared/model/tipo-servico';
import { ModalTipoServicoComponent } from './modal-tipo-servico/modal-tipo-servico.component';
import { MatDialog } from '@angular/material/dialog';
import { TipoServicoService } from './../../shared/service/tipo-servico.service';
import { TipoServicoFilter } from './../../shared/filter/tipo-servico-filter';
import { ContratoService } from './../../shared/service/contrato.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-tipo-servico',
  templateUrl: './tipo-servico.component.html',
  styleUrls: ['./tipo-servico.component.scss']
})
export class TipoServicoComponent implements OnInit {

  filter = new TipoServicoFilter();
  tipoServico = new TipoServico();

  loading: boolean;
  listaContratoCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  listaCategoriaTipoServicoCarregado:any[]=[];
  displayedColumns: string[] = ['categoria','nome', 'diasToleranciaExecucao', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  msgAlteracao: string;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility: UtilityService,
    private categoriaTipoServicoService:CategoriaTipoServicoService,
    private tipoServicoService: TipoServicoService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading = false;
    this.utility.setTitlePage('Tipo Serviço');
    this.listaContratoCarregado = this.utility.carregarComboContratoUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.listaCategoriaTipoServicoCarregado = this.utility.carregarComboObjetolNome(this.categoriaTipoServicoService);
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro() {
    this.filter = new TipoServicoFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.tipoServicoService.pesquisar(this.filter)
      .then(response => {
        this.dataSource = new MatTableDataSource<any>(response);
        this.dataSource.paginator = this.paginator;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);

        }
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalTipoServicoComponent, {
      width: '560px',
      data: {
        id: entity.id,
        nome: entity.nome,
        numDiasAgendamento: entity.numDiasAgendamento,
        contrato: entity.contrato,
        dataAtualizacao: entity.dataAtualizacao,
        categoriaTipoServico:entity.categoriaTipoServico,
        ativo: entity.ativo,
        listaContrato: this.listaContratoCarregado,
        listaCategoriaTipoServicoCarregado: this.listaCategoriaTipoServicoCarregado
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        let obj:any= {
          id: result.id,
          nome: result.nome,
          numDiasAgendamento: result.numDiasAgendamento,
          contrato: result.contrato,
          dataAtualizacao: result.dataAtualizacao,
          categoriaTipoServico:result.categoriaTipoServico,
          ativo: result.ativo
        }
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(obj);
        } else {
          this.incluir(obj);
        }
      }
    })
  }

  novo() {
    this.tipoServico = new TipoServico();
    this.openDialog(this.tipoServico);
  }

  incluir(entity: any) {
    this.loading = true;
    this.tipoServicoService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  editar(row: any) {
    this.tipoServico = new TipoServico();
    this.tipoServico = row;
    this.openDialog(this.tipoServico);
  }

  alterar(entity: any) {
    this.loading = true;
    this.tipoServicoService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
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
    this.tipoServico = new TipoServico();
    this.tipoServico = row;
    if (this.tipoServico.ativo == 1) {
      this.tipoServico.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.tipoServico.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.tipoServico);

  }

}

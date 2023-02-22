import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { ServicoService } from './../../shared/service/servico.service';
import { ColaboradorService } from './../../shared/service/colaborador.service';
import { Colaborador } from './../../shared/model/colaborador';
import { ModalColaboradorComponent } from './modal-colaborador/modal-colaborador.component';
import { MatDialog } from '@angular/material/dialog';
import { ColaboradorFilter } from './../../shared/filter/colaborador-filter';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-colaborador',
  templateUrl: './colaborador.component.html',
  styleUrls: ['./colaborador.component.scss']
})
export class ColaboradorComponent implements OnInit {

  filter = new ColaboradorFilter();
  colaborador = new Colaborador();
  loading: boolean;
  listaRegionalCarregado: any[] = [];
  // listaLocalidadeCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  msgAlteracao: string;

  displayedColumns: string[] = ['matricula', 'nome', 'cpf', 'cnh', 'categoriaCnh', 'opcao'];
  dataSource = new MatTableDataSource<Colaborador>([]);
  // selection = new SelectionModel<Colaborador>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility: UtilityService,
    private colaboradorService: ColaboradorService,
    private servicoService: ServicoService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading = false;
    this.utility.setTitlePage('Colaborador');
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro() {
    this.filter = new ColaboradorFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.colaboradorService.pesquisar(this.filter)
      .then(response => {
        this.dataSource = new MatTableDataSource<Colaborador>(response);
        this.dataSource.paginator = this.paginator;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
        this.loading = false;
      }).catch(errorCustom => {
        this.erroHandleService.handle(errorCustom);
        this.loading = false;
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalColaboradorComponent, {
      width: '720px',
      data: {
        id: entity.id,
        nome: entity.nome,
        matricula: entity.matricula,
        cpf: entity.cpf,
        dataNascimento: entity.dataNascimento,
        dataAdmissao: entity.dataAdmissao,
        cnh: entity.cnh,
        dataValidadeCnh: entity.dataValidadeCnh,
        categoriaCnh: entity.categoriaCnh,
        regional:entity.regional,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo,
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          // this.msgAlteracao = 'Alterado com sucesso!';
          result.dataNascimento = this.utility.formatDataBrForUs(result.dataNascimento);
          result.dataAdmissao = this.utility.formatDataBrForUs(result.dataAdmissao);
          result.dataValidadeCnh = this.utility.formatDataBrForUs(result.dataValidadeCnh);
          this.alterar(result);
        } else {
          this.incluir(result)
        }
      }
    })
  }

  novo() {
    this.colaborador = new Colaborador();
    this.openDialog(this.colaborador);
  }

  incluir(entity: any) {
    this.loading = true;
    entity.dataNascimento = this.utility.formatDataBrForUs(entity.dataNascimento);
    entity.dataValidadeCnh = this.utility.formatDataBrForUs(entity.dataValidadeCnh);
    entity.dataAdmissao = this.utility.formatDataBrForUs(entity.dataAdmissao);
    this.colaboradorService.incluir(entity)
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
    this.colaborador = new Colaborador();
    this.colaborador = row;
    this.colaborador.dataNascimento = this.utility.formatDataUsForBr(this.colaborador.dataNascimento);
    this.colaborador.dataAdmissao = this.utility.formatDataUsForBr(this.colaborador.dataAdmissao);
    this.colaborador.dataValidadeCnh = this.utility.formatDataUsForBr(this.colaborador.dataValidadeCnh);
    this.openDialog(this.colaborador);
  }

  alterar(entity: any) {
    this.loading = true;
    this.colaboradorService.alterar(entity)
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
    this.colaborador = new Colaborador();
    this.colaborador = row;
    if (this.colaborador.ativo == 1) {
      this.colaborador.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.colaborador.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.colaborador);

  }
}

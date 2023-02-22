import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { ServicoService } from './../../shared/service/servico.service';
import { TipoMensagem } from './../../shared/enum/tipo-mensagem.enum';
import { DistribuicaoDto } from './../../shared/dto/distribuicao-dto';
import { UsuarioFilter } from './../../shared/filter/usuario-filter';
import { UsuarioService } from './../../shared/service/usuario.service';
import { Usuario } from './../../shared/model/usuario';
import { DistribuicaoService } from './../../shared/service/distribuicao.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { DistribuicaoFilter } from 'src/app/shared/filter/distribuicao-filter';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { TipoServicoService } from 'src/app/shared/service/tipo-servico.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
// import { PageEvent } from '@angular/material/paginator';
import { SelectionModel } from '@angular/cdk/collections';
import { Colaborador } from 'src/app/shared/model/colaborador';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-distribuicao',
  templateUrl: './distribuicao.component.html',
  styleUrls: ['./distribuicao.component.scss']
})

export class DistribuicaoComponent implements OnInit {

  filter = new DistribuicaoFilter();
  usuario = new Usuario();

  loading: boolean;
  dataRef: string;
  listaRegionalCarregado: any[] = [];
  listaLocalidadeCarregado: any[] = [];
  listaTipoServicoCarregado: any[] = [];
  listaUsuarioCarregado: any[] = [];


  displayedColumnsServicoDistribuir: string[] = ['select', 'codigo', 'nomecolaborador', 'datasolicitacao', 'dataprogramada'];
  dataSourceServicoDistribuir = new MatTableDataSource<any>([]);
  @ViewChild('paginatorDistribuir', { static: true }) paginatorDistribuir: MatPaginator;
  selection = new SelectionModel<any>(true, []);


  displayedColumnsServicoDistribuido: string[] = ['codigodistribuido', 'nomecolaboradordistribuido', 'dataprogramadadistribuido','flagassociadodistribuido','opcaodistribuido'];
  dataSourceServicoDistribuido = new MatTableDataSource<any>([]);
  @ViewChild('paginatorDistribuido', { static: true }) paginatorDistribuido: MatPaginator;

  constructor(
    private utility: UtilityService,
    private tipoServicoService: TipoServicoService,
    private distribuicaoService: DistribuicaoService,
    private servicoService: ServicoService,
    private usuarioService: UsuarioService,
    private erroHandleService: ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Distribuição');
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.dataSourceServicoDistribuir.paginator = this.utility.getTraducaoTabela(this.paginatorDistribuir);
    this.dataSourceServicoDistribuido.paginator = this.utility.getTraducaoTabela(this.paginatorDistribuido);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  carregarTipoServico() {
    this.listaTipoServicoCarregado = [];
    let filter = new TipoServicoFilter();
    filter.ativo = 1;
    filter.idContrato = this.utility.getIdContrato(this.filter.idRegional);
    this.tipoServicoService.pesquisar(filter)
      .then(response => {
        response.forEach(element => {
          this.listaTipoServicoCarregado.push({
            label: element.nome,
            value: element.id
          });
        });
      }).catch(error => {
        this.erroHandleService.handle(error);
      })
  }

  carregarUsuario() {
    this.listaUsuarioCarregado = [];
    let filterUsuario = new UsuarioFilter();
    filterUsuario.idRegional = this.filter.idRegional;
    filterUsuario.ativo = 1;
    this.usuarioService.pesquisar(filterUsuario)
      .then(response => {
        response.forEach(element => {
          this.listaUsuarioCarregado.push({
            label: element.nome,
            value: element.id
          });
        });
      }).catch(error => {
        this.erroHandleService.handle(error);
      })
  }

  limparFiltro() {
    this.filter = new DistribuicaoFilter();
    this.filter.ativo = SituacaoEnum.ativo;
    this.dataSourceServicoDistribuir = new MatTableDataSource<any>([]);
    this.usuario = new Usuario();
    this.dataSourceServicoDistribuido = new MatTableDataSource<any>([]);
    this.selection = new SelectionModel<any>(true, []);
  }

  limparDistribuidoSelecionado(){
    this.usuario = new Usuario();
    this.selection = new SelectionModel<any>(true, []);
  }

  pesquisar() {
    this.loading = true;
    this.dataSourceServicoDistribuir = new MatTableDataSource<any>([]);
    this.dataSourceServicoDistribuido = new MatTableDataSource<any>([]);
    this.filter.dataRef = this.utility.formatDataRefBrForUs(this.dataRef);
    this.servicoService.pesquisar(this.filter)
      .then(response => {
        this.dataSourceServicoDistribuir = new MatTableDataSource<any>(response);
        this.dataSourceServicoDistribuir.paginator = this.paginatorDistribuir;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
      }).catch(error => {
        this.erroHandleService.handle(error);
      })
  }

  listaDistribuidoUsuario() {
    this.loading = true;
    this.filter.idUsuario = this.usuario.id;
    this.distribuicaoService.listaDistribuidoUsuario(this.filter)
      .then(response => {
        this.dataSourceServicoDistribuido = new MatTableDataSource<any>(response);
        this.dataSourceServicoDistribuido.paginator = this.paginatorDistribuido;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem servico cadastrado!', TipoMensagem.alerta);
        }
      }).catch(error => {
        this.erroHandleService.handle(error);
      })
  }

  /** Se o número de elementos selecionados corresponde ao número total de linhas. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSourceServicoDistribuir.data.length;
    return numSelected === numRows;
  }

  /** Seleciona todas as linhas se não estiverem todas selecionadas; caso contrário, limpar a seleção. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSourceServicoDistribuir.data.forEach(row => this.selection.select(row.id));
  }

  /** O rótulo da caixa de seleção na linha passada */
  checkboxLabel(row?: Colaborador): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row}`;
  }


  distribuirServico() {
    this.loading = true;
    let dto = new DistribuicaoDto();
    dto.idUsuario = this.usuario.id;
    dto.listaIdServico = this.selection.selected;
    this.distribuicaoService.associarServico(dto)
      .then(response => {
        this.selection = new SelectionModel<any>(true, []);
        this.pesquisar();
        this.listaDistribuidoUsuario();
        this.loading = false;
        if (response) {
          this.utility.openSnackBar('Associado com sucesso!', TipoMensagem.sucesso);
        }
      }).catch(error => {
        this.erroHandleService.handle(error);
      })
  }

  desassociar(row: any) {
    this.loading = true;
    this.distribuicaoService.desassociar(row.id, this.usuario.id)
      .then(response => {
        this.pesquisar();
        this.listaDistribuidoUsuario();
        this.utility.openSnackBar('Desassociado com sucesso!', TipoMensagem.sucesso);
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
        this.desassociar(row);
      }
    })
  }

}

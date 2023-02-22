import { ErrorHandleService } from './../../core/error-handle.service';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { DistribuicaoFilter } from 'src/app/shared/filter/distribuicao-filter';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { DistribuicaoService } from 'src/app/shared/service/distribuicao.service';
import { TipoServicoService } from 'src/app/shared/service/tipo-servico.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { SelectionModel } from '@angular/cdk/collections';
import { Colaborador } from 'src/app/shared/model/colaborador';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-liberacao',
  templateUrl: './liberacao.component.html',
  styleUrls: ['./liberacao.component.scss']
})
export class LiberacaoComponent implements OnInit {

  filter = new DistribuicaoFilter();

  loading: boolean;
  dataRef: string;
  listaRegionalCarregado: any[] = [];
  listaLocalidadeCarregado: any[] = [];
  listaTipoServicoCarregado: any[] = [];
  listaUsuarioCarregado: any[] = [];
  listaServicoLiberacaoAll: any[] = [];

  displayedColumns: string[] = ['select', 'codigo', 'nomecolaborador', 'datasolicitacao', 'dataprogramada', 'nomeusuario', 'flagasossiado','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  selection = new SelectionModel<any>(true, []);

  constructor(
    private utility: UtilityService,
    private tipoServicoService: TipoServicoService,
    private distribuicaoService: DistribuicaoService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Liberação');
    this.loading = false;
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
  }

  limparFiltro() {
    this.filter = new DistribuicaoFilter();
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
      }).catch(error=>{
        this.erroHandleService.handle(error);
      })
  }

  pesquisar() {
    this.loading = true;
    this.filter.dataRef = this.utility.formatDataRefBrForUs(this.dataRef);
    this.distribuicaoService.listaDistribuido(this.filter)
      .then(response => {
        this.listaServicoLiberacaoAll = response;
        this.dataSource = new MatTableDataSource<any>(this.listaServicoLiberacaoAll);
        this.dataSource.paginator = this.paginator;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para lista!', TipoMensagem.alerta);
        }
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  searchUsuario(txt: any) {
    if (txt != undefined || txt != '' || txt != null) {
      let lista: any[] = [];
      let string = txt.toUpperCase();
      for (let index = 0; index < this.listaServicoLiberacaoAll.length; index++) {
        let nomeUsuario = this.listaServicoLiberacaoAll[index].nomeUsuario.toUpperCase();
        if (nomeUsuario.indexOf(string) > -1) {
          lista.push(this.listaServicoLiberacaoAll[index])
        }
      }
      this.dataSource = new MatTableDataSource<any>(lista);
    } else {
      this.dataSource = new MatTableDataSource<any>(this.listaServicoLiberacaoAll);
    }
  }

  /** Se o número de elementos selecionados corresponde ao número total de linhas. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.filter(function(item){
      if(item.flagAsossiado === 0){
        return true;
      }else{
        return false;
      }
    }).length;
    return numSelected === numRows;
  }

  /** Seleciona todas as linhas se não estiverem todas selecionadas; caso contrário, limpar a seleção. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => row.flagAsossiado == 0 ? this.selection.select(row) : '');
  }

  /** O rótulo da caixa de seleção na linha passada */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row}`;
  }

  liberarServicos() {
    this.loading = true;
    let listaIdDistribuicao: any[] = this.getListaIdDistribuicao(this.selection.selected);
    this.distribuicaoService.liberarServico(listaIdDistribuicao)
      .then(response => {
        if (response) {
          this.utility.openSnackBar('Liberados com sucesso!', TipoMensagem.sucesso);
        } else {
          this.utility.openSnackBar('Sem servicos liberados!', TipoMensagem.alerta);
        }
        this.selection = new SelectionModel<any>(true, []);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.selection = new SelectionModel<any>(true, []);
        this.loading = false;
      })
  }

  getListaIdDistribuicao(listaIdDistribuicao: any[]) {
    let lista: any[] = [];
    listaIdDistribuicao.forEach(element => {
      lista.push(element.idDistribuicao);
    });
    return lista;
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
        this.cancelarLiberacao(row);
      }
    })
  }

  cancelarLiberacao(row:any){
    this.loading = true;
    this.distribuicaoService.cancelarLiberacao(row.idDistribuicao)
    .then(response=>{
      this.loading=false;
      if(response >0){
        this.pesquisar();
        this.utility.openSnackBar('Liberacao cancelada!',TipoMensagem.sucesso);
      }
    }).catch(error => {
      this.erroHandleService.handle(error);
      this.selection = new SelectionModel<any>(true, []);
      this.loading = false;
    })
  }
}

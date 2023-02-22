import { CategoriaVeiculoService } from './../../../../shared/service/categoria-veiculo.service';
import { ModalResponsavelComponent } from './../../../veiculo/modal-responsavel/modal-responsavel.component';
import { VeiculoService } from './../../../../shared/service/veiculo.service';
import { VeiculoFilter } from './../../../../shared/filter/veiculo-filter';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { SelectionModel } from '@angular/cdk/collections';
import { UtilityService } from 'src/app/shared/utility.service';
import { SituacaoEnum } from 'src/app/shared/enum/situacao.enum';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { ErrorHandleService } from 'src/app/core/error-handle.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-lista-veiculo',
  templateUrl: './lista-veiculo.component.html',
  styleUrls: ['./lista-veiculo.component.scss']
})
export class ListaVeiculoComponent implements OnInit {

 
  @Input() exibirTabelaVeiculo = new EventEmitter();
  @Input() idRegional:number;
  @Output() enviarListaSelecionada = new EventEmitter();

  filter = new VeiculoFilter();
  listaCategoriaVeiculoCarregado: any[] = [];
  listaVeiculoAll:any[]=[];
  listaVeiculo:any[]=[];
  placaFilter:string;
  idCategoriaVeiculoFilter:number;

  displayedColumns: string[] = ['select', 'placa','modelo','cor','responsavel','tipoveiculo','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private veiculoService:VeiculoService,
    private categoriaVeiculoService:CategoriaVeiculoService,
    private utility:UtilityService,
    private errorHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
  }

  ngOnChanges(changes: SimpleChanges) {
    if(this.exibirTabelaVeiculo){
      this.idCategoriaVeiculoFilter = null;
      this.listaCategoriaVeiculoCarregado = this.utility.carregarComboObjetolNome(this.categoriaVeiculoService)
      this.getListVeiculo();
    }
  }
  getListVeiculo(){
    this.dataSource = new MatTableDataSource<any>([]);
    this.filter.idRegional = this.idRegional;
    this.filter.idCategoriaVeiculo = null;
    this.filter.ativo = SituacaoEnum.ativo;
    this.veiculoService.pesquisar(this.filter)
    .then(response=>{
      this.listaVeiculoAll = response;
      this.listaVeiculo = this.listaVeiculoAll
      this.dataSource = new MatTableDataSource<any>(this.listaVeiculo);
      this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
      if(response.length == 0){
        this.utility.openSnackBar('Sem veiculos cadastrado!',TipoMensagem.alerta);
      }
    })
    .catch(error=>{
      this.errorHandleService.handle(error)
    })
  }

  /** Se o número de elementos selecionados corresponde ao número total de linhas. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Seleciona todas as linhas se não estiverem todas selecionadas; caso contrário, limpar a seleção. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** O rótulo da caixa de seleção na linha passada */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row}`;
  }

  enviarSelecionados(){
    this.enviarListaSelecionada.emit(this.selection.selected);
  }

  filtrarTabela(){
    if(this.listaVeiculoAll.length>0 && this.placaFilter || this.idCategoriaVeiculoFilter){
      let lista = [];
      this.listaVeiculoAll.forEach(element => {
        let placa = element.placa.toUpperCase();
        let idCategoriaVeiculo = element.categoriaVeiculo.id;
        if(this.placaFilter && this.idCategoriaVeiculoFilter == null ){
          if(placa.indexOf(this.placaFilter.toUpperCase()) > -1){
            lista.push(element);
          }
        }else if(!this.placaFilter && this.idCategoriaVeiculoFilter !=null){
          if(idCategoriaVeiculo == this.idCategoriaVeiculoFilter){
            lista.push(element);
          }
        }else if(this.placaFilter && this.idCategoriaVeiculoFilter !=null){
          if(placa.indexOf(this.placaFilter.toUpperCase()) > -1 && idCategoriaVeiculo == this.idCategoriaVeiculoFilter){
            lista.push(element);
          }
        }
      });
      this.dataSource = new MatTableDataSource<any>(lista);
      this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    }else{
      this.dataSource = new MatTableDataSource<any>(this.listaVeiculoAll);
      this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    }
  }

  // Alterar Responsavel

  openDialogAlterarResponsavel(row:any){
    const dialogRef = this.dialog.open(ModalResponsavelComponent, {
      width: '350px',
      data: {
        id:row.id,
        placa:row.placa,
        cor:row.cor,
        ano:row.ano,
        modelo:row.modelo,
        codigoTipoVeiculo:row.codigoTipoVeiculo,
        regional:row.regional,
        colaborador:row.colaborador,
        dataAtualizacao:row.dataAtualizacao,
        ativo:row.ativo
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          this.alterar(result);
        }
      }
    })
  }

  alterar(entity: any) {
    this.veiculoService.alterar(entity)
      .then(response => {
        let index = this.listaVeiculo.findIndex(element => element.id == entity.id);
        let indexAll = this.listaVeiculoAll.findIndex(element => element.id == entity.id);
        this.listaVeiculo[index].colaborador = response.colaborador;
        this.listaVeiculoAll[indexAll].colaborador = response.colaborador;
        this.utility.openSnackBar('Responsavel alterado', TipoMensagem.sucesso);
      }).catch(error => {
        this.errorHandleService.handle(error);
      })
  }
}

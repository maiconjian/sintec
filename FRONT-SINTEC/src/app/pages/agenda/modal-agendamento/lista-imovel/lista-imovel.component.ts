import { TipoMensagem } from './../../../../shared/enum/tipo-mensagem.enum';
import { UtilityService } from 'src/app/shared/utility.service';
import { ImovelService } from './../../../../shared/service/imovel.service';
import { ImovelFilter } from './../../../../shared/filter/imovel-filter';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { SituacaoEnum } from 'src/app/shared/enum/situacao.enum';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { ErrorHandleService } from 'src/app/core/error-handle.service';

@Component({
  selector: 'app-lista-imovel',
  templateUrl: './lista-imovel.component.html',
  styleUrls: ['./lista-imovel.component.scss']
})
export class ListaImovelComponent implements OnInit {

  @Input() exibirTabelaImovel = new EventEmitter();
  @Input() idRegional:number;
  @Output() enviarListaSelecionada = new EventEmitter();

  filter = new ImovelFilter();

  displayedColumns: string[] = ['select', 'nome','bairro','logradouro','complemento'];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private imovelService:ImovelService,
    private utility:UtilityService,
    private erroHandleService:ErrorHandleService
  ) { }

  ngOnInit(): void {
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
  }

  ngOnChanges(changes: SimpleChanges) {
    if(this.exibirTabelaImovel){
      this.getListImovel();
      this.dataSource.paginator = this.paginator;
    }
  }
  getListImovel(){
    this.dataSource = new MatTableDataSource<any>([]);
    this.filter.idRegional = this.idRegional;
    this.filter.ativo = SituacaoEnum.ativo;
    this.imovelService.pesquisar(this.filter)
    .then(response=>{
      console.log(response);
      this.dataSource = new MatTableDataSource<any>(response);
      this.dataSource.paginator=this.paginator;
      if(response.length == 0){
        this.utility.openSnackBar('Sem imoveis cadastrado!',TipoMensagem.alerta);
      }
    }).catch(error=>{
      this.erroHandleService.handle(error)
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


}

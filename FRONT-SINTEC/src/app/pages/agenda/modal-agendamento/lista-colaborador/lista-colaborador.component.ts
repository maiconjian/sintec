import { ErrorHandleService } from './../../../../core/error-handle.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { SituacaoEnum } from './../../../../shared/enum/situacao.enum';
import { ColaboradorFilter } from './../../../../shared/filter/colaborador-filter';
import { ColaboradorService } from './../../../../shared/service/colaborador.service';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Colaborador } from 'src/app/shared/model/colaborador';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-lista-colaborador',
  templateUrl: './lista-colaborador.component.html',
  styleUrls: ['./lista-colaborador.component.scss']
})
export class ListaColaboradorComponent implements OnInit {

  @Input() exibirTabelaColaborador = new EventEmitter();
  @Input() idRegional:number;
  @Output() enviarListaSelecionada = new EventEmitter();

  filter = new ColaboradorFilter();

  displayedColumns: string[] = ['select','matricula', 'nome', 'cpf'];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<Colaborador>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private colaboradorService:ColaboradorService,
    private utility:UtilityService,
    private erroHandleService:ErrorHandleService
  ) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    if(this.exibirTabelaColaborador){
      this.getListColaborador();
      this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    }
  }
  getListColaborador(){
    this.dataSource = new MatTableDataSource<any>([]);
    this.filter.idRegional = this.idRegional;
    this.filter.ativo = SituacaoEnum.ativo;
    this.colaboradorService.pesquisar(this.filter)
    .then(response=>{
        this.dataSource = new MatTableDataSource<any>(response);
        this.dataSource.paginator=this.paginator;
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
  checkboxLabel(row?: Colaborador): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row}`;
  }

  enviarSelecionados(){
    this.enviarListaSelecionada.emit(this.selection.selected);
  }

}

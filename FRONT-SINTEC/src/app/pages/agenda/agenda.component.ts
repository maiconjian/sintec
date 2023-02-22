import { ModalAgendamentoComponent } from './modal-agendamento/modal-agendamento.component';
import { ModalAgendaComponent } from './modal-agenda/modal-agenda.component';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { TipoMensagem } from '../../shared/enum/tipo-mensagem.enum';
import { ServicoService } from '../../shared/service/servico.service';
import { TipoServicoService } from '../../shared/service/tipo-servico.service';
import { ServicoFilter } from '../../shared/filter/servico-filter';
import { UtilityService } from '../../shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { ErrorHandleService } from 'src/app/core/error-handle.service';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.scss']
})
export class AgendaComponent implements OnInit {

  filter = new ServicoFilter();
  dataAnoMesRef:string;
  loading:boolean;
  listaRegionalCarregado: any[] = [];
  listaLocalidadeCarregado: any[] = [];
  listaTipoServicoCarregado:any[]=[];
  listaSituacaoCarregado: any[] = [];
  msgAlteracao:string;

  displayedColumns: string[] = ['codigo','colaborador','placa','imovel','data-solicitacao','data-programada','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility:UtilityService,
    private tipoServicoService:TipoServicoService,
    private servicoService:ServicoService,
    private errorHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading=false;
    this.utility.setTitlePage('Agenda');
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  carregarTipoServico() {
    this.listaTipoServicoCarregado = [];
    let filterTipoServico = new TipoServicoFilter();
    filterTipoServico.idContrato = this.utility.getIdContrato(this.filter.idRegional);
    filterTipoServico.ativo = 1;
    this.tipoServicoService.pesquisar(filterTipoServico)
      .then(response => {
        response.forEach(element => {
          this.listaTipoServicoCarregado.push({
            label: element.nome,
            value: element.id
          });
        });
      })
  }

  limparFiltro(){
    this.filter = new ServicoFilter();
    this.dataAnoMesRef = '';
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar(){
    this.filter.dataRef = this.utility.formatDataRefBrForUs(this.dataAnoMesRef);
    this.loading =true;
    this.servicoService.pesquisar(this.filter)
    .then(response=>{
      console.log(response);
      this.loading=false;
      this.dataSource = new MatTableDataSource<any>(response);
      this.dataSource.paginator = this.paginator;
      if(response.length == 0){
        this.utility.openSnackBar('Sem dados para listar!',TipoMensagem.alerta);
      }
    }).catch(errorCustom => {
      this.errorHandleService.handle(errorCustom);
      this.loading = false;
    })
  }

  editar(idServico:any){
    this.servicoService.buscarPorId(idServico)
        .then(response=>{
         this.openDialog(response);
    })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalAgendaComponent, {
      width: '280px',
      data: entity
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          result.dataProgramada = this.utility.formatDataBrForUs(result.dataProgramada);
          this.alterar(result);
        }
      }
    })
  }

  alterar(entity: any) {
    this.loading = true;
    this.servicoService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
        this.loading = false;
      }).catch(errorCustom => {
        this.errorHandleService.handle(errorCustom);
        this.loading = false;
      })
  }
  // cancelar servico
  confirmDialog(idServico: any) {
    let data = {
      'acao': 0
    };
    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: data
    })
    confirmDialog.afterClosed().subscribe(result => {
      if (result.acao == 1) {
        this.servicoService.buscarPorId(idServico)
        .then(response=>{
          this.alterarSituacao(response);
        }).catch(errorCustom => {
          this.errorHandleService.handle(errorCustom);
        })
      }
    })
  }

  alterarSituacao(servico: any) {
    if (servico.ativo == 1) {
      servico.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      servico.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(servico);
  }

  //Agendamento
  openDialogAgendamento() {
    const dialogRef = this.dialog.open(ModalAgendamentoComponent, {
      width: '1420px',
      position:{top:'80px'},
      data: {}
    })
    dialogRef.afterClosed().subscribe(result => {
       if(result != undefined){
        this.agendarServico(result);
       }
    })
  }

  agendarServico(entity:any){
    this.loading = true;
    this.servicoService.agendarServico(entity)
    .then(response=>{
      this.utility.openSnackBar('Serviços agendados!',TipoMensagem.sucesso);
      this.loading=false;
    }).catch(errorCustom => {
      this.errorHandleService.handle(errorCustom);
      this.loading = false;
    })
  }
  
}

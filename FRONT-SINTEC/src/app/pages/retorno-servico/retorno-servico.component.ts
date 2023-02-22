import { ModalDetalhadoComponent } from './modal-detalhado/modal-detalhado.component';
import { RetornoService } from './../../shared/service/retorno.service';
import { RetornoFilter } from './../../shared/filter/retorno-filter';
import { UtilityService } from './../../shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { TipoServicoService } from 'src/app/shared/service/tipo-servico.service';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ModalMapaComponent } from './modal-mapa/modal-mapa.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-retorno-servico',
  templateUrl: './retorno-servico.component.html',
  styleUrls: ['./retorno-servico.component.scss']
})
export class RetornoServicoComponent implements OnInit {

  filter = new RetornoFilter();
  loading:boolean;
  dataAnoMesRef:string;

  listaRegionalCarregado: any[] = [];
  listaLocalidadeCarregado: any[] = [];
  listaTipoServicoCarregado:any[]=[];

  displayedColumns: string[] = ['codigo','colaborador','placa','imovel','data-execucao','usuario','racp','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility:UtilityService,
    private tipoServicoService:TipoServicoService,
    private retornoService:RetornoService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading =false;
    this.utility.setTitlePage('Retorno');
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
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
    this.filter = new RetornoFilter();
  }

  pesquisar(){
    this.filter.dataRef = this.utility.formatDataRefBrForUs(this.dataAnoMesRef);
    this.loading =true;
    this.retornoService.pesquisar(this.filter)
    .then(response=>{
      this.loading=false;
      this.dataSource = new MatTableDataSource<any>(response);
      this.dataSource.paginator = this.paginator;
      if(response.length == 0){
        this.utility.openSnackBar('Sem dados para listar!',TipoMensagem.alerta);
      }
    }).catch(errorCustom => {
      console.log(errorCustom);
      // this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
      this.loading = false;
    })
  }

  openDialogMapa(row:any) {
    const dialogRef = this.dialog.open(ModalMapaComponent, {
      width: '980px',
      height: '520px',
      disableClose:true,
      position: { top: '50px' },
      data: {
        latitude:row.latitude,
        longitude:row.longitude
      }
    });
  }

  openDialogDetalhado(row:any){
    this.loading= true;
    setTimeout(() => {
      this.loading= false;
      const dialogDetalhado = this.dialog.open(ModalDetalhadoComponent, {
        width: '1500px',
        position: { top: '0px' },
        disableClose:true,
        data: row
      });
    }, 650);

  }

}

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SituacaoRacpEnum } from 'src/app/shared/enum/situacao-racp.enum';
import { RacpFilter } from 'src/app/shared/filter/racp-filter';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { RacpService } from 'src/app/shared/service/racp.service';
import { SituacaoRacpService } from 'src/app/shared/service/situacao-racp.service';
import { TipoServicoService } from 'src/app/shared/service/tipo-servico.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { NconfComponent } from './nconf/nconf.component';

@Component({
  selector: 'app-racp',
  templateUrl: './racp.component.html',
  styleUrls: ['./racp.component.scss']
})
export class RacpComponent implements OnInit {

  filter = new RacpFilter();
  loading:boolean;
  dataAnoMesRef:string;

  listaRegionalCarregado: any[] = [];
  listaSituacaoRacpCarregado: any[] = [];
  listaTipoServicoCarregado:any[]=[];

  displayedColumns: string[] = ['codigoRacp','codigoServico','colaborador','placa','imovel','dataGerada','dataExecucao','opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility:UtilityService,
    private tipoServicoService:TipoServicoService,
    private situacaoRacpService:SituacaoRacpService,
    private racpService:RacpService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Racp');
    this.loading = false;
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.listaSituacaoRacpCarregado = this.utility.carregarComboObjetolNome(this.situacaoRacpService);
    this.filter.idSituacaoRacp = SituacaoRacpEnum.PENDENTE;
     // this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
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

  }

  pesquisar(){
    this.loading=true;
    this.filter.dataRef = this.utility.formatDataRefBrForUs(this.dataAnoMesRef);
    this.racpService.pesquisar(this.filter)
    .then(response=>{
      this.dataSource = new MatTableDataSource<any>(response);
      this.dataSource.paginator = this.paginator;
      this.loading=false;
    })

  }

  openDialog(idRacp) {
    const dialogRef = this.dialog.open(NconfComponent, {
      width: '720px',
      position: { top: '100px' },
      data: {
        idRacp:idRacp
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      // if (result != undefined) {
      //   if (result.id) {
      //     // this.msgAlteracao = 'Alterado com sucesso!';
      //     this.alterar(result);
      //   } else {
      //     this.incluir(result)
      //   }
      // }
    })
  }

}

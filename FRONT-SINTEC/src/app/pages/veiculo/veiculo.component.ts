import { CategoriaVeiculoService } from './../../shared/service/categoria-veiculo.service';
import { ColaboradorService } from './../../shared/service/colaborador.service';
import { ErrorHandleService } from './../../core/error-handle.service';
import { MatTableDataSource } from '@angular/material/table';
import { VeiculoService } from './../../shared/service/veiculo.service';
import { ModalVeiculoComponent } from './modal-veiculo/modal-veiculo.component';
import { TipoVeiculoEnum } from './../../shared/enum/tipo-veiculo.enum';
import { VeiculoFilter } from './../../shared/filter/veiculo-filter';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { SituacaoEnum } from 'src/app/shared/enum/situacao.enum';
import { MatDialog } from '@angular/material/dialog';
import { Veiculo } from 'src/app/shared/model/veiculo';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-veiculo',
  templateUrl: './veiculo.component.html',
  styleUrls: ['./veiculo.component.scss']
})
export class VeiculoComponent implements OnInit {

  filter = new VeiculoFilter();
  veiculo = new Veiculo();
  loading:boolean;
  listaRegionalCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  listaCategoriaVeiculoCarregado: any[] = [];
  listaColaboradorCarregado:any[]=[];
  msgAlteracao: string;

  displayedColumns: string[] = ['placa', 'modelo', 'cor', 'ano','categoriaVeiculo','vencimento-doc','colaborador', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;



  constructor(
    private utility:UtilityService,
    private veiculoService:VeiculoService,
    private categoriaVeiculoService:CategoriaVeiculoService,
    private errorHandleService:ErrorHandleService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.loading=false;
    this.utility.setTitlePage('Veiculo');
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.listaCategoriaVeiculoCarregado = this.utility.carregarComboObjetolNome(this.categoriaVeiculoService)
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro(){
    this.filter = new VeiculoFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar(){
    this.loading=true;
    if(this.filter.idRegional){
      this.veiculoService.pesquisar(this.filter)
      .then(response=>{
        this.dataSource = new MatTableDataSource<any>(response);
        this.dataSource.paginator = this.paginator;
        if(response.length == 0){
          this.utility.openSnackBar('Sem dados para listar!',TipoMensagem.alerta);
        }
        this.loading = false;
      }).catch(error=>{
        this.errorHandleService.handle(error);
        this.loading=false;
      })
    }
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalVeiculoComponent, {
      width: '600px',
      position: { top: '100px' },
      data: {
        listaRegionalCarregado: this.listaRegionalCarregado, 
        listaCategoriaVeiculoCarregado: this.listaCategoriaVeiculoCarregado, 
        id: entity.id,
        placa: entity.placa,
        cor:entity.cor,
        ano:entity.ano,
        modelo:entity.modelo,
        regional: entity.regional,
        categoriaVeiculo:entity.categoriaVeiculo,
        dataVencimentoDoc: this.utility.formatDataRefUsForBr(entity.dataVencimentoDoc),
        colaborador:entity.colaborador,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        this.veiculo = new Veiculo();
        this.veiculo.id = result.id;
        this.veiculo.regional = result.regional;
        // this.veiculo.codigoTipoVeiculo = result.codigoTipoVeiculo;
        this.veiculo.categoriaVeiculo = result.categoriaVeiculo;
        this.veiculo.placa = result.placa;
        this.veiculo.modelo = result.modelo;
        this.veiculo.ano = result.ano;
        this.veiculo.cor = result.cor;
        this.veiculo.dataVencimentoDoc = this.utility.formatDataRefBrForUs(result.dataVencimentoDoc),
        this.veiculo.ativo = result.ativo;
        this.veiculo.colaborador = result.colaborador;
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(this.veiculo);
        } else {
          this.incluir(this.veiculo);
        }
      }
    })
  }

  novo() {
    this.veiculo = new Veiculo();
    this.openDialog(this.veiculo);
  }

  editar(entity: any) {
    this.veiculo = new Veiculo();
    this.veiculo = entity;
    this.openDialog(this.veiculo);
  }

  incluir(entity: any) {
    this.loading = true;
    this.veiculoService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.errorHandleService.handle(error);
        this.loading = false;
      })
  }

  alterar(entity: any) {
    this.loading = true;
    this.veiculoService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.errorHandleService.handle(error);
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
    this.veiculo = new Veiculo();
    this.veiculo = row;
    if (this.veiculo.ativo == 1) {
      this.veiculo.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.veiculo.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.veiculo);
  }

}

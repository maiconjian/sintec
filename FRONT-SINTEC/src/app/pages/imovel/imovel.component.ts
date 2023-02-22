import { ImovelFilter } from './../../shared/filter/imovel-filter';
import { Imovel } from './../../shared/model/imovel';
import { ImovelService } from './../../shared/service/imovel.service';
import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from '../../shared/enum/situacao.enum';
import { ModalLocalidadeComponent } from './modal-imovel/modal-imovel.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { RegionalService } from '../../shared/service/regional.service';
import { UtilityService } from '../../shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-imovel',
  templateUrl: './imovel.component.html',
  styleUrls: ['./imovel.component.scss']
})
export class LocalidadeComponent implements OnInit {

  filter = new ImovelFilter();
  imovel = new Imovel();
  loading: boolean;
  listaRegionalCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  displayedColumns: string[] = ['nome', 'bairro', 'logradouro', 'complemento', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  msgAlteracao: string;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;


  constructor(
    private utility: UtilityService,
    private imovelService: ImovelService,
    private regionalService: RegionalService,
    private dialog: MatDialog,
    private erroHandleService:ErrorHandleService
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Imovel');
    this.loading = false;
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro() {
    this.filter = new ImovelFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.imovelService.pesquisar(this.filter)
      .then(response => {
        this.dataSource = new MatTableDataSource<any>(response);
        this.dataSource.paginator = this.paginator;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
      }).catch(errorCustom => {
        this.erroHandleService.handle(errorCustom);
        this.loading = false;
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalLocalidadeComponent, {
      width: '720px',
      data: {
        id: entity.id,
        codigo: entity.codigo,
        nome: entity.nome,
        bairro: entity.bairro,
        logradouro: entity.logradouro,
        complemento: entity.complemento,
        regional: entity.regional,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo,
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(result);
        } else {
          this.incluir(result)
        }
      }
    })
  }

  novo() {
    this.imovel = new Imovel();
    this.openDialog(this.imovel);
  }

  incluir(entity: any) {
    this.loading = true;
    this.imovelService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(errorCustom => {
        this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
        this.loading = false;
      })
  }

  editar(row: any) {
    this.imovel = new Imovel();
    this.imovel = row;
    this.openDialog(this.imovel);
  }

  alterar(entity: any) {
    this.loading = true;
    this.imovelService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
        this.loading = false;
      }).catch(errorCustom => {
        this.utility.openSnackBar(errorCustom.error[0].mensagemUsuario, TipoMensagem.erro);
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
    this.imovel = new Imovel();
    this.imovel = row;
    if (this.imovel.ativo == 1) {
      this.imovel.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.imovel.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.imovel);
  }

}

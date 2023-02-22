import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { ModalListaContatoComponent } from './modal-lista-contato/modal-lista-contato.component';
import { ModalRegionalComponent } from './modal-regional/modal-regional.component';
import { RegionalService } from 'src/app/shared/service/regional.service';
import { ContratoService } from './../../shared/service/contrato.service';
import { RegionalFilter } from './../../shared/filter/regional-filter';
import { UtilityService } from './../../shared/utility.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Regional } from 'src/app/shared/model/regional';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { PerfilEnum } from 'src/app/shared/enum/perfil.enum';

@Component({
  selector: 'app-regional',
  templateUrl: './regional.component.html',
  styleUrls: ['./regional.component.scss']
})
export class RegionalComponent implements OnInit {

  filter = new RegionalFilter();
  regional = new Regional();
  usuarioLogado:any;
  loading: boolean;
  msgAlteracao: string;
  listaContratoCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  displayedColumns: string[] = ['contrato', 'nome', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility: UtilityService,
    private regionalService: RegionalService,
    private contratoService:ContratoService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loading = false;
    this.utility.setTitlePage('Regional');
    this.carregarContratos();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  carregarContratos(){
    this.listaContratoCarregado=[];
    this.usuarioLogado = this.utility.getUsuarioLogado();
    if(this.usuarioLogado.idPerfil == PerfilEnum.DESENVOLVEDOR){
      this.listaContratoCarregado = this.utility.carregarComboObjetolNome(this.contratoService);
    }else{
      this.listaContratoCarregado = this.utility.carregarComboContratoUsuario();
    }
  }

  limparFiltro() {
    this.filter = new RegionalFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.regionalService.pesquisar(this.filter)
      .then(response => {
        this.dataSource = new MatTableDataSource<any>(response);
        this.dataSource.paginator = this.paginator;
        this.loading = false;
        if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalRegionalComponent, {
      width: '380px',
      data: {
        id: entity.id,
        codigo: entity.codigo,
        nome: entity.nome,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo,
        contrato: entity.contrato,
        contatos: entity.contatos,
        listaContratoCarregado: this.listaContratoCarregado
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        let regional = {
          id: result.id,
          codigo: result.codigo,
          nome: result.nome,
          dataAtualizacao: result.dataAtualizacao,
          ativo: result.ativo,
          contrato: result.contrato,
          contatos: result.contatos,
        }
        if (regional.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(regional);
        } else {
          this.incluir(regional)
        }
      }
    })
  }

  novo() {
    this.regional = new Regional();
    this.openDialog(this.regional);
  }

  incluir(entity: any) {
    this.loading = true;
    this.regionalService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  editar(row: any) {
    this.regional = new Regional();
    this.regional = row;
    this.openDialog(this.regional);
  }

  alterar(entity: any) {
    this.loading = true;
    this.regionalService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.pesquisar();
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
        this.alterarSituacao(row);
      }
    })
  }

  alterarSituacao(row: any) {
    this.regional = new Regional();
    this.regional = row;
    if (this.regional.ativo == 1) {
      this.regional.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.regional.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.regional);
  }

  listaContatoDialog(row: any) {
    let data = {
      'idRegional': row.id
    };
    const listaContatoDialog = this.dialog.open(ModalListaContatoComponent, {
      width: '960px',
      position: { top: '35px' },
      data: data
    })
  }

}

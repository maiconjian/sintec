import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { TipoMensagem } from './../../shared/enum/tipo-mensagem.enum';
import { ModalEmpresaComponent } from './modal-empresa/modal-empresa.component';
import { EmpresaService } from './../../shared/service/empresa.service';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { EmpresaFilter } from 'src/app/shared/filter/empresa-filter';
import { UtilityService } from 'src/app/shared/utility.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { Empresa } from 'src/app/shared/model/empresa';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.scss']
})
export class EmpresaComponent implements OnInit {

  filter = new EmpresaFilter();
  empresa = new Empresa();
  listaSituacaoCarregado: any[] = [];
  displayedColumns: string[] = ['nome', 'cnpj', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  loading: boolean;
  msgAlteracao: string;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  constructor(
    private utility: UtilityService,
    private dialog: MatDialog,
    private empresaService: EmpresaService,
    private erroHandleService:ErrorHandleService
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Empresa');
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.loading = false;
    this.filter.ativo = SituacaoEnum.ativo;
  }

  limparFiltro() {
    this.filter = new EmpresaFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.empresaService.pesquisar(this.filter)
      .then(response => {
          this.dataSource = new MatTableDataSource<any>(response);
          this.dataSource.paginator = this.paginator;
          this.loading = false;
          if (response.length == 0) {
          this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
        }
      }).catch(error=>{
        this.erroHandleService.handle(error);
      })
  }

  openDialog(entity) {
    const dialogRef = this.dialog.open(ModalEmpresaComponent, {
      width: '720px',
      data: {
        id: entity.id,
        nome: entity.nome,
        nomeFantasia: entity.nomeFantasia,
        cnpj: entity.cnpj,
        cidade: entity.cidade,
        bairro: entity.bairro,
        endereco: entity.endereco,
        inscricaoMunicipal: entity.inscricaoMunicipal,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo,
      }
    });
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
    this.empresa = new Empresa();
    this.openDialog(this.empresa);
  }

  editar(row: any) {
    this.empresa = new Empresa();
    this.empresa = row;
    this.openDialog(this.empresa);
  }

  incluir(entity: any) {
    this.loading = true;
    this.empresaService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterar(entity: any) {
    this.loading = true;
    this.empresaService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar( this.msgAlteracao, TipoMensagem.sucesso);
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
    this.empresa = new Empresa();
    this.empresa = row;
    if (this.empresa.ativo == 1) {
      this.empresa.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.empresa.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.empresa);

  }

}

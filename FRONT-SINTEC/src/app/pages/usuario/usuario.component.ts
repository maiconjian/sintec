import { ErrorHandleService } from './../../core/error-handle.service';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { ConfirmDialogComponent } from './../../core/confirm-dialog/confirm-dialog.component';
import { ModalUsuarioComponent } from './modal-usuario/modal-usuario.component';
import { UsuarioService } from './../../shared/service/usuario.service';
import { RegionalService } from './../../shared/service/regional.service';
import { PerfilAcessoService } from './../../shared/service/perfil-acesso.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { UsuarioFilter } from './../../shared/filter/usuario-filter';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Usuario } from 'src/app/shared/model/usuario';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { PerfilEnum } from 'src/app/shared/enum/perfil.enum';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  filter = new UsuarioFilter();
  usuario = new Usuario();
  loading: boolean;
  listaPerfilCarregado: any[] = [];
  listaRegionalCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  msgAlteracao: string;

  displayedColumns: string[] = ['cpf', 'nome', 'email', 'perfil', 'opcao'];
  dataSource = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private utility: UtilityService,
    private perfilService: PerfilAcessoService,
    private regionalService: RegionalService,
    private usuarioService: UsuarioService,
    private erroHandleService:ErrorHandleService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Usuario')
    this.loading = false;
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.carregarComboPerfil();
    this.carregarRegionais();
    this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  carregarRegionais(){
    let usuario = this.utility.getUsuarioLogado();
    if(usuario.idPerfil == PerfilEnum.DESENVOLVEDOR){
      this.listaRegionalCarregado = this.utility.carregarComboObjetolNome(this.regionalService);
    }else{
      this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    }
  }

  carregarComboPerfil(){
    this.listaPerfilCarregado = [];
    let usuario = this.utility.getUsuarioLogado();
    let filtro = {
      'ativo': 1
    }
    this.perfilService.pesquisar(filtro)
    .then(response=>{
      for (let index = 0; index < response.length; index++) {
        if(usuario.idPerfil == PerfilEnum.DESENVOLVEDOR){
          this.listaPerfilCarregado.push({
            "label":response[index].nome,
            "value":response[index].id
          });
        }else{
          if(usuario.idPerfil < response[index].id){
            this.listaPerfilCarregado.push({
              "label":response[index].nome,
              "value":response[index].id
            });
          }
        } 
      }
    });

  }

  limparFiltro() {
    this.filter = new UsuarioFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.dataSource = new MatTableDataSource<any>([]);
    this.usuarioService.pesquisar(this.filter)
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
    const dialogRef = this.dialog.open(ModalUsuarioComponent, {
      width: '720px',
      data: {
        id: entity.id,
        nome: entity.nome,
        matricula: entity.matricula,
        login: entity.login,
        senha: entity.senha,
        email: entity.email,
        cpf: entity.cpf,
        pin: entity.pin,
        perfil: entity.perfil,
        listaRegional: entity.listaRegional,
        dataAtualizacao: entity.dataAtualizacao,
        ativo: entity.ativo,
        listaRegionalCarregado:this.listaRegionalCarregado,
        listaPerfilCarregado:this.listaPerfilCarregado
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        let usuario ={
          id: result.id,
          nome: result.nome,
          matricula: result.matricula,
          login: result.login,
          senha: result.senha,
          email: result.email,
          cpf: result.cpf,
          pin: result.pin,
          perfil: result.perfil,
          listaRegional: result.listaRegional,
          dataAtualizacao: result.dataAtualizacao,
          ativo: result.ativo,
        }
        if (usuario.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterar(usuario);
        } else {
          this.incluir(usuario)
        }
      }
    })
  }

  novo() {
    this.usuario = new Usuario();
    this.openDialog(this.usuario);
  }

  editar(entity: any) {
    this.usuario = new Usuario();
    this.usuario = entity;
    this.openDialog(this.usuario);
  }

  incluir(entity: any) {
    this.loading = true;
    this.usuarioService.incluir(entity)
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
    this.usuarioService.alterar(entity)
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

  confirmDialogResetSenha(idRow: any) {
    let data = {
      'acao': 0
    };
    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: data
    })
    confirmDialog.afterClosed().subscribe(result => {
      if (result.acao == 1) {
        this.resetPassword(idRow);
      }
    })
  }

  alterarSituacao(row: any) {
    this.usuario = new Usuario();
    this.usuario = row;
    if (this.usuario.ativo == 1) {
      this.usuario.ativo = 0;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.usuario.ativo = 1;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterar(this.usuario);
  }

  resetPassword(id: number) {
    this.loading = true;
    this.usuarioService.resetPassword(id)
      .then(response => {
        this.loading = false;
        this.pesquisar();
        this.utility.openSnackBar('Senha redefinida!', TipoMensagem.sucesso);
      })
      .catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
        // this.errorHandler.handle(erro);
      });
  }

}

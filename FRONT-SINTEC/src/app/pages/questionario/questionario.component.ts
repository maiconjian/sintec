import { ErrorHandleService } from './../../core/error-handle.service';
import { PrioridadeEnum } from './../../shared/enum/prioridade.enum';
import { ModalAlternativaComponent } from './modal-alternativa/modal-alternativa.component';
import { Alternativa } from './../../shared/model/alternativa';
import { ModalPerguntaComponent } from './modal-pergunta/modal-pergunta.component';
import { SituacaoEnum } from './../../shared/enum/situacao.enum';
import { AlternativaService } from './../../shared/service/alternativa.service';
import { PerguntaService } from './../../shared/service/pergunta.service';
import { QuestionarioService } from './../../shared/service/questionario.service';
import { Questionario } from './../../shared/model/questionario';
import { ModalQuestionarioComponent } from './modal-questionario/modal-questionario.component';
import { TipoServicoService } from './../../shared/service/tipo-servico.service';
import { QuestionarioFilter } from './../../shared/filter/questionario-filter';
import { UtilityService } from './../../shared/utility.service';
import { ChangeDetectionStrategy, Component, OnInit, ViewChild } from '@angular/core';
import { TipoServicoFilter } from 'src/app/shared/filter/tipo-servico-filter';
import { Regional } from 'src/app/shared/model/regional';
import { MatDialog } from '@angular/material/dialog';
import { TipoMensagem } from 'src/app/shared/enum/tipo-mensagem.enum';
import { MatTableDataSource } from '@angular/material/table';
import { state, style, transition, animate, trigger } from '@angular/animations';
import { MatPaginator } from '@angular/material/paginator';
import { Pergunta } from 'src/app/shared/model/pergunta';
import { ConfirmDialogComponent } from 'src/app/core/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-questionario',
  templateUrl: './questionario.component.html',
  styleUrls: ['./questionario.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0px' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class QuestionarioComponent implements OnInit {

  filter = new QuestionarioFilter();
  questionario = new Questionario();
  pergunta = new Pergunta();
  perguntaSelecionada = new Pergunta();
  alternativa = new Alternativa();
  idQuestionarioSelecionado: number;
  idPerguntaSelecionado: number;
  clickedEditarQuestionario: boolean = false;
  clickedEditarPergunta: boolean = false;
  clickConfirmDialog: boolean = false;

  indexTabs: number;
  loading: boolean;
  listaContratoCarregado: any[] = [];
  listaTipoServicoCarregado: any[] = [];
  listaSituacaoCarregado: any[] = [];
  msgAlteracao: string;
  tituloQuestionarioTab: string;
  enunciadoPerguntaTab: string;

  displayedColumnsQuestionario: string[] = ['ordem-questionario', 'titulo-questionario', 'opcao-questionario'];
  dataSourceQuestionario = new MatTableDataSource<any>([]);
  @ViewChild('paginatorQuestionario', { static: true }) paginatorQuestionario: MatPaginator;

  displayedColumnsPergunta: string[] = ['ordem-pergunta', 'enunciado-pergunta', 'multipla-escolha-pergunta', 'opcao-pergunta'];
  dataSourcePergunta = new MatTableDataSource<any>([]);
  expandedElementPergunta: Questionario | null;
  @ViewChild('paginatorQuestionario', { static: true }) paginatorPergunta: MatPaginator;

  displayedColumnsAlternativa: string[] = ['ordem-alternativa', 'descricao-alternativa', 'flagnconf-alternativa', 'prioridade-alternativa', 'recomendacao-alternativa', 'opcao-alternativa'];
  dataSourceAlternativa = new MatTableDataSource<any>([]);
  expandedElementAlternativa: Questionario | null;
  @ViewChild('paginatorAlternativa', { static: true }) paginatorAlternativa: MatPaginator;

  constructor(
    private utility: UtilityService,
    private tipoServicoService: TipoServicoService,
    private dialog: MatDialog,
    private questionarioService: QuestionarioService,
    private perguntaService: PerguntaService,
    private alternativaService: AlternativaService,
    private erroHandleService:ErrorHandleService

  ) { }

  ngOnInit(): void {
    this.loading = false;
    this.indexTabs = 0;
    this.utility.setTitlePage('Questionario');
    this.listaContratoCarregado = this.utility.carregarComboContratoUsuario();
    this.listaSituacaoCarregado = this.utility.carregarComboStatus();
    this.dataSourceQuestionario.paginator = this.utility.getTraducaoTabela(this.paginatorQuestionario);
    this.dataSourcePergunta.paginator = this.utility.getTraducaoTabela(this.paginatorPergunta);
    this.dataSourceAlternativa.paginator = this.utility.getTraducaoTabela(this.paginatorAlternativa);
    this.filter.ativo = SituacaoEnum.ativo;
  }

  carregarTipoServico() {
    this.listaTipoServicoCarregado = [];
    let filterTipoServico = new TipoServicoFilter();
    filterTipoServico.idContrato = this.filter.idContrato;
    filterTipoServico.ativo = 1;
    this.tipoServicoService.pesquisar(filterTipoServico)
      .then(response => {
        response.forEach(element => {
          this.listaTipoServicoCarregado.push({
            label: element.nome,
            value: element.id
          });
        });
      }).catch(error=>{
        this.erroHandleService.handle(error);
      })
  }
  limparFiltro() {
    this.filter = new QuestionarioFilter();
    this.filter.ativo = SituacaoEnum.ativo;
  }

  pesquisar() {
    this.loading = true;
    this.indexTabs = 0;
    this.idQuestionarioSelecionado = 0;
    this.questionarioService.pesquisar(this.filter)
      .then(response => {
          this.loading = false;
          this.dataSourceQuestionario = new MatTableDataSource<any>(response);
          this.dataSourceQuestionario.paginator = this.paginatorQuestionario;
          if (response.length == 0){
            this.utility.openSnackBar('Sem dados para listar!', TipoMensagem.alerta);
          }
          
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  listaPerguntaQuestionario(idRow: any, tituloRow: any) {
    this.idPerguntaSelecionado = 0;
    if (!this.clickedEditarQuestionario && !this.clickConfirmDialog) {
      this.dataSourcePergunta = new MatTableDataSource<any>([]);
      this.idQuestionarioSelecionado = idRow;
      this.perguntaService.listaPerguntaQuestionario(this.idQuestionarioSelecionado)
        .then(response => {
          response.sort(function (a, b) {
            if (a.ativo && b.ativo) {
              return a.ordemApresentacao < b.ordemApresentacao;
            }
          });
          this.tituloQuestionarioTab = tituloRow;
          this.dataSourcePergunta = new MatTableDataSource<any>(response);
          this.dataSourcePergunta.paginator = this.paginatorPergunta;
          this.indexTabs = 1;
        }).catch(error=>{
          this.erroHandleService.handle(error);
        })
    }
  }

  listaAlternativaPergunta(row: any) {
    if (!this.clickedEditarPergunta && !this.clickConfirmDialog) {
      this.dataSourceAlternativa = new MatTableDataSource<any>([]);
      if (row.ativo == 1) {
        if (row.flagMultiplaEscolha == 1) {
          this.idPerguntaSelecionado = row.id;
          this.alternativaService.listaAlternativaPergunta(this.idPerguntaSelecionado)
            .then(response => {
              this.enunciadoPerguntaTab = row.enunciado
              response.sort(function (a, b) {
                if (a.ativo && b.ativo) {
                  return a.ordemApresentacao < b.ordemApresentacao;
                }
              });
              this.dataSourceAlternativa = new MatTableDataSource<any>(response);
              this.dataSourceAlternativa.paginator = this.paginatorAlternativa;
              this.indexTabs = 2;
            }).catch(error=>{
              this.erroHandleService.handle(error);
            })
        } else {
          this.utility.openSnackBar('Nao é de multipla escolha!', TipoMensagem.alerta);
        }
      } else {
        this.utility.openSnackBar('Opcao desativada!', TipoMensagem.alerta);
      }
    }
  }

  voltar() {
    this.indexTabs--;
    if (this.indexTabs == 0) {
      this.idQuestionarioSelecionado = 0;
    } else if (this.indexTabs == 1) {
      this.idPerguntaSelecionado = 0;
    }
  }
  // Questionario

  teste(){
    this.loading=true;
  }

  openDialogQuestionario(entity) {
    const dialogRef = this.dialog.open(ModalQuestionarioComponent, {
      width: '720px',
      position: { top: '100px' },
      disableClose:true,
      data: {
        id: entity.id,
        tipoServico: entity.tipoServico,
        titulo: entity.titulo,
        ordemApresentacao: entity.ordemApresentacao,
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          this.teste();
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterarQuestionario(result);
        } else {
          this.incluirQuestionario(result)
        }
      }
      this.clickedEditarQuestionario = false;
    })
  }

  novoQuestionario() {
    this.questionario = new Questionario();
    this.openDialogQuestionario(this.questionario);
  }

  editarQuestionario(row: any) {
    this.clickedEditarQuestionario = true;
    this.questionario = new Questionario();
    this.questionario = row;
    this.openDialogQuestionario(this.questionario);
  }

  incluirQuestionario(entity: any) {
    this.loading = true;
    this.questionarioService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.pesquisar();
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterarQuestionario(entity: any) {
    this.loading = true;
    this.questionarioService.alterar(entity)
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

  alterarSituacaoQuestionario(entity: any) {
    if (entity.ativo == SituacaoEnum.ativo) {
      entity.ativo = SituacaoEnum.inativo;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      entity.ativo = SituacaoEnum.ativo;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.questionarioService.alterarSituacaoQuestionario(entity)
      .then(response => {
        this.pesquisar();
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  //Pergunta

  openDialogPergunta(entity) {
    let questionario = new Questionario();
    questionario.id = this.idQuestionarioSelecionado;

    const dialogRefPergunta = this.dialog.open(ModalPerguntaComponent, {
      width: '720px',
      position: { top: '100px' },
      disableClose:true,
      data: {
        id: entity.id,
        enunciado: entity.enunciado,
        ordemApresentacao: entity.ordemApresentacao,
        flagFoto: entity.flagFoto,
        flagObrigatorio: entity.flagObrigatorio,
        questionario: questionario,
        flagMultiplaEscolha: entity.flagMultiplaEscolha
      }
    });
    dialogRefPergunta.afterClosed().subscribe(result => {
      if (result != undefined) {
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterarPergunta(result);
        } else {
          this.incluirPergunta(result)
        }
      }
      this.clickedEditarPergunta = false;
    })
  }

  novoPergunta() {
    this.pergunta = new Pergunta();
    this.openDialogPergunta(this.pergunta);
  }

  editarPergunta(row: any) {
    this.clickedEditarPergunta = true;
    this.pergunta = new Pergunta();
    this.pergunta = row;
    this.openDialogPergunta(this.pergunta);
  }

  incluirPergunta(entity: any) {
    this.loading = true;
    this.perguntaService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.listaPerguntaQuestionario(this.idQuestionarioSelecionado, this.tituloQuestionarioTab);
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterarPergunta(entity: any) {
    this.loading = true;
    this.perguntaService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.listaPerguntaQuestionario(this.idQuestionarioSelecionado, this.tituloQuestionarioTab);
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterarSituacaoPergunta(entity: any) {
    if (entity.ativo == SituacaoEnum.ativo) {
      entity.ativo = SituacaoEnum.inativo;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      entity.ativo = SituacaoEnum.ativo;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.perguntaService.alterarSituacaoPergunta(entity)
      .then(response => {
        this.listaPerguntaQuestionario(this.idQuestionarioSelecionado, this.tituloQuestionarioTab);
      })
  }

  // Alternativa

  openDialogAlternativa(entity) {
    let pergunta = new Pergunta();
    pergunta.id = this.idPerguntaSelecionado;

    const dialogRefAlternativa = this.dialog.open(ModalAlternativaComponent, {
      width: '720px',
      position: { top: '100px' },
      disableClose:true,
      data: {
        id: entity.id,
        descricao: entity.descricao,
        flagNconf: entity.flagNconf,
        ordemApresentacao: entity.ordemApresentacao,
        prioridadeNconf: entity.prioridadeNconf,
        recomendacaoNconf: entity.recomendacaoNconf,
        pergunta: pergunta,
        ativo: entity.ativo
      }
    });
    dialogRefAlternativa.afterClosed().subscribe(result => {
      if (result != undefined) {
        this.loading = true;
        if (result.id) {
          this.msgAlteracao = 'Alterado com sucesso!';
          this.alterarAlternativa(result);
        } else {
          this.incluirAlternativa(result)
        }
      }
    })
  }

  novoAlternativa() {
    this.alternativa = new Alternativa();
    this.openDialogAlternativa(this.alternativa);
  }

  editarAlternativa(row: any) {
    this.alternativa = new Alternativa();
    this.alternativa = row;
    this.alternativa.ativo = row.ativo;
    this.openDialogAlternativa(this.alternativa);
  }

  incluirAlternativa(entity: any) {
    this.loading = true;
    this.alternativaService.incluir(entity)
      .then(response => {
        this.utility.openSnackBar('Incluido com sucesso!', TipoMensagem.sucesso);
        this.listaAlternativaPergunta(this.perguntaSelecionada);
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterarAlternativa(entity: any) {
    this.loading = true;
    this.alternativaService.alterar(entity)
      .then(response => {
        this.utility.openSnackBar(this.msgAlteracao, TipoMensagem.sucesso);
        this.msgAlteracao = '';
        this.listaAlternativaPergunta(this.perguntaSelecionada);
        this.loading = false;
      }).catch(error => {
        this.erroHandleService.handle(error);
        this.loading = false;
      })
  }

  alterarSituacaoAlternativa(row: any) {
    this.alternativa = new Alternativa();
    this.alternativa = row;
    if (this.alternativa.ativo == SituacaoEnum.ativo) {
      this.alternativa.ativo = SituacaoEnum.inativo;
      this.msgAlteracao = 'Inativada com sucesso!';
    } else {
      this.alternativa.ativo = SituacaoEnum.ativo;
      this.msgAlteracao = 'Ativada com sucesso!';
    }
    this.alterarAlternativa(this.alternativa);
  }

  confirmDialog(row: any) {
    this.clickConfirmDialog = true;
    let data = {
      'acao': 0
    };
    const confirmDialog = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: data
    })
    confirmDialog.afterClosed().subscribe(result => {
      if (result.acao == 1) {
        if (this.indexTabs == 0) {
          console.log(this.indexTabs);
          this.alterarSituacaoQuestionario(row);
        } else if (this.indexTabs == 1) {
          this.alterarSituacaoPergunta(row);
        } else if (this.indexTabs == 2) {
          this.alterarSituacaoAlternativa(row);
        }

      }
      this.clickConfirmDialog = false;
    })
  }
}


import { CategoriaTipoServicoService } from './../../../shared/service/categoria-tipo-servico.service';
import { ErrorHandleService } from './../../../core/error-handle.service';
import { AgendarServicoDto } from './../../../shared/dto/agenda-servico-dto';
import { CategoriaTipoServicoEnum } from './../../../shared/enum/categoria-tipo-servico.enum';
import { TipoServicoService } from './../../../shared/service/tipo-servico.service';
import { SituacaoEnum } from './../../../shared/enum/situacao.enum';
import { TipoServicoFilter } from './../../../shared/filter/tipo-servico-filter';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalContratoComponent } from '../../contrato/modal-contrato/modal-contrato.component';
import { ServicoService } from 'src/app/shared/service/servico.service';

@Component({
  selector: 'app-modal-agendamento',
  templateUrl: './modal-agendamento.component.html',
  styleUrls: ['./modal-agendamento.component.scss']
})
export class ModalAgendamentoComponent implements OnInit {

  dto = new AgendarServicoDto();
  dataSolicitacao:string;
  tamanhoListaRecebida:number;

  listaCategoriaTipoServico:any[]=[];
  listaTipoServicoCarregado:any[]=[];
  listaRegionalCarregado: any[] = [];

  exibirTabelaColaborador:boolean= false;
  exibirTabelaImovel:boolean= false;
  exibirTabelaVeiculo:boolean = false;

  constructor(
    private utility:UtilityService,
    private categoriaTipoServicoService:CategoriaTipoServicoService,
    private tipoServicoService:TipoServicoService,
    private dialogRef:MatDialogRef<ModalContratoComponent>,
    private erroHandleService:ErrorHandleService,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.dto = new AgendarServicoDto();
    this.listaCategoriaTipoServico = this.utility.carregarComboObjetolNome(this.categoriaTipoServicoService);
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    this.dataSolicitacao =   this.utility.formatDataUsForBr(this.utility.formataDataUS(new Date()));
    this.exibirTabelaColaborador = false;
    this.exibirTabelaImovel = false;
    this.exibirTabelaVeiculo= false;
    this.tamanhoListaRecebida = 0;
  }

  carregarTipoServico(){
    this.listaTipoServicoCarregado = [];
    if(this.dto.idRegional && this.dto.idCategoriaTipoServico){
      let filter = new TipoServicoFilter();
      filter.idContrato =  this.utility.getIdContrato(this.dto.idRegional);
      filter.idCategoria = this.dto.idCategoriaTipoServico;
      filter.ativo = SituacaoEnum.ativo;
      this.tipoServicoService.pesquisar(filter)
      .then(response=>{
        response.forEach(element => {
          this.listaTipoServicoCarregado.push({
            label:element.nome,
            value:element.id
          })
        });
      }).catch(error=>{
        this.erroHandleService.handle(error)
      })
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  montarObj(){
    this.dto.dataSolicitacao = this.utility.formatDataBrForUs(this.dataSolicitacao);
    this.dto.numDiasAgendamento = this.dto.numDiasAgendamento == null || this.dto.numDiasAgendamento == undefined ? 0 : this.dto.numDiasAgendamento;
  }

  exibirTabela(){
    if(this.dto.idRegional && this.dto.idCategoriaTipoServico){
      if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.FUNCIONARIO){
        this.exibirTabelaColaborador = true;
        this.exibirTabelaImovel = false;
        this.exibirTabelaVeiculo=false;
      }else if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.LOCAL){
        this.exibirTabelaColaborador = false;
        this.exibirTabelaImovel = true;
        this.exibirTabelaVeiculo=false;
      }else if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.VEICULO){
        this.exibirTabelaColaborador = false;
        this.exibirTabelaImovel = false;
        this.exibirTabelaVeiculo=true;
      }else{
        this.exibirTabelaColaborador = false;
        this.exibirTabelaImovel = false;
        this.exibirTabelaVeiculo=false;
      }
    }
  }

  getSelecionados(event:any){
    this.tamanhoListaRecebida = event.length;
    if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.FUNCIONARIO){
      this.dto.listaColaborador = event;
    }else if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.LOCAL){
      this.dto.listaImovel = event;
    }else if(this.dto.idCategoriaTipoServico == CategoriaTipoServicoEnum.VEICULO){
      this.dto.listaVeiculo = event;
    }
    this.data = this.dto;
  }
}

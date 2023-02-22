import { VeiculoService } from './../../../shared/service/veiculo.service';
import { ErrorHandleService } from './../../../core/error-handle.service';
import { TipoMensagem } from './../../../shared/enum/tipo-mensagem.enum';
import { UtilityService } from './../../../shared/utility.service';
import { Colaborador } from './../../../shared/model/colaborador';
import { SituacaoEnum } from './../../../shared/enum/situacao.enum';
import { ColaboradorFilter } from './../../../shared/filter/colaborador-filter';
import { ColaboradorService } from './../../../shared/service/colaborador.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-responsavel',
  templateUrl: './modal-responsavel.component.html',
  styleUrls: ['./modal-responsavel.component.scss']
})
export class ModalResponsavelComponent implements OnInit {

  listaColaboradorCarregado:any[]=[];
  listaColaboradorCarregadoAll:any[]=[];
  colaborador = new Colaborador();

  constructor(
    private utility:UtilityService,
    private colaboradorService:ColaboradorService,
    private errorHandleService:ErrorHandleService,
    private dialogRef:MatDialogRef<ModalResponsavelComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any
  ) { }

  ngOnInit(): void {
    this.colaborador.id = null;
    this.carregarColaborador();
    this.colaborador.id = this.data.colaborador.id;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  montarObj(){
    this.data.colaborador= this.colaborador;
  }

  carregarColaborador(){
    let filter = new ColaboradorFilter();
    filter.idRegional = this.data.regional.id;
    filter.ativo = SituacaoEnum.ativo;
    this.colaboradorService.pesquisar(filter)
    .then(response=>{
      response.forEach(element => {
        this.listaColaboradorCarregadoAll.push({
          label:element.nome,
          value:element.id
        })
      });
      this.listaColaboradorCarregado = this.listaColaboradorCarregadoAll;
      if(this.listaColaboradorCarregadoAll.length == 0){
        this.utility.openSnackBar('Sem dados para listar!',TipoMensagem.alerta);
      };
    }).catch(error =>{
      this.errorHandleService.handle(error);
    })
  }
  
  searchColaboradorDropdown(txt:any){
    if(txt != undefined || txt != '' || txt != null){
      let lista:any[]=[];
      let string  = txt.toUpperCase();
      for (let index = 0; index < this.listaColaboradorCarregadoAll.length; index++) {
        let label=this.listaColaboradorCarregadoAll[index].label.toUpperCase();
        if(label.indexOf(string) > -1){
          lista.push(this.listaColaboradorCarregadoAll[index])
        }
      }
      this.listaColaboradorCarregado = lista;
    }else{
      this.listaColaboradorCarregado = this.listaColaboradorCarregadoAll;
    }
  }
  resetDropColaborador(){
    this.listaColaboradorCarregado = this.listaColaboradorCarregadoAll;
  }
}

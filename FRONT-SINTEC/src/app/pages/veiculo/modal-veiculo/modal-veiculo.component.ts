import { CategoriaVeiculo } from './../../../shared/model/categoria-veiculo';
import { ColaboradorFilter } from './../../../shared/filter/colaborador-filter';
import { SituacaoEnum } from './../../../shared/enum/situacao.enum';
import { VeiculoFilter } from './../../../shared/filter/veiculo-filter';
import { ColaboradorService } from './../../../shared/service/colaborador.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Regional } from 'src/app/shared/model/regional';
import { Colaborador } from 'src/app/shared/model/colaborador';

@Component({
  selector: 'app-modal-veiculo',
  templateUrl: './modal-veiculo.component.html',
  styleUrls: ['./modal-veiculo.component.scss']
})
export class ModalVeiculoComponent implements OnInit {

  titulo:string;
  regional = new Regional();
  colaborador = new Colaborador();
  categoriaVeiculo = new CategoriaVeiculo();
  listaColaboradorCarregadoAll:any[]=[];
  listaColaboradorCarregado:any[]=[];

  constructor(
    private colaboradorService:ColaboradorService,
    private dialogRef:MatDialogRef<ModalVeiculoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any
  ) { }

  ngOnInit(): void {
    this.regional = new Regional();
    this.colaborador = new Colaborador();
    if(this.data.id){
      this.titulo = 'Alterar';
      this.regional = this.data.regional;
      this.categoriaVeiculo = this.data.categoriaVeiculo;
      this.getListaColaborador();
      this.colaborador = this.data.colaborador;
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  montarUsuario(){
    this.data.regional = this.regional;
    this.data.colaborador = this.colaborador;
    this.data.categoriaVeiculo = this.categoriaVeiculo;
  }

  getListaColaborador(){
    this.listaColaboradorCarregado=[];
    this.listaColaboradorCarregadoAll=[];
    let filter = new ColaboradorFilter();
    filter.idRegional = this.regional.id;
    filter.ativo = SituacaoEnum.ativo;
    this.colaboradorService.pesquisar(filter)
    .then(response=>{
      response.forEach(element => {
        this.listaColaboradorCarregadoAll.push({
          label:element.nome,
          value:element.id
        })
      });
      this.listaColaboradorCarregado= this.listaColaboradorCarregadoAll;
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

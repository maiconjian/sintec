import { PerfilAcesso } from './../../../shared/model/perfil-acesso';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmpresaService } from 'src/app/shared/service/empresa.service';
import { PerfilAcessoService } from 'src/app/shared/service/perfil-acesso.service';
import { RegionalService } from 'src/app/shared/service/regional.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalEmpresaComponent } from '../../empresa/modal-empresa/modal-empresa.component';
import { Regional } from 'src/app/shared/model/regional';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-modal-usuario',
  templateUrl: './modal-usuario.component.html',
  styleUrls: ['./modal-usuario.component.scss']
})
export class ModalUsuarioComponent implements OnInit {

  titulo:string;
  perfil=new PerfilAcesso();
  listaPerfilCarregado:any[]=[];
  listaRegionalCarregadoAll:any[]=[];
  listaRegionalCarregado:any[]=[];
  regionaisSelecionadas:any[]=[];

  constructor(
    private utility:UtilityService,
    private perfilService:PerfilAcessoService,
    private regionalService:RegionalService,
    private dialogRef:MatDialogRef<ModalEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {
    this.listaPerfilCarregado = this.data.listaPerfilCarregado;
    this.listaRegionalCarregadoAll = this.data.listaRegionalCarregado;
    this.listaRegionalCarregado = this.listaRegionalCarregadoAll;
    if(this.data.id){
      this.titulo = 'Alterar';
      this.perfil = this.data.perfil;
      this.regionaisSelecionadas = this.utility.convertListaEmValueMultiselect(this.data.listaRegional);
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.perfil = new PerfilAcesso();
    this.dialogRef.close();
  }

  montarUsuario(){
    this.data.perfil = this.perfil;
    this.data.listaRegional = this.formatarRegionaisObj();
  }

  formatarRegionaisObj(){
    let listaFormatada:any[]=[];
    for (let i = 0; i < this.regionaisSelecionadas.length; i++) {
      let regional = new Regional();
      regional.id = this.regionaisSelecionadas[i];
      listaFormatada.push(regional);
    }
    return listaFormatada;
  }

  searchRegionalDropdown(txt:any){
    if(txt != undefined || txt != '' || txt != null){
      let lista:any[]=[];
      let string  = txt.toUpperCase();
      for (let index = 0; index < this.listaRegionalCarregadoAll.length; index++) {
        let label=this.listaRegionalCarregadoAll[index].label.toUpperCase();
        if(label.indexOf(string) > -1){
          lista.push(this.listaRegionalCarregadoAll[index])
        }
      }
      this.listaRegionalCarregado = lista;
    }else{
      this.listaRegionalCarregado = this.listaRegionalCarregadoAll;
    }
  }

  resetDropRegional(){
    this.listaRegionalCarregado = this.listaRegionalCarregadoAll;
  }
  
  

}

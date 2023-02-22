import { Imovel } from './../../../shared/model/imovel';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/shared/utility.service';
import { RegionalService } from 'src/app/shared/service/regional.service';
import { Regional } from 'src/app/shared/model/regional';
import { Colaborador } from 'src/app/shared/model/colaborador';

@Component({
  selector: 'app-modal-colaborador',
  templateUrl: './modal-colaborador.component.html',
  styleUrls: ['./modal-colaborador.component.scss']
})
export class ModalColaboradorComponent implements OnInit {

  regional = new Regional();
  imovel = new Imovel();
  colaborador = new Colaborador();

  titulo:string;
  listaRegionalCarregado:any[]=[];
  listaLocalidadeCarregado:any[]=[];

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalColaboradorComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
  ) { }

  ngOnInit(): void {
    this.colaborador = this.data;
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.regional = this.colaborador.regional;
    }else{
      this.titulo = 'Incluir';
    }
  }



  onNoClick(): void {
    this.regional = new Regional();
    this.dialogRef.close();
  }

  montarObj(){
    this.colaborador.regional = this.regional;
    this.data = this.colaborador;
  }

  validarCpf(){
    // console.log(this.utility.validarCPF(this.colaborador.cpf));
  }

}

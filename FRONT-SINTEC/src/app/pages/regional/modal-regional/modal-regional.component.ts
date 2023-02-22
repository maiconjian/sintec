import { ContratoService } from './../../../shared/service/contrato.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/utility.service';
import { Contrato } from 'src/app/shared/model/contrato';

@Component({
  selector: 'app-modal-regional',
  templateUrl: './modal-regional.component.html',
  styleUrls: ['./modal-regional.component.scss']
})
export class ModalRegionalComponent implements OnInit {

  contrato = new Contrato();
  titulo:string;
  listaContratoCarregado:any[]=[];

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalRegionalComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.listaContratoCarregado = this.data.listaContratoCarregado;
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.contrato = this.data.contrato;
     
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.contrato = new Contrato();
    this.dialogRef.close();
  }

  montarObj(){
    this.data.contrato = this.contrato;
  }

}

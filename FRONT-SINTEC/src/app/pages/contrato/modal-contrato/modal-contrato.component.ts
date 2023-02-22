import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Empresa } from 'src/app/shared/model/empresa';
import { EmpresaService } from 'src/app/shared/service/empresa.service';
import { UtilityService } from 'src/app/shared/utility.service';

@Component({
  selector: 'app-modal-contrato',
  templateUrl: './modal-contrato.component.html',
  styleUrls: ['./modal-contrato.component.scss']
})
export class ModalContratoComponent implements OnInit {

  titulo:string;
  empresa = new Empresa();
  listaEmpresaCarregado:any[]=[];

  constructor(
    private utility:UtilityService,
    private empresaService:EmpresaService,
    private dialogRef:MatDialogRef<ModalContratoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.listaEmpresaCarregado = this.utility.carregarComboObjetolNome(this.empresaService);
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.empresa = this.data.empresa;
     
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.empresa = new Empresa();
    this.dialogRef.close();
  }

  montarObj(){
    this.data.empresa = this.empresa;
  }

}

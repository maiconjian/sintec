import { CategoriaTipoServico } from './../../../shared/model/categoria-tipo-servico';
import { Contrato } from 'src/app/shared/model/contrato';
import { ContratoService } from './../../../shared/service/contrato.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-tipo-servico',
  templateUrl: './modal-tipo-servico.component.html',
  styleUrls: ['./modal-tipo-servico.component.scss']
})
export class ModalTipoServicoComponent implements OnInit {

  titulo:string;
  listaContratoCarregado:any[]=[];
  listaCategoriaTipoServico:any[]=[];
  contrato = new Contrato();
  categoriaTipoServico = new CategoriaTipoServico();

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalTipoServicoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.listaContratoCarregado = this.data.listaContrato;
    this.listaCategoriaTipoServico =this.data.listaCategoriaTipoServicoCarregado;
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.contrato = this.data.contrato;
      this.categoriaTipoServico = this.data.categoriaTipoServico;
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(){
    this.contrato = new Contrato();
    this.categoriaTipoServico = new CategoriaTipoServico();
    this.dialogRef.close();
  }

  montarObj(){
    this.data.contrato = this.contrato;
    this.data.categoriaTipoServico = this.categoriaTipoServico;
  }

}

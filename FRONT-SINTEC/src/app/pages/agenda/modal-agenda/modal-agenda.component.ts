import { UtilityService } from './../../../shared/utility.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Empresa } from 'src/app/shared/model/empresa';
import { ModalContratoComponent } from '../../contrato/modal-contrato/modal-contrato.component';

@Component({
  selector: 'app-modal-agenda',
  templateUrl: './modal-agenda.component.html',
  styleUrls: ['./modal-agenda.component.scss']
})
export class ModalAgendaComponent implements OnInit {

  titulo:string;

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalContratoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.data.dataProgramada = this.utility.formatDataUsForBr(this.data.dataProgramada);
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalEmpresaComponent } from '../../empresa/modal-empresa/modal-empresa.component';

@Component({
  selector: 'app-modal-alternativa',
  templateUrl: './modal-alternativa.component.html',
  styleUrls: ['./modal-alternativa.component.scss']
})
export class ModalAlternativaComponent implements OnInit {
  titulo:string;
  listaPrioridade:any[]=[];
  flagNconf:boolean=false;

  constructor(
    private utility: UtilityService,
    private dialogRef: MatDialogRef<ModalEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

    ngOnInit(): void {
      this.listaPrioridade = this.utility.carregarComboPrioridade();
      if (this.data.id) {
        this.titulo = 'Alterar';
        this.flagNconf = this.data.flagNconf ? true:false;
      } else {
        this.titulo = 'Incluir';
      }
    }

    onNoClick(): void {
      this.dialogRef.close();
    }

    montarObj(){
      this.data.flagNconf = this.flagNconf ? 1:0;
     
    }


}

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Questionario } from 'src/app/shared/model/questionario';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalEmpresaComponent } from '../../empresa/modal-empresa/modal-empresa.component';

@Component({
  selector: 'app-modal-pergunta',
  templateUrl: './modal-pergunta.component.html',
  styleUrls: ['./modal-pergunta.component.scss']
})
export class ModalPerguntaComponent implements OnInit {

  titulo:string;
  flagObrigatorio:boolean= false;
  flagFoto:boolean= false;
  flagMultiplaEscolha:boolean= false;

  constructor(
    private utility: UtilityService,
    private dialogRef: MatDialogRef<ModalEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

    ngOnInit(): void {
      if (this.data.id) {
        this.titulo = 'Alterar';
        this.flagObrigatorio = this.data.flagObrigatorio ? true:false;
        this.flagFoto = this.data.flagFoto ? true:false;
        this.flagMultiplaEscolha = this.data.flagMultiplaEscolha ? true:false;
      } else {
        this.titulo = 'Incluir';
      }
    }

    onNoClick(): void {
      this.dialogRef.close();
    }

    montarObj(){
      this.data.flagObrigatorio = this.flagObrigatorio ? 1:0;
      this.data.flagFoto = this.flagFoto ? 1:0;
      this.data.flagMultiplaEscolha = this.flagMultiplaEscolha ? 1:0;
    }

   

}

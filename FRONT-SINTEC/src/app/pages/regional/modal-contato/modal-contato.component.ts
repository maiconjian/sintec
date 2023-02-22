import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-contato',
  templateUrl: './modal-contato.component.html',
  styleUrls: ['./modal-contato.component.scss']
})
export class ModalContatoComponent implements OnInit {

  titulo:string;

  constructor(
    private dialogRef:MatDialogRef<ModalContatoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    if(this.data.id){      
      this.titulo = 'Alterar';
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

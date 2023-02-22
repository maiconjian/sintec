import { UtilityService } from './../../../shared/utility.service';
import { Component, Inject, OnInit } from '@angular/core';
import { Empresa } from 'src/app/shared/model/empresa';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmpresaService } from 'src/app/shared/service/empresa.service';

@Component({
  selector: 'app-modal-empresa',
  templateUrl: './modal-empresa.component.html',
  styleUrls: ['./modal-empresa.component.scss']
})
export class ModalEmpresaComponent implements OnInit {

  titulo:string;

  constructor(
    private utility:UtilityService,
    private empresaService:EmpresaService,
    private dialogRef:MatDialogRef<ModalEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any) { }

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

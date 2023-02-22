import { RegionalService } from '../../../shared/service/regional.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/shared/utility.service';
import { Regional } from 'src/app/shared/model/regional';

@Component({
  selector: 'app-modal-imovel',
  templateUrl: './modal-imovel.component.html',
  styleUrls: ['./modal-imovel.component.scss']
})
export class ModalLocalidadeComponent implements OnInit {

  titulo:string;
  regional = new Regional();
  listaRegionalCarregado:any[]=[];

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalLocalidadeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    this.listaRegionalCarregado = this.utility.carregarComboRegionaisUsuario();
    if(this.data.id){      
      this.titulo = 'Alterar';
      this.regional = this.data.regional;
    }else{
      this.titulo = 'Incluir';
    }
  }

  onNoClick(): void {
    this.regional = new Regional();
    this.dialogRef.close();
  }

  montarObj(){
    this.data.regional = this.regional;
  }

}

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/utility.service';
import { ModalLocalidadeComponent } from '../../imovel/modal-imovel/modal-imovel.component';

@Component({
  selector: 'app-nconf',
  templateUrl: './nconf.component.html',
  styleUrls: ['./nconf.component.scss']
})
export class NconfComponent implements OnInit {

  constructor(
    private utility:UtilityService,
    private dialogRef:MatDialogRef<ModalLocalidadeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
    console.log(this.data);
  }

}

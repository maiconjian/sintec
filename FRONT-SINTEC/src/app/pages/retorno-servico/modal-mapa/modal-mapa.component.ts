import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

declare var google: any;

@Component({
  selector: 'app-modal-mapa',
  templateUrl: './modal-mapa.component.html',
  styleUrls: ['./modal-mapa.component.scss']
})
export class ModalMapaComponent implements OnInit {

  options: any;

  overlays: any[];

  constructor(
    private dialogRef:MatDialogRef<ModalMapaComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit() {
    console.log(this.data);
    this.options = {
        center: {lat: Number(this.data.latitude), lng: Number(this.data.longitude)},
        zoom: 16
    };
    this.overlays = [
      new google.maps.Marker({position: {lat: Number(this.data.latitude), lng:  Number(this.data.longitude)}})
    ];
  }

  closeButton(): void {
    this.dialogRef.close();
  }

}

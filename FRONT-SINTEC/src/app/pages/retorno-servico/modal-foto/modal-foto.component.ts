import { Component, EventEmitter, Inject, Input, OnInit, SimpleChanges } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RetornoService } from 'src/app/shared/service/retorno.service';
import { UtilityService } from 'src/app/shared/utility.service';

@Component({
  selector: 'app-modal-foto',
  templateUrl: './modal-foto.component.html',
  styleUrls: ['./modal-foto.component.scss']
})
export class ModalFotoComponent implements OnInit {

  @Input() imagesRecebidas = new EventEmitter();
  images: any[] = [];
  indexFoto: number = 0;
  titulo: string = "";
  observacao: string = "";
  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];
  // url:string;

  constructor(
    private utility: UtilityService,
    private retornoService: RetornoService,
    private dialogRef: MatDialogRef<ModalFotoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    if (this.images.length > 0) {
      this.titulo = this.images[this.indexFoto].titulo;
      this.observacao = this.images[this.indexFoto].observacao;
    }

  }

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes['imagesRecebidas'].currentValue);
    if (changes['imagesRecebidas'].currentValue != null) {
      this.images = changes['imagesRecebidas'].currentValue;
      if (this.images.length > 0) {
        this.titulo = this.images[this.indexFoto].titulo;
        this.observacao = this.images[this.indexFoto].observacao;
      }
    }
  }

  nextPhoto() {
    if ((this.indexFoto + 1) == this.images.length) {
      this.indexFoto = 0;
    } else {
      this.indexFoto++;
    }
    this.titulo = this.images[this.indexFoto].titulo;
    this.observacao = this.images[this.indexFoto].observacao;
  }

  backPhoto() {
    if (this.indexFoto > 0) {
      this.indexFoto--;
    }
    this.titulo = this.images[this.indexFoto].titulo;
    this.observacao = this.images[this.indexFoto].observacao;
  }

}

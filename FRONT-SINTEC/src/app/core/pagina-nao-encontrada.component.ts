import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pagina-nao-encontrada',
  template: `
    <div class="container">
      <img class="img-responsive" src="../../assets/img/background/error-404.png" width="70%" height="70%" alt="" style="margin-top: 10px; margin-left: 10%;"/>
      <h1 style="text-align: center; margin-top: 5px; font-weight: bold; color: rgb(128, 128, 128)">Página não encontrada!</h1>
    </div>
  `,
  styles: []
})
export class PaginaNaoEncontradaComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nao-autorizado',
  template: `
  <div class="container">
  <img class="img-responsive" src="../../assets/img/background/acesso_negado.png" width="45%" height="45%" alt="" style="margin-top: 10px; margin-left: 25%;"/>
  <h1 style="text-align: center; margin-top: 5px; font-weight: bold; color: rgb(187, 5, 5)">Acesso Negado!</h1>
</div>
  `,
  styles: []
})
export class NaoAutorizadoComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}

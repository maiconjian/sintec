import { UtilityService } from 'src/app/shared/utility.service';
import { NavService } from './../../shared/service/nav.service';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/security/auth.service';

import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  usuarioLogado:any;

  constructor(
    private navBar:NavService,
    private util:UtilityService,
    private auth: AuthService,
  ) { }

  ngOnInit(): void {
    this.carregamentoInicial();
  }

  carregamentoInicial(){
    this.getPermission();
  }


  getPermission() {
    let string = sessionStorage.getItem('usuario');
    let decript = CryptoJS.AES.decrypt(string, 'flk').toString(CryptoJS.enc.Utf8);
    let usuario = JSON.parse(decript);
    let lista: any[] = [];
    lista.push(usuario.nomePerfil);
    let user = {
      username: usuario.nome,
      permissions: lista
    }
    this.navBar.setUser(user);
  }

}

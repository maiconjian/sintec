import { TipoVeiculoEnum } from './enum/tipo-veiculo.enum';
import { PrioridadeEnum } from './enum/prioridade.enum';
import { TipoMensagem } from './enum/tipo-mensagem.enum';
import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from '../security/auth.service';

import * as CryptoJS from 'crypto-js';
import { EmpresaService } from './service/empresa.service';
import { CategoriaTipoServicoEnum } from './enum/categoria-tipo-servico.enum';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {


  constructor(
    private title: Title,
    private router: Router,
    private _snackBar: MatSnackBar,
    private auth: AuthService,
    // private logout: LogoutService,
  ) { }

  setUsuario() {
    let usuarioLogado = {
      'id': this.auth.jwtPayload.id,
      'login': this.auth.jwtPayload.user_name,
      'nome': this.auth.jwtPayload.nome,
      'idPerfil': this.auth.jwtPayload.idPerfil,
      'nomePerfil': this.auth.jwtPayload.nomePerfil,
      'email': this.auth.jwtPayload.email,
      'listaRegional': this.auth.jwtPayload.listaRegional,
      'listaContrato': this.auth.jwtPayload.listaContrato,
    }
    let cript = CryptoJS.AES.encrypt(JSON.stringify(usuarioLogado), 'flk').toString();
    sessionStorage.setItem('usuario', cript);
  }

  getUsuarioLogado(){
    let string = sessionStorage.getItem('usuario');
    let decript = CryptoJS.AES.decrypt(string, 'flk').toString(CryptoJS.enc.Utf8);
    let usuario = JSON.parse(decript);
    return usuario;
  }

  setTitlePage(titulo: string) {
    this.title.setTitle(`GF-SINTEC - ${titulo}`);
  }

  getTitlePage() {
    return this.title.getTitle();
  }

  setNavegation(pagina: string) {
    this.router.navigate([pagina]);
  }

  getUrl() {
    return this.router.url;
  }

  getRouter() {
    return this.router;
  }


  openSnackBar(message: string, tipo: string) {
    let verticalPosition: MatSnackBarVerticalPosition = 'top';
    let horizontalPosition: MatSnackBarHorizontalPosition = 'right';
    let panelClass: any[] = [];
    let segundos = 2;

    if (tipo == TipoMensagem.sucesso) {
      panelClass = ['sucesso-snackbar']
    } else if (tipo == TipoMensagem.alerta) {
      panelClass = ['alerta-snackbar']
    } else if (tipo == TipoMensagem.erro) {
      panelClass = ['erro-snackbar']
    } else if (tipo == TipoMensagem.info) {
      panelClass = ['info-snackbar']
    }

    this._snackBar.open(message, '', {
      panelClass: panelClass,
      duration: segundos * 1000,
      verticalPosition: verticalPosition,
      horizontalPosition: horizontalPosition
    })
  }

  getTraducaoTabela(paginator: any) {
    paginator._intl.itemsPerPageLabel = 'Itens por Pagina';
    paginator._intl.nextPageLabel = 'Proxima Pagina';
    paginator._intl.previousPageLabel = 'Pagina Anterior';
    paginator._intl.lastPageLabel = 'Ultima Pagina';
    paginator._intl.firstPageLabel = 'Primeira Pagina';
    paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => { if (length == 0 || pageSize == 0) { return `0 de ${length}`; } length = Math.max(length, 0); const startIndex = page * pageSize; const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize; return `${startIndex + 1} – ${endIndex} de ${length}`; }
    return paginator;
  }

  removerAcento(str:string){
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "")
  }


  carregarComboStatus() {
    let lista: any[] = [];
    lista.push(
      {
        label: 'ATIVO', value: 1
      }
    );
    lista.push(
      {
        label: 'INATIVO', value: 0
      }
    );
    return lista;
  }

  carregarComboPrioridade() {
    let lista: any[] = [];
    lista.push(
      {
        label: 'ALTO', value: PrioridadeEnum.ALTO
      }
    );
    lista.push(
      {
        label: 'MEDIO', value: PrioridadeEnum.MEDIO
      }
    );
    lista.push(
      {
        label: 'BAIXO', value: PrioridadeEnum.BAIXO
      }
    );
    return lista;
  }

  carregarComboRegionaisUsuario() {
    let string = sessionStorage.getItem('usuario');
    let decript = CryptoJS.AES.decrypt(string, 'flk').toString(CryptoJS.enc.Utf8);
    let usuario = JSON.parse(decript);
    let lista: any[] = [];
    usuario.listaRegional.forEach(element => {
      lista.push({
        label: element.nomeRegional,
        value: element.idRegional
      });
    });
    return lista;
  }

  getIdContrato(idRegional: any) {
    let string = sessionStorage.getItem('usuario');
    let decript = CryptoJS.AES.decrypt(string, 'flk').toString(CryptoJS.enc.Utf8);
    let usuario = JSON.parse(decript);
    let lista: any[] = usuario.listaRegional;
    let index = lista.findIndex(element => element.idRegional == idRegional);
    return lista[index].idContrato;

  }


  carregarComboContratoUsuario() {
    let string = sessionStorage.getItem('usuario');
    let decript = CryptoJS.AES.decrypt(string, 'flk').toString(CryptoJS.enc.Utf8);
    let usuario = JSON.parse(decript);
    let lista: any[] = [];
    usuario.listaContrato.forEach(element => {
      lista.push({
        label: element.nomeContrato,
        value: element.idContrato
      });
    });
    return lista;
  }


  carregarComboObjetolNome(service: any) {
    let lista: any[] = [];
    let filtro = {
      'ativo': 1
    }
    service.pesquisar(filtro)
      .then(response => {
        if (response.length > 0) {
          response.forEach(element => {
            if (service instanceof EmpresaService) {
              lista.push({
                label: element.nomeFantasia,
                value: element.id
              });
            } else {
              lista.push({
                label: element.nome,
                value: element.id
              });
            }

          });
        }
      })
      .catch(erro => {
        console.log(erro);
      });
    return lista;
  }

  convertListaEmValueMultiselect(listaObjeto: any[]) {
    let lista: any[] = [];
    for (let index = 0; index < listaObjeto.length; index++) {
      lista.push(listaObjeto[index].id)
    }
    return lista;
  }

  formatDataBrForUs(data: string) {
    // 10/02/2022
    if (data == undefined || data == '') {
      return '';
    } else {
      return data.substring(6, 10) + '-' + data.substring(3, 5) + '-' + data.substring(0, 2);
    }

  }

  formatDataUsForBr(data: string) {
    // 2022-08-10
    if (data == undefined || data == '') {
      return '';
    } else {
      return data.substring(8, 10) + '/' + data.substring(5, 7) + '/' + data.substring(0, 4);
    }
  }

  formatDataRefBrForUs(data: string) {
    // 02/2022
    return data.substring(3, 7) + '-' + data.substring(0, 2) + '-01'
  }

  formatDataRefUsForBr(data: string) {
    // 2022-08-10
    if(data == undefined || data == ''){
      return '';
    }else{
      return data.substring(5, 7) + '/' + data.substring(0, 4);
    }
   
  }
  

  public formataDataUS(data: any) {
    return data.getFullYear() + '-' + (this.pad(data.getMonth() + 1)) + '-' + this.pad(data.getDate());
  }

  public formataDataBR(data: any) {
    return this.pad(data.getDate()) + "/" + (this.pad(data.getMonth() + 1)) + "/" + data.getFullYear();
  }

  public formataDataMesAnoUSString(data: Date) {
    return data.getFullYear() + '-' + (this.pad(data.getMonth() + 1));
  }

  public formataDataMesAnoBRString(data: any) {
    return data.substring(5, 7) + '/' + data.substring(0, 4);
  }

  public formatarDataHoraBRString(data: any) {
    return data.substring(8, 10) + '/' + data.substring(5, 7) + '/' + data.substring(0, 4)
      + ' ' + data.substring(11, 13) + ':' + data.substring(14, 16) + ':' + data.substring(17, 19);
  }

  public formatarDataReferencia(data: any) {
    return (this.pad(data.getMonth() + 1)) + '/' + data.getFullYear();
  }

  public pad(n) {
    return n < 10 ? '0' + n : n;
  }

  public formataDataHoraBR(data: any) {
    let hora: string = '' + data.getHours();
    let minuto: string = '' + data.getMinutes();
    let segundo: string = '' + data.getSeconds();
    hora = hora.length == 1 ? '0' + hora : hora;
    minuto = minuto.length == 1 ? '0' + minuto : minuto;
    segundo = segundo.length == 1 ? '0' + segundo : segundo;
    return this.pad(data.getDate()) + '/' + (this.pad(data.getMonth() + 1)) + '/' + data.getFullYear()
      + ' ' + hora + ':' + minuto + ':' + segundo;
  }

  public formataDataHoraUS(data: any) {
    let hora: string = '' + data.getHours();
    let minuto: string = '' + data.getMinutes();
    let segundo: string = '' + data.getSeconds();
    hora = hora.length == 1 ? '0' + hora : hora;
    minuto = minuto.length == 1 ? '0' + minuto : minuto;
    segundo = segundo.length == 1 ? '0' + segundo : segundo;
    return data.getFullYear() + '-' + (this.pad(data.getMonth() + 1)) + '-' + this.pad(data.getDate())
      + ' ' + hora + ':' + minuto + ':' + segundo;
  }


  formatarCpfCnpj(event: any) {
    let texto = event;

    let validador: string = texto.replace(".", "").replace(".", "").replace("/", "").replace("-", "");

    if (texto.length == 14) {
      let valor: string = texto.replace(".", "").replace(".", "").replace("/", "").replace("-", "");

      valor = "";

      for (let i = 0; i < texto.length; i++) {
        valor += texto.charAt(i);
        if (i == 1) {
          valor += ".";
        }

        if (i == 4) {
          valor += ".";
        }

        if (i == 7) {
          valor += "/";
        }

        if (i == 11) {
          valor += "-";
        }
      }
      texto = valor;
    }

    if (texto.length == 11) {
      let valor: string = texto.replace(".", "").replace(".", "").replace("-", "");

      valor = "";

      for (let i = 0; i < texto.length; i++) {
        valor += texto.charAt(i);
        if (i == 2) {
          valor += ".";
        }

        if (i == 5) {
          valor += ".";
        }

        if (i == 8) {
          valor += "-";
        }
      }
      texto = valor;
    }

    if (validador.length > 11 && validador.length < 14) {
      texto = null;
    }

    if (validador.length < 11) {
      texto = null;
    }

    return texto;
  }

  public gerenciarEntradaCpfCnpj(cpfCnpj: any) {
    if (cpfCnpj) {
      let valor: string = '';
      for (let i = 0; i < cpfCnpj.length; i++) {
        if (cpfCnpj.charAt(i) == '0' || cpfCnpj.charAt(i) == '1' || cpfCnpj.charAt(i) == '2'
          || cpfCnpj.charAt(i) == '3' || cpfCnpj.charAt(i) == '4' || cpfCnpj.charAt(i) == '5'
          || cpfCnpj.charAt(i) == '6' || cpfCnpj.charAt(i) == '7' || cpfCnpj.charAt(i) == '8'
          || cpfCnpj.charAt(i) == '9') {
          valor += cpfCnpj.charAt(i);
        }
      }
      cpfCnpj = valor;
    }
    if (cpfCnpj.length == 11) {
      if (!this.validarCPF(cpfCnpj)) {
        return { 'msg': 'CPF INFORMADO É INVÁLIDO!', 'valido': false };
      } else {
        return { 'msg': null, 'valido': true };
      }
    } else if (cpfCnpj.length == 14) {
      if (!this.validarCNPJ(cpfCnpj)) {
        return { 'msg': 'CNPJ INFORMADO É INVÁLIDO!', 'valido': false };
      } else {
        return { 'msg': null, 'valido': true };
      }
    } else {
      return { 'msg': 'CPF/CNPJ INFORMADO É INVÁLIDO!', 'valido': false };
    }
  }

  public validarCPF(cpfCnpj: any): boolean {
    let n1: number;
    let n2: number;
    let n3: number;
    let n4: number;
    let n5: number;
    let n6: number;
    let n7: number;
    let n8: number;
    let n9: number;
    let n10: number;
    let n11: number;
    let d1: number;
    let d2: number;
    let digitado: number;
    let calculado: number;
    n1 = parseFloat(cpfCnpj.charAt(0));
    n2 = parseFloat(cpfCnpj.charAt(1));
    n3 = parseFloat(cpfCnpj.charAt(2));
    n4 = parseFloat(cpfCnpj.charAt(3));
    n5 = parseFloat(cpfCnpj.charAt(4));
    n6 = parseFloat(cpfCnpj.charAt(5));
    n7 = parseFloat(cpfCnpj.charAt(6));
    n8 = parseFloat(cpfCnpj.charAt(7));
    n9 = parseFloat(cpfCnpj.charAt(8));
    n10 = parseFloat(cpfCnpj.charAt(9));
    n11 = parseFloat(cpfCnpj.charAt(10));
    if (cpfCnpj == "00000000000" ||
      cpfCnpj == "11111111111" ||
      cpfCnpj == "22222222222" ||
      cpfCnpj == "33333333333" ||
      cpfCnpj == "44444444444" ||
      cpfCnpj == "55555555555" ||
      cpfCnpj == "66666666666" ||
      cpfCnpj == "77777777777" ||
      cpfCnpj == "88888888888" ||
      cpfCnpj == "99999999999") {
      return false;
    }
    d1 = (n9 * 2) + (n8 * 3) + (n7 * 4) + (n6 * 5) + (n5 * 6) + (n4 * 7) + (n3 * 8) + (n2 * 9) + (n1 * 10);
    d1 = 11 - (d1 % 11);
    if (d1 >= 10) {
      d1 = 0;
    }
    d2 = (d1 * 2) + (n9 * 3) + (n8 * 4) + (n7 * 5) + (n6 * 6) + (n5 * 7) + (n4 * 8) + (n3 * 9) + (n2 * 10) + (n1 * 11);
    d2 = 11 - (d2 % 11);
    if (d2 >= 10) {
      d2 = 0;
    }
    digitado = n10 + n11;
    calculado = d1 + d2;
    return calculado == digitado;
  }

  public validarCNPJ(cpfCnpj: any): boolean {
    let n1: number;
    let n2: number;
    let n3: number;
    let n4: number;
    let n5: number;
    let n6: number;
    let n7: number;
    let n8: number;
    let n9: number;
    let n10: number;
    let n11: number;
    let n12: number;
    let n13: number;
    let n14: number;
    let d1: number;
    let d2: number;
    let digitado: number;
    let calculado: number;
    n1 = parseFloat(cpfCnpj.charAt(0));
    n2 = parseFloat(cpfCnpj.charAt(1));
    n3 = parseFloat(cpfCnpj.charAt(2));
    n4 = parseFloat(cpfCnpj.charAt(3));
    n5 = parseFloat(cpfCnpj.charAt(4));
    n6 = parseFloat(cpfCnpj.charAt(5));
    n7 = parseFloat(cpfCnpj.charAt(6));
    n8 = parseFloat(cpfCnpj.charAt(7));
    n9 = parseFloat(cpfCnpj.charAt(8));
    n10 = parseFloat(cpfCnpj.charAt(9));
    n11 = parseFloat(cpfCnpj.charAt(10));
    n12 = parseFloat(cpfCnpj.charAt(11));
    n13 = parseFloat(cpfCnpj.charAt(12));
    n14 = parseFloat(cpfCnpj.charAt(13));
    if (cpfCnpj == "00000000000000" ||
      cpfCnpj == "11111111111111" ||
      cpfCnpj == "22222222222222" ||
      cpfCnpj == "33333333333333" ||
      cpfCnpj == "44444444444444" ||
      cpfCnpj == "55555555555555" ||
      cpfCnpj == "66666666666666" ||
      cpfCnpj == "77777777777777" ||
      cpfCnpj == "88888888888888" ||
      cpfCnpj == "99999999999999") {
      return false;
    }
    d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4 + n1 * 5;
    d1 = 11 - (d1 % 11);
    if (d1 >= 10) {
      d1 = 0;
    }
    d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4 + n2 * 5 + n1 * 6;
    d2 = 11 - (d2 % 11);
    if (d2 >= 10) {
      d2 = 0;
    }
    digitado = n13 + n14;
    calculado = d1 + d2;
    return calculado == digitado;
  }
}

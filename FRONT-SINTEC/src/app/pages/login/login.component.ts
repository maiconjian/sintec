import { ErrorHandleService } from './../../core/error-handle.service';
import { TipoMensagem } from './../../shared/enum/tipo-mensagem.enum';
import { AuthService } from './../../security/auth.service';
import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/shared/utility.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  login: string;
  senha: string;
  loading: boolean;
  usuarioLogado: any;

  constructor(
    private utility: UtilityService,
    private authService:AuthService,
    private errorHandleService:ErrorHandleService
  ) { }

  ngOnInit(): void {
    this.utility.setTitlePage('Login');
    this.loading = false;
  }

  autenticar() {
    this.loading = true;
    if (this.login == null || this.login == undefined || this.login == '') {
      this.utility.openSnackBar('USUARIO NAO PODE SER VAZIO!',TipoMensagem.alerta);
      this.loading = false;
      return null;
    } else if (this.senha == null || this.senha == undefined || this.senha == '') {
      this.utility.openSnackBar('SENHA NAO PODE SER VAZIO!',TipoMensagem.alerta);
      this.loading = false;
      return null;
    } else {
      this.authService.autenticar(this.login, this.senha)
        .then(() => {
          this.utility.setUsuario();
          this.utility.setNavegation('/home');
        })
        .catch(erro => {
          this.errorHandleService.handle(erro);
          this.loading = false;
          // this.utility.openSnackBar(erro.error.error_description,TipoMensagem.erro);
        });
    }
  }
}

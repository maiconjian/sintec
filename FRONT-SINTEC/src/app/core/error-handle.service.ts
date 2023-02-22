import { TipoMensagem } from './../shared/enum/tipo-mensagem.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NotAuthenticatedError } from '../security/http.service';
import { UtilityService } from '../shared/utility.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandleService {

  constructor(
    private utility: UtilityService
  ) { }

  handle(errorResponse: any) {
    let msg: string;
    if (typeof errorResponse === 'string') {
      msg = errorResponse;
    } else if (errorResponse instanceof NotAuthenticatedError) {
      msg = 'SUA SESSÃO EXPIROU!'
      this.utility.setNavegation('/login');
    } else if (errorResponse instanceof HttpErrorResponse
      && errorResponse.status >= 400 && errorResponse.status <= 499) {

      msg = 'ERRO AO PROCESSAR SOLICITAÇÃO!';

      if (errorResponse.error.error === 'invalid_token') {
        console.error('Ocorreu um erro de token', errorResponse);
        msg = undefined;
      }
      if (errorResponse.error.error === 'unauthorized') {
        console.error('Ocorreu um erro de token', errorResponse);
        msg = errorResponse.error.error_description;
      }
      if (errorResponse.error.error === 'invalid_grant') {
        console.error('Ocorreu um erro de login', errorResponse);
        msg = 'USUARIO OU SENHA INVALIDOS!';
      }
      if (errorResponse.status === 403) {
        msg = 'SEM PERMISSAO PARA EXECUTAR ACAO!';
      }
      try {
        msg = errorResponse.error[0].mensagemUsuario;
      } catch (e) { }
      console.error('Ocorreu um erro', errorResponse);
    } else {
      msg = 'ERRO AO PROCESSAR SERVICO REMOTO!';
      console.error('Ocorreu um erro', errorResponse);
    }
    if (msg) {
      this.utility.openSnackBar(msg, TipoMensagem.erro);
    }
  }
}

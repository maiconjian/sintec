import { UtilityService } from './../shared/utility.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(
    // private mensagem: MensagemComponent,
    private auth: AuthService,
    private util: UtilityService
  ) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.auth.isAccessTokenInvalido()) {
        console.log('Navegação com access token inválido, obtendo novo...');
        return this.auth.obterNovoAccessToken()
          .then(() => {
            if (this.auth.isAccessTokenInvalido()) {
              this.util.setNavegation('/login');
              return false;
            }
            return true;
          });
      } else if (next.data.roles && !this.auth.verificarPermissoes(next.data.roles)) {
        return false;
      }
      return true;
  }
  
}

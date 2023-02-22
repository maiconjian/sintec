import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  oauthTokenUrl = `${environment.apiUrl}/oauth/token`;
  jwtPayload:any;

  constructor(
      private http:HttpClient,
      private jwtHelper: JwtHelperService,
  ){}

  autenticar(login:string,senha:string):Promise<void>{
      const headers = new HttpHeaders()
          .append('Content-Type','application/x-www-form-urlencoded')
          .append('Authorization', 'Basic ZmxvcmlwYXJrOkZMSw==');
      
      const body = `client=floripark&username=${login}&password=${senha}&grant_type=password`;
      return this.http.post(this.oauthTokenUrl,body,{responseType:'json',headers,withCredentials:true})
      .toPromise()
      .then(response =>{
          this.armazenarToken(response['access_token']);
      })
      .catch(erro => {
          return Promise.reject(erro);
        });
      
  }

  private armazenarToken(token: string) {
      this.jwtPayload = this.jwtHelper.decodeToken(token);
      localStorage.setItem('token', token);
  }

  private carregarToken() {
      const token = localStorage.getItem('token');
      if (token) {
        this.armazenarToken(token);
      }
  }

    removerToken() {
      localStorage.removeItem('token');
      this.jwtPayload = null;
    }
  
    isAccessTokenInvalido() {
      const token = localStorage.getItem('token');
      return !token || this.jwtHelper.isTokenExpired(token);
    }
  
    getPermissao(permissao: string) {
      return this.jwtPayload && this.jwtPayload.authorities.includes(permissao);
    }
  
    verificarPermissoes(roles: any[]) {
      for (const role of roles) {
        if (this.getPermissao(role)) {
          return true;
        }
      }
      return false;
    }
  
    obterNovoAccessToken(): Promise<void> {
      const headers = new HttpHeaders()
        .append('Content-Type', 'application/x-www-form-urlencoded')
        .append('Authorization', 'Basic ZmxvcmlwYXJrOkZMSw==');
      const body = 'grant_type=refresh_token';
      return this.http.post<any>(this.oauthTokenUrl, body, { responseType: 'json', headers, withCredentials: true })
        .toPromise()
        .then(response => {
          console.log('Novo access token criado!');
          this.armazenarToken(response['access_token']);
          return Promise.resolve(null);
        })
        .catch(erro => {
          return Promise.resolve(null);
        });
    }
 
}

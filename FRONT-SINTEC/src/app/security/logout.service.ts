import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  tokensRevokeUrl = `${environment.apiUrl}/tokens/revoke`;

  constructor(
    private http: HttpService,
    private auth: AuthService,
  ) { }

  logout() {
    return this.http.delete(this.tokensRevokeUrl, { withCredentials: true })
    .toPromise()
    .then(() => {
      this.auth.removerToken();
    })
    .catch(erro => {
      return Promise.reject(erro);
    });
  }
}

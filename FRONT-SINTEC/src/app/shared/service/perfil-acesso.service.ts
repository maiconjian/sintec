import { PerfilAcessoFilter } from './../filter/perfil-acesso-filter';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class PerfilAcessoService {

  
  constructor(
    private http: HttpService
  ) { }

  pesquisar(filter: PerfilAcessoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? null : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.perfilAcesso}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

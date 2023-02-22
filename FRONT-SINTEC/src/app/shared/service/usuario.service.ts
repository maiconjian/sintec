import { UsuarioFilter } from './../filter/usuario-filter';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.usuario}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }

  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.usuario}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  pesquisar(filter: UsuarioFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional',  `${filter.idRegional == null || filter.idRegional == undefined ? 0 :filter.idRegional}`);
    params = params.append('idPerfil', `${filter.idPerfil == null || filter.idPerfil == undefined ? 0 : filter.idPerfil}`);
    params = params.append('nome', filter.nome == '' || filter.nome == null || filter.nome == undefined ? '' : filter.nome);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.usuario}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }

  resetPassword(idEntity: number): Promise<any> {
    return this.http.get<any>(`${environment.apiUrl}/${RequestMappingApi.usuario}/${UrlMappingApi.resetPassword}/${idEntity}`)
      .toPromise()
      .catch(erro => Promise.reject(erro));
  }
}

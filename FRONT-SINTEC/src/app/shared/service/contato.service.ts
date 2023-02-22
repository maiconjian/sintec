import { ContatoFilter } from './../filter/contato-filter';
import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';
import { ContratoFilter } from '../filter/contrato-filter';

@Injectable({
  providedIn: 'root'
})
export class ContatoService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.contato}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.contato}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }
  deletar(id:number): Promise<any>{
    return this.http.delete(`${environment.apiUrl}/${RequestMappingApi.contato}/${UrlMappingApi.deletar}/${id}`)
    .toPromise()
  }

  pesquisar(filter: ContatoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.contato}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

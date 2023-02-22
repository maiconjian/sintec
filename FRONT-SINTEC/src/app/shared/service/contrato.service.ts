import { ContratoFilter } from './../filter/contrato-filter';
import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class ContratoService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.contrato}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.contrato}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }
  pesquisar(filter: ContratoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idEmpresa', `${filter.idEmpresa == null || filter.idEmpresa == undefined ? 0 : filter.idEmpresa}`);
    params = params.append('nome', filter.nome == '' || filter.nome == null || filter.nome == undefined ? '' : filter.nome);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.contrato}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

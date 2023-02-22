import { HttpHeaders, HttpParams } from '@angular/common/http';
import { UrlMappingApi } from './../enum/url-mapping-api.enum';
import { RequestMappingApi } from './../enum/request-mapping-api.enum';
import { environment } from 'src/environments/environment';
import { EmpresaFilter } from './../filter/empresa-filter';
import { HttpService } from './../../security/http.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.empresa}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.empresa}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }
  pesquisar(filter: EmpresaFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('cnpj', filter.cnpj == '' || filter.cnpj == null || filter.cnpj == undefined ? 'null' : filter.cnpj);
    params = params.append('nomeFantasia', filter.nomeFantasia == '' || filter.nomeFantasia == null || filter.nomeFantasia == undefined ? 'null' : filter.nomeFantasia);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.empresa}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

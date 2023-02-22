import { RegionalFilter } from './../filter/regional-filter';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class RegionalService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.regional}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.regional}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }
  
  pesquisar(filter: RegionalFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idContrato',  `${filter.idContrato == null || filter.idContrato == undefined ? 0 :filter.idContrato}`);
    params = params.append('nome', filter.nome == '' || filter.nome == null || filter.nome == undefined ? '' : filter.nome);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.regional}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

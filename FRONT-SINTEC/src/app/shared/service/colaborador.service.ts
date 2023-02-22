import { ColaboradorFilter } from './../filter/colaborador-filter';
import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.colaborador}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.colaborador}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }


  pesquisar(filter: ColaboradorFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('matricula', `${filter.matricula == null || filter.matricula == undefined ? 0 : filter.matricula}`);
    params = params.append('nome', filter.nome == '' || filter.nome == null || filter.nome == undefined ? '' : filter.nome);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.colaborador}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }

  listaColaboradorSemVeiculo(idRegional:number): Promise<any> {
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.colaborador}/${UrlMappingApi.listaColaboradorSemVeiculo}/${idRegional}`)
      .toPromise()
  }
}

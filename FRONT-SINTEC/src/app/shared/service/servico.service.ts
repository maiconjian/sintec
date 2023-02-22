import { ServicoFilter } from './../filter/servico-filter';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';
import { HttpParams } from '@angular/common/http';
import { DistribuicaoFilter } from '../filter/distribuicao-filter';

@Injectable({
  providedIn: 'root'
})
export class ServicoService {

  constructor(
    private http: HttpService
  ) { }

  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.servico}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  buscarPorId(idServico: any): Promise<any> {
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.servico}/${UrlMappingApi.buscar}/${idServico}`)
      .toPromise()
  }

  agendarServico(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.servico}/${UrlMappingApi.agendarServico}`, entity)
      .toPromise()
  }

  pesquisar(filter: any): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('idTipoServico', `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 : filter.idTipoServico}`);
    params = params.append('dataRef', filter.dataRef == '' || filter.dataRef == null || filter.dataRef == undefined ? 'null' : filter.dataRef);
    params = params.append('ativo', `${filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.servico}/${UrlMappingApi.listaServicoPendente}`, { params })
      .toPromise()
  }
  
}

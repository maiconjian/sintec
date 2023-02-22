import { TipoServicoFilter } from './../filter/tipo-servico-filter';
import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';
import { UsuarioFilter } from '../filter/usuario-filter';

@Injectable({
  providedIn: 'root'
})
export class TipoServicoService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.tipoServico}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }

  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.tipoServico}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  pesquisar(filter: TipoServicoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idContrato',  `${filter.idContrato == null || filter.idContrato == undefined ? 0 :filter.idContrato}`);
    params = params.append('idCategoria',  `${filter.idCategoria == null || filter.idCategoria == undefined ? 0 :filter.idCategoria}`);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? null : filter.ativo}`);

    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.tipoServico}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

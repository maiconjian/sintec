import { VeiculoFilter } from './../filter/veiculo-filter';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { HttpParams } from '@angular/common/http';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    console.log(entity);
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.veiculo}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }

  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.veiculo}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  pesquisar(filter: VeiculoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional',  `${filter.idRegional == null || filter.idRegional == undefined ? 0 :filter.idRegional}`);
    params = params.append('placa', filter.placa == '' || filter.placa == null || filter.placa == undefined ? '' : filter.placa);
    params = params.append('idCategoriaVeiculo', `${filter.idCategoriaVeiculo == null || filter.idCategoriaVeiculo == undefined ? 0 :filter.idCategoriaVeiculo}`);
    params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.veiculo}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

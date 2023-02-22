import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class RetornoService {

  constructor(
    private http: HttpService
  ) { }

  pesquisar(filter: any): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('codigoServico', `${filter.codigoServico == null || filter.codigoServico == undefined ? 0 : filter.codigoServico}`);
    params = params.append('idTipoServico', `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 : filter.idTipoServico}`);
    params = params.append('dataRef', filter.dataRef == '' || filter.dataRef == null || filter.dataRef == undefined ? 'null' : filter.dataRef);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.retorno}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }

  getRespostas(idServico:any):Promise<any>{
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.retornoResposta}/${UrlMappingApi.getRespostas}/${idServico}`)
    .toPromise()
  }

  getFoto(caminho: any) {
    return `${environment.apiUrl}/${RequestMappingApi.retornoFoto}/${UrlMappingApi.getFoto}/${caminho}`;
  }

  getRetornoFotos(idServico:any):Promise<any>{
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.retornoFoto}/${UrlMappingApi.getRetornoFotos}/${idServico}`)
    .toPromise()
  }

}

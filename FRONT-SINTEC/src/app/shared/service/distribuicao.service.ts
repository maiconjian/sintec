import { DistribuicaoDto } from './../dto/distribuicao-dto';
import { DistribuicaoFilter } from './../filter/distribuicao-filter';
import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class DistribuicaoService {

  constructor(
    private http: HttpService
  ) { }

  associarServico(entity: DistribuicaoDto): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.associarServico}`, entity)
      .toPromise()
  }

  listaDistribuidoUsuario(filter:any): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('idTipoServico', `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 : filter.idTipoServico}`);
    params = params.append('dataRef', filter.dataRef == '' || filter.dataRef == null || filter.dataRef == undefined ? 'null' : filter.dataRef);
    params = params.append('idUsuario', `${filter.idUsuario == null || filter.idUsuario == undefined ? 0 : filter.idUsuario}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.listaDistribuidoUsuario}`, { params })
      .toPromise()
  }

  listaDistribuido(filter: DistribuicaoFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('idRegional', `${filter.idRegional == null || filter.idRegional == undefined ? 0 : filter.idRegional}`);
    params = params.append('idTipoServico', `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 : filter.idTipoServico}`);
    params = params.append('dataRef', filter.dataRef == '' || filter.dataRef == null || filter.dataRef == undefined ? 'null' : filter.dataRef);
    params = params.append('idUsuario', `${filter.idUsuario == null || filter.idUsuario == undefined ? 0 : filter.idUsuario}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.listaDistribuido}`, { params })
      .toPromise()
  }

  desassociar(idServico:number,idUsuario:number):Promise<any>{
    return this.http.delete(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.desassociar}/${idServico}/${idUsuario}`)
    .toPromise();
  }

  liberarServico(listaIdDistribuicao:any[]){
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.liberarServico}`, listaIdDistribuicao)
      .toPromise()
  }
  
  cancelarLiberacao(idDistribuicao:any){
    return this.http.delete(`${environment.apiUrl}/${RequestMappingApi.distribuicao}/${UrlMappingApi.cancelarLiberacao}/${idDistribuicao}`)
      .toPromise()
  }

}

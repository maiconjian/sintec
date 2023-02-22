import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';
import { QuestionarioFilter } from '../filter/questionario-filter';

@Injectable({
  providedIn: 'root'
})
export class AlternativaService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.alternativa}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.alternativa}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  listaAlternativaPergunta(idPergunta:number):Promise<any>{
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.alternativa}/${UrlMappingApi.listaAlternativaPergunta}/${idPergunta}`)
    .toPromise()
  }


  // pesquisar(filter: QuestionarioFilter): Promise<any> {
  //   let params = new HttpParams();
  //   params = params.append('idContrato', `${filter.idContrato == null || filter.idContrato == undefined ? 0 : filter.idContrato}`);
  //   params = params.append('idTipoServico', `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 : filter.idTipoServico}`);
  //   params = params.append('ativo', `${filter.ativo == null || filter.ativo == undefined ? 2 : filter.ativo}`);
  //   return this.http.get(`${environment.apiUrl}/${RequestMappingApi.pergunta}/${UrlMappingApi.pesquisar}`, { params })
  //     .toPromise()
  // }
}

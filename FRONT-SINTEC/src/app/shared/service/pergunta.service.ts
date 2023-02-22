
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';

@Injectable({
  providedIn: 'root'
})
export class PerguntaService {

  constructor(
    private http: HttpService
  ) { }

  incluir(entity: any): Promise<any> {
    return this.http.post(`${environment.apiUrl}/${RequestMappingApi.pergunta}/${UrlMappingApi.incluir}`, entity)
      .toPromise()
  }
  alterar(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.pergunta}/${UrlMappingApi.alterar}`, entity)
      .toPromise()
  }

  listaPerguntaQuestionario(idQuestionario:number):Promise<any>{
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.pergunta}/${UrlMappingApi.listaPerguntaQuestionario}/${idQuestionario}`)
    .toPromise()
  }

  alterarSituacaoPergunta(entity: any): Promise<any> {
    return this.http.put(`${environment.apiUrl}/${RequestMappingApi.pergunta}/${UrlMappingApi.alterarSituacaoPergunta}`, entity)
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

import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/security/http.service';
import { environment } from 'src/environments/environment';
import { RequestMappingApi } from '../enum/request-mapping-api.enum';
import { UrlMappingApi } from '../enum/url-mapping-api.enum';
import { RacpFilter } from '../filter/racp-filter';

@Injectable({
  providedIn: 'root'
})
export class RacpService {

  constructor(
    private http: HttpService
  ) { }

  pesquisar(filter: RacpFilter): Promise<any> {
    let params = new HttpParams();
    params = params.append('codigoServico', filter.codigoServico == '' || filter.codigoServico == null || filter.codigoServico == undefined ? '' : filter.codigoServico);
    params = params.append('codigoRacp', filter.codigoRacp == '' || filter.codigoRacp == null || filter.codigoRacp == undefined ? '' : filter.codigoRacp);
    params = params.append('dataRef', filter.dataRef == '' || filter.dataRef == null || filter.dataRef == undefined ? '' : filter.dataRef);
    params = params.append('idRegional',  `${filter.idRegional == null || filter.idRegional == undefined ? 0 :filter.idRegional}`);
    params = params.append('idTipoServico',  `${filter.idTipoServico == null || filter.idTipoServico == undefined ? 0 :filter.idTipoServico}`);
    params = params.append('idSituacaoRacp', `${filter.idSituacaoRacp == null || filter.idSituacaoRacp == undefined ? 2 : filter.idSituacaoRacp}`);
    return this.http.get(`${environment.apiUrl}/${RequestMappingApi.racp}/${UrlMappingApi.pesquisar}`, { params })
      .toPromise()
  }
}

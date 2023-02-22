import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import br from '@angular/common/locales/br';
import { HashLocationStrategy, LocationStrategy, registerLocaleData } from '@angular/common';
import { JwtHelperService, JwtModule } from '@auth0/angular-jwt';
import { BrMaskerModule, BrMaskDirective} from 'br-mask'



import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatFormFieldModule} from '@angular/material/form-field';

import {GMapModule} from 'primeng/gmap';
import {GalleriaModule} from 'primeng/galleria';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './pages/login/login.component';
import { environment } from 'src/environments/environment';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './template/header/header.component';
import { NavComponent } from './template/nav/nav.component';
import { HomeComponent } from './pages/home/home.component';
import { NaoAutorizadoComponent } from './core/nao-autorizado.component';
import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';
import { HasPermissionDirective } from './core/has-permission.directive';
import { UtilityService } from './shared/utility.service';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { EmpresaComponent } from './pages/empresa/empresa.component';
import { SpinnerComponent } from './core/spinner/spinner.component';
import { ModalEmpresaComponent } from './pages/empresa/modal-empresa/modal-empresa.component';
import { UsuarioComponent } from './pages/usuario/usuario.component';
import { ModalUsuarioComponent } from './pages/usuario/modal-usuario/modal-usuario.component';
import { ConfirmDialogComponent } from './core/confirm-dialog/confirm-dialog.component';
import { ContratoComponent } from './pages/contrato/contrato.component';
import { ModalContratoComponent } from './pages/contrato/modal-contrato/modal-contrato.component';
import { TipoServicoComponent } from './pages/tipo-servico/tipo-servico.component';
import { ModalTipoServicoComponent } from './pages/tipo-servico/modal-tipo-servico/modal-tipo-servico.component';
import { RegionalComponent } from './pages/regional/regional.component';
import { ModalRegionalComponent } from './pages/regional/modal-regional/modal-regional.component';
import { ModalListaContatoComponent } from './pages/regional/modal-lista-contato/modal-lista-contato.component';
import { ModalContatoComponent } from './pages/regional/modal-contato/modal-contato.component';
import { LocalidadeComponent } from './pages/imovel/imovel.component';
import { ModalLocalidadeComponent } from './pages/imovel/modal-imovel/modal-imovel.component';
import { ColaboradorComponent } from './pages/colaborador/colaborador.component';
import { ModalColaboradorComponent } from './pages/colaborador/modal-colaborador/modal-colaborador.component';
import { DistribuicaoComponent } from './pages/distribuicao/distribuicao.component';
import { LiberacaoComponent } from './pages/liberacao/liberacao.component';
import { QuestionarioComponent } from './pages/questionario/questionario.component';
import { ModalQuestionarioComponent } from './pages/questionario/modal-questionario/modal-questionario.component';
import { ModalPerguntaComponent } from './pages/questionario/modal-pergunta/modal-pergunta.component';
import { ModalAlternativaComponent } from './pages/questionario/modal-alternativa/modal-alternativa.component';
import { AgendaComponent } from './pages/agenda/agenda.component';
import { ModalMapaComponent } from './pages/retorno-servico/modal-mapa/modal-mapa.component';
import { ModalAgendaComponent } from './pages/agenda/modal-agenda/modal-agenda.component';
import { RetornoServicoComponent } from './pages/retorno-servico/retorno-servico.component';
import { ModalDetalhadoComponent } from './pages/retorno-servico/modal-detalhado/modal-detalhado.component';
import { VeiculoComponent } from './pages/veiculo/veiculo.component';
import { ModalVeiculoComponent } from './pages/veiculo/modal-veiculo/modal-veiculo.component';
import { ModalAgendamentoComponent } from './pages/agenda/modal-agendamento/modal-agendamento.component';
import { ListaColaboradorComponent } from './pages/agenda/modal-agendamento/lista-colaborador/lista-colaborador.component';
import { ListaImovelComponent } from './pages/agenda/modal-agendamento/lista-imovel/lista-imovel.component';
import { ListaVeiculoComponent } from './pages/agenda/modal-agendamento/lista-veiculo/lista-veiculo.component';
import { ModalResponsavelComponent } from './pages/veiculo/modal-responsavel/modal-responsavel.component';
import { ModalFotoComponent } from './pages/retorno-servico/modal-foto/modal-foto.component';
import { RacpComponent } from './pages/racp/racp.component';
import { NconfComponent } from './pages/racp/nconf/nconf.component';


export function tokenGetter() {
  return localStorage.getItem('token');
}

registerLocaleData(br, 'pt-BR');

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    NavComponent,
    HomeComponent,
    NaoAutorizadoComponent,
    PaginaNaoEncontradaComponent,
    HasPermissionDirective,
    EmpresaComponent,
    SpinnerComponent,
    ModalEmpresaComponent,
    UsuarioComponent,
    ModalUsuarioComponent,
    ConfirmDialogComponent,
    ContratoComponent,
    ModalContratoComponent,
    TipoServicoComponent,
    ModalTipoServicoComponent,
    RegionalComponent,
    ModalRegionalComponent,
    ModalListaContatoComponent,
    ModalContatoComponent,
    LocalidadeComponent,
    ModalLocalidadeComponent,
    ColaboradorComponent,
    ModalColaboradorComponent,
    DistribuicaoComponent,
    LiberacaoComponent,
    QuestionarioComponent,
    ModalQuestionarioComponent,
    ModalPerguntaComponent,
    ModalAlternativaComponent,
    AgendaComponent,
    ModalMapaComponent,
    ModalAgendaComponent,
    RetornoServicoComponent,
    ModalDetalhadoComponent,
    VeiculoComponent,
    ModalVeiculoComponent,
    ModalAgendaComponent,
    ModalAgendamentoComponent,
    ListaColaboradorComponent,
    ListaImovelComponent,
    ListaVeiculoComponent,
    ModalResponsavelComponent,
    ModalFotoComponent,
    RacpComponent,
    NconfComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatSnackBarModule,
    HttpClientModule,
    MatToolbarModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatGridListModule,
    MatInputModule,
    MatButtonModule,
    BrMaskerModule,
    MatSelectModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatDialogModule,
    MatTooltipModule,
    MatTabsModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    GMapModule,
    GalleriaModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: environment.tokenWhitelistedDomains,
        disallowedRoutes: environment.tokenBlacklistedRoutes
      }
    })
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'pt-BR'},
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    HttpClient,
    UtilityService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

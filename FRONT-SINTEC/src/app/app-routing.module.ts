import { VeiculoComponent } from './pages/veiculo/veiculo.component';
import { RetornoServicoComponent } from './pages/retorno-servico/retorno-servico.component';
import { AgendaComponent } from './pages/agenda/agenda.component';
import { QuestionarioComponent } from './pages/questionario/questionario.component';
import { LiberacaoComponent } from './pages/liberacao/liberacao.component';
import { DistribuicaoComponent } from './pages/distribuicao/distribuicao.component';
import { ColaboradorComponent } from './pages/colaborador/colaborador.component';
import { LocalidadeComponent } from './pages/imovel/imovel.component';
import { RegionalComponent } from './pages/regional/regional.component';
import { TipoServicoComponent } from './pages/tipo-servico/tipo-servico.component';
import { ContratoComponent } from './pages/contrato/contrato.component';
import { UsuarioComponent } from './pages/usuario/usuario.component';
import { EmpresaComponent } from './pages/empresa/empresa.component';
import { NaoAutorizadoComponent } from './core/nao-autorizado.component';
import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';
import { HomeComponent } from './pages/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RacpComponent } from './pages/racp/racp.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path:'home',
    component:HomeComponent
  },
  {
    path:'usuario',
    component:UsuarioComponent
  },
  {
    path:'empresa',
    component:EmpresaComponent
  },
  {
    path:'contrato',
    component:ContratoComponent
  },
  {
    path:'tipo-servico',
    component:TipoServicoComponent
  },
  {
    path:'regional',
    component:RegionalComponent
  },
  {
    path:'localidade',
    component:LocalidadeComponent
  },
  {
    path:'colaborador',
    component:ColaboradorComponent
  },
  {
    path:'veiculo',
    component:VeiculoComponent
  },
  {
    path:'agenda',
    component:AgendaComponent
  },
  {path:'retorno-servico',
   component:RetornoServicoComponent  
  },
  {
    path:'distribuicao',
    component:DistribuicaoComponent
  },
  {
    path:'liberacao',
    component:LiberacaoComponent
  },
  {
    path:'racp',
    component:RacpComponent
  },
  {
    path:'questionario',
    component:QuestionarioComponent
  },
  {
    path:'pagina-nao-autorizada',
    component:NaoAutorizadoComponent
  },
  {
    path:'pagina-nao-encontrada',
    component:PaginaNaoEncontradaComponent
  },
  {
    path: '**',
    redirectTo: 'pagina-nao-encontrada'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

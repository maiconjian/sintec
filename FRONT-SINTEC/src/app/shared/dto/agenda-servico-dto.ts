import { Imovel } from '../model/imovel';
import { Colaborador } from 'src/app/shared/model/colaborador';
import { Veiculo } from '../model/veiculo';
export class AgendarServicoDto{
    listaColaborador:Colaborador[];
	listaImovel:Imovel[];
	listaVeiculo:Veiculo[];
	idTipoServico:number;
	idRegional:number;
	idCategoriaTipoServico:number;
	dataSolicitacao:string;
	numDiasAgendamento:number;
}
import { Colaborador } from './colaborador';
import { TipoServico } from './tipo-servico';
export class Servico{
    
    id:number;
	codigo:number;
	dataRef:string;
	tipoServico:TipoServico;
	colaborador:Colaborador;
	dataSolicitacao:string;
	numDiasAgendamento:number;
	dataProgramada:string;
	flagAvulsa:number;
	dataAtualizacao:string;
	ativo:number;
}
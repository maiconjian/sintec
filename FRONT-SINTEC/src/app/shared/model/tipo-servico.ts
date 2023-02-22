import { Contrato } from 'src/app/shared/model/contrato';
export class TipoServico{
    id:number;
	nome:string;
	numDiasAgendamento:number;
	contrato:Contrato;
    dataAtualziacao:string;
	ativo:number;
}
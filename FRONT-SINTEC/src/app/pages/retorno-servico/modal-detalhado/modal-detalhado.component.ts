import { ModalFotoComponent } from './../modal-foto/modal-foto.component';
import { RetornoService } from './../../../shared/service/retorno.service';
import { UtilityService } from 'src/app/shared/utility.service';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

export class Group {
  level: number = 0;
  parent: Group;
  expanded: boolean = true;
  get visible(): boolean {
    return !this.parent || (this.parent.visible && this.parent.expanded);
  }
}


@Component({
  selector: 'app-modal-detalhado',
  templateUrl: './modal-detalhado.component.html',
  styleUrls: ['./modal-detalhado.component.scss']
})

export class ModalDetalhadoComponent implements OnInit {

  pathAssinatura:string;
  images:any[]=[];
  responsiveOptions:any[] = [
    {
        breakpoint: '1024px',
        numVisible: 5
    },
    {
        breakpoint: '768px',
        numVisible: 3
    },
    {
        breakpoint: '560px',
        numVisible: 1
    }
  ];

  dataSolicitacao:string;
  dataProgramada:string;
  dataExecucao:string;

  displayedColumns: string[] = ['enunciado','resposta','nconf'];
  dataSource = new MatTableDataSource<any | Group>([]);
  // @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  groupByColumns: string[] = ['tituloQuestionario'];

  constructor(
    private utility:UtilityService,
    private retornoService:RetornoService,
    private dialog: MatDialog,
    private dialogRef:MatDialogRef<ModalDetalhadoComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any 
  ) { }

  ngOnInit(): void {
   this.dataSolicitacao = this.utility.formatDataUsForBr(this.data.dataSolicitacao);
   this.dataProgramada = this.utility.formatDataUsForBr(this.data.dataProgramada);
   this.dataExecucao = this.utility.formatarDataHoraBRString(this.data.dataExecucao);
    // this.dataSource.paginator = this.utility.getTraducaoTabela(this.paginator);
   this.getRespostas();
   this.getRetornoFotos();
  }

  getRespostas(){
    this.retornoService.getRespostas(this.data.idServico)
    .then(response=>{
      // this.dataSource.data = response;
      this.dataSource.data = this.addGroups(response, this.groupByColumns);
      this.dataSource.filterPredicate = this.customFilterPredicate.bind(this);
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // table questionario

  customFilterPredicate(data: any | Group, filter: string): boolean {
    return (data instanceof Group) ? data.visible : this.getDataRowVisible(data);
  }

  getDataRowVisible(data: any): boolean {
    const groupRows = this.dataSource.data.filter(
      row => {
        if (!(row instanceof Group)) return false;
        
        let match = true;
        this.groupByColumns.forEach(
          column => {
            if (!row[column] || !data[column] || row[column] !== data[column]) match = false;
          }
        );
        return match;
      }
    );

    if (groupRows.length === 0) return true;
    if (groupRows.length > 1) throw "Data row is in more than one group!";
    const parent = <Group>groupRows[0];  // </Group> (Fix syntax coloring)

    return parent.visible && parent.expanded;
  }

  groupHeaderClick(row) {
    row.expanded = !row.expanded
    this.dataSource.filter = performance.now().toString();  // hack to trigger filter refresh
  }

  addGroups(data: any[], groupByColumns: string[]): any[] {
    var rootGroup = new Group();
    return this.getSublevel(data, 0, groupByColumns, rootGroup);
  }

  getSublevel(data: any[], level: number, groupByColumns: string[], parent: Group): any[] {
    // Recursive function, stop when there are no more levels. 
    if (level >= groupByColumns.length)
      return data;

    var groups = this.uniqueBy(
      data.map(
        row => {
          var result = new Group();
          result.level = level + 1;
          result.parent = parent;
          for (var i = 0; i <= level; i++)
            result[groupByColumns[i]] = row[groupByColumns[i]];
          return result;
        }
      ),
      JSON.stringify);

    const currentColumn = groupByColumns[level];

    var subGroups = [];
    groups.forEach(group => {
      let rowsInGroup = data.filter(row => group[currentColumn] === row[currentColumn])
      let subGroup = this.getSublevel(rowsInGroup, level + 1, groupByColumns, group);
      subGroup.unshift(group);
      subGroups = subGroups.concat(subGroup);
    })
    return subGroups;
  }

  uniqueBy(a, key) {
    var seen = {};
    return a.filter(function (item) {
      var k = key(item);
      return seen.hasOwnProperty(k) ? false : (seen[k] = true);
    })
  }

  isGroup(index, item): boolean {
    return item.level;
  }

  // foto

  getRetornoFotos(){
    let lista:any[]=[];
    this.retornoService.getRetornoFotos(this.data.idServico)
    .then(response=>{
      for (let i = 0; i < response.length; i++) {
        if(response[i].flagAssinatura == 1){
          let responsePath = response[i].path
          let path= responsePath.substring(responsePath.indexOf('sintec/')+7, responsePath.length).replaceAll('/', '-')
          this.pathAssinatura = this.getImage(path);
        }else{
          let responsePath = response[i].path
          let path= responsePath.substring(responsePath.indexOf('sintec/')+7, responsePath.length).replaceAll('/', '-')
           lista.push({
            titulo: response[i].titulo,
            observacao:response[i].observacao,
            previewImageSrc: this.getImage(path),
          });
         
        }
        
      }
      this.images = lista;
    })
  }

  getImage(path:any){
    return this.retornoService.getFoto(path);
  }


  // openDialogFoto(){
  //   const dialogDetalhado = this.dialog.open(ModalFotoComponent, {
  //     width: '90%',
  //     height: '90%',
  //     position: { top: '20px' },
  //     data: lista
  //   });
  // }

}

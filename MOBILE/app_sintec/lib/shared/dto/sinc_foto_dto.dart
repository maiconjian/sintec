class SincFotoDto{
   String? nome;
   String? latitude;
   String? longitude;
   String? image;
   int? idServico;
   int? idQuestionario;
   int? flagAssinatura;
   String? dataAtualizacao;
   String? observacao;

   SincFotoDto({
      this.nome,
      this.latitude,
      this.longitude,
      this.image,
      this.idServico,
      this.idQuestionario,
      this.flagAssinatura,
      this.dataAtualizacao,
      this.observacao
});

   Map<String, dynamic> toJson() {
     final Map<String, dynamic> data = new Map<String, dynamic>();
     data['nome'] = this.nome;
     data['latitude'] = this.latitude;
     data['longitude'] = this.longitude ;
     data['image'] = this.image;
     data['idServico'] = this.idServico;
     data['idQuestionario'] = this.idQuestionario;
     data['flagAssinatura'] = this.flagAssinatura;
     data['dataAtualizacao'] = this.dataAtualizacao;
     data['observacao']=this.observacao;
     return data;
   }

}
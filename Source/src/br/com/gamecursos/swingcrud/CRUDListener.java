package br.com.gamecursos.swingcrud;

public interface CRUDListener<T> {

   public boolean antesBotaoIncluir();
   public void aposBotaoIncluir();
   
   public boolean antesBotaoAlterar();
   public void aposBotaoAlterar();
   
   public boolean antesBotaoGravar();
   public void acaoGravarInclusao(T objeto) throws Exception;
   public void acaoGravarAlteracao(T objeto) throws Exception;
   public void aposBotaoGravar();
   
   public boolean antesBotaoCancelar();
   public void aposBotaoCancelar();
   
   public boolean antesBotaoExcluir();
   public void acaoExcluir(T objeto) throws Exception;
   public void aposBotaoExcluir();
   
}

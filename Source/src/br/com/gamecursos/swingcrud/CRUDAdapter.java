package br.com.gamecursos.swingcrud;

public class CRUDAdapter<T> implements CRUDListener<T> {

   @Override
   public boolean antesBotaoIncluir() {
      return true;
   }

   @Override
   public void aposBotaoIncluir() {
   }

   @Override
   public boolean antesBotaoAlterar() {
      return true;
   }

   @Override
   public void aposBotaoAlterar() {
   }

   @Override
   public boolean antesBotaoGravar() {
      return true;
   }

   @Override
   public void acaoGravarInclusao(T objeto) throws Exception {
   }

   @Override
   public void acaoGravarAlteracao(T objeto) throws Exception {
   }

   @Override
   public void aposBotaoGravar() {
   }

   @Override
   public boolean antesBotaoCancelar() {
      return true;
   }

   @Override
   public void aposBotaoCancelar() {
   }

   @Override
   public boolean antesBotaoExcluir() {
      return true;
   }

   @Override
   public void acaoExcluir(T objeto) throws Exception {
   }

   @Override
   public void aposBotaoExcluir() {
   }

}

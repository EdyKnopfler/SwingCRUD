package br.com.gamecursos.swingcrud;

import java.util.*;

import javax.swing.table.AbstractTableModel;

public abstract class TableModelEntidade<T> extends AbstractTableModel {

   private List<T> lista = new ArrayList<>();
   private String[] colunas;
   private ControladorCRUD<T> controlador;
   
   public TableModelEntidade() {
      colunas = getColunas();
   }
   
   public void setControlador(ControladorCRUD<T> controlador) {
      this.controlador = controlador;
   }
   
   public void setListaObjetos(List<T> lista) {
      this.lista = lista;
      fireTableDataChanged();
      controlador.limpouTela();
   }
   
   @Override
   public int getRowCount() {
      return lista.size();
   }

   @Override
   public int getColumnCount() {
      return colunas.length;
   }
   
   @Override
   public String getColumnName(int column) {
      return colunas[column];
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      T entidade = lista.get(rowIndex);
      return getDadoColuna(columnIndex, entidade);
   }
   
   public T get(int indice) {
      return lista.get(indice);
   }
   
   public void incluir(T objeto) {
      int novoIndice = lista.size();
      lista.add(objeto);
      fireTableRowsInserted(novoIndice, novoIndice);
   }
   
   public void alterou(int indice, T objeto) {
      lista.set(indice, objeto);
      fireTableRowsUpdated(indice, indice);
   }
   
   public void excluir(int indice) {
      lista.remove(indice);
      fireTableRowsDeleted(indice, indice);
   }
   
   public abstract String[] getColunas();
   public abstract Object getDadoColuna(int coluna, T objeto);

}

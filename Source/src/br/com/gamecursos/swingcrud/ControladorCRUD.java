package br.com.gamecursos.swingcrud;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import static javax.swing.JOptionPane.*;

public class ControladorCRUD<T> {

   private enum Estado { INCLUINDO, ALTERANDO };
   
   private TableModelEntidade<T> tableModel;
   private PainelCampos<T> painelCampos;
   private CRUDListener<T> crudListener;
   
   private JTable tabela;
   private JButton incluir = new JButton("Incluir");
   private JButton alterar = new JButton("Alterar");
   private JButton gravar = new JButton("Gravar");
   private JButton cancelar = new JButton("Cancelar");
   private JButton excluir = new JButton("Excluir");
   
   private Estado estadoAtual;   
   
   public ControladorCRUD(TableModelEntidade<T> tableModel, 
                          PainelCampos<T> painelCampos,
                          CRUDListener<T> crudListener) {
      
      this.tableModel = tableModel;
      this.painelCampos = painelCampos;
      this.crudListener = crudListener;
      
      tableModel.setControlador(this);
      this.tabela = new JTable(tableModel);
      tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      tabela.addMouseListener(new CliqueTabela());
      
      incluir.addActionListener(new AcaoIncluir());
      alterar.addActionListener(new AcaoAlterar());
      gravar.addActionListener(new AcaoGravar());
      cancelar.addActionListener(new AcaoCancelar());
      excluir.addActionListener(new AcaoExcluir());
      
      habilitarEdicao(false);
   }
   
   private void habilitarEdicao(boolean habilitar) {
      boolean temLinhaSelecionada = (tabela.getSelectedRow() != -1);
      
      tabela.setEnabled(!habilitar);
      
      incluir.setEnabled(!habilitar);
      alterar.setEnabled(!habilitar && temLinhaSelecionada);
      gravar.setEnabled(habilitar);
      cancelar.setEnabled(habilitar);
      excluir.setEnabled(!habilitar && temLinhaSelecionada);
      
      painelCampos.habilitarCampos(habilitar);
   }
   
   private class AcaoIncluir implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (!crudListener.antesBotaoIncluir())
            return;
         
         tabela.getSelectionModel().clearSelection();;
         painelCampos.limpar();
         habilitarEdicao(true);
         estadoAtual = Estado.INCLUINDO;
         
         crudListener.aposBotaoIncluir();
      }
   }
   
   private class AcaoAlterar implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (!crudListener.antesBotaoAlterar())
            return;
         
         habilitarEdicao(true);
         estadoAtual = Estado.ALTERANDO;
         
         crudListener.aposBotaoAlterar();
      }
   }
   
   private class AcaoGravar implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (!crudListener.antesBotaoGravar())
            return;
         
         try {
            if (estadoAtual == Estado.INCLUINDO) {
               T objeto = painelCampos.novoObjeto();
               
               crudListener.acaoGravarInclusao(objeto);
               tableModel.incluir(objeto);
               int indice = tableModel.getRowCount() - 1;
               tabela.getSelectionModel().setSelectionInterval(
                     indice, indice);
               finaliza();
            }
            else {
               int selecionado = tabela.getSelectedRow();
               T objeto = painelCampos.objetoSendoAlterado();
               
               crudListener.acaoGravarAlteracao(objeto);
               tableModel.alterou(selecionado, objeto);
               finaliza();
            }
         }
         catch (CRUDException crex) {
            // A ação de inclusão/alteração pode lançar exceção
            showMessageDialog(null, crex.getMessage(), "Dados incorretos", 
                  WARNING_MESSAGE);;
         }
         catch (Exception ex) {
            showMessageDialog(null, ex.getMessage(), "ERRO AO GRAVAR", ERROR_MESSAGE);
            ex.printStackTrace();
         }
      }
      
      private void finaliza() {
         int selecionado = tabela.getSelectedRow();
         T objeto = tableModel.get(selecionado);
         painelCampos.exibir(objeto);
         habilitarEdicao(false);
         crudListener.aposBotaoGravar();
      }
   }
   
   private class AcaoCancelar implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (!crudListener.antesBotaoCancelar())
            return;
         
         int selecionado = tabela.getSelectedRow();
         
         if (selecionado != -1) 
            painelCampos.exibir(tableModel.get(selecionado));
         else
            painelCampos.limpar();
         
         habilitarEdicao(false);
         
         crudListener.aposBotaoCancelar();
      }
   }
   
   private class AcaoExcluir implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (!crudListener.antesBotaoExcluir())
            return;
         
         int selecionado = tabela.getSelectedRow();
         T objeto = tableModel.get(selecionado);
         
         try {
            crudListener.acaoExcluir(objeto);
            tableModel.excluir(selecionado);
            painelCampos.limpar();
            habilitarEdicao(false);
            crudListener.aposBotaoExcluir();
         } 
         catch (Exception ex) {
            showMessageDialog(null, ex.getMessage(), "ERRO AO EXCLUIR", 
                  ERROR_MESSAGE);
            ex.printStackTrace();
         }
         
      }
   }
   
   private class CliqueTabela extends MouseAdapter {
      
      @Override
      public void mouseClicked(MouseEvent e) {
         if (!tabela.isEnabled())
            return;
         
         if (e.getClickCount() == 2) {
            alterar.doClick();
         }
         else {
            habilitarEdicao(false);
            int selecionado = tabela.getSelectedRow();
            
            if (selecionado != -1) {
               T objeto = tableModel.get(selecionado);
               painelCampos.exibir(objeto);
            }
            else {
               painelCampos.limpar();
            }
         }
      }
      
   }
   
   public void limpouTela() {
      habilitarEdicao(false);
      painelCampos.limpar();
   }
   
   public void setCRUDListener(CRUDListener<T> listener) {
      this.crudListener = listener;
   }
   
   public JTable getTabela() {
      return tabela;
   }
   
   public JButton getIncluir() {
      return incluir;
   }

   public JButton getAlterar() {
      return alterar;
   }

   public JButton getGravar() {
      return gravar;
   }

   public JButton getCancelar() {
      return cancelar;
   }

   public JButton getExcluir() {
      return excluir;
   }
   
}

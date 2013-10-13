package br.com.gamecursos.swingcrud.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.gamecursos.swingcrud.CRUDAdapter;
import br.com.gamecursos.swingcrud.CRUDListener;
import br.com.gamecursos.swingcrud.PainelCampos;
import br.com.gamecursos.swingcrud.TableModelEntidade;

public class FormContato extends FormPadrao<Contato> {

   private TableModelContato tableModel;
   private PainelContato painelContato;
   private CrudContatoListener crudListener;
   
   private JTextField txtPesquisa;
   
   public FormContato() {
      setTitle("Testando Controlador SwingCRUD");
      setSize(600, 500);
      
      tamanhoColuna(0, 250);
      
      // Painel de pesquisa customizado
      txtPesquisa = new JTextField(20);
      JButton btnPesquisar = new JButton("Pesquisar >>");
      txtPesquisa.addActionListener(new AcaoPesquisar());
      btnPesquisar.addActionListener(new AcaoPesquisar());
      
      getPainelPesquisa().add(new JLabel("Procurar:"));
      getPainelPesquisa().add(txtPesquisa);
      getPainelPesquisa().add(btnPesquisar);
      
      // Simulando o banco de dados
      List<Contato> lista = new ArrayList<Contato>();
      lista.add(new Contato("João", "1111-1111", Calendar.getInstance()));
      lista.add(new Contato("Maria", "2222-2222", Calendar.getInstance()));
      lista.add(new Contato("Pedro", "3333-3333", Calendar.getInstance()));
      tableModel.setListaObjetos(lista);
   }
   
   @Override
   public TableModelEntidade<Contato> criaTableModel() {
      // Criamos e guardamos referência ao TableModel 
      tableModel = new TableModelContato();
      return tableModel;
   }

   @Override
   public PainelCampos<Contato> criaPainelCampos() {
      // Criamos e guardamos referência ao painel de campos
      painelContato = new PainelContato();
      return painelContato;
   }

   @Override
   public CRUDListener<Contato> criaCrudListener() {
      // Criamos e guardamos referência ao ouvinte dos eventos 
      crudListener = new CrudContatoListener();
      return crudListener;
   }
   
   private class CrudContatoListener extends CRUDAdapter<Contato> {

      // Aqui ficam os eventos do CRUD:
      // - as ações de incluir, alterar e excluir
      // - eventos antes e após estas ações
      // Os eventos antes de ações podem retornar false para impedir a ação
      // (Sim, eu copiei o modelo do Dataset do Delphi aqui :D)
      
      @Override
      public void aposBotaoIncluir() {
         painelContato.focoEmNome();
      }

      @Override
      public void aposBotaoAlterar() {
         painelContato.focoEmNome();
      }

      @Override
      public void acaoGravarInclusao(Contato contato) throws Exception {
         // Aqui eu mando incluir o contato no banco de dados (ou lanço uma exceção
         // caso falhe)
      }

      @Override
      public void acaoGravarAlteracao(Contato contato) throws Exception {
         // Aqui eu mando atualizar o contato no banco de dados (ou lanço uma exceção
         // caso falhe)
      }

      @Override
      public void aposBotaoGravar() {
         getControlador().getAlterar().requestFocus();
      }

      @Override
      public boolean antesBotaoExcluir() {
         if (JOptionPane.showConfirmDialog(null, "Tem certeza?", "Excluir",
               JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            return true;
         else
            return false;
      }

      @Override
      public void acaoExcluir(Contato contato) throws Exception {
         // Aqui eu mando excluir o contato do banco de dados (ou lanço uma exceção
         // caso falhe)
      }

      @Override
      public void aposBotaoExcluir() {
         // É bom direcionar o foco para algum lugar, de modo que o usuário
         // não fique perdido...
         getControlador().getIncluir().requestFocus();         
      }
      
   }
   
   private class AcaoPesquisar implements ActionListener {

      // Simulando uma pesquisa no banco de dados...
      
      @Override
      public void actionPerformed(ActionEvent e) {
         List<Contato> lista = new ArrayList<Contato>();
         lista.add(new Contato("Carla", "4444-4444", Calendar.getInstance()));
         lista.add(new Contato("Priscila", "5555-5555", Calendar.getInstance()));
         lista.add(new Contato("Antônio", "6666-6666", Calendar.getInstance()));
         tableModel.setListaObjetos(lista); 
      }
      
   }

}

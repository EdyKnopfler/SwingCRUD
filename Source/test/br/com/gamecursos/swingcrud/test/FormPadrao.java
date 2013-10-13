package br.com.gamecursos.swingcrud.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.gamecursos.swingcrud.CRUDListener;
import br.com.gamecursos.swingcrud.ControladorCRUD;
import br.com.gamecursos.swingcrud.PainelCampos;
import br.com.gamecursos.swingcrud.TableModelEntidade;

public abstract class FormPadrao<T> extends JFrame {
   
   private TableModelEntidade<T> tableModel;
   private PainelCampos<T> painelCampos;
   private CRUDListener<T> crudListener;
   
   private ControladorCRUD<T> controlador;
   
   // Será customizável pelos filhos, o form pai apenas reserva espaço
   private JPanel painelPesquisa;
   
   public FormPadrao() {
      // Criando as dependências do controlador
      tableModel = criaTableModel();
      painelCampos = criaPainelCampos();
      crudListener = criaCrudListener();
      
      // Criando o controlador
      controlador = new ControladorCRUD<T>(tableModel, painelCampos,
            crudListener);
      
      // Obtendo os componentes criados pelo controlador
      JTable tabela = controlador.getTabela();
      JButton incluir = controlador.getIncluir();
      JButton alterar = controlador.getAlterar();
      JButton gravar = controlador.getGravar();
      JButton cancelar = controlador.getCancelar();
      JButton excluir = controlador.getExcluir();
      
      // Personalizando os componentes
      Dimension d = new Dimension(100, 30);
      incluir.setPreferredSize(d);
      alterar.setPreferredSize(d);
      gravar.setPreferredSize(d);
      cancelar.setPreferredSize(d);
      excluir.setPreferredSize(d);

      // Layout do formulário a gosto - é VOCÊ que está criando um layout padrão.
      painelPesquisa = new JPanel();
      JPanel painelBotoes = new JPanel();
      painelBotoes.add(incluir);
      painelBotoes.add(alterar);
      painelBotoes.add(gravar);
      painelBotoes.add(cancelar);
      painelBotoes.add(excluir);
      
      JPanel painelNorte = new JPanel();
      painelNorte.setLayout(new BoxLayout(painelNorte, BoxLayout.Y_AXIS));
      painelNorte.add(painelBotoes);
      painelNorte.add(painelPesquisa);
      
      JScrollPane scroll = new JScrollPane(tabela);
      scroll.getViewport().setBackground(Color.WHITE);
      
      Container c = getContentPane();
      c.setLayout(new BorderLayout());
      c.add(painelNorte, BorderLayout.NORTH);
      c.add(scroll, BorderLayout.CENTER);
      c.add(painelCampos, BorderLayout.SOUTH);

      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }
   
   protected JPanel getPainelPesquisa() {
      // Permitir aos filhos customizar o painel
      return painelPesquisa;
   }
   
   protected ControladorCRUD<T> getControlador() {
      // Permitir aos filhos interagir com os componentes criados
      return controlador;
   }
   
   protected void tamanhoColuna(int indice, int largura) {
      getControlador().getTabela().getColumnModel().getColumn(indice)
            .setPreferredWidth(largura);
   }

   
   public abstract TableModelEntidade<T> criaTableModel();
   public abstract PainelCampos<T> criaPainelCampos();
   public abstract CRUDListener<T> criaCrudListener();
   
}

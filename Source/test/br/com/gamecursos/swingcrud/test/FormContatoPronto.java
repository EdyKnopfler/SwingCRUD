package br.com.gamecursos.swingcrud.test;

import static javax.swing.JOptionPane.*;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.List;

import javax.swing.*;

import br.com.gamecursos.swingcrud.*;

public class FormContatoPronto extends FormularioPadraoPronto<Contato> {

   private TableModelContato tableModel;
   private PainelContato painelContato;
   private CrudContatoListener crudListener;
   
   private JTextField txtPesquisa;
   
   public FormContatoPronto() {
      setTitle("Testando Controlador SwingCRUD");
      setSize(600, 500);
      
      getControlador().getTabela().getColumnModel().getColumn(0)
         .setPreferredWidth(250);
      
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
   
   private class TableModelContato extends TableModelEntidade<Contato> {

      // TableModel responsável por exibir os contatos no JTable
      
      private DateFormat formato = DateFormat.getDateInstance();
      
      @Override
      public String[] getColunas() {
         // Aqui retorno os nomes das colunas na grid
         return new String[] {"Nome", "Telefone", "Data nasc."};
      }

      @Override
      public Object getDadoColuna(int coluna, Contato contato) {
         String nascimento = formato.format(contato.getNascimento().getTime());
         
         // Aqui retorno um dado do contato de acordo com os índices das colunas
         switch (coluna) {
            case 0:  return contato.getNome();
            case 1:  return contato.getTelefone();
            case 2:  return nascimento;
            default: return null;
         }
      }
      
   }
   
   private class PainelContato extends PainelCampos<Contato> {
      
      // Painel contendo a exibição dos campos
      // Aqui eu poderia chamar o framework SwingBean para facilitar a criação
      // do layout e a troca de dados entre o formulário e os objetos.
      
      private JTextField nome = new JTextField(20);
      private JTextField telefone = new JTextField(10);
      private JTextField nascimento = new JTextField(10);
      
      private Contato contatoAtual;
      
      public PainelContato() {
         
         // Layout a gosto...
         
         setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.anchor = gbc.WEST;
         gbc.insets = new Insets(5, 5, 5, 5);
         
         gbc.gridx = 0;
         gbc.gridy = 0;
         
         add(new JLabel("Nome:"), gbc);
         gbc.gridy++;
         add(new JLabel("Telefone:"), gbc);
         gbc.gridy++;
         add(new JLabel("Data nasc.:"), gbc);

         gbc.gridx = 1;
         gbc.gridy = 0;
         
         add(nome, gbc);
         gbc.gridy++;
         add(telefone, gbc);
         gbc.gridy++;
         add(nascimento, gbc);
      }

      @Override
      public void exibir(Contato c) {
         // Aqui exibimos o contato no painel e guardamos uma referência a ele
         contatoAtual = c;
         nome.setText(c.getNome());
         telefone.setText(c.getTelefone());
         nascimento.setText(DateFormat.getDateInstance()
               .format(c.getNascimento().getTime()));
      }

      @Override
      public Contato novoObjeto() throws CRUDException {
         // Aqui é chamado quando vai gravar um novo contato
         // Clicou Incluir, depois Gravar
         // Posso lançar uma exceção para indicar que os dados são inválidos
         // e impedir o controlador de efetuar a gravação do contato.
         Contato novo = new Contato();
         popula(novo);
         contatoAtual = novo;
         return novo;
      }

      @Override
      public Contato objetoSendoAlterado() throws CRUDException {
         // Aqui é chamado quando vai gravar um contato existente
         // Clicou Alterar, depois Gravar
         // É interessante retornar o mesmo objeto recebido no Exibir, pois
         // ele pode conter, por exemplo, o id do banco de dados
         popula(contatoAtual);
         return contatoAtual;
      }
      
      private void popula(Contato c) throws CRUDException {
         try {
            c.setNome(nome.getText().trim());
            c.setTelefone(telefone.getText().trim());
            Calendar calNascimento = Calendar.getInstance();
            
            // Aqui pode haver uma exceção se a data estiver em formato inválido
            calNascimento.setTime(DateFormat.getDateInstance()
                  .parse(nascimento.getText().trim()));
            
            c.setNascimento(calNascimento);
         }
         catch (ParseException pex) {
            throw new CRUDException("Data inválida: " + nascimento.getText());
         }
      }

      @Override
      public void limpar() {
         // Aqui eu limpo meus campos
         nome.setText("");
         telefone.setText("");
         nascimento.setText("");
      }

      @Override
      public void habilitarCampos() {
         // Aqui eu habilito os campos
         nome.setEditable(true);
         telefone.setEditable(true);
         nascimento.setEditable(true);
      }

      @Override
      public void desabilitarCampos() {
         // Aqui eu desabilito os campos.
         // O controlador começa em modo não editável.
         nome.setEditable(false);
         telefone.setEditable(false);
         nascimento.setEditable(false);
      }

      public void focoEmNome() {
         nome.requestFocus();
      }
      
   }
   
   private class CrudContatoListener extends CRUDAdapter<Contato> {

      // Aqui ficam os eventos do CRUD:
      // - as ações de incluir, alterar e excluir
      // - eventos antes e após estas ações
      // Os eventos antes podem retornar false para impedir a ação
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
         if (showConfirmDialog(null, "Tem certeza?", "Excluir",
               YES_NO_OPTION) == YES_OPTION)
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

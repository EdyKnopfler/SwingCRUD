package br.com.gamecursos.swingcrud.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.gamecursos.swingcrud.CRUDException;
import br.com.gamecursos.swingcrud.PainelCampos;

public class PainelContato extends PainelCampos<Contato> {
   
   // Painel contendo a exibi��o dos campos
   // Aqui eu poderia chamar o framework SwingBean para facilitar a cria��o
   // do layout e a troca de dados entre o formul�rio e os objetos.
   
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
      // Aqui exibimos o contato no painel e guardamos uma refer�ncia a ele
      contatoAtual = c;
      nome.setText(c.getNome());
      telefone.setText(c.getTelefone());
      nascimento.setText(DateFormat.getDateInstance()
            .format(c.getNascimento().getTime()));
   }

   @Override
   public Contato novoObjeto() throws CRUDException {
      // Aqui � chamado quando vai gravar um novo contato
      // Clicou Incluir, depois Gravar
      // Posso lan�ar uma exce��o para indicar que os dados s�o inv�lidos
      // e impedir o controlador de efetuar a grava��o do contato.
      Contato novo = new Contato();
      popula(novo);
      contatoAtual = novo;
      return novo;
   }

   @Override
   public Contato objetoSendoAlterado() throws CRUDException {
      // Aqui � chamado quando vai gravar um contato existente
      // Clicou Alterar, depois Gravar
      // � interessante retornar o mesmo objeto recebido no Exibir, pois
      // ele pode conter, por exemplo, o id do banco de dados
      popula(contatoAtual);
      return contatoAtual;
   }
   
   private void popula(Contato c) throws CRUDException {
      try {
         c.setNome(nome.getText().trim());
         c.setTelefone(telefone.getText().trim());
         Calendar calNascimento = Calendar.getInstance();
         
         // Aqui pode haver uma exce��o se a data estiver em formato inv�lido
         calNascimento.setTime(DateFormat.getDateInstance()
               .parse(nascimento.getText().trim()));
         
         c.setNascimento(calNascimento);
      }
      catch (ParseException pex) {
         throw new CRUDException("Data inv�lida: " + nascimento.getText());
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
   public void habilitarCampos(boolean habilitar) {
      // Aqui eu habilito os campos
      nome.setEditable(habilitar);
      telefone.setEditable(habilitar);
      nascimento.setEditable(habilitar);
   }
  
   public void focoEmNome() {
      nome.requestFocus();
   }
   
}

package br.com.gamecursos.swingcrud.test;

import java.text.DateFormat;

import br.com.gamecursos.swingcrud.TableModelEntidade;

public class TableModelContato extends TableModelEntidade<Contato> {

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

package br.com.gamecursos.swingcrud.test;

import java.util.Calendar;

public class Contato {
   
   private String nome;
   private String telefone;
   private Calendar nascimento;
   
   public Contato() {
   }
   
   public Contato(String nome, String telefone, Calendar nascimento) {
      this.nome = nome;
      this.telefone = telefone;
      this.nascimento = nascimento;
   }
   
   public String getNome() {
      return nome;
   }
   public void setNome(String nome) {
      this.nome = nome;
   }
   public String getTelefone() {
      return telefone;
   }
   public void setTelefone(String telefone) {
      this.telefone = telefone;
   }
   public Calendar getNascimento() {
      return nascimento;
   }
   public void setNascimento(Calendar nascimento) {
      this.nascimento = nascimento;
   }
   
}

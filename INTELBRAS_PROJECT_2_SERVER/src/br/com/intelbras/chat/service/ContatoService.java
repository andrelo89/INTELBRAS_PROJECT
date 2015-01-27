package br.com.intelbras.chat.service;

import java.util.List;

import br.com.intelbras.chat.model.Contato;
import br.com.intelbras.chat.model.TalkText;

public interface ContatoService{
	
	List<Contato> list(Integer codigo);
	
	boolean add(Contato c);

	Contato logar(String value, String value2);
	
	int updateContatoOpen(Integer cod1, Integer cod2, Boolean open);

	List<Contato> getContatosOpen(Integer codigo);
	
	boolean addTalk(Integer cod1, Integer cod2, String text);
	
	List<TalkText> getTalks(Integer cod1, Integer cod2);

}

package br.com.intelbras.chat.client;

import java.util.List;

import br.com.intelbras.chat.model.Contato;
import br.com.intelbras.chat.model.TalkText;
import br.com.intelbras.chat.service.ContatoService;
import br.com.intelbras.chat.service.ContatoServiceImpl;

public class ContatoServiceClient implements ContatoService{

	@Override
	public List<Contato> list(Integer codigo) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.list(codigo);
	}

	@Override
	public boolean add(Contato c) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.add(c);
	}

	@Override
	public Contato logar(String value, String value2) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.logar(value, value2);
	}

	@Override
	public int updateContatoOpen(Integer cod1, Integer cod2, Boolean open) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.updateContatoOpen(cod1, cod2, open);
	}

	@Override
	public List<Contato> getContatosOpen(Integer codigo) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.getContatosOpen(codigo);
	}

	@Override
	public boolean addTalk(Integer cod1, Integer cod2, String text) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.addTalk(cod1, cod2, text);
	}

	@Override
	public List<TalkText> getTalks(Integer cod1, Integer cod2) {
		ContatoServiceImpl impl = new ContatoServiceImpl();
		return impl.getTalks(cod1, cod2);
	}

}

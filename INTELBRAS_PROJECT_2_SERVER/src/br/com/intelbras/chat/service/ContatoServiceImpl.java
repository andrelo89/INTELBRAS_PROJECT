package br.com.intelbras.chat.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.intelbras.chat.model.Contato;
import br.com.intelbras.chat.model.TalkText;

public class ContatoServiceImpl extends ConnectionDB implements ContatoService {

	@Override
	public List<Contato> list(Integer codigo) {
		List<Contato> out = new ArrayList<Contato>();
		try {
			String sql = "SELECT t1.*, open FROM public.user t1"
					+ " LEFT JOIN public.talk_open t2 ON t2.cod2 = t1.codigo and t2.cod1 = "
					+ codigo + " " + " WHERE t1.codigo <> " + codigo
					+ " order by name";
			ResultSet rs;
			rs = getInstance().executeQuery(sql);
			while (rs.next()) {
				Contato c = new Contato();
				c.setCodigo(rs.getInt("codigo"));
				c.setNome(rs.getString("name"));
				c.setApelido(rs.getString("apelido"));
				c.setOpen(rs.getInt("open") == 1);
				out.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public boolean add(Contato c) {
		String sql = "INSERT INTO public.user(codigo, name, apelido, password) "
				+ "VALUES (nextval('seq_user'), '"
				+ c.getNome()
				+ "', '"
				+ c.getApelido() + "', '" + c.getPassword() + "' );";
		try {
			return getInstance().executeUpdate(sql) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public Contato logar(String name, String senha) {
		Contato out = null;
		try {
			String sql = "SELECT * FROM public.user WHERE apelido LIKE '"
					+ name + "' AND password LIKE '" + senha + "'";
			ResultSet rs;
			rs = getInstance().executeQuery(sql);
			if (rs.next()) {
				out = new Contato();
				out.setCodigo(rs.getInt("codigo"));
				out.setNome(rs.getString("name"));
				out.setApelido(rs.getString("apelido"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public List<Contato> getContatosOpen(Integer codigo) {
		List<Contato> out = new ArrayList<Contato>();
		try {
			String sql = "SELECT t1.*, open FROM public.user t1 "
					+ " LEFT JOIN public.talk_open t2 ON "
					+ " t2.cod2 = t1.codigo " + " and open=1 WHERE t2.cod1 = "
					+ codigo + " order by name";
			ResultSet rs;
			rs = getInstance().executeQuery(sql);
			while (rs.next()) {
				Contato o = new Contato();
				o.setCodigo(rs.getInt("codigo"));
				o.setNome(rs.getString("name"));
				o.setApelido(rs.getString("apelido"));
				o.setOpen(rs.getInt("open")==1);
				out.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public int updateContatoOpen(Integer cod1, Integer cod2, Boolean open) {
		try {
			String sql = "SELECT * FROM public.talk_open WHERE cod1 = " + cod1
					+ " AND cod2 = " + cod2;
			ResultSet rs;
			rs = getInstance().executeQuery(sql);
			int value;
			if (open) {
				value = 1;
			} else {
				value = 0;
			}
			if (rs.next()) {
				sql = "UPDATE public.talk_open" + "   SET cod1=" + cod1
						+ ", cod2=" + cod2 + ", open=" + value + " WHERE cod1="
						+ cod1 + " and cod2=" + cod2;
			} else {
				sql = "INSERT INTO talk_open(" + "   cod1, cod2, open)"
						+ "			    VALUES (" + cod1 + ", " + cod2 + ", " + value
						+ ")";
			}
			return getInstance().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public boolean addTalk(Integer cod1, Integer cod2, String text) {
		try {
			String sql = "INSERT INTO public.talk("
					+ "            cod1, cod2, date, talk)" + "    VALUES ("
					+ cod1 + ", " + cod2 + ", current_timestamp, '" + text + "')";
			return getInstance().executeUpdate(sql) != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TalkText> getTalks(Integer cod1, Integer cod2) {
		List<TalkText> out = new ArrayList<TalkText>();
		try {
			String sql = "SELECT * FROM talk where cod1 in ("+cod1+","+cod2+") AND cod2 in ("+cod1+","+cod2+") order by date;";
			ResultSet rs;
			rs = getInstance().executeQuery(sql);
			while (rs.next()) {
				TalkText o = new TalkText();
				o.setCod1(rs.getInt("cod1"));
				o.setCod2(rs.getInt("cod2"));
				o.setComent(rs.getString("talk"));
				o.setDate(rs.getDate("date"));
				out.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

}

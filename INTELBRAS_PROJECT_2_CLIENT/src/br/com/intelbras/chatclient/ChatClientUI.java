package br.com.intelbras.chatclient;

import javax.servlet.annotation.WebServlet;

import br.com.intelbras.chat.client.ContatoServiceClient;
import br.com.intelbras.chat.model.Contato;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("chatclient")
public class ChatClientUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ChatClientUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private UsersUI users;
	private ConversationsUI conversations;
	private Window window;
	private TextField nome;
	private TextField apelido;
	private PasswordField passwordField;
	private Button logarButton;
	public Contato usuarioLogado;
	
	private ContatoServiceClient service;
	private Button cadastroButton;
	private Button cancelarButton;
	private Button salvarButton;

	@Override
	protected void init(VaadinRequest request) {
		
		service = new ContatoServiceClient();
		
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setWidth("1300");
		setContent(layout);

		users = usersLayout();
		conversations = conversationsLayout(2);
		layout.addComponent(users);
		layout.addComponent(conversations);
		
		window = loginLayout();
	}

	private Window loginLayout() {
		
		window = new Window("Chat - Login");
		window.setWidth(350.0f, Unit.PIXELS);
		window.setHeight(250.0f, Unit.PIXELS);
		window.setClosable(false);
		window.setResizable(false);
		window.setDraggable(false);
		window.setModal(true);
		
		FormLayout layout = new FormLayout();
		layout.setSizeFull();
		
		{
			cadastroButton = new Button("Cadastre-se");
			cadastroButton.addClickListener(new ClickListener() {
	            @Override
	            public void buttonClick(final ClickEvent event) {
	            	ChatClientUI.this.statusLogando(false);
	            }
	        });
			layout.addComponent(cadastroButton);
			layout.setComponentAlignment(cadastroButton, Alignment.BOTTOM_RIGHT);
		}
		
		{
			nome = new TextField("Nome");
			nome.setMaxLength(50);
			nome.setRequired(true);
			nome.setSizeFull();
			layout.addComponent(nome);
		}
		
		{
			apelido = new TextField("Apelido");
			apelido.setMaxLength(50);
			apelido.setRequired(true);
			apelido.setSizeFull();
			layout.addComponent(apelido);
		}
		apelido.setValue("andre");
		{
			passwordField = new PasswordField("Senha");
			passwordField.setRequired(true);
			passwordField.setMaxLength(20);
			passwordField.setSizeFull();
			layout.addComponent(passwordField);
		}
		passwordField.setValue("123qwe");
		{
			HorizontalLayout botoes = new HorizontalLayout();
			botoes.setSpacing(true);
			
			cancelarButton = new Button("Cancelar");
			cancelarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					ChatClientUI.this.statusLogando(true);
				}
			});
			botoes.addComponent(cancelarButton);
			
			
			salvarButton = new Button("Salvar");
			salvarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					Contato contato = new Contato();
					contato.setNome(nome.getValue());
					contato.setApelido(apelido.getValue());
					contato.setPassword(passwordField.getValue());
					if(service.add(contato)){
						service.add(contato);
						ChatClientUI.this.usuarioLogado = ChatClientUI.this.service.logar(apelido.getValue(), passwordField.getValue());
						ChatClientUI.this.statusLogando(true);
						ChatClientUI.this.window.close();
						ChatClientUI.this.users.updateTable();
						ChatClientUI.this.conversations.updateConversations();
						Notification.show("Cadastro efetuado com Sucesso!", Type.HUMANIZED_MESSAGE);
					} else {
						Notification.show("Não foi possível finalizar cadastro.<br>Verifique as informação cadastrais.", Type.WARNING_MESSAGE);
					}
					
					
				}
			});
			botoes.addComponent(salvarButton);
			
			logarButton = new Button("Entrar");
			logarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					ChatClientUI.this.usuarioLogado = service.logar(apelido.getValue(), passwordField.getValue());
					if(ChatClientUI.this.usuarioLogado == null){
						Notification.show("Apelido ou senha inseridos estão incorretos!", Type.WARNING_MESSAGE);
					} else {
						ChatClientUI.this.window.close();
						ChatClientUI.this.users.updateTable();
						ChatClientUI.this.conversations.updateConversations();
					}
				}
			});
			botoes.addComponent(logarButton);	
			
			layout.addComponent(botoes);	
			layout.setComponentAlignment(botoes, Alignment.TOP_RIGHT);
		}
		
		layout.setMargin(true);
		layout.setSpacing(true);
		
		window.setContent(layout);
		
		this.statusLogando(true);
		
		UI.getCurrent().addWindow(window);

		return window;
	}

	private ConversationsUI conversationsLayout(int col) {
		conversations = new ConversationsUI(service, users, this);
		return conversations;
	}

	private UsersUI usersLayout() {
		users = new UsersUI(service, this);
		return users;
	}
	
	private void statusLogando(boolean yes){
		ChatClientUI.this.cadastroButton.setVisible(yes);
		ChatClientUI.this.salvarButton.setVisible(!yes);
		ChatClientUI.this.nome.setVisible(!yes);
		ChatClientUI.this.logarButton.setVisible(yes);
		ChatClientUI.this.cancelarButton.setVisible(!yes);
	}
	
	public void actionSair(){
		this.usuarioLogado = null;
		this.statusLogando(true);
		nome.setValue("");
		apelido.setValue("");
		passwordField.setValue("");
		
		UI.getCurrent().addWindow(window);
	}

	public void updateContatoOpen(Integer codigo, Boolean open) {
		service.updateContatoOpen(usuarioLogado.getCodigo(), codigo, open);
		this.conversations.updateConversations();
		
	}

}
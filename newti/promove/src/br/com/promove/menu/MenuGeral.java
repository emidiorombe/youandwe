package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.ClimaAvariaView;
import br.com.promove.view.CorView;
import br.com.promove.view.ExportCadastroView;
import br.com.promove.view.ExtensaoAvariaView;
import br.com.promove.view.FabricanteView;
import br.com.promove.view.FilialView;
import br.com.promove.view.LocalAvariaView;
import br.com.promove.view.ModeloView;
import br.com.promove.view.OrigemAvariaView;
import br.com.promove.view.TipoAvariaView;
import br.com.promove.view.UsuarioView;
import br.com.promove.view.form.ClimaForm;
import br.com.promove.view.form.CorForm;
import br.com.promove.view.form.ExtensaoAvariaForm;
import br.com.promove.view.form.FabricanteForm;
import br.com.promove.view.form.FilialForm;
import br.com.promove.view.form.LocalAvariaForm;
import br.com.promove.view.form.ModeloForm;
import br.com.promove.view.form.OrigemAvariaForm;
import br.com.promove.view.form.TipoAvariaForm;
import br.com.promove.view.form.UsuarioForm;
import br.com.promove.view.form.VeiculoForm;
import br.com.promove.view.table.ClimaTable;
import br.com.promove.view.table.CorTable;
import br.com.promove.view.table.ExtensaoAvariaTable;
import br.com.promove.view.table.FabricanteTable;
import br.com.promove.view.table.FilialTable;
import br.com.promove.view.table.LocalAvariaTable;
import br.com.promove.view.table.ModeloTable;
import br.com.promove.view.table.OrigemAvariaTable;
import br.com.promove.view.table.TipoAvariaTable;
import br.com.promove.view.table.UsuarioTable;
import br.com.promove.view.table.VeiculoTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MenuGeral extends CssLayout{
	private PromoveApplication app;
	private Button usuario;
	private Button filial;
	private Button fabricante;
	private Button modelo;
	private Button cor;
	private Button tipo;
	private Button local;
	private Button origem;
	private Button extensao;
	private Button clima;
	private Button export;
	
	public MenuGeral(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}

	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Gerenciar Cadastros");
		title.addStyleName("section");
		
		usuario = new NativeButton("Usuario");
		filial = new NativeButton("Filial");
		fabricante = new NativeButton("Fabricante");
		modelo = new NativeButton("Modelo");
		cor = new NativeButton("Cor");
		tipo = new NativeButton("Tipos de Avaria");
		local = new NativeButton("Local de Avaria");
		origem = new NativeButton("Origem de Avaria");
		extensao = new NativeButton("Extensão de Avaria");
		clima = new NativeButton("Condição Climática");
		export = new NativeButton("Exportar Cadastros Básicos");
		
		
		addListeners(usuario, filial, fabricante, modelo, cor, tipo, local, origem, extensao, clima, export);
		addComponents(title, filial, usuario, fabricante, modelo, cor, tipo, local, origem, extensao, clima, export);
		
	}
	
	private void addListeners(Button... btns) {
		for (Button b : btns) {
			b.addListener(new MenuItemListener());
		}
	}

	private void addComponents(Component... components) {
		for (Component component : components) {
			addComponent(component);
		}
	}
	
	class MenuItemListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			addAndRemoveStyle(event.getButton(), usuario, filial, fabricante, modelo, cor, tipo, local, origem, extensao, clima);
			if(event.getButton() == usuario) {
				UsuarioTable table = new UsuarioTable();
				UsuarioForm form = new UsuarioForm();
				app.setMainView(new UsuarioView(table, form));
			}else if(event.getButton() == filial) {
				FilialTable table = new FilialTable();
				FilialForm form= new FilialForm();
				app.setMainView(new FilialView(table, form));
			}else if(event.getButton() == fabricante) {
				FabricanteTable table = new FabricanteTable();
				FabricanteForm form = new FabricanteForm();
				app.setMainView(new FabricanteView(table,form));
			}else if(event.getButton() == modelo) {
				ModeloTable table = new ModeloTable();
				ModeloForm form = new ModeloForm();
				app.setMainView(new ModeloView(table, form));
			}else if(event.getButton() == cor) {
				CorTable table = new CorTable();
				CorForm form = new CorForm();
				app.setMainView(new CorView(table, form));
			}else if(event.getButton() == tipo){
				TipoAvariaTable table = new TipoAvariaTable();
				TipoAvariaForm form = new TipoAvariaForm();
				app.setMainView(new TipoAvariaView(table, form));
			}else if(event.getButton() == local){
				LocalAvariaTable table = new LocalAvariaTable();
				LocalAvariaForm form = new LocalAvariaForm();
				app.setMainView(new LocalAvariaView(table, form));
			}else if(event.getButton() == origem){
				OrigemAvariaTable table = new OrigemAvariaTable();
				OrigemAvariaForm form = new OrigemAvariaForm();
				app.setMainView(new OrigemAvariaView(table, form));
			}else if(event.getButton() == extensao){
				ExtensaoAvariaTable table = new ExtensaoAvariaTable();
				ExtensaoAvariaForm form = new ExtensaoAvariaForm();
				app.setMainView(new ExtensaoAvariaView(table, form));
			}else if(event.getButton() == clima){
				ClimaTable table = new ClimaTable();
				ClimaForm form = new ClimaForm();
				app.setMainView(new ClimaAvariaView(table, form));
			}else if(event.getButton() == export){
				app.setMainView(new ExportCadastroView(app).getLayout());
			}
			
		}

		private void addAndRemoveStyle(Button clicked, Button...others) {
			for (Button b : others) {
				if(b == clicked)
					clicked.addStyleName("selected");
				else
					b.removeStyleName("selected");
					
			}
		}
	}
	

}

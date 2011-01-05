package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.CorView;
import br.com.promove.view.FabricanteView;
import br.com.promove.view.FilialView;
import br.com.promove.view.ModeloView;
import br.com.promove.view.UsuarioView;
import br.com.promove.view.form.CorForm;
import br.com.promove.view.form.FabricanteForm;
import br.com.promove.view.form.FilialForm;
import br.com.promove.view.form.ModeloForm;
import br.com.promove.view.form.UsuarioForm;
import br.com.promove.view.form.VeiculoForm;
import br.com.promove.view.table.CorTable;
import br.com.promove.view.table.FabricanteTable;
import br.com.promove.view.table.FilialTable;
import br.com.promove.view.table.ModeloTable;
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
	private Button add_veiculo;
	private Button list_veiculo;
	private Button usuario;
	private Button filial;
	private Button fabricante;
	private Button modelo;
	private Button cor;
	
	public MenuGeral(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}

	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Gerenciar Cadastros");
		title.addStyleName("section");
		
		add_veiculo = new NativeButton("Incluir Veículo");
		list_veiculo = new NativeButton("Listar Veículos");
		usuario = new NativeButton("Usuarios");
		filial = new NativeButton("Filiais");
		fabricante = new NativeButton("Fabricantes");
		modelo = new NativeButton("Modelos");
		cor = new NativeButton("Cores");
		
		
		addListeners(add_veiculo, list_veiculo, usuario, filial, fabricante, modelo, cor);
		addComponents(title, add_veiculo, list_veiculo, usuario, filial, fabricante, modelo, cor);
		
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
			addAndRemoveStyle(event.getButton(), add_veiculo, list_veiculo, usuario, filial, fabricante, modelo, cor);
			if(event.getButton() == add_veiculo) {
				VeiculoForm form = new VeiculoForm();
				app.setMainView(form.getFormLayout());
			}else if(event.getButton() == list_veiculo) {
				VeiculoTable table = new VeiculoTable(app);
				app.setMainView(table);
			}else if(event.getButton() == usuario) {
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

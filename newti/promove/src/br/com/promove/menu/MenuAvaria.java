package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.AvariaSearchView;
import br.com.promove.view.ErroImportAvariaView;
import br.com.promove.view.ErroImportVeiculoView;
import br.com.promove.view.ImportAvariaView;
import br.com.promove.view.ImportVeiculoView;
import br.com.promove.view.VeiculoListView;
import br.com.promove.view.form.AvariaForm;
import br.com.promove.view.form.AvariaSearchForm;
import br.com.promove.view.form.ErroImportAvariaForm;
import br.com.promove.view.form.ErroImportVeiculoForm;
import br.com.promove.view.form.VeiculoForm;
import br.com.promove.view.form.VeiculoSearchForm;
import br.com.promove.view.table.AvariaTable;
import br.com.promove.view.table.ErroImportAvariaTable;
import br.com.promove.view.table.ErroImportVeiculoTable;
import br.com.promove.view.table.VeiculoTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;

public class MenuAvaria  extends CssLayout{
	private Button add;
	private Button list;
	private Button add_veiculo;
	private Button list_veiculo;
	private Button import_avaria;
	private Button erro_import_avaria;
	private Button import_veiculos;
	private Button erro_import_veiculos;
	private Button export_avaria;
	
	private PromoveApplication app;
	
	public MenuAvaria(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}

	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Gerenciar Avarias");
		title.addStyleName("section");
		
		add = new NativeButton("Registrar Avaria");
		list = new NativeButton("Listar Avarias");
		add_veiculo = new NativeButton("Registrar Veículo");
		list_veiculo = new NativeButton("Listar Veículos");
		import_avaria = new NativeButton("Importar Avarias");
		erro_import_avaria = new NativeButton("Auditar erros importação Avarias");
		import_veiculos = new NativeButton("Importar Veículo");
		erro_import_veiculos = new NativeButton("Auditar erros importação Veículos");
		export_avaria = new NativeButton("Exportar Avarias");

		
		
		addListeners(add, list, add_veiculo, list_veiculo, import_avaria, erro_import_avaria, import_veiculos, erro_import_veiculos, export_avaria);
		addComponents(add_veiculo, list_veiculo, add, list, import_avaria, erro_import_avaria, import_veiculos, erro_import_veiculos, export_avaria);
				
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
			addAndRemoveStyle(event.getButton(), add, list, add_veiculo, list_veiculo, import_avaria, erro_import_avaria, import_veiculos, erro_import_veiculos, export_avaria);
			if(event.getButton() == add) {
				AvariaForm form = new AvariaForm();
				app.setMainView(form.getFormLayout());
			}else if(event.getButton() == list){
				AvariaSearchForm form = new AvariaSearchForm();
				AvariaTable table = new AvariaTable(app);
				app.setMainView(new AvariaSearchView(table, form));
			}else if(event.getButton() == add_veiculo) {
				VeiculoForm form = new VeiculoForm();
				app.setMainView(form.getFormLayout());
			}else if(event.getButton() == list_veiculo) {
				VeiculoSearchForm form = new VeiculoSearchForm();
				VeiculoTable table = new VeiculoTable(app);
				app.setMainView(new VeiculoListView(table, form));
			}else if(event.getButton() == import_avaria) {
				ImportAvariaView view = new ImportAvariaView(app);
				app.setMainView(view.getLayout());
			}else if(event.getButton() == import_veiculos) {
				ImportVeiculoView view = new ImportVeiculoView();
				app.setMainView(view);
			}else if(event.getButton() == erro_import_veiculos) {
				ErroImportVeiculoTable table = new ErroImportVeiculoTable();
				ErroImportVeiculoForm form = new ErroImportVeiculoForm();
				app.setMainView(new ErroImportVeiculoView(table, form));
			}else if(event.getButton() == erro_import_avaria) {
				ErroImportAvariaTable table = new ErroImportAvariaTable();
				ErroImportAvariaForm form = new ErroImportAvariaForm();
				app.setMainView(new ErroImportAvariaView(table, form));
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

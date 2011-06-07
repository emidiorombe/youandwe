package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Usuario;
import br.com.promove.view.AuditoriaVistoriasTables;
import br.com.promove.view.AuditoriaVistoriasView;
import br.com.promove.view.AvariaSearchView;
import br.com.promove.view.ErroImportAvariaView;
import br.com.promove.view.ErroImportVeiculoView;
import br.com.promove.view.ExportAvariaView;
import br.com.promove.view.ImportAvariaView;
import br.com.promove.view.ImportVeiculoView;
import br.com.promove.view.VeiculoAvariaTables;
import br.com.promove.view.VeiculoListView;
import br.com.promove.view.form.AuditoriaVistoriasForm;
import br.com.promove.view.form.AvariaForm;
import br.com.promove.view.form.AvariaSearchForm;
import br.com.promove.view.form.ErroImportAvariaForm;
import br.com.promove.view.form.ErroImportVeiculoForm;
import br.com.promove.view.form.VeiculoForm;
import br.com.promove.view.form.VeiculoSearchForm;
import br.com.promove.view.table.AvariaTable;
import br.com.promove.view.table.AvariaVeiculoTable;
import br.com.promove.view.table.ErroImportAvariaTable;
import br.com.promove.view.table.ErroImportVeiculoTable;
import br.com.promove.view.table.VeiculoTable;

import com.vaadin.terminal.gwt.server.WebApplicationContext;
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
	private Button export;
	private Button auditoria;
	private Button analise;
	private Button resumo;
	
	private PromoveApplication app;
	
	public MenuAvaria(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}
	
	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Gerenciar Avarias e Veículos");
		title.addStyleName("section");

		list = new NativeButton("Listar Avarias");
		add = new NativeButton("Registrar Avaria");
		list_veiculo = new NativeButton("Listar Veículos");
		add_veiculo = new NativeButton("Registrar Veículo");
		import_avaria = new NativeButton("Importar Avarias");
		erro_import_avaria = new NativeButton("Auditar erros importação Avarias");
		import_veiculos = new NativeButton("Importar Veículos");
		erro_import_veiculos = new NativeButton("Auditar erros importação Veículos");
		export = new NativeButton("Exportar Avarias");
		auditoria = new NativeButton("Auditoria de Vistorias");
		analise = new NativeButton("Análise de Resultado");
		resumo = new NativeButton("Resumo de Avarias");

		addListeners(list, add, list_veiculo, add_veiculo, import_avaria, erro_import_avaria, import_veiculos, erro_import_veiculos, export, auditoria, analise, resumo);
		addComponents(title, list, add, list_veiculo, add_veiculo, import_avaria, erro_import_avaria, import_veiculos, erro_import_veiculos, export, auditoria, analise, resumo);
				
		setPermissionVisible();
		
	}
	
	private void setPermissionVisible() {
		
		WebApplicationContext ctx = (WebApplicationContext) app.getContext();
		Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
		
		if(user.getTipo().getId() != 1 && user.getTipo().getId() != 2) {
			add.setVisible(false); 
			add_veiculo.setVisible(false);
			import_avaria.setVisible(false);
			erro_import_avaria.setVisible(false);
			import_veiculos.setVisible(false);
			erro_import_veiculos.setVisible(false);
			export.setVisible(false);
			auditoria.setVisible(false);
			analise.setVisible(false);
			resumo.setVisible(false);
		}
	}
	
	public void changeStyleToAdd() {
		addAndRemoveStyle(add, list, import_avaria, erro_import_avaria, export, auditoria, analise, resumo, add_veiculo, list_veiculo, import_veiculos, erro_import_veiculos);
		
	}
	
	public void loadMainView(ClickEvent event) {
		addAndRemoveStyle(event.getButton(), add, list, import_avaria, erro_import_avaria, export, auditoria, analise, resumo, add_veiculo, list_veiculo, import_veiculos, erro_import_veiculos);
		if(event.getButton() == add) {
			AvariaForm form = new AvariaForm(app);
			app.setMainView(form.getFormLayout());
		}else if(event.getButton() == list){
			AvariaSearchForm form = new AvariaSearchForm(app);
			AvariaTable table = new AvariaTable(app);
			app.setMainView(new AvariaSearchView(table, form));
		}else if(event.getButton() == add_veiculo) {
			VeiculoForm form = new VeiculoForm();
			app.setMainView(form.getFormLayout());
		}else if(event.getButton() == list_veiculo) {
			VeiculoSearchForm form = new VeiculoSearchForm(app);
			VeiculoTable tableVeiculo = new VeiculoTable(app, this);
			AvariaVeiculoTable tableAvaria = new AvariaVeiculoTable(app);
			VeiculoAvariaTables tables = new VeiculoAvariaTables(tableVeiculo, tableAvaria);
			app.setMainView(new VeiculoListView(tables, form));
		}else if(event.getButton() == import_avaria) {
			ImportAvariaView view = new ImportAvariaView(app);
			app.setMainView(view.getLayout());
		}else if(event.getButton() == erro_import_avaria) {
			ErroImportAvariaTable table = new ErroImportAvariaTable();
			ErroImportAvariaForm form = new ErroImportAvariaForm(app);
			app.setMainView(new ErroImportAvariaView(table, form));
		}else if(event.getButton() == import_veiculos) {
			ImportVeiculoView view = new ImportVeiculoView();
			app.setMainView(view);
		}else if(event.getButton() == erro_import_veiculos) {
			ErroImportVeiculoTable table = new ErroImportVeiculoTable();
			ErroImportVeiculoForm form = new ErroImportVeiculoForm();
			app.setMainView(new ErroImportVeiculoView(table, form));
		}else if(event.getButton() == export) {
			ExportAvariaView view = new ExportAvariaView(app);
			app.setMainView(view.getLayout());
		}else if(event.getButton() == auditoria) {
			AuditoriaVistoriasForm form = new AuditoriaVistoriasForm(app);
			VeiculoTable tableVeiculo = new VeiculoTable(app, this);
			AvariaVeiculoTable tableAvaria = new AvariaVeiculoTable(app);
			AuditoriaVistoriasTables tables = new AuditoriaVistoriasTables(tableVeiculo, tableAvaria);
			app.setMainView(new AuditoriaVistoriasView(tables, form));
		}else if(event.getButton() == analise) {
		}else if(event.getButton() == resumo) {
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
			loadMainView(event);
		}

		
	}
}

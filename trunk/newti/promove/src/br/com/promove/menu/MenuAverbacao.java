package br.com.promove.menu;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Usuario;
import br.com.promove.view.CtrcVeiculoTables;
import br.com.promove.view.CtrcView;
import br.com.promove.view.ErroImportCtrcVeiculoTables;
import br.com.promove.view.ErroImportCtrcView;
import br.com.promove.view.ImportCtrcView;
import br.com.promove.view.form.CtrcForm;
import br.com.promove.view.form.CtrcSearchForm;
import br.com.promove.view.form.ErroImportCtrcForm;
import br.com.promove.view.table.CtrcTable;
import br.com.promove.view.table.ErroImportCtrcTable;
import br.com.promove.view.table.ErroImportVeiculoCtrcTable;
import br.com.promove.view.table.VeiculoCtrcTable;

import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;

public class MenuAverbacao extends CssLayout {
	private Button add_ctrc;
	private Button list_ctrc;
	private Button import_ctrc;
	private Button erro_import_ctrc;
	
	private PromoveApplication app;
	
	public MenuAverbacao(PromoveApplication app) {
		this.app = app;
		buildMenu();
	}
	
	private void buildMenu() {
		addStyleName("menu");
		setWidth("100%");
		
		Label title = new Label("Averbação de Seguros");
		title.addStyleName("section");
		
		list_ctrc = new NativeButton("Listar CTRCs");
		add_ctrc = new NativeButton("Registrar CTRC");
		import_ctrc = new NativeButton("Importar CTRCs");
		erro_import_ctrc = new NativeButton("Auditar erros importação CTRCs");
		
		addListeners(list_ctrc, add_ctrc, import_ctrc, erro_import_ctrc);
		addComponents(title, list_ctrc, add_ctrc, import_ctrc, erro_import_ctrc);
				
		setPermissionVisible();
		
	}
	
	private void setPermissionVisible() {
		
		WebApplicationContext ctx = (WebApplicationContext) app.getContext();
		Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
		
		if(user.getTipo().getId() != 1 && user.getTipo().getId() != 7) {
			add_ctrc.setVisible(false); 
			list_ctrc.setVisible(false);
			import_ctrc.setVisible(false);
			erro_import_ctrc.setVisible(false);
		}
	}
	
	public void changeStyleToAdd() {
		addAndRemoveStyle(add_ctrc, list_ctrc, import_ctrc, erro_import_ctrc);
		
	}
	
	public void loadMainView(ClickEvent event) {
		addAndRemoveStyle(event.getButton(), add_ctrc, list_ctrc, import_ctrc, erro_import_ctrc);
		if(event.getButton() == add_ctrc) {
			CtrcForm form = new CtrcForm(app);
			app.setMainView(form.getFormLayout());
		}else if(event.getButton() == list_ctrc){
			CtrcSearchForm form = new CtrcSearchForm(app);
			CtrcTable tableCtrc = new CtrcTable(app);
			VeiculoCtrcTable tableVeiculo = new VeiculoCtrcTable(app);
			CtrcVeiculoTables tables = new CtrcVeiculoTables(tableCtrc, tableVeiculo);
			app.setMainView(new CtrcView(tables, form));
		}else if(event.getButton() == import_ctrc) {
			ImportCtrcView view = new ImportCtrcView(app);
			app.setMainView(view.getLayout());
		}else if(event.getButton() == erro_import_ctrc) {
			ErroImportCtrcTable tableCtrc = new ErroImportCtrcTable();
			ErroImportVeiculoCtrcTable tableVeic = new ErroImportVeiculoCtrcTable(app);
			ErroImportCtrcVeiculoTables tables = new ErroImportCtrcVeiculoTables(tableCtrc, tableVeic);
			ErroImportCtrcForm form = new ErroImportCtrcForm(app);
			app.setMainView(new ErroImportCtrcView(tables, form));
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

package br.com.promove.view;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.form.VeiculoSearchForm;

import com.vaadin.ui.SplitPanel;

public class VeiculoListView extends SplitPanel{
	private VeiculoAvariaTables tables;
	private VeiculoSearchForm form;
	private PromoveApplication app;
	
	public VeiculoListView(VeiculoAvariaTables tables, VeiculoSearchForm form) {
		this.tables = tables;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		tables.setView(this);
		form.setView(this);
		setFirstComponent(form.getFormLayout());
		setSecondComponent(tables);
		setSplitPosition(55);
		
	}

	public VeiculoAvariaTables getTables() {
		return tables;
	}

	public void setTables(VeiculoAvariaTables tables) {
		this.tables = tables;
	}

	public VeiculoSearchForm getForm() {
		return form;
	}

	public void setForm(VeiculoSearchForm form) {
		this.form = form;
	}

	public PromoveApplication getApp() {
		return app;
	}

	public void setApp(PromoveApplication app) {
		this.app = app;
	}
}

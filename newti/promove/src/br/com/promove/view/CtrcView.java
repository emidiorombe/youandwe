package br.com.promove.view;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.form.CtrcSearchForm;

import com.vaadin.ui.SplitPanel;

public class CtrcView extends SplitPanel {
	private CtrcVeiculoTables tables;
	private CtrcSearchForm form;
	private PromoveApplication app;
	
	public CtrcView(CtrcVeiculoTables tables, CtrcSearchForm form) {
		this.tables = tables;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		tables.setView(this);
		form.setView(this);
		setFirstComponent(form.getFormLayout());
		setSecondComponent(tables);
		setSplitPosition(50);
		
	}

	public CtrcVeiculoTables getTables() {
		return tables;
	}

	public void setTables(CtrcVeiculoTables tables) {
		this.tables = tables;
	}

	public CtrcSearchForm getForm() {
		return form;
	}

	public void setForm(CtrcSearchForm form) {
		this.form = form;
	}

	public PromoveApplication getApp() {
		return app;
	}

	public void setApp(PromoveApplication app) {
		this.app = app;
	}
}

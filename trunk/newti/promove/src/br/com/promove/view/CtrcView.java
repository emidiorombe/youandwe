package br.com.promove.view;

import br.com.promove.application.PromoveApplication;
import br.com.promove.view.form.CtrcSearchForm;
import br.com.promove.view.table.CtrcTable;

import com.vaadin.ui.SplitPanel;

public class CtrcView extends SplitPanel{
	private CtrcTable table;
	private CtrcSearchForm form;
	private PromoveApplication app;
	
	public CtrcView(CtrcTable table, CtrcSearchForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(form.getFormLayout());
		setSecondComponent(table);
		setSplitPosition(50);
		
	}

	public CtrcTable getTable() {
		return table;
	}

	public void setTable(CtrcTable table) {
		this.table = table;
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

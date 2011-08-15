package br.com.promove.view;


import br.com.promove.view.form.ErroImportCtrcForm;

import com.vaadin.ui.SplitPanel;

public class ErroImportCtrcView extends SplitPanel{

	private ErroImportCtrcForm form;
	private ErroImportCtrcVeiculoTables tables;

	public ErroImportCtrcView(ErroImportCtrcVeiculoTables tables, ErroImportCtrcForm form) {
		this.tables = tables;
		this.form = form;
		buildListView();
	}
	
	private void buildListView() {
		tables.setView(this);
		form.setView(this);
		setFirstComponent(tables);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(80);
		
	}

	public ErroImportCtrcForm getForm() {
		return form;
	}

	public void setForm(ErroImportCtrcForm form) {
		this.form = form;
	}

	public ErroImportCtrcVeiculoTables getTables() {
		return tables;
	}

	public void setTables(ErroImportCtrcVeiculoTables tables) {
		this.tables = tables;
	}
	
	
}

package br.com.promove.view;


import br.com.promove.view.form.ErroImportAvariaForm;
import br.com.promove.view.table.ErroImportAvariaTable;

import com.vaadin.ui.SplitPanel;

public class ErroImportAvariaView extends SplitPanel{

	private ErroImportAvariaForm form;
	private ErroImportAvariaTable table;

	public ErroImportAvariaView(ErroImportAvariaTable table, ErroImportAvariaForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}
	
	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(table);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(75);
		
	}

	public ErroImportAvariaForm getForm() {
		return form;
	}

	public void setForm(ErroImportAvariaForm form) {
		this.form = form;
	}

	public ErroImportAvariaTable getTable() {
		return table;
	}

	public void setTable(ErroImportAvariaTable table) {
		this.table = table;
	}
	
	
}

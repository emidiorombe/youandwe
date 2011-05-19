package br.com.promove.view;


import br.com.promove.view.form.ErroImportCtrcForm;
import br.com.promove.view.table.ErroImportCtrcTable;

import com.vaadin.ui.SplitPanel;

public class ErroImportCtrcView extends SplitPanel{

	private ErroImportCtrcForm form;
	private ErroImportCtrcTable table;

	public ErroImportCtrcView(ErroImportCtrcTable table, ErroImportCtrcForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}
	
	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(table);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(40);
		
	}

	public ErroImportCtrcForm getForm() {
		return form;
	}

	public void setForm(ErroImportCtrcForm form) {
		this.form = form;
	}

	public ErroImportCtrcTable getTable() {
		return table;
	}

	public void setTable(ErroImportCtrcTable table) {
		this.table = table;
	}
	
	
}

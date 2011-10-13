package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.form.ParametroForm;
import br.com.promove.view.table.ParametroTable;

public class ParametroView extends SplitPanel{

	private ParametroTable table;
	private ParametroForm form;
	
	public ParametroView(ParametroTable table, ParametroForm form) {
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

	public ParametroTable getTable() {
		return table;
	}

	public void setTable(ParametroTable table) {
		this.table = table;
	}

	public ParametroForm getForm() {
		return form;
	}

	public void setForm(ParametroForm form) {
		this.form = form;
	}

}

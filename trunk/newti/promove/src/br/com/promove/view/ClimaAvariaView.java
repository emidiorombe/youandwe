package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.form.ClimaForm;
import br.com.promove.view.table.ClimaTable;



public class ClimaAvariaView extends SplitPanel{

	private ClimaTable table;
	private ClimaForm form;
	
	public ClimaAvariaView(ClimaTable table, ClimaForm form) {
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

	public ClimaTable getTable() {
		return table;
	}

	public void setTable(ClimaTable table) {
		this.table = table;
	}

	public ClimaForm getForm() {
		return form;
	}

	public void setForm(ClimaForm form) {
		this.form = form;
	}

}

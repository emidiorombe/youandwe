package br.com.promove.view;

import br.com.promove.view.form.VeiculoSearchForm;
import br.com.promove.view.table.VeiculoTable;

import com.vaadin.ui.SplitPanel;

public class VeiculoListView extends SplitPanel{
	private VeiculoTable table;
	private VeiculoSearchForm form;
	
	public VeiculoListView(VeiculoTable table, VeiculoSearchForm form) {
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

	public VeiculoTable getTable() {
		return table;
	}

	public void setTable(VeiculoTable table) {
		this.table = table;
	}

	public VeiculoSearchForm getForm() {
		return form;
	}

	public void setForm(VeiculoSearchForm form) {
		this.form = form;
	}
	
	
}

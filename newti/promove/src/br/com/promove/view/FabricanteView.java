package br.com.promove.view;

import br.com.promove.view.form.FabricanteForm;
import br.com.promove.view.table.FabricanteTable;

import com.vaadin.ui.SplitPanel;

public class FabricanteView extends SplitPanel{

	private FabricanteTable table;
	private FabricanteForm form;

	public FabricanteView(FabricanteTable table, FabricanteForm form) {
		this.table = table;
		this.form = form;
		buildView();
	}

	private void buildView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(table);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(40);
		
	}

	public FabricanteTable getTable() {
		return table;
	}

	public void setTable(FabricanteTable table) {
		this.table = table;
	}

	public FabricanteForm getForm() {
		return form;
	}

	public void setForm(FabricanteForm form) {
		this.form = form;
	}
	
	
	
}

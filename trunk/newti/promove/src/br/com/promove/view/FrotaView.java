package br.com.promove.view;

import br.com.promove.view.form.FrotaForm;
import br.com.promove.view.table.FrotaTable;

import com.vaadin.ui.SplitPanel;

public class FrotaView extends SplitPanel{

	private FrotaTable table;
	private FrotaForm form;

	public FrotaView(FrotaTable table, FrotaForm form) {
		this.table = table;
		this.form = form;
		buildView();
	}
	
	public  void buildView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(table);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(40);
		
	}

	public FrotaTable getTable() {
		return table;
	}

	public void setTable(FrotaTable table) {
		this.table = table;
	}

	public FrotaForm getForm() {
		return form;
	}

	public void setForm(FrotaForm form) {
		this.form = form;
	}
	
}

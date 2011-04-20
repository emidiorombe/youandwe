package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.form.TransportadoraForm;
import br.com.promove.view.table.TransportadoraTable;

public class TransportadoraView extends SplitPanel{

	private TransportadoraTable table;
	private TransportadoraForm form;
	
	public TransportadoraView(TransportadoraTable table, TransportadoraForm form) {
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

	public TransportadoraTable getTable() {
		return table;
	}

	public void setTable(TransportadoraTable table) {
		this.table = table;
	}

	public TransportadoraForm getForm() {
		return form;
	}

	public void setForm(TransportadoraForm form) {
		this.form = form;
	}

}

package br.com.promove.view;


import br.com.promove.view.form.CorForm;
import br.com.promove.view.table.CorTable;

import com.vaadin.ui.SplitPanel;

public class CorView extends SplitPanel{
	private CorTable table;
	private CorForm form;
	
	public CorView(CorTable table, CorForm form) {
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

	public CorTable getTable() {
		return table;
	}

	public void setTable(CorTable table) {
		this.table = table;
	}

	public CorForm getForm() {
		return form;
	}

	public void setForm(CorForm form) {
		this.form = form;
	}
	
	
	
	
}

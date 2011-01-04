package br.com.promove.view;

import br.com.promove.view.form.ExtensaoAvariaForm;
import br.com.promove.view.table.ExtensaoAvariaTable;

import com.vaadin.ui.SplitPanel;

public class ExtensaoAvariaView extends SplitPanel {

	private ExtensaoAvariaTable table;
	private ExtensaoAvariaForm form;
	
	private ExtensaoAvariaView() {}
	
	public ExtensaoAvariaView(ExtensaoAvariaTable table, ExtensaoAvariaForm form) {
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

	public ExtensaoAvariaTable getTable() {
		return table;
	}

	public void setTable(ExtensaoAvariaTable table) {
		this.table = table;
	}

	public ExtensaoAvariaForm getForm() {
		return form;
	}

	public void setForm(ExtensaoAvariaForm form) {
		this.form = form;
	}
	
	

}

package br.com.promove.view;

import br.com.promove.view.form.UsuarioForm;
import br.com.promove.view.table.UsuarioTable;

import com.vaadin.ui.SplitPanel;

public class UsuarioView  extends SplitPanel{

	private UsuarioTable table;
	private UsuarioForm form;

	public UsuarioView(UsuarioTable table, UsuarioForm form) {
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

	public UsuarioTable getTable() {
		return table;
	}

	public void setTable(UsuarioTable table) {
		this.table = table;
	}

	public UsuarioForm getForm() {
		return form;
	}

	public void setForm(UsuarioForm form) {
		this.form = form;
	}
	
}

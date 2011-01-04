package br.com.promove.view;

import br.com.promove.view.form.OrigemAvariaForm;
import br.com.promove.view.table.OrigemAvariaTable;

import com.vaadin.ui.SplitPanel;

public class OrigemAvariaView extends SplitPanel{
	private OrigemAvariaTable table;
	private OrigemAvariaForm form;
	
	public OrigemAvariaView() {}
	
	public OrigemAvariaView(OrigemAvariaTable table, OrigemAvariaForm form) {
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

	public OrigemAvariaTable getTable() {
		return table;
	}

	public void setTable(OrigemAvariaTable table) {
		this.table = table;
	}

	public OrigemAvariaForm getForm() {
		return form;
	}

	public void setForm(OrigemAvariaForm form) {
		this.form = form;
	}
	
	
}

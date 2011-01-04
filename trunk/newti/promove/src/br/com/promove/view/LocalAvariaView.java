package br.com.promove.view;

import br.com.promove.view.form.LocalAvariaForm;
import br.com.promove.view.table.LocalAvariaTable;

import com.vaadin.ui.SplitPanel;

public class LocalAvariaView extends SplitPanel{
	private LocalAvariaTable table;
	private LocalAvariaForm form;

	public LocalAvariaView() {}
	
	public LocalAvariaView(LocalAvariaTable table, LocalAvariaForm form) {
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

	public LocalAvariaTable getTable() {
		return table;
	}

	public void setTable(LocalAvariaTable table) {
		this.table = table;
	}

	public LocalAvariaForm getForm() {
		return form;
	}

	public void setForm(LocalAvariaForm form) {
		this.form = form;
	}
	
	
}

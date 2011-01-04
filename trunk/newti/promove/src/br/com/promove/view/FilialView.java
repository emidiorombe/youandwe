package br.com.promove.view;

import br.com.promove.view.form.FilialForm;
import br.com.promove.view.table.FilialTable;

import com.vaadin.ui.SplitPanel;

public class FilialView extends SplitPanel{

	private FilialTable table;
	private FilialForm form;

	public FilialView(FilialTable table, FilialForm form) {
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

	public FilialTable getTable() {
		return table;
	}

	public void setTable(FilialTable table) {
		this.table = table;
	}

	public FilialForm getForm() {
		return form;
	}

	public void setForm(FilialForm form) {
		this.form = form;
	}
	
	

}

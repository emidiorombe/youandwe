package br.com.promove.view;

import br.com.promove.view.form.MotoristaForm;
import br.com.promove.view.table.MotoristaTable;

import com.vaadin.ui.SplitPanel;

public class MotoristaView extends SplitPanel{

	private MotoristaTable table;
	private MotoristaForm form;

	public MotoristaView(MotoristaTable table, MotoristaForm form) {
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

	public MotoristaTable getTable() {
		return table;
	}

	public void setTable(MotoristaTable table) {
		this.table = table;
	}

	public MotoristaForm getForm() {
		return form;
	}

	public void setForm(MotoristaForm form) {
		this.form = form;
	}
	
}

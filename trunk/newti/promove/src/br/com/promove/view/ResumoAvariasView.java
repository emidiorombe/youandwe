package br.com.promove.view;


import br.com.promove.view.table.ResumoAvariasTable;
import br.com.promove.view.form.ResumoAvariasForm;

import com.vaadin.ui.SplitPanel;

public class ResumoAvariasView extends SplitPanel {
	private ResumoAvariasTable table;
	private ResumoAvariasForm form;
	
	public ResumoAvariasView(ResumoAvariasTable table, ResumoAvariasForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(form.getLayout());
		setSecondComponent(table);
		setSplitPosition(60);
		
	}
	
	public ResumoAvariasForm getForm() {
		return form;
	}
	public void setForm(ResumoAvariasForm form) {
		this.form = form;
	}
	
	public ResumoAvariasTable getTable() {
		return table;
	}
	
	public void setTable(ResumoAvariasTable table) {
		this.table = table;
	}
}

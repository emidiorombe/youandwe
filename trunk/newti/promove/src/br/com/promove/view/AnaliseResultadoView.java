package br.com.promove.view;


import br.com.promove.view.table.AnaliseResultadoTable;
import br.com.promove.view.form.AnaliseResultadoForm;

import com.vaadin.ui.SplitPanel;

public class AnaliseResultadoView extends SplitPanel {
	private AnaliseResultadoTable table;
	private AnaliseResultadoForm form;
	
	public AnaliseResultadoView(AnaliseResultadoTable table, AnaliseResultadoForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(form.getLayout());
		setSecondComponent(table);
		setSplitPosition(45);
		
	}
	
	public AnaliseResultadoForm getForm() {
		return form;
	}
	public void setForm(AnaliseResultadoForm form) {
		this.form = form;
	}
	
	public AnaliseResultadoTable getTable() {
		return table;
	}
	
	public void setTable(AnaliseResultadoTable table) {
		this.table = table;
	}
}

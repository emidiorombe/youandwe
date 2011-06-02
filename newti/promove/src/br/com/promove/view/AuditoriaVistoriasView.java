package br.com.promove.view;


import br.com.promove.view.form.AuditoriaVistoriasForm;
import br.com.promove.view.table.AuditoriaVistoriasTable;

import com.vaadin.ui.SplitPanel;

public class AuditoriaVistoriasView extends SplitPanel {

	private AuditoriaVistoriasForm form;
	private AuditoriaVistoriasTable table;
	
	public AuditoriaVistoriasView(AuditoriaVistoriasTable table, AuditoriaVistoriasForm form) {
		this.table = table;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(form.getLayout());
		setSecondComponent(table);
		setSplitPosition(40);
		
	}
	
	public AuditoriaVistoriasForm getForm() {
		return form;
	}
	public void setForm(AuditoriaVistoriasForm form) {
		this.form = form;
	}
	public AuditoriaVistoriasTable getTable() {
		return table;
	}
	public void setTable(AuditoriaVistoriasTable table) {
		this.table = table;
	}
	
	

}

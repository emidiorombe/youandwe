package br.com.promove.view;


import br.com.promove.view.form.AuditoriaVistoriasForm;

import com.vaadin.ui.SplitPanel;

public class AuditoriaVistoriasView extends SplitPanel {
	private AuditoriaVistoriasTables tables;
	private AuditoriaVistoriasForm form;
	
	public AuditoriaVistoriasView(AuditoriaVistoriasTables tables, AuditoriaVistoriasForm form) {
		this.tables = tables;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		tables.setView(this);
		form.setView(this);
		setFirstComponent(form.getLayout());
		setSecondComponent(tables);
		setSplitPosition(55);
		
	}
	
	public AuditoriaVistoriasForm getForm() {
		return form;
	}
	public void setForm(AuditoriaVistoriasForm form) {
		this.form = form;
	}
	
	public AuditoriaVistoriasTables getTables() {
		return tables;
	}
	
	public void setTable(AuditoriaVistoriasTables tables) {
		this.tables = tables;
	}
	
	

}

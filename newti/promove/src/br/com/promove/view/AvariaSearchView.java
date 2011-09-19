package br.com.promove.view;


import br.com.promove.view.form.AvariaSearchForm;
import br.com.promove.view.table.AvariaTable;

import com.vaadin.ui.SplitPanel;

public class AvariaSearchView extends SplitPanel {

	private AvariaSearchForm form;
	private AvariaTable table;
	
	public AvariaSearchView(AvariaTable table, AvariaSearchForm form) {
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
	
	public AvariaSearchForm getForm() {
		return form;
	}
	public void setForm(AvariaSearchForm form) {
		this.form = form;
	}
	public AvariaTable getTable() {
		return table;
	}
	public void setTable(AvariaTable table) {
		this.table = table;
	}
	
	

}

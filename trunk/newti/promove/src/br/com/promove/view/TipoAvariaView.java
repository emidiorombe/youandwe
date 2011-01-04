package br.com.promove.view;

import br.com.promove.view.form.TipoAvariaForm;
import br.com.promove.view.table.TipoAvariaTable;

import com.vaadin.ui.SplitPanel;

public class TipoAvariaView extends SplitPanel{
	
	private TipoAvariaTable table;
	private TipoAvariaForm form;

	public TipoAvariaView(){}
	
	public TipoAvariaView(TipoAvariaTable table, TipoAvariaForm form){
		this.table = table;
		this.form = form;
		buildListView();
	}

	private void buildListView() {
		table.setView(this);
		form.setView(this);
		setFirstComponent(table);
		setSecondComponent(form.getFormLayout());
		setSplitPosition(40);
		
	}

	public TipoAvariaTable getTable() {
		return table;
	}

	public void setTable(TipoAvariaTable table) {
		this.table = table;
	}

	public TipoAvariaForm getForm() {
		return form;
	}

	public void setForm(TipoAvariaForm form) {
		this.form = form;
	}
	
	
	
}

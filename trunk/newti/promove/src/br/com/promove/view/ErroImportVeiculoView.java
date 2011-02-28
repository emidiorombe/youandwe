package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.form.ErroImportVeiculoForm;
import br.com.promove.view.table.ErroImportVeiculoTable;

public class ErroImportVeiculoView extends SplitPanel{

	private ErroImportVeiculoTable table;
	private ErroImportVeiculoForm form;

	public ErroImportVeiculoView(ErroImportVeiculoTable table,	ErroImportVeiculoForm form) {
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

	public ErroImportVeiculoTable getTable() {
		return table;
	}

	public void setTable(ErroImportVeiculoTable table) {
		this.table = table;
	}

	public ErroImportVeiculoForm getForm() {
		return form;
	}

	public void setForm(ErroImportVeiculoForm form) {
		this.form = form;
	}
	
	

}

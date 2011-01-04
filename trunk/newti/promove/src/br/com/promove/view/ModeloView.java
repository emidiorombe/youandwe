package br.com.promove.view;

import br.com.promove.view.form.ModeloForm;
import br.com.promove.view.table.ModeloTable;

import com.vaadin.ui.SplitPanel;

public class ModeloView extends SplitPanel{

	private ModeloTable table;
	private ModeloForm form;

	public ModeloView(ModeloTable table, ModeloForm form) {
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

	public ModeloTable getTable() {
		return table;
	}

	public void setTable(ModeloTable table) {
		this.table = table;
	}

	public ModeloForm getForm() {
		return form;
	}

	public void setForm(ModeloForm form) {
		this.form = form;
	}
	
}

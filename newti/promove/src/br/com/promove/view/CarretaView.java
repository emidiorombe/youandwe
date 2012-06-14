package br.com.promove.view;

import br.com.promove.view.form.CarretaForm;
import br.com.promove.view.table.CarretaTable;

import com.vaadin.ui.SplitPanel;

public class CarretaView extends SplitPanel{

	private CarretaTable table;
	private CarretaForm form;

	public CarretaView(CarretaTable table, CarretaForm form) {
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

	public CarretaTable getTable() {
		return table;
	}

	public void setTable(CarretaTable table) {
		this.table = table;
	}

	public CarretaForm getForm() {
		return form;
	}

	public void setForm(CarretaForm form) {
		this.form = form;
	}
	
}

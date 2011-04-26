package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.form.VeiculoSearchForm;
import br.com.promove.view.table.AvariaVeiculoTable;
import br.com.promove.view.table.VeiculoTable;

public class VeiculoAvariaTables extends SplitPanel {
	private VeiculoTable tableVeiculo;
	private AvariaVeiculoTable tableAvaria;
	private VeiculoListView view;

	public VeiculoAvariaTables(VeiculoTable tableVeiculo, AvariaVeiculoTable tableAvaria) {
		this.tableVeiculo = tableVeiculo;
		this.tableAvaria = tableAvaria;
		buildListView();
	}
	private void buildListView() {
		tableVeiculo.setView(this);
		tableAvaria.setView(this);
		setFirstComponent(tableVeiculo);
		setSecondComponent(tableAvaria);
		setSplitPosition(50);
		
	}

	public void setTableVeiculo(VeiculoTable tableVeiculo) {
		this.tableVeiculo = tableVeiculo;
	}

	public VeiculoTable getTableVeiculo() {
		return tableVeiculo;
	}

	public void setTableAvaria(AvariaVeiculoTable tableAvaria) {
		this.tableAvaria = tableAvaria;
	}

	public AvariaVeiculoTable getTableAvaria() {
		return tableAvaria;
	}
	public void setView(VeiculoListView view) {
		this.view = view;
	}
	public VeiculoListView getView() {
		return view;
	}

}

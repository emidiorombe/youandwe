package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.table.CtrcTable;
import br.com.promove.view.table.VeiculoCtrcTable;

public class CtrcVeiculoTables extends SplitPanel {
	private CtrcTable tableCtrc;
	private VeiculoCtrcTable tableVeiculo;
	private CtrcView view;

	public CtrcVeiculoTables(CtrcTable tableCtrc, VeiculoCtrcTable tableVeiculo) {
		this.tableCtrc = tableCtrc;
		this.tableVeiculo = tableVeiculo;
		buildListView();
	}
	private void buildListView() {
		tableCtrc.setView(this);
		tableVeiculo.setView(this);
		setFirstComponent(tableCtrc);
		setSecondComponent(tableVeiculo);
		setSplitPosition(50);
	}
	
	public void setTableCtrc(CtrcTable tableCtrc) {
		this.tableCtrc = tableCtrc;
	}
	
	public CtrcTable getTableCtrc() {
		return tableCtrc;
	}
	
	public void setTableVeiculo(VeiculoCtrcTable tableVeiculo) {
		this.tableVeiculo = tableVeiculo;
	}
	
	public VeiculoCtrcTable getTableVeiculo() {
		return tableVeiculo;
	}
	
	public void setView(CtrcView view) {
		this.view = view;
	}
	
	public CtrcView getView() {
		return view;
	}
}

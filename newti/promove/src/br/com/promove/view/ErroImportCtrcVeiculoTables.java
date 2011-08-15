package br.com.promove.view;

import com.vaadin.ui.SplitPanel;

import br.com.promove.view.table.CtrcTable;
import br.com.promove.view.table.ErroImportCtrcTable;
import br.com.promove.view.table.ErroImportVeiculoCtrcTable;
import br.com.promove.view.table.VeiculoCtrcTable;

public class ErroImportCtrcVeiculoTables extends SplitPanel {
	private ErroImportCtrcTable tableCtrc;
	private ErroImportVeiculoCtrcTable tableVeiculo;
	private ErroImportCtrcView view;

	public ErroImportCtrcVeiculoTables(ErroImportCtrcTable tableCtrc, ErroImportVeiculoCtrcTable tableVeiculo) {
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
	
	public void setTableCtrc(ErroImportCtrcTable tableCtrc) {
		this.tableCtrc = tableCtrc;
	}
	
	public ErroImportCtrcTable getTableCtrc() {
		return tableCtrc;
	}
	
	public void setTableVeiculo(ErroImportVeiculoCtrcTable tableVeiculo) {
		this.tableVeiculo = tableVeiculo;
	}
	
	public ErroImportVeiculoCtrcTable getTableVeiculo() {
		return tableVeiculo;
	}
	
	public void setView(ErroImportCtrcView view) {
		this.view = view;
	}
	
	public ErroImportCtrcView getView() {
		return view;
	}
}

package br.com.promove.view;

import br.com.promove.view.table.AvariaVeiculoTable;
import br.com.promove.view.table.VeiculoTable;

public class AuditoriaVistoriasTables extends VeiculoAvariaTables {
	private AuditoriaVistoriasView view;

	public AuditoriaVistoriasTables(VeiculoTable tableVeiculo, AvariaVeiculoTable tableAvaria) {
		super(tableVeiculo, tableAvaria);
	}
	
	public void setView(AuditoriaVistoriasView view) {
		this.view = view;
	}
}

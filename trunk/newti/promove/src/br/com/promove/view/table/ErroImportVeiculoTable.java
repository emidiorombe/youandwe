package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.InconsistenciaVeiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportVeiculoView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class ErroImportVeiculoTable extends Table {
	
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"chassi", "modelo", "cor", "tipo", "navio", "msgErro"};
	public static final String[] COL_HEADERS = new String[] {"Chassi", "Modelo", "Cor", "Tipo", "Navio", "Mensagem"};
	
	private ErroImportVeiculoView view;
	private CadastroService cadastroService;
	private ErroImportVeiculoContainer container;
	
	public ErroImportVeiculoTable() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildTable();
	}

	private void buildTable() {
		setSizeFull();
		setColumnCollapsingAllowed(true);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setContainerDataSource(getContainer());
		setVisibleColumns(NATURAL_COL_ORDER);
		setColumnHeaders(COL_HEADERS);
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("tipo", new ErroImportVeiculoColumnGenerator());

	}
	

	public BeanItemContainer<InconsistenciaVeiculo> getContainer(){
		if(container == null)
			container = new ErroImportVeiculoContainer();
		return container;
	}

	public void setView(ErroImportVeiculoView view) {
		this.view = view;
		
	}
	
	public void reloadTable() {
		container.removeAllItems();
		container.populate();
	}
	
	class ErroImportVeiculoContainer extends BeanItemContainer<InconsistenciaVeiculo>  implements Serializable{
		
		public ErroImportVeiculoContainer() {
			super(InconsistenciaVeiculo.class);
			populate();
		}
		
		private void populate() {
			try {
				List<InconsistenciaVeiculo> list = cadastroService.buscarTodasInconsistenciasDeVeiculos();
				for (InconsistenciaVeiculo inc : list) {
					addItem(inc);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<InconsistenciaVeiculo> item =  (BeanItem<InconsistenciaVeiculo>) getItem(getValue());
            view.getForm().createFormBody(item);
			
		}
		
	}
	
	class ErroImportVeiculoColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			InconsistenciaVeiculo inc = (InconsistenciaVeiculo) itemId;
			if(columnId.equals("tipo")) {
				return new Label(inc.getTipo().getNome());
			}
			return null;
		}
		
	}

}

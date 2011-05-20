package br.com.promove.view.table;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportCtrcView;

public class ErroImportCtrcTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"filial", "numero", "tipo", "serie", "transp", "dataEmissao", "placaFrota", "placaCarreta", "ufOrigem", "municipioOrigem", "ufDestino", "municipioDestino", "taxaRct", "taxaRr", "taxaRcf", "taxaFluvial", "msgErro", "valorMercadoria"};
	public static final String[] COL_HEADERS = new String[] {"Filial", "Numero", "Tipo", "Série", "Transportadora", "Data", "Frota", "Carreta", "UF", "Origem", "UF", "Destino", "RCT", "RR", "RCF", "Fluvial", "Valor Mercadoria", "Mensagem"};
	
	private ErroImportCtrcView view;
	private CtrcService ctrcService;
	private ErroImportCtrcContainer container;
	
	public ErroImportCtrcTable() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
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
		
		addGeneratedColumn("dataEmissao", new ErroImportVeiculoColumnGenerator());
		
		try {
			setColumnCollapsed("placaFrota", true);
			setColumnCollapsed("placaCarreta", true);
			setColumnCollapsed("ufOrigem", true);
			setColumnCollapsed("municipioOrigem", true);
			setColumnCollapsed("ufDestino", true);
			setColumnCollapsed("municipioDestino", true);
			setColumnCollapsed("taxaRr", true);
			setColumnCollapsed("taxaRcf", true);
			setColumnCollapsed("taxaFluvial", true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setView(ErroImportCtrcView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<InconsistenciaCtrc> getContainer(){
		if(container == null)
			container = new ErroImportCtrcContainer();
		return container;
	}
	
	public void reloadTable() {
		container.removeAllItems();
		container.populate();
	}
	
	class ErroImportCtrcContainer extends BeanItemContainer<InconsistenciaCtrc>  implements Serializable{
		
		public ErroImportCtrcContainer() {
			super(InconsistenciaCtrc.class);
			populate();
		}
		
		private void populate() {
			try {
				List<InconsistenciaCtrc> list = ctrcService.buscarTodasInconsistenciasCtrc();
				for (InconsistenciaCtrc inc : list) {
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
			BeanItem<InconsistenciaCtrc> item =  (BeanItem<InconsistenciaCtrc>) getItem(getValue());
            view.getForm().createFormBody(item);
			
		}
		
	}
	
	class ErroImportVeiculoColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			InconsistenciaCtrc inc = (InconsistenciaCtrc) itemId;
			if(columnId.equals("dataEmissao")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(inc.getDataEmissao())); 
			}
			return null;
		}
		
	}

}

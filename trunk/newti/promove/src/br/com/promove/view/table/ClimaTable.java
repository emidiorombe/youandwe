package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Clima;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ClimaAvariaView;
import br.com.promove.view.table.ClimaTable.ClimaContainer;
import br.com.promove.view.table.ClimaTable.RowSelectedListener;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class ClimaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição"};

	private ClimaAvariaView view;
	private AvariaService avariaService;
	private ClimaContainer container;
	
	public ClimaTable() {
		avariaService = ServiceFactory.getService(AvariaService.class);
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
		
		
	}

	public void setView(ClimaAvariaView view) {
		this.view = view;
	}

	public BeanItemContainer<Clima> getContainer() {
		if(container == null)
			container = new ClimaContainer();
		return container;
	}
	
	class ClimaContainer extends BeanItemContainer<Clima> implements Serializable {
	
		public ClimaContainer(){
			super(Clima.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Clima> list = avariaService.buscarTodosClimas();
				for (Clima Clima : list) {
					addItem(Clima);
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
				 BeanItem<Clima> item =  (BeanItem<Clima>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}
	

}

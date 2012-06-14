package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Carreta;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CarretaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class CarretaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "placa"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Placa"};
	
	private CarretaView view;
	private CadastroService cadastroService;
	private CarretaTableContainer container;
	
	public CarretaTable() {
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
	}

	public void setView(CarretaView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Carreta> getContainer() {
		if(container == null)
			container = new CarretaTableContainer();
		return container;
	}
	
	class CarretaTableContainer extends BeanItemContainer<Carreta> implements Serializable {
		
		public CarretaTableContainer(){
			super(Carreta.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Carreta> list = cadastroService.buscarTodasCarretas("id");
				for (Carreta f: list) {
					addItem(f);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class CarretaTableColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Carreta f = (Carreta)itemId;
			return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<Carreta> item =  (BeanItem<Carreta>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

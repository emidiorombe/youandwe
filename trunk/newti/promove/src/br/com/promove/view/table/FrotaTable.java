package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Frota;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.FrotaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class FrotaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "placa"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Placa"};
	
	private FrotaView view;
	private CadastroService cadastroService;
	private FrotaTableContainer container;
	
	public FrotaTable() {
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

	public void setView(FrotaView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Frota> getContainer() {
		if(container == null)
			container = new FrotaTableContainer();
		return container;
	}
	
	class FrotaTableContainer extends BeanItemContainer<Frota> implements Serializable {
		
		public FrotaTableContainer(){
			super(Frota.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Frota> list = cadastroService.buscarTodasFrotas("id");
				for (Frota f: list) {
					addItem(f);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class FrotaTableColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Frota f = (Frota)itemId;
			return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<Frota> item =  (BeanItem<Frota>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ExtensaoAvariaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class ExtensaoAvariaTable extends Table {
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição"};
	
	private ExtensaoAvariaView view;
	private AvariaService avariaService;
	private ExtensaoAvariaContainer container;
	
	public ExtensaoAvariaTable() {
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

	public void setView(ExtensaoAvariaView view) {
		this.view = view;
	}

	public BeanItemContainer<ExtensaoAvaria> getContainer() {
		if(container == null)
			container = new ExtensaoAvariaContainer();
		return container;
	}
	
	class ExtensaoAvariaContainer extends BeanItemContainer<ExtensaoAvaria> implements Serializable {
		
		public ExtensaoAvariaContainer(){
			super(ExtensaoAvaria.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<ExtensaoAvaria> list = avariaService.buscarTodasExtensoesAvaria();
				for (ExtensaoAvaria ext : list) {
					addItem(ext);
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
				 BeanItem<ExtensaoAvaria> item =  (BeanItem<ExtensaoAvaria>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

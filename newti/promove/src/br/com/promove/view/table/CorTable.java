package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CorView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class CorTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao", "codigoExterno"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição", "Código Externo"};

	private CorView view;
	private CadastroService cadastroService;
	private CorContainer container;
	
	public CorTable() {
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

	public void setView(CorView view) {
		this.view = view;
	}

	public BeanItemContainer<Cor> getContainer() {
		if(container == null)
			container = new CorContainer();
		return container;
	}
	
	class CorContainer extends BeanItemContainer<Cor> implements Serializable {
	
		public CorContainer(){
			super(Cor.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Cor> list = cadastroService.buscarTodasCores();
				for (Cor cor : list) {
					addItem(cor);
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
				 BeanItem<Cor> item =  (BeanItem<Cor>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}
	
}

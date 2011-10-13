package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Parametro;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ParametroView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class ParametroTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"chave", "valor"};
	public static final String[] COL_HEADERS = new String[] {"Chave", "Valor"};

	private ParametroView view;
	private CadastroService cadastroService;
	private ParametroContainer container;
	
	public ParametroTable() {
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

	public void setView(ParametroView view) {
		this.view = view;
	}

	public BeanItemContainer<Parametro> getContainer() {
		if(container == null)
			container = new ParametroContainer();
		return container;
	}
	
	class ParametroContainer extends BeanItemContainer<Parametro> implements Serializable {
	
		public ParametroContainer(){
			super(Parametro.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Parametro> list = cadastroService.buscarTodosParametros();
				for (Parametro Parametro : list) {
					addItem(Parametro);
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
				 BeanItem<Parametro> item =  (BeanItem<Parametro>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}
	

}

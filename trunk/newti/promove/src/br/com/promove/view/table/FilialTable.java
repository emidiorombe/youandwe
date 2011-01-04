package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Filial;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.FilialView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class FilialTable extends Table{

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "nome", "sigla", "codigoExterno"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Nome", "Sigla", "Código Externo"};
	
	private FilialView view;
	private CadastroService cadastroService;
	private FilialTableContainer container;

	public FilialTable() {
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

	public void setView(FilialView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Filial> getContainer() {
		if(container == null)
			container = new FilialTableContainer();
		return container;
	}
	
	class FilialTableContainer extends BeanItemContainer<Filial> implements Serializable {
		
		public FilialTableContainer(){
			super(Filial.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Filial> list =cadastroService.buscarTodasFiliais();
				for (Filial fab : list) {
					addItem(fab);
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
				 BeanItem<Filial> item =  (BeanItem<Filial>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.OrigemAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.OrigemAvariaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class OrigemAvariaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao", "responsabilidade", "sigla", "filial", "tipo"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição", "Responsabilidade", "Sigla", "Filial", "Tipo"};


	private OrigemAvariaView view;
	private OrigemAvariaContainer container;
	private AvariaService avariaService;	
	
	public OrigemAvariaTable() {
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

	public void setView(OrigemAvariaView view) {
		this.view = view;
		
	}

	public BeanItemContainer<OrigemAvaria> getContainer(){
		if(container == null)
			container = new OrigemAvariaContainer();
		return container;
	}
	
	class OrigemAvariaContainer extends BeanItemContainer<OrigemAvaria> implements Serializable {
		
		public OrigemAvariaContainer(){
			super(OrigemAvaria.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<OrigemAvaria> list = avariaService.buscarTodasOrigensAvaria("codigo");
				for (OrigemAvaria or : list) {
					addItem(or);
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
				 BeanItem<OrigemAvaria> item =  (BeanItem<OrigemAvaria>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.LocalAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.LocalAvariaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class LocalAvariaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao", "acessorio", "descricaoSeguradora"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição", "Acessório", "Descrição Seguradora"};
	
	private LocalAvariaView view;
	private AvariaService avariaService;
	private LocalAvariaContainer container;
	
	public LocalAvariaTable() {
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
		
		addGeneratedColumn("acessorio", new LocalAvariaColumnGenerator());
		
	}

	public void setView(LocalAvariaView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<LocalAvaria> getContainer() {
		if(container == null)
			container = new LocalAvariaContainer();
		return container;
	}
	
	class LocalAvariaContainer extends BeanItemContainer<LocalAvaria> implements Serializable {
		
		public LocalAvariaContainer(){
			super(LocalAvaria.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<LocalAvaria> list = avariaService.buscarTodosLocaisAvaria("codigo");
				for (LocalAvaria local : list) {
					addItem(local);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class LocalAvariaColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			LocalAvaria loc = (LocalAvaria)itemId;
			if(columnId.toString().equals("acessorio"))
				return new Label(StringUtilities.booleanToReadableString(loc.getAcessorio()));
			else 
				return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<LocalAvaria> item =  (BeanItem<LocalAvaria>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.TipoAvariaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class TipoAvariaTable extends Table {
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao", "falta", "perdaTotal", "movimentacao", "descricaoSeguradora"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição", "Falta", "PT", "Movimentação", "Descrição Seguradora"};
	private TipoAvariaView view;
	private TipoAvariaContainer container;
	private AvariaService avariaService;
	
	public TipoAvariaTable() {
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
		
		addGeneratedColumn("falta", new TipoAvariaColumnGenerator());
		addGeneratedColumn("perdaTotal", new TipoAvariaColumnGenerator());
		addGeneratedColumn("movimentacao", new TipoAvariaColumnGenerator());

	}
	
	public void setView(TipoAvariaView view) {
		this.view = view;
	}
	
	public BeanItemContainer<TipoAvaria> getContainer(){
		if(container == null)
			container = new TipoAvariaContainer();
		return container;
	}



	class TipoAvariaContainer extends BeanItemContainer<TipoAvaria> implements Serializable {
		
		public TipoAvariaContainer(){
			super(TipoAvaria.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<TipoAvaria> list = avariaService.buscarTodosTipoAvaria("codigo");
				for (TipoAvaria tipoAvaria : list) {
					addItem(tipoAvaria);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	class TipoAvariaColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			TipoAvaria tp = (TipoAvaria)itemId;
			if(columnId.toString().equals("falta")) {
				Label l = new Label(StringUtilities.booleanToReadableString(tp.getFalta()));
				l.setImmediate(true);
				return l;
			} else if(columnId.toString().equals("perdaTotal"))
				return new Label(StringUtilities.booleanToReadableString(tp.getPerdaTotal()));
			else if(columnId.toString().equals("movimentacao"))
				return new Label(StringUtilities.booleanToReadableString(tp.getMovimentacao()));
			else 
				return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<TipoAvaria> item =  (BeanItem<TipoAvaria>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}
	
}

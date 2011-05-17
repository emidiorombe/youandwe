package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Modelo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ModeloView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class ModeloTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "descricao", "fabricante", "codigoExternoNacional", "codigoExternoImportacao"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Descrição", "Fabricante", "Código Nacional", "Código Importação"};
	
	private ModeloView view;
	private CadastroService cadastroService;
	private ModeloTableContainer container;
	
	public ModeloTable() {
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
		
		addGeneratedColumn("fabricante", new ModeloTableColumnGenerator());
	}

	public void setView(ModeloView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Modelo> getContainer() {
		if(container == null)
			container = new ModeloTableContainer();
		return container;
	}
	
	class ModeloTableContainer extends BeanItemContainer<Modelo> implements Serializable {
		
		public ModeloTableContainer(){
			super(Modelo.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Modelo> list = cadastroService.buscarTodosModelos("id");
				for (Modelo mo : list) {
					addItem(mo);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class ModeloTableColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Modelo mo = (Modelo)itemId;
			if(columnId.toString().equals("fabricante"))
				return new Label(mo.getFabricante().getNome());
			else 
				return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<Modelo> item =  (BeanItem<Modelo>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}

}

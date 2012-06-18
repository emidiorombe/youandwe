package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Motorista;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.MotoristaView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class MotoristaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "nome", "cpf", "rg", "cnh", "frota", "carreta", "ativo"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Nome", "CPF", "RG", "CNH", "Frota", "Carreta", "Ativo"};
	
	private MotoristaView view;
	private CadastroService cadastroService;
	private MotoristaTableContainer container;
	
	public MotoristaTable() {
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

		addGeneratedColumn("ativo", new MotoristaTableColumnGenerator());
	}

	public void setView(MotoristaView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Motorista> getContainer() {
		if(container == null)
			container = new MotoristaTableContainer();
		return container;
	}
	
	class MotoristaTableContainer extends BeanItemContainer<Motorista> implements Serializable {
		
		public MotoristaTableContainer(){
			super(Motorista.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Motorista> list = cadastroService.buscarTodosMotoristas("id");
				for (Motorista f: list) {
					addItem(f);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class MotoristaTableColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Motorista m = (Motorista)itemId;
			if(columnId.toString().equals("ativo")) {
				return new Label(StringUtilities.booleanToReadableString(m.getAtivo()));
			} else {
				return null;
			}
		}
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<Motorista> item =  (BeanItem<Motorista>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
	}
}

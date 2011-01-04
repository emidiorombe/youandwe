package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Usuario;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.UsuarioView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class UsuarioTable extends Table {

	private UsuarioView view;
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "nome", "mail", "filial", "tipo"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Nome", "E-mail", "Filial", "Tipo"};
	
	private CadastroService cadastroService;
	private UsuarioTableContainer container;
	
	public UsuarioTable() {
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
		
		addGeneratedColumn("filial", new UsuarioTableColumnGenerator());
	}

	public void setView(UsuarioView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<Usuario> getContainer() {
		if(container == null)
			container = new UsuarioTableContainer();
		return container;
	}
	
	class UsuarioTableContainer extends BeanItemContainer<Usuario> implements Serializable {
		
		public UsuarioTableContainer(){
			super(Usuario.class);
			populate();
		}
		
		
		private void populate() {
			try {
				List<Usuario> list = cadastroService.buscarTodosUsuarios();
				for (Usuario mo : list) {
					addItem(mo);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class UsuarioTableColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Usuario us = (Usuario)itemId;
			if(columnId.toString().equals("filial"))
				return new Label(us.getFilial().getNome());
			else 
				return null;
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
				 BeanItem<Usuario> item =  (BeanItem<Usuario>) getItem(getValue());
                 view.getForm().createFormBody(item);
			
		}
		
	}
}

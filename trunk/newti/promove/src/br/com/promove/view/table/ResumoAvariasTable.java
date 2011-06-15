package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.entity.Cor;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ResumoAvariasView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class ResumoAvariasTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"descricao", "codigoExterno", "codigo"};
	public static final String[] COL_HEADERS = new String[] {"Item", "Sub-item", "Quantidade"};

	private ResumoAvariasView view;
	private CadastroService cadastroService;
	private CorContainer container;
	
	public ResumoAvariasTable() {
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

	public void filterTable(List<Cor> cores) {
		container.populate(cores);
	}
	
	public void setView(ResumoAvariasView view) {
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
		}
		
		private void populate(List<Cor> cores) {
			container.removeAllItems();
			for (Cor cor : cores) {
				addItem(cor);
			}
		}
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<Cor> item =  (BeanItem<Cor>) getItem(getValue());
		}
	}
}

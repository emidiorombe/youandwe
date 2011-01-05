package br.com.promove.view.table;

import java.io.Serializable;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.form.VeiculoForm;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class VeiculoTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"codigo", "chassi", "modelo", "cor", "avarias"};
	public static final String[] COL_HEADERS = new String[] {"Código", "Chassi", "Modelo", "Cor", "Avarias"};
	
	private CadastroService cadastroService;
	private VeiculoTableContainer container;
	private PromoveApplication app;
	
	public VeiculoTable(PromoveApplication app) {
		this.app = app;
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
		
		addGeneratedColumn("modelo", new VeiculoTableColumnGenerator(this));
		addGeneratedColumn("cor", new VeiculoTableColumnGenerator(this));
		addGeneratedColumn("avarias", new VeiculoTableColumnGenerator(this));
	}

	
	public BeanItemContainer<Veiculo> getContainer() {
		if(container == null)
			container = new VeiculoTableContainer();
		return container;
	}
	
	class VeiculoTableContainer extends BeanItemContainer<Veiculo> implements Serializable {
		
		public VeiculoTableContainer(){
			super(Veiculo.class);
			populate();
		}
		
		private void populate() {
			try {
				List<Veiculo> list = cadastroService.buscarTodosVeiculos();
				for (Veiculo mo : list) {
					addItem(mo);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	class VeiculoTableColumnGenerator implements Table.ColumnGenerator{

		private VeiculoTable table;

		public VeiculoTableColumnGenerator(VeiculoTable table) {
			this.table = table;
		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Veiculo v = (Veiculo)itemId;
			if(columnId.toString().equals("modelo")) {
				return new Label(v.getModelo().getDescricao());
			}else if(columnId.toString().equals("cor")) {
					return new Label(v.getCor().getDescricao());
			}else if(columnId.toString().equals("avarias")) {
				Button b = new Button(Integer.toString(v.getAvarias().size()));	
				b.setStyleName(BaseTheme.BUTTON_LINK);
				b.addListener(new LinkListener(table));
				return b;
					
			}else {
				return null;
			}
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<Veiculo> item =  (BeanItem<Veiculo>) getItem(getValue());
		}
	}
	
	class LinkListener implements ClickListener {

		private VeiculoTable table;

		public LinkListener(VeiculoTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			Window w = new Window("Avarias");
			w.setHeight("400px");
			w.setWidth("500px");
			w.setPositionY(50);
			w.setPositionX(300);
			app.getMainWindow().addWindow(w);
			w.setContent(buildComponents());

		}

		private ComponentContainer buildComponents() {
			CssLayout right = new CssLayout();
			right.setSizeUndefined();
			right.addStyleName("right");
			return right;
		}

	}
}

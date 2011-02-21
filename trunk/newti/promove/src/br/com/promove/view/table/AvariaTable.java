package br.com.promove.view.table;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AvariaSearchView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

public class AvariaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"veiculo", "tipo", "local", "origem", "extensao", "clima", "dataLancamento", "fotos", "usuario", "observacao"};
	public static final String[] COL_HEADERS = new String[] {"Veículo", "Tipo", "Local", "Origem", "Extensão", "Clima", "Data", "Foto", "Usuário", "Obs"};
	
	private AvariaService avariaService;
	private AvariaTableContainer container;
	private PromoveApplication app;
	private AvariaSearchView view;
	
	public AvariaTable(PromoveApplication app) {
		this.app = app;
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
		
//		addGeneratedColumn("veiculo", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("tipo", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("local", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("origem", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("extensao", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("clima", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("dataLancamento", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("usuario", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("observacao", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("foto", new AvariaTableColumnGenerator(this));
	}

	
	public BeanItemContainer<Avaria> getContainer() {
		if(container == null)
			container = new AvariaTableContainer();
		return container;
	}
	
	public void filterTable(List<Avaria> avarias) {
		container.populate(avarias);
	}

	class AvariaTableContainer extends BeanItemContainer<Avaria> implements
			Serializable {

		public AvariaTableContainer() {
			super(Avaria.class);
		}

		private void populate(List<Avaria> avarias) {
			for (Avaria mo : avarias) {
				addItem(mo);
			}
		}

	}

	class AvariaTableColumnGenerator implements Table.ColumnGenerator{

		private AvariaTable table;

		public AvariaTableColumnGenerator(AvariaTable table) {
			this.table = table;
		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Avaria av = (Avaria)itemId;
			if(columnId.toString().equals("dataLancamento")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(av.getDataLancamento()));
			}else if(columnId.toString().equals("observacao")) {
					if(av.getObservacao().length() > 5)
						return new Label(av.getObservacao().substring(0, 5) + "...");
					else
						return new Label(av.getObservacao());
			}else if(columnId.toString().equals("fotos")) { 
				return new Label("fotus");
			}else {
				return null;
			}
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<Avaria> item =  (BeanItem<Avaria>) getItem(getValue());
		}
	}
	
	class LinkListener implements ClickListener {

		private AvariaTable table;

		public LinkListener(AvariaTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			Window w = new Window("Avarias");
			w.setHeight("400px");
			w.setWidth("500px");
			w.setPositionY(table.getWindow().getPositionY() + 30);
			w.setPositionX(360);
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

	public void setView(AvariaSearchView view) {
		this.view = view;
		
	}
}

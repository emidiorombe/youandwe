package br.com.promove.view.table;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.menu.MenuAvaria;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AvariaSearchView;
import br.com.promove.view.VeiculoAvariaTables;
import br.com.promove.view.VeiculoListView;
import br.com.promove.view.form.AvariaSearchForm;
import br.com.promove.view.form.VeiculoForm;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class VeiculoTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "chassi", "modelo", "cor", "dataCadastro"};
	public static final String[] COL_HEADERS = new String[] {"ID", "Chassi", "Modelo", "Cor", "Data"};
	
	private CadastroService cadastroService;
	private VeiculoTableContainer container;
	private PromoveApplication app;
	private VeiculoAvariaTables view;
	private MenuAvaria menuAvaria;
	private AvariaService avariaService;
	
	public VeiculoTable(PromoveApplication app, MenuAvaria menuAvaria) {
		this.app = app;
		this.menuAvaria = menuAvaria;
		cadastroService = ServiceFactory.getService(CadastroService.class);
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
		
		addGeneratedColumn("id", new VeiculoTableColumnGenerator(this));
		addGeneratedColumn("modelo", new VeiculoTableColumnGenerator(this));
		addGeneratedColumn("cor", new VeiculoTableColumnGenerator(this));
		addGeneratedColumn("dataCadastro", new VeiculoTableColumnGenerator(this));
		//addGeneratedColumn("avarias", new VeiculoTableColumnGenerator(this));
	}

	
	public BeanItemContainer<Veiculo> getContainer() {
		if(container == null)
			container = new VeiculoTableContainer();
		return container;
	}
	
	public void filterTable(List<Veiculo> veiculos) {
		container.populate(veiculos);
	}
	
	public void setView(VeiculoAvariaTables view) {
		this.view = view;
		
	}
	
	public VeiculoAvariaTables getView() {
		return view;
	}
	
	class VeiculoTableContainer extends BeanItemContainer<Veiculo> implements
			Serializable {

		public VeiculoTableContainer() {
			super(Veiculo.class);
		}

		private void populate(List<Veiculo> veiculos) {
			removeAllItems();
			for (Veiculo mo : veiculos) {
				addItem(mo);
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
				Button b = new Button("Ver");	
				b.setStyleName(BaseTheme.BUTTON_LINK);
				b.addListener(new LinkListener(table));
				b.setDebugId("av&"+v.getChassi());
				return b;
					
			}else if(columnId.toString().equals("dataCadastro")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getDataCadastro()));
			}else if(columnId.toString().equals("id")) {
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
				
				if(user.getTipo().getId() == 1 || user.getTipo().getId() == 2) {
					Button b = new Button(v.getId().toString());	
					b.setStyleName(BaseTheme.BUTTON_LINK);
					b.addListener(new LinkListener(table));
					b.setDebugId("ch"+v.getChassi());
					return b;
				}else {
					return new Label(v.getId().toString());
				}
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
			view.getTableAvaria().filterTable(item.getBean().getChassi());
		}
	}
	
	class LinkListener implements ClickListener {

		private VeiculoTable table;

		public LinkListener(VeiculoTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			String debug = event.getButton().getDebugId();
			if(debug.startsWith("av")) {
				//AvariaSearchForm form = new AvariaSearchForm(app);
				//AvariaTable table  = new AvariaTable(app);
				//app.setMainView(new AvariaSearchView(table, form));
				//table.filterTable(debug.substring(debug.indexOf("&") + 1));
				view.getTableAvaria().filterTable(debug.substring(debug.indexOf("&") + 1));
			}else if(event.getButton().getDebugId().startsWith("ch")) {
				try {
					VeiculoForm form = new VeiculoForm();
					Veiculo veic = cadastroService.getById(Veiculo.class, new Integer(event.getButton().getCaption()));
					app.setMainView(form.getFormLayout());
					form.createFormBody(new BeanItem<Veiculo>(veic));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		private ComponentContainer buildComponents() {
			CssLayout right = new CssLayout();
			right.setSizeUndefined();
			right.addStyleName("right");
			return right;
		}

	}
}

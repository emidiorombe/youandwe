package br.com.promove.view.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CtrcVeiculoTables;
import br.com.promove.view.form.VeiculoCtrcForm;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class VeiculoCtrcTable extends Table {
public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "veiculo", "modelo", "cor", "dataCadastro", "tipo", "navio", "numeroNF", "serieNF", "dataNF", "valorMercadoria"};
	public static final String[] COL_HEADERS = new String[] {"ID", "Chassi", "Modelo", "Cor", "Data", "Tipo", "Navio", "NF", "Série", "Data NF", "Valor Merc."};
	
	private CtrcService ctrcService;
	private VeiculoCtrcTableContainer container;
	private PromoveApplication app;
	private CtrcVeiculoTables view;
	private NumberFormat formatMoeda = new DecimalFormat("'R$' #0.00");
	
	public VeiculoCtrcTable(PromoveApplication app) {
		this.app = app;
		ctrcService = ServiceFactory.getService(CtrcService.class);
		buildTable();
	}
	
	private void buildTable() {
		setSizeFull();
		setColumnCollapsingAllowed(true);	
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setContainerDataSource(getContainer());
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("id", new VeiculoCtrcTableColumnGenerator(this));
		addGeneratedColumn("modelo", new VeiculoCtrcTableColumnGenerator(this));
		addGeneratedColumn("cor", new VeiculoCtrcTableColumnGenerator(this));
		addGeneratedColumn("dataCadastro", new VeiculoCtrcTableColumnGenerator(this));
		addGeneratedColumn("tipo", new VeiculoCtrcTableColumnGenerator(this));
		addGeneratedColumn("navio", new VeiculoCtrcTableColumnGenerator(this));
		
		setVisibleColumns(NATURAL_COL_ORDER);
		setColumnHeaders(COL_HEADERS);

		setColumnAlignment("valorMercadoria", ALIGN_RIGHT);
		
		try {
			setColumnCollapsed("cor", true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public BeanItemContainer<VeiculoCtrc> getContainer() {
		if(container == null)
			container = new VeiculoCtrcTableContainer();
		return container;
	}
	
	public void filterTable(List<VeiculoCtrc> veiculos) {
		container.populate(veiculos);
	}
	
	public void filterTable(Ctrc ctrc) {
		try {
			filterTable(ctrcService.buscarVeiculosPorCtrc(ctrc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setView(CtrcVeiculoTables view) {
		this.view = view;
	}
	
	public CtrcVeiculoTables getView() {
		return view;
	}
	
	class VeiculoCtrcTableContainer extends BeanItemContainer<VeiculoCtrc> implements Serializable {

		public VeiculoCtrcTableContainer() {
			super(VeiculoCtrc.class);
		}

		private void populate(List<VeiculoCtrc> veiculos) {
			removeAllItems();
			for (VeiculoCtrc ve : veiculos) {
				addItem(ve);
			}
		}
	}
	
	class VeiculoCtrcTableColumnGenerator implements Table.ColumnGenerator {

		private VeiculoCtrcTable table;

		public VeiculoCtrcTableColumnGenerator(VeiculoCtrcTable table) {
			this.table = table;
		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			VeiculoCtrc v = (VeiculoCtrc)itemId;
			if(columnId.toString().equals("modelo")) {
				return new Label(v.getVeiculo().getModelo().getDescricao());
			}else if(columnId.toString().equals("cor")) {
					return new Label(v.getVeiculo().getCor().getDescricao());
			}else if(columnId.equals("tipo")) {
				return v.getVeiculo().getTipo() == 1 ? new Label("Nacional") : new Label("Importado");
			}else if(columnId.toString().equals("dataCadastro")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getVeiculo().getDataCadastro()));
			}else if(columnId.toString().equals("navio")) {
					return new Label(v.getVeiculo().getNavio());
			}else if(columnId.toString().equals("dataNF")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getDataNF()));
			}else if(columnId.toString().equals("valorMercadoria")) {
				double valor = v.getValorMercadoria() == null ? 0.0 : v.getValorMercadoria();
				return new Label(formatMoeda.format(valor));
			}else if(columnId.toString().equals("id")) {
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
				
				if(user.getTipo().getId() == 1 || user.getTipo().getId() == 2) {
					Button b = new Button(v.getId().toString());	
					b.setStyleName(BaseTheme.BUTTON_LINK);
					b.addListener(new LinkListener(table));
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
			BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItem(getValue());
		}
	}
	
	class LinkListener implements ClickListener {

		private VeiculoCtrcTable table;

		public LinkListener(VeiculoCtrcTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				VeiculoCtrcForm form = new VeiculoCtrcForm();
				VeiculoCtrc veic = ctrcService.getById(VeiculoCtrc.class, new Integer(event.getButton().getCaption()));
				app.setMainView(form.getFormLayout());
				form.createFormBody(new BeanItem<VeiculoCtrc>(veic));
			}catch(Exception e) {
				e.printStackTrace();
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

package br.com.promove.view.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CtrcVeiculoTables;
import br.com.promove.view.form.CtrcForm;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
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

public class CtrcTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "filial", "numero", "tipo", "serie", "transp", "dataEmissao", "placaFrota", "placaCarreta", "motorista", "ufOrigem", "municipioOrigem", "ufDestino", "municipioDestino", "valorMercadoria", "taxaRct", "taxaRr", "taxaRcf", "taxaFluvial", "cancelado"};
	public static final String[] COL_HEADERS = new String[] {"ID", "Filial", "Numero", "Tipo", "Série", "Transportadora", "Data Emis.", "Frota", "Carreta", "Motorista", "UF", "Mun. Origem", "UF", "Mun. Destino", "Valor Mercadoria", "RCT", "RR", "RCF", "Fluvial", "Cancelado"};
	
	private CtrcService ctrcService;
	private CtrcTableContainer container;
	private PromoveApplication app;
	private CtrcVeiculoTables view;
	private NumberFormat formatMoeda = new DecimalFormat("'R$' #0.00");
	private NumberFormat formatPercentual = new DecimalFormat("#0.00'%'");
	
	public CtrcTable(PromoveApplication app) {
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
		setVisibleColumns(NATURAL_COL_ORDER);
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("id", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("transp", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("dataEmissao", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("taxaRct", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("taxaRr", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("taxaRcf", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("taxaFluvial", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("valorMercadoria", new CtrcTableColumnGenerator(this));
		//addGeneratedColumn("premio", new CtrcTableColumnGenerator(this));

		setColumnHeaders(COL_HEADERS);

		setColumnAlignment("taxaRct", ALIGN_RIGHT);
		setColumnAlignment("taxaRr", ALIGN_RIGHT);
		setColumnAlignment("taxaRcf", ALIGN_RIGHT);
		setColumnAlignment("taxaFluvial", ALIGN_RIGHT);
		setColumnAlignment("valorMercadoria", ALIGN_RIGHT);
		//setColumnAlignment("premio", ALIGN_RIGHT);

		try {
			setColumnCollapsed("tipo", true);
			setColumnCollapsed("serie", true);
			setColumnCollapsed("transp", true);
			setColumnCollapsed("placaFrota", true);
			setColumnCollapsed("placaCarreta", true);
			//setColumnCollapsed("motorista", true);
			setColumnCollapsed("ufOrigem", true);
			//setColumnCollapsed("municipioOrigem", true);
			setColumnCollapsed("ufDestino", true);
			//setColumnCollapsed("municipioDestino", true);
			setColumnCollapsed("taxaRct", true);
			setColumnCollapsed("taxaRr", true);
			setColumnCollapsed("taxaRcf", true);
			setColumnCollapsed("taxaFluvial", true);
			setColumnCollapsed("cancelado", true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

        setFooterVisible(true);
        setColumnFooter("numero", "Total");
        setColumnFooter("valorMercadoria", "");
		
	}

	
	public BeanItemContainer<Ctrc> getContainer() {
		if(container == null)
			container = new CtrcTableContainer();
		return container;
	}
	
	public void filterTable(List<Ctrc> ctrcs, Double desconto) {
		container.populate(ctrcs, desconto);
	}
	
	public void setView(CtrcVeiculoTables view) {
		this.view = view;
		
	}
	
	public CtrcVeiculoTables getView() {
		return view;
	}
	
	class CtrcTableContainer extends BeanItemContainer<Ctrc> implements
			Serializable {

		public CtrcTableContainer() {
			super(Ctrc.class);
		}

		private void populate(List<Ctrc> ctrcs, Double desconto) {
			//Double premio = 0.0;
			Double valorMercadoria = 0.0;
			removeAllItems();
			//try {
			//	finalize();
			//} catch (Throwable e) {
			//	e.printStackTrace();
			//}
			
			for (Ctrc c : ctrcs) {
				addItem(c);
				//premio += c.getValorMercadoria() * c.getTaxas() * .01;
				if(c.getValorMercadoria() != null) valorMercadoria += c.getValorMercadoria();
			}
			setColumnFooter("valorMercadoria", formatMoeda.format(valorMercadoria));
			/*
			if (desconto != 0) {
				Ctrc c = new Ctrc();
				c.setId(-1); // Prêmio
				c.setValorMercadoria(premio);
				addItem(c);
				c = new Ctrc();
				c.setId(-2); // Desconto
				c.setValorMercadoria(premio * desconto * .01);
				addItem(c);
				c = new Ctrc();
				c.setId(-3); // Prêmio Líquido
				c.setValorMercadoria(premio * (100 - desconto) * .01);
				addItem(c);
			}
			*/
		}

	}
	
	class CtrcTableColumnGenerator implements Table.ColumnGenerator{

		private CtrcTable table;

		public CtrcTableColumnGenerator(CtrcTable table) {
			this.table = table;
		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Ctrc c = (Ctrc)itemId;
			if(columnId.toString().equals("transp")) {
				return new Label(c.getTransp().getDescricao());
			}else if(columnId.toString().equals("dataEmissao")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(c.getDataEmissao()));
			/*
			}else if(columnId.toString().equals("premio")) {
				return new Label(formatMoeda.format(c.getValorMercadoria() * c.getTaxas() * .01));
			*/
			}else if(columnId.toString().equals("taxaRct") || columnId.toString().equals("taxaRcf") || columnId.toString().equals("taxaRr") || columnId.toString().equals("taxaFluvial")) {
				double valor = 0.0;
				if(columnId.toString().equals("taxaRct")) {
					valor = c.getTaxaRct() == null ? 0.0 : c.getTaxaRct();
				} else if(columnId.toString().equals("taxaRr")) { 
					valor = c.getTaxaRr() == null ? 0.0 : c.getTaxaRr();
				} else if(columnId.toString().equals("taxaRcf")) {
					valor = c.getTaxaRcf() == null ? 0.0 : c.getTaxaRcf();
				} else {
					valor = c.getTaxaFluvial() == null ? 0.0 : c.getTaxaFluvial();
				}
				return new Label(formatPercentual.format(valor));
			}else if(columnId.toString().equals("valorMercadoria")) {
				double valor = c.getValorMercadoria() == null ? 0.0 : c.getValorMercadoria();
				return new Label(formatMoeda.format(valor));
			}else if(columnId.toString().equals("id")) {
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
				
				if(user.getTipo().getId() == 1 || user.getTipo().getId() == 7) {
					Button b = new Button(c.getId().toString());	
					b.setStyleName(BaseTheme.BUTTON_LINK);
					b.addListener(new LinkListener(table));
					b.setDebugId("id"+c.getId());
					return b;
				}else {
					return new Label(c.getId().toString());
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
			BeanItem<Ctrc> item = (BeanItem<Ctrc>) getItem(getValue());
			view.getTableVeiculo().filterTable(item.getBean());
		}
	}
	
	class LinkListener implements ClickListener {
		private CtrcTable table;

		public LinkListener(CtrcTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			String debug = event.getButton().getDebugId();
			if(event.getButton().getDebugId().startsWith("id")) {
				try {
					CtrcForm form = new CtrcForm();
					Ctrc ctrc = ctrcService.getById(Ctrc.class, new Integer(event.getButton().getCaption()));
					app.setMainView(form.getFormLayout());
					form.createFormBody(new BeanItem<Ctrc>(ctrc));
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

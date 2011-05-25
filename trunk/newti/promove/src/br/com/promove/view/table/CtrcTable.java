package br.com.promove.view.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.Usuario;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CtrcView;
import br.com.promove.view.form.CtrcForm;
import br.com.promove.view.table.ErroImportCtrcTable.ErroImportVeiculoColumnGenerator;

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
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "filial", "numero", "tipo", "serie", "transp", "dataEmissao", "placaFrota", "placaCarreta", "ufOrigem", "municipioOrigem", "ufDestino", "municipioDestino", "valorMercadoria", "taxaRct", "taxaRr", "taxaRcf", "taxaFluvial"};
	public static final String[] COL_HEADERS = new String[] {"ID", "Filial", "Numero", "Tipo", "Série", "Transportadora", "Data Emis.", "Frota", "Carreta", "UF", "Origem", "UF", "Destino", "Valor Mercadoria", "RCT", "RR", "RCF", "Fluvial", "Prêmio"};
	
	private CtrcService ctrcService;
	private CtrcTableContainer container;
	private PromoveApplication app;
	private CtrcView view;
	
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
		addGeneratedColumn("premio", new CtrcTableColumnGenerator(this));

		setColumnHeaders(COL_HEADERS);

		setColumnAlignment("taxaRct", ALIGN_RIGHT);
		setColumnAlignment("taxaRr", ALIGN_RIGHT);
		setColumnAlignment("taxaRcf", ALIGN_RIGHT);
		setColumnAlignment("taxaFluvial", ALIGN_RIGHT);
		setColumnAlignment("valorMercadoria", ALIGN_RIGHT);
		setColumnAlignment("premio", ALIGN_RIGHT);

		try {
			setColumnCollapsed("tipo", true);
			setColumnCollapsed("serie", true);
			setColumnCollapsed("transp", true);
			//setColumnCollapsed("placaFrota", true);
			//setColumnCollapsed("placaCarreta", true);
			//setColumnCollapsed("ufOrigem", true);
			//setColumnCollapsed("municipioOrigem", true);
			//setColumnCollapsed("ufDestino", true);
			//setColumnCollapsed("municipioDestino", true);
			setColumnCollapsed("taxaRr", true);
			setColumnCollapsed("taxaRcf", true);
			setColumnCollapsed("taxaFluvial", true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
}

	
	public BeanItemContainer<Ctrc> getContainer() {
		if(container == null)
			container = new CtrcTableContainer();
		return container;
	}
	
	public void filterTable(List<Ctrc> ctrcs, Double desconto) {
		container.populate(ctrcs, desconto);
	}
	
	public void setView(CtrcView view) {
		this.view = view;
		
	}
	
	public CtrcView getView() {
		return view;
	}
	
	class CtrcTableContainer extends BeanItemContainer<Ctrc> implements
			Serializable {

		public CtrcTableContainer() {
			super(Ctrc.class);
		}

		private void populate(List<Ctrc> ctrcs, Double desconto) {
			Double premio = new Double(0);
			removeAllItems();
			try {
				finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			for (Ctrc c : ctrcs) {
				addItem(c);
				premio += c.getValorMercadoria() * c.getTaxas() * .01;
			}
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
				if (c.getId() > 0) {
					return new Label(new SimpleDateFormat("dd/MM/yyyy").format(c.getDataEmissao()));
				} else {
					return null;
				}
			}else if(columnId.toString().equals("premio")) {
				NumberFormat formatter = new DecimalFormat("'R$' #0.00");
				if (c.getId() > 0) {
					return new Label(formatter.format(c.getValorMercadoria() * c.getTaxas() * .01));
				} else {
					return new Label(formatter.format(c.getValorMercadoria()));
				}
			}else if(columnId.toString().equals("taxaRct") || columnId.toString().equals("taxaRcf") || columnId.toString().equals("taxaRr") || columnId.toString().equals("taxaFluvial")) {
				if (c.getId() > 0) {
					NumberFormat formatter = new DecimalFormat("#0.00'%'");
					if(columnId.toString().equals("taxaRct")) {
						return new Label(formatter.format(c.getTaxaRct()));
					} else if(columnId.toString().equals("taxaRr")) { 
						return new Label(formatter.format(c.getTaxaRr()));
					} else if(columnId.toString().equals("taxaRcf")) {
						return new Label(formatter.format(c.getTaxaRcf()));
					} else {
						return new Label(formatter.format(c.getTaxaFluvial()));
					}
				} else {
					return null;
				}
			}else if(columnId.toString().equals("valorMercadoria")) {
				if (c.getId() > 0) {
					NumberFormat formatter = new DecimalFormat("'R$' #0.00");
					return new Label(formatter.format(c.getValorMercadoria()));
				} else if (c.getId() == -1) {
					return new Label("Total");
				} else if (c.getId() == -2) {
					return new Label("Desconto");
				} else if (c.getId() == -3) {
					return new Label("Líquido");
				} else {
					return null;
				}
			}else if(columnId.toString().equals("id")) {
				if (c.getId() > 0) {
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
				} else {
					return null;
				}
			}else {
				return null;
			}
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
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

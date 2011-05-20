package br.com.promove.view.table;

import java.io.Serializable;
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
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "filial", "numero", "tipo", "serie", "transp", "dataEmissao", "placaFrota", "placaCarreta", "ufOrigem", "municipioOrigem", "ufDestino", "municipioDestino", "taxaRct", "taxaRr", "taxaRcf", "taxaFluvial", "valorMercadoria"};
	public static final String[] COL_HEADERS = new String[] {"ID", "Filial", "Numero", "Tipo", "Série", "Transportadora", "Data", "Frota", "Carreta", "UF", "Origem", "UF", "Destino", "RCT", "RR", "RCF", "Fluvial", "Valor Mercadoria"};
	
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
		setColumnHeaders(COL_HEADERS);
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("id", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("transp", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("dataEmissao", new CtrcTableColumnGenerator(this));
		addGeneratedColumn("premio", new CtrcTableColumnGenerator(this));
		
		try {
			setColumnCollapsed("transp", true);
			setColumnCollapsed("placaFrota", true);
			setColumnCollapsed("placaCarreta", true);
			setColumnCollapsed("ufOrigem", true);
			setColumnCollapsed("municipioOrigem", true);
			setColumnCollapsed("ufDestino", true);
			setColumnCollapsed("municipioDestino", true);
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
	
	public void filterTable(List<Ctrc> ctrcs) {
		container.populate(ctrcs);
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

		private void populate(List<Ctrc> ctrcs) {
			removeAllItems();
			for (Ctrc c : ctrcs) {
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
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(c.getDataEmissao()));
			}else if(columnId.toString().equals("premio")) {
				return new Label(String.valueOf(c.getValorMercadoria() * c.getTaxas()));
			}else if(columnId.toString().equals("id")) {
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
				
				if(user.getTipo().getId() == 1 || user.getTipo().getId() == 7) {
					Button b = new Button(c.getId().toString());	
					b.setStyleName(BaseTheme.BUTTON_LINK);
					b.addListener(new LinkListener(table));
					b.setDebugId("nr"+c.getNumero());
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
			if(event.getButton().getDebugId().startsWith("nr")) {
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

package br.com.promove.view.table;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AvariaSearchView;
import br.com.promove.view.form.AvariaForm;

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

public class AvariaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id", "veiculo", "modelo", "tipo", "local", "origem", "extensao", "clima", "dataLancamento","hora", "fotos", "usuario", "observacao"};
	public static final String[] COL_HEADERS = new String[] {"ID","Veículo", "Modelo", "Tipo", "Local", "Origem", "Extensão", "Clima", "Data","Hora", "Fotos", "Usuário", "Obs"};
	
	private AvariaService avariaService;
	private AvariaTableContainer container;
	private PromoveApplication app;
	private AvariaSearchView view;
	
	public AvariaTable(PromoveApplication app) {
		this.app = app;
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildTable();
	}

	public AvariaTable(PromoveApplication app, Veiculo veiculo) {
		this(app);
		filterTable(veiculo.getAvarias());
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
		addGeneratedColumn("modelo", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("dataLancamento", new AvariaTableColumnGenerator(this));
//		addGeneratedColumn("usuario", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("observacao", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("fotos", new AvariaTableColumnGenerator(this));
		addGeneratedColumn("id", new AvariaTableColumnGenerator(this));
	}

	
	public BeanItemContainer<Avaria> getContainer() {
		if(container == null)
			container = new AvariaTableContainer();
		return container;
	}
	
	public void filterTable(List<Avaria> avarias) {
		container.populate(avarias);
	}
	
	public void filterTable(String chassi) {
		try {
			filterTable(avariaService.buscarAvariaPorFiltros(chassi, null, null, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	class AvariaTableContainer extends BeanItemContainer<Avaria> implements
			Serializable {

		public AvariaTableContainer() {
			super(Avaria.class);
		}

		private void populate(List<Avaria> avarias) {
			container.removeAllItems();
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
					if(av.getObservacao() != null && av.getObservacao().length() > 5)
						return new Label(av.getObservacao().substring(0, 5) + "...");
					else
						return new Label(av.getObservacao());
			}else if(columnId.toString().equals("fotos")) { 
				return new Label(Integer.toString(av.getFotos().size()));
			}else if(columnId.toString().equals("modelo")) { 
				return new Label(av.getVeiculo().getModelo().getDescricao());
			}else if(columnId.toString().equals("id")) {
				
				WebApplicationContext ctx = (WebApplicationContext) app.getContext();
				Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
				
				if(user.getTipo().getId() == 1) {
					Button b = new Button(av.getId().toString());	
					b.setStyleName(BaseTheme.BUTTON_LINK);
					b.addListener(new IdLinkListener(table));
					return b;
				}else {
					Label lbl = new Label(av.getId().toString());
					return lbl;
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
			BeanItem<Avaria> item =  (BeanItem<Avaria>) getItem(getValue());
		}
	}
	
	class IdLinkListener implements ClickListener {

		private AvariaTable table;

		public IdLinkListener(AvariaTable table) {
			this.table = table;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				Avaria av = avariaService.getById(Avaria.class, new Integer(event.getButton().getCaption()));
				AvariaForm form = new AvariaForm(app);
				app.setMainView(form.getFormLayout());
				form.createFormBody(new BeanItem<Avaria>(av));
			}catch(PromoveException pe) {
				pe.printStackTrace();
			}

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

package br.com.promove.view.form;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AuditoriaVistoriasView;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.Filtering;

public class AuditoriaVistoriasForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private AuditoriaVistoriasView view;
	private Button search;
	private Button export;
	private ComboBox cmbOrigemDe;
	private ComboBox cmbOrigemAte;
	private ComboBox cmbPeriodo;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private PromoveApplication app;
	
	public AuditoriaVistoriasForm(PromoveApplication app) {
		this.app = app;
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}

	private void buildForm() {
		WebApplicationContext ctx = (WebApplicationContext) app.getContext();
		Usuario user = (Usuario) ctx.getHttpSession().getAttribute("loggedUser");
		
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		Item i;
		
		search = new Button("Buscar", new AuditoriaVistoriasListener());
		export = new Button("Gerar Arquivo", new AuditoriaVistoriasListener());
		
		cmbOrigemDe = new ComboBox("Local de Vistoria De");
		cmbOrigemDe.addContainerProperty("label", String.class, null);
		
		try {
			//i = cmbOrigemDe.addItem(new OrigemAvaria());
			//i.getItemProperty("label").setValue("Selecione...");
			for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
				if (user.getTipo().getId() != 9 || or.getFilial().getCodigo().equals(user.getFilial().getCodigo())) { // Consulta por Local
					i = cmbOrigemDe.addItem(or);
					i.getItemProperty("label").setValue(or.getDescricao());
				}
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar as Origens");
		}

		cmbOrigemDe.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbOrigemDe.setImmediate(true);
		cmbOrigemDe.setNullSelectionAllowed(false);
		cmbOrigemDe.setItemCaptionPropertyId("label");
		cmbOrigemDe.setWidth("250px");
		cmbOrigemDe.setValue(cmbOrigemDe.getItemIds().iterator().next());
		
		cmbOrigemAte = new ComboBox("Local de Vistoria Até");
		cmbOrigemAte.addContainerProperty("label", String.class, null);
		
		try {
			//i = cmbOrigemAte.addItem(new OrigemAvaria());
			//i.getItemProperty("label").setValue("Selecione...");
			for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
				if (user.getTipo().getId() != 9 || or.getFilial().getCodigo().equals(user.getFilial().getCodigo())) { // Consulta por Local
					i = cmbOrigemAte.addItem(or);
					i.getItemProperty("label").setValue(or.getDescricao());
					if (!or.getTipo().equals("3")) cmbOrigemAte.setValue(or);
				}
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar as Origens");
		}

		cmbOrigemAte.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbOrigemAte.setImmediate(true);
		cmbOrigemAte.setNullSelectionAllowed(false);
		cmbOrigemAte.setItemCaptionPropertyId("label");
		cmbOrigemAte.setWidth("250px");
		//cmbOrigemAte.setValue(cmbOrigemAte.getItemIds().iterator().next());
		
		txtDe = new PopupDateField("De");
		txtDe.setLocale(new Locale("pt", "BR"));
		txtDe.setResolution(DateField.RESOLUTION_DAY);
		
		txtAte = new PopupDateField("Até");
		txtAte.setLocale(new Locale("pt", "BR"));
		txtAte.setResolution(DateField.RESOLUTION_DAY);
		
		cmbPeriodo = new ComboBox("Período por");
		cmbPeriodo.addContainerProperty("label", String.class, null);
		
		i = cmbPeriodo.addItem(3);
		i.getItemProperty("label").setValue("Data do veículo");
		i = cmbPeriodo.addItem(4);
		i.getItemProperty("label").setValue("Data de registro do veículo");
		
		cmbPeriodo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbPeriodo.setImmediate(true);
		cmbPeriodo.setNullSelectionAllowed(false);
		cmbPeriodo.setItemCaptionPropertyId("label");
		cmbPeriodo.setWidth("200px");
		cmbPeriodo.setValue(cmbPeriodo.getItemIds().iterator().next());

		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
		layout.addComponent(this);
		addField("cmbOrigemDe", cmbOrigemDe);
		addField("cmbOrigemAte", cmbOrigemAte);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		addField("cmbPeriodo", cmbPeriodo);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}

	public void createFormBody(BeanItem<Veiculo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new AuditoriaFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"tipo", "navio"});
		
	}
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(search);
		footer.addComponent(export);
		footer.setVisible(true);
		
		return footer;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	public void setView(AuditoriaVistoriasView view) {
		this.view = view;
	}
	
	class AuditoriaFieldFactory extends DefaultFieldFactory{
		
		private AuditoriaVistoriasForm form;
		private boolean isNew;

		public AuditoriaFieldFactory(AuditoriaVistoriasForm form, boolean isNew) {
			this.form = form;
			this.isNew = isNew;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
			}
			
			if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo de Veículo");
				c.addContainerProperty("label", String.class, null);
				
				Item i_default = c.addItem(new TipoVeiculo());
				i_default.getItemProperty("label").setValue("Selecione...");
				
				try {
					for(TipoVeiculo tv: cadastroService.buscarTodosTiposVeiculos()){
						if (tv.getId() != 9) {
							Item i = c.addItem(tv);
							i.getItemProperty("label").setValue(tv.getNome());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				return c;
			}else if(propertyId.equals("navio")) {
				try {
					ComboBox c = new ComboBox("Navio");
					c.addContainerProperty("label", String.class, null);
				
					Item item_default = c.addItem(new String());
					item_default.getItemProperty("label").setValue("Selecione...");
					for(String s: cadastroService.buscarTodosNavios()) {
						Item i = c.addItem(s);
						i.getItemProperty("label").setValue(s);
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					c.setWidth("250px");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar os Navios");
				}
			}
			return f;
		}
	}
	
	class AuditoriaVistoriasListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				Integer periodo = (Integer)cmbPeriodo.getValue();
				OrigemAvaria oride = (OrigemAvaria)cmbOrigemDe.getValue();
				OrigemAvaria oriate = (OrigemAvaria)cmbOrigemAte.getValue();
				BeanItem<Veiculo> item = (BeanItem<Veiculo>)getItemDataSource();
				
				if(de == null || ate == null)
					if(item.getBean().getNavio().isEmpty())
						throw new IllegalArgumentException("Informe um navio ou período");
				
				List<Veiculo> veiculos = cadastroService.buscarVeiculosAuditoria(item.getBean(), de, ate, periodo, oride, oriate);
				
				if(event.getButton() == search) {
					view.getTables().getTableVeiculo().filterTable(veiculos);
					view.getTables().getTableAvaria().removeAllItems();
				}else if(event.getButton() == export) {
					String file = exportacaoService.exportarXLSVeiculos(veiculos);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=auditoria_vistorias.xls&file=" + file));
				}
			}catch(IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}catch (Exception e) {
				showErrorMessage(view, "Não foi possível buscar os veículos");
				e.printStackTrace();
			}
		}
	}
}

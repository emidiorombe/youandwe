package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Cor;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ResumoAvariasView;
import br.com.promove.view.form.VeiculoSearchForm.VeiculoFieldFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
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

public class ResumoAvariasForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private ResumoAvariasView view;
	private Button search;
	private Button export;
	private ComboBox cmbOrigemDe;
	private ComboBox cmbOrigemAte;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private ComboBox cmbPeriodo;
	private ComboBox cmbItem;
	private ComboBox cmbSubitem;
	private PromoveApplication app;
	
	public ResumoAvariasForm(PromoveApplication app) {
		this.app = app;
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		Item i;
		
		search = new Button("Buscar", new ResumoAvariasListener());
		export = new Button("Gerar Arquivo", new ResumoAvariasListener());
		
		cmbOrigemDe = new ComboBox("Origem De");
		cmbOrigemDe.addContainerProperty("label", String.class, null);
		
		try {
			//i = cmbOrigemDe.addItem(new OrigemAvaria());
			//i.getItemProperty("label").setValue("Selecione...");
			for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
				i = cmbOrigemDe.addItem(or);
				i.getItemProperty("label").setValue(or.getDescricao());
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar as Origens de Avaria");
		}

		cmbOrigemDe.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbOrigemDe.setImmediate(true);
		cmbOrigemDe.setNullSelectionAllowed(false);
		cmbOrigemDe.setItemCaptionPropertyId("label");
		cmbOrigemDe.setWidth("250px");
		//cmbOrigemDe.setValue(cmbOrigemDe.getItemIds().iterator().next());
		
		cmbOrigemAte = new ComboBox("Origem Até");
		cmbOrigemAte.addContainerProperty("label", String.class, null);
		
		try {
			//i = cmbOrigemAte.addItem(new OrigemAvaria());
			//i.getItemProperty("label").setValue("Selecione...");
			for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
				i = cmbOrigemAte.addItem(or);
				i.getItemProperty("label").setValue(or.getDescricao());
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar as Origens de Avaria");
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
		
		i = cmbPeriodo.addItem(1);
		i.getItemProperty("label").setValue("Data de lançamento da avaria");
		i = cmbPeriodo.addItem(2);
		i.getItemProperty("label").setValue("Data de cadastro do veículo");
		
		cmbPeriodo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbPeriodo.setImmediate(true);
		cmbPeriodo.setNullSelectionAllowed(false);
		cmbPeriodo.setItemCaptionPropertyId("label");
		cmbPeriodo.setWidth("200px");
		cmbPeriodo.setValue(cmbPeriodo.getItemIds().iterator().next());

		cmbItem = new ComboBox("Resumo por");
		cmbItem.addContainerProperty("label", String.class, null);
		
		i = cmbItem.addItem(new String());
		i.getItemProperty("label").setValue("Selecione...");
		i = cmbItem.addItem("tipoavaria");
		i.getItemProperty("label").setValue("Tipo de avaria");
		i = cmbItem .addItem("localavaria");
		i.getItemProperty("label").setValue("Local de avaria");
		i = cmbItem .addItem("origemavaria");
		i.getItemProperty("label").setValue("Origem de avaria");
		i = cmbItem .addItem("modelo");
		i.getItemProperty("label").setValue("Modelo");
		i = cmbItem .addItem("fabricante");
		i.getItemProperty("label").setValue("Fabricante");
		
		cmbItem.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbItem.setImmediate(true);
		cmbItem.setNullSelectionAllowed(false);
		cmbItem.setItemCaptionPropertyId("label");
		cmbItem.setValue(cmbItem.getItemIds().iterator().next());

		cmbSubitem = new ComboBox("Sub-resumo por");
		cmbSubitem.addContainerProperty("label", String.class, null);
		
		i = cmbSubitem.addItem(new String());
		i.getItemProperty("label").setValue("Selecione...");
		i = cmbSubitem.addItem("tipoavaria");
		i.getItemProperty("label").setValue("Tipo de avaria");
		i = cmbSubitem .addItem("localavaria");
		i.getItemProperty("label").setValue("Local de avaria");
		i = cmbSubitem .addItem("origemavaria");
		i.getItemProperty("label").setValue("Origem de avaria");
		i = cmbSubitem .addItem("modelo");
		i.getItemProperty("label").setValue("Modelo");
		i = cmbSubitem .addItem("fabricante");
		i.getItemProperty("label").setValue("Fabricante");
		
		cmbSubitem.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbSubitem.setImmediate(true);
		cmbSubitem.setNullSelectionAllowed(false);
		cmbSubitem.setItemCaptionPropertyId("label");
		cmbSubitem.setValue(cmbSubitem.getItemIds().iterator().next());

		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
		layout.addComponent(this);
		addField("cmbOrigemDe", cmbOrigemDe);
		addField("cmbOrigemAte", cmbOrigemAte);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		addField("cmbPeriodo", cmbPeriodo);
		addField("cmbItem", cmbItem);
		addField("cmbSubItem", cmbSubitem);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}

	public void createFormBody(BeanItem<Veiculo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new AuditoriaFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"tipo"});
		
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

	public void setView(ResumoAvariasView view) {
		this.view = view;
	}
	
	class AuditoriaFieldFactory extends DefaultFieldFactory{
		
		private ResumoAvariasForm form;
		private boolean isNew;

		public AuditoriaFieldFactory(ResumoAvariasForm form, boolean isNew) {
			this.form = form;
			this.isNew = isNew;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo de Veículo");
				c.addContainerProperty("label", String.class, null);
				
				Item i = c.addItem(new Integer(0));
				i.getItemProperty("label").setValue("Selecione...");
				i = c.addItem(1);
				i.getItemProperty("label").setValue("Nacional");
				i = c.addItem(2);
				i.getItemProperty("label").setValue("Importado");
				
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				return c;
			}
			return f;
		}
	}
	
	class ResumoAvariasListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				OrigemAvaria oride = (OrigemAvaria)cmbOrigemDe.getValue();
				OrigemAvaria oriate = (OrigemAvaria)cmbOrigemAte.getValue();
				Integer periodo = (Integer)cmbPeriodo.getValue();
				String item = (String)cmbItem.getValue();
				String subitem = (String)cmbSubitem.getValue();
				BeanItem<Veiculo> veic = (BeanItem<Veiculo>)getItemDataSource();
				
				if(oride == null || oride.getId() == null || oriate == null || 
						oriate.getId() == null || de == null || ate == null)
					throw new IllegalArgumentException("Informe as origens e o período");
				
				List<Cor> cores = avariaService.buscarResumo(veic.getBean(), de, ate, periodo, oride, oriate, item, subitem);
				
				if(event.getButton() == search) {
					view.getTable().filterTable(cores);
				}else if(event.getButton() == export) {
					//String file = exportacaoService.exportarXLSVeiculos(veiculos);
					
					//WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					//String path = ctx.getHttpSession().getServletContext().getContextPath();
					//event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=auditoria.xls&file=" + file));
				}
			}catch(IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}catch (Exception e) {
				showErrorMessage(view, e.getMessage() + " Não foi possível apurar");
				e.printStackTrace();
			}
		}
	}
}

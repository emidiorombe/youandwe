package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.ResponsabilidadeAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AvariaSearchView;

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

public class AvariaSearchForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private AvariaSearchView view;
	private Button search;
	private Button export;
	private Button exportXml;
	//private ComboBox cmbOrigemDe;
	private ComboBox cmbOrigemAte;
	private ComboBox cmbResponsabilidade;
	private ComboBox cmbFabricante;
	private ComboBox cmbModelo;
	private ComboBox cmbTipo;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private CheckBox chkMovimentacao;
	private CheckBox chkRegistradas;
	private ComboBox cmbPeriodo;
	private PromoveApplication app;
	
	public AvariaSearchForm(PromoveApplication app) {
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
		
		search = new Button("Buscar", new AvariaSearchListener());
		export = new Button("Gerar Arquivo", new AvariaSearchListener());
		exportXml = new Button("Gerar XML", new AvariaSearchListener());
		
		/*
		cmbOrigemDe = new ComboBox("Origem De");
		cmbOrigemDe.addContainerProperty("label", String.class, null);
		
		try {
			i = cmbOrigemDe.addItem(new OrigemAvaria());
			i.getItemProperty("label").setValue("Selecione...");
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
		//cmbOrigemDe.setWidth("250px");
		cmbOrigemDe.setValue(cmbOrigemDe.getItemIds().iterator().next());
		*/
		
		cmbOrigemAte = new ComboBox("Origem (De/Até)");
		cmbOrigemAte.addContainerProperty("label", String.class, null);
		
		try {
			i = cmbOrigemAte.addItem(new OrigemAvaria());
			i.getItemProperty("label").setValue("Selecione...");
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
		//cmbOrigemAte.setWidth("250px");
		cmbOrigemAte.setValue(cmbOrigemAte.getItemIds().iterator().next());
		
		cmbResponsabilidade = new ComboBox("Responsabilidade");
		cmbResponsabilidade.addContainerProperty("label", String.class, null);
		
		try {
			i = cmbResponsabilidade.addItem(new ResponsabilidadeAvaria());
			i.getItemProperty("label").setValue("Selecione...");
			for(ResponsabilidadeAvaria res: avariaService.buscarTodasResponsabilidades()){
				i = cmbResponsabilidade.addItem(res);
				i.getItemProperty("label").setValue(res.getNome());
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar as Responsabilidades de Avaria");
		}

		cmbResponsabilidade.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbResponsabilidade.setImmediate(true);
		cmbResponsabilidade.setNullSelectionAllowed(false);
		cmbResponsabilidade.setItemCaptionPropertyId("label");
		//cmbResponsabilidade.setWidth("200px");
		cmbResponsabilidade.setValue(cmbResponsabilidade.getItemIds().iterator().next());
		
		cmbFabricante = new ComboBox("Fabricante");
		cmbFabricante.addContainerProperty("label", String.class, null);
		
		try {
			i = cmbFabricante.addItem(new Fabricante());
			i.getItemProperty("label").setValue("Selecione...");
			for(Fabricante fab: cadastroService.buscarTodosFabricantes()){
				i = cmbFabricante.addItem(fab);
				i.getItemProperty("label").setValue(fab.getNome());
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar os Fabricantes");
		}

		cmbFabricante.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbFabricante.setImmediate(true);
		cmbFabricante.setNullSelectionAllowed(false);
		cmbFabricante.setItemCaptionPropertyId("label");
		//cmbFabricante.setWidth("200px");
		cmbFabricante.setValue(cmbFabricante.getItemIds().iterator().next());
		
		cmbModelo = new ComboBox("Modelo");
		cmbModelo.addContainerProperty("label", String.class, null);
		
		try {
			i = cmbModelo.addItem(new Fabricante());
			i.getItemProperty("label").setValue("Selecione...");
			for(Modelo m: cadastroService.buscarTodosModelos()){
				i = cmbModelo.addItem(m);
				i.getItemProperty("label").setValue(m.getDescricao());
			}
		} catch (PromoveException e) {
			showErrorMessage(this, "Não foi possível buscar os Modelos");
		}

		cmbModelo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbModelo.setImmediate(true);
		cmbModelo.setNullSelectionAllowed(false);
		cmbModelo.setItemCaptionPropertyId("label");
		//cmbModelo.setWidth("200px");
		cmbModelo.setValue(cmbModelo.getItemIds().iterator().next());
		
		cmbTipo = new ComboBox("Tipo de Veículo");
		cmbTipo.addContainerProperty("label", String.class, null);
		
		i = cmbTipo.addItem(new Integer(0));
		i.getItemProperty("label").setValue("Selecione...");
		i = cmbTipo.addItem(1);
		i.getItemProperty("label").setValue("Nacional");
		i = cmbTipo.addItem(2);
		i.getItemProperty("label").setValue("Importado");

		cmbTipo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbTipo.setImmediate(true);
		cmbTipo.setNullSelectionAllowed(false);
		cmbTipo.setItemCaptionPropertyId("label");
		//cmbTipo.setWidth("200px");
		cmbTipo.setValue(cmbTipo.getItemIds().iterator().next());
		
		chkMovimentacao = new CheckBox();
		chkMovimentacao.setCaption("Desconsiderar movimentações sem avaria");
		
		chkRegistradas = new CheckBox();
		chkRegistradas.setCaption("Desconsiderar avarias previamente registradas");
		
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

		//createFormBodyVe(new BeanItem<Veiculo>(new Veiculo()));
		createFormBody(new BeanItem<Avaria>(new Avaria()));
		layout.addComponent(this);
		//addField("cmbOrigemAte", cmbOrigemAte);
		//addField("cmbResponsabilidade", cmbResponsabilidade);
		//addField("cmbFabricante", cmbFabricante);
		//addField("cmbModelo", cmbModelo);
		//addField("cmbTipo", cmbTipo);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		addField("cmbPeriodo", cmbPeriodo);
		addField("chkMovimentacao", chkMovimentacao);
		addField("chkRegistradas", chkRegistradas);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}

	/*
	private void createFormBodyVe(BeanItem<Veiculo> beanItem) {
		setItemDataSource(beanItem);
		setFormFieldFactory(new VeiculoFieldFactory(this, beanItem.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"fabricante", "modelo", "tipo"});
	}
	*/
	
	private void createFormBody(BeanItem<Avaria> beanItem) {
		setItemDataSource(beanItem);
		setFormFieldFactory(new AvariaFieldFactory(this, beanItem.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"veiculo", "tipo", "local", "origem"});
		
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(search);
		footer.addComponent(export);
		//footer.addComponent(exportXml);
		footer.setVisible(true);
		
		return footer;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	public void setView(AvariaSearchView view) {
		this.view = view;
	}
	
	class AvariaFieldFactory extends DefaultFieldFactory{
		
		private AvariaSearchForm avariaForm;
		private boolean isNew;

		public AvariaFieldFactory(AvariaSearchForm avariaForm, boolean isNew) {
			this.avariaForm = avariaForm;
			this.isNew = isNew;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			try {
				if(propertyId.equals("veiculo")) {
					TextField tf = new TextField("Chassi");
					tf.setNullRepresentation("");
					tf.setImmediate(true);
					tf.setWidth("200px");
					return tf;
				} else if(propertyId.equals("tipo")) {
					ComboBox c = new ComboBox("Tipo Avaria");
					c.addContainerProperty("label", String.class, null);
					
					Item i_default = c.addItem(new TipoAvaria());
					i_default.getItemProperty("label").setValue("Selecione...");
					
					
					for(TipoAvaria tp: avariaService.buscarTodosTipoAvaria()){
						Item i = c.addItem(tp);
						i.getItemProperty("label").setValue(tp.getDescricao());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					
					return c;
				}else if(propertyId.equals("local")) {
					ComboBox c = new ComboBox("Local Avaria");
					c.addContainerProperty("label", String.class, null);

					
					Item i_default = c.addItem(new LocalAvaria());
					i_default.getItemProperty("label").setValue("Selecione...");

					for(LocalAvaria l: avariaService.buscarTodosLocaisAvaria()){
						Item i = c.addItem(l);
						i.getItemProperty("label").setValue(l.getDescricao());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					
					return c;
				}else if(propertyId.equals("origem")) {
					ComboBox c = new ComboBox("Origem Avaria");
					c.addContainerProperty("label", String.class, null);

					Item i_default = c.addItem(new OrigemAvaria());
					i_default.getItemProperty("label").setValue("Selecione...");

					for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
						Item i = c.addItem(or);
						i.getItemProperty("label").setValue(or.getDescricao());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					
					return c;
				}
			}catch(PromoveException pe) {
				showErrorMessage(avariaForm, "Não foi possível montar o formulário de Avaria");
			}
			return null;
		}
	}
	
	class VeiculoFieldFactory extends DefaultFieldFactory{
		
		private AvariaSearchForm avariaForm;
		private boolean isNew;

		public VeiculoFieldFactory(AvariaSearchForm avariaForm, boolean isNew) {
			this.avariaForm = avariaForm;
			this.isNew = isNew;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			try {
				if(propertyId.equals("fabricante")) {
					ComboBox c = new ComboBox("Fabricante");
					c.addContainerProperty("label", String.class, null);
	
					
					Item i_default = c.addItem(new Fabricante());
					i_default.getItemProperty("label").setValue("Selecione...");
	
					for(Fabricante fab: cadastroService.buscarTodosFabricantes()){
						Item i = c.addItem(fab);
						i.getItemProperty("label").setValue(fab.getNome());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				} else if(propertyId.equals("modelo")) {
					ComboBox c = new ComboBox("Modelo");
					c.addContainerProperty("label", String.class, null);
					
					Item i_default = c.addItem(new Modelo());
					i_default.getItemProperty("label").setValue("Selecione...");
					
					
					for(Modelo m: cadastroService.buscarTodosModelos()){
						Item i = c.addItem(m);
						i.getItemProperty("label").setValue(m.getDescricao());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}else if(propertyId.equals("tipo")) {
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
			}catch(PromoveException pe) {
				showErrorMessage(avariaForm, "Não foi possível montar o formulário de Avaria");
			}
			return null;
		}
	}
	
	class AvariaSearchListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				//OrigemAvaria oriAte = (OrigemAvaria)cmbOrigemAte.getValue();
				//ResponsabilidadeAvaria res = (ResponsabilidadeAvaria)cmbResponsabilidade.getValue();
				//Fabricante fab = (Fabricante)cmbFabricante.getValue();
				//Modelo modelo = (Modelo)cmbModelo.getValue();
				//Integer tipo = (Integer)cmbTipo.getValue();
				
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				Integer periodo = (Integer)cmbPeriodo.getValue();
				Boolean movimentacao = (Boolean)chkMovimentacao.getValue();
				Boolean registradas = (Boolean)chkRegistradas.getValue();
				BeanItem<Avaria> item = (BeanItem<Avaria>)getItemDataSource();
				
				if(item.getBean().getVeiculo() == null || item.getBean().getVeiculo().getChassi().isEmpty()) {
					if(de == null || ate == null)
						throw new IllegalArgumentException("Informe um chassi ou período");
				}
				
				List<Avaria> avarias = avariaService.buscarAvariaPorFiltros(item.getBean(), de, ate, periodo, movimentacao, registradas);

				if(event.getButton() == search) {
					view.getTable().filterTable(avarias);
				} else if(event.getButton() == export) {
					String file = exportacaoService.exportarXLSAvarias(avarias);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=avarias.xls&file=" + file));
				} else if(event.getButton() == exportXml) {
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_avarias&fileName=avarias.xml&de=" + new SimpleDateFormat("dd/MM/yyyy").format(de) + "&ate=" + new SimpleDateFormat("dd/MM/yyyy").format(ate)));
				}
			}catch(IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}catch (Exception e) {
				showErrorMessage(view, "Não foi possível buscar as avarias");
				e.printStackTrace();
			}
		}
	}
}

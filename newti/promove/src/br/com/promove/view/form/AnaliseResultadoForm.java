package br.com.promove.view.form;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Resumo;
import br.com.promove.entity.FotoAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.exportacao.GraficoExport;
import br.com.promove.menu.PromoveToolbar.RelatorioSWF;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AnaliseResultadoView;
import br.com.promove.view.form.VeiculoSearchForm.VeiculoFieldFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.AbstractSelect.Filtering;

public class AnaliseResultadoForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private AnaliseResultadoView view;
	private Button search;
	private Button export;
	private Button grafico;
	private ComboBox cmbOrigemDe;
	private ComboBox cmbOrigemAte;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private PromoveApplication app;
	
	public AnaliseResultadoForm(PromoveApplication app) {
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
		
		search = new Button("Buscar", new AnaliseResultadoListener());
		export = new Button("Gerar Arquivo", new AnaliseResultadoListener());
		grafico = new Button("Gerar Gráfico", new AnaliseResultadoListener());
		
		cmbOrigemDe = new ComboBox("Local de Vistoria De");
		cmbOrigemDe.addContainerProperty("label", String.class, null);
		
		try {
			//i = cmbOrigemDe.addItem(new OrigemAvaria());
			//i.getItemProperty("label").setValue("Selecione...");
			for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
				i = cmbOrigemDe.addItem(or);
				i.getItemProperty("label").setValue(or.getDescricao());
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
				i = cmbOrigemAte.addItem(or);
				i.getItemProperty("label").setValue(or.getDescricao());
				cmbOrigemAte.setValue(or);
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
		
		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
		layout.addComponent(this);
		addField("cmbOrigemDe", cmbOrigemDe);
		addField("cmbOrigemAte", cmbOrigemAte);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
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
		footer.addComponent(grafico);
		footer.setVisible(true);
		
		return footer;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}

	public void setView(AnaliseResultadoView view) {
		this.view = view;
	}
	
	class AuditoriaFieldFactory extends DefaultFieldFactory{
		
		private AnaliseResultadoForm form;
		private boolean isNew;

		public AuditoriaFieldFactory(AnaliseResultadoForm form, boolean isNew) {
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
	
	class AnaliseResultadoListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				List<Resumo> resumos = null;
				Map<String, List<PieData>> itens = null;
				
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				OrigemAvaria oride = (OrigemAvaria)cmbOrigemDe.getValue();
				OrigemAvaria oriate = (OrigemAvaria)cmbOrigemAte.getValue();
				//Integer periodo = (Integer)cmbPeriodo.getValue();
				BeanItem<Veiculo> item = (BeanItem<Veiculo>)getItemDataSource();
			
				if(de == null || ate == null)
					throw new IllegalArgumentException("Informe o período");
			
				itens = cadastroService.buscarAnaliseResultado(item.getBean(), de, ate, oride, oriate);
				
				if(event.getButton() == search) {
					resumos = criaListaResumoesComItens(itens);
					view.getTable().filterTable(resumos);
				}else if(event.getButton() == export) {
					resumos = criaListaResumoesComItens(itens);
					String file = exportacaoService.exportarXLSResumo(resumos, "VEÍCULOS", "MODELO");
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=analise_resultado.xls&file=" + file));
				}else if(event.getButton() == grafico) {
					String xml = GraficoExport.gerarXmlExportacao(itens);
					String xmlEncoded = URLEncoder.encode(xml, "UTF-8");
					Window w = new Window("Gráfico");
			        w.setHeight("520px");
			        w.setWidth("650px");
			        w.setPositionY(25);
			        w.setPositionX(300);
			        
			        WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					//String path = ctx.getHttpSession().getServletContext().getContextPath();
					
			        app.getMainWindow().addWindow(w);
			        
			        //w.addComponent(new RelatorioSWF());
			    	ThemeResource swf = new ThemeResource("swf/pie.swf");
			        Embedded e = new Embedded(null, swf);
			        e.setType(Embedded.TYPE_OBJECT);
			        e.setMimeType("application/x-shockwave-flash");
			        e.setParameter("allowFullScreen", "true");
			        e.setParameter("FlashVars", "report="+xmlEncoded);
			        e.setWidth("600px");
			        e.setHeight("400px");
			        Label lbl = new Label("<h2>Análise de Resultado</h2>");
			        lbl.setContentMode(Label.CONTENT_XHTML);
			        w.addComponent(lbl);
			        w.addComponent(e);
			        
				}
			}catch(IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}catch (Exception e) {
				showErrorMessage(view, "Não foi possível apurar");
				e.printStackTrace();
			}
		}

		private List<Resumo> criaListaResumoesComItens(Map<String, List<PieData>> itens) {
			List<Resumo> resumos = new ArrayList<Resumo>();
			Integer itemTotalGeral = 0;

			for(Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				for(PieData pd : entry.getValue()) {
					int vl = new Integer(pd.getValue());
					itemTotalGeral += vl;
				}
			}
	        view.getTable().setColumnFooter("quantidadeItem", itemTotalGeral.toString());
	        
	        for(Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				String itemName = entry.getKey();
				Integer itemTotal = 0;
				for(PieData pd : entry.getValue()) {
					int vl = new Integer(pd.getValue());
					itemTotal += vl;
				}
				for(PieData pd : entry.getValue()) {
					int vl = new Integer(pd.getValue());
					double percentual = ((double)vl / (double)itemTotal) * 100;
					if (pd == entry.getValue().get(0)) {
						double percentualTotal = ((double)itemTotal / (double)itemTotalGeral) * 100;
						resumos.add(new Resumo(itemName, itemTotal, percentualTotal, pd.getLabel(), vl, percentual));
					}else {
						resumos.add(new Resumo(null, null, null, pd.getLabel(), vl, percentual));
					}
				}
			}
			return resumos;
		}
	}
}

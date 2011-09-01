package br.com.promove.view.form;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Resumo;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.PieData;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.exportacao.AvariasExport;
import br.com.promove.exportacao.GraficoExport;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.OrdemResumoComparator;
import br.com.promove.view.ResumoAvariasView;
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

public class ResumoAvariasForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private ResumoAvariasView view;
	private Button search;
	private Button export;
	private Button grafico;
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
		grafico = new Button("Gerar Gráfico", new ResumoAvariasListener());
		
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
		
		cmbPeriodo = new ComboBox("Período por");
		cmbPeriodo.addContainerProperty("label", String.class, null);
		
		i = cmbPeriodo.addItem(1);
		i.getItemProperty("label").setValue("Data da vistoria");
		i = cmbPeriodo.addItem(2);
		i.getItemProperty("label").setValue("Data de registro do veículo");
		
		cmbPeriodo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbPeriodo.setImmediate(true);
		cmbPeriodo.setNullSelectionAllowed(false);
		cmbPeriodo.setItemCaptionPropertyId("label");
		cmbPeriodo.setWidth("200px");
		cmbPeriodo.setValue(cmbPeriodo.getItemIds().iterator().next());

		cmbItem = new ComboBox("Resumo por");
		cmbItem.addContainerProperty("label", String.class, null);
		
		i = cmbItem.addItem("");
		i.getItemProperty("label").setValue("Selecione...");
		i = cmbItem.addItem("origemavaria");
		i.getItemProperty("label").setValue("Local de Vistoria");
		i = cmbItem.addItem("localavaria");
		i.getItemProperty("label").setValue("Peça Avariada");
		i = cmbItem.addItem("tipoavaria");
		i.getItemProperty("label").setValue("Tipo de avaria");
		i = cmbItem.addItem("modelo");
		i.getItemProperty("label").setValue("Modelo");
		i = cmbItem.addItem("fabricante");
		i.getItemProperty("label").setValue("Fabricante");
		
		cmbItem.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		cmbItem.setImmediate(true);
		cmbItem.setNullSelectionAllowed(false);
		cmbItem.setItemCaptionPropertyId("label");
		cmbItem.setValue(cmbItem.getItemIds().iterator().next());

		cmbSubitem = new ComboBox("Sub-resumo por");
		cmbSubitem.addContainerProperty("label", String.class, null);
		
		i = cmbSubitem.addItem("");
		i.getItemProperty("label").setValue("Selecione...");
		i = cmbSubitem.addItem("origemavaria");
		i.getItemProperty("label").setValue("Local de Vistoria");
		i = cmbSubitem.addItem("localavaria");
		i.getItemProperty("label").setValue("Peça Avariada");
		i = cmbSubitem.addItem("tipoavaria");
		i.getItemProperty("label").setValue("Tipo de avaria");
		i = cmbSubitem.addItem("modelo");
		i.getItemProperty("label").setValue("Modelo");
		i = cmbSubitem.addItem("fabricante");
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
				List<Resumo> resumos = null;
				Map<String, List<PieData>> itens = null;
				String item = null;
				String subitem = null;
				
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				OrigemAvaria oride = (OrigemAvaria)cmbOrigemDe.getValue();
				OrigemAvaria oriate = (OrigemAvaria)cmbOrigemAte.getValue();
				Integer periodo = (Integer)cmbPeriodo.getValue();
				item = (String)cmbItem.getValue();
				subitem = (String)cmbSubitem.getValue();
				
				if (item.isEmpty() && !subitem.isEmpty()) item = subitem;
				if (subitem.equals(item)) subitem = "";
				
				String itemLabel = (String)cmbItem.getItemCaption(item);
				String subitemLabel = (String)cmbItem.getItemCaption(subitem);

				
				BeanItem<Veiculo> veic = (BeanItem<Veiculo>)getItemDataSource();
				
				if(de == null || ate == null)
					throw new IllegalArgumentException("Informe o período");
				
				itens = avariaService.buscarResumo(veic.getBean(), de, ate, periodo, oride, oriate, item, subitem);
				
				if(event.getButton() == search) {
					resumos = criaListaResumoesComItens(itens);
					view.getTable().filterTable(resumos);
				}else if(event.getButton() == export) {
					resumos = criaListaResumoesComItens(itens);
					String file = exportacaoService.exportarXLSResumo(resumos, itemLabel.toUpperCase(), subitemLabel.toUpperCase());
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=resumo_avarias.xls&file=" + file));
				}else if(event.getButton() == grafico) {
					List<Map<String, List<PieData>>> itens_ordenados = ordenarItensMap(itens);
					String xml = GraficoExport.gerarXmlExportacaoFromList(itens_ordenados);
					String xmlEncoded = URLEncoder.encode(xml, "UTF-8");
					
					Window w = new Window("Gráfico");
			        w.setHeight("520px");
			        w.setWidth("950px");
			        w.setPositionY(25);
			        w.setPositionX(150);
			        
			        WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					
			        app.getMainWindow().addWindow(w);
			        
			        //w.addComponent(new RelatorioSWF());
			    	ThemeResource swf = new ThemeResource("swf/pie.swf");
			        Embedded e = new Embedded(null, swf);
			        e.setType(Embedded.TYPE_OBJECT);
			        e.setMimeType("application/x-shockwave-flash");
			        e.setParameter("allowFullScreen", "true");
			        e.setWidth("900px");
			        e.setHeight("400px");
			        e.setParameter("FlashVars", "report="+xmlEncoded);
			        String titulo = "<h2>Resumo de Avarias";
			        if (!item.isEmpty()) titulo += " por " + itemLabel;
			        if (!subitem.isEmpty()) titulo += " e " + subitemLabel;
			        titulo += "</h2>";
			        Label lbl = new Label(titulo);
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
		
		private List<Map<String, List<PieData>>> ordenarItensMap(Map<String, List<PieData>> itens) {
			List<Map<String, List<PieData>>> data = new ArrayList<Map<String,List<PieData>>>();
			Map<Integer, Map<String, List<PieData>>> ordered = new TreeMap<Integer, Map<String, List<PieData>>>(new OrdemResumoComparator());
			for(Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				List<PieData> tmp_pd = new ArrayList<PieData>();
				String itemName = entry.getKey();
				Integer itemTotal = 0;
				for(PieData pd : entry.getValue()) {
					int vl = new Integer(pd.getValue());
					tmp_pd.add(new PieData(pd.getLabel(), Integer.toString(vl)));
					itemTotal += vl;
				}
				
				Map<String, List<PieData>> tmp_map = new HashMap<String, List<PieData>>();
				tmp_map.put(itemName, tmp_pd);
				ordered.put(itemTotal, tmp_map);
			}
			
			for(Map.Entry<Integer, Map<String, List<PieData>>> entry : ordered.entrySet()) {
				data.add(entry.getValue());
			}
			
			return data;
		}

		private List<Resumo> criaListaResumoesComItens(Map<String, List<PieData>> itens) {
			List<Resumo> resumos = new ArrayList<Resumo>();
			Map<Integer, List<Resumo>> ordered = new TreeMap<Integer, List<Resumo>>(new OrdemResumoComparator());
			Integer itemTotalGeral = 0;
			
			for(Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				for(PieData pd : entry.getValue()) {
					int vl = new Integer(pd.getValue());
					itemTotalGeral += vl;
				}
			}
	        view.getTable().setColumnFooter("quantidadeItem", itemTotalGeral.toString());
	        
			for(Map.Entry<String, List<PieData>> entry : itens.entrySet()) {
				List<Resumo> tmp_resumos = new ArrayList<Resumo>();
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
						tmp_resumos.add(new Resumo(itemName, itemTotal, percentualTotal, pd.getLabel(), vl, percentual));
					}else {
						tmp_resumos.add(new Resumo(null, null, null, pd.getLabel(), vl, percentual));
					}
				}
				tmp_resumos.add(new Resumo(null, null, null, ".", null, null));
				ordered.put(itemTotal, tmp_resumos);
			}
			
			for(List<Resumo> lresumo: ordered.values()) {
				resumos.addAll(lresumo);
			}
			return resumos;
		}
	}
}

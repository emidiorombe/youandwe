package br.com.promove.view.form;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.VeiculoAvariaTables;
import br.com.promove.view.VeiculoListView;
import br.com.promove.view.form.AvariaSearchForm.AvariaSearchListener;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.Filtering;
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

public class VeiculoSearchForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private VeiculoListView view;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private Button search;
	private Button export;
	private PromoveApplication app;
	
	public VeiculoSearchForm(PromoveApplication app) {
		this.app = app;
		cadastroService = ServiceFactory.getService(CadastroService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		search = new Button("Buscar", new VeiculoSearchListener());
		export = new Button("Gerar Arquivo", new VeiculoSearchListener());
		
		txtDe = new PopupDateField("De");
		txtDe.setLocale(new Locale("pt", "BR"));
		txtDe.setResolution(DateField.RESOLUTION_DAY);
		
		txtAte = new PopupDateField("Ate");
		txtAte.setLocale(new Locale("pt", "BR"));
		txtAte.setResolution(DateField.RESOLUTION_DAY);
		
		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
		layout.addComponent(this);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		
	}
	
	public void createFormBody(BeanItem<Veiculo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new VeiculoFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"chassi", "modelo", "tipo", "navio"});
		
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
	
	public Component getFormLayout() {
		return layout;
	}

	public void setView(VeiculoListView view) {
		this.view = view;
	}
	
	class VeiculoSearchListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				BeanItem<Veiculo> item = (BeanItem<Veiculo>)getItemDataSource();
				
				if(item.getBean().getChassi() == null || item.getBean().getChassi().isEmpty()) {
					if(de == null || ate == null)
						throw new IllegalArgumentException("Informe um chassi ou período");
				}
				
				List<Veiculo> list = cadastroService.buscarVeiculoPorFiltro(item.getBean(), de, ate);
				
				if(event.getButton() == search) {
					view.getTables().getTableVeiculo().filterTable(list);
					view.getTables().getTableAvaria().removeAllItems();
				} else if(event.getButton() == export) {
					String file = exportacaoService.exportarXLSVeiculos(list);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=veiculos.xls&file=" + file));
				}
			} catch(IllegalArgumentException ie) {
				showErrorMessage(view, ie.getMessage());
			}catch(PromoveException pe) {
				showErrorMessage(view, "Não foi possível buscar os veículos");
			} 
		}
	}
	
	class VeiculoFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		private VeiculoSearchForm form;
		public VeiculoFieldFactory(VeiculoSearchForm form, boolean b) {
			this.form = form;
			newLocal = b;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
			}
			
			if(propertyId.equals("chassi")) {
				f.setWidth("200px");
			}else if(propertyId.equals("modelo")) {
				try {
					ComboBox c = new ComboBox("Modelo");
					c.addContainerProperty("label", String.class, null);
					
					Item item_default = c.addItem(new Modelo());
					item_default.getItemProperty("label").setValue("Selecione...");

					
					for(Modelo m: cadastroService.buscarTodosModelos()) {
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
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar os Modelos");
				}
			}else if(propertyId.equals("fabricante")) {
				try {
					ComboBox c = new ComboBox("Fabricante");
					c.addContainerProperty("label", String.class, null);
					
					Item item_default = c.addItem(new Fabricante());
					item_default.getItemProperty("label").setValue("Selecione...");
					
					for(Fabricante fabricante: cadastroService.buscarTodosFabricantes()) {
						Item i = c.addItem(fabricante);
						i.getItemProperty("label").setValue(fabricante.getNome());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar os Fabricantes");
				}
			}else if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo");
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
	
}

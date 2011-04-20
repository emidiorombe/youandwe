package br.com.promove.view.form;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.Transportadora;
import br.com.promove.entity.Ctrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.CtrcView;
import br.com.promove.view.form.AvariaSearchForm.AvariaSearchListener;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerValidator;
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

public class CtrcSearchForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private CtrcService ctrcService;
	private ExportacaoService exportacaoService;
	private CtrcView view;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	//private TextField txtNumero;
	private Button search;
	private Button export;
	private PromoveApplication app;
	
	public CtrcSearchForm(PromoveApplication app) {
		this.app = app;
		ctrcService = ServiceFactory.getService(CtrcService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		search = new Button("Buscar", new CtrcSearchListener());
		export = new Button("Gerar Arquivo", new CtrcSearchListener());
		
		//txtNumero = new TextField("Numero");
		
		txtDe = new PopupDateField("De");
		txtDe.setLocale(new Locale("pt", "BR"));
		txtDe.setResolution(DateField.RESOLUTION_DAY);
		
		txtAte = new PopupDateField("Ate");
		txtAte.setLocale(new Locale("pt", "BR"));
		txtAte.setResolution(DateField.RESOLUTION_DAY);
		
		createFormBody(new BeanItem<Ctrc>(new Ctrc()));
		layout.addComponent(this);
		//addField("txtNumero", txtNumero);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		
	}
	
	public void createFormBody(BeanItem<Ctrc> item) {
		setItemDataSource(item);
		setFormFieldFactory(new CtrcFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"numero", "transp"});
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

	public void setView(CtrcView view) {
		this.view = view;
	}
	
	class CtrcSearchListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == search) {
				try {
					commit();
					Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
					Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
					BeanItem<Ctrc> item = (BeanItem<Ctrc>)getItemDataSource();
					
					//if (!(txtNumero.toString() == null) && !(txtNumero.toString().isEmpty()))
					//	item.getBean().setNumero(Integer.parseInt(txtNumero.toString()));
					if(item.getBean().getNumero() == null || item.getBean().getNumero().equals(null)) {
						if(de == null || ate == null)
							throw new IllegalArgumentException("Informe um numero ou período para busca.");
					}
					
					List<Ctrc> list = ctrcService.buscarCtrcPorFiltro(item.getBean(), de, ate);
					view.getTable().filterTable(list);
				}catch(IllegalArgumentException ie) {
					showErrorMessage(view, ie.getMessage());
				}catch(PromoveException pe) {
					showErrorMessage(view, pe.getMessage() + " Não foi possível buscar os CTRCs");
				} 
			}else if(event.getButton() == export) {
				try {
					commit();
					Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
					Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
					BeanItem<Ctrc> item = (BeanItem<Ctrc>)getItemDataSource();
					
					if (item.getBean().getNumero() == null) {
						if(de == null || ate == null)
							throw new IllegalArgumentException("Informe um numero ou período para gerar arquivo.");
					}
					
					List<Ctrc> list = ctrcService.buscarCtrcPorFiltro(item.getBean(), de, ate);
					String file = exportacaoService.exportarXLSCtrcs(list);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=ctrcs.xls&file=" + file));
				}catch(PromoveException pe) {
					showErrorMessage(view, "Não foi possível gerar arquivo.");
				}
			}
		}
		
	}
	
	class CtrcFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		private CtrcSearchForm form;
		public CtrcFieldFactory(CtrcSearchForm form, boolean b) {
			this.form = form;
			newLocal = b;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
			}
			
			if(propertyId.equals("codigo")) {
				if(!newLocal)
					f.setReadOnly(true);
			} else if(propertyId.equals("numero")) {
				f.addValidator(new IntegerValidator(propertyId.toString() + " deve ser numérico"));
			} else if(propertyId.equals("transp")) {
				try {
					ComboBox c = new ComboBox("Transportadora");
					c.addContainerProperty("label", String.class, null);
					
					Item item_default = c.addItem(new Transportadora());
					item_default.getItemProperty("label").setValue("Selecione...");

					
					for(Transportadora t: ctrcService.buscarTodasTransportadoras()) {
						Item i = c.addItem(t);
						i.getItemProperty("label").setValue(t.getDescricao());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				} catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar as Transportadoras");
				}
			}
			return f;
		}
		
	}
	
}

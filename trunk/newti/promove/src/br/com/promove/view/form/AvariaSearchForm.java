package br.com.promove.view.form;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.AvariaSearchView;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.Filtering;

public class AvariaSearchForm extends BaseForm{
	private VerticalLayout layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	private AvariaSearchView view;
	private Button search;
	private PopupDateField txtDe;
	private PopupDateField txtAte;
	private TextField txtChassi;
	
	public AvariaSearchForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		txtChassi = new TextField("Chassi:");
		
		search = new Button("Buscar", new AvariaSearchListener());
		txtDe = new PopupDateField("De:");
		txtDe.setLocale(new Locale("pt", "BR"));
		txtDe.setResolution(DateField.RESOLUTION_DAY);
		
		txtAte = new PopupDateField("Até:");
		txtAte.setLocale(new Locale("pt", "BR"));
		txtAte.setResolution(DateField.RESOLUTION_DAY);
		
		
		createFormBody(new BeanItem<Avaria>(new Avaria()));
		layout.addComponent(this);
		addField("txtDe", txtDe);
		addField("txtAte", txtAte);
		addField("txtChassi", txtChassi);
		layout.addComponent(search);
		layout.setSpacing(true);
		
		
	}

	private void createFormBody(BeanItem<Avaria> beanItem) {
		setItemDataSource(beanItem);
		setFormFieldFactory(new AvariaFieldFactory(this, beanItem.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"tipo", "origem", "local"});
		
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
				if(propertyId.equals("tipo")) {
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
	
	class AvariaSearchListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				commit();
				Date de = txtDe.getValue() != null ? (Date)txtDe.getValue() : null;
				Date ate = txtAte.getValue() != null ? (Date)txtAte.getValue() : null; 
				BeanItem<Avaria> item = (BeanItem<Avaria>)getItemDataSource();
				
				List<Avaria> avarias = avariaService.buscarAvariaPorFiltros(txtChassi.getValue().toString(), item.getBean(), de, ate);
				view.getTable().filterTable(avarias);
			} catch (Exception e) {
				showErrorMessage(view, "Não foi possível buscar as avarias");
			}
			
		}
		
	}
	
}

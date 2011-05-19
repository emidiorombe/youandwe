package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.Transportadora;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ErroImportCtrcView;
import br.com.promove.view.form.CtrcSearchForm.CtrcSearchListener;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ErroImportCtrcForm extends BaseForm {
	private ErroImportCtrcView view;
	private VerticalLayout layout = new VerticalLayout();
	private CtrcService ctrcService;
	private PromoveApplication app;
	
	private Button save;
	private Button saveAll;
	private Button remove;
	private Button export;
	
	public ErroImportCtrcForm(PromoveApplication app) {
		this.app = app;
		ctrcService = ServiceFactory.getService(CtrcService.class);
		buildForm();
	}
	
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroCtrcFormListener());
		remove = new Button("Excluir", new ErroCtrcFormListener());
		saveAll = new Button("Revalidar Todos", new ErroCtrcFormListener());
		export = new Button("Exportar Lista", new ErroCtrcFormListener());

		createFormBody(new BeanItem<InconsistenciaCtrc>(new InconsistenciaCtrc()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}
	
	public void createFormBody(BeanItem<InconsistenciaCtrc> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroCtrcFieldFactory()); 
		setVisibleItemProperties(new Object[] {"transp"});

	}

	public void addNewTipoCtrc() {
		createFormBody(new BeanItem<InconsistenciaCtrc>(new InconsistenciaCtrc()));
	}

	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(saveAll);
		//footer.addComponent(export);
		footer.setVisible(true);

		return footer;
	}

	
	class ErroCtrcFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<InconsistenciaCtrc> item = (BeanItem<InconsistenciaCtrc>) getItemDataSource();
						item.getBean().getCtrc().setTransp((Transportadora)item.getItemProperty("transp").getValue());
						ctrcService.salvarCtrc(item.getBean().getCtrc());
						ctrcService.excluirInconsistenciaCtrc(item.getBean());
						view.getTable().reloadTable();
						showSuccessMessage(view, "Inconsistência salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(IllegalArgumentException ie) {
					view.getTable().reloadTable();
					showErrorMessage(view, ie.getMessage());
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Inconsistência");
				}
				
			}else if(event.getButton() == remove){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<InconsistenciaCtrc> item = (BeanItem<InconsistenciaCtrc>) getItemDataSource();
						ctrcService.excluirInconsistenciaCtrc(item.getBean());
						view.getTable().reloadTable();
						showSuccessMessage(view, "Inconsistência excluida!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível excluir Inconsistência");
				}
			}else if(event.getButton() == saveAll) {
				try {
					List<InconsistenciaCtrc> buscarTodasInconsistenciasDeCtrcs = ctrcService.buscarTodasInconsistenciasCtrc();
					for (InconsistenciaCtrc inc : buscarTodasInconsistenciasDeCtrcs) {
						if(ctrcService.buscarCtrcPorFiltro(inc.getCtrc(), null, null).size() == 0) {
							if(StringUtilities.getTranspFromErrorMessage(inc.getMsgErro()) != null) {
								List<Transportadora> transportadoras = ctrcService.buscaTransportadoraPorCnpj(StringUtilities.getTranspFromErrorMessage(inc.getMsgErro()));
								if((transportadoras.size() == 0)) {
									continue;
								}else {
									inc.getCtrc().setTransp(transportadoras.get(0));
								}
							}
							
							ctrcService.salvarCtrc(inc.getCtrc());
							ctrcService.excluirInconsistenciaCtrc(inc);
						} else {
							ctrcService.excluirInconsistenciaCtrc(inc);
						}
					}
					showSuccessMessage(view, "Inconsistências salvas!");
					view.getTable().reloadTable();
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Inconsistências");
				}
			}
		}
	}
	
	class ErroCtrcFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
			}
			
			if(propertyId.equals("transp")) {
				try {
					ComboBox c = new ComboBox("Transportadora");
					c.addContainerProperty("label", String.class, null);
				
					for(Transportadora t: ctrcService.buscarTodasTransportadoras()) {
						Item i = c.addItem(t);
						i.getItemProperty("label").setValue(t.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Transportadora obrigatória");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0) {
						Transportadora t2 = ((BeanItem<InconsistenciaCtrc>) getItemDataSource()).getBean().getTransp();
						if(t2 != null) {
							Iterator<Transportadora> it = c.getItemIds().iterator(); 
							while(it.hasNext()) {
								Transportadora t1 = it.next();
								if(t2.getId().equals(t1.getId())) {
									c.setValue(t1);
								}
							}
						}
					
					}
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view, "Não foi possível buscar as transportadoras");
				}
			}
			return f;
		}
	}

	public void setView(ErroImportCtrcView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return layout;
	}

}

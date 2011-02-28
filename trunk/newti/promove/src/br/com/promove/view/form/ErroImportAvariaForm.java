package br.com.promove.view.form;

import java.util.List;

import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportAvariaView;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ErroImportAvariaForm extends BaseForm {

	private ErroImportAvariaView view;
	private VerticalLayout layout = new VerticalLayout();
	private CadastroService cadastroService;
	private AvariaService avariaService;
	
	private Button save;
	private Button saveAll;
	private Button remove;
	
	public ErroImportAvariaForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}
	
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroAvariaFormListener());
		remove = new Button("Excluir", new ErroAvariaFormListener());
		saveAll = new Button("Revalidar Todos", new ErroAvariaFormListener());

		createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		
	}
	
	public void createFormBody(BeanItem<InconsistenciaAvaria> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroVeiculoFieldFactory()); 
		setVisibleItemProperties(new Object[] {"veiculo"});

	}

	public void addNewTipoAvaria() {
		createFormBody(new BeanItem<InconsistenciaAvaria>(new InconsistenciaAvaria()));
	}

	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(saveAll);
		footer.setVisible(true);

		return footer;
	}

	
	class ErroAvariaFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<InconsistenciaAvaria> item = (BeanItem<InconsistenciaAvaria>) getItemDataSource();
						List<Veiculo> v = cadastroService.buscarVeiculosPorChassi(item.getBean().getVeiculo().getChassi());
						if( v.size() == 0) {
							throw new IllegalArgumentException("Veículo com chassi " + item.getBean().getVeiculo().getChassi() + " não encontrado");
						}else
							item.getBean().setVeiculo(v.get(0));

						avariaService.salvarAvaria(item.getBean().getAvaria());
						avariaService.excluirInconsistenciaImportAvaria(item.getBean());
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
						BeanItem<InconsistenciaAvaria> item = (BeanItem<InconsistenciaAvaria>) getItemDataSource();
						avariaService.excluirInconsistenciaImportAvaria(item.getBean());
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
					List<InconsistenciaAvaria> lista = avariaService.buscarTodasInconsistenciasAvaria();
					for (InconsistenciaAvaria inc : lista) {
						avariaService.salvarAvariaDeInconsistencias(inc.getAvaria());
					}
					view.getTable().reloadTable();
					showSuccessMessage(view, "Inconsistências salvas!");

				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Inconsistências");
				}
			}
			
		}
		
	}
	
	class ErroVeiculoFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			 if(propertyId.equals("veiculo")) {
				 TextField chassi = new TextField("Chassi");
				 chassi.setNullRepresentation("");
				 return chassi;
			 }
			
			
			return f;
		}
	}


	public void setView(ErroImportAvariaView view) {
		this.view = view;
		
	}

	public Component getFormLayout() {
		return layout;
	}

}

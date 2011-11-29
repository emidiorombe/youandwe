package br.com.promove.view.form;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.InconsistenciaCtrc;
import br.com.promove.entity.InconsistenciaVeiculo;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Veiculo;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ExportacaoService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportCtrcView;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErroImportCtrcForm extends BaseForm {
	private ErroImportCtrcView view;
	private VerticalLayout layout = new VerticalLayout();
	private CtrcService ctrcService;
	private CadastroService cadastroService;
	private ExportacaoService exportacaoService;
	private PromoveApplication app;
	private InconsistenciaCtrc inconsistencia;
	
	private Button save;
	private Button usado;
	private Button saveAll;
	private Button remove;
	private Button export;
	
	public ErroImportCtrcForm(PromoveApplication app) {
		this.app = app;
		this.inconsistencia = new InconsistenciaCtrc();
		ctrcService = ServiceFactory.getService(CtrcService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		exportacaoService = ServiceFactory.getService(ExportacaoService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ErroCtrcFormListener());
		usado = new Button("Cadastrar como Usado", new ErroCtrcFormListener());
		remove = new Button("Excluir CTRC", new ErroCtrcFormListener());
		saveAll = new Button("Revalidar Todos", new ErroCtrcFormListener());
		export = new Button("Exportar Lista", new ErroCtrcFormListener());

		createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
		layout.addComponent(this);
		layout.addComponent(createFooter());
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
	}
	
	public void createFormBody(BeanItem<VeiculoCtrc> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new ErroCtrcFieldFactory()); 
		setVisibleItemProperties(new Object[] {"chassiInvalido"});
	}

	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(usado);
		footer.addComponent(remove);
		footer.addComponent(saveAll);
		footer.addComponent(export);
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
						BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItemDataSource();
						if (item.getBean().getId() == null || item.getBean().getChassiInvalido() == null)
							throw new IllegalArgumentException("Selecione um veículo!");
						String chassi = item.getBean().getChassiInvalido().substring(0, 17);
						List<Veiculo> v = cadastroService.buscarVeiculosPorChassi(chassi);
						if (v.size() == 0) {
							throw new IllegalArgumentException("Veículo com chassi " + chassi + " não encontrado");
						}
						
						item.getBean().setVeiculo(v.get(0));
						item.getBean().setModelo(v.get(0).getModelo().getDescricao());
						item.getBean().setChassi(v.get(0).getChassi());
						item.getBean().setMsgErro("");
						
						if (ctrcService.salvarVeiculoCtrcDeInconsistencia(item.getBean())) {
							view.getTables().getTableCtrc().reloadTable();
							view.getTables().getTableVeiculo().removeAllItems();
							createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
						} else {
							view.getTables().getTableVeiculo().reloadTable(item.getBean().getInconsistencia());							
						}
						showSuccessMessage(view, "Inconsistência salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(IllegalArgumentException ie) {
					showErrorMessage(view, ie.getMessage());
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível salvar Inconsistência");
				}
				
			} else if(event.getButton() == usado){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItemDataSource();
						if (item.getBean().getId() == null || item.getBean().getChassiInvalido() == null)
							throw new IllegalArgumentException("Selecione um veículo!");
						String chassi = item.getBean().getChassiInvalido().substring(0, 17);
						List<Veiculo> v = cadastroService.buscarVeiculosPorChassi(chassi);
						if (v.size() > 0) {
							throw new IllegalArgumentException("Veículo com chassi " + chassi + " já cadastrado");
						}
						
						Veiculo veiculo = new Veiculo();
						veiculo.setChassi(chassi);
						veiculo.setCor(cadastroService.getById(Cor.class, new Integer(97)));
						veiculo.setTipo(cadastroService.getById(TipoVeiculo.class, new Integer(9)));
						veiculo.setValorMercadoria(item.getBean().getValorMercadoria());

						for(Modelo m : cadastroService.buscarTodosModelos()) {
							if (m.getDescricao().equals(item.getBean().getModelo())) {
								veiculo.setModelo(m);
							}
						}
						
						if (veiculo.getModelo() == null) {
							throw new IllegalArgumentException("Modelo " + item.getBean().getModelo() + " não cadastrado");
						}
							
						cadastroService.salvarVeiculo(veiculo);
						
						item.getBean().setVeiculo(veiculo);
						item.getBean().setMsgErro("");
						
						if (ctrcService.salvarVeiculoCtrcDeInconsistencia(item.getBean())) {
							view.getTables().getTableCtrc().reloadTable();
							view.getTables().getTableVeiculo().removeAllItems();
							createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
						} else {
							view.getTables().getTableVeiculo().reloadTable(item.getBean().getInconsistencia());							
						}
						showSuccessMessage(view, "Inconsistência salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(IllegalArgumentException ie) {
					showErrorMessage(view, ie.getMessage());
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível salvar Inconsistência");
				}
				
			}else if(event.getButton() == remove){
				try{
					if (inconsistencia.getId() == null) {
						throw new IllegalArgumentException("Selecione um CTRC");
					}
					
					ctrcService.excluirInconsistenciaCtrc(inconsistencia);
					List<Ctrc> ctrcs = ctrcService.buscarCtrcDuplicadoPorFiltros(inconsistencia.getCtrc());
					if (ctrcs.size() > 0) {
						ctrcService.excluirCtrc(ctrcs.get(0));
					}
					view.getTables().getTableCtrc().reloadTable();
					view.getTables().getTableVeiculo().removeAllItems();
					createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
					
					showSuccessMessage(view, "Inconsistência excluida!");
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível excluir Inconsistência");
				}catch(Exception e){
					showErrorMessage(view, e.getMessage());
				}
			}else if(event.getButton() == saveAll) {
				try {
					List<InconsistenciaCtrc> buscarTodasInconsistenciasDeCtrcs = ctrcService.buscarTodasInconsistenciasCtrc();
					for (InconsistenciaCtrc inc : buscarTodasInconsistenciasDeCtrcs) {
						ctrcService.revalidarInconsistencia(inc, true);
						/*
						if(ctrcService.buscarCtrcDuplicadoPorFiltros(inc.getCtrc()).size() == 0) {
							ctrcService.revalidarInconsistencia(inc, true);
						} else {
							ctrcService.excluirInconsistenciaCtrc(inc);
						}
						*/
					}
					showSuccessMessage(view, "Inconsistências salvas!");
					view.getTables().getTableCtrc().reloadTable();
					view.getTables().getTableVeiculo().removeAllItems();
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					de.printStackTrace();
					showErrorMessage(view, "Não foi possível salvar Inconsistências");
				}
			}else if(event.getButton() == export) {
				try {
					List<VeiculoCtrc> lista = ctrcService.buscarTodasInconsistenciasCtrcVeiculo();
					String file = exportacaoService.exportarXLSInconsistenciaCtrcVeiculo(lista);
					
					WebApplicationContext ctx = (WebApplicationContext) app.getContext();
					String path = ctx.getHttpSession().getServletContext().getContextPath();
					event.getButton().getWindow().open(new ExternalResource(path + "/export?action=export_excel&fileName=incctrc.xls&file=" + file));
				} catch (Exception e) {
					e.printStackTrace();
					showErrorMessage(view, "Não foi possível gerar arquivo.");
				}
			}
		}
	}
	
	class ErroCtrcFieldFactory extends DefaultFieldFactory {
		
		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if (propertyId.equals("chassiInvalido")) {
				String chassi = null;
				BeanItem<VeiculoCtrc> bitem = (BeanItem<VeiculoCtrc>) item;
				if (bitem.getBean() != null && bitem.getBean().getVeiculo() == null &&
						bitem.getBean().getChassiInvalido() != null)
					 chassi = bitem.getBean().getChassiInvalido();
				final ComboBox c = new ComboBox("Chassi");
				try {
					c.addContainerProperty("label", String.class, null);
					c.setRequired(true);
					c.setRequiredError("Chassi obrigatório");
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setItemCaptionPropertyId("label");
					c.setNewItemsAllowed(true);
					if(chassi != null) {
						Item i2 = c.addItem(new Veiculo(chassi));
						i2.getItemProperty("label").setValue(chassi);
						for(Veiculo v : cadastroService.buscarVeiculosPorFZ(chassi)) {
							Item i = c.addItem(v);
							i.getItemProperty("label").setValue(v.getChassi() + " - " + new Label(new SimpleDateFormat("dd/MM/yyyy").format(v.getDataCadastro())) + " - " + v.getModelo().getDescricao());
						}
					}
					c.setNewItemHandler(new NewItemHandler() {
						@Override
						public void addNewItem(String newV) {
							Item i = c.addItem(new Veiculo(newV));
							i.getItemProperty("label").setValue(newV);
				            c.setValue(new Veiculo(newV));
							
						}
					});
				}catch(Exception e) {
					e.printStackTrace();
					showErrorMessage(view,"Não foi possível buscar Veículos pelo FZ");
				}
				return c;
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


	public void setInconsistencia(InconsistenciaCtrc inconsistencia) {
		this.inconsistencia = inconsistencia;
	}


	public InconsistenciaCtrc getInconsistencia() {
		return inconsistencia;
	}

}

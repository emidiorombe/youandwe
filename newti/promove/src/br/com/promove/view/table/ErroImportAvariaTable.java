package br.com.promove.view.table;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import br.com.promove.entity.InconsistenciaAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ErroImportAvariaView;

public class ErroImportAvariaTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"chassiInvalido", "dataLancamento", "origem", "local",  "tipo", "msgErro"};
	public static final String[] COL_HEADERS = new String[] {"Chassi", "Data","Origem", "Local", "Tipo", "Mensagem"};
	
	private ErroImportAvariaView view;
	private CadastroService cadastroService;
	private ErroImportAvariaContainer container;
	private AvariaService avariaService;
	
	public ErroImportAvariaTable() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildTable();
	}

	private void buildTable() {
		setSizeFull();
		setColumnCollapsingAllowed(true);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setContainerDataSource(getContainer());
		setVisibleColumns(NATURAL_COL_ORDER);
		setColumnHeaders(COL_HEADERS);
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("dataLancamento", new ErroImportVeiculoColumnGenerator());
		
	}

	public void setView(ErroImportAvariaView view) {
		this.view = view;
		
	}
	
	public BeanItemContainer<InconsistenciaAvaria> getContainer(){
		if(container == null)
			container = new ErroImportAvariaContainer();
		return container;
	}
	
	public void reloadTable() {
		container.removeAllItems();
		container.populate();
	}
	
	class ErroImportAvariaContainer extends BeanItemContainer<InconsistenciaAvaria>  implements Serializable{
		
		public ErroImportAvariaContainer() {
			super(InconsistenciaAvaria.class);
			populate();
		}
		
		private void populate() {
			try {
				List<InconsistenciaAvaria> list = avariaService.buscarTodasInconsistenciasAvaria();
				for (InconsistenciaAvaria inc : list) {
					addItem(inc);
				}
			} catch (PromoveException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<InconsistenciaAvaria> item =  (BeanItem<InconsistenciaAvaria>) getItem(getValue());
			item.getBean().setVeiculo(new Veiculo(item.getBean().getChassiInvalido()));
            view.getForm().createFormBody(item);
			
		}
		
	}
	
	class ErroImportVeiculoColumnGenerator implements Table.ColumnGenerator{

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			InconsistenciaAvaria inc = (InconsistenciaAvaria) itemId;
			if(columnId.equals("dataLancamento")) {
				return new Label(new SimpleDateFormat("dd/MM/yyyy").format(inc.getDataLancamento())); 
			}
			return null;
		}
		
	}

}

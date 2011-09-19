package br.com.promove.view.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.promove.entity.Resumo;
import br.com.promove.entity.Usuario;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.view.ResumoAvariasView;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class ResumoAvariasTable extends Table{
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"item", "quantidadeItem", "percentualItem", "subitem", "quantidadeSubitem", "percentualSubitem"};
	public static final String[] COL_HEADERS = new String[] {"Item do Resumo", "Quantidade", "Percentual", "Item do sub-resumo", "Quantidade", "Percentual"};

	private ResumoAvariasView view;
	private CadastroService cadastroService;
	private ResumoContainer container;
	private NumberFormat formatPercentual = new DecimalFormat("#0.0'%'"); //("#0.00'%'")
	
	public ResumoAvariasTable() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildTable();
	}

	private void buildTable() {
		setSizeFull();
		//setColumnCollapsingAllowed(true);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setContainerDataSource(getContainer());
		setVisibleColumns(NATURAL_COL_ORDER);
		setColumnHeaders(COL_HEADERS);
		addListener(new RowSelectedListener());
		
		addGeneratedColumn("percentualItem", new ResumoTableColumnGenerator(this));
		addGeneratedColumn("percentualSubitem", new ResumoTableColumnGenerator(this));
		addGeneratedColumn("item", new ResumoTableColumnGenerator(this));
		addGeneratedColumn("subitem", new ResumoTableColumnGenerator(this));
		
		setColumnAlignment("quantidadeItem", ALIGN_RIGHT);
		setColumnAlignment("percentualItem", ALIGN_RIGHT);
		setColumnAlignment("quantidadeSubitem", ALIGN_RIGHT);
		setColumnAlignment("percentualSubitem", ALIGN_RIGHT);
		
        setFooterVisible(true);
        setColumnFooter("item", "Total");
        setColumnFooter("quantidadeItem", "");
	}

	public void filterTable(List<Resumo> resumos) {
		container.populate(resumos);
	}
	
	public void setView(ResumoAvariasView view) {
		this.view = view;
	}

	public BeanItemContainer<Resumo> getContainer() {
		if(container == null)
			container = new ResumoContainer();
		return container;
	}
	
	class ResumoContainer extends BeanItemContainer<Resumo> implements Serializable {
	
		public ResumoContainer(){
			super(Resumo.class);
		}
		
		private void populate(List<Resumo> resumos) {
			container.removeAllItems();
			for (Resumo resumo : resumos) {
				addItem(resumo);
			}
		}
	}
	
	class ResumoTableColumnGenerator implements Table.ColumnGenerator{

		private ResumoAvariasTable table;

		public ResumoTableColumnGenerator(ResumoAvariasTable table) {
			this.table = table;
		}

		@Override
		public Component generateCell(Table source, Object itemId, Object columnId) {
			Resumo c = (Resumo)itemId;
			if(columnId.toString().equals("percentualItem") && c.getPercentualItem() != null) {
				return new Label(formatPercentual.format(c.getPercentualItem()));
			}else if(columnId.toString().equals("percentualSubitem") && c.getPercentualSubitem() != null) {
				return new Label(formatPercentual.format(c.getPercentualSubitem()));
			}else if(columnId.toString().equals("item")) {
				return new Label(c.getItem());
			}else if(columnId.toString().equals("subitem")) {
				return new Label(c.getSubitem());
			}else {
				return null;
			}
		}
	}
	
	class RowSelectedListener implements ValueChangeListener{
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			Property property = event.getProperty();
			BeanItem<Resumo> item =  (BeanItem<Resumo>) getItem(getValue());
		}
	}
}

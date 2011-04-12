var PresidentsDataStore;         // this will be our datastore
var PresidentsColumnModel;       // this will be our columnmodel
var PresidentListingEditorGrid;
var PresidentListingWindow;

Ext.onReady(function(){
  Ext.QuickTips.init();
  
  PresidentsDataStore = new Ext.data.Store({
      id: 'PresidentsDataStore',
      proxy: new Ext.data.HttpProxy({
                url: '/ext_test',      // File to connect to
                method: 'POST'
            }),
            baseParams:{action: "GRID_LIST"}, // this parameter asks for listing
      reader: new Ext.data.JsonReader({   
                  // we tell the datastore where to get his data from
        root: 'results',
        totalProperty: 'total',
        id: 'id'
      },[ 
        {name: 'id', type: 'int', mapping: 'id'},
        {name: 'FirstName', type: 'string', mapping: 'firstName'},
        {name: 'LastName', type: 'string', mapping: 'lastName'},
        {name: 'IDparty', type: 'int', mapping: 'party'},
        {name: 'PartyName', type: 'string', mapping: 'pname'},
        {name: 'TookOffice', type: 'string', mapping: 'tookOffice'},
        {name: 'LeftOffice', type: 'string', mapping: 'leftOffice'},
        {name: 'Income', type: 'float', mapping: 'income'}
      ]),
      sortInfo:{field: 'id', direction: "ASC"}
    });
  
  PresidentsColumnModel = new Ext.grid.ColumnModel(
		    [{
		        header: 'ID',
		        readOnly: true,
		        dataIndex: 'id', // this is where the mapped name is important!
		        width: 50,
		        hidden: false
		      },{
		        header: 'First Name',
		        dataIndex: 'FirstName',
		        width: 150,
		        editor: new Ext.form.TextField({  // rules about editing
		            allowBlank: false,
		            maxLength: 20,
		            maskRe: /([a-zA-Z0-9\s]+)$/   // alphanumeric + spaces allowed
		          })
		      },{
		        header: 'Last Name',
		        dataIndex: 'LastName',
		        width: 150,
		        editor: new Ext.form.TextField({
		          allowBlank: false,
		          maxLength: 20,
		          maskRe: /([a-zA-Z0-9\s]+)$/
		          })
		      },{
		        header: 'ID party',
		        readOnly: true,
		        dataIndex: 'IDparty',
		        width: 50,
		        hidden: true                      // we don't necessarily want to see this...
		      },{
		        header: 'Party',
		        dataIndex: 'PartyName',
		        width: 150,
		        readOnly: true
		      },{
		        header: "Income",
		        dataIndex: 'Income',
		        width: 180,
		        renderer: function(v){ return '$ ' + v; },   
		        // we tell Ext how to display the number
		        editor: new Ext.form.NumberField({
		          allowBlank: false,
		          decimalSeparator : ',',
		          allowDecimals: true,
		          allowNegative: false,
		          blankText: '0',
		          maxLength: 11
		          })
		      },{
			        header: 'Took',
			        dataIndex: 'TookOffice',
			        width: 150,
			        readOnly: true
			      },{
				        header: 'Left',
				        dataIndex: 'LeftOffice',
				        width: 150,
				        readOnly: true
				      }]
		    );
		    PresidentsColumnModel.defaultSortable= true;
		    
		    PresidentListingEditorGrid =  new Ext.grid.EditorGridPanel({
		        id: 'PresidentListingEditorGrid',
		        store: PresidentsDataStore,     // the datastore is defined here
		        cm: PresidentsColumnModel,      // the columnmodel is defined here
		        enableColLock:true,
		        clicksToEdit:1,
		        selModel: new Ext.grid.RowSelectionModel({singleSelect:false})
		      });
		      
		    PresidentListingWindow = new Ext.Window({
		        id: 'PresidentListingWindow',
		        title: 'The Presidents of the USA',
		        closable:true,
		        width:1020,
		        height:350,
		        plain:true,
		        layout: 'fit',
		        draggable: false,
		        items: PresidentListingEditorGrid  // We'll just put the grid in for now...
		      });
		    
		    PresidentsDataStore.load();      // Load the data
		    PresidentListingWindow.show();   // Display our window
		    
		  });
  
  
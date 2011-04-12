Ext.onReady(function(){
	Ext.get('okButton').on('click', function(){
		var msg = Ext.get('msg');
		msg.load({
			url: '/ext_test', // <-- change if necessary
			params: 'name=' + Ext.get('name').dom.value,
			text: 'Updating...'
		});
		msg.show();
	});
	
	//GRID EXAMPLE
	var myData = [['Apple',29.89,0.24,0.81,'9/1 12:00am'],
	      		['Ext',83.81,0.28,0.34,'9/12 12:00am'],
	      		['Google',71.72,0.02,0.03,'10/1 12:00am'],
	      		['Microsoft',52.55,0.01,0.02,'7/4 12:00am'],
	      		['Yahoo!',29.01,0.42,1.47,'5/22 12:00am']
	          ];
	       
	          // create the data store
	          var ds = new Ext.data.ArrayStore({
	              fields: [
	                 {name: 'company'},
	                 {name: 'price', type: 'float'},
	                 {name: 'change', type: 'float'},
	                 {name: 'pctChange', type: 'float'},
	                 {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
	              ]
	          });
	       
	          // manually load local data
	          ds.loadData(myData);
	       
	          // create the colum Manager
	          var colModel = new Ext.grid.ColumnModel([
	          	    {header: 'Company', width: 160, sortable: true, dataIndex: 'company'},
	                  {header: 'Price', width: 75, sortable: true, dataIndex: 'price'},
	                  {header: 'Change', width: 75, sortable: true, dataIndex: 'change'},
	                  {header: '% Change', width: 75, sortable: true, dataIndex: 'pctChange'},
	                  {header: 'Last Updated', width: 85, sortable: true,
	                      renderer: Ext.util.Format.dateRenderer('m/d/Y'), dataIndex: 'lastChange'}
	          ]);
	       
	       
	          // create the Grid
	          var grid = new Ext.grid.GridPanel({
	              store: ds,
	              colModel: colModel,
	              height: 180,
	              width: 475,
	              title: 'My First Grid'
	          });
	       
	          // render the grid to the specified div in the page
	          grid.render('grid');
});
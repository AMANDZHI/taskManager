Ext.onReady(function(){
	Ext.define('Project', {
	 extend: 'Ext.data.Model',
	 idProperty: 'id',
	 fields: [
	     {name: 'id', type: 'string'},
	     {name: 'name',  type: 'string'},
	     {name: 'description', type: 'string'}
	 ],
	  getId: function() {
	    return this.id;
	  },

	 getName: function() {
	    return this.name;
	 },
	 setName: function(value) {
	    this.name = value;
	 },
	 getDescr: function() {
	    return this.description;
	 },
	 setDescr: function(value) {
	    this.description = value;
	 }
	});



	var myStore = Ext.create('Ext.data.Store', {
       model: 'Project',
       proxy: {
           type: 'rest',
           url: 'http://localhost:8080/projects',
           reader: {
               type: 'json',
               rootProperty: 'projects'
           }
       },
       autoLoad: true
   });

	Ext.create('Ext.grid.Panel', {
    title: 'Projects',
    store: myStore,
    plugins:[{
            ptype:'rowediting',
            clicksToEdit: 1
        }],
    selType: 'rowmodel',
    columns: 
    [
        { 
        	text: 'ID', 
        	dataIndex: 'id'
        },
        { 
        	text: 'NAME', 
        	dataIndex: 'name', 
        	flex: 1 ,
        	editor: {
                	xtype: 'textfield',
                	allowBlank: false
            	}
    	},
        { 
        	text: 'DESCRIPTION', 
        	dataIndex: 'description',
        	editor: {
                		xtype: 'textfield',
                		allowBlank: false
            		} 
        }
    ],
    listeners : {
    	beforeedit: function(e) {
    		console.log(e);
    	},
    	edit: function(e) {
    		var projectId = e.context.record.data.id;
    		var projectName = e.context.record.data.name;
    		var projectDescr = e.context.record.data.description;
    		var restURL = 'http://localhost:8080/project/update/' + projectId;

    		var conn = new Ext.data.Connection();
             conn.request({
                url: restURL,
                method: 'POST',
                params: {
                  id: projectId,
                  name: projectName,
                  description: projectDescr
                },

                success: function(a, response) {
                  e.context.record.commit();
                },
                                
                failure: function(a, response) {
                  Ext.Msg.alert("Failed", response.result.message);
                  e.context.record.reject();
                }
            });
    	}
    },

    tbar: [{
    		 text:'Add Project',
            tooltip:'Add a new Project',
            handler: function() {
            	var formPanel=Ext.create('Ext.form.Panel',{
            		title: 'Add Project',
			        width: 300,
			        height:200,
			        margin: 100,
			        layout: 'anchor',
			        defaults: {
			            anchor: '80%'
			        },
			        renderTo: Ext.getBody(),
			        items:[{
		                xtype: 'textfield',
		                fieldLabel: 'Name',
		                name: 'name',
		                labelAlign: 'top',
		                cls: 'field-margin',
		                flex: 1
		               }, 
		               {
		                xtype: 'textfield',
		                fieldLabel: 'Descr',
		                name: 'description',
		                labelAlign: 'top',
		                cls: 'field-margin',
		                flex: 1
		              }],
		            buttons: [{
			            text: 'Send',
			            handler: function(e) {
			               console.log(formPanel);
			               	var nameProject = formPanel.items.items[1].value;
			                var descrProject = formPanel.items.items[0].value;

			                var addUrl = 'http://localhost:8080/project/add';

				    		// var conn2 = new Ext.data.Connection();
				      //        conn2.request({
				      //           url: restURL,
				      //           method: 'POST',
				      //           params: {
				      //             name: nameProject,
				      //             description: descrProject
				      //           },

				      //           success: function(a, response) {
				      //             e.context.record.commit();
				      //           },
				                                
				      //           failure: function(a, response) {
				      //             Ext.Msg.alert("Failed", response.result.message);
				      //             e.context.record.reject();
				      //           }
				      //       });
						      Ext.Ajax.request({
							    url: addUrl, 
							    method: 'POST',
							     headers: { 'Content-Type': 'application/json' },  
							    params: {
							    	name: nameProject,
				                  description: descrProject
				              },
				              jsonData: {
                   "name" : nameProject,
                    "description" : descrProject
                 },
				              success: function(a, response) {
				              	myStore.add(response);
				                  e.context.record.commit();
				                },
				                                
				                failure: function(a, response) {
				                  Ext.Msg.alert("Failed", response.result.message);
				                  e.context.record.reject();
				                }
							});
			                
			            
			     
			            }
			        }, {
			            text: 'Cancel',
			            handler: function() {
			                formPanel.close();
			                }
			        	}],
			        });
            }
    }],
    height: 200,
    width: 400,
    renderTo: Ext.getBody()
	});
});
Ext.onReady(function(){
	Ext.define('Project', {
		extend: 'Ext.data.Model',
		idProperty: 'id',
		fields: [
		{name: 'id', type: 'string'},
		{name: 'name',  type: 'string'},
		{name: 'description', type: 'string'},
		{name: 'date', type: 'date'},
		{name: 'userLogin', type: 'string'}

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
			clicksToEdit: 2
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
			editor: {
				xtype: 'textfield',
				allowBlank: false
			}
		},
		{ 
			text: 'DESCRIPTION', 
			dataIndex: 'description',
			flex: 1,
			editor: {
				xtype: 'textfield',
				allowBlank: false
			} 
		},
		{ 
			text: 'DATE', 
			dataIndex: 'date',
			flex: 1,
			xtype: 'datecolumn',   
			format:'d-m-Y' 
		},
		{ 
			text: 'Login User', 
			dataIndex: 'userLogin',
			flex: 1 
		},
		{
			xtype:'actioncolumn',
			width:40,
			items:[{
				icon:'/resources/img/del.png',
				handler:function (grid, rowIndex, colIndex, e, item) {

					var idProject = item.record.data.id;
					var addUrl = 'http://localhost:8080/project/delete';
					Ext.Ajax.request({
						url: addUrl, 
						method: 'POST',
						headers: { 'Content-Type': 'application/json' },  
						params: {
							id: idProject

						},
						jsonData : {
							"id": idProject

						},

						success: function(response) {
							myStore.removeAt(rowIndex);
						},

						failure: function( response) {
							Ext.Msg.alert("Failed", response.result.message);

						}
					});
				}
			}]
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
					height:300,
					autoEl: 'center',
					margin: '-50 0 0 200',
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
					},
					{
						xtype: 'textfield',
						fieldLabel: 'Login User',
						name: 'userLogin',
						labelAlign: 'top',
						cls: 'field-margin',
						flex: 1
					},
					{
						xtype: 'datefield',
						fieldLabel: 'Date project',
						name: 'date',
						labelAlign: 'top',
						cls: 'field-margin',
						flex: 1,
						format:'y-m-d H:i:s'
					}

					],
					buttons: [{
						text: 'Send',
						handler: function(e) {
							console.log(formPanel);

							var nameProject = formPanel.items.items[0].value;
							var descrProject = formPanel.items.items[1].value;
							var userProject = formPanel.items.items[2].value;
							var dateProject = formPanel.items.items[3].value;
							console.log(dateProject);

							var addUrl = 'http://localhost:8080/project/add';
							Ext.Ajax.request({
								url: addUrl, 
								method: 'POST',
								headers: { 'Content-Type': 'application/json' },  
								params: {
									name: nameProject,
									description: descrProject,
									userLogin : userProject,
									date: dateProject
								},
								jsonData : {
									"name": nameProject,
									"description" : descrProject,
									"userLogin" : userProject,
									"date": dateProject
								},

								success: function(response) {
									formPanel.close();
									myStore.load();
								},

								failure: function( response) {
									console.log(response);
									Ext.Msg.alert("Failed", response.result.responseText);
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
		height: 500,
		width: 1000,
		renderTo: Ext.getBody()
	});
});
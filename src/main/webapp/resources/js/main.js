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

	Ext.define('Task', {
		extend: 'Ext.data.Model',
		idProperty: 'id',
		fields: [
		{name: 'id', type: 'string'},
		{name: 'name',  type: 'string'},
		{name: 'description', type: 'string'},
		{name: 'projectName', type: 'string'},
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

	Ext.define('User', {
		extend: 'Ext.data.Model',
		idProperty: 'id',
		fields: [
		{name: 'id', type: 'string'},
		{name: 'name',  type: 'string'},
		{name: 'login', type: 'string'},
		{name: 'date', type: 'date'},
		{name: 'role', type: 'string'}

		],
		getId: function() {
			return this.id;
		},

		getName: function() {
			return this.name;
		},
		setName: function(value) {
			this.name = value;
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

	var taskStore = Ext.create('Ext.data.Store', {
		model: 'Task',
		proxy: {
			type: 'rest',
			url: 'http://localhost:8080/tasks',
			reader: {
				type: 'json',
				rootProperty: 'tasks'
			}
		},
		autoLoad: true
	});

	var userStore = Ext.create('Ext.data.Store', {
		model: 'User',
		proxy: {
			type: 'rest',
			url: 'http://localhost:8080/users',
			reader: {
				type: 'json',
				rootProperty: 'users'
			}
		},
		autoLoad: true
	});

	var roles = [
	['ADMIN'],
	['USER']
	];

	Ext.create('Ext.tab.Panel', {
		title: 'Panels',
		width: 1000,
		height: 500,
		items:
		[{
			xtype: 'grid',
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
			}]},
			{
				xtype: 'grid',
				title: 'Tasks',
				store: taskStore,
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
					text: 'Name Project', 
					dataIndex: 'projectName',
					flex: 1 
				},
				{
					xtype:'actioncolumn',
					width:40,
					items:[{
						icon:'/resources/img/del.png',
						handler:function (grid, rowIndex, colIndex, e, item) {

							var idTask = item.record.data.id;
							var addUrl = 'http://localhost:8080/task/delete';
							Ext.Ajax.request({
								url: addUrl, 
								method: 'POST',
								headers: { 'Content-Type': 'application/json' },  
								params: {
									id: idTask

								},
								jsonData : {
									"id": idTask

								},

								success: function(response) {
									taskStore.removeAt(rowIndex);
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
						var taskId = e.context.record.data.id;
						var taskName = e.context.record.data.name;
						var taskDescr = e.context.record.data.description;
						var restURL = 'http://localhost:8080/task/update/' + taskId;

						var conn = new Ext.data.Connection();
						conn.request({
							url: restURL,
							method: 'POST',
							params: {
								id: taskId,
								name: taskName,
								description: taskDescr
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
					text:'Add Task',
					tooltip:'Add a new Task',
					handler: function() {
						var formPanel=Ext.create('Ext.form.Panel',{
							title: 'Add Task',
							width: 300,
							height:500,
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
								xtype: 'combobox',
								fieldLabel: 'Login User',
								store: userStore,
								valueField:'id',
								displayField:'login',
								queryMode:'remote',
								autoSelect:true,
								name: 'login',
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
							},
							{
								xtype: 'combobox',
								fieldLabel: 'Project',
								store: myStore,
								valueField:'id',
								displayField:'name',
								queryMode:'remote',
								autoSelect:true,
								name: 'nameProject',
								labelAlign: 'top',
								cls: 'field-margin',
								flex: 1
							}

							],
							buttons: [{
								text: 'Send',
								handler: function(e) {
									console.log(formPanel);

									var nameTask = formPanel.items.items[0].value;
									var descrTask = formPanel.items.items[1].value;
									var userTask = formPanel.items.items[2].value;
									var dateTask = formPanel.items.items[3].value;
									var idProject = formPanel.items.items[4].value;
									console.log(idProject);
									console.log(userTask);

									var addUrl = 'http://localhost:8080/task/add';
									Ext.Ajax.request({
										url: addUrl, 
										method: 'POST',
										headers: { 'Content-Type': 'application/json' },  
										params: {
											name: nameTask,
											description: descrTask,
											userId : userTask,
											date: dateTask,
											project: idProject
										},
										jsonData : {
											"name": nameTask,
											"description" : descrTask,
											"userId" : userTask,
											"date": dateTask
										},

										success: function(response) {
											formPanel.close();
											taskStore.load();
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
				}]},
				{
					xtype: 'grid',
					title: 'Users',
					store: userStore,
					plugins:[{
						ptype:'cellediting',
						clicksToEdit: 2
					}],
					selModel: 'cellmodel',
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
						text: 'LOGIN', 
						dataIndex: 'login',
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
						text: 'ROLE', 
						dataIndex: 'role',
						flex: 1 
					},
					{
						xtype:'actioncolumn',
						width:40,
						items:[{
							icon:'/resources/img/del.png',
							handler:function (grid, rowIndex, colIndex, e, item) {

								var idUser = item.record.data.id;
								var addUrl = 'http://localhost:8080/user/delete';
								Ext.Ajax.request({
									url: addUrl, 
									method: 'POST',
									headers: { 'Content-Type': 'application/json' },  
									params: {
										id: idUser

									},
									jsonData : {
										"id": idUser

									},

									success: function(response) {
										userStore.removeAt(rowIndex);
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
							var userId = e.context.record.data.id;
							var userName = e.context.record.data.name;
							var userLogin = e.context.record.data.login;
							var restURL = 'http://localhost:8080/user/update/' + userId;

							var conn = new Ext.data.Connection();
							conn.request({
								url: restURL,
								method: 'POST',
								params: {
									id: userId,
									name: userName,
									login: userLogin
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
						text:'Add User',
						tooltip:'Add a new User',
						handler: function() {
							var formPanel=Ext.create('Ext.form.Panel',{
								title: 'Add User',
								width: 300,
								height:600,
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
									fieldLabel: 'Login',
									name: 'login',
									labelAlign: 'top',
									cls: 'field-margin',
									flex: 1
								},
								{
									xtype: 'textfield',
									fieldLabel: 'Password User',
									name: 'password',
									labelAlign: 'top',
									cls: 'field-margin',
									flex: 1
								},
								{
									xtype: 'datefield',
									fieldLabel: 'Date user',
									name: 'date',
									labelAlign: 'top',
									cls: 'field-margin',
									flex: 1,
									format:'y-m-d H:i:s'
								},
								{
									xtype: 'combobox',
									fieldLabel: 'Role User',
									store: new Ext.data.SimpleStore({
										id:0,
										fields:
										[
										'myText' 
										],
										data: roles
									}),
									valueField:'myText',
									displayField:'myText',
									queryMode:'local',
									autoSelect:true,
									name: 'role',
									labelAlign: 'top',
									cls: 'field-margin',
									flex: 1
								}

								],
								buttons: [{
									text: 'Send',
									handler: function(e) {


										var nameUser = formPanel.items.items[0].value;
										var loginUser = formPanel.items.items[1].value;
										var passwordUser = formPanel.items.items[2].value;
										var dateUser = formPanel.items.items[3].value;
										var roleUser = formPanel.items.items[4].value;
										console.log(nameUser);
										console.log(roleUser);


										var addUrl = 'http://localhost:8080/user/add';
										Ext.Ajax.request({
											url: addUrl, 
											method: 'POST',
											headers: { 'Content-Type': 'application/json' },  
											params: {
												name: nameUser,
												login: loginUser,
												password : passwordUser,
												date: dateUser,
												role: roleUser
											},
											jsonData : {
												"name": nameUser,
												"login" : loginUser,
												"password" : passwordUser,
												"date": dateUser,
												"role": roleUser
											},

											success: function(response) {
												formPanel.close();
												userStore.load();
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
					}]}
					],
					renderTo: Ext.getBody()
				});




	// Ext.create('Ext.grid.Panel', {
	// 	title: 'Projects',
	// 	store: myStore,
	// 	plugins:[{
	// 		ptype:'rowediting',
	// 		clicksToEdit: 2
	// 	}],
	// 	selType: 'rowmodel',
	// 	columns: 
	// 	[
	// 	{ 
	// 		text: 'ID', 
	// 		dataIndex: 'id'
	// 	},
	// 	{ 
	// 		text: 'NAME', 
	// 		dataIndex: 'name', 
	// 		editor: {
	// 			xtype: 'textfield',
	// 			allowBlank: false
	// 		}
	// 	},
	// 	{ 
	// 		text: 'DESCRIPTION', 
	// 		dataIndex: 'description',
	// 		flex: 1,
	// 		editor: {
	// 			xtype: 'textfield',
	// 			allowBlank: false
	// 		} 
	// 	},
	// 	{ 
	// 		text: 'DATE', 
	// 		dataIndex: 'date',
	// 		flex: 1,
	// 		xtype: 'datecolumn',   
	// 		format:'d-m-Y' 
	// 	},
	// 	{ 
	// 		text: 'Login User', 
	// 		dataIndex: 'userLogin',
	// 		flex: 1 
	// 	},
	// 	{
	// 		xtype:'actioncolumn',
	// 		width:40,
	// 		items:[{
	// 			icon:'/resources/img/del.png',
	// 			handler:function (grid, rowIndex, colIndex, e, item) {

	// 				var idProject = item.record.data.id;
	// 				var addUrl = 'http://localhost:8080/project/delete';
	// 				Ext.Ajax.request({
	// 					url: addUrl, 
	// 					method: 'POST',
	// 					headers: { 'Content-Type': 'application/json' },  
	// 					params: {
	// 						id: idProject

	// 					},
	// 					jsonData : {
	// 						"id": idProject

	// 					},

	// 					success: function(response) {
	// 						myStore.removeAt(rowIndex);
	// 					},

	// 					failure: function( response) {
	// 						Ext.Msg.alert("Failed", response.result.message);

	// 					}
	// 				});
	// 			}
	// 		}]
	// 	}
	// 	],
	// 	listeners : {
	// 		beforeedit: function(e) {
	// 			console.log(e);
	// 		},
	// 		edit: function(e) {
	// 			var projectId = e.context.record.data.id;
	// 			var projectName = e.context.record.data.name;
	// 			var projectDescr = e.context.record.data.description;
	// 			var restURL = 'http://localhost:8080/project/update/' + projectId;

	// 			var conn = new Ext.data.Connection();
	// 			conn.request({
	// 				url: restURL,
	// 				method: 'POST',
	// 				params: {
	// 					id: projectId,
	// 					name: projectName,
	// 					description: projectDescr
	// 				},

	// 				success: function(a, response) {
	// 					e.context.record.commit();
	// 				},

	// 				failure: function(a, response) {
	// 					Ext.Msg.alert("Failed", response.result.message);
	// 					e.context.record.reject();
	// 				}
	// 			});
	// 		}
	// 	},

	// 	tbar: [{
	// 		text:'Add Project',
	// 		tooltip:'Add a new Project',
	// 		handler: function() {
	// 			var formPanel=Ext.create('Ext.form.Panel',{
	// 				title: 'Add Project',
	// 				width: 300,
	// 				height:300,
	// 				autoEl: 'center',
	// 				margin: '-50 0 0 200',
	// 				layout: 'anchor',
	// 				defaults: {
	// 					anchor: '80%'
	// 				},
	// 				renderTo: Ext.getBody(),
	// 				items:[{
	// 					xtype: 'textfield',
	// 					fieldLabel: 'Name',
	// 					name: 'name',
	// 					labelAlign: 'top',
	// 					cls: 'field-margin',
	// 					flex: 1
	// 				}, 
	// 				{
	// 					xtype: 'textfield',
	// 					fieldLabel: 'Descr',
	// 					name: 'description',
	// 					labelAlign: 'top',
	// 					cls: 'field-margin',
	// 					flex: 1
	// 				},
	// 				{
	// 					xtype: 'textfield',
	// 					fieldLabel: 'Login User',
	// 					name: 'userLogin',
	// 					labelAlign: 'top',
	// 					cls: 'field-margin',
	// 					flex: 1
	// 				},
	// 				{
	// 					xtype: 'datefield',
	// 					fieldLabel: 'Date project',
	// 					name: 'date',
	// 					labelAlign: 'top',
	// 					cls: 'field-margin',
	// 					flex: 1,
	// 					format:'y-m-d H:i:s'
	// 				}

	// 				],
	// 				buttons: [{
	// 					text: 'Send',
	// 					handler: function(e) {
	// 						console.log(formPanel);

	// 						var nameProject = formPanel.items.items[0].value;
	// 						var descrProject = formPanel.items.items[1].value;
	// 						var userProject = formPanel.items.items[2].value;
	// 						var dateProject = formPanel.items.items[3].value;
	// 						console.log(dateProject);

	// 						var addUrl = 'http://localhost:8080/project/add';
	// 						Ext.Ajax.request({
	// 							url: addUrl, 
	// 							method: 'POST',
	// 							headers: { 'Content-Type': 'application/json' },  
	// 							params: {
	// 								name: nameProject,
	// 								description: descrProject,
	// 								userLogin : userProject,
	// 								date: dateProject
	// 							},
	// 							jsonData : {
	// 								"name": nameProject,
	// 								"description" : descrProject,
	// 								"userLogin" : userProject,
	// 								"date": dateProject
	// 							},

	// 							success: function(response) {
	// 								formPanel.close();
	// 								myStore.load();
	// 							},

	// 							failure: function( response) {
	// 								console.log(response);
	// 								Ext.Msg.alert("Failed", response.result.responseText);
	// 							}
	// 						});
	// 					}
	// 				}, {
	// 					text: 'Cancel',
	// 					handler: function() {
	// 						formPanel.close();
	// 					}
	// 				}],
	// 			});
	// 		}
	// 	}],
	// 	height: 500,
	// 	width: 1000,
	// 	renderTo: Ext.getBody()
	// });
});
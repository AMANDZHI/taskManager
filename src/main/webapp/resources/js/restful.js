Ext.onReady(function() {
    
      var store = new Ext.data.Store({
        url: 'http://localhost:8080/projects',
        reader: new Ext.data.JsonReader({
          // root: 'projects',
          id: 'id'
        }, [
          'id',
          'name',
          'description'
        ])
      });
      store.load();

      var ds_model = Ext.data.Record.create([
        'id',
        'name',
        'description'
      ]);
      

      var id_edit = new Ext.form.TextField();
      var name_edit = new Ext.form.TextField();
      var description_edit = new Ext.form.TextField();
      

      var sm2 = new Ext.grid.CheckboxSelectionModel();
      var grid = new Ext.grid.EditorGridPanel({
        id:'button-grid',
        store: store,
        cm: new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
          {header: "ID", dataIndex: 'id', sortable: true},
          // {id: 'name', header: "Name", dataIndex: 'name', sortable: true, editor: name_edit},
          {header: "Name", dataIndex: 'name', sortable: true, editor: name_edit},
          {header: "Description", dataIndex: 'description', sortable: true, width: 75, editor: description_edit}
        ]),
            


        selModel: new Ext.grid.RowSelectionModel({
          singleSelect: false
        }),
            


        listeners: {
          afteredit: function(e) {
            var _id = e.record.data.id;
            var _name = (e.field == 'name') ? e.value : e.record.data.name;
            var _description = (e.field == 'description') ? e.value : e.record.data.description;
                

            var restURL = 'http://localhost:8080/project/update/' + _id;
            var conn = new Ext.data.Connection();
              conn.request({
                url: restURL,
                method: 'POST',
                params: {
                  id: _id,
                  name: _name,
                  description: _description
                },

                success: function(a, response) {
                  e.record.commit();
                },
                                
                failure: function(a, response) {
                  Ext.Msg.alert("Failed", response.result.message);
                  e.record.reject();
                }
            });
          }
        },

                viewConfig: {
          forceFit:true
        },

        // inline toolbars
        tbar:[{
            text:'Add Project',
                tooltip:'Add a new Project',
                // icon: 'images/addIssuer16.png',
                // cls: 'x-btn-text-icon',
                handler: function() {
                  var form = new Ext.form.FormPanel({
                    baseCls: 'x-plain',
                    labelWidth: 75,
                    name: 'MyForm',
                    url: 'http://localhost:8080/project/add/',
                    defaultType: 'textfield',

                    items: [
            //         {
            //             fieldLabel: 'ID',
            //             id: 'id', 

            //             name: 'id',
            //             xtype: 'textfield',
            //             maxLength: 10,
            //             allowBlank:false,
            //             width: 100,
                                                // listeners: {
            //               afterrender: function(field) {
            //               field.focus(false, 200);
            //             }
            //           }
            //         },
                    {
                        fieldLabel: 'Name',
                        name: 'name',
                        allowBlank:false,
                        anchor: '100%',  // anchor width by percentage
                      }
                    , {
                      fieldLabel: 'Description',
                        name: 'description',
                        maxLength: 10,
                        width: 90

                    }]
                });
              

              var window = new Ext.Window({
                    title: 'Add New Project',
                    width: 350,
                    height:180,
                    minWidth: 350,
                    minHeight: 180,
                    layout: 'fit',
                    plain:true,
                    bodyStyle:'padding:5px;',
                    buttonAlign:'center',
                    resizable: false,
                    items: form,

                    buttons: [{
                        text: 'Save Project',
                        handler: function () {
                          // var formId = Ext.get('id').getValue();
                          var formName = Ext.get('name').getValue();
                          var formDescription = Ext.get('description').getValue();

                          var restURL = 'http://localhost:8080/project/add/';
                var conn = new Ext.data.Connection();
                  conn.request({
                url: restURL,
                method: 'POST',
                params: {
          
                  name: formName,
                  description: formDescription
                },

                success: function(a, response) {
                  // e.record.commit();
                  grid.getStore().insert(
                              0,
                              new ds_model({
                                name: formName,
                                description: formDescription
                              })
                             );
                             window.close();
                            },
      
                
                failure: function(a, response) {
                  // Ext.Msg.alert("Failed", response.result.message);
                  // e.record.reject();
                  Ext.Msg.alert("Failed", response.result.message);
                }
            });
                        
                          

                      //     if (form.getForm().isValid()) {
                      //       form.getForm().submit({
                      //       method: 'POST',
                      //       url: 'http://localhost:8080/project/add/',

                      //       success: function(a, response) {
                      //         grid.getStore().insert(
                      //         0,
                      //         new ds_model({
                      //           name: formName,
                      //           description: formDescription
                      //         })
                      //        );
                      //        window.close();
                      //       },

                      //     failure: function(a, response) {
                      //       Ext.Msg.alert("Failed", response.result.message);
                      //     }
                      //   });
                      // }
                    }
                  },{
                    text: 'Cancel',
                    handler: function () {
                      if (window) {
                        window.close();
                      }
                    }
                  }]
                });
                window.show();
              }
            },'-',{
                text:'Remove Project',
                tooltip:'Remove the selected project',
                handler: function() {
                  var sm = grid.getSelectionModel();
                  var sel = sm.getSelected();
                  if (sm.hasSelection()) {
                    Ext.Msg.show({
                      title: 'Remove Project',
                      buttons: Ext.MessageBox.YESNOCANCEL,
                      msg: 'Remove ' + sel.data.name + '?',
                      fn: function(btn) {
                        if (btn == 'yes') {
                          var conn = new Ext.data.Connection();
                          var restURL = 'http://localhost:8080/project/delete/' + sel.data.id;
                          conn.request({
                            method: 'DELETE',
                            url: restURL,
 
                                                        success: function(resp,opt) {
                              grid.getStore().remove(sel);
                            },
                            
                                                        failure: function(resp,opt) {
                              Ext.Msg.alert('Error', 'Unable to delete project');
                            }
                          });
                        }
                      }
                    });
                  };
                }
            }],

            width: 600,
            height: 350,
            collapsible: true,
            frame: true,
            clicksToEdit: 2,
            animCollapse: false,
            title:'Project Grid Panel for MySql',
            iconCls:'icon-grid',
            renderTo: document.body
        });
    });
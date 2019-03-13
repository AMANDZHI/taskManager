Ext.onReady(function(){
 
    Ext.define('Project', {
           extend: 'Ext.data.Model',
                     
            idProperty: 'id',
                     
            fields: [{
                name: 'id',
                type: 'string'
            }, {
                name: 'name',
                type: 'string'
            }, {
                name: 'description',
                type: 'string'
            }]
      });


    var store = Ext.create('Ext.data.Store', {
                    model: 'Project',
                    autoLoad: true,
                    proxy: {
                            type: 'rest',
                            url: 'http://localhost:8080/projects.json',
                            reader: {
                                type: 'json'
                            }
                }
        });

   

    Ext.create('Ext.grid.Panel', {
        title: 'Projects',
        height: 200,
        width: 400,
        store: store,
        columns: [{
            header: 'ID',
            dataIndex: 'id'
        }, {
            header: 'Name',
            dataIndex: 'name'
        }, {
            header: 'Description',
            dataIndex: 'description'
        }],
        renderTo: Ext.getBody()
    });


});
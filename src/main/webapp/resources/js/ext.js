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
      }

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
    columns: [
        { text: 'ID', dataIndex: 'id' },
        { text: 'NAME', dataIndex: 'name', flex: 1 },
        { text: 'DESCRIPTION', dataIndex: 'description' }
    ],
    height: 200,
    width: 400,
    renderTo: Ext.getBody()
});



  var mainId = Ext.get('mygrid'); // взяли на ссылку элемент дома
  var elements = Ext.select('div .t2');
  var elements = Ext.select('div').item(1);
  var element = Ext.select('div#text2').item(0);
alert(element.dom.innerHTML); //выведет 'Текст3'
var el = element.next();
alert(el.dom.innerHTML); //выведет 'Текст4'
el = element.prev();

element.on('click', function(e, target, options){
        alert('Элемент был нажат');
    }, this);

    //Обработка событий
//     Ext.onReady(function(){
//     Ext.create('Ext.Button', {
//         margin:'10 0 0 30',
//         text: 'Жми здесь',
//         renderTo: Ext.getBody(),
//         listeners: { 
//             click: function(){
//                 alert('Hello World');
//             },
//             scope:this
//         }
//     });
// });

//Делегация на все

// Ext.onReady(function(){
//     var element = Ext.get('content');
//     element.on('click', function(e, target, options){
//         alert(target.innerHTML);
//     }, this, {
//         delegate: '.text'
//     });
// });

  // var project = Ext.create('Project');
  // project.setName('test');
  // console.log(project.getName());

   bigPannel.hide(); // скрываем компонент
        bigPannel.show(); /

        Ext.create('Ext.tab.Panel', {
    title: 'Панель вкладок',
    width: 300,
    height: 200,
    items:[{
        title: 'C#',
        html: 'WPF, ASP.NET, MVC, Windows Forms'
    },{
        title: 'Java',
        html: 'JSP, Java FX, Swing, AWT'
    }],
    renderTo: Ext.getBody()
});

Ext.onReady(function(){
    Ext.create('Ext.Panel', {
        width:300,
        height:200,
        renderTo: Ext.getBody(),
        items   : [{
                xtype: 'button',
                text : 'My Button',
                margin:'75 0 0 125',
                enableToggle: true
            }]
    });
});

Ext.onReady(function(){
    Ext.create('Ext.Panel', {
        width:300,
        height:200,
        renderTo: Ext.getBody(),
        items   : [{
                xtype: 'button',
                text : 'My Button',
                margin:'15 0 0 25',
                listeners: {
                    click: function() { // аналогичен функции в параметре handler
                        // this в данном случае хранит ссылку на текущую кнопку
                        this.setText('Привет мир');
                    },
                    mouseover: function() {// обработчик наведения курсора
                        // если параметр установлен
                        if (!this.mousedOver) {
                            this.mousedOver = true;
                            alert('Вы навели курсором на элемент!\n\nБольше сообщение не будет появляться.');
                        }
                    }
            }
            }]
    });
});

Текстовые поля в ExtJS представлены классом Ext.form.TextField:

Ext.onReady(function(){ 
    var formPanel = Ext.create('Ext.Panel', {
                    title: 'Форма ввода',
                    width: 250,
                    autoHeight: true,
                    bodyPadding: 10,
                    defaults: {
                        labelWidth: 100
                    },
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: 'Ваше имя:',
                        name: 'name'
                    }],
                    renderTo: Ext.getBody()
                });
});

items: [{
    xtype: 'textfield',
    fieldLabel: 'Ваше имя:',
    allowBlank:false,
    name: 'name'
}],

Ext.Ajax.request({
        url: 'ajaxData.json',
        success: function(response, options){
         
        },
        failure: function(response, options){
             
        }
    }); 
    
    Формы представлены компонентом Ext.form.Panel
    var formPanel=Ext.create('Ext.form.Panel',{
        title: 'Форма авторизации',
        width: 300,
        height:200,
        layout: 'anchor',
        defaults: {
            anchor: '80%'
        },
        renderTo: Ext.getBody(),
        items:[{
                xtype: 'textfield',
                fieldLabel: 'Логин',
                name: 'login',
                labelAlign: 'top',
                cls: 'field-margin',
                flex: 1
               }, {
                xtype: 'textfield',
                fieldLabel: 'Пароль',
                name: 'password',
                labelAlign: 'top',
                cls: 'field-margin',
                flex: 1
              }],       
        // кнопки формы
        buttons: [{
            text: 'Оправить',
            handler: function() {
                // действие отправки
            }
        }, {
            text: 'Отмена',
            handler: function() {
                // действие отмены
                }
        }],
    });

    Доступ к полям формы
    var formPanel=Ext.create('Ext.form.Panel',{
        title: 'Форма авторизации',
        width: 300,
        height:200,
        layout: 'anchor',
        defaults: {
            anchor: '80%'
        },
        renderTo: Ext.getBody(),
        items:[{
                xtype: 'textfield',
                fieldLabel: 'Логин',
                name: 'login',
                labelAlign: 'top',
                cls: 'field-margin',
                flex: 1
               }, {
                xtype: 'textfield',
                fieldLabel: 'Пароль',
                name: 'password',
                labelAlign: 'top',
                cls: 'field-margin',
                flex: 1
              }],       
        // кнопки формы
        buttons: [{
            text: 'Оправить',
            handler: function() {
                // действие отправки
            }
        }, {
            text: 'Логин',
            handler: function() {
                var login = formPanel.items.get(0);
                alert(login.getValue());
                }
        }],
    });
});
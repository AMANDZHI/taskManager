$(document).ready(function () {

    $( ".eBtn" ).click(function() {
  	event.preventDefault();
    var name = $(this).attr('name');
    console.log(name);
    $.ajax({
                type : "GET",
                url : "/editProject",
                dataType : 'json',
                data:  {
                    value: name
                },
                success: function(project){
     
               
                            $('.myForm #idProject').val(project.id);
                    
                        $('.myForm #exampleModalLong').modal();
             			edit();
                         
                }
            });
        });

    $( ".cBtn" ).click(function() {
  	event.preventDefault();
                    
                 $('.myForm #exampleModalLong2').modal();
                         create();
                });

    function edit() {
    	$(".sBtn").click(function () {
            event.preventDefault();
            var id = $('.myForm #idProject').val();
            var name = $('.myForm #newName').val();
            var login = $('.myForm #newLogin').val();
            var descr = $('.myForm #newDescr').val();
            console.log(name);

            $.ajax({
                type:"POST",
                url: "/editProject",
                dataType : 'json',
                data: {
                    id : id,
                    name: name,
                    login: login,
                    descr: descr
                },
                success: function(project){
                    $('.myForm #exampleModalLong').modal('hide');

                    $("#" + project.id + "+td").html(project.name);
                    $("#" + project.id + "+td+td").html(project.description);
                    $("#" + project.id + "+td+td+td").html(project.login);
             
                }
            });
        })
    };

    function create() {
    	$(".createProject").click(function () {
            event.preventDefault();
            var name = $('.myForm #name').val();
            var login = $('.myForm #login').val();
            var descr = $('.myForm #descr').val();
            console.log(name);

            $.ajax({
                type:"POST",
                url: "/createProject",
                dataType : 'json',
                data: {
                    name: name,
                    login: login,
                    descr: descr
                },
                success: function(project){

                    $('.myForm #exampleModalLong2').modal('hide');

                    var projectRow = "<tr>" +
                    "<td id=\"" + project.id + "\">" + project.id + "</td>" +
                    "<td>" + project.name + "</td>" +
                    "<td>" + project.description + "</td>" +
                    "<td>" + project.userLogin + "</td>" +
                    "<td>" + project.date + "</td>"+
					"<td>" + " <a class=\"btn btn-dark\" href=\"removeProject\"" + " name=\"" + project.id + "\"" + ">" + "REMOVE" + "</a>" + "</td>" +
					"<td>" + " <a class=\"btn btn-dark\" href=\"editProject\"" + " name=\"" + project.id + "\"" + ">" + "EDIT" + "</a>" + "</td>" +
                    "</tr>";
					
					$('#table tbody').append(projectRow);
             
                }
            });
        })
    }
});

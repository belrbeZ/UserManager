$(document).ready(function () {
    $('form').submit(function(event) {
        register(event);
    });
});

function register(event){
    event.preventDefault();
    $(".alert").html("").hide();
    $(".error-list").html("");
    var formDataCustom = {"email":"", "firstName":"", "lastName":"","enabled":"","phoneNumber":"","id":""};
    $.each($('form').serializeArray(), function(index, param) {
        if(param.name === "email") {
            formDataCustom.email = param.value;
        } else if(param.name === "firstName") {
            formDataCustom.firstName = param.value;
        } else if(param.name === "lastName") {
            formDataCustom.lastName = param.value;
        } else if(param.name === "enabled") {
            formDataCustom.enabled = param.value;
        } else if(param.name === "phoneNumber") {
            formDataCustom.phoneNumber = param.value;
        } else if(param.name === "id") {
            formDataCustom.id = param.value;
        }
    });
    var formDataCustomStr = JSON.stringify(formDataCustom);
    $.ajax({
        type: "PATCH",
        url: serverContext + "user/" + formDataCustom.id,
        processData: false,
        contentType: 'application/json',
        data: formDataCustomStr,
        success: function(data) {
            if(data.statusCode == 102) {
                $.alert(data.description, {
                    autoClose: true,
                    closeTime: 1500,
                    type: 'success'
                });
                setTimeout(function(){
                    window.location.href = serverContext + "user-list";
                }, 2000);
            }
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );

            $.alert(jqXhr.responseJSON.description, {
                autoClose: false,
                type: 'warning'
            });

            if(jqXhr.responseJSON.statusCode == 409) {
                $("#emailError").show().html(jqXhr.responseJSON.description);
            } else {
                var errors = $.parseJSON(jqXhr.responseJSON.description);
                $.each( errors, function( index,item ){
                    if (item.field){
                        $("#"+item.field+"Error").show().append(item.defaultMessage+"<br/>");
                    }
                    else {
                        $("#globalError").show().append(item.defaultMessage+"<br/>");
                    }

                });
            }
        }
    });
}
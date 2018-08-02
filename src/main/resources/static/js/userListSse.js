$(document).ready(function () {

    if (!!window.EventSource) {
        var eventSource = new EventSource("/sse/user/event");

        var elements = document.getElementById("messages");

        function add(message) {
            var element = document.createElement("li");
            element.innerHTML = message;
            elements.appendChild(element);
        }

        eventSource.onmessage = function (e) {

            var message = JSON.parse(e.data);
            add(message.eventType);

            $.ajax({
                type: "GET",
                contentType: 'application/json',
                url: serverContext + "user/list",
                success: function (data) {
                    if (data.statusCode == 103) {
                        $.alert(data.description, {
                            autoClose: true,
                            closeTime: 1500,
                            type: 'success',
                        });
                        $('#userTable > tbody').empty();
                        $.each(data.content, function (index, obj) {
                            var row = '<tr><td>' + obj.email + '</td><td>' + obj.firstName + '</td><td>' + obj.lastName + '</td><td>' + obj.phoneNumber + '</td><td>' + obj.enabled + '</td><td>' + obj.changedAt + '</td><td>' + obj.changedBy + '</td><td><a href="@{/user-change/' + obj.id + '">Edit</a></tr>';
                            $('#userTable > tbody').append(row);
                        });
                    }
                },
                error: function (jqXhr, textStatus, errorThrown) {
                    console.log(errorThrown);

                    $.alert(jqXhr.responseJSON != null ? jqXhr.responseJSON.description : "Smthg went wrong!", {
                        autoClose: false,
                        type: 'warning'
                    });

                    if (jqXhr.responseJSON.statusCode == 409) {
                        $("#emailError").show().html(jqXhr.responseJSON.description);
                    } else {
                        var errors = $.parseJSON(jqXhr.responseJSON.description);
                        $.each(errors, function (index, item) {
                            if (item.field) {
                                $("#" + item.field + "Error").show().append(item.defaultMessage + "<br/>");
                            }
                            else {
                                $("#globalError").show().append(item.defaultMessage + "<br/>");
                            }

                        });
                    }
                }
            });
        };

        eventSource.onopen = function (e) {
            add('connection was opened');
        };

        eventSource.onerror = function (e) {
            if (e.readyState == EventSource.CONNECTING) {
                add('event: CONNECTING');
            } else if (e.readyState == EventSource.OPEN) {
                add('event: OPEN');
            } else if (e.readyState == EventSource.CLOSING) {
                add('event: CLOSING');
            } else if (e.readyState == EventSource.CLOSED) {
                add('event: CLOSED');
            }
        };
    } else {
        alert('The browser does not support Server-Sent Events');
    }
});
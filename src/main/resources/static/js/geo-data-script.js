let jsonResponse;

$.ajax({
    url: "http://localhost:8080/api/geo-data" + document.location.search, success: function (data) {
        jsonResponse = data;

        $("#geo").html("Found location!<br>" +
            data.name + "<br>" +
            "Latitude: " + data.latitude + "<br>" +
            "Longitude: " + data.longitude + "<br>");
    },

    error: function () {
        if(document.location.search === "?location=" || document.location.search === "")
            $("#geo").html("");

        else
            $("#geo").html("Location not found! Try again!");
    }
});
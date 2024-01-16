let jsonResponse;

$.ajax({
    url: "http://localhost:8080/api/quick-results" + document.location.search, success: function (data) {
        jsonResponse = data;

        document.title = "Weather Optimizer - " + jsonResponse[0].location.locationString;

        $("#content").load("results-content.html", function () {

            $("#location").text("Results for: " + jsonResponse[0].location.locationString)

            $.each(jsonResponse, function (index, value) {
                let temp = value.temps[0];
                let precipitationChance = value.precipitationChance
                let sunrise = value.sunrise
                let sunset = value.sunset

                $("#day" + index).html(
                    "<div class='minor'>" + getDateString(value.date, "en-GB") + "</div><br>" +
                    "<div class='temp'><b>" + temp + "°C" + "</b></div><br>" +
                    "<div class='minor'>Precipitation chance: <b>" + precipitationChance + "%" + "</b></div><br>" +
                    "<div class='minor'>Sunrise: <b>" + sunrise + "</b></div><br>" +
                    "<div class='minor'>Sunset: <b>" + sunset + "</b></div><br>"
                );
            });
        });
    },

    error: function () {
        document.getElementById("content").innerHTML = "Error when getting location data (wrong location?). Try again!" + "<br><br><a href=\"../../index.html\">\n" +
            "<button>Back to homepage!</button></a>"
        document.title = "Error! - Weather Optimizer";
    }
});

function getDateString(dateString, locale) {
    let date = new Date(dateString);
    return date.toLocaleDateString(locale, {weekday: "long", year: "numeric", month: "2-digit", day: "2-digit"});
}
let jsonResponse;
let dayOfWeek = 0

$.ajax({
    url: "http://localhost:8080/api/results" + document.location.search,
    success: function (data, textStatus, xhr) {
        jsonResponse = data;
        let keys = Object.keys(jsonResponse)

        keys.push(keys.splice(keys.indexOf("Average"), 1)[0]);

        $("#content").load("results-content.html", function () {
            $("#res-header").text("Results for: " + jsonResponse.OpenMeteo[0].location.locationString);
            $("#res-day").text("Current date: " + getDateString(jsonResponse.OpenMeteo[0].date, "pl-PL"));

            // $("#previous-day-button").on("click", function () { previousData() });
            // $("#next-day-button").on("click", function () { nextData() });

        });
    },

    error: function (xhr, textStatus) {
        document.getElementById("content").innerHTML = "Error when getting location data (wrong location?). Try again!"
    }
});

function getDateString(dateString, locale) {
    let date = new Date(dateString);
    return date.toLocaleDateString(locale, {weekday: "long", year: "numeric", month: "2-digit", day: "2-digit"});
}

function loadData() {
    $("#test-content").html("<h1>" + dayOfWeek + "</h1>")
}

function previousData() {
    if (dayOfWeek === 1) {
        dayOfWeek--;
        $("#previous-day-button").prop("disabled", true);
    } else if (dayOfWeek === 6) {
        dayOfWeek--;
        $("#next-day-button").prop("disabled", false);
    } else
        dayOfWeek--;

    loadData();

}

function nextData() {
    if (dayOfWeek === 0) {
        dayOfWeek++;
        $("#previous-day-button").prop("disabled", false);
    } else if (dayOfWeek === 5) {
        dayOfWeek++;
        $("#next-day-button").prop("disabled", true);
    } else
        dayOfWeek++;

    loadData();
}

function th(text) {
    return "<th>" + text + "</th>";
}

function td(text) {
    return "<td>" + text + "</td>";
}
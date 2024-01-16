let jsonResponse;
let dayOfWeek = 0
let keys = "";
let _7TimerDaySkip = 0;
let _7TimerDaySkip2 = 0;

$.ajax({
    url: "http://localhost:8080/api/results" + document.location.search, success: function (data) {
        jsonResponse = data;
        keys = Object.keys(jsonResponse);

        keys.push(keys.splice(keys.indexOf("Average"), 1)[0]);

        $("#content").load("results-content.html", function () {
            $("#res-header").text("Results for: " + jsonResponse.OpenMeteo[0].location.locationString);
            document.title = "Weather Optimizer - " + jsonResponse.OpenMeteo[0].location.locationString;

            _7TimerDaySkip = _7TimerDaySkip2 = 7 - jsonResponse["7Timer"][0].times.length;

            loadData();
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

function loadData() {
    $("#res-day").text("Current date: " + getDateString(jsonResponse.OpenMeteo[dayOfWeek].date, "en-GB"));

    clearTables();
    prepareTables();
}

function clearTables() {
    $("#temperatures-table").html("");
    $("#precipitation-table").html("");
    $("#precipitation-chance-table").html("");
    $("#sunrise-table").html("");
    $("#sunset-table").html("");
}

function previousData() {
    if (dayOfWeek === 1) {
        dayOfWeek--;
        $("#previous-day-button").prop("disabled", true);
    } else if (dayOfWeek === 6) {
        dayOfWeek--;
        $("#next-day-button").prop("disabled", false);
    } else dayOfWeek--;

    loadData();

}

function nextData() {
    if (dayOfWeek === 0) {
        dayOfWeek++;
        $("#previous-day-button").prop("disabled", false);
    } else if (dayOfWeek === 5) {
        dayOfWeek++;
        $("#next-day-button").prop("disabled", true);
    } else dayOfWeek++;

    loadData();
}

function addTableHeaders(keysArray) {
    let result = "";

    keysArray.forEach((key) => {
        result += ("<th>" + key + "</th>");
    });

    return result;
}

function prepareTables() {
    $("#temperatures-table").append(addTr("<th>Hour</th>" + addTableHeaders(keys) + addTemperature()));
    $("#precipitation-table").append(addTr("<th>Hour</th>" + addTableHeaders(keys) + addPrecipitation(keys)));

    $("#precipitation-chance-table").append(addTr(addTableHeaders(keys) + addPrecipitationChance()));
    $("#sunrise-table").append(addTr(addTableHeaders(keys) + addSunrise()));
    $("#sunset-table").append(addTr(addTableHeaders(keys) + addSunset()));
}

function addTd(text) {
    return "<td>" + text + "</td>";
}

function addTr(text) {
    return "<tr>" + text + "</tr>";
}

function addTemperature() {
    let result = "";

    let timeAmount = jsonResponse.Average[dayOfWeek].times.length;

    for (let i = 0; i < timeAmount; i++) {
        result += addTd(jsonResponse.Average[dayOfWeek].times[i]);

        for (const [key, value] of Object.entries(jsonResponse)) {
            if (value == null) {
                result += addTd("-");
                continue;
            }

            if (key === "Average") continue;

            else {
                if (dayOfWeek === 0 && key === "7Timer" && _7TimerDaySkip > 0) {
                    result += addTd("-");
                    _7TimerDaySkip--;
                }

                else {
                    value[dayOfWeek].times.forEach((element, index) => {
                        if (element === jsonResponse.Average[dayOfWeek].times[i]) {
                            result += addTd(value[dayOfWeek].temps[index] + "°C");
                        }
                    });
                }
            }
        }

        result += addTd(jsonResponse.Average[dayOfWeek].temps[i] + "°C");
        result = addTr(result);
    }

    return result;
}

function addPrecipitation() {
    let result = "";

    let timeAmount = jsonResponse.Average[dayOfWeek].times.length;

    for (let i = 0; i < timeAmount; i++) {
        result += addTd(jsonResponse.Average[dayOfWeek].times[i]);

        for (const [key, value] of Object.entries(jsonResponse)) {
            if (value == null) {
                result += addTd("-");
                continue;
            }

            if (key === "Average") continue;

            else {
                if (dayOfWeek === 0 && key === "7Timer" && _7TimerDaySkip2 > 0) {
                    result += addTd("-");
                    _7TimerDaySkip2--;
                }

                else {
                    value[dayOfWeek].times.forEach((element, index) => {
                        if (element === jsonResponse.Average[dayOfWeek].times[i]) {
                            if(isNaN(Number(value[dayOfWeek].precipitation[index])))
                                result += addTd("-");

                            else
                                result += addTd(value[dayOfWeek].precipitation[index] + "mm");
                        }
                    });
                }
            }
        }

        result += addTd(jsonResponse.Average[dayOfWeek].precipitation[i] + "mm");
        result = addTr(result);
    }

    return result;
}

function addPrecipitationChance() {
    let result = "";

    for (const [key, value] of Object.entries(jsonResponse)) {
        if (value == null) {
            result += addTd("-");
            continue;
        }

        if (key === "Average") continue;

        else {
            if (value[dayOfWeek].precipitationChance <= 100 && value[dayOfWeek].precipitationChance >= 0) result += addTd(value[dayOfWeek].precipitationChance + "%");

            else result += addTd("-");
        }
    }

    result += addTd(jsonResponse.Average[dayOfWeek].precipitationChance + "%")

    return addTr(result);
}

function addSunrise() {
    let result = "";

    for (const [key, value] of Object.entries(jsonResponse)) {
        if (value == null) {
            result += addTd("-");
            continue;
        }

        if (key === "Average") continue;

        else {
            if (value[dayOfWeek].sunrise != null) result += addTd(value[dayOfWeek].sunrise);

            else result += addTd("-");
        }
    }

    result += addTd(jsonResponse.Average[dayOfWeek].sunrise)

    return addTr(result);
}

function addSunset() {
    let result = "";

    for (const [key, value] of Object.entries(jsonResponse)) {
        if (value == null) {
            result += addTd("-");
            continue;
        }

        if (key === "Average") continue;

        else {
            if (value[dayOfWeek].sunset != null) result += addTd(value[dayOfWeek].sunset);

            else result += addTd("-");
        }
    }

    result += addTd(jsonResponse.Average[dayOfWeek].sunset)

    return addTr(result);
}
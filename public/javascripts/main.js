$(function() {
  return $("#subApi").bind("click", function() {
    var matchResult, route;
    route = $("#addMatchResult").val();
    matchResult = {};
    matchResult.firstTeam  = $("#firstTeamInMatch option:selected").val();
    matchResult.secondTeam  = $("#secondTeamInMatch option:selected").val();
    matchResult.firstTeamScore = $("#firstTeamScore").val();
    matchResult.secondTeamScore = $("#secondTeamScore").val();
    return $.ajax({
      url: route,
      type: "POST",
      data: JSON.stringify(matchResult),
      contentType: "application/json",
      error: function(a, b, c) {
        console.log("Error");
        console.error("Error a:" + a);
        console.error("Error b:" + b);
        return console.error("Error c:" + c);
      },
      success: function(data) {}
    });
  });
});

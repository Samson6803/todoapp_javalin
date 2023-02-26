function showPosition(position){
    console.log("Latitude: " +position.coords.latitude +
        "<br> Longitude: " + position.coords.longitude);
}
$(document).ready(function (){
    $("#button").on('click', function (){
        let currency = $('#currency').val();
        $.ajax({
            type:"POST",
            url:"/",
            contentType:"application/json",
            data:JSON.stringify({"currency":currency}),
            dataType: "json",
        })
            .done(function (data, textStatus, jqXHR){
                $('#currencyResult').text(data["data"][currency]["value"]);
            })
    })
});
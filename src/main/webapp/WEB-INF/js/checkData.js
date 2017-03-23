function checkData() {
    var startDate = Date.parse($("#startDate").val());
    var endDate = Date.parse($("#endDate").val());
    var plus1hour = Date.now() + (60*60*1000);
    if(startDate > endDate) {
        alert("Конец торгов должен быть позже чем начало торгов.");
    } else if ((endDate - 2*60*60*1000) < plus1hour || endDate < (startDate + 60*60*1000)) {
        alert("Минимальное время торгов - 1 час.");
    } else {
        if(window.location.pathname.search("edit") > 0) {
            editLot();
        } else {
            addLot();
        }
    }
}

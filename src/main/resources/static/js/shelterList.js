let index = {

    init: function() {
        // 동적으로 생성된 .shelterName 요소에 클릭 이벤트를 바인딩
        $(document).on("click", ".shelterName", function() {

            var shelterId = $(this).attr("data-shelter-id");
            console.log(shelterId);
            index.shelterInfo(shelterId);
        });
    },

    shelterInfo: function(shelterId) {
        let data = {
            id: shelterId
        };

        console.log("shelterInfo data: " + JSON.stringify(data));
        $.ajax({
            type: "POST",
            url: `/shelterInfo/${data.id}`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            console.log("response: " + JSON.stringify(resp));

            let tmp = document.getElementById("infoTitle");
            tmp.innerHTML = resp.title;

            tmp = document.getElementById("infoAddr");
            tmp.innerHTML = resp.address;

            tmp = document.getElementById("infoAddr2");
            tmp.innerHTML = resp.address2;

            tmp = document.getElementById("infoTel");
            tmp.innerHTML = resp.tel;

            tmp = document.getElementById("infoContent");
            tmp.innerHTML = resp.content;

//            tmp = document.getElementById("infoImg");
//            tmp.innerHTML = "<img src='/img/shelter/" + resp.img + "'>";
        }).fail(function(error) {
            console.log(JSON.stringify(error));
        });
    },
}

index.init();

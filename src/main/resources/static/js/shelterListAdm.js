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

// 현재 URL을 가져옵니다
var currentUrl = window.location.href;

// URL 객체를 생성합니다
var url = new URL(currentUrl);

// URLSearchParams 객체를 사용하여 쿼리 스트링 파라미터를 가져옵니다
var searchParams = url.searchParams;

var searchKeyword = "";
// 특정 쿼리 파라미터가 있는지 확인합니다
if (searchParams.has('searchKeyword')) {
    // 특정 쿼리 파라미터의 값을 가져옵니다
    searchKeyword = searchParams.get('searchKeyword');
}

var approval = "";
// 특정 쿼리 파라미터가 있는지 확인합니다
if (searchParams.has('approval')) {
    // 특정 쿼리 파라미터의 값을 가져옵니다
    approval = searchParams.get('approval');
}


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

            tmp = document.getElementById("isApproval");
            tmp.innerHTML ='';



            if(resp.approval=="0"){
                tmp.innerHTML = '<span>미승인 보호소</span><form method="post" action="/approval" >'+
                '<input type="text" value="1" name="approval">'+
                '<input type="text" value="'+resp.id+'" name="id">'+
                '<input type="text" value="'+resp.title+'" name="title">'+
                '<input type="text" value="'+resp.content+'" name="content">'+
                '<input type="text" value="'+resp.address+'" name="address">'+
                '<input type="text" value="'+resp.address2+'" name="address2">'+
                '<input type="text" value="'+resp.lon+'" name="lon">'+
                '<input type="text" value="'+resp.lat+'" name="lat">'+
                '<input type="text" value="'+resp.tel+'" name="tel">'+
                '<input type="text" value="0" name="count">'+

                '<input type="text" value="'+resp.username+'" name="username">'+
                '<input type="text" value="'+resp.fileAttached+'" name="fileAttached">'+
                '<input type="submit" value="승인하기"></form>';
            }else{
                tmp.innerHTML = '<span>승인 보호소</span>';
            }



            tmp = document.getElementById("infoImg");
            tmp.innerHTML = "<img src='/img/shelter/" + resp.img + "'>";
        }).fail(function(error) {
            console.log(JSON.stringify(error));
        });
    },
}

index.init();

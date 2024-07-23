let index ={

    init:function(){
        // 댓글 쓰기를 눌렀을 경우
        $("#btn-reply-save").on("click",()=>{
            this.replySave();
        });

        // 댓글 삭제를 눌렀을 경우
        $(".reply-delete").on("click", function() {
            var replyId = $(this).attr("data-reply-id");
            index.replyDelete(replyId);
        });

        // 댓글 수정을 눌렀을 경우
        $(".reply-update").on("click", function() {
            var replyId = $(this).attr("data-reply-id");
            var replyContent = $(this).closest("div").siblings("div").attr("data-reply-content");
            index.replyUpdateReady(replyContent,replyId);
        });

        // 댓글 수정등록을 눌렀을 경우
        $("#btn-reply-update").on("click",()=>{
            this.replyUpdate();
        });

    },


    replySave: function () {
        let data = {
            boardId : $("#boardId").val(),
            content: $("#reply-content").val()
        };


        $.ajax({
            type: "POST",
            url: `/commnunityV/${data.boardId}/replyW`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("댓글작성이 완료되었습니다.");
                location.href = `/communityV?id=${data.boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },



    replyDelete: function(replyId) {
        let data = {
            boardId : $("#boardId").val(),
            id : replyId
        };

        console.log("replyDelete data : "+data);
        $.ajax({
            type: "POST",
            url: `/commnunityV/${data.boardId}/replyD`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("댓글삭제가 완료되었습니다.");
                location.href = `/communityV?id=${data.boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    replyUpdate: function () {
        let data = {
            boardId : $("#boardId").val(),
            content: $("#reply-content").val(),
            id :$("#replyId").val()
        };

//        alert("boardId : "+data.boardId + ", content : "+ data.content + ", replyId : "+ data.replyId);

        $.ajax({
            type: "POST",
            url: `/commnunityV/${data.boardId}/replyU`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("댓글작성이 완료되었습니다.");
                location.href = `/communityV?id=${data.boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    replyUpdateReady: function(replyContent,replyId) {
        const reply = document.getElementById("reply-content");
        reply.innerHTML = replyContent;
        $('#btn-reply-save').addClass('d-none');
        $('#btn-reply-update').removeClass('d-none');
        $('#btn-reply-update-cancel').removeClass('d-none');

        $('#reply-card-body > form').append("<input type='text' id='replyId' name='replyId' value='"+replyId+"'>");
        const element = document.getElementById('reply-write');
        element.innerText = "댓글 수정";
    },





}
index.init();
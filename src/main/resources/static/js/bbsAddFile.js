let index = {
    init: function() {
        $("#btn-addFile").on("click", () => {
            this.addFile();
        });

        // 이벤트 위임 적용
        $("#inputFile").on("click", ".btn-removeFile", function() {
            index.removeParentFormGroup($(this));
        });

        $("#inputFile").on("click", ".btn-hiddenFile", function() {
            index.hiddenParentFormGroup($(this));
        });
    },
    addFile: function() {
        var addContent = "";
        addContent += '<div class="form-group">'
            + '<input name="files" type="file" accept="image/png, image/jpeg, image/jpg" required>'
            + '<span class="btn-removeFile">x</span>'
            + '</div>';

        $("#inputFile").append(addContent);
    },
    removeParentFormGroup: function(button) {
        button.closest("div.form-group").remove();
    },


    hiddenParentFormGroup: function(button) {
        button.closest("div.form-group").addClass("d-none");
    }
}

index.init();

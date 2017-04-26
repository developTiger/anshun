define(function (require, exports, module) {

    require("jquery");
    require("mutiSelect");
    $(document).ready(function() {
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400
        });
    });

    $(document).ready(function() {
               $('#example-post').multiselect({
                       includeSelectAllOption: true,
                        enableFiltering: true
               });
        });


})
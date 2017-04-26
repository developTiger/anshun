requirejs.config({
    //默认情况下模块所在目录为js/lib
    baseUrl: 'js',
    //当模块id前缀为app时，他便由js/app加载模块文件
    //这里设置的路径是相对与baseUrl的，不要包含.js
    paths: {
        jquery: 'jslib/jquery.min',
        text: 'requireJs-plugins/text',
        template:'template.min',
        bootstrap:'jslib/bootstrap.min',
        np:'jslib/nprogress',
        psbar:'jslib/progressbar/bootstrap-progressbar.min',
        bsPage:'jslib/bootstrap-paginator.min',
        iCheck:'jslib/icheck/icheck.min',
        ztree:'jslib/zTree_v3/js/jquery.ztree.all-3.5.min',
        treeTable:'jslib/treeTable/jquery.treetable',
        validate:'jslib/jquery-plugin/jquery.validate',
        metadata:'jslib/jquery-plugin/jquery.metadata',
        wdatePicker:"jslib/My97DatePicker/WdatePicker",
        calendar:"jslib/calendar/fullcalendar.min",
        moment:"jslib/moment/moment.min",
        mutiSelect:"jslib/bootstrap-multiselect/bootstrap-multiselect",
        uploadify:"jslib/uploadify/jquery.uploadify",
        ajaxUpload:"jslib/ajaxfileupload/ajaxfileupload",
        chained:"jslib/jquery-chained/jquery.chained",
        kindeditor:"jslib/kindeditor/kindeditor-min",
        select2:"jslib/select2/select2.full",
        kindeditor_all:"jslib/kindeditor/kindeditor-all-min",
        tagsinput:"jslib/tagsinput/jquery.tagsinput.min",
        echarts:"jslib/echarts/echarts",
        contabs:"jslib/contabs.min"


    }
});
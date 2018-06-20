/**
 * 实现滚动分页插件
 * 当滚动条滚动底部时，再一次发送请求
* @Description
* @author shisong
* @date 14:49 2018/6/19
* @modifyNote
* @param
* @return
*/
;(function ($) {
    var defaults = {
        interval : 1000,//interval  unit：millisecond  间隔时间 单位 毫秒
        isUseYourSelfCondition : false,//是否使用你自己定义的触发条件  默认不使用
        //trigger Condition  return ==》 Boolean 触发条件 返回boolean类型
        triggerCondition : function () {
            return true;
        },
        //结束条件 返回true 则不在执行
        endCondition : function () {
            return false;
        },
        //显示loading
        showloading : function () {

        },
        //隐藏loading
        hideloading : function () {

        },
        //dosomething 需要做的事情
        _doSomething : function ($ele) {
        }
    };

    function Plugin(element,options) {
        this.el = $(element);
        this.opt = options;
        this._initProcess();
    }

    var innerOptions = {
        firstId :0,
        secondId :0
    }

    Plugin.prototype = {
        _initProcess : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            $plgThis._request();

            $plgThis._bindEvents();
        },
        _bindEvents : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            //绑定事件
        },
        _request : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            $plgThis.checkScroll();

        },
        checkScroll : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            if(opt.isUseYourSelfCondition){
                if(!opt.triggerCondition()) return $plgThis.pollScroll();
            }else{
                if(!$plgThis.lowEnough()) return $plgThis.pollScroll();
            }
            opt.showloading();
            if(isNaN(opt.interval) || opt.interval<=0){
                opt.interval = 1000;
            }
            //修改this的指向，默认指向window， 这里将this指向插件本身
            //setTimeout 和 setInterval  第一个参数是 调用方法的名字，不需要加()
            //setTimeout 只执行一次  setInterval 可重复执行
            innerOptions.firstId = setTimeout($plgThis.doSomething.bind($plgThis),opt.interval);
        },
        doSomething : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;

            opt._doSomething($el);

            if(!opt.endCondition()){
                $plgThis.pollScroll();
            }
            opt.hideloading();
        },
        pollScroll : function () {
            var $plgThis = this;
            var opt = $plgThis.opt;
            var $el = $plgThis.el;
            if(isNaN(opt.interval) || opt.interval<=0){
                opt.interval = 1000;
            }
            //修改this的指向，默认指向window， 这里将this指向插件本身
            innerOptions.secondId = setTimeout($plgThis.checkScroll.bind($plgThis),opt.interval);
        },
        lowEnough : function () {
            var pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
            var viewportHeight = window.innerHeight ||
                document.documentElement.clientHeight ||
                document.body.clientHeight || 0;
            var scrollHeight = window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop || 0;
            return pageHeight - viewportHeight - scrollHeight < 20;
        },

    }


    $.fn.pageHelper = function (params) {
        $(this).each(function () {
            if(typeof params === 'object'){
                params = $.extend({}, defaults, params);
                var nPlu = new Plugin(this,params);
            }
        })
    }


})(jQuery)
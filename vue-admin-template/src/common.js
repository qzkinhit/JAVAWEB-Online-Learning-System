
import $ from 'jquery'
export default {
  go_alert: function (msg) {
    $("body").append(`
    <div class='alert_box' style='position:fixed;z-index:5000;width:100%;height:100%;left:0;top:0px;background: rgba(255, 255, 255, 0)'>
      <div class='content' style='max-width: 70%;text-align:center;position:absolute;font-size:14px;left:50%;top:50%; transform:translateY(-50%) translateX(-50%);-webkit-transform:translateY(-50%) translateX(-50%);background-color:rgba(0,0,0,.5);border-radius:4px'>
        <p style='padding:10px 15px; line-height:1.6; color:#fff;font-size:14px'>${msg}</p>
      <div>
    <div>`);
    setTimeout(function () {
      $(".alert_box").css({ "opacity": "0", "width": "0%", "height": "0" })
      setTimeout(function () { $(".alert_box").remove() }, 2000);
    }, 2000)

  },
  loading: function () {
    $("body").append(`<div class='loading_box' style='position:fixed;z-index:5000;width:100%;opacity:1;height:100%;left:0;top:0px;background-color:transparent;'>
    <div class='content' style='max-width: 70%;text-align:center;position:absolute;font-size:14px;left:50%;top:50%; transform:translateY(-50%) translateX(-50%);-webkit-transform:translateY(-50%) translateX(-50%);border-radius:4px'>
    <img style='width: 40px' src='/static/img/common/loading.gif' />
    <div>
    <div>`);
  },
  loadingHide: function () {
    if ($(".loading_box").length) {
      $(".loading_box").remove();
    }
  },
  exam_alert: function () {
    if (arguments.length >= 1 && typeof arguments[0] == "string") {
      var json = {
        msg: arguments[0],
        agree_text: "",
        cancel_text: '',
        noFunc: false,//回调函数
        // 判断是否传入回调函数
        yesFunc: typeof (arguments[1]) == "function" ? arguments[1] : false
      }
    } else if (typeof arguments[0] == "object") {
      var json = $.extend({

        msg: "你还没有提供任何可展示的信息！",
        agree_text: false,
        cancel_text: false,
        yesFunc: false,
        noFunc: false,
      }, arguments[0]);

    }
    let cancel_text = json.cancel_text ? `<div  class="cancelBtn" style=" cursor: pointer;background: url(/static/img/mockExam/ts_4.gif) no-repeat;width: 84px;
  height: 31px;line-height:31px; text-align:center;">${json.cancel_text}</div>` : '';
    let agree_text = json.agree_text ? `<div class="agreeBtn" style=" cursor: pointer;background: url(/static/img/mockExam/ts_4.gif) no-repeat;width: 84px;
    height: 31px;line-height:31px; text-align:center;">${json.agree_text}</div>` : '';
    let buttons = json.agree_text ? `<div class="buttoncDiv" style="color:#ffffff;font-size:14px;margin-top:15px; display:flex;justify-content: space-around;">${cancel_text}${agree_text}</div>` : '';

    let template = `<div class="confirmModal"
      style="position: fixed;z-index: 1500;
      width: 100%;opacity: 1;height: 100%;left: 0;
      top: 0px;background-color: rgba(68, 68, 68, 0.4)">
     <div class="body_style"
      style="height: 290px;width: 525px;background: url(/static/img/mockExam/ts_d.gif) no-repeat;
      position: absolute;overflow: hidden;
      font-size: 14px;left: 50%; top: 50%;
      transform: translateY(-50%) translateX(-50%);
      -webkit-transform: translateY(-50%) translateX(-50%);
      background-color: #fff;border-radius: 10px;">
      <div style="padding:25px;">
      <div style="background: url(/static/img/mockExam/ts_1.gif) no-repeat;height: 40px;width: 100%;"></div>
      <div style="display: flex;display: -webkit-flex;align-items: center;-webkit-align-items: center;">
      <div style="width:175px; padding:25px 10px 10px 20px;  box-sizing: border-box"><img src="/static/img/mockExam/ts_2.gif" style="width:130px;"/></div>
      <div style="flex: 1;-webkit-flex: 1;display: block;padding:0px 15px; min-width: 0;">
       <div style="color:red; font-size:16px;font-family:宋体;height:100px;font-weight:600;line-height:24px;display:flex; align-items: center;">${json.msg}</div>
      ${buttons}
      </div>
      </div>
      </div>
      </div>
    </div>`
    $("body").append(template)
    // if (!(json.yesFunc && json.agree_text)) {
    //   setTimeout(function () {
    //     // 隐藏弹框
    //     $(".confirmModal").css({ "opacity": "0", "width": "0%", "height": "0" });
    //     setTimeout(function () { $(".confirmModal").remove() }, 0);
    //   }, 3000)
    // }
    $(".confirmModal .buttoncDiv div").click(function () {
      var that = $(this);
      setTimeout(function () {
        // 隐藏弹框
        that.parents(".confirmModal").css({ "opacity": "0", "width": "0%", "height": "0" });
        setTimeout(function () { that.parents(".confirmModal").remove() }, 0);
      }, 0)
      if ($(this).text() == json.agree_text) {
        json.yesFunc && json.yesFunc();
      } else if ($(this).text() == json.cancel_text) {
        json.noFunc && json.noFunc();
      }
    })
  },
  // 倒计时-考试
  daojishi: function (counttime) {
    if (counttime >= 0) {
      let ms = counttime % 60;//余数 89%60==29秒
      let mis = Math.floor(counttime / 60);//分钟
      if (mis >= 60) {
        let hour = Math.floor(mis / 60);
        mis = Math.floor((counttime - hour * 60 * 60) / 60);
        return `${hour > 9 ? hour : `0${hour}`}:${mis > 9 ? mis : `0${mis}`}:${ms > 9 ? ms : `0${ms}`}`;
      } else if (mis >= 1) {
        // return "00:" + mis + ":" + ms;
        return `00:${mis > 9 ? mis : `0${mis}`}:${ms > 9 ? ms : `0${ms}`}`;
      } else {
        ms > 9 ? ms : "0" + ms
        return `00:00:${ms > 9 ? ms : `0${ms}`}`;
      }
    }
  },
  // 格式化时间 ,displaytime 是否显示具体时间，1为显示，2为显示不显示秒，0为不显示
  dateFormat: function (val, displaytime) {
    let date = new Date(val * 1000);
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    let day = date.getDate()
    let hour = date.getHours()
    let minute = date.getMinutes()
    let second = date.getSeconds()
    let str = `${year}-${month > 9 ? month : `0${month}`}-${day > 9 ? day : `0${day}`}`;
    if (displaytime == 1) {
      str = str + ` ${hour > 9 ? hour : `0${hour}`}:${minute > 9 ? minute : `0${minute}`}:${second > 9 ? second : `0${second}`}`
    } else if (displaytime == 2) {
      str = str + ` ${hour > 9 ? hour : `0${hour}`}:${minute > 9 ? minute : `0${minute}`}`
    }
    return str;
  },
  // 根据类型变化返回一些数据的Label
  FormatSelect(options, typeId) {
    let title = '未知'
    options.forEach(item => {
      if (item.Id == typeId) {
        title = item.Label
        return
      } else if (item.value == typeId) {
        title = item.Label
        return
      }
    });
    return title
  },
  // 授课形式
  teachingMethod: [
    {
      value: 1,
      Label: "面授周末班"
    },
    {
      value: 2,
      Label: "面授工作日班"
    },
    {
      value: 3,
      Label: "面授晚班"
    },
    {
      value: 4,
      Label: "在线直播班"
    },
    {
      value: 5,
      Label: "在线录播班"
    },
    {
      value: 6,
      Label: "一对一VIP班"
    },
    {
      value: 7,
      Label: "其他类型"
    }
  ],

}
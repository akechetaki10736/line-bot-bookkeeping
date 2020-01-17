$(function() {
    const useNodeJS = false;   // if you are not using a node server, set this value to false
    // DO NOT CHANGE THIS
    let myLiffId = "";

	myLiffId = defaultLiffId;
	initializeLiffOrDie(myLiffId);
/**
* Check if myLiffId is null. If null do not initiate liff.
* @param {string} myLiffId The LIFF ID of the selected element
*/
function initializeLiffOrDie(myLiffId) {
        initializeLiff(myLiffId);
}

/**
* Initialize LIFF
* @param {string} myLiffId The LIFF ID of the selected element
*/
function initializeLiff(myLiffId) {
    liff
        .init({
            liffId: myLiffId
        })
        .then(function ()  {
            $('#browserLanguage').html(liff.getLanguage());
            $('#sdkVersion').html(liff.getVersion());
            $('#isInClient').html(liff.isInClient());
            $('#isLoggedIn').html(liff.isLoggedIn());
            $('#deviceOS').html(liff.getOS());
            liff.getProfile().then(function(profile) {
                $('#UID').html(profile.userId);
                $("#testP").jqGrid({
                    url:"query",
                    datatype: "json",
                    postData:{UID: profile.userId, session : $('#sskey').text()},
                    colNames:['Item', 'Price', 'Comment', 'Timestamp'],
                    colModel:[
                        {name:'item', width:65},
                        {name:'price', width:150},
                        {name:'comment', width:100},
                        {name:'billTime', width:100, formatter:'date', formatoptions: {newformat:'Y-m-d'} }
                    ],
                    pager: '#test',                 //這是對應到<div>的id，會透過<div>變成分頁的相關UI
                    width: 'auto',                   //jqGrid的寬度
                    height: 'auto',                  //jqGrid的高度
                    rowNum: 10,                      //jqGrid預設顯示筆數
                    rowList: [5, 10, 20, 50],        //jqGrid可選擇每頁顯示幾筆
                    sortname: 'billTime',                //jqGrid預設排序欄位名稱
                    sortorder: "asc",                //jqGrid預設排序方式asc升冪，desc降冪
                    viewrecords: true,               //是否要顯示總筆數
                    caption: 'Ranger清單',           //jqGrid標題欄顯示的文字
                    loadonce: true                   //是否只載入一次
                });
                $('#Name').html(profile.displayName);
            }).catch(function(error) {
                alert('Error getting profile: ' + error);
            });
        })
        .catch((err) => {
			alert('liff sdk init error! err : ' + err);
        });
}

});

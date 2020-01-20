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
                $('#accessToken').val(liff.getAccessToken());
                liff.getProfile().then(function(profile) {
                    $('#UID').val(profile.userId);
                    $('#Name').html(profile.displayName);
                    checkMember(profile.userId);
                }).catch(function(error) {
                    alert('Error getting profile: ' + error);
                    alert(liff.getAccessToken());
                });
            })
            .catch((err) => {
                alert('liff sdk init error! err : ' + err);
            });


    }

    function checkMember(uid) {
        const UID = uid;
        $.get({
            url: "/checkMemberStatus",
            data: {UID: UID},
            success: function (status) {
                if(status){
                    $('#registerButton').val("已是會員");
                    $('#registerButton').prop('disabled', true);
                } else {
                    $('#accountingButton').prop('disabled', true);
                    $('#queryButton').prop('disabled', true);
                }
            },
        });
    }

});

$(function() {
    const useNodeJS = false;   // if you are not using a node server, set this value to false
    const defaultLiffId = "1653663188-x3zv4LO8";   // change the default LIFF value if you are not using a node server

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
                $.get("https://3f49adcb.ngrok.io/query",
                    {UID: profile.userId},
                    function (data) {
                        alert("Data Loaded: " + data);
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

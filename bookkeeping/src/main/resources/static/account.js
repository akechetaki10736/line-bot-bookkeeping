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
                $.get("query",
                    {UID: "U6545f9db35efe7f605d2cf5f9c771a81", session : $('#sskey').text()},
                    function (bill) {

                        alert(JSON.parse(bill));
                        $('#test').html(JSON.parse(bill));

                    });
                $('#Name').html(profile.displayName);
            }).catch(function(error) {
                alert('Error getting profile: ' + error);
                $.get("query",
                    {UID: "U6545f9db35efe7f605d2cf5f9c771a81", session : $('#sskey').text()},
                    function (bill) {

                        alert(JSON.stringify(bill));
                        $('#test').html(JSON.stringify(bill));

                    });
                // $('#Name').html(profile.displayName);
            });
        })
        .catch((err) => {
			alert('liff sdk init error! err : ' + err);

        });
}

});

<%@ include file="include/header.jsp" %>
<body>

Print File:
<p> filename : ${onlineForm.inputFileName}
<p> contentType : ${onlineForm.inputFileType}

<input type="hidden" id="params"/>
<p>
<input type="button" value="Print By AJAX" id="PrintByAJAXBtn"/>

<input type="button" value="Print By Spring" 
	onclick="printOnlineReport('${pageContext.request.contextPath}', 'printFileBySpring', 'testData')"/>

</body>

<%@ include file="include/footer.jsp" %>

<script>

function printOnlineReport(_contextPath, doAction, JSONParam) {
	var submitForm = $('<form></form>');
	submitForm.attr("action", _contextPath + "/OnlineController/" + doAction);
	submitForm.attr('method', 'post');
	submitForm.attr('target', '_blank');
	submitForm.attr("onsubmit", "window.open('', 'formPopup', 'resizable,scrollbars,status')");
	submitForm.attr("target", "formPopup");
    submitForm.append($("<input type='hidden'>").attr("name", "params").val(JSONParam));
    $(document.body).append(submitForm);
    submitForm.submit();
    return false;
};

$("#PrintByAJAXBtn").click(function() {
	var fileMIMEType;
	$.ajax({
		type: 'POST',
	    url: '${pageContext.request.contextPath}/OnlineController/printFileByAJAX',
	    contentType : "application/json",
	    data: JSON.stringify("testData"),
	    cache: false,
	    xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
            	fileMIMEType = xhr.getResponseHeader("fileMIMEType");
            	console.log("xhr type: " + fileMIMEType);
                if (xhr.readyState == 2) {
                    if (xhr.status == 200) {
                        xhr.responseType = "blob";
                    } else {
                        xhr.responseType = "text";
                    }
                }
            };
            return xhr;
        },
	    success: function(data) {
        	console.log("size: " + data.size);
        	console.log("type: " + fileMIMEType);
	    	var file = new Blob([data], { type: fileMIMEType });
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            	window.navigator.msSaveOrOpenBlob(file, "mergeReport.pdf");
            }
            else {
            	var url = window.URL || window.webkitURL;
                var fileURL = url.createObjectURL(file);
                window.open(fileURL, 'Merge Report', 'resizable,scrollbars,status');
            }
	    }
	});
})

</script>
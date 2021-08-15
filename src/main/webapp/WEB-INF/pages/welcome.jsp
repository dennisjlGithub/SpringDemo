<%@ include file="include/header.jsp" %>

<script>

function getJsonData(){
	$.ajax({
        type: "POST",
        url: "http://localhost:8080/tm/JsonController/testJsonparam",
        contentType: "application/json;charset=utf-8",
        data:JSON.stringify({"cnName":"test name"}),
        dataType: "json",
        success:function (message) {
        	alert("success: " + message.cnName);
        },
        error:function (message) {
        	alert("failed: " + message);
        }
    });
} 

</script>

<body>
${apostrophe1}
<br/>
${apostrophe2}
<br/>

Welcome
<br/><br/>
<button type="button" onclick="getJsonData()">getJson</button> 
<br/><br/>
<button type="button" onclick="window.open('http://localhost:8080/tm/JsonController/downloadFileClientByJson', 'pdf', 'resizable,scrollbars,status')">Print PDF from Local</button>
<br/><br/>
<button type="button" onclick="window.open('http://localhost:8080/tm/JsonController/getByteReport', 'test', 'resizable,scrollbars,status')">Print PDF from Report Server </button>  

<p>
========================================
<form:form method="POST" name="testSubmit" action="toSubmit" modelAttribute="onlineForm">

<form:input path="inputText" />

<p>
Drop down list: 
<form:select path="city">
	<form:option selected="true" value="ALL">(Any)</form:option>
	<form:options items="${onlineForm.cityList}" />
	<%-- <form:options items="${countryList}" itemValue="value" itemLabel="description"/> --%>
</form:select>


<p/>=====================<p/>

<form:checkbox path="checkbox1"/>checkbox1
<form:checkbox path="checkbox2"/>checkbox2
<p/>
<form:checkbox path="checkboxes" value="Java"/>Java
<form:checkbox path="checkboxes" value="Net"/>Net
<form:checkbox path="checkboxes" value="Python"/>Python
<p/>
<form:checkboxes items="${books}" path="favoriteBooks"/>

<p/>=====================<p/>
<input type="submit" value="Submit"/>
                  
</form:form>

<p>
========================================
<form method="POST" action="fileUpload" name="fileUpload" modelAttribute="onlineForm" enctype="multipart/form-data">
	Select a file to upload: 
	<input type="file" name="uploadFile" />
	<input type="submit" value="Print"/>
</form>
<p>
========================================
<p>

Webservice:  
&nbsp;&nbsp;<a href="http://localhost:8080/tm/ws/userService?wsdl" target="_blank">Open WSDL</a>
&nbsp;&nbsp;<a href="http://localhost:8080/tm/WSClientController/callWS" target="_blank">Client call</a>

<p>
========================================

</body>

<%@ include file="include/footer.jsp" %>


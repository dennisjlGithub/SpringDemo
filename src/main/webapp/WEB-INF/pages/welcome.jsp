<%@ include file="include/header.jsp" %>

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
<form:form method="POST" name="testForm" id="testForm" action="toSubmit" modelAttribute="onlineForm">

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

<p/>
Gender:   
<form:radiobutton path="gender" value="Male"/>Male 
<form:radiobutton path="gender" value="Female"/>Female
<br><br>

<p/>=====================<p/>
<input type="submit" value="Submit"/>
                  
</form:form>

<button id="toJsonBtn">ToJson</button>
<span id="jsonFormat"></span>
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
<p>
Load Static Query Report: <a href="http://localhost:8080/tm/OnlineController/loadStaticQueryReport" target="_blank">Open</a>

</body>

<%@ include file="include/footer.jsp" %>

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


(function($){ //jquery form serialize to Json object
    $.fn.serializeJson=function(){
      var serializeObj={};
      var array=this.serializeArray();
      var str=this.serialize();
      $(array).each(function(){
        if(serializeObj[this.name]){
          if($.isArray(serializeObj[this.name])){
            serializeObj[this.name].push(this.value);
          }else{
            serializeObj[this.name]=[serializeObj[this.name],this.value];
          }
        }else{
          serializeObj[this.name]=this.value;
        }
      });
      return serializeObj;
    };
  })(jQuery);
  $(document).ready(function(){
    $("#toJsonBtn").click(function(){
      var post_data=$("#testForm").serializeJson(); //form serialize.
      $("#jsonFormat").html(JSON.stringify(post_data));
    })
  })
</script>

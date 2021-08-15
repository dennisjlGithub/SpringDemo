<%@ include file="include/header.jsp" %>
<body>


StaticQueryReport

<br/><br/>

<c:forEach items="${onlineForm.staticQueryReport.staticQueryTemplate}" var="template">
    <b>ReportID: <c:out value="${template.reportID}" /></b> <br/>
    Description: <c:out value="${template.description}" /> <br/>
    ---------- Parameters ----------<br/>
    <c:forEach items="${template.staticQueryParameter}" var="parameter">
    	Type: <c:out value="${parameter.type}" />
    	<br/>
    	Name: 
    	<c:forEach items="${parameter.name}" var="pName">
    		<c:out value="${pName}" />, 
    	</c:forEach>
    	<br/>
    	Label: 
    	<c:forEach items="${parameter.label}" var="pLabel">
    		<c:out value="${pLabel}" />, 
    	</c:forEach>
    	<br/>
    	IsCompulsory: <c:out value="${parameter.isCompulsory}" />
    	<br/>
    	Data: 
    	<c:forEach items="${parameter.data}" var="pData">
    		<c:out value="${pData}" />, 
    	</c:forEach>
    	<p/>
    </c:forEach>
    
    <p/>----------------------------------------<p/>
</c:forEach>

</body>

<%@ include file="include/footer.jsp" %>
<%@ include file="include/header.jsp" %>
<body>


Submit

<br/><br/>
Input text is: <c:out value="${onlineForm.inputText}"/>

<br/><br/>
City is: <c:out value="${onlineForm.city}"/>

<br/><br/>
<c:out value="${onlineForm.checkbox1}" /> -- <c:out value="${onlineForm.checkbox2}" />
<br/><br/>
<c:forEach items="${onlineForm.checkboxes}" var="book">
    [<c:out value="${book}" />]
</c:forEach>
<br/><br/>
Favorite Books:
<c:forEach items="${onlineForm.favoriteBooks}" var="book">
    [<c:out value="${book}" />]
</c:forEach>
<br/><br/>
Gender: <c:out value="${onlineForm.gender}" />

<form:form method="POST" name="testSubmit" action="resetForm" modelAttribute="onlineForm">
<br/>
<input type="submit" value="Back"/>

</form:form>

</body>

<%@ include file="include/footer.jsp" %>
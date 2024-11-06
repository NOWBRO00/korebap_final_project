<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="datas" type="java.lang.Object" %>

<ul>
<c:forEach var="r" items="${datas}">
	<li>${r.writer} >> ${r.content}</li>
</c:forEach>
</ul>
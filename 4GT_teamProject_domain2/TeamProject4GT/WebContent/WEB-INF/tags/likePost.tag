<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="info" required="true" type="java.util.ArrayList"%>

<c:forEach var="pl" items="${info}">
	<a href="selectOne.pdo?pnum=${pl.pnum}" class="d-block tm-mb-40">
		<figure>
			<img src="${pl.path}" alt="${pl.pnum}Image" class="mb-3 img-fluid"
				onerror="this.src='img/defaultImage.png'">
			<figcaption class="tm-color-primary">${pl.title}</figcaption>
		</figure>
	</a>
</c:forEach>
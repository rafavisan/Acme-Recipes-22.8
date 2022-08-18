<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ page import="org.springframework.data.util.Pair" %>

<jstl:forEach var="entry" items="${averageArtifactRetailPrice.entrySet()}">
			<p>${entry}</p>
			</jstl:forEach>

<jstl:forEach var="type" items="${artifactTypes}">
	<h2><acme:message code="administrator.dashboard.title.${type.name().toLowerCase()}" /></h2>
	<h3>Total: ${totalArtifact.get(type)}</h3>
	<table>
		<tr>
			<th></th>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<th>${currency}</th>
			</jstl:forEach>
		</tr>
		<tr>
			<td><acme:message code="administrator.dashboard.averageRetailPriceByCurrency" /></td>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<td>${averageArtifactRetailPrice.get(Pair.of(type, currency))}</td>
			</jstl:forEach>
		</tr>
	</table>
</jstl:forEach>
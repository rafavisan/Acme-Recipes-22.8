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
<%@ page import="org.springframework.data.util.Pair"%>

<jstl:forEach var="type" items="${artifactTypes}">
	<h2>
		<acme:message
			code="administrator.dashboard.title.${type.name().toLowerCase()}" />
	</h2>
	<h3><acme:message
				code="administrator.dashboard.total" />: ${totalArtifact.get(type)}</h3>
	<table>
		<tr>
			<th></th>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<th>${currency}</th>
			</jstl:forEach>
		</tr>
		<tr>
			<td><acme:message
					code="administrator.dashboard.averageRetailPriceByCurrency" /></td>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<td>${averageArtifactRetailPrice.get(Pair.of(type, currency))}</td>
			</jstl:forEach>
		</tr>
		<tr>
			<td><acme:message
					code="administrator.dashboard.DeviationRetailPriceByCurrency" /></td>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<td>${deviationArtifactRetailPrice.get(Pair.of(type, currency))}</td>
			</jstl:forEach>
		</tr>
		<tr>
			<td><acme:message
					code="administrator.dashboard.MinimumRetailPriceByCurrency" /></td>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<td>${minimumArtifactRetailPrice.get(Pair.of(type, currency))}</td>
			</jstl:forEach>
		</tr>
		<tr>
			<td><acme:message
					code="administrator.dashboard.MaximumRetailPriceByCurrency" /></td>
			<jstl:forEach var="currency" items="${acceptedCurrencies}">
				<td>${maximumArtifactRetailPrice.get(Pair.of(type, currency))}</td>
			</jstl:forEach>
		</tr>
	</table>
</jstl:forEach>

<h2>
	<acme:message code="administrator.dashboard.title.FineDish" />
</h2>
<table>
	<tr>
		<th></th>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<th>${status}</th>
		</jstl:forEach>
	</tr>
	<tr>
		<td><acme:message
				code="administrator.dashboard.total" /></td>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<td>${totalFineDish.get(status)}</td>
		</jstl:forEach>
	</tr>
	<tr>
		<td><acme:message
				code="administrator.dashboard.averageFineDishBudget" /></td>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<td>${averageFineDishBudget.get(status)}</td>
		</jstl:forEach>
	</tr>
	<tr>
		<td><acme:message
				code="administrator.dashboard.deviationFineDishBudget" /></td>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<td>${deviationFineDishBudget.get(status)}</td>
		</jstl:forEach>
	</tr>
	<tr>
		<td><acme:message
				code="administrator.dashboard.maximumFineDishBudget" /></td>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<td>${maximumFineDishBudget.get(status)}</td>
		</jstl:forEach>
	</tr>
	<tr>
		<td><acme:message
				code="administrator.dashboard.minimumFineDishBudget" /></td>
		<jstl:forEach var="status" items="${fineDishStatuses}">
			<td>${minimumFineDishBudget.get(status)}</td>
		</jstl:forEach>
	</tr>
</table>
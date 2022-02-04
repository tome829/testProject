<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul id="navi" class="nav nav-pills nav-stacked collapse in">
	<li data-target="TOTAL" <c:if test="${params.category eq 'TOTAL'}">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('TOTAL');">통합검색</a>
	</li>
	<%-- <li data-target="PERSON" <c:if test="${params.category eq 'PERSON'}">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('PERSON');">인물</a>
	</li>
	<li data-target="BOARD" <c:if test="${params.category eq 'BOARD'}">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('BOARD');">게시판</a>
	</li>							
	<li data-target="NEWS" <c:if test="${params.category eq 'NEWS' }">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('NEWS');">뉴스</a>
	</li> --%>
	<li data-target="top_news" <c:if test="${params.category eq 'top_news' }">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('top_news');">테스트 1번</a>
	</li>
	<li data-target="book_info" <c:if test="${params.category eq 'book_info' }">class="active"</c:if> >
		<a href='javascript:void(0);' onclick="goCategory('book_info');">테스트 2번</a>
	</li>
</ul>	
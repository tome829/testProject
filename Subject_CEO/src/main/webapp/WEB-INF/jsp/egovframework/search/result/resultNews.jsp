<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${newsTotal > 0}">
<section>
	<h3>뉴스
	<c:if test="${params.category != 'TOTAL'}">	
		<small><fmt:formatNumber value="${newsTotal}" groupingUsed="true"/>건</small>
	</c:if>

	</h3>
	<ul class="media-list">
	<c:forEach var="result" items="${newsList}" varStatus="status">
	<fmt:parseDate value="${result.created_time}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
    <li class="media">
      <div class="media-body">
        <a class="media-heading"><c:out value="${result.title}"  escapeXml="false"/></a>
        <span><c:out value="${result.user_id}"  escapeXml="false"/><c:out value="${result.post_url}"  escapeXml="false"/> | <c:out value="${result.likes }"/> | <fmt:formatDate value="${dateFmt}"  pattern="yyyy-MM-dd"/></span>
        <p><c:out value="${result.message}"  escapeXml="false"/></p>
 
		
      </div>
      
		

 
		<c:if test="${not empty result.thumbnail}">	

		<!-- 첨부파일 미리보기 -->

	      <div class="media-link">
	      	<ul class="list-inline">
	      		<c:forTokens var="filename" items="${result.thumbnail}" delims="|">
	      			<li><a class="filename"><c:out value="${filename}" escapeXml="false"/></a></li>
		      		<li><span id="" class="preview label label-default">미리보기</span></li>
	      		</c:forTokens>
	      	</ul>
	      </div>
		</c:if>

		<c:forTokens items = "${result.content}" delims = "##@@##" var = "filebody">
		<%-- <c:forEach items = "${fn:split(result.DOCUMENT, '##@@##')}" var = "filebody"> --%>
			<div class="">
				<div id="preview-dialog" class="preview-dialog modal-dialog collapse">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h6>미리보기</h6>
						</div>
						<div class="modal-body">
							<p><c:out value = "${filebody}" escapeXml="false"/></p>
						</div>
					</div>
				</div>      
			</div>      
		<%-- </c:forEach> --%>      
		</c:forTokens>      
      
    </li>
	</c:forEach>

  </ul>
	<c:if test="${newsTotal > params.pageSize}">
	  <c:choose>
	  	<c:when test="${params.category eq 'TOTAL'}">
	      <div class="text-right more">
	      	<a data-target="NEWS">더보기</a>
	      </div>
	    </c:when>
	    <c:otherwise>
	    	<div class="text-center"><ul class="pagination" id="pagination"></ul></div>
	  	</c:otherwise>
	  </c:choose>
	</c:if>	
</section>
<script>
$(function() {
<c:if test="${newsTotal  > newsRow}"> 
    $('#pagination').pagination(<c:out value="${newsTotal}"/>, {
        current_page: <fmt:formatNumber value="${params.offSet / params.pageSize}" minFractionDigits="0"/>,
        callback: gotopage
    });
</c:if>
});
</script>
</c:if>
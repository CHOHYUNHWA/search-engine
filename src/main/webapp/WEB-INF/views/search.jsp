<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>악보 검색</title>
</head>
<body>
<h1>악보 검색</h1>

<!-- 검색 폼 -->
<form action="/search" method="get">
    <label>검색어: <input type="text" name="keyword" required /></label><br/>
    <label>정렬:
        <select name="sortOrder">
            <option value="desc">최신순</option>
            <option value="asc">오래된순</option>
        </select>
    </label><br/>
    <input type="hidden" name="searchType" value="title" />
    <input type="hidden" name="page" value="0" />
    <input type="hidden" name="size" value="10" />
    <button type="submit">검색</button>
</form>

<hr/>

<!-- 검색 결과 출력 -->
<c:if test="${not empty songs}">
    <table border="1">
        <thead>
        <tr>
            <th>곡명</th>
            <th>아티스트</th>
            <th>앨범</th>
            <th>발매일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="song" items="${songs}">
            <tr>
                <td>${song.title}</td>
                <td>${song.artistName}</td>
                <td>${song.albumName}</td>
                <td>${song.issuedDt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${totalPages > 1}">
    <div>
        <c:forEach var="i" begin="0" end="${totalPages - 1}">
            <a href="/search?keyword=${keyword}&sortOrder=${sortOrder}&page=${i}&size=${size}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <strong>[${i + 1}]</strong>
                    </c:when>
                    <c:otherwise>
                        [${i + 1}]
                    </c:otherwise>
                </c:choose>
            </a>
        </c:forEach>
    </div>
</c:if>

<c:if test="${empty songs}">
    <p>검색 결과가 없습니다.</p>
</c:if>

</body>
</html>
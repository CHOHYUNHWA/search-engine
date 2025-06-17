<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>인덱스 등록</title>
    <script>
        window.onload = function () {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get("success") === "true") {
                alert("등록이 완료되었습니다.");
            }
        }
    </script>
</head>
<body>

<h2>Elasticsearch 인덱스 등록</h2>

<form action="/registerIndex" method="post">

    곡ID : <input type="text" name="songId"><br>
    제목: <input type="text" name="title"><br>
    아티스트명: <input type="text" name="artistName"><br>
    앨범ID: <input type="text" name="albumId"><br>
    앨범명: <input type="text" name="albumName"><br>
    앨범커버이미지: <input type="text" name="albumCoverImg"><br>
    키워드: <input type="text" name="keyword"><br>
    <input type="submit" value="등록">
</form>

</body>
</html>
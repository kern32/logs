<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Log Manager</title>
  <link rel="stylesheet" href="resources/css/bootstrap.min.css">
  <script src="resources/js/jquery.min.js"></script>
  <script src="resources/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <h2>Log Files</h2>
  <table class="table">
    <thead>
    <tr>
      <c:forEach items="${columns}" var="item">
        <th>${item}</th>
      </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${logList}" var="log">
      <tr class="${log.status.type}">
        <form action="manage" method="post">
          <td><a href="<%=request.getContextPath()%>/open/show?id=<c:out value="${log.id}" />&file=${log.fileName}">${log.fileName}</a></td>
          <td><c:out value="${log.appName}" /></td>
          <td><c:out value="${log.date}" /></td>
          <td><input type="submit" class="btn btn-primary active" value="remove" name="operation" /></td>
          <td><input name="id" value="${log.id}" style="display: none"></td>
          <td><input name="fileLink" value="${log.href}" style="display: none"></td>
          <td><input name="appName" value="${log.appName}" style="display: none"></td>
        </form>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <form action="manage" method="post" style="width:300px">
    <div class="form-group">
      <label for="appName">Application name:</label>
      <input type="text" class="form-control" id="appName" name="appName" required>

      <label for="fileLink">Log link:</label>
      <input type="text" class="form-control" id="fileLink" name="fileLink">

      <label for="folderLink">Folder link:</label>
      <input type="text" class="form-control" id="folderLink" name="folderLink">
    </div>
    <input type="submit" class="btn btn-primary active" value="add" name="operation" />
  </form>
</div>
</body>
</html>

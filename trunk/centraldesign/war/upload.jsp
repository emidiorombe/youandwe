<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="com.google.appengine.api.blobstore.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload my Photo</title>
</head>
<body>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<form action="<%= blobstoreService.createUploadUrl("/blobstore-servlet?action=upload")%>" method="post" enctype="multipart/form-data">
        <input type="file" name="photoUrl">
        <input type="submit" value="Upload">
    </form>
</body>
</html>
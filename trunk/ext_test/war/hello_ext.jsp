<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World EXTJS</title>
<!-- ** CSS ** -->
<!-- base library -->
<link rel="stylesheet" type="text/css"
	href="/static/css/ext-all.css" />

<!-- overrides to base library -->


<!-- ** Javascript ** -->
        <!-- ExtJS library: base/adapter -->
        <script type="text/javascript" src="/static/js/ext-base.js"></script>
        <!-- ExtJS library: all widgets -->
        <script type="text/javascript" src="/static/js/ext-all-debug.js"></script>
 
 
        <!-- overrides to library -->

<!-- extensions -->

<!-- page specific -->

 <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '/static/images/default/s.gif';
 
        Ext.onReady(function(){
 
           console.info('woohoo!!!');
           alert("Congratulations!  You have Ext configured correctly!");
 
        }); 
        </script>

</head>
<body>

</body>
</html>
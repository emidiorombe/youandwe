<html>
<head>
    <title>Introduction to Ext 2.0: Starter Page</title>
    
    <!-- Include Ext and app-specific scripts: -->
    <script type="text/javascript" src="/static/js/ext-base.js"></script>
    <script type="text/javascript" src="/static/js/ext-all-debug.js"></script>
    <script type="text/javascript" src="/static/js/ExtStart.js"></script>
    
    <!-- Include Ext stylesheets here: -->
    <link rel="stylesheet" type="text/css" href="/static/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="/static/css/ExtStart.css">
    

</head>
<body>
	<h1>Introduction to Ext 2.0: Starter Page</h1>

    <div id="content">
        
    	<p>This is the starter page that accompanies the <a href="http://extjs.com/learn/Tutorial:Introduction_to_Ext2">Introduction to Ext 2.0 tutorial</a> on the Ext JS website.</p>

    	<p>This page is intended to help you interactively explore some of the capabilities of the Ext library, so please make sure that your script references are correct.  This page assumes by default that it is in a directory directly beneath the root Ext deployment directory.  For example, if your Ext directory structure is located at "C:\code\Ext\v1.0\," then this file should be saved in a directory like "C:\code\Ext\v1.0\tutorial\."  If you choose to locate this file somewhere else, then make sure you change the script references of this file as needed.</p>

    	<p>If you have any questions or issues getting this tutorial to work correctly, please stop by the <a href="http://extjs.com/forum/">Ext Forums</a> and ask for help!</p>	

    	<div id="myDiv">This is a test div.</div>
	
    	<input type="button" id="myButton" value="My Button" onclick="myDiv.setOpacity(.25);"/>
    	
    	<div id="grid"></div>
        
	</div>
	
	<div id="msg"></div>
	Name: <input type="text" id="name" /><br />
<input type="button" id="okButton" value="OK" />
</body>
</html>
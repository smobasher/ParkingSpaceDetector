<!------
Salma Mobasher
8120214
Due March 24,2021 
--->
<div>
<table class = "course">
<?php
$db=new SQLite3('./parkingspaces.db');
$stmt = $db->prepare('SELECT * FROM parkingspaces');
$result = $stmt->execute(); 
echo"<td style=background-color:yellow >"."SpaceNum"."</td>"; 
echo"<td style=background-color:yellow>"."IsTaken"."</td>";
while($row=$result->fetchArray(SQLITE3_ASSOC)){
	echo"<tr>";
	foreach($row as $key => $val) //creating a row on the table for each new entry
	{
		echo"<td>".$val."</td>"; 
		
	}
	echo"<tr>";
}

$stmt->close();
$db->close();
?>
</div>
</table>


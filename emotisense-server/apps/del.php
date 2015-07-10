<?php
include("conn.php");

$conn=dbconnect();

$q="delete from USER_MOOD where photo_id>4;";
mysqli_query($conn,$q);

dbclose($conn);
?>

<?php

include("conn.php");

$conn=dbconnect();

$user_id=$_GET['user'];
$mood=$_GET['mood'];

$query = "SELECT name FROM USER_DETAILS WHERE user_id='$user_id';";
$res = mysqli_fetch_assoc(mysqli_query($conn,$query));
$name = $res['name'];
$parts = explode(" ", $name);
$firstname = $parts[0];

if($mood<25)
{
  $imgloc="../icons/sad.gif";
  $moodstr="sad";
}
else if($mood<50)
{
  $imgloc="../icons/confused1.gif";
  $moodstr="confused";
}
else if($mood<75)
{
  $imgloc="../icons/happy.png";
  $moodstr="happy";
}
else
{
  $imgloc="../icons/excited1.png";
  $moodstr="excited";
}
?>
<html>
<head>
<link rel="stylesheet" type="text/css" href="url.css">
</head>
<body>
<div id="container">
<img src="../icons/background_screen.png" id="img1" />
<img src="<?php echo $imgloc; ?>" id="img2" />
<div id="text1"><p><?php echo $firstname; ?> is <?php echo $mood ?>% <?php echo $moodstr;  ?> today</p></div>
</div>
</body>
</html>

<?php

dbclose($conn);

?>

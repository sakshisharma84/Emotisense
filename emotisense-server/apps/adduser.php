<?php

include("conn.php");

$conn=dbconnect();

$user_id = $_GET['user'];
$name = $_GET['name'];
$friend_id = $_GET['friend'];

$user_exists = false;

$check_if_user_exists = "SELECT COUNT(*) FROM USER_DETAILS WHERE user_id='$user_id';";
$res=mysqli_fetch_array(mysqli_query($conn,$check_if_user_exists));
//var_dump($res);
if($res[0]==0)
{
  $insert_new_user = "INSERT INTO USER_DETAILS (user_id, name) VALUES('$user_id','$name');";
  $res1=mysqli_query($conn,$insert_new_user);
//  var_dump($res1);
  if($res1)
  {
    $user_exists = true;
  }
}
else
{
  $user_exists = true;
}

if($user_exists)
{
  foreach($friend_id as $friend)
  {
    $add_friend_1 = "INSERT INTO USER_FRIENDS (user_1, user_2) VALUES('$user_id','$friend');";
    $res1=mysqli_query($conn,$add_friend_1);    
//    var_dump($res1);

    $add_friend_2 = "INSERT INTO USER_FRIENDS (user_1, user_2) VALUES('$friend','$user_id');";
    $res2=mysqli_query($conn,$add_friend_2);
//    var_dump($res2);
  }
  echo true;
}

echo false;

dbclose($conn);

?>

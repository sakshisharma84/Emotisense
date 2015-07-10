<?php

include("conn.php");

$conn=dbconnect();

$photo_id = $_GET['photo'];
$friend_id = $_GET['friend'];
$mood = $_GET['mood'];

$get_photo_user = "SELECT user_id FROM USER_PHOTO WHERE photo_id=$photo_id;";
$res=mysqli_fetch_assoc(mysqli_query($conn,$get_photo_user));
if($res['user_id'] == $friend_id)
{
  echo "User cannot rate his own photo, wrong friend id sent\n";
}
else
{
  $insert_mood = "INSERT INTO USER_MOOD (photo_id, friend_id, mood) VALUES($photo_id, '$friend_id', $mood);";
  $res = mysqli_query($conn, $insert_mood);
}

dbclose($conn);

?>

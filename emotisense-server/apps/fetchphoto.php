<?php

include("conn.php");

$conn=dbconnect();

class Photo
{
  public $photoid;
  public $url;
}
$photos=array();

$user_id = $_GET['user'];
$friend_id = $_GET['friend'];

$check_if_friends = "SELECT COUNT(*) FROM USER_FRIENDS WHERE user_1='$user_id' AND user_2='$friend_id';";

$res=mysqli_fetch_array(mysqli_query($conn,$check_if_friends));
if($res[0])
{
  $get_photos = "SELECT photo_id, location FROM USER_PHOTO WHERE user_id='$friend_id' ORDER BY time_stamp;";
  $res1=mysqli_query($conn,$get_photos);
  while($row=mysqli_fetch_assoc($res1))
  {
    $check_if_rated = "SELECT COUNT(*) FROM USER_MOOD WHERE photo_id=".$row['photo_id']." AND friend_id='$user_id';";
    $res2=mysqli_fetch_array(mysqli_query($conn,$check_if_rated));
    if($res2[0]==0)
    {
      $photo = new Photo();
      $photo->photoid = $row['photo_id'];
      $photo->url = $row['location'];
      $photos[] = $photo;
    }
  }
}

echo json_encode($photos);

dbclose($conn);

?>

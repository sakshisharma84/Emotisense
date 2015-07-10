<?php

include("conn.php");

$conn=dbconnect();

$user_id = $_POST['user'];
$photo = $_FILES['photo'];

if(!$user_id)
  echo "user is NULL\n";

if(!$photo)
  echo "photo is NULL\n";

$ok=1;

$check_if_user_exists = "SELECT COUNT(*) FROM USER_DETAILS WHERE user_id='$user_id';";
$res=mysqli_fetch_array(mysqli_query($conn,$check_if_user_exists));

if($res[0] && exif_imagetype($photo['tmp_name']))
{
  $tmp_target = uniqid().'_'.basename($photo['name']);
  $target = "../files/".$tmp_target;
  $location = "files/".$tmp_target;
  if(!file_exists($target))
  {
    if($photo['size'] <= 2097152)
    {
      if(!move_uploaded_file($photo['tmp_name'],$target))
      {
        echo "Could not write file to disk\n";
        $ok=0;
      }
    }
    else
    {
      echo "Photo size is > 2MB\n";
      $ok=0;
    }
  }
  else
  {
    echo "File already exists\n";
    $ok=0;
  }
}
else
{
  echo "User does not exist or file is not image\n";
  $ok=0;
}

if($ok)
{
  $insert_photo = "INSERT INTO USER_PHOTO (user_id, location) VALUES('$user_id','$location');";
  $res=mysqli_query($conn,$insert_photo);
  if($res)
  {
    echo true;
  }
  else
  {
    unlink($target);
    echo "Could not add file to database\nNot working\n";
  }
}
else
{
  echo "Not working";
}

dbclose($conn);

?>

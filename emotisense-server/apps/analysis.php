<?php

include("conn.php");

$conn=dbconnect();

class Hourly
{
  public $hour;
  public $val;
}

class Weekly
{
  public $date;
  public $val;
}

$MAX_MOOD = 2;

$user_id = $_GET['user'];
$type = $_GET['type'];

function getMood($mood,$count)
{
  global $MAX_MOOD;
  if($count)
    return round(50+($mood*100)/(2*$count*$MAX_MOOD));
  else
    return 50;
}

function dayAnalysis()
{
  global $user_id, $conn;
  $get_todays_mood = "SELECT SUM(a.mood) as mood, COUNT(*) as count FROM USER_MOOD a, USER_PHOTO b WHERE b.user_id='$user_id' and a.photo_id=b.photo_id and DATE(b.time_stamp)=CURDATE();";
  $res = mysqli_fetch_assoc(mysqli_query($conn,$get_todays_mood));
  echo json_encode(getMood($res['mood'],$res['count']));
}

function hourlyAnalysis()
{
  global $user_id, $conn;
  $get_hourly_mood = "SELECT SUM(a.mood) as mood, HOUR(b.time_stamp) as hour, COUNT(*) as count FROM USER_MOOD a, USER_PHOTO b WHERE b.user_id='$user_id' and a.photo_id=b.photo_id and DATE(b.time_stamp)=CURDATE() GROUP BY HOUR(time_stamp);";
  $res = mysqli_query($conn,$get_hourly_mood);
  $moods=array();
  while($row=mysqli_fetch_assoc($res))
  {
    $mood = new Hourly();
    $mood->hour = $row['hour'];
    $mood->val = getMood($row['mood'],$row['count']);
    $moods[] = $mood;
  }
/*  $moods=array();
  $i=0;
  for($i=0; $i<6; $i++)
  {
    $mood = new Hourly();
    $mood->hour = $i;
    $mood->val = $i*10;
    $moods[] = $mood;
  }*/
  echo json_encode($moods);
}

function weekAnalysis()
{
  global $user_id, $conn;
  $get_weekly_mood = "SELECT SUM(a.mood) as mood, DATE(time_stamp) as date, 7-DATEDIFF(CURDATE(),DATE(b.time_stamp)) as iter, COUNT(*) as count FROM USER_MOOD a, USER_PHOTO b WHERE b.user_id='$user_id' and a.photo_id=b.photo_id and DATEDIFF(CURDATE(),DATE(b.time_stamp))>=0 and DATEDIFF(CURDATE(),DATE(b.time_stamp))<7 GROUP BY DATE(time_stamp) ORDER BY DATE(time_stamp);";
  $res = mysqli_query($conn,$get_weekly_mood);
  $moods=array();
  while($row=mysqli_fetch_assoc($res))
  {
    $mood = new Weekly();
    $mood->date = $row['iter'];
    $mood->val = getMood($row['mood'],$row['count']);
    $moods[] = $mood;
  }
/*  $moods=array();
  $i=0;
  for($i=0; $i<3; $i++)
  {
    $mood = new Weekly();
    $mood->date = $i;
    $mood->val = $i*20;
    $moods[] = $mood;
  }
*/
  echo json_encode($moods);
}

switch($type)
{
  case "day":dayAnalysis(); break;
  case "hourly":hourlyAnalysis(); break;
  case "week":weekAnalysis(); break;
}

dbclose($conn);

?>

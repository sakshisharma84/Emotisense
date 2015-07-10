<?php

function dbconnect()
{
  $con = mysqli_connect('localhost', 'emotisense', 'emotisense','emotisense');
  if (!$con) {
      die('Could not connect: ' . mysqli_error());
  }
  return $con;
}

function dbclose($con)
{
  mysqli_close($con);
}

?>


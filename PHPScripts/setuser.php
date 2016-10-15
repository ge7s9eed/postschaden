<?php
/**
 * Created by PhpStorm.
 * User: stuxnet
 * Date: 09.10.16
 * Time: 15:50
 */
header('Content-type: application/json');

/**
 * Konstanten und andere Variablen
 */

$SERVERNAME = "localhost";
$SQLUSERNAME = "proappschmidt";
$SQLPASSWORD = "schmidt89";
$DBNAME = "proappschmidt";

$response = array();
$method = "";

if(isset($_GET["userid"])){
    echo createSuccessRespone("Super !!!!");
}else{
    echo createFailResponse("Es wurden zu wenige Paramter mitgegeben. ");
}




function createSuccessRespone($message){
    $response['code'] = 200;
    $response['status'] = $message;
    return $response;
}

function createFailResponse($message){
    $response['code'] = 500;
    $response['status'] = $message;
      return $response;
}












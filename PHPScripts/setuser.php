<?php
/**
 * Created by PhpStorm.
 * User: stuxnet
 * Date: 09.10.16
 * Time: 15:50
 */
header('Content-type: application/json');

// array for JSON response
$response = array();

if(isset($_POST['userid']) && isset($_POST['name']) && isset($_POST['lastname']) &&
        isset($_POST['token']) && isset($_POST['vertrag'])){

    $response['code'] = 200;
    $response['status'] = "successfull user upload";

    echo json_encode($response);

}else{

    $response['code'] = 500;
    $response['status'] = "not enough Parameters for user upload";

    echo json_encode($response);
}













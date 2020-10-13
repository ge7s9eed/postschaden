<?php
/**
 * Created by PhpStorm.
 * User: stuxnet
 * Date: 09.10.16
 * Time: 22:13
 */

header('Content-type: application/json');

// array for JSON response
$response = array();


if(isset($_POST['userid']) && isset($_POST['fahrzeugnr']) && isset($_POST['location']) &&
    isset($_POST['description']) && isset($_POST['imgurl']) && isset($_POST['timestamp'])){

    $response['code'] = 200;
    $response['status'] = "successfull damage upload";
    logger::log($response['status']);
    // Hier wird die Ausgabe der Response gemacht. Solange die POST Parameter stimmen.
    echo json_encode($response, true);

}else{

    $response['code'] = 500;
    $response['status'] = "not enough Parameters for damage upload";
    logger::log($response['status'], 'warning<');
    echo json_encode($response, true);
}


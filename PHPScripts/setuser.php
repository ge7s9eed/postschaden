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

$response = array();
$method = "";

/**
 * Defines
 */
define('HOST','localhost');
define('USER','proappschmidt');
define('PASS','schmidt89');
define('DB','proappschmidt');






/**
 * Hier wird entschieden, was fuer eine Aktion mit dem User gemacht werden soll
 * bsp. addNewUser, isUserinDB, getUser etc.
 */
if(isset($_POST["method"])){
    $method = $_POST['method'];
}else{
    echo json_encode(createFailResponse('Fail'));
}

switch ($method){
    //Wenn sich ein Benutzer Anmelden will und schon ein login besitzt "user> hol, pwd> 12345"
    case 'loginuser':
        validate($_POST['kuerzel'],$_POST['pwd']);
        break;
    case 'adduser':
       // validate($_POST[''],$_POST[''],$_POST[''],$_POST[''],$_POST[''],$_POST[''],$_POST['']);
        break;
    default:
        echo json_encode(createFailResponse('MethodNotFoundException'));
        break;
}


function validate($kurz, $pwd){
   $con = mysqli_connect(HOST,USER,PASS,DB);

    $sql = "select * from schmidt_user where token='".$kurz."' and pwd='".$pwd."'";
    //$sql = "select * from schmidt_user";
    $res = mysqli_query($con,$sql);
    $result = array();

    while($row = mysqli_fetch_row($res)){
        array_push($result,
            array('userid'=> $row[0],
                'name' => $row[1],
                'lastname' => $row[2],
                'token' => $row[3],
                'pwd' => $row[4],
                'vertrag' => $row[5]));
    }

    echo json_encode(array("result"=>$result));
    mysqli_close($con);

 }




function createSuccessRespone($message, $value){
    $response['code'] = 200;
    $response['status'] = $message;
    $response['value'] = $value;
    return $response;
}

function createFailResponse($message){
    $response['code'] = 500;
    $response['status'] = $message;
    return $response;
}












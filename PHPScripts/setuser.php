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
define('USER','proapp');
define('PASS','**********');
define('DB','**********');

/**
 * Hier wird entschieden, was fuer eine Aktion mit dem User gemacht werden soll
 * bsp. addNewUser, isUserinDB, getUser etc.
 */
if(isset($_POST["method"])){
    $method = $_POST['method'];
}else{
    echo json_encode(createFailResponse('Fail'));
}

// Switch entscheidet, was für eine Aktion gestartet werden soll
// Switch entscheidet, was für eine Aktion gestartet werden soll..
switch ($method){
    //Wenn sich ein Benutzer Anmelden will und schon ein login besitzt "user> hol, pwd> 12345"
    case 'loginuser':
        validate($_POST['kuerzel'],$_POST['pwd']);
        break;
    case 'adduser':
        break;
    default:
        echo json_encode(createFailResponse('MethodNotFoundException'));
        break;
}

/*
* Function validiert den Username und das Password. sehr sehr schlecht...
*/
function validate($kurz, $pwd){
   $con = mysqli_connect(HOST,USER,PASS,DB);
   // In so vielen Schichten falsch. Passwörter müssen gehasht sein. So nicht.
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

/*
* Function create an Success-Array
*/
function createSuccessRespone($message, $value){
    $response['code'] = 200;
    $response['status'] = $message;
    $response['value'] = $value;
    return $response;
}

/*
* Function create Faild-Array
* Do never create a failed-array this way.
*/
function createFailResponse($message){
    $response['code'] = 500;
    $response['status'] = $message;
    return $response;
}

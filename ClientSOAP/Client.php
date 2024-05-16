<?php
// Initialize WS with the WSDL
$client = new SoapClient("http://localhost:8080/?wsdl");
$para=new stdClass();
$para->montant=100;
$response1=$client->__soapCall("ConversionEuroToDH",array($para));
$response2=$client->__soapCall("getCompte", array(1));
$response3 = $client->__soapCall("listComptes", array());

//Convert stdClass to array
$response2=$response2->return;
$response3=$response3->return;
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test SOAP</title>
</head>
<body>
    <p>Le montant en DHS : 
    </p>
    <p>Le compte est :
    <!--<?php var_dump($response2) ?>-->
    </p>
    <p>Liste des comptes : <?php var_dump($response3) ?></p>
</body>
</html>
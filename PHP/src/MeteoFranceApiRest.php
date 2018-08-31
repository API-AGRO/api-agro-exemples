<?php
// PHP sample of a REST call to API-AGRO Platform
//
// This sample uses the Météo France API to retrieve Arome and Arpege model data from a given location
//
// More information on Arome : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/le-modele-a-maille-fine-arome
// More information on Arpege : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/les-modeles-de-prevision-de-meteo-france
?>

<?php
require '../vendor/autoload.php';
$client = new \GuzzleHttp\Client(['verify' => false]);
$server_url = 'https://plateforme.api-agro.fr/service/sorties-de-modeles-meteofrance';
?>
<html>
<head>
    <title>Exemple Météo France</title>
</head>
<body>

<h1>API-AGRO - Exemple Météo France</h1>

<div class="request">
    <h2>GET /arome?lat={latitude}&long={longitude} :</h2>
    <?php $res = $client->get($server_url.'/arome?lat=48.85839179087659&long=2.33861966446477'); ?>

    <div class="content">
        <pre><?php echo $res->getBody(); ?></pre>
    </div>
</div>

<div class="request">
    <h2>GET /arpege?lat={latitude}&long={longitude} :</h2>
    <?php $res = $client->get($server_url.'/arpege?lat=48.85839179087659&long=2.33861966446477'); ?>

    <div class="content">
        <pre><?php echo $res->getBody(); ?></pre>
    </div>
</div>

</body>
</html>

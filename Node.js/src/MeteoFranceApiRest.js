// Node sample of a REST call to API-AGRO Platform
//
// This sample uses the Météo France API to retrieve Arome and Arpege model data from a given location
//
// More information on Arome : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/le-modele-a-maille-fine-arome
// More information on Arpege : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/les-modeles-de-prevision-de-meteo-france
const request = require('request');
const server_url = "https://plateforme.api-agro.fr/service/sorties-de-modeles-meteofrance";

// Asynchronous request of Arome data
request(server_url + '/arome?lat=48.85839179087659&long=2.33861966446477', { json: true }, (err, res, body) => {
    console.log("\nGET /arome?lat={latitude}&long={longitude}");
    if (err) { return console.log(err); }

    // Display HTTP status code
    console.log("Code retour HTTP : " + res.statusCode);

    // JSON data are stored into res.body object. Here we are only displaying the number of hits
    console.log("Nombre de résultats : " + res.body.nhits);

});

// Asynchronous request of Arpege data
request(server_url + '/arpege?lat=48.85839179087659&long=2.33861966446477', { json: true }, (err, res, body) => {
    console.log("\nGET /arpege?lat={latitude}&long={longitude}")
    if (err) { return console.log(err); }

    // Display HTTP status code
    console.log("Code retour HTTP : " + res.statusCode);

    // JSON data are stored into res.body object. Here we are only displaying the number of hits
    console.log("Nombre de résultats : " + res.body.nhits);
});
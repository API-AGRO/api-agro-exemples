# Python sample of a REST call to API-AGRO Platform
#
# This sample uses the Météo France API to retrieve Arome and Arpege model data from a given location
#
# More information on Arome : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/le-modele-a-maille-fine-arome
# More information on Arpege : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/les-modeles-de-prevision-de-meteo-france
import requests
import json
import datetime

# Save base enpoint as variable
server_url = "https://plateforme.api-agro.fr/service/sorties-de-modeles-meteofrance"

print("GET /arome?lat={latitude}&long={longitude}")

# Request URL for Arome data
request = server_url + "/arome?lat=48.85839179087659&long=2.33861966446477"

# Use request library to call API-AGRO platform
arome_request = requests.get(request)

# Get JSON data
arome_data = arome_request.json()

# Display the number of hits
print("Nombre de résultats : " + str(arome_data["nhits"]))

print("--------------------------------")

print("GET /arpege?lat={latitude}&long={longitude}")

# Request URL for Arpege data
request = server_url + "/arpege?lat=48.85839179087659&long=2.33861966446477"

# Use request library to call API-AGRO platform
arpege_request = requests.get(request)

# Get JSON data
arpege_data = arome_request.json()

# Display the number of hits
print("Nombre de résultats : " + str(arpege_data["nhits"]))

# R sample of a REST call to API-AGRO Platform
#
# This sample uses the Météo France API to retrieve Arome and Arpege model data from a given location
#
# More information on Arome (french) : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/le-modele-a-maille-fine-arome
# More information on Arpege (french) : http://www.meteofrance.fr/prevoir-le-temps/la-prevision-du-temps/les-modeles-de-prevision-de-meteo-france

# Package installation (uncomment if necessary)
#install.packages(c("jsonlite"))

# load jsonlite library
library(jsonlite)

print("GET /arome?lat={latitude}&long={longitude}")

# Save base enpoint as variable
server_url <- 'https://plateforme.api-agro.fr/service/sorties-de-modeles-meteofrance'

# Request result stored into a variable
arome_data <- fromJSON(paste0(server_url, "/arome?lat=48.85839179087659&long=2.33861966446477"))

# Display number of results (nhits key)
print(paste0("Number of results : ", arome_data$nhits))


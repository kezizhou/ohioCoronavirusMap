# ohioCoronavirusMap
Displays a map of COVID-19 cases in Ohio by county

### Current Format
Currently, a CSV of data from the ODH [here](https://coronavirus.ohio.gov/wps/portal/gov/covid-19/home/dashboard) can be downloaded. Using the OpenCSV library, this CSV is converted into a list of County objects with county name and number of cases per county.

### Previous Format
The project was initially designed to retrieve an HTML file of the [https://coronavirus.ohio.gov/wps/portal/gov/covid-19/] website, since the raw data was not publicized at that time. The HTML contained a list of counties with COVID-19 confirmed cases and the number per county.
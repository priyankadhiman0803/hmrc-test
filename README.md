Command to run the programme

This is a microservice which will calculate the sum total of the fruits bought in a superstore.
The list of fruits is passed with the curl command
In order to run the application  try this command on the terminal->  sbt run

1) If no need to apply the discount add N at the end of the url as shown below
curl -X GET localhost:9000/calculate/discount/N -H 'Content-Type: application/json' -d '["Apple","Apple","orange"]'
2) If discount needs to be applied , add Y
curl -X GET localhost:9000/calculate/discount/Y -H 'Content-Type: application/json' -d '["Apple","Apple","orange"]'


application.conf holds the following two parameters :
1) allowedFruitList -> which holds the list of fruits allowed in the billing. This list can be updated as per requirment.
2) If the list passed in curl command has
any item not in this list , a message will appear "Elements entered should match the approved list i.e <list of items>"
3) priceList -> which will hold the price of each item in the allowedFruitList in the same order fruit is listed. 
If there is a mismatch between the list, then a message will appear.
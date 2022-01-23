# RestAssured_CRUD_TEST
is a project that give a detail Test file where the GET,POST,PUT,PATCH and DELETE Method are used.
The Test is based on the api coming from  https://devqa.io/rest-assured-api-requests-examples/ 
getDataApi() is a Test Method that implement the GET Method
postDataApi() is a Test Method that implement the POST Method
putDataApi() is a Test Method that implement the PUT Method
patchDataApi() is a Test Method that implement the PATCH Method
deleteDataApi() is a Test Method that implement the DELETE Method

#Two Deserialization TEST METHODS are added
-deserializationTest() 
This method will deserialize the body to an array Object and will query the body to get the first object 
so it can verify that the first Object of the Array is equal to the Json Object .
-deserializedObjectTest()
This method will deserialize the body to an Object and will  verify that the Object  is equal to the Json Object .
Method POST and GET are used for this test.



## Second server for xml parser

   This server must accept the data from the server №1 and put them into the database. 
   The database has a separate table for banks and payers/recipients (the same entity, the same table "organizations"). 
   
   When saving new document, search for the bank with BIC = Bank_PAY.BIC_PAY/Inf_RCP.BIC_PAY in the table of banks; if such bank is found, take it, put a reference to it. 
   If the bank is not found, then create a new entry in the table with banks. Then use its ID.
   
   The same saving scheme with the payer/recipient. First you need to search for the INN+KPP combination of the entry in the table of organizations and if such entry is found and it is 1, then take it and set the reference to it.
   If the organization is not found, you should search by Name. If such a record is found and it is 1, then take it by putting the reference to it. If the organization is not found after the step above, create a new entry in the table with the organizations. Then use its ID.
   
   The server must have the following endpoints:
   * endpoint that will receive data from the first server;
   * the endpoint with the statistics. On GET request it must return the number of documents in the system and their average amount;
   An * endpoint that returns data of the following form:
	  "name_organization(payer/recipient)" {
		"pay": "number of documents where this organization is the payer", 
		{ "rec": { "number of documents where this organization is the recipient"
	  }, ...
     It should also be possible to pass organization name/his id to this endpoint and it will return data only for this organization

#### There are the following endpoints in the system:
| Endpoint name      | URL                                | Description                                                   | Available actions                
| --------           |-------------                       | -------------                                                 | -------------    
| `acceptData`       | */acceptData*                      | Accepting data from first server                              | Parse data from first server
| `getStat`          | */check*                           | Returning  list of payers/recipients for all organizations    | Get list of payers/recipients for all organizations 
| `getStat`          | */check/{id}*                      | Returning information for current organization                | Get information for current organization        
| `getStat`          | */check/org/{organizationName}*    | Returning information for current organization                | Get information for current organization
| `stat`             | */average*                         | Returning average sum of docs and count of docs               | Get average sum of docs and count of docs in the system


Tech stack:
- Spring (Boot, Data, Web)
- Postgresql
- Hibernate
- Lombok

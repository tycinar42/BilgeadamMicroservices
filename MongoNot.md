#MongoDB i�in Notlar

�ncelikle ilgili db ye ge�ilmesi gereklidir.

use dbadi

db.createUser(
{
user: "accountUser",
pwd: passwordPrompt(),  // Or  "<cleartext password>"
roles: [ "readWrite", "dbAdmin" ]
}
)

db.createUser({user: "bilgeuser",pwd: "bilge!!**",roles: [ "readWrite", "dbAdmin" ]})
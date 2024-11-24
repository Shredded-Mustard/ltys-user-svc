use admin
db.createUser({
    user: "noketchupadmin",
    pwd: "sausage",
    roles: [{
        role: "readWrite",
        db: "userdb"
    }]
});
//Esto es como un import
const express = require("express");
const app = express();

const PORT = 3000;

const games =[
    {"id":1, "Name":"Among Us", "Genre": "Strategy"},
    {"id":2, "Name":"Clash Royale", "Genre": "Tower Defense"},
    {"id":3, "Name":"Minecraft", "Genre": "Adventure"}
];

app.get("/games" , (req, res)=>{
    res.json(games);
});


app.listen(PORT , ()=>{ //ARROW FUNCTION   
    console.log("App is running on port: " + PORT);
    

});
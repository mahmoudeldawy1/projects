var express = require("express")
var bodyParser = require("body-parser")
var port = 4000
var db = require('./db')
var app = express()
var Item = require('./itemmodel');
console.log(Item); 


app.use(bodyParser.json())

app.get("/", async (req, res) => {
  try {
    var findItems = await Item.find()
    res.status(201).json(findItems)
  } catch (error) {
    res.status(400).json({error:error.message})
  }
})

app.post('/',async(req, res)=>{
    try{
var item  =  new Item (req.body);
var savedItem = await item.save();
res.status(201).json(savedItem);

    }catch(error){
        res.status(400).json({error:error.message})

    }
})

app.put('/:id',async(req, res)=>{
    try{
        var id = req.params.id;
        var updateItem = await Item.findByIdAndUpdate(req.params.id, req.body, {new:true});
        res.status(201).json(updateItem);
    }catch(error){
        res.status(400).json({error:error.message})
    }
})

app.delete('/:id',async(req, res)=>{
    try{
      var deleteItem = await Item.findByIdAndDelete(req.params.id); 
        res.status(201).json(deleteItem);
    }catch(error){
        res.status(400).json({error:error.message})
    }
}
)
app.listen(port, ()=>{
    console.log('server is running')
})



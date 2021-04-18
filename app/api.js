var java = require("java");
java.classpath.push("bin/decolib.jar");

var planner = java.import("decolib.planner");

//console.log(planner.sampleSync());

function get_deco(depth, time) {
    let deco = planner.deco_dive_stringSync(depth, time);
    return deco;
}

console.log(get_deco(45,30));

function get_ndl(depth) {
    return planner.no_decoSync(depth);
}

function test() {
    alert("test");
}
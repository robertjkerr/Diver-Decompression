
function get_deco() {

    let java = require("java");
    java.classpath.push("bin/decolib.jar");
    let planner = java.import("decolib.planner");

    let depth = 45;
    let time = 30;

    let deco = planner.deco_dive_stringSync(depth, time);    

    
    return deco;
}

async function testdeco() {
    const deco = await get_deco();
    alert("test");
}

function test() {
    alert("test");
}

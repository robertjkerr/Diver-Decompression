var java = require("java");
java.classpath.push("bin/decolib.jar");

var planner = java.import("decolib.planner");

console.log(planner.sampleSync());
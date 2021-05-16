//JS functions for front/backend interaction

var java = require("java");
java.classpath.push("bin/decolib.jar");



function get_deco(depth, time) {
	let tracker = java.newInstanceSync("decolib.tracker");
	
}






function reset_input() {
	document.getElementById('depth').value = '';
	document.getElementById('time').value = '';
}


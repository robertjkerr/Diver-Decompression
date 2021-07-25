//JS functions for front/backend interaction



function get_deco(depth, time) {
	let java = require("java");
	java.classpath.push("bin/decolib.jar");

	let planner = java.import("decolib.planner");


	let deco = planner.deco_dive_stringSync(depth, time);
	alert(deco);
}






function reset_input() {
	document.getElementById('depth').value = '';
	document.getElementById('time').value = '';
}


//JS functions for front/backend interaction



function get_deco(gasMixes, GFs, segments) {
	let java = require("java");
	java.classpath.push("bin/decolib.jar");

	let planner = java.import("decolib.planner");


	let deco = planner.deco_dive_stringSync(gasMixes, segments, GFs);
	alert(deco);
}






function reset_input() {
	document.getElementById('depth').value = '';
	document.getElementById('time').value = '';
}

